//package redes;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	  private static final String SERVER_ADDRESS = "127.0.0.1";
	  private Socket clientSocket;
	  private Scanner scanner;
	  private PrintWriter out;

	  public Client() {
		scanner = new Scanner(System.in);
	  }

	  public void start() throws IOException {
		clientSocket = new Socket(SERVER_ADDRESS, Server.PORTA);
		this.out = new BufferedWriter( new OutputStreamWriter(clientSocket.getOutputStream()));
		System.out.println("Cliente conectado no servidor " + SERVER_ADDRESS + " na porta: " + Server.PORTA);
		loopDeMensagem();
	  }

	  private void loopDeMensagem() throws IOException {
	  	String mensagem;
	  	do {
			System.out.print("Digite sua mensagem, ou escreva |sair| para finalizar: ");
			mensagem = scanner.nextLine();
			out.write(mensagem);
			out.newLine();
			} while (!mensagem.equalsIgnoreCase("sair"));

	  }

	  public static void main(String[] args) {

		  try {
			  Client client = new Client();
			  client.start();
		  } catch (IOException ex) {
			  System.out.println("Erro, CLiente não iniciado" + ex.getMessage());
		  }

		  System.out.println("Cliente finalizado");
	  }

}
