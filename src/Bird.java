import bagel.*;
public class Bird {

    //constants
    public static final double ACCELERATION = 0.11;
    public final double MAX_SPEED_Y = 8;
    public final double X = 200;

    //variables
    public double Y = 350;
    public double SpeedY = 0;
    public int flapCounter = 1;
    public boolean stateDown = false;
    public static Image birdUp = new Image("res/level-0/birdWingUp.png");
    public static Image birdDown = new Image("res/level-0/birdWingDown.png");

    /**
     * draws Bird and alternated type for every 10 frames
     */
    public void drawBird(){
        //every 10 frames makes bird flap
        if(flapCounter % 10 == 0){
            birdDown.draw(X,Y);
            flapCounter ++;
            //tells getter what image to return
            stateDown = true;
        }
        else{
            birdUp.draw(X,Y);
            flapCounter ++;
            stateDown = false;
        }
    }

    /**
     * moves bird up when flying
     */
    public void fly(){
        SpeedY = -5;
        Y += SpeedY;
        this.drawBird();
    }

    /**
     * gravity applied to bird when not flying
     */
    public void fall(){
        if ((SpeedY + ACCELERATION) <= MAX_SPEED_Y){
            SpeedY = SpeedY + ACCELERATION;
        }
        else{
            SpeedY = MAX_SPEED_Y;
        }

        Y += SpeedY;
        this.drawBird();
    }

    //getters
    /**
     * gets birds Y co-ordinate
     * @return returns bird y co-ordinate
     */
    public double getY(){return Y;}

    /**
     * gets birds X co-ordinate
     * @return returns bird X co-ordinate
     */
    public double getX(){return X;}

    /**
     * gets bird image according to current image being drawn
     * @return bird image currently being used
     */
    public Image getBird() {
        if(stateDown){
            return birdDown;
        }
        else{
            return birdUp;
        }

    }

}


