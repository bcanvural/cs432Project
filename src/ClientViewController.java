import sun.nio.cs.StandardCharsets;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientViewController extends Thread implements ActionListener {
    ClientGUI clientGUI;
    Client client;
    int filenameCounter = 0;

    public static void main(String [] args){
        byte[] arr = Crypto.SHA256("fdsa");
        byte[] key = Arrays.copyOfRange(arr, 0, 16);
        byte [] IV = Arrays.copyOfRange(arr,16,32);
        System.out.println();
    }

    public ClientViewController(){

    }

    public ClientGUI getClientGUI() {
        return clientGUI;
    }

    public void run(){
        clientGUI = new ClientGUI(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (actionEvent.getSource() == clientGUI.connectButton){

            String ip = clientGUI.getIp();
            int portNum = clientGUI.getPortNum();
            String username = clientGUI.getUserName();
            File enrollmentFile = checkEnrollment(username);

            if (enrollmentFile==null){ //create enrollment
                if (!this.clientGUI.serverKeyChosen){
                    this.clientGUI.displayAlertMessage("You haven't chosen your server key file");
                    return;
                } else if (!this.clientGUI.clientPublicKeyChosen){
                    this.clientGUI.displayAlertMessage("You haven't chosen your client public key file");
                    return;
                } else if (!this.clientGUI.clientPrivateKeyChosen){
                    this.clientGUI.displayAlertMessage("You haven't chosen your client private key file");
                    return;
                }
                try {
                    String filePath = this.clientGUI.getClientPrivatePassFilePath();
                    byte [] passwordDigest = Crypto.SHA256(String.valueOf(clientGUI.getPassword()));
                    String encrypted = new String(
                            Crypto.encrypt(FileOps.readFile(filePath),getKey(passwordDigest),getIV(passwordDigest))
                    );
                    FileOps.writeFile(String.valueOf(++filenameCounter)+".txt",
                            new String(Crypto.SHA256(username))+encrypted);
                    enrollmentFile = new File(String.valueOf(filenameCounter)+".txt");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                    //System.out.println("Crypto exception");
                }
            }

            assert (enrollmentFile!=null);
            //at this point there is definitely an enrollment

            String password = String.valueOf(clientGUI.getPassword());



            client = new Client(this,ip,portNum);
            client.start();

        }
    }

    public File checkEnrollment(String username){
        File [] enrollments = FileOps.findTxtFiles(System.getProperty("user.dir")); //get the current working directory
        for(File file : enrollments){
            String content = FileOps.readFile(file.getAbsolutePath());
            if (new String(Crypto.SHA256(content.substring(0, 256))) == new String(Crypto.SHA256(username))){ //enrolled before?
                return file;
            }
        }
        return null; //if there are no enrollments or no text files return null
    }

    private String getKey(byte[] digest){
        try {
            byte [] key = Arrays.copyOfRange(digest, 0, 16);
            return new String(Arrays.copyOfRange(digest, 0, 16),"ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String getIV(byte[] digest){
        try {
            return new String(Arrays.copyOfRange(digest, 16, 32),"ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}