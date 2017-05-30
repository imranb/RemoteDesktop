import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

//Pranon screenshot nga klienti dhe i shfaq ato ne server
public class ReceiveScreen extends Thread {

    private ObjectInputStream objectInputStream = null;
    private JPanel cPanel = null;
    private boolean continueLoop = true;

    public ReceiveScreen(ObjectInputStream ois, JPanel p) {
        objectInputStream = ois;
        cPanel = p;
        //fillon thread-in (pasi cdo klient ka nje objekt te veqante te kesaj klase)
        start();
    }

    public void run(){
        
            try {
                
                //lexon screenshot-a te klientit dhe i vizaton ato
                while(continueLoop){
                    //pranon screenshot-a te klientit dhe i pershtat ne madhesine e panelit aktual
                    ImageIcon imageIcon = (ImageIcon) objectInputStream.readObject();
                    //System.out.println("New image recieved");
                    Image image = imageIcon.getImage();
                    image = image.getScaledInstance(cPanel.getWidth(),cPanel.getHeight() ,Image.SCALE_FAST);
                    //vizaton screenshot-in
                    Graphics graphics = cPanel.getGraphics();
                    graphics.drawImage(image, 0, 0, cPanel.getWidth(),cPanel.getHeight(),cPanel);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
          } catch(ClassNotFoundException ex){
              ex.printStackTrace();
          }
     }
}

//SendCommands implementon mouse events
class SendCommands implements KeyListener,
        MouseMotionListener,MouseListener {

    private Socket cSocket = null;
    private JPanel cPanel = null;
    private Rectangle clientScreenDim = null;

    SendCommands(Socket s, JPanel p, Rectangle r) {
        cSocket = s;
        cPanel = p;
        clientScreenDim = r;
        
        cPanel.addKeyListener(this);
        cPanel.addMouseListener(this);
        cPanel.addMouseMotionListener(this);
      
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        double xScale = clientScreenDim.getWidth()/cPanel.getWidth();
        
        double yScale = clientScreenDim.getHeight()/cPanel.getHeight();
       
    }

    
    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        int button = e.getButton();
        int xButton;
        if (button == MouseEvent.MOUSE_PRESSED) {
            xButton = button;
        }
    }

    public void mouseReleased(MouseEvent e) {
        int button = e.getButton();
        int xButton;
        if (button == MouseEvent.MOUSE_RELEASED) {
            xButton = button;
        }
      
    }

    
    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        
    }

    public void keyReleased(KeyEvent e) {
        
    }

}

