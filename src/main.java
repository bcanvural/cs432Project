
public class main {
    public static void main(String args[]){
        ServerViewController serverViewController = new ServerViewController();
        serverViewController.start();
        ClientViewController clientViewController = new ClientViewController();
        clientViewController.start();
    }
}
