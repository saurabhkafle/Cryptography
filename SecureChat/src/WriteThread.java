 
import java.io.*;
import java.net.*;
import java.security.NoSuchAlgorithmException;
 
/**
 * This thread is responsible for reading user's input and send it
 * to the server.
 * It runs in an infinite loop until the user types 'bye' to quit.
 *
 * @author fozdemir
 */
public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;
    private Console console;
 
    public WriteThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;
 
        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public void run() {
 
        console = System.console();
        
        console.printf("\n Secure Chat Application By Saurabh \r\n");
        
        String userName = console.readLine("\nEnter your name: ");
        
        console.printf("\nWELCOME "+ userName+"\n");
        writeListOfCommands();
        
 
        client.setUserName(userName);
		
        
        writer.println(userName);
        
        String text;
 
        do {
			text = console.readLine("[" + userName + "]: ");
			if(text.equals("/commands"))
		        writeListOfCommands();
			
			else
			{
				writer.println(text);
			}          
			
        } while (!text.equals("/bye"));
 
        try {
            socket.close();
        } catch (IOException ex) {
 
            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
    
    public void writeListOfCommands()
    {
    	console.printf("\ntype /commands for see cammand list \n");
    	console.printf("type /list for see all online user \n");
    	console.printf("type /select <username> for start to chat with user in complite privacy \n");
    	console.printf("type /bye to leave the program \n\n");

    }
}
