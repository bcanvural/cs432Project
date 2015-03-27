import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerViewController extends Thread implements ActionListener{
    ServerGUI serverGUI;
    Server server;

    public ServerViewController(){

    }

    public ServerGUI getServerGUI() {
        return serverGUI;
    }

    public void run(){
        serverGUI = new ServerGUI(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == serverGUI.startButton){
            //serverGUI.startButton.setEnabled(false);
            int portNum = serverGUI.getPortNum();
            //TODO validation
            this.server = new Server(this,portNum);
            server.start();
            serverGUI.addLogMessage("Server started listening on port:"+portNum);
        }
    }
}
