import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2021
 *
 * Please filling your name below
 * @author: Gurjeet Cheema 1170878
 */
public class ShadowFlap extends AbstractGame {

    //finals
    private static final int APP_HEIGHT = 768;
    private static final int APP_WIDTH = 1024;
    private static final int FONT_SIZE = 48;
    private static final int COUNTER_LOCATION = 100;
    private static final int SCORE_GAP = 75;
    private static final int MAX_HEALTH_L0 = 3;
    private static final int MAX_HEALTH_L1 = 6;
    public final double Y_INITIAL = 350;
    public final double WEAPON_SPEED = 5;
    public final int HEART_Y = 15;
    private final int HEART_X = 100;
    private final int HEART_X_OFFSET = 50;
    private final int END_SCORE = 30;
    private final int FLAME_TIME = 50;
    private final int FLAME_INTERVAL = 100;
    private final int MAX_HEIGHT = 500;
    private final int MIN_HEIGHT = 100;
    private final int LOW_GAP = 116;
    private final int MID_GAP = -84;
    private final int HIGH_GAP = -284;
    private final int LEVELUP_TIME = 300;
    private final int MAX_TIMESCALE = 5;
    private final int MIN_TIMESCALE = 1;
    private final double WEAPON_OFFSET = 27.5;
    private final int LEVELUP_SCORE = 10;


    //variables
    private static int health = MAX_HEALTH_L0;
    private boolean start = false;
    private boolean end = false;
    private boolean levelup = false;
    private boolean doOnce = true;
    private boolean collied = false;
    int rand;
    int pipeRand;
    private int weapondistance = 0;
    private int score = 0;
    private int flamecount = 0;
    private int levelupCount = 0;
    private int count = 0;
    private int idcount = 0;
    private int healthCount = 0;
    private int heartX  = 0;
    private int timescale = 1;
    private int currentMax = MAX_HEALTH_L0;
    private boolean setweaponoutfoway = true;
    private boolean weaponon = false;
    private boolean shooting = false;
    private boolean flameon = false;
    private double yPosition;
    private int pipespawnTime = 200;
    private double pipespawnTimeScale = pipespawnTime;
    private int weaponsspawnTime = 250;
    private double weaponspawnTimeScale = weaponsspawnTime;
    private double TimeSpeed = 2;


    //string variables
    private static final String APP_NAME = "Shadow Flap";
    private static final String TEXT_START = "PRESS SPACE TO START";
    private static final String TEXT_START_LEVEL1 = "PRESS 'S' TO SHOOT";
    private static final String TEXT_COUNTER = "SCORE: ";
    private static final String TEXT_LEVEUP = "LEVEL-UP!";
    private static final String TEXT_ENDSCORE = "FINAL SCORE: ";
    private static final String TEXT_WIN = "CONGRATULATIONS!";
    private static final String TEXT_LOSE = "GAME OVER";

    //files
    private Image backgroundImage = new Image("res/level-0/background.png");
    private Image fullhealth = new Image("res/level/fullLife.png");
    private Image nohealth = new Image("res/level/noLife.png");
    private final Font appFont = new Font("res/font/slkscr.ttf", FONT_SIZE);

    //final screen alignment variables
    private final double textWinx = (Window.getWidth() / 2) - (appFont.getWidth(TEXT_WIN)/2);
    private final double textLosex = (Window.getWidth()/2)-(appFont.getWidth(TEXT_LOSE)/2);
    private final double textLeveupx = (Window.getWidth()/2)-(appFont.getWidth(TEXT_LEVEUP)/2);
    private final double textTextMidy = (Window.getHeight()/2)+(FONT_SIZE/2);
    private final double textScorex = (Window.getWidth()/2) - (appFont.getWidth(TEXT_ENDSCORE+score)/2);
    private final double textScorey = (Window.getHeight()/2) +(FONT_SIZE/2) + SCORE_GAP;

    //Instances
    private final Bird bird = new Bird();
    private Weapons playerWeapon = new Weapons();
    ArrayList<Pipes> pipes = new ArrayList<>();
    ArrayList<Flame> flames = new ArrayList<>();
    ArrayList<Weapons> weapons = new ArrayList<>();
    int[] array;


