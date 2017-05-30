import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Klasa kryesore e serverit 
 */
public class ServerMain {
    //Frame (korniza) kryesore e serverit
    private JFrame frame = new JFrame();
   //JDesktopPane paraqet containerin kryesor qe do te permbaje te gjithe ekranet e klienteve te lidhur
    private JDesktopPane desktop = new JDesktopPane();

  //GUI Vizaton GUI-n e serverit
    
    public void drawGUI(){
            frame.add(desktop,BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //E shfaq kornizen te maksimizuar
            frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
    }
    
    public void init(int port){		//metoda inicializuese

        try {
            ServerSocket serversocket = new ServerSocket(port);
            //Shfaq GUI-n e serverit
            drawGUI();
            //E merr portin e serverit dhe pranon lidhjet
            while(true){
                Socket client = serversocket.accept();
                System.out.println("Klient i ri eshte kyçur ne server");
                //Per secilin klient krijon nje klase menaxhuese (ManageClient) 
                new ManageClient(client,desktop);
                
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    
    
    public static void main(String args[]){
        String port = JOptionPane.showInputDialog("Jepni portin: ");
        new ServerMain().init(Integer.parseInt(port));
    }
}
