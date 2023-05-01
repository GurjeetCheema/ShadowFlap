import bagel.*;

import java.nio.channels.Pipe;

public class Pipes {

    //constants
    private final DrawOptions drawing = new DrawOptions();
    //variables
    public int id;
    public boolean passed = false;
    public static double yPosition;
    public double TOP_Y = yPosition;
    public double BOT_Y = Window.getHeight() + yPosition + GAP;
    public int Type;
    public int Speed = 2;
    public Image pipe;
    private static final int GAP = 168;
    private double X = Window.getWidth();

    /**
     * draw pipes and moves them
     * @return returns true if pipe is less then x co-ordinate of window bound else false
     */
    public boolean drawPipes(){

        //stops drawing if out of bounds
        if(X >= 0){
            X -= Speed;
            pipe.draw(X,BOT_Y, drawing.setRotation(Math.PI));
            pipe.draw(X,TOP_Y);
        }
        else{
            return true;
        }
        return false;
    }

    /**
     * gets pipe image
     * @return returns pipe image
     */
    public Image getPipe() {return pipe;}

    /**
     * gets Bottom y co-ordinate
     * @return reutnrs bottom y co-ordinate
     */
    public double getBOT_Y(){return BOT_Y;}

    /**
     * gets top y co-ordinate
     * @return returns top y co-ordinate
     */
    public double getTOP_Y(){return TOP_Y;}

    /**
     * gets x co-ordinate
     * @return returns x co-ordinate
     */
    public double getX(){return X;}


}

/**
 * subclass for plastic pipes
 */
class plasticPipe extends Pipes{
    /**
     * sets plastic pipe image for super class
     */
    public plasticPipe(){
        this.pipe = new Image("res/level/plasticPipe.png");
    }

}

/**
 * subclass for steel pipes
 */
class steelPipe extends Pipes{
    /**
     * sets steel pipe image for super class
     */
    public steelPipe(){
        this.pipe = new Image("res/level-1/steelPipe.png");
    }
}


