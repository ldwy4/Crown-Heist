package crownheist;
import java.lang.Math;
/* ShipEntity.java
 * March 27, 2006
 * Represents player's ship
 */
public class ShipEntity extends Entity {

    private Game game; // the game in which the ship exists
    private int playerWidth = 68;
    private int playerHeight = 98;
    private int blockWidth = 40;



    /* construct the player's ship
     * input: game - the game in which the ship is being created
     *        ref - a string with the name of the image associated to
     *              the sprite for the ship
     *        x, y - initial location of ship
     */
    public ShipEntity(Game g, String r, int newX, int newY) {
        super(r, newX, newY);  // calls the constructor in Entity
        game = g;
    } // constructor

    /* move
     * input: delta - time elapsed since last move (ms)
     * purpose: move ship
     */
    public void move (long delta){

        // stop at left side of screen
        if ((dx < 0) && (x < 10)) {
            return;
        } // if
        // stop at right side of screen
        if ((dx > 0) && (x > 3250)) {
            return;
        } // if

        // cycle through grid system
        for(int j = 0; j < game.getBlock().length; j++){
            for(int i = 0; i < game.getBlock().length; i++){

                // true if this part of grid is a block
                if(game.getBlock()[j][i] == 1){
                    for(int k = 0; k < game.getBlockCounter(); k++) {

                        // player approaches from top
                        if ((x <= game.getBlockX()[k] && x + playerWidth >= game.getBlockX()[k] + blockWidth
                                || x + playerWidth >= game.getBlockX()[k] && x + playerWidth <= game.getBlockX()[k] + blockWidth
                                || x >= game.getBlockX()[k] && x <= game.getBlockX()[k] + blockWidth)
                                && (y < game.getBlockY()[k] && y + playerHeight >= game.getBlockY()[k] && y + playerHeight <= game.getBlockY()[k] + (blockWidth / 2))) {
                            game.setIsFalling(false);
                            y = game.getBlockY()[k] - (playerHeight + 2);
                        }

                        // player approaches from bottom
                        if ((x <= game.getBlockX()[k] && x + playerWidth >= game.getBlockX()[k] + blockWidth
                                || x + playerWidth >= game.getBlockX()[k] && x + playerWidth <= game.getBlockX()[k] + blockWidth
                                || x >= game.getBlockX()[k] && x <= game.getBlockX()[k] + blockWidth)
                                && (y <= game.getBlockY()[k] + blockWidth && y + playerHeight >= game.getBlockY()[k] + blockWidth)
                                && (Math.abs(y - (game.getBlockY()[k] + blockWidth)) < Math.abs((y + playerHeight) - (game.getBlockY()[k] + blockWidth)))
                                && y < 1140) {
                            game.setIsFalling(true);
                            y = game.getBlockY()[k] + (blockWidth + 1);
                        }

                        // player approaches from left
                        if (x + playerWidth >= game.getBlockX()[k]
                                && x + playerWidth <= game.getBlockX()[k] + blockWidth
                                && (y + playerHeight >= game.getBlockY()[k] && y <= game.getBlockY()[k]
                                || y >= game.getBlockY()[k] && y <= game.getBlockY()[k] + blockWidth)
                            /*&& dx > 0*/) {
                            x = game.getBlockX()[k] - (playerWidth + 1);
                            return;
                        }

                        // player approaches from right
                        if (x >= game.getBlockX()[k]
                                && x <= game.getBlockX()[k] + blockWidth
                                && (y + playerHeight >= game.getBlockY()[k] && y <= game.getBlockY()[k]
                                || y >= game.getBlockY()[k] && y <= game.getBlockY()[k] + blockWidth)
                                /*&& dx < 0*/) {
                            x = game.getBlockX()[k] + (blockWidth + 1);
                            return;
                        }

                        // player approaches from bottom
                    }
                }
            }
        }

        super.move(delta);  // calls the move method in Entity
    } // move


    /* collidedWith
     * input: other - the entity with which the ship has collided
     * purpose: notification that the player's ship has collided
     *          with something
     */
    public void collidedWith(Entity other) {
        if (other instanceof GemEntity && game.getDownPressed()) {
            game.removeEntity(other);
            game.notifyGemKilled();
        } // if

        if(other instanceof DoorEntity && game.getGemCount() == 0){
            game.notifyWin();
        }

        if(other instanceof AlienEntity){
            game.notifyDeath();
        }
    } // collidedWith

} // ShipEntity class