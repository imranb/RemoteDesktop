//Komandat e mausit qe mund te dergohen nga serveri
public enum MouseCommands {
    PRESS_MOUSE(-1),
    RELEASE_MOUSE(-2),
    PRESS_KEY(-3),
    RELEASE_KEY(-4),
    MOVE_MOUSE(-5);

    private int shkurt;

    MouseCommands(int shkurt){
        this.shkurt = shkurt;
    }
    
   public int getShkurt(){
        return shkurt;
    }
}
