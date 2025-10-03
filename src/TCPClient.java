import javax.swing.*;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.out.println("client commands for tcp server");
            return;

    Scanner keyboard = new Scanner(System.in);
    String input = keyboard.nextLine();
    char command;
    do {
        System.out.println("Enter a command (List, Delete, Rename,Download, Upload, Quit):");
        String input = keyboard.nextLine();
        command = input.toUpperCase().charAt(0);
        switch (command) {
            case 'List':
                Scanner keyboard = new Scanner(System.in);
                String input = keyboard.nextLine();
                channel.connect(new InetSocketAddress(args[0], serverPort));
                commandBuffer.putChar(command);
                commandBuffer.flip();
                channel.write(commandBuffer);

                java.io.ObjectInputStream = new ObjectInputStream(socket.getInputStream());
                Map<String, String> commands = (Map<String, String>) in.readObject();

                System.out.println("Available Commands:");
                for (Map.Entry<String, String> entry : commands.entrySet()) {
                    System.out.println("  " + entry.getKey() + ": " + entry.getValue());
                }
                bytesRead = channel.read(replyBuffer);
                replyBuffer.flip();
                a = new byte[bytesRead];
                replyBuffer.get(a);
                System.out.println(new String(a));


                break;
            case 'Delete':
                System.out.println("pick the file you want to delete");
                String SelectedFile = keyboard.nextLine();
                File file = new File("SelectedFile", SelectedFile);
                channel.connect(new InetSocketAddress(args[0], serverPort));
                commandBuffer.putChar(command);
                commandBuffer.flip();
                channel.write(commandBuffer);
                channel.shutdownOutput();
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String selectedFile = br.readLine();
                File file = new File(selectedFile);

                selectedFile = null;

                if (file.delete()) {
                    System.out.println("File deleted");
                }
                else {
                    System.out.println("Failed to delete file");
                }
                bytesRead = channel.read(replyBuffer);
                replyBuffer.flip();
                a = new byte[bytesRead];
                replyBuffer.get(a);
                System.out.println(new String(a));

                break;
            case 'Rename':
                System.out.println("Enter the name of the file you want to upload:");
                String SelectedFile = keyboard.nextLine();
                File file = new File("SelectedFile", SelectedFile);
                channel.connect(new InetSocketAddress(args[0], serverPort));
                commandBuffer.putChar(command);
                commandBuffer.flip();
                channel.write(commandBuffer);

                if (selectedFile != null) {
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                } else {
                    System.out.println("File selection cancelled.");
                }
                System.out.println("what name do you want to change it to?");
                selectedFile.renameTo(new file(scanner.nextLine()));

                bytesRead = channel.read(replyBuffer);
                replyBuffer.flip();
                a = new byte[bytesRead];
                replyBuffer.get(a);
                System.out.println(new String(a));
                break;
            case 'Download':
                System.out.println("Enter the name of the file you want to
                        upload:");
                String SelectedFile = keyboard.nextLine();
                File file = new File("SelectedFile", SelectedFile);
                channel.connect(new InetSocketAddress(args[0], serverPort));
                commandBuffer.putChar(command);
                commandBuffer.flip();

                ReadableByteChannel rbc = Channels.newChannel(in);
                FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

                bytesRead = channel.read(replyBuffer);
                replyBuffer.flip();
                a = new byte[bytesRead];
                replyBuffer.get(a);
                System.out.println(new String(a));
                break;
            case 'Upload':
                System.out.println("Enter the name of the file you want to
                        upload:");
                String SelectedFile = keyboard.nextLine();
                File file = new File("SelectedFile", SelectedFile);

                if (selectedFile != null) {
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                } else {
                    System.out.println("File selection cancelled.");
                }
                channel.connect(new InetSocketAddress(args[0], serverPort));
                commandBuffer.putChar(command);
                commandBuffer.flip();
                channel.write(commandBuffer);
                ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
                int fileNameLength = SelectedFile.length();
                lengthBuffer.putInt(fileNameLength);
                lengthBuffer.flip();
                channel.write(lengthBuffer);
                ByteBuffer nameBuffer = ByteBuffer.wrap(SelectedFile.getBytes());
                channel.write(nameBuffer);
               each time
                FileInputStream fis = new FileInputStream(file);
                FileChannel fc = fis.getChannel();
                ByteBuffer contentBuffer = ByteBuffer.allocate(1024);
                while(fc.read(contentBuffer) != -1) {
                    contentBuffer.flip();
                    channel.write(contentBuffer);
                    contentBuffer.clear();
                    use!
                }
                channel.shutdownOutput();
                    fis.close();
                bytesRead = channel.read(contentBuffer);
                contentBuffer.flip();
                a = new byte[bytesRead];
                replyBuffer.get(a);
                System.out.println(new String(a));
                break;
                    case 'Quit':
                    System.exit(0);
                    channel.connect(new InetSocketAddress(args[0], serverPort));
                    commandBuffer.putChar(command);
                    commandBuffer.flip();
                    channel.write(commandBuffer);
                        break;

        }
    }
}
