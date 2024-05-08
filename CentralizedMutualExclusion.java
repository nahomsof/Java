
import java.io.*;
import java.net.*;

class CentralizedMutualExclusionServer extends Thread {
    private ServerSocket serverSocket;
    private boolean[] processesDone;

    public CentralizedMutualExclusionServer(int port, int numProcesses) throws IOException {
        serverSocket = new ServerSocket(port);
        processesDone = new boolean[numProcesses];
    }

    public void run() {
        System.out.println("Centralized mutual exclusion server started...");
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void requestCriticalSection(int processId) {
        for (int i = 0; i < processesDone.length; i++) {
            processesDone[i] = false;
        }
        processesDone[processId] = true;
    }

    private synchronized void releaseCriticalSection(int processId) {
        processesDone[processId] = false;
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            clientSocket = socket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                String message = in.readLine();
                if (message.startsWith("REQUEST")) {
                    int processId = Integer.parseInt(message.split(" ")[1]);
                    requestCriticalSection(processId);
                    out.println("OK");
                } else if (message.startsWith("RELEASE")) {
                    int processId = Integer.parseInt(message.split(" ")[1]);
                    releaseCriticalSection(processId);
                    out.println("OK");
                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class CentralizedMutualExclusion {
    public static void main(String[] args) throws IOException {
        int portNumber = 5000;
        int numProcesses = 3; // Change this according to the number of processes
        CentralizedMutualExclusionServer server = new CentralizedMutualExclusionServer(portNumber, numProcesses);
        server.start();
    }
    
}

    

