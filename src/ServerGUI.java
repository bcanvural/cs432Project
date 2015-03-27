
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ServerGUI  {

    /**
     * Components
     */
    JFrame frame;
    JLabel portLabel;
    JTextField portText;
    JButton startButton;
    JPanel logPanel;
    JTextArea logTextArea;
    JScrollPane scroll;

    ServerViewController svc;


    public ServerGUI(ServerViewController svc) {

        this.svc = svc;

        frame = new JFrame("Server");
        frame.setSize(500, 500);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        frame.add(panel);

        placeComponents(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void placeComponents(JPanel panel) {

        panel.setLayout(null);

        portLabel = new JLabel("Port:");
        portLabel.setBounds(40, 70, 80, 25);
        panel.add(portLabel);

        portText = new JTextField(20);
        portText.setText("1024"); //default value
        portText.setBounds(100, 70, 160, 25);
        panel.add(portText);


        startButton = new JButton("Start Server");
        startButton.setBounds(160, 110, 100, 25);
        startButton.addActionListener(this.svc);
        panel.add(startButton);

        //LOG
        logPanel = new JPanel ();
        logPanel.setBorder (new TitledBorder(new EtchedBorder(), "Log"));

        logTextArea = new JTextArea(10,30);
        logTextArea.setText("");
        logTextArea.setEditable ( false ); // set textArea non-editable
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);
        scroll = new JScrollPane ( logTextArea );
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        scroll.setVisible(true);

        //Add Textarea in to log panel
        logPanel.add(scroll);
        logPanel.setBounds(10,170,400,200);
        panel.add ( logPanel );
    }

    public void displayAlertMessage(String message){
        JOptionPane.showMessageDialog(this.frame, message);
    }

    public int getPortNum(){
        return Integer.parseInt(this.portText.getText());
    }

    public void addLogMessage(String message){
        this.logTextArea.append(message + '\n');
    }

    public void clearLog(){
        this.logTextArea.setText("");
    }
}