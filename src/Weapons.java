import bagel.Image;
import bagel.Window;

public class Weapons {
    public double X = Window.getWidth();
    public double Y;
    public int Speed = 2;
    public Image weapon;
    public int range;
    public int type;

    /**
     * draws Weapons at co-ordinate positions
     * @return returns true if weapon in less than bound of window
     * else return false
     */
    public boolean drawWeapons(){
        //stops drawing if out of bounds
        if(X >= 0){
            X -= Speed;
            weapon.draw(X,Y);
        }
        else{
            return true;
        }
        return false;
    }

    /**
     * gets image for weapon
     * @return returns weapon image
     */
    public Image getWeapon(){return weapon;}

    /**
     * gets x-co-ordinate
     * @return returns double for x co-ordinate
     */
    public double getX(){return X;}

    /**
     * gets y-co-ordinate
     * @return retruns double for y co-ordinate
     */
    public double getY(){return Y;}

    /**
     * sets x co-orinate for weapon
     * @param x co-ordinate to be set
     */
    public void setX(double x) {
        X = x;
    }

    /**
     * sets y co-orinate for weapon
     * @param y co-ordinate to be set
     */
    public void setY(double y) {
        Y = y;
    }
}

/**
 * WeaponRock subclass Weapons that changes
 * range of weapon, image and type according for
 * Weapon Rock
 */
class weaponRock extends Weapons{
    /**
     * sets values for weapon rock
     */
    public weaponRock(){
        this.weapon = new Image("res/level-1/rock.png");
        this.range = 100;
        this.type = 1;
    }

}

/**
 * Weapon Bomb subclass Weapons that changes
 * range of weapon, image and type according for
 * Weapon Bomb
 */
class weaponBomb extends Weapons{
    /**
     * sets values for weapon bomb
     */
    public weaponBomb(){
        this.weapon = new Image("res/level-1/bomb.png");
        this.range = 125;
        this.type = 2;
    }
}
