import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.in;
import static java.nio.file.Files.write;

public class TCPClient {
    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.out.println("Usage: java TCP Client <serverHost> <serverPort>");
            return;
        }
        //int serverPort;
        String serverHost = args[0];
        int serverPort = Integer.parseInt(args[1]);

        Scanner keyboard = new Scanner(in);
        //String input = keyboard.nextLine();
        char command;
        do {
            System.out.println("Enter a command (L for List, D for Delete, R for Rename, O for Download, U for Upload, Q for Quit.):");
            String input = keyboard.nextLine();
            command = input.toUpperCase().charAt(0);
            int serverPort1 = serverPort;
            switch (command) {
                case 'L':
                    SocketChannel channel = SocketChannel.open();
                    channel.connect(new InetSocketAddress(serverHost, serverPort));

                    ByteBuffer commandBuffer = ByteBuffer.allocate(2);
                    commandBuffer.putChar(command);
                    commandBuffer.flip();
                    channel.write(commandBuffer);

                    ByteBuffer replyBuffer = ByteBuffer.allocate(5000);
                    int bytesRead = channel.read(replyBuffer);
                    replyBuffer.flip();
                    byte[] data = new byte[bytesRead];
                    replyBuffer.get(data);
                    System.out.println(new String(data));


                    channel.close();
                    break;

                case 'D':
                    System.out.println("pick the file you want to delete");
                    channel = SocketChannel.open();
                    commandBuffer = ByteBuffer.allocate(2);

                    String selectedFile = keyboard.nextLine();
                    // File file = new File("SelectedFile", SelectedFile);
                    try {
                        channel.connect(new InetSocketAddress(serverHost, serverPort1));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    commandBuffer.putChar(command);
                    commandBuffer.flip();
                    try {
                        channel.write(commandBuffer);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    ByteBuffer fileNameBuffer = ByteBuffer.wrap(selectedFile.getBytes());
                    channel.write(fileNameBuffer);
                    try {
                        channel.shutdownOutput();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String response;
                    try {
                        replyBuffer = ByteBuffer.allocate(16);
                        bytesRead = channel.read(replyBuffer);
                        replyBuffer.flip();
                        byte[] responseBytes = new byte[bytesRead];
                        replyBuffer.get(responseBytes);
                        response = new String(responseBytes);
                        //selectedFile = br.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //file = new File(selectedFile);

                    //selectedFile = null;

                    if (response.equals("S")) {
                        System.out.println("operation sucessful");
                    } else {
                        System.out.println("operation failed");
                    }
                    channel.close();
                    break;
                case 'R':
                    System.out.println("Enter the name of the file you want to rename:");
                    commandBuffer = ByteBuffer.allocate(2);
                    channel = SocketChannel.open();
                    selectedFile = keyboard.nextLine();
                    //file = new File("SelectedFile", SelectedFile);
                    channel.connect(new InetSocketAddress(serverHost, serverPort));
                    commandBuffer.putChar(command);
                    commandBuffer.flip();
                    channel.write(commandBuffer);
                    String filename;
                    System.out.println("what name do you want to change it to?");
                    String newName = keyboard.nextLine();
                    String both = selectedFile + ":" + newName;
                    //renameFile.renameTo(new File(SelectedFile));
                    ByteBuffer nameBuffer = ByteBuffer.wrap(both.getBytes());
                    channel.write(nameBuffer);

                    ByteBuffer replyBuffer2 = ByteBuffer.allocate(1024);
                    bytesRead = channel.read(replyBuffer2);
                    replyBuffer2.flip();
                    byte[] a = new byte[bytesRead];
                    replyBuffer2.get(a);
                    System.out.println(new String(a));

                    channel.close();
                    break;
                case 'O':
                    ByteBuffer commandBuffer2 = ByteBuffer.allocate(2);
                    System.out.println("Enter the name of the file you want to download:");
                    selectedFile = keyboard.nextLine();
                    commandBuffer2.putChar(command);
                    commandBuffer2.flip();
                    channel = SocketChannel.open();
                    channel.connect(
                            new InetSocketAddress(serverHost, serverPort));
                    channel.write(commandBuffer2);

                    ByteBuffer lengthBuffer = ByteBuffer.allocate(4); //length
                    lengthBuffer.putInt(selectedFile.length());
                    lengthBuffer.flip();
                    channel.write(lengthBuffer);

                    nameBuffer = ByteBuffer.wrap(selectedFile.getBytes()); //name
                    channel.write(nameBuffer);


                    //channel.shutdownOutput();
                    replyBuffer2 = ByteBuffer.allocate(1024);
                    bytesRead = channel.read(replyBuffer2);
                    replyBuffer2.flip();
                    if (bytesRead <= 0) {
                        System.out.println("No response from server.");
                        channel.close();
                        break;
                    }

                    String status = new String(replyBuffer2.array(), 0, bytesRead);
                    if (status.equals("F")) {
                        System.out.println("Server: File not found.");
                        channel.close();
                        break;
                    }

                    File outFile = new File("ClientFiles", selectedFile);
                    FileOutputStream fos = new FileOutputStream(outFile);
                    FileChannel outChannel = fos.getChannel();

                    ByteBuffer contentBuffer = ByteBuffer.allocate(1024);
                    while ((bytesRead = channel.read(contentBuffer)) > 0) {
                        contentBuffer.flip();
                        outChannel.write(contentBuffer);
                        contentBuffer.clear();
                    }

                    outChannel.close();
                    fos.close();
                    System.out.println("File downloaded.");
                    channel.close();
                    break;

                case 'U':
                    System.out.print("Enter file name to upload: ");
                    String fileName = keyboard.nextLine();
                    File file2 = new File("ClientFiles", fileName);

                    if (!file2.exists()) {
                        System.out.println("File not found.");
                        break;
                    }

                    channel = SocketChannel.open(new InetSocketAddress(serverHost, serverPort));
                    commandBuffer = ByteBuffer.allocate(2);
                    commandBuffer.putChar(command);
                    commandBuffer.flip();
                    channel.write(commandBuffer);

                    lengthBuffer = ByteBuffer.allocate(4);
                    lengthBuffer.putInt(fileName.length());
                    lengthBuffer.flip();
                    channel.write(lengthBuffer);

                    nameBuffer = ByteBuffer.wrap(fileName.getBytes());
                    channel.write(nameBuffer);

                    FileInputStream fis = new FileInputStream(file2);
                    FileChannel fileChannel = fis.getChannel();
                    contentBuffer = ByteBuffer.allocate(1024);
                    while (fileChannel.read(contentBuffer) != -1) {
                        contentBuffer.flip();
                        channel.write(contentBuffer);
                        contentBuffer.clear();
                    }
                    fis.close();

                    channel.shutdownOutput();

                    ByteBuffer statusBuffer = ByteBuffer.allocate(1);
                    channel.read(statusBuffer);
                    statusBuffer.flip();
                    status = new String(statusBuffer.array());
                    System.out.println(status.equals("S") ? "Upload complete." : "Upload failed.");

                    channel.close();
                    break;
                case 'Q':
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command, please try again.");
            }

        }

        while (command != 'Q');
    }
}