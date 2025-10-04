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
    static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.out.println("client commands for tcp server");
            return;
            int serverPort;
    Scanner keyboard = new Scanner(in);
    String input = keyboard.nextLine();
    char command;
    do {
        System.out.println("Enter a command (L, D, R,D, U, Q):");
        input = keyboard.nextLine();
        command = input.toUpperCase().charAt(0);
        int serverPort1 = serverPort;
        switch (command) {
            case "L":
                keyboard = new Scanner(in);
                input = keyboard.nextLine();
                SocketChannel channel = null;
                try {
                    channel.connect(new InetSocketAddress(args[0],
                            serverPort1));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ByteBuffer commandBuffer = null;
                commandBuffer.putChar(command);
                commandBuffer.flip();
                try {
                    channel.write(commandBuffer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Map<String, String> commands;
                commands = (Map<String, String>) (in);

                System.out.println("Available Commands:");
                for (Map.Entry<String, String> entry : commands.entrySet()) {
                    System.out.println("  " + entry.getKey() + ": " + entry.getValue());
                }
                command = (char) channel.read(commandBuffer);
                byte[] a = new byte[command];
                commandBuffer.get(a);
                System.out.println(new String(a));


          ;      break;
            case "D":
                System.out.println("pick the file you want to delete");
                String SelectedFile = keyboard.nextLine();
                File file = new File("SelectedFile", SelectedFile);
                try {
                    channel.connect(new InetSocketAddress(args[0], serverPort1));
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
                try {
                    channel.shutdownOutput();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String selectedFile = "";
                selectedFile = null;
                try {
                    selectedFile = br.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                file = new File(selectedFile);

                selectedFile = null;

                if (file.delete()) {
                    System.out.println("File deleted");
                }
                else {
                    System.out.println("Failed to delete file");
                }
                command = (char) channel.read(commandBuffer);
                commandBuffer.flip();
                a = new byte[command];
                commandBuffer.get(a);
                System.out.println(new String(a));

                break;
            case "R":
                System.out.println("Enter the name of the file you want to rename:");
                SelectedFile = keyboard.nextLine();
                file = new File("SelectedFile", SelectedFile);
                channel.connect(new InetSocketAddress(args[0], serverPort1));
                commandBuffer.putChar(command);
                commandBuffer.flip();
                channel.write(commandBuffer);
                String filename;
                System.out.println("what name do you want to change it to?");
                File renameFile = new File(keyboard.nextLine());
                renameFile = new File(String.valueOf(file));
                renameFile.renameTo(new File(SelectedFile));

                command = (char) channel.read(commandBuffer);
                commandBuffer.flip();
                a = new byte[command];
                commandBuffer.get(a);
                System.out.println(new String(a));
                break;
            case 'D':
                System.out.println("Enter the name of the file you want to download:");
                SelectedFile = keyboard.nextLine();
                commandBuffer.putChar(command);
                commandBuffer.flip();
                channel = SocketChannel.open();
                channel.connect(
                        new InetSocketAddress(args[0], serverPort));
                channel.write(commandBuffer);
                channel.shutdownOutput();
                ByteBuffer replyBuffer = ByteBuffer.allocate(1024);
                int bytesRead = channel.read(replyBuffer);
                file = new File("SelectedFile", SelectedFile);
                Process socket = null;
                InputStream inputStream = socket.getInputStream();

                FileOutputStream fos = null;
                commandBuffer.flip();
                a = new byte[command];
                commandBuffer.get(a);
                System.out.println(new String(a));
                break;
            case "U":
                System.out.println("Enter the name of the file you want to upload:");
                SelectedFile = keyboard.nextLine();
                file = new File("SelectedFile", SelectedFile);

                ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
                int fileNameLength = SelectedFile.length();
                lengthBuffer.putInt(fileNameLength);
                lengthBuffer.flip();
                channel.write(lengthBuffer);
                ByteBuffer nameBuffer = ByteBuffer.wrap(SelectedFile.getBytes());
                channel.write(nameBuffer);
                FileInputStream fis = new FileInputStream(file);
                FileChannel fc = fis.getChannel();
                ByteBuffer contentBuffer = ByteBuffer.allocate(1024);
                while(fc.read(contentBuffer) != -1) {
                    contentBuffer.flip();
                    channel.write(contentBuffer);
                    contentBuffer.clear();
                }
                channel.shutdownOutput();
                    fis.close();
                command = (char) channel.read(contentBuffer);
                contentBuffer.flip();
                a = new byte[command];
                SelectedFile.getChars(a);
                System.out.println(new String(a));
                break;
            case 'Q':
                    System.exit(0);
                        break;


        default:
                System.out.println("Invalid command, please try again.");
            }

        } while(command != 'Q');}

    }
}