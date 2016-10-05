package com.Kerstin.reddit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CardClient {
	// #268 Network and Cards
	private final String hostName;
	private final int port;

	// prototype not working
	public static void main(String[] args) {
		new CardClient("192.168.0.11", 4444);
	}

	CardClient(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
		try (Socket clientSocket = new Socket(this.hostName, this.port);
				PrintWriter out = new PrintWriter(System.out);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
			String inputline;
			while ((inputline = in.readLine()) != null) {
				out.println(inputline);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}