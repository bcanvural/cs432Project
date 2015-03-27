import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client extends Thread{   // CLIENT

    String ip;
    int portNum;
    ClientViewController cvc;
    public Client(ClientViewController cvc,String ip,int portNum){
        this.cvc = cvc;
        this.ip = ip;
        this.portNum = portNum;
    }

    public void run(){
        try {
            Socket clientSocket = new Socket(ip, portNum);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while(true){
                this.cvc.getClientGUI().addLogMessage(in.readLine());
            }
        } catch (IOException e){
            this.cvc.getClientGUI().displayAlertMessage(e.toString());
        }
    }

//    public static void main (String[] args) throws UnknownHostException,IOException {
//
//        if(args.length<1){
//            System.out.println("Machine name or Ip address not specified ");
//            System.exit(1);
//        }
//        if(args.length<2){
//            System.out.println("Port number not specified");
//            System.exit(1);
//        }
//
//        try{
//            Socket clientSocket = new Socket(args[0],Integer.parseInt(args[1]));
//
//            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
//            Scanner in = new Scanner(System.in);
//            new ClientThread(clientSocket).start();
//
//
//            while(true){
//
//                out.println(in.nextLine());
//
//            }
//        }
//        catch(UnknownHostException e){
//            e.printStackTrace();
//        }
//        catch(NumberFormatException e){
//            e.printStackTrace();
//        }
//
//
//    }
}
		