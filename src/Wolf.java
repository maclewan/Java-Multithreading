public class Wolf extends Animal {
    Integer pauseTime=new Integer(0);

    public Wolf(){
        super();
    }

    public void setPauseTime5() {
        pauseTime = 5;
    }
    public void decrementPauseTime(){
        pauseTime--;
    }
}
