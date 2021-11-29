//package redes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import javax.sound.sampled.SourceDataLine;

   	public class Server {

		public static final int PORTA = 4000;
		private ServerSocket serverSocket;
		//private BufferedReader in;

		public void start() throws IOException {
			serverSocket = new ServerSocket(PORTA);
			System.out.println("Servidor iniciado na porta " + PORTA);
			loopDeConexao();
		}

		private void loopDeConexao() throws IOException {
			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Cliente " + ClientSocket.getRemoteSocketAddress());
				BufferedReader in = new BufferedReader(InputStreamReader(clientSocket.getInputStream()));
				String mensagem = in.readLine();
				System.out.println("Mensagem recebida do cliente = " + clientSocket.getRemoteSocketAddress() + ": " + mensagem); 
				//new Thread(() -> loopDeMensagemCliente(clietSocket) ).start();
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
			} catch (IOException ex) {
				System.out.println("Servidor não iniciado por erro " + ex.getMessage());
			}

			//System.out.println("Servidor finalizado");

		}

	}


