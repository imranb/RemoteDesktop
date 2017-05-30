import java.awt.Robot;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

//Pranon komandat e serverit dhe i ekzekuton te klienti
public class ReceiveComms extends Thread {

    Socket socket = null;
    Robot robot = null;
    boolean continueLoop = true;

    public ReceiveComms(Socket socket, Robot robot) {
        this.socket = socket;
        this.robot = robot;
        start(); //fillon thread-in
    }

    public void run(){
        Scanner scanner = null;
        try {
        	
            scanner = new Scanner(socket.getInputStream());

            while(continueLoop){		//pranon komandat dhe u pergjigjet seciles
                
                
                int command = scanner.nextInt();
                
                switch(command){
                    case -1:
                        robot.mousePress(scanner.nextInt());
                    break;
                    case -2:
                        robot.mouseRelease(scanner.nextInt());
                    break;
                    case -3:
                        robot.keyPress(scanner.nextInt());
                    break;
                    case -4:
                        robot.keyRelease(scanner.nextInt());
                    break;
                    case -5:
                        robot.mouseMove(scanner.nextInt(), scanner.nextInt());
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}