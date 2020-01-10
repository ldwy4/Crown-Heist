package crownheist;

public class DoorEntity extends Entity{

    private Game game; // the game in which the alien exists

    public DoorEntity(Game g, String r, int newX, int newY) {
        super(r, newX, newY);  // calls the constructor in Entity
        game = g;
    } // constructor

    public void collidedWith(Entity other) {
        // collisions with aliens are handled in ShotEntity and ShipEntity
    } // collidedWith
}//DoorEntity