    public ShadowFlap() {
        super(APP_WIDTH,APP_HEIGHT,APP_NAME);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * this method is used to draw background
     */
    public void drawBackground(){
        this.backgroundImage.draw(Window.getWidth()/2,Window.getHeight()/2);
    }

    /**
     * this method is used to draw any text passed
     * @param strText passed for printing desired text
     * @param xCord x co-ordinate for where it is to be drawn
     * @param yCord y co-ordinate for where it is to be drawn
     */

    public void drawText(String strText, double xCord,double yCord){
        appFont.drawString(strText, xCord,yCord);
    }

    /**
     * checks if bird is colliding with window bounds or pipes
     * @param birdX bird x co-ordinate
     * @param birdY bird y co-ordinate
     * @param X pipe x co-ordinate
     * @param Y1 pipe bottom y co-ordinate
     * @param Y2 pipe top y co-ordinate
     * @param pipes pipe object
     * @param bird bird object
     * @return returns int that is associated with whether bird is hiting pipe or bound
     * it it returns a 1 its hitting pipe, 2 if it is hitting bounds of window and 0 if nothing
     * is being hit
     */
    public static int isColliding(double birdX, double birdY, double X, double Y1,
                                  double Y2, ArrayList<Pipes> pipes, Bird bird){

        Rectangle boxBird = bird.getBird().getBoundingBoxAt(new Point(birdX,birdY));

        for(Pipes pipe: pipes){

            Rectangle boxPBot = pipe.getPipe().getBoundingBoxAt(new Point(X,Y1));
            Rectangle boxPTop = pipe.getPipe().getBoundingBoxAt(new Point(X,Y2));

            if (boxBird.intersects(boxPBot)||boxBird.intersects(boxPTop)){
                return 1;
            }
        }
        if(Double.compare(birdY, Window.getHeight())>0 || birdY<0){
            return 2;
        }
        return 0;
    }

    /**
     * an overload of isCollding to detect collision for detecting collsion between bird and weapons
     * @param birdX bird x co-ordinate
     * @param birdY bird y co-ordinate
     * @param X weapon x co-ordinate
     * @param Y weapon y co-ordinate
     * @param bird bird object
     * @param weapons weapons array list
     * @return returns true or false whether weapon has collided with the bird or not
     */
    public static boolean isColliding(double birdX, double birdY, double X,
                                      double Y, Bird bird, ArrayList<Weapons> weapons){
        Rectangle boxBird = bird.getBird().getBoundingBoxAt(new Point(birdX,birdY));
        for(int i = 0;weapons.size() > i; i++){
            Rectangle boxWeapons = weapons.get(i).getWeapon().getBoundingBoxAt(new Point(X,Y));
            if(boxBird.intersects(boxWeapons)){
                return true;
            }
        }
        return false;
    }

    /**
     * an overload of isCollding to detect collision for detecting collsion between bird and weapons
     * @param birdX bird x co-ordinate
     * @param birdY bird y co-ordinate
     * @param pipeX pipe x co-ordinate
     * @param pBotY pipe bottom y co-ordinate
     * @param pTopY pipe top y co-ordinate
     * @param flames flames array list
     * @return returns true if bird collides with flame else returns false
     */
    public boolean isColliding(double birdX, double birdY, double pipeX,
                               double pBotY, double pTopY,ArrayList<Flame> flames){
        for(int i = 0; flames.size() > i; i++){
            Rectangle boxBird = bird.getBird().getBoundingBoxAt(new Point(birdX,birdY));
            Rectangle boxPBot = flames.get(i).getFlame().getBoundingBoxAt(new Point(pipeX,pBotY));
            Rectangle boxPTop = flames.get(i).getFlame().getBoundingBoxAt(new Point(pipeX,pTopY));

            if (boxBird.intersects(boxPBot)||boxBird.intersects(boxPTop)){
                return true;
            }
        }
        return false;
    }

    /**
     * an overload of isCollding to detect collision for weapons and pipes
     * @param weapon Weapons array
     * @return returning true if weapon intersects pipe else false
     */
    public boolean isColliding(Weapons weapon){
        Rectangle boxWeapon = weapon.getWeapon().getBoundingBoxAt(new Point(weapon.getX(),weapon.getY()));
        for(Pipes pipe:pipes){
            Rectangle boxPBot = pipe.getPipe().getBoundingBoxAt(new Point(pipe.getX(),pipe.getBOT_Y()));
            Rectangle boxPTop = pipe.getPipe().getBoundingBoxAt(new Point(pipe.getX(),pipe.getTOP_Y()));
            if(boxWeapon.intersects(boxPBot)||boxWeapon.intersects(boxPTop)){
                return true;
            }
        }
        return false;
    }

    /**
     * Collding action is a function that does action when certain collisions occur
     * @param i index
     * @param action int for what type of action required
     * @return return true or false if an action has occured
     */
    public boolean CollidingAction(int i, int action){

        // action to delete pipe, weapon and increase score if collided with player weapon that is shot
        if(action == 0){
            if(isColliding(playerWeapon.getX(),playerWeapon.getY(),pipes.get(i).getX()
                    ,pipes.get(i).TOP_Y, pipes.get(i).BOT_Y, pipes, bird)==1){
                if(pipes.get(i).Type <= playerWeapon.type && !weaponon){
                    pipes.remove(i);
                    score++;
                }
                shooting = false;
                playerWeapon.setX(-10);
                return true;
            }
        }
        // action to delete pipe and falme if bird collide with pipe
        else if(action == 1){
            if (isColliding(bird.getX(), bird.getY(), pipes.get(i).getX(),
                    pipes.get(i).getBOT_Y(), pipes.get(i).getTOP_Y(), pipes, bird) == 1) {
                //reduces health
                health --;
                for(int j = 0; flames.size() > j; j++){
                    if(pipes.get(i).id == flames.get(j).flameid){
                        flames.remove(j);
                    }
                }
                pipes.remove(i);
                return true;
            }
        }
        // action to delete pipe and falme if bird collide with flame
        else if(action == 2){
            if (isColliding(bird.getX(), bird.getY(), flames.get(i).getX(),
                    flames.get(i).getBOT_Y(), flames.get(i).getTOP_Y(),flames)) {
                //reduces health
                health --;
                for(int j = 0; pipes.size() > j; j++){
                    if(pipes.get(j).id == flames.get(i).flameid){
                        pipes.remove(j);
                    }
                }
                flames.remove(i);
                return true;
            }
        }
        // action to delete weapon if it had gone off bound of window or player has picked it up
        // if picked up it will be assingmed to playerWeapon
        else if(action == 3){
            if(isColliding(bird.getX(), bird.getY(),weapons.get(i).getX(),weapons.get(i).getY(),bird,weapons)){
                playerWeapon = weapons.get(i);
                weapons.remove(i);
                weaponon = true;
                shooting = false;
                weapondistance = 0;
                return true;
            }
            if(weapons.get(i).getX() < 0){
                weapons.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Method to dected if bird has passed a pipe
     * @param birdX birds x co-ordinate
     * @param pipeX pipe x co-ordinate
     * @return returning true if bird has passed pipe x co-ordinate else returns false
     */
    public boolean hasPassed(double birdX, double pipeX){
        if(birdX > pipeX){
            return true;
        }
        return false;
    }

    /**
     * draws start text according to level0 or level1
     */
    public void drawStartText(){
        //draws start text
        double textOffset = appFont.getWidth(TEXT_START)/2;
        drawText(TEXT_START, (Window.getWidth()/2)-textOffset,(Window.getHeight()/2));
        //adds "press s to shoot" text for level1
        if(levelup){
            double textOffset1 = appFont.getWidth(TEXT_START_LEVEL1)/2;
            drawText(TEXT_START_LEVEL1, (Window.getWidth()/2)-textOffset1,(Window.getHeight()/2)+FONT_SIZE);
        }
    }

    /**
     * method to set speed of pipe
     * @param speed speed to be set
     */
    public void setTimeSpeed(int speed){
        for(Pipes pipe : pipes){
            pipe.Speed = speed;
        }
    }

    /**
     * sets weapon speed
     * @param speed speed to be set
     */
    public void setWeaponSpeed(int speed){
        for(Weapons weapon : weapons){
            weapon.Speed = speed;
        }
    }
    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        if(setweaponoutfoway){
            playerWeapon.setX(-10);
        }
        setweaponoutfoway = false;

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        this.drawBackground();

        if (input.wasPressed(Keys.SPACE)) {
            start = true;
        }
        if (input.wasPressed(Keys.S) & weaponon){
            shooting = true;
            weaponon = false;
        }


        if (input.wasPressed(Keys.L) && timescale < MAX_TIMESCALE){
            pipespawnTimeScale -= pipespawnTimeScale/2;
            weaponspawnTimeScale -= weaponspawnTimeScale/2;
            TimeSpeed += TimeSpeed;
            timescale ++;
        }
        if (input.wasPressed(Keys.K) && timescale > MIN_TIMESCALE){
            pipespawnTimeScale += pipespawnTimeScale;
            weaponspawnTimeScale += weaponspawnTimeScale;
            TimeSpeed -= TimeSpeed/2;
            timescale --;
        }

        setTimeSpeed((int)(TimeSpeed));
        setWeaponSpeed((int)(TimeSpeed));
        pipespawnTime = (int)(pipespawnTimeScale);
        weaponsspawnTime = (int)(weaponspawnTimeScale);

        //if start is true due to space being pressed start text will no longer be drawn
        if (!start){drawStartText();}
        else {
            if(health <= 0){
                end = true;
                collied = true;
            }

            //checks if bird collides with window bounds or pipes
            if(isColliding(bird.getX(), bird.getY(),0,0,0, pipes,bird) == 2){
                //reduces health
                health --;
                bird.Y = Y_INITIAL;
                bird.SpeedY = 0;
            }

            for(int i = 0; pipes.size() > i; i++) {
               if (CollidingAction(i,0)){
                   break;
               }

                if(CollidingAction(i,1)){
                    break;
                }


            }
            for(int i = 0; flames.size() > i; i++){
                if(CollidingAction(i,2)){
                    break;
                }
            }
            for(int i = 0; i < weapons.size();i++){
                if(CollidingAction(i,3)){
                    break;
                }
            }
            if(weaponon){
                playerWeapon.setX(bird.getX() + WEAPON_OFFSET);
                playerWeapon.setY(bird.Y);
                playerWeapon.drawWeapons();
            }

            else if(!weaponon && shooting){
                playerWeapon.setX(playerWeapon.getX() + WEAPON_SPEED);
                playerWeapon.drawWeapons();
                weapondistance ++;
            }

            if(weapondistance >= playerWeapon.range){
                shooting = false;
                weaponon = false;
                playerWeapon = new Weapons();
                playerWeapon.setX(-10);
                weapondistance = 0;
            }

            //if game has ended due to loss or victory
            if(end){

                //if loss
                if(collied){
                    drawText(TEXT_LOSE,textLosex,textTextMidy);
                    drawText(TEXT_ENDSCORE+score,textScorex,textScorey);
                }

                //if victory
                else{
                    drawText(TEXT_WIN,textWinx,textTextMidy);
                    drawText(TEXT_ENDSCORE +score,textScorex,textScorey);
                }

            }
            //checks for level up
            else if(levelup && levelupCount < LEVELUP_TIME && doOnce){
                drawText(TEXT_LEVEUP,textLeveupx,textTextMidy);
                levelupCount ++;
                if(levelupCount >= LEVELUP_TIME){
                    doOnce  = false;
                    start = false;
                    backgroundImage = new Image("res/level-1/background.png");
                    bird.birdUp = new Image("res/level-1/birdWingUp.png");
                    bird.birdDown = new Image("res/level-1/birdWingDown.png");
                    pipes.removeAll(pipes);
                    bird.Y = Y_INITIAL;
                }
            }
            //if no collision or victory is detected bird and pipes remain rendered
            else {
                count ++;
                //drawing counter
                drawText(TEXT_COUNTER + score, COUNTER_LOCATION, COUNTER_LOCATION);




                //pipes spawning
                if(count % pipespawnTime == 0){

                    //if level1 random pipe height then choose random height between
                    //100 and 500
                    if(levelup) {
                        rand = (int)(Math.random() * HIGH_GAP + LOW_GAP);
                        yPosition = rand;
                        pipeRand = (int) (Math.random() * 2 + 1);

                    }
                    //if level0 uses randomly choose between low, mid or high pipe gap
                    else{
                        rand = (int)(Math.random() * 3 + 1);
                        switch (rand){
                            case 1:
                                yPosition = HIGH_GAP;
                                break;
                            case 2:
                                yPosition = MID_GAP;
                                break;
                            case 3:
                                yPosition = LOW_GAP;
                                break;
                        }
                        pipeRand = 1;
                    }


                    //chooses and adds pipe subclasses that was randomly generated
                    switch (pipeRand) {

                        case 1:
                            pipes.add(new plasticPipe());
                            pipes.get(pipes.size()-1).Type = 1;
                            break;
                        case 2:
                            pipes.add(new steelPipe());
                            pipes.get(pipes.size()-1).Type = 2;
                            flames.add(new Flame());
                            flames.get(flames.size()-1).flameid = idcount;
                            break;
                    }

                    //sets values for pipes
                    pipes.get(pipes.size()-1).yPosition = yPosition;
                    pipes.get(pipes.size()-1).id = idcount;
                    idcount += 1;

                }

                //adds new instances of a weapon every weaponspawntime, ensures it does not
                // spawn at the same pipespawnTime. Randomly selects position and type.
                if(count % weaponsspawnTime == 0 && count % pipespawnTime != 0 && levelup) {
                    int weaponRand = (int) (Math.random() * 2 + 1);
                    double weaponlocRand = Math.random() * (MAX_HEIGHT-MIN_HEIGHT) + MAX_HEIGHT;

                    switch (weaponRand) {
                        case 1:
                            weapons.add( new weaponRock());
                            break;
                        case 2:
                            weapons.add(new weaponBomb());
                            break;
                    }
                    //allocates Y value
                    weapons.get(weapons.size()-1).setY(weaponlocRand);

                    //if weapon spawns ontop of pipes it will remove it
                    if(isColliding(weapons.get(weapons.size()-1))){
                        weapons.remove(weapons.size()-1);
                    }
                }

                //draws all weapons
                for(int i = 0; weapons.size() > i; i++){
                    weapons.get(i).drawWeapons();
                }

                //drawing pipes loop & scoring
                for(int i = 0; pipes.size()-1 > i; i++){

                    //checks if bird has passed pipe and isn't already a counted as passed
                    if(hasPassed(bird.X, pipes.get(i).getX()) && (pipes.get(i).passed == false)){
                        score ++;
                        pipes.get(i).passed = true;
                    }
                    //draws pipes, if draw pipes returns true it will remove pipe as it is out of bounds
                    if(pipes.get(i).drawPipes()){
                        pipes.remove(i);
                    }

                    //turns on flame and draws flame in intervals
                    if(pipes.get(i).Type == 2 && ((count % FLAME_INTERVAL == 0)||flameon)){
                        int icount = 0;
                        if(flamecount < FLAME_TIME){
                            flameon = true;
                        }
                        else {
                            flamecount = 0;
                            flameon = false;
                        }
                        flamecount ++;
                        for (int j = 0; j < flames.size(); j++) {

                            if(flames.get(j).flameid == pipes.get(i).id){

                                flames.get(icount).drawFlame(pipes.get(i).getX(),pipes.get(i).getBOT_Y(),pipes.get(i).getTOP_Y());

                            }
                            icount ++;
                        }

                    }


                }

                //Health drawing loop
                heartX = HEART_X;
                for(int i = 0; currentMax > i; i++){


                    if(health > healthCount){
                        this.fullhealth.drawFromTopLeft(heartX,HEART_Y);
                    }
                    else{
                        this.nohealth.drawFromTopLeft(heartX,HEART_Y);
                    }
                    healthCount++;
                    heartX += HEART_X_OFFSET;
                }
                healthCount = 0;

                //ends game if end game score is met
                if(score >= END_SCORE){
                    end = true;
                }

                //flying mechanism
                if (input.wasPressed(Keys.SPACE)) {
                    bird.fly();
                } else {
                    bird.fall();
                }

                //leveling up
                if(score >= LEVELUP_SCORE && !levelup){
                    levelup = true;
                    health = MAX_HEALTH_L1;
                    currentMax = MAX_HEALTH_L1;
                }
            }


        }
    }

}
