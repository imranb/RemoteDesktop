import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.ImageIcon;

//Dergon screenshot-a per cdo periudhe te caktuar kohore
public class SendScreen extends Thread {

    Socket socket = null; 
    Robot robot = null; // perdoret per te bere capture ekranin
    Rectangle rectangle = null; //perdoret per te paraqitur dimensionet e ekranit
    boolean continueLoop = true; //sherben si boolean per te dale nga programi
    
    public SendScreen(Socket socket, Robot robot,Rectangle rect) {
        this.socket = socket;
        this.robot = robot;
        rectangle = rect;
        start();
    }

    public void run(){
    	
        ObjectOutputStream oos = null; //sherben per te shkruar nje objekt ne stream
        try{
            //kthen nje OutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());
             //i dergon madhesine e ekranit serverit qe te llogarise lokacionin e sakte te mausit ne panelin e serverit
            oos.writeObject(rectangle);
        }catch(IOException ex){
            ex.printStackTrace();
        }

       while(continueLoop){
            //ben screenshot
            BufferedImage image = robot.createScreenCapture(rectangle);
            ImageIcon imageIcon = new ImageIcon(image);

            //e dergon screenshot-in ne server
            try {
                
                oos.writeObject(imageIcon);
                oos.reset(); //reseton gjendjen e ObjectOutputStream
                
            } catch (IOException ex) {
               ex.printStackTrace();
            }

            
            try{
                Thread.sleep(50);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

    }

}


