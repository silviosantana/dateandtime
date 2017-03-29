import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DateAndTimeClientTCP {
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		//open Socket
		Socket clientSocket = new Socket("localhost", 3000);
		
		//Create I/O streams
		ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
		ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

		String response = null;
		String request = "time(hour)";
		
		//send request to server
		outToServer.writeObject(request);
		
		//receive response from server
		response = (String) inFromServer.readObject();
		
		System.out.println("Response = " + response);

		// close sockets and streams
		clientSocket.close();
		outToServer.close();
		inFromServer.close();
	}
}
