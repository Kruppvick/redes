import java.io.IOException;
import java.net.ServerSocket;

/**
 * UNISINOS - FUNDAMENTOS DE REDES DE COMPUTADORES
 * Codigo Desenvolvido pelos Alunos: 
 * Felipe Lovison Tedesco, Icaro de Menezes Landgraf, 
 * Rafael Modesto dos Santos e Victoria Regina Pereira Raupp
 */

   	public class TcpServer {

		private final int PORT = 4000;
		private ServerSocket serverSocket;

		public void start() throws IOException {
			System.out.println("Servidor iniciado na porta " + PORT);
			serverSocket = new ServerSocket(PORT);
		}


		public static void main(String[] args) {

			try {
				TcpServer server = new TcpServer();
				server.start();
			} catch (IOException ex) {
				System.out.println("Servidor não iniciado por erro " + ex.getMessage());
			}
			System.out.println("Servidor finalizado");
		}

	}