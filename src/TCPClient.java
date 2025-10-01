import javax.swing.*;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        System.out.println("");
    }

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
                break;
            case 'Delete':

                break;
            case 'Rename':

                }
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
                break;
                    case 'Quit':

                        break;

        }
    }
}
