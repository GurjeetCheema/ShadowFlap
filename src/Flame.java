import bagel.DrawOptions;
import bagel.Image;

public class Flame {
    //variables
    public int flameid;
    public Image flame = new Image("res/level-1/flame.png");
    public double BOT_Y;
    public double TOP_Y;
    public double X;
    //constants
    private final DrawOptions drawing = new DrawOptions();

    /**
     * Draw flame for corresponding pipe
     * @param x x co-ordinate
     * @param bot y bottom co-ordinate
     * @param top y top co-ordinate
     */
    public void drawFlame(double x,double bot,double top){
        BOT_Y = bot - 394;
        TOP_Y = top + 394;
        X = x;
        flame.draw(x,BOT_Y, drawing.setRotation(Math.PI));
        flame.draw(x,TOP_Y);
    }

    /**
     * gets image for flame
     * @return returns image for flame
     */
    public Image getFlame(){return flame;}

    /**
     * gets x co-ordiante
     * @return returns x co-ordinate double
     */
    public double getX(){return X;}
    /**
     * gets bottom y co-ordiante
     * @return returns bottom y co-ordinate double
     */
    public double getBOT_Y(){return BOT_Y;}
    /**
     * gets top y co-ordiante
     * @return returns top y co-ordinate double
     */
    public double getTOP_Y(){return TOP_Y;}
}
