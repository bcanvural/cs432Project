import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

    private Socket myS;
    private String formOfMeasurement = "";
    private boolean echo = true;        //global variables

    private ServerViewController svc;

    ServerThread(ServerViewController svc,Socket s){
        this.svc = svc;
        myS = s;
    }

    public void run(){
        int numberOfProbes=0;


        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(myS.getInputStream()));
            this.svc.getServerGUI().addLogMessage("A client has connected");

            //Create Printwriter object

            PrintWriter out = new PrintWriter(myS.getOutputStream(),true);

            out.println("200:OK");


            while(true){

                String msg = in.readLine();
                if(msg==null){      //if client quits, we close the respective socket
                    System.out.println("Server terminates connection with a client");
                    myS.close();
                    break;
                }
                if(msg.equals("bye")){  //termination condition
                    System.out.println("Connection terminated with a client");

                    myS.close();

                    break;
                }

                if(echo){
                    System.out.println(msg);
                    out.println(msg);
                }
                String[]tokens = msg.split(" ");
                if(tokens.length==5){
                    if(tokens[0].equals("s") && tokens[1].equals("rtt")|| tokens[1].equals("tput")){
                        if(tokens[1].equals("tput")){
                            formOfMeasurement = "tput";
                        }
                        else
                            formOfMeasurement = "rtt";
                        try{
                            Integer.parseInt(tokens[2]);
                            Integer.parseInt(tokens[3]);
                            Integer.parseInt(tokens[4]);   //format checks
                            numberOfProbes = Integer.parseInt(tokens[2]);
                            out.println("200:OK READY");
                            if(formOfMeasurement.equals("rtt") || formOfMeasurement.equals("tput")){
                                out.println(formOfMeasurement+" "+tokens[2]+" "+tokens[3]+" "+tokens[4]); //we send back the validated data
                            }
                        }catch(NumberFormatException e){
                            out.println("404 ERROR: Invalid Connection Setup");
                        }
                    }
                    echo = false;
                }
                else if(tokens.length==3){
                    if(tokens[0].equals("m")){
                        try{

                            if(Integer.parseInt(tokens[1])>numberOfProbes){
                                out.println("probe number error");
                            }
                            else
                            {
                                out.println("OK");
                                out.println(tokens[1]);
                                out.println(tokens[2]);
                                int i = Integer.parseInt(tokens[1]);
                                while(i<numberOfProbes){
                                    String temp = in.readLine();
                                    out.println(temp);

                                    i++;
                                }
                            }
                        }catch(NumberFormatException n){
                            out.println("404 ERROR: Invalid Measurement Message");
                        }
                    }
                    echo = false;
                }
                else if(tokens.length==1){
                    if(tokens[0].equals("t")){
                        out.println("200 OK: Connection Closed");
                        myS.close();

                    }

                }

            }myS.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
