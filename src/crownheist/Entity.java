package crownheist;

/* Entity.java
 * An entity is any object that appears in the game.
 * It is responsible for resolving collisions and movement.
 */

import java.awt.*;

public abstract class Entity {

    // Java Note: the visibility modifier "protected"
    // allows the variable to be seen by this class,
    // any classes in the same package, and any subclasses
    // "private" - this class only
    // "public" - any class can see it

    protected double x;   // current x location
    protected double y;   // current y location
    protected Sprite sprite; // this entity's sprite
    protected double dx; // horizontal speed (px/s)  + -> right
    protected double dy; // vertical speed (px/s) + -> down

    private Rectangle me = new Rectangle(); // bounding rectangle of
    // this entity
    private Rectangle him = new Rectangle(); // bounding rect. of other
    // entities

    /* Constructor
     * input: reference to the image for this entity,
     *        initial x and y location to be drawn at
     */
    public Entity(String r, int newX, int newY) {
        x = newX;
        y = newY;
        sprite = (SpriteStore.get()).getSprite(r);
    } // constructor

    /* move
     * input: delta - the amount of time passed in ms
     * output: none
     * purpose: after a certain amout of time has passed,
     *          update the location
     */
    public void move(long delta) {
        // update location of entity based ov move speeds
        x += (delta * dx) / 1000;
        y += (delta * dy) / 1000;
    } // move

    // set entity gif
    public void setGif (int a) {
        switch (a) {
            case 0: this.sprite = (SpriteStore.get()).getSprite("sprites/cloak3.png");
                break;
            case 1: this.sprite = (SpriteStore.get()).getSprite("sprites/cloak1.png");
                break;
            case 2: this.sprite = (SpriteStore.get()).getSprite("sprites/cloak2.png");
                break;
            case 3: this.sprite = (SpriteStore.get()).getSprite("sprites/pick1.png");
                break;
            case 4: this.sprite = (SpriteStore.get()).getSprite("sprites/pick2.png");
                break;
            case 5: this.sprite = (SpriteStore.get()).getSprite("sprites/pick3.png");
                break;
            case 6: this.sprite = (SpriteStore.get()).getSprite("sprites/pick1.png");
                break;
            case 7: this.sprite = (SpriteStore.get()).getSprite("sprites/warriormountain.png");
                break;
            case 8: this.sprite = (SpriteStore.get()).getSprite("sprites/warriormountain2.png");
                break;
            case 9: this.sprite = (SpriteStore.get()).getSprite("sprites/warriormountain3.png");
                break;
            case 10: this.sprite = (SpriteStore.get()).getSprite("sprites/warriorforest.png");
                break;
            case 11: this.sprite = (SpriteStore.get()).getSprite("sprites/warriorforest2.png");
                break;
            case 12: this.sprite = (SpriteStore.get()).getSprite("sprites/warriorforest3.png");
                break;
            case 13: this.sprite = (SpriteStore.get()).getSprite("sprites/warriorfire.png");
                break;
            case 14: this.sprite = (SpriteStore.get()).getSprite("sprites/warriorfire2.png");
                break;
            case 15: this.sprite = (SpriteStore.get()).getSprite("sprites/warriorfire3.png");
                break;
        } // switch
    } // setGif

    // get and set velocities
    public void setHorizontalMovement(double newDX) {
        dx = newDX;
    } // setHorizontalMovement

    public void setVerticalMovement(double newDY) {
        dy = newDY;
    } // setVerticalMovement

    public double getHorizontalMovement() {
        return dx;
    } // getHorizontalMovement

    public double getVerticalMovement() {
        return dy;
    } // getVerticalMovement

    // get position
    public int getX() {
        return (int) x;
    } // getX

    public int getY() {
        return (int) y;
    } // getY

    /*
     * Draw this entity to the graphics object provided at (x,y)
     */
    public void draw (Graphics g) {
        sprite.draw(g,(int)x,(int)y);
    }  // draw

    /* Do the logic associated with this entity.  This method
     * will be called periodically based on game events.
     */
    public void doLogic() {}

    /* collidesWith
     * input: the other entity to check collision against
     * output: true if entities collide
     * purpose: check if this entity collides with the other.
     */
    public boolean collidesWith(Entity other) {
        me.setBounds((int)x, (int)y, sprite.getWidth(), sprite.getHeight());
        him.setBounds(other.getX(), other.getY(),
                other.sprite.getWidth(), other.sprite.getHeight());
        return me.intersects(him);
    } // collidesWith

    /* collidedWith
     * input: the entity with which this has collided
     * purpose: notification that this entity collided with another
     * Note: abstract methods must be implemented by any class
     *       that extends this class
     */
    public abstract void collidedWith(Entity other);

} // Entity class