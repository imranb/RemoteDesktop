import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//Lidhet me serverin dhe shfrytezon klasat SendScreen dhe ReceiveComms
public class ClientMain {

    Socket socket = null;

    private void drawGUI() {
        JFrame frame = new JFrame("Remote Admin");
        JButton button= new JButton("Stop");
        
        frame.setBounds(100,100,150,150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(button);
        button.addActionListener( new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
     }
      );
      frame.setVisible(true);
    }
    
    public void init(String ip, int port ){		//metoda inicializuese

        Robot robot = null; //perdoret per te bere capture ekranin
        Rectangle rectangle = null; //perdoret per ruajtjen e dimensioneve te ekranit

        try {
            System.out.println("Duke u lidhur........");
            socket = new Socket(ip, port);
            System.out.println("Lidhja u arrit.");

            //e merr ekranin default
            GraphicsEnvironment graphicsEnv=GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev=graphicsEnv.getDefaultScreenDevice();

            //i merr dimensionet e ekranit
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            rectangle = new Rectangle(dim);

            robot = new Robot(gDev);

            //vizaton GUI-n e klientit
            drawGUI();
            //dergon screenshots ne ekranin e klientit
            new SendScreen(socket,robot,rectangle);
            //pranon komandat e serverit dhe i ekzekuton ato
            new ReceiveComms(socket,robot);
            
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
            
        } catch (IOException ex) {
            ex.printStackTrace();
            
        } catch (AWTException ex) {
                ex.printStackTrace();
        }
    }

    
    
    public static void main(String[] args){
        String ip = JOptionPane.showInputDialog("Jepni IP e serverit: ");
        String port = JOptionPane.showInputDialog("Jepni portin e serverit: ");
        new ClientMain().init(ip, Integer.parseInt(port));
    }
}
