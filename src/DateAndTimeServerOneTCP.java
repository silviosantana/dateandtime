import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class ThreadProcessRequest implements Runnable {
	private Socket connectionSocket = null;

	public ThreadProcessRequest(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
	}

	public void run() {
		ObjectOutputStream outToClient = null;
		ObjectInputStream inFromClient = null;
		
		String request = null;
		String response = null;
		
		String opName = null;		
		DateAndTimeImpl dAndt = new DateAndTimeImpl();
		String p1 = null;
		int result = 0;

		//Create I/O streams
		try {
			outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
			inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
		} catch (IOException e1) {
			return;
		}
		
		//wait for client request
		try {
			request = (String) inFromClient.readObject();
		} catch (ClassNotFoundException | IOException e1) {
			return;
		}
		
		// process request
		opName = request.substring(0, request.indexOf("("));
		p1 = request.substring(request.indexOf("(") + 1, request.indexOf(")"));
		
		result = dAndt.time(p1);
		
		// send response to client
		response = Integer.toString(result);
		try {
			outToClient.writeObject(response);
			outToClient.flush();
		} catch (IOException e) {

		}

	}
}

public class DateAndTimeServerOneTCP {
	
	public static void main(String[] args) throws IOException {

		boolean processing = true;
		
		// create sockets and streams
		ServerSocket welcomeSocket = null;
		Socket connectionSocket = null;

		System.out.println("Date and Time Server is ready...");
		welcomeSocket = new ServerSocket(3000);

		while (processing) {
			connectionSocket = welcomeSocket.accept();
			(new Thread(new ThreadProcessRequest(connectionSocket))).start();
		}
		
		welcomeSocket.close();
	}
}
