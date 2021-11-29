package redes;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

	private static final String SERVER_ADDRESS = "127.0.0.1";
	private ClientSocket clientSocket;
	private Scanner scanner;
	private PrintWriter out;

	public static void main(String[] args) {

		try {
			Client client = new Client();
			client.start();
		} catch (IOException e) {
			System.out.println("CLiente não iniciado");
		}

		//System.out.println("Cliente finalizado");
	}


	  public Client() {

	  	scanner = new Scanner(System.in);
	  }

	  public void start() throws IOException {
	  	Socket socket = new Socket(SERVER_ADDRESS, Server.PORTA);
	  	clientSocket = new ClientSocket(socket);


		  System.out.println("Cliente conectado no servidor " + SERVER_ADDRESS + " na porta: " + Server.PORTA);

		  new Thread((Runnable) this).start();
		  loopDeMensagem();

	  }

	  private void loopDeMensagem() throws IOException {
	  		String mensagem;
	  		do {
				System.out.print("Digite sua mensagem, ou escreva |sair| para finalizar: ");
						mensagem = scanner.nextLine();
						clientSocket.enviaMensagem(mensagem);
			} while (!"sair".equalsIgnoreCase(mensagem));
	  		clientSocket.fechar();
	  }
	  @Override
	public void run() {
		String mensagem;
		while ((mensagem = clientSocket.recebeMensagem())!=null) {
			System.out.println("Servidor diz " + mensagem);
		}
	  }


}
