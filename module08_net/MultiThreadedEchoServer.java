package module08_net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedEchoServer implements Runnable {
	
    public static void main(String[] args) throws IOException, InterruptedException {
        MultiThreadedEchoServer server = new MultiThreadedEchoServer(Integer.parseInt(args[0]));
        Thread t = new Thread(server);
        t.start();
        t.join();
        server.shutdown();
    }

    private final int port;
    private int count = 0;

    private volatile boolean runFlag = true;

    public MultiThreadedEchoServer(int port) {
        this.port = port;
    }

    public void shutdown() {
        this.runFlag = false;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            System.out.println("Listening on " + port);
            System.out.println("(Control-C to terminate)");
            while (this.runFlag) {
                Socket clientSocket = serverSocket.accept();
                Handler handler = new Handler(clientSocket, ++count);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Handler implements Runnable {
        private final Socket socket;
        private final Integer id;

        public Handler(Socket socket, Integer id) {
            this.socket = socket;
            this.id = id;
        }

        public void run() {
            try {
                try (InputStream in = socket.getInputStream();
                        OutputStream out = socket.getOutputStream();) {
                	String greeting = "Hello: " + id;
                	out.write(greeting.getBytes());
                	out.flush();
                	
                    int b;
                    while ((b = in.read()) != -1) {
                        out.write(b);
                    }
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    this.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}