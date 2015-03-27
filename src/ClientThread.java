import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ClientThread extends Thread{       // * CLIENT THREAD *

    private Socket myS;
    ClientThread(Socket s){
        myS = s;
    }
    public void run(){
        System.out.println("Connected");

        String msg="";
        String formOfMeasurement="";
        int numberOfProbes=0;
        int msgSize=0;
        int serverDelay=0;
        long time = 0;
        String payLoad="";
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    myS.getInputStream()));
            PrintWriter out = new PrintWriter(myS.getOutputStream(),true);    //in and out connections

            while(true){
                msg = in.readLine();
                if(msg==null){      //if one side terminates connection, we terminate too
                    System.out.println("Server closing connection...");
                    myS.close();
                    System.exit(1);
                }
                if(!msg.equals("200:OK READY"))
                    System.out.println("Server echoes back: "+msg);

                if(msg.equals("200:OK READY")){
                    System.out.println("200:OK READY");
                    String temp = in.readLine();
                    String[]kReady = temp.split(" ");
                    formOfMeasurement = kReady[0];
                    numberOfProbes = Integer.parseInt(kReady[1]);
                    msgSize = Integer.parseInt(kReady[2]);
                    serverDelay = Integer.parseInt(kReady[3]);

                    out.println("m 1 a"); //assuming payLoad = a;

                }
                if(msg.equals("OK")){
                    int i = Integer.parseInt(in.readLine());
                    payLoad = in.readLine();
                    if(formOfMeasurement.equals("rtt")){
                        System.out.println("Calculating...");
                        while(i<numberOfProbes){

                            String probePacket = formPacket(msgSize,payLoad);
                            long start = System.currentTimeMillis();
                            //System.out.println(start);
                            out.println(probePacket);
                            String temp = in.readLine();
                            long end = System.currentTimeMillis();

                            time=time+end-start+Long.valueOf(String.valueOf(serverDelay)); //calculation of time

                            i++;
                            if(i==numberOfProbes){ //if this is the end

                                NumberFormat format = new DecimalFormat("#.#########"); //the format we display our values in

                                System.out.println("Average rtt is: "+format.format(Double.parseDouble(String.valueOf(time)) / Double.valueOf(numberOfProbes)));

                                myS.close();
                                System.out.println("Client leaves the connection...");
                                System.exit(1);

                            }
                        }
                    }
                    if(formOfMeasurement.equals("tput")){
                        System.out.println("Calculating...");
                        while(i<numberOfProbes){
                            String probePacket = formPacket(msgSize,payLoad);
                            long start = System.currentTimeMillis();
                            out.println(probePacket);
                            String temp1 = in.readLine();
                            long end = System.currentTimeMillis();
                            time=time+end-start+Long.valueOf(String.valueOf(serverDelay)); //calculation of time

                            i++;
                            if(i==numberOfProbes){ //if this is the end

                                NumberFormat format = new DecimalFormat("#.#########");

                                System.out.println("Average tput is: "+format.format((Double.parseDouble(String.valueOf(msgSize))) / (Double.parseDouble(String.valueOf(time)) / Double.valueOf(numberOfProbes))));
                            }
                        }

                    }

                }

            }

        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NullPointerException e){
            try {
                myS.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
    private String formPacket(int mSize,String payLoad){ //forming the packet to send in each prbobe
        String toBeSent="";
        for(int i=0;i<mSize/payLoad.length();i++){  //append until message size is reached
            toBeSent+=payLoad;
        }
        if(payLoad.length()>mSize){
            toBeSent=toBeSent.substring(0,mSize+1);  //truncate  if payload is bigger than the message size
        }
        return toBeSent;

    }
}
