import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class ManageClient extends Thread {

    private JDesktopPane desktopPane = null;
    private Socket cSocket = null;
    private JInternalFrame interFrame = new JInternalFrame("Ekrani i Klientit", true, true, true);
    private JPanel cPanel = new JPanel();
    
    public ManageClient(Socket cSocket, JDesktopPane desktop) {
        this.cSocket = cSocket;
        this.desktopPane = desktop;
        start();
    }

    //Vizaton GUI per secilin klient te lidhur
    public void drawGUI(){
        interFrame.setLayout(new BorderLayout());
        interFrame.getContentPane().add(cPanel,BorderLayout.CENTER);
        interFrame.setSize(100,100);
        desktopPane.add(interFrame);
        try {
            //shfaq kornizen e maximizuar
            interFrame.setMaximum(true);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }
        
        cPanel.setFocusable(true);
        interFrame.setVisible(true);
    }

    public void run(){

        //prezenton madhesine e ekranit te klientit
        Rectangle clientScreenDim = null;
        //perdoret per leximin e screenshotave dhe dimensionin e ekranit te klientit
        ObjectInputStream ois = null;
        //fillon vizatimin e GUI-t
        drawGUI();

        try{
            //lexon dimensionet e ekranit te klientit
            ois = new ObjectInputStream(cSocket.getInputStream());
            clientScreenDim =(Rectangle) ois.readObject();
        }catch(IOException ex){
            ex.printStackTrace();
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        //pranon screenshota
        new ReceiveScreen(ois,cPanel);
        //i dergon evente klientit
        new SendCommands(cSocket,cPanel,clientScreenDim);
    }

}
