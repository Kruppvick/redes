package redes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Server {

		public static int PORTA = 4000;
		private ServerSocket serverSocket;
		private BufferedReader in;
		private final List<ClientSocket> clientSocketList;

		public Server() {
			clientSocketList = new LinkedList<>();
		}


		public static void main(String[] args) {
			final Server server = new Server();
			try {
				server.start();
			} catch (IOException e) {
			System.out.println("Servidor não iniciado por erro ");
			}

			System.out.println("Servidor finalizado");

		}



		public void start() throws IOException {
			serverSocket = new ServerSocket(PORTA);
			System.out.println("Servidor iniciado na porta " + serverSocket.getInetAddress().getHostAddress() + PORTA);
			loopDeConexao();

		}

		private void loopDeConexao() throws IOException {
			try {
				while (true) {
					System.out.println("Aguardando conexão de novo cliente");
					final ClientSocket clientSocket;
					try {
						clientSocket = new ClientSocket(serverSocket.accept());
						System.out.println("Cliente " + clientSocket.getRemoteSocketAddress() + "conectado");
					} catch (SocketException e) {
						System.err.println("Erro ao aceitar conexão do cliente. O servidor Possivelmente está sobrecarregado:");
						System.err.println(e.getMessage());
						continue;
					}
					try {
						new Thread(() -> loopDeMensagemCliente(clientSocket)).start();
						clientSocketList.add(clientSocket);
					} catch (OutOfMemoryError ex) {
						System.err.println("Não foi possível criar thread para novo cliente. O servidor possivelmente está" +
								" sobrecarregdo. Conexão será fechada: ");
						System.err.println(ex.getMessage());
						clientSocket.fechar();
					}
				}
			} finally {
				parar();
			}
		}

		public void loopDeMensagemCliente(ClientSocket clientSocket) {

			try {
				String mensagem;
				while ((mensagem = clientSocket.recebeMensagem()) != null) {
					System.out.println("Mensagem recebida do cliente " + clientSocket.getRemoteSocketAddress() + ": "
							+ mensagem);
					if ("sair".equalsIgnoreCase(mensagem)){
						return;

				}
				enviarMensagemParaTodos(clientSocket, mensagem);
			}


		} finally {
				clientSocket.fechar();
		}

	}
		private void enviarMensagemParaTodos (final ClientSocket remetente, final String mensagem) {
			final Iterator<ClientSocket> iterator = clientSocketList.iterator();
			int count = 0;

			while (iterator.hasNext()) {
				final ClientSocket client = iterator.next();

				if (!client.equals(remetente)) {
					if (client.enviaMensagem(mensagem))
						count++;
					else iterator.remove();
				}
			}
			System.out.println("Mensagem encaminhada para " + count + " clientes");
		}

		private void parar() {
			try {
				System.out.println("Finalizando o servidor");
				serverSocket.close();
			} catch (IOException e) {
				System.err.println("Erro ao fechar socket do servidor: " + e.getMessage());
			}
		}
}



