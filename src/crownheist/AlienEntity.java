package crownheist;

public class AlienEntity extends Entity {

    private double moveSpeed = 105; // horizontal speed

    private Game game; // the game in which the this exists

    /* construct a new this
     * input: game - the game in which the this is being created
     *        r - the image representing the this
     *        x, y - initial location of this
     */
    public AlienEntity(Game g, String r, int newX, int newY) {
        super(r, newX, newY);  // calls the constructor in Entity
        game = g;
        dx = -moveSpeed;  // start off moving left
    } // constructor

    public void collidedWith(Entity other) {
        // collisions with this are handled in ShotEntity and ShipEntity
    } // collidedWith

} // thisEntity class