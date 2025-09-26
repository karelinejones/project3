import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;

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
                    commandBuffer = ByteBuffer.allocate(20);
                    break;
                case "DELETE":
                    break;
                case "RENAME":
                    break;
                case "DOWNLOAD":
                    break;
                case "UPLOAD":
                    break;
                case "QUIT":
                    break;
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
