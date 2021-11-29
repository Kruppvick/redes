import java.io.IOException;
import java.net.ServerSocket;

public class TcpServer {

		private final int PORT = 4000;
		private ServerSocket serverSocket;

		public void start() throws IOException {
			serverSocket = new ServerSocket(PORT);
		}

    public static void main(String[] args) {

			try {
				TcpServer server = new TcpServer();
				server.start();
			} catch (IOException ex) {
				System.out.println("Servidor não iniciado por erro " + ex.getMessage());
			}
	}
}