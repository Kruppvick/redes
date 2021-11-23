package redes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
   	public class Server {

		public static int PORTA = 4000;
		private ServerSocket serverSocket;
		private BufferedReader in;

		public void start() throws IOException {
			serverSocket = new ServerSocket(PORTA);
			System.out.println("Servidor iniciado na porta " + PORTA);
			loopDeConexao();

		}

		private void loopDeConexao() throws IOException {
			while (true) {

				ClientSocket clietSocket = new  ClientSocket(serverSocket.accept());

				new Thread(() -> loopDeMensagemCliente(clietSocket) ).start();
 			}
		}

		public void loopDeMensagemCliente(ClientSocket clientSocket) {
			String mensagem;
			try {
				while ((mensagem = clientSocket.recebeMensagem()) !=null) {
					if("sair".equalsIgnoreCase(mensagem))
						return;
					System.out.printf("Mensagem recebida do cliente %s: %s\n", clientSocket.getRemoteSocketAddress(), mensagem);
				}

			}finally {
				clientSocket.fechar();
		}

	}


		public static void main(String[] args) {

			try {
				Server server = new Server();
				server.start();
			} catch (IOException e) {
				System.out.println("Servidor não iniciado por erro ");
			}

			System.out.println("Servidor finalizado");

		}

	}


