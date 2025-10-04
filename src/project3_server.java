import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Objects;

public class project3_server {
    public static void main(String[] args) throws Exception{
        int port = 3000;
        ServerSocketChannel listenSocket = ServerSocketChannel.open();
        listenSocket.bind(new InetSocketAddress(3000));
        while (true){
            //accept the client connection request and establish dedicated channel with the new client
            //serveChannel represents this channel

            SocketChannel serveChannel = listenSocket.accept(); //whenever new request, accept it to establish channel, works with connect on client side
            ByteBuffer commandBuffer = ByteBuffer.allocate(2);
            int bytesRead = serveChannel.read(commandBuffer); //read client message
            commandBuffer.flip();
            char command = commandBuffer.getChar();
            switch (command){
                case 'L': //or L
                    //commandBuffer = ByteBuffer.allocate(20);
                    //byte[] a = new byte[bytesRead];
                    File folder = new File(".");
                    String[] fileNames = folder.list();
                    StringBuilder clientMessage = new StringBuilder("Server Files: ");
                    if (fileNames != null && fileNames.length > 0) {
                        for (String name : fileNames) {
                            clientMessage.append(name).append("\n");
                        }
                    } else {
                        clientMessage.append("No files found\n");
                    }
                    System.out.println(clientMessage);
                    ByteBuffer replyBuffer = ByteBuffer.wrap(clientMessage.toString().getBytes()); //we do these things, like udp
                    serveChannel.write(replyBuffer);
                    serveChannel.close();
                    break;
                case 'D': //or D
                    ByteBuffer messageBuffer = ByteBuffer.allocate(1024); //this is for fileName
                    bytesRead = serveChannel.read(messageBuffer);
                    messageBuffer.flip();
                    byte[] a = new byte[bytesRead];
                    messageBuffer.get(a);
                    String otherClientMessage = new String(a);
                    String send;
                    File fileInFolder = new File("." , otherClientMessage);
                    //fileNames = folder.list();
                    if (fileInFolder.exists() && fileInFolder.isFile()) {
                        if (fileInFolder.delete()) {
                            send = "S";
                        } else {
                            send = "F";
                        }
                    } else {
                        send = "F"; // file not found
                    }
                    //access file name, then check to see if it exists
                    replyBuffer = ByteBuffer.wrap(send.getBytes());
                    serveChannel.write(replyBuffer);
                    serveChannel.close();
                    break;
                case 'R': //or R
                    messageBuffer = ByteBuffer.allocate(1024); //this is for fileName
                    bytesRead = serveChannel.read(messageBuffer);
                    messageBuffer.flip();
                    a = new byte[bytesRead];
                    messageBuffer.get(a);
                    String anotherClientMessage = new String(a);
                    String[] parts = anotherClientMessage.split(":", 2);
                    String result;
                    if (parts.length == 2) {
                        String oldName = parts[0];
                        String newName = parts[1];

                        File oldFile = new File(".", oldName);
                        File newFile = new File(".", newName);

                        if (oldFile.exists() && oldFile.isFile()) {
                            boolean renamed = oldFile.renameTo(newFile);
                            if (renamed) {
                                result = "File was renamed."; // success
                            } else {
                                result = "File wasn't renamed."; // failed
                            }

                        } else {
                            result = "Cannot find the file you want to rename.";
                        }

                    } else {
                        result = "Didn't give an old name and a new name.";
                    }
                    replyBuffer = ByteBuffer.wrap(result.getBytes()); //we do these things, like udp
                    serveChannel.write(replyBuffer);
                    serveChannel.close();
                    break;

                    /*
                    folder = new File(".");
                    fileNames = folder.list();
                    for (String name : fileNames) {
                        if (Objects.equals(name, anotherClientMessage)) {
                            System.out.println("Testing that I got the name");
                        } else {
                            System.out.println("Testing that I didn't get the name");
                        }
                    }
                    */
                    //match specific file like delete, then change its name in the folder

                case 'O': //or O? N? Something
                    ByteBuffer lengthBuffer = ByteBuffer.allocate(4); //filename length
                    serveChannel.read(lengthBuffer);
                    lengthBuffer.flip();
                    int fileNameLength = lengthBuffer.getInt();
                    ByteBuffer nameBuffer = ByteBuffer.allocate(fileNameLength); //filename
                    serveChannel.read(nameBuffer);
                    nameBuffer.flip();
                    String fileNameBStr = new String(nameBuffer.array());
                    File file = new File("ServerFiles", fileNameBStr);
                    if (file.exists() && file.isFile()) {
                        serveChannel.write(ByteBuffer.wrap("S".getBytes()));
                        FileInputStream fis2 = new FileInputStream(file);
                        FileChannel fileChannel = fis2.getChannel();
                        ByteBuffer downloadBuffer = ByteBuffer.allocate(1024);
                        while (fileChannel.read(downloadBuffer) != -1) {
                            downloadBuffer.flip();
                            serveChannel.write(downloadBuffer);
                            downloadBuffer.clear();
                        }
                        fileChannel.close();
                        fis2.close();
                    } else {
                        serveChannel.write(ByteBuffer.wrap("F".getBytes()));
                    }
                    serveChannel.close();
                    break;
                case 'U':
                    ByteBuffer anotherLengthBuffer = ByteBuffer.allocate(4); //filename length
                    serveChannel.read(anotherLengthBuffer);
                    anotherLengthBuffer.flip();
                    fileNameLength = anotherLengthBuffer.getInt();
                    ByteBuffer fileNameBuffer = ByteBuffer.allocate(fileNameLength); //filename
                    serveChannel.read(fileNameBuffer);
                    fileNameBuffer.flip();
                    String fileNameBufferStr = new String(fileNameBuffer.array());
                    file = new File("ServerFiles", fileNameBufferStr);
                    FileOutputStream fos = new FileOutputStream(file);
                    FileChannel outChannel = fos.getChannel();
                    ByteBuffer contentBuffer = ByteBuffer.allocate(1024);
                    int bytesReadUpload;
                    while ((bytesReadUpload = serveChannel.read(contentBuffer)) > 0) {
                        contentBuffer.flip();
                        outChannel.write(contentBuffer);
                        contentBuffer.clear();
                    }
                    outChannel.close();
                    fos.close();
                    ByteBuffer status = ByteBuffer.wrap("S".getBytes());
                    serveChannel.write(status);
                    serveChannel.close();
                    break;
                case 'Q':
                    //String theClientMessage = "You quit.";
                    //System.out.println(theClientMessage); //we are just echoing the message back to the client

                    //replyBuffer = ByteBuffer.wrap(theClientMessage.getBytes()); //we do these things, like udp
                    //serveChannel.write(replyBuffer);
                    //serveChannel.close();
                    break;
                /*
                case 'D': //not hard, just need to reply
                    byte[] a = new byte[bytesRead];
                    //messageBuffer.get(a);
                    String clientMessage = new String(a);
                    LocalDateTime nowAgain = LocalDateTime.now(); //had to change now to nowAgain
                    clientMessage = nowAgain.toLocalDate().toString(); //changing date to clientmessage for return
                    System.out.println(clientMessage); //we are just echoing the message back to the client

                    ByteBuffer replyBuffer = ByteBuffer.wrap(clientMessage.getBytes()); //we do these things, like udp
                    serveChannel.write(replyBuffer);
                    serveChannel.close();
                    break;
                case 'T':
                    a = new byte[bytesRead];
                    //messageBuffer.get(a);
                    clientMessage = new String(a);
                    LocalDateTime now = LocalDateTime.now();
                    clientMessage = now.toLocalTime().toString();
                    System.out.println(clientMessage); //we are just echoing the message back to the client

                    replyBuffer = ByteBuffer.wrap(clientMessage.getBytes()); //we do these things, like udp
                    serveChannel.write(replyBuffer);
                    serveChannel.close();
                    break;
                case 'E':
                    ByteBuffer messageBuffer = ByteBuffer.allocate(1024); //NOT needed for other two commands
                    bytesRead = serveChannel.read(messageBuffer);
                    messageBuffer.flip();

                    a = new byte[bytesRead];
                    messageBuffer.get(a);
                    clientMessage = new String(a);
                    System.out.println(clientMessage); //we are just echoing the message back to the client

                    replyBuffer = ByteBuffer.wrap(clientMessage.getBytes()); //we do these things, like udp
                    serveChannel.write(replyBuffer);
                    serveChannel.close();
                    break;
                default:
                    System.out.println("Invalid command recieved.");

                 */
            }
            /*
            byte[] a = new byte[bytesRead];
            commandBuffer.get(a);
            String clientMessage = new String(a);
            System.out.println(clientMessage); //we are just echoing the message back to the client

            ByteBuffer replyBuffer = ByteBuffer.wrap(clientMessage.getBytes());
            serveChannel.write(replyBuffer);
            serveChannel.close();
            */
        }
    }
}
