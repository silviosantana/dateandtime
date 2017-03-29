import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DateAndTimeServerTwoUDP {

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {

		// create sockets
		DatagramSocket serverSocket = new DatagramSocket(1313);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		
		//create streams
		DatagramPacket receivePacket = null;
		String reqMsg = null;
		InetAddress IPAddress = null;
		int port;

		//Aplicacao desejada
		DateAndTimeImpl dAndT = new DateAndTimeImpl();
		String result = null;
		boolean processing = true;

		System.out.println("UDP server ready...");
		
		while (processing) {

			// create socket
			receivePacket = new DatagramPacket(receiveData,
					receiveData.length);

			// receive request from client
			serverSocket.receive(receivePacket);
			reqMsg = new String(receivePacket.getData());
			IPAddress = receivePacket.getAddress();
			port = receivePacket.getPort();

			// process request
		    result = dAndT.date(reqMsg);

			// send response to client
			//String response = Float.toString(result);
			sendData = result.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
		}
		serverSocket.close();
	}
}