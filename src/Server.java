
import java.net.*;
import java.io.*;

public class Server extends Thread {

    private int portNum;
    private ServerViewController svc;

    public Server(ServerViewController svc,int portNum){
        this.svc = svc;
        this.portNum = portNum;
    }

    public void run(){
        try {
            ServerSocket serverSocket = new ServerSocket(this.portNum);
            while(true){
                Socket clientSocket = serverSocket.accept();    // if accepted, then create a thread
                new ServerThread(this.svc,clientSocket).start();  //create new server thread
            }
        } catch (IOException e){
            this.svc.getServerGUI().addLogMessage(e.toString());
        }
    }

    public static void main(String[] args) throws IOException,NumberFormatException {



//        try{
//            if(args.length<1){
//                System.out.println("Port number not specified");
//                System.exit(1);
//            }
//            if(Integer.parseInt(args[0])<1024){
//                System.out.println("Port number has to be bigger than 1023");
//                System.exit(1);
//            }
//        }catch(NumberFormatException e){
//            System.out.println("Invalid format for inputs, terminating..");
//            System.exit(1);
//        }
//        System.out.println("Server established, awaiting connections..");
//
//        portNum = Integer.parseInt(args[0]);
//
//        ServerSocket serverSocket = new ServerSocket(portNum);
//
//        while(true){
//            Socket clientSocket = serverSocket.accept();    // if accepted, then create a thread
//            new ServerThread(clientSocket).start();  //create new server thread
//        }
    }




}
