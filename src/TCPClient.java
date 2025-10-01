import javax.swing.*;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) throws Exception {


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

                java.io.ObjectInputStream = new ObjectInputStream(socket.getInputStream());
                Map<String, String> commands = (Map<String, String>) in.readObject();

                System.out.println("Available Commands:");
                for (Map.Entry<String, String> entry : commands.entrySet()) {
                    System.out.println("  " + entry.getKey() + ": " + entry.getValue());
                }
                channel.connect(new InetSocketAddress(args[0], serverPort));
                commandBuffer.putChar(command);
                commandBuffer.flip();
                channel.write(commandBuffer);
                break;
            case 'Delete':
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                JFileChooser JFileChooser = new JFileChooser();
                JFileChooser.setTitle("pick a file");
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                String selecedFile = br.readLine();
                File file = new File(selecedFile);

                if (file.delete()) {
                    System.out.println("File deleted");
                }
                else {
                    System.out.println("Failed to delete file");
                }
                channel.connect(new InetSocketAddress(args[0], serverPort));
                commandBuffer.putChar(command);
                commandBuffer.flip();
                channel.write(commandBuffer);
                break;
            case 'Rename':
                JFileChooser JFileChooser = new JFileChooser();
                JFileChooser.setTitle("pick a file");
                File selectedFile = fileChooser.showOpenDialog(primaryStage);

                if (selectedFile != null) {
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                } else {
                    System.out.println("File selection cancelled.");
                }
                System.out.println("what name do you want to change it to?");
                selectedFile.renameTo(new file(scanner.nextLine()));
                channel.connect(new InetSocketAddress(args[0], serverPort));
                commandBuffer.putChar(command);
                commandBuffer.flip();
                channel.write(commandBuffer);
                break;
            case 'Download':

                break;
            case 'Upload':
                JFileChooser JFileChooser = new JFileChooser();
                JFileChooser.setTitle("pick a file");
                File selectedFile = fileChooser.showOpenDialog(primaryStage);

                if (selectedFile != null) {
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                } else {
                    System.out.println("File selection cancelled.");
                }
                channel.connect(new InetSocketAddress(args[0], serverPort));
                commandBuffer.putChar(command);
                commandBuffer.flip();
                channel.write(commandBuffer);
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
