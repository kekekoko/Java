package com.Kerstin.reddit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CardServer{
	//#268 Network and Cards
	String hostName;
	int port;
	
	public static void main(String[] args){
		new CardServer("192.168.0.11", 4444);
	}
	
	CardServer(String hostName, int port){
		this.hostName = hostName;
		this.port = port;
		
		try(ServerSocket serverSocket = new ServerSocket(this.port)){
			while (true){
				new Thread(new ClientThread(serverSocket.accept())).start();
				System.out.println("new thread created");
			}
			
//			System.out.println("new thread created");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try (
//			ServerSocket serverSocket = new ServerSocket(this.port);
//			Socket clientSocket = serverSocket.accept();
//		    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
////		    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//		){
//			while (true){
//				out.println("there ");
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	class ClientThread implements Runnable{
		Socket clientSocket;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try (
				    PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
//				    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				){
					while (true){
						out.println("there");
//						Thread.sleep(3000);
//						out.println("bla");
//						
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		ClientThread(Socket clientSocket){
			this.clientSocket = clientSocket;
			this.run();
		}
		
	}
}