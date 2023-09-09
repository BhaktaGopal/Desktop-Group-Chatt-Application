package group_chatting_application;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements Runnable{
	static Socket socket;
	public static Vector client = new Vector();
	public Server(Socket socket) {
		try {
			this.socket = socket;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void run() {// function of multithreading
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			client.add(writer);
			
			while(true) {
				String data = reader.readLine().trim();
//				System.out.println("received"+ data);
				
				for(int i =0;i<client.size();i++) {
					try {
						BufferedWriter bw = (BufferedWriter)client.get(i);
						bw.write(data);
						bw.write("\r\n");
						bw.flush();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception{
		ServerSocket s = new ServerSocket(2003);
		while(true) {
			Socket socket = s.accept();
			Server server = new Server(socket);
			Thread thread = new Thread(server);

			thread.start();

		}
 
	}

}
