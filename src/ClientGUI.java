
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ClientGUI  {

    /**
     * Components
     */
    JFrame frame;
    JLabel ipLabel;
    JTextField ipText;
    JLabel portLabel;
    JTextField portText;
    JLabel userLabel;
    JTextField userText ;
    JLabel passwordLabel;
    JPasswordField passwordText;
    JButton connectButton;
    JButton browseButton;
    JPanel logPanel;
    JTextArea logTextArea;
    JScrollPane scroll;

    ClientViewController cvc; //Controller of this view
    private String serverPassFilePath = null;
    private String clientPublicPassFilePath = null;
    private String clientPrivatePassFilePath = null;
    boolean serverKeyChosen = false;
    boolean clientPublicKeyChosen = false;
    boolean clientPrivateKeyChosen = false;

    public ClientGUI(ClientViewController cvc) {

        this.cvc = cvc;

        frame = new JFrame("Client");
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

        ipLabel = new JLabel("Server Ip:");
        ipLabel.setBounds(10, 10, 80, 25);
        panel.add(ipLabel);

        ipText = new JTextField(20);
        ipText.setText("localhost"); // default value
        ipText.setBounds(100, 10, 160, 25);
        panel.add(ipText);

        portLabel = new JLabel("Port:");
        portLabel.setBounds(10, 40, 80, 25);
        panel.add(portLabel);

        portText = new JTextField(20);
        portText.setText("1024");
        portText.setBounds(100, 40, 160, 25);
        panel.add(portText);

        userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 70, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 70, 160, 25);
        panel.add(userText);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 100, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 100, 160, 25);
        panel.add(passwordText);

        connectButton = new JButton("Connect");
        connectButton.setBounds(180, 140, 80, 25);
        connectButton.addActionListener(this.cvc);
        panel.add(connectButton);

        browseButton = new JButton("Browse");
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser inputFilePath = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
                inputFilePath.setFileFilter(filter);
                int reply = inputFilePath.showOpenDialog(frame);
                if(reply == inputFilePath.APPROVE_OPTION){
                    if (!serverKeyChosen){
                        serverPassFilePath = inputFilePath.getSelectedFile().getAbsolutePath();
                        System.out.println(serverPassFilePath);
                        serverKeyChosen = true;
                    }
                    else if (!clientPublicKeyChosen) {
                        clientPublicPassFilePath = inputFilePath.getSelectedFile().getAbsolutePath();
                        System.out.println(clientPublicPassFilePath);
                        clientPublicKeyChosen = true;
                    } else if (!clientPrivateKeyChosen){
                        clientPrivatePassFilePath = inputFilePath.getSelectedFile().getAbsolutePath();
                        System.out.println(clientPrivatePassFilePath);
                        clientPrivateKeyChosen = true;
                    }
                    //checkButton();
                }
            }

            private void checkButton() {
                if (serverKeyChosen && clientPublicKeyChosen && clientPrivateKeyChosen) {
                    connectButton.setEnabled(true);
                    browseButton.setEnabled(false);
                }
            }
        });

        browseButton.setBounds(90, 140, 80, 25);
        panel.add(browseButton);

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

        //Add Textarea in to middle panel
        logPanel.add(scroll);
        logPanel.setBounds(10,170,400,200);
        panel.add ( logPanel );
    }

    public String getIp(){
        return this.ipText.getText();
    }

    public int getPortNum(){
        return Integer.parseInt(this.portText.getText());
    }

    public String getUserName(){
        return this.userText.getText();
    }

    public char[] getPassword(){
        return this.passwordText.getPassword();
    }

    public String getServerPassFilePath() {
        return serverPassFilePath;
    }

    public String getClientPublicPassFilePath() {
        return clientPublicPassFilePath;
    }

    public String getClientPrivatePassFilePath() {
        return clientPrivatePassFilePath;
    }

    public void displayAlertMessage(String message){
        JOptionPane.showMessageDialog(this.frame, message);
    }

    public void addLogMessage(String message){
        this.logTextArea.append(message);
    }

    public void clearLog(){
        this.logTextArea.setText("");
    }
}