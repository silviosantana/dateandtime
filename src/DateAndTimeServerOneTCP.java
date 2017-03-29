import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

class ThreadProcessRequest implements Runnable {
	private Socket connectionSocket = null;

	public ThreadProcessRequest(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
	}
	
	private String sendRequestToServer2 (String p1) throws UnknownHostException, IOException, ClassNotFoundException{
		byte[] reqMsg = new byte[1024];
		byte[] repMsg = new byte[1024];
		// create socket
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		DatagramPacket sendPacket = null;
		DatagramPacket receivePacket = null;
		String response = null;
		
		reqMsg = p1.getBytes();
		sendPacket = new DatagramPacket(reqMsg, reqMsg.length, IPAddress, 1313);
		clientSocket.send(sendPacket);

			// receive response from server
		receivePacket = new DatagramPacket(repMsg, repMsg.length);
		clientSocket.receive(receivePacket);
		response = new String(receivePacket.getData());
		clientSocket.close();
		
		return response;
	}

	public void run() {
		ObjectOutputStream outToClient = null;
		ObjectInputStream inFromClient = null;
		
		String request = null;
		String response = null;
		
		String opName = null;		
		DateAndTimeImpl dAndt = new DateAndTimeImpl();
		String p1 = null;
		String result = null;
		boolean processing = true;

		//Create I/O streams
		try {
			outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
			inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
		} catch (IOException e1) {
			return;
		}
		
		while (processing){
			//wait for client request
			try {
				request = (String) inFromClient.readObject();
			} catch (ClassNotFoundException | IOException e1) {
				return;
			}
			
			// process request
			opName = request.substring(0, request.indexOf("(")).trim();
			p1 = request.substring(request.indexOf("(") + 1, request.indexOf(")"));
			if (opName.equals("time")){
				result = Integer.toString(dAndt.time(p1));
			}else{
				try {
					result = sendRequestToServer2(p1);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			// send response to client
			response = result;
			try {
				outToClient.writeObject(response);
				outToClient.flush();
			} catch (IOException e) {

			}
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
