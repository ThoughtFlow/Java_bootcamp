package lab10;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ShellServer {

	private static void runShell(String rootDir, String portString) throws IOException
	{
		int port = Integer.parseInt(portString);
		try (ServerSocket serverSocket = new ServerSocket(port);) {
			LocalShell shell = new LocalShell();

			while (true) {
				try (Socket clientSocket = serverSocket.accept();) {
					try (InputStream in = clientSocket.getInputStream();
						 OutputStream out = clientSocket.getOutputStream()) {
						
                	   shell.runShell(rootDir, in, out);
					}
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		if (args.length != 2)
		{
			System.err.println("Usage: rootDir port");
		}
		else
		{
		   try {
			   runShell(args[0], args[1]);
		   } catch (IOException e) {
    			e.printStackTrace();
		   }
		}
	}
}