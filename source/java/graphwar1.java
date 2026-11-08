package GraphServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Connection
{
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	
	private long lastReceivedTime;
	private long lastSentTime;
	
	public Connection(String ip, int port) throws IOException
	{		
		SocketAddress sockaddr = new InetSocketAddress(ip, port);
		socket = new Socket();
		
		socket.connect(sockaddr, Constants.TIMEOUT_CONNECTING);
		
		socket.setSoTimeout(Constants.TIMEOUT_KEEPALIVE);
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));		
		
		lastReceivedTime = System.currentTimeMillis();
		lastSentTime = System.currentTimeMillis();
				
	}
		
	public Connection(Socket socket) throws IOException
	{
		this.socket = socket;
		
		socket.setSoTimeout(Constants.TIMEOUT_KEEPALIVE);
		
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));	
	}
}