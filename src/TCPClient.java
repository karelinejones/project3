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
        System.out.println("Enter a command (L for List, D for Delete, R for Rename, O for Download, U for Upload, Q for Quit):");
        String input = keyboard.nextLine();
        command = input.toUpperCase().charAt(0);
        switch (command) {
            case 'L':
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
            case 'D':
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
            case 'R':
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
            case 'O':

                break;
            case 'U':
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
            case 'Q':
                System.exit(0);
                channel.connect(new InetSocketAddress(args[0], serverPort));
                commandBuffer.putChar(command);
                commandBuffer.flip();
                channel.write(commandBuffer);
                break;
        }
    }
}
