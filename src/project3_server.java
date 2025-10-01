import java.io.File;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
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
            String command = String.valueOf(commandBuffer.getChar());
            switch (command){
                case "LIST":
                    //commandBuffer = ByteBuffer.allocate(20);
                    //byte[] a = new byte[bytesRead];
                    //will send to default
                    File folder = new File(".");
                    String[] fileNames = folder.list();
                    StringBuilder clientMessage = new StringBuilder("Server Files: ");

                    if (fileNames != null && fileNames.length > 0) {
                        for (String name : fileNames) {
                            clientMessage.append(name).append("\n");
                        }
                    } else {
                        clientMessage.append("(No files found)\n");
                    }

                    System.out.println(clientMessage);

                    ByteBuffer replyBuffer = ByteBuffer.wrap(clientMessage.toString().getBytes()); //we do these things, like udp
                    serveChannel.write(replyBuffer);
                    serveChannel.close();
                    break;
                case "DELETE":
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
                case "RENAME":
                    messageBuffer = ByteBuffer.allocate(1024); //this is for fileName
                    bytesRead = serveChannel.read(messageBuffer);
                    messageBuffer.flip();
                    a = new byte[bytesRead];
                    messageBuffer.get(a);
                    String anotherClientMessage = new String(a);
                    folder = new File(".");
                    fileNames = folder.list();
                    for (String name : fileNames) {
                        if (Objects.equals(name, anotherClientMessage)) {
                            System.out.println("Testing that I got the name");
                        } else {
                            System.out.println("Testing that I didn't get the name");
                        }
                    }
                    //match specific file like delete, then change its name in the folder


                    break;
                case "DOWNLOAD":
                    break;
                case "UPLOAD":
                    break;
                case "QUIT":
                    String theClientMessage = "You quit.";
                    System.out.println(theClientMessage); //we are just echoing the message back to the client

                    replyBuffer = ByteBuffer.wrap(theClientMessage.getBytes()); //we do these things, like udp
                    serveChannel.write(replyBuffer);
                    serveChannel.close();
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
