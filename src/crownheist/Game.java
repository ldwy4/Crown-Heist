package crownheist;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;

public class Game extends Canvas {

    private final int START_SCREEN = 0; // start screen
    private final int STORY_SCREEN = 1; // story screens
    private final int LEVEL_ONE = 2; // level 1
    private final int MIDDLE_SCREEN1 = 3; // screen between level 1 and level 2
    private final int LEVEL_TWO = 4; // level 2
    private final int MIDDLE_SCREEN2 = 5; // screen between level 1 and level 2
    private final int LEVEL_THREE = 6; // level 3
    private final int END_SCREEN = 7; // End game screen

    private BufferStrategy strategy;   // take advantage of accelerated graphics
    private boolean waitingForKeyPress = true;  // true if game held up until
    // a key is pressed
    private boolean leftPressed = false;  // true if left arrow key currently pressed
    private boolean rightPressed = false; // true if right arrow key currently pressed
    private boolean upPressed = false; // true if up arrow key currently pressed
    private boolean downPressed = false; // true if up arrow key currently pressed
    private boolean firePressed = false; // true if firing

    private boolean gameRunning = true;
    private ArrayList entities = new ArrayList(); // list of entities
    // in game
    private ArrayList removeEntities = new ArrayList(); // list of entities
    // to remove this loop
    private ArrayList alienList = new ArrayList(); // list of all aliens
    private Entity ship;  // the ship
    private Entity alien;  // the alien
    private Entity alien2;
    private double moveSpeed = 100; // hor. vel. of ship (px/s)
    private double gravity = 80; //falling speed of character (px/s)
    private long lastFire = 0; // time last shot fired
    private long firingInterval = 300; // interval between shots (ms)
    private int walkingCount = 0; // number to set ship gif to when walking
    private long lastWalk = 0;
    private long walkingInterval = 5; // interval between frames
    private int pickCount = 3; // number to set ship gif to when picking up
    private int storyTime = 4000; // interval between story scenes
    private long lastStory = 0; // time of last screen
    private int gemCount = 0; // # of aliens left on screen
    private long lastPick = 0; // time last pick occurred
    private long pickInterval = 7; // interval between frames
    private int enemyCount = 7; // number to set ship gif to when walking
    private long lastEnemyWalk = 0; // time enemy last walked
    private long enemyWalkingInterval = 100; // interval between frames

    private int worldSizeX = 3200; //world width
    private int worldSizeY = 1400; //world height
    private int cameraX = 1250; //viewing screen width
    private int cameraY = 720; //viewing screen height
    private int offsetMaxX = worldSizeX - cameraX; // maximum offset of the camera in the X direction
    private int offsetMaxY = worldSizeY - cameraY; // maximum offset of the camera in the Y direction
    private int offsetMinX = 0; // minimum offset of the camera in the X direction
    private int offsetMinY = 0; // minimum offset of the camera in the Y direction
    private int camX = 0; //camera position X
    private int camY = 0; //camera position Y
    private int levelCounter = 0; // which level the game is at
    private int storyBoardCount = 0; // which scene is playing in the story stage
    private boolean storyPlay = true; // plays the story stage
    private boolean levelWin = false; // set false until level is won
    private int[][] blocks = new int[36][80]; //grind of the world
    private int[] blockY = new int[2801]; //stores x location of blocks
    private int[] blockX = new int[2801]; //stores y location of blocks
    private String [] storyBoard = {"sprites/storyquesttaker.png", "sprites/storypalace.png", "sprites/storyqueen.png",
            "sprites/storyjewels.png", "sprites/storymask.png", "sprites/storygoodluck.png", "sprites/storyinstructions.png"};
    private int blockCounter = 0; //number of blocks in the game
    private String message = ""; // message to display while waiting
    // for a key press
    private boolean isFalling = true; //true after jumping
    private boolean logicRequiredThisLoop = false; // true if logic
    // needs to be
    // applied this loop

    private Sprite imageBlock = null; //block image
    private Sprite background = null; // background image


    public void initializeBlocks(int[][]blocks){
        if(levelCounter == LEVEL_ONE) {
            for (int j = 0; j < blocks.length; j++) {
                for (int i = 0; i < blocks[0].length; i++) {
                    blocks[j][i] = 0;
                    blocks[31][i] = 1;
                    blocks[32][i] = 1;
                    blocks[33][i] = 1;
                    blocks[34][i] = 1;
                    blocks[35][i] = 1;

                    if(j >= 25) {
                        blocks[j][53] = 1;
                        blocks[j][54] = 1;
                    }
                    if(j >= 26) {
                        blocks[j][14] = 1;
                        blocks[j][15] = 1;
                        blocks[j][16] = 1;
                        blocks[j][17] = 1;
                        blocks[j][18] = 1;
                        blocks[j][19] = 1;
                        blocks[j][20] = 1;
                        blocks[j][21] = 1;
                        blocks[j][22] = 1;
                        blocks[j][52] = 1;
                        blocks[j][55] = 1;
                        blocks[j][65] = 1;
                        blocks[j][66] = 1;
                    }
                    if(j >= 27){
                        blocks[j][13] = 1;
                        blocks[j][23] = 1;
                        blocks[j][51] = 1;
                        blocks[j][56] = 1;
                        blocks[j][57] = 1;
                        blocks[j][58] = 1;
                        blocks[j][59] = 1;
                        blocks[j][60] = 1;
                        blocks[j][61] = 1;
                        blocks[j][62] = 1;
                        blocks[j][63] = 1;
                        blocks[j][64] = 1;
                        blocks[j][67] = 1;
                    }
                    if(j >= 28){
                        blocks[j][11] = 1;
                        blocks[j][12] = 1;
                        blocks[j][24] = 1;
                        blocks[j][25] = 1;
                        blocks[j][49] = 1;
                        blocks[j][50] = 1;
                        blocks[j][68] = 1;
                    }
                    if(j >= 29){
                        blocks[j][9] = 1;
                        blocks[j][10] = 1;
                        blocks[j][26] = 1;
                        blocks[j][27] = 1;
                        blocks[j][43] = 1;
                        blocks[j][44] = 1;
                        blocks[j][45] = 1;
                        blocks[j][46] = 1;
                        blocks[j][47] = 1;
                        blocks[j][48] = 1;
                        blocks[j][69] = 1;
                    }
                }
            }
            blocks[21][14] = 1;
            blocks[21][15] = 1;
            blocks[21][16] = 1;
            blocks[21][17] = 1;
            blocks[21][18] = 1;
            blocks[21][19] = 1;
            blocks[30][8] = 1;
            blocks[30][28] = 1;
            blocks[30][42] = 1;
            blocks[30][70] = 1;
        }else if(levelCounter == LEVEL_TWO) {
            for (int j = 0; j < blocks.length; j++) {
                for (int i = 0; i < blocks[0].length; i++) {
                    blocks[j][i] = 0;
                    blocks[31][i] = 1;
                    blocks[32][i] = 1;
                    blocks[33][i] = 1;
                    blocks[34][i] = 1;
                    blocks[35][i] = 1;

                    if(j >= 11 && i > 67){
                        blocks[j][i] = 1;
                    }
                    if(j >= 12){
                        blocks[j][67] = 1;
                    }
                    if(j >= 13){
                        blocks[j][65] = 1;
                        blocks[j][66] = 1;
                    }
                    if(j >= 14){
                        blocks[j][64] = 1;
                    }
                    if(j >= 15){
                        blocks[j][60] = 1;
                        blocks[j][61] = 1;
                        blocks[j][62] = 1;
                        blocks[j][63] = 1;
                    }
                    if(j >= 16){
                        blocks[j][43] = 1;
                        blocks[j][44] = 1;
                        blocks[j][45] = 1;
                        blocks[j][46] = 1;
                        blocks[j][58] = 1;
                        blocks[j][59] = 1;
                    }
                    if(j >= 17){
                        blocks[j][30] = 1;
                        blocks[j][31] = 1;
                        blocks[j][41] = 1;
                        blocks[j][42] = 1;
                        blocks[j][57] = 1;
                    }
                    if(j >= 18){
                        blocks[j][29] = 1;
                        blocks[j][32] = 1;
                        blocks[j][40] = 1;
                        blocks[j][47] = 1;
                    }
                    if(j >= 19){
                        blocks[j][28] = 1;
                        blocks[j][33] = 1;
                        blocks[j][34] = 1;
                        blocks[j][35] = 1;
                        blocks[j][36] = 1;
                        blocks[j][37] = 1;
                        blocks[j][38] = 1;
                        blocks[j][39] = 1;
                        blocks[j][48] = 1;
                        blocks[j][56] = 1;
                    }
                    if(j >= 20){
                        blocks[j][26] = 1;
                        blocks[j][27] = 1;
                        blocks[j][49] = 1;
                        blocks[j][55] = 1;
                    }
                    if(j >= 21){
                        blocks[j][25] = 1;
                        blocks[j][50] = 1;
                        blocks[j][51] = 1;
                        blocks[j][52] = 1;
                        blocks[j][53] = 1;
                        blocks[j][54] = 1;
                    }
                    if(j >= 22){
                        blocks[j][24] = 1;
                    }
                    if(j >= 23){
                        blocks[j][23] = 1;
                    }
                    if(j >= 24){
                        blocks[j][22] = 1;
                    }
                    if(j >= 25) {
                        blocks[j][16] = 1;
                        blocks[j][17] = 1;
                        blocks[j][18] = 1;
                        blocks[j][19] = 1;
                        blocks[j][20] = 1;
                        blocks[j][21] = 1;
                    }
                    if(j >= 26) {
                        blocks[j][14] = 1;
                        blocks[j][15] = 1;

                    }
                    if(j >= 27){
                        blocks[j][13] = 1;
                    }
                    if(j >= 28){
                        blocks[j][11] = 1;
                        blocks[j][12] = 1;
                    }
                    if(j >= 29){
                        blocks[j][9] = 1;
                        blocks[j][10] = 1;
                    }
                }
            }
            blocks[30][8] = 1;
        } else if(levelCounter == LEVEL_THREE) {
            for (int j = 0; j < blocks.length; j++) {
                for (int i = 0; i < blocks[0].length; i++) {
                    blocks[j][i] = 0;
                    blocks[30][i] = 1;
                    blocks[31][i] = 1;
                    blocks[32][i] = 1;
                    blocks[33][i] = 1;
                    blocks[34][i] = 1;
                    blocks[35][i] = 1;

                    if(j >= 11 && i < 7){
                        blocks[j][i] = 1;
                    }
                    if(j >= 12){
                        blocks[j][7] = 1;
                        blocks[j][8] = 1;
                    }
                    if(j >= 13){
                        blocks[j][9] = 1;

                    }
                    if(j >= 14){
                        blocks[j][10] = 1;

                    }
                    if(j >= 15){
                        blocks[j][11] = 1;
                    }
                    if(j >= 16){
                        blocks[j][12] = 1;
                    }
                    if(j >= 17){
                        blocks[j][13] = 1;
                        blocks[j][24] = 1;
                        blocks[j][25] = 1;
                        blocks[j][26] = 1;
                        blocks[j][27] = 1;
                        blocks[j][28] = 1;
                        blocks[j][29] = 1;
                        blocks[j][30] = 1;
                        blocks[j][31] = 1;
                        blocks[j][32] = 1;
                        blocks[j][33] = 1;
                    }
                    if(j >= 18){
                        blocks[j][14] = 1;
                        blocks[j][15] = 1;
                        blocks[j][16] = 1;
                        blocks[j][17] = 1;
                        blocks[j][18] = 1;
                        blocks[j][19] = 1;
                        blocks[j][20] = 1;
                        blocks[j][21] = 1;
                        blocks[j][22] = 1;
                        blocks[j][23] = 1;
                        blocks[j][34] = 1;
                    }
                    if(j >= 19){
                    }
                    if(j >= 20){
                        blocks[j][35] = 1;
                    }
                    if(j >= 21){
                        blocks[j][36] = 1;
                        blocks[j][72] = 1;
                        blocks[j][73] = 1;
                        blocks[j][74] = 1;
                        blocks[j][75] = 1;
                        blocks[j][76] = 1;
                        blocks[j][77] = 1;
                        blocks[j][78] = 1;
                        blocks[j][79] = 1;
                    }
                    if(j >= 22){
                        blocks[j][37] = 1;
                        blocks[j][44] = 1;
                        blocks[j][45] = 1;
                        blocks[j][46] = 1;
                        blocks[j][47] = 1;
                        blocks[j][71] = 1;
                    }
                    if(j >= 23){
                        blocks[j][38] = 1;
                        blocks[j][43] = 1;
                        blocks[j][70] = 1;
                    }
                    if(j >= 24){
                        blocks[j][39] = 1;
                        blocks[j][40] = 1;
                        blocks[j][41] = 1;
                        blocks[j][42] = 1;
                        blocks[j][48] = 1;
                        blocks[j][69] = 1;
                    }
                    if(j >= 25) {
                        blocks[j][16] = 1;
                        blocks[j][17] = 1;
                        blocks[j][18] = 1;
                        blocks[j][19] = 1;
                        blocks[j][20] = 1;
                        blocks[j][21] = 1;
                        blocks[j][49] = 1;
                        blocks[j][68] = 1;
                    }
                    if(j >= 26) {
                        blocks[j][14] = 1;
                        blocks[j][15] = 1;
                        blocks[j][50] = 1;
                        blocks[j][51] = 1;
                        blocks[j][52] = 1;
                        blocks[j][53] = 1;
                        blocks[j][67] = 1;
                    }
                    if(j >= 27){
                        blocks[j][13] = 1;
                        blocks[j][54] = 1;
                        blocks[j][66] = 1;
                    }
                    if(j >= 28){
                        blocks[j][11] = 1;
                        blocks[j][12] = 1;
                        blocks[j][55] = 1;
                    }
                    if(j >= 29){
                        blocks[j][9] = 1;
                        blocks[j][10] = 1;
                        blocks[j][56] = 1;
                        blocks[j][65] = 1;
                    }
                }
            }
        } else{
            for (int j = 0; j < blocks.length; j++) {
                for (int i = 0; i < blocks[0].length; i++) {
                    blocks[j][i] = 0;
                }
            }

        }
    }



    /*
     * Construct our game and set it running.
     */
    public Game() {
        // create a frame to contain game
        JFrame container = new JFrame("Liam's Space Invaders");

        // get hold the content of the frame
        JPanel panel = (JPanel) container.getContentPane();

        // set up the resolution of the game
        panel.setPreferredSize(new Dimension(cameraX,cameraY));
        panel.setLayout(null);

        // set up canvas size (this) and add to frame
        setBounds(0,0,worldSizeX,worldSizeY);
        panel.add(this);

        // Tell AWT not to bother repainting canvas since that will
        // be done using graphics acceleration
        setIgnoreRepaint(true);

        // make the window visible
        container.pack();
        container.setResizable(false);
        container.setVisible(true);


        // if user closes window, shutdown game and jre
        container.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            } // windowClosing
        });

        // add key listener to this canvas
        addKeyListener(new KeyInputHandler());

        // request focus so key events are handled by this canvas
        requestFocus();

        // create buffer strategy to take advantage of accelerated graphics
        createBufferStrategy(2);
        strategy = getBufferStrategy();

        // initialize entities
        initEntities();

        // start the game
        gameLoop();
    } // constructor


    /* initEntities
     * input: none
     * output: none
     * purpose: Initialise the starting state of the ship and alien entities.
     *          Each entity will be added to the array of entities in the game.
     */
    private void initEntities() {
        initializeBlocks(blocks);
        //creates the blocks
        imageBlock = (SpriteStore.get()).getSprite("sprites/block.png");
        // create the ship and put in center of screen
        ship = new ShipEntity(this, "sprites/cloak1.png", 10, 1140);

        //creates aliens
        alien = new AlienEntity(this, "sprites/warriormountain.png", 600, 918);

        alien2 = new AlienEntity(this, "sprites/warriormountain.png", 2440, 958);

        // create gems
        Entity gem = new GemEntity(this, "sprites/mountaingem.png", 840, 1020);

        // create a door
        Entity door = new DoorEntity(this, "sprites/door.png", 3000, 1038);

        if(levelCounter == START_SCREEN){
            background = (SpriteStore.get()).getSprite("sprites/startscreen.png");
        }
        if(levelCounter == LEVEL_ONE) {
            background = (SpriteStore.get()).getSprite("sprites/background.jpg");
            entities.add(ship);
            entities.add(alien);
            alienList.add(alien);
            entities.add(alien2);
            alienList.add(alien2);

            entities.add(gem);
            gemCount = 1;
            entities.add(door);
        } else if(levelCounter == MIDDLE_SCREEN1){
            background = (SpriteStore.get()).getSprite("sprites/addedgem1.png");

        }else if(levelCounter == LEVEL_TWO) {
            background = (SpriteStore.get()).getSprite("sprites/background2.jpg");
            imageBlock = (SpriteStore.get()).getSprite("sprites/forestblock.png");

            alien = new AlienEntity(this, "sprites/warriorforest.png", 750, 878);
            alien2 = new AlienEntity(this, "sprites/warriorforest.png", 1400, 638);

            entities.add(ship);
            entities.add(alien);
            alienList.add(alien);
            entities.add(alien2);
            alienList.add(alien2);

            gem = new GemEntity(this, "sprites/forestgem.png", 2520, 580);
            entities.add(gem);
            gemCount = 1;

            door = new DoorEntity(this, "sprites/door.png", 3000, 240);
            entities.add(door);

            enemyCount = 10;

        } else if(levelCounter == MIDDLE_SCREEN2){
            background = (SpriteStore.get()).getSprite("sprites/addedgem2.png");

        } else if(levelCounter == LEVEL_THREE){

            background = (SpriteStore.get()).getSprite("sprites/background3.jpg");
            imageBlock = (SpriteStore.get()).getSprite("sprites/fireblock.png");

            ship = new ShipEntity(this, "sprites/cloak1.png", 10, 340);

            alien = new AlienEntity(this, "sprites/warriorfire.png", 1100, 543);
            alien2 = new AlienEntity(this, "sprites/warriorfire.png", 2400, 1063);


            entities.add(ship);
            entities.add(alien);
            alienList.add(alien);
            entities.add(alien2);
            alienList.add(alien2);

            gem = new GemEntity(this, "sprites/firegem.png", 2520, 1180);
            entities.add(gem);
            gemCount = 1;

            door = new DoorEntity(this, "sprites/door.png", 3000, 640);
            entities.add(door);

            enemyCount = 13;

        }else if(levelCounter == END_SCREEN){
            background = (SpriteStore.get()).getSprite("sprites/storycongratulations.png");
        }
    } // initEntities

    /* Notification from a game entity that the logic of the game
     * should be run at the next opportunity
     */
    public void updateLogic() {
        logicRequiredThisLoop = true;
    } // updateLogic

    /* Remove an entity from the game.  It will no longer be
     * moved or drawn.
     */
    public void removeEntity(Entity entity) {
        removeEntities.add(entity);
    } // removeEntity

    /* Notification that the player has died.
     */
    public void notifyDeath() {
        message = "You have lost! Do you want to play again?";
        waitingForKeyPress = true;
    } // notifyDeath


    /* Notification that the play has killed all aliens
     */
    public void notifyWin(){
        if(!levelWin) {
            message = "You kicked some ALIEN BUTT!  You win!";
            blockCounter = 0;
            levelCounter++;
            if(levelCounter == MIDDLE_SCREEN1){
                message = "";
                initEntities();
            }
            if(levelCounter == MIDDLE_SCREEN2){
                message = "";
                initEntities();
            }
            if(levelCounter == END_SCREEN){
                message = "";
                initEntities();
            }
            if (levelCounter > END_SCREEN) {
                levelCounter = LEVEL_ONE;
            }
            waitingForKeyPress = true;
            levelWin = true;
        }
    } // notifyWin

    /* Notification than an alien has been killed
     */
    public void notifyGemKilled() {
        gemCount--;
    } // notifyAlienKilled

    /* Attempt to fire.*/
    public void tryToFire() {
        // check that we've waited long enough to fire
        if ((System.currentTimeMillis() - lastFire) < firingInterval){
            return;
        } // if

        // otherwise add a shot
        lastFire = System.currentTimeMillis();
        ShotEntity shot = new ShotEntity(this, "sprites/sword.png",
                ship.getX() + 10, ship.getY() + 50);
        entities.add(shot);
    } // tryToFire

    // animate ship walking
    public void walk() {
        if ((System.currentTimeMillis() - lastWalk) > walkingInterval) {
            ship.setGif(walkingCount);
            walkingCount++;
            if (walkingCount > 2) {
                walkingCount = 0;
            } // if
            lastWalk = System.currentTimeMillis();
        } // if
    } // walk

    // animate ship picking up gem
    public void pick() {
        if ((System.currentTimeMillis() - lastPick) > pickInterval) {
            ship.setGif(pickCount);
            lastPick = System.currentTimeMillis();
            pickCount++;
            if (pickCount > 6) {
                pickCount = 3;
                downPressed = false;
            } // if
        } // if
    } // pick

    /*
     * gameLoop
     * input: none
     * output: none
     * purpose: Main game loop. Runs throughout game play.
     *          Responsible for the following activities:
     *           - calculates speed of the game loop to update moves
     *           - moves the game entities
     *           - draws the screen contents (entities, text)
     *           - updates game events
     *           - checks input
     */
    public void gameLoop() {
        long lastLoopTime = System.currentTimeMillis();

        // keep loop running until game ends
        while (gameRunning) {
            // calc. time since last update, will be used to calculate
            // entities movement
            long delta = System.currentTimeMillis() - lastLoopTime;
            lastLoopTime = System.currentTimeMillis();

            // camera set with player in the middle of screen
            if(levelCounter == START_SCREEN || levelCounter == STORY_SCREEN || levelCounter == MIDDLE_SCREEN1 || levelCounter == MIDDLE_SCREEN2) {
                camX = 0;
                camY = 0;
            } else{
                camX = (int) ship.x - cameraX / 2;
                camY = (int) ship.y - cameraY / 2;
            }

            //stops scrolling at either ends of screen
            if(camX > offsetMaxX){
                camX = offsetMaxX;
            }else if(camX < offsetMinX){
                camX = offsetMinX;
            }

            //stops scrolling at top and bottom of screen
            if(camY > offsetMaxY){
                camY = offsetMaxY;
            }else if(camY < offsetMinY){
                camY = offsetMinY;
            }

            // get graphics context for the accelerated surface and make it black
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.translate(-camX, -camY);
            if(levelCounter == STORY_SCREEN && storyPlay){
                if ((System.currentTimeMillis() - lastStory) > storyTime) {
                    background = (SpriteStore.get()).getSprite(storyBoard[storyBoardCount]);
                    background.drawBackground(g, camX, camY, 1250, 720);
                    lastStory = System.currentTimeMillis();
                    storyBoardCount++;
                    if (storyBoardCount > 6) {
                        storyPlay = false;
                    } // if
                } // if
            }else if((levelCounter == STORY_SCREEN && !storyPlay) || levelCounter == START_SCREEN
                    || levelCounter == MIDDLE_SCREEN1 || levelCounter == MIDDLE_SCREEN2 || levelCounter == END_SCREEN){
                background.drawBackground(g, camX, camY, 1250, 720);
            }else{
                background.drawBackground(g, camX, camY, 1380, 820);
            }
            // move each entity
            if (!waitingForKeyPress) {
                for (int i = 0; i < entities.size(); i++) {
                    Entity entity = (Entity) entities.get(i);
                    entity.move(delta);
                } // for
            } // if

            // draw all entities
            for (int i = 0; i < entities.size(); i++) {
                Entity entity = (Entity) entities.get(i);
                entity.draw(g);
            } // for


            //draws all blocks
            for(int j = 0; j < blocks.length; j++){
                for(int i = 0; i < blocks[0].length; i++){
                    if(blocks[j][i] == 1){
                        imageBlock.draw(g, i*40, j*40);
                    }
                }
            }

            // brute force collisions, compare every entity
            // against every other entity.  If any collisions
            // are detected notify both entities that it has
            // occurred
            for (int i = 0; i < entities.size(); i++) {
                for (int j = i + 1; j < entities.size(); j++) {
                    Entity me = (Entity)entities.get(i);
                    Entity him = (Entity)entities.get(j);

                    if (me.collidesWith(him)) {
                        me.collidedWith(him);
                        him.collidedWith(me);
                    } // if
                } // inner for
            } // outer for

            // remove dead entities
            entities.removeAll(removeEntities);
            removeEntities.clear();

            // run logic if required
            if (logicRequiredThisLoop) {
                for (int i = 0; i < entities.size(); i++) {
                    Entity entity = (Entity) entities.get(i);
                    entity.doLogic();
                } // for
                logicRequiredThisLoop = false;
            } // if

            // if waiting for "any key press", draw message
            if (waitingForKeyPress) {
                g.setColor(Color.white);
                if(levelCounter != START_SCREEN && levelCounter != STORY_SCREEN && levelCounter != MIDDLE_SCREEN1 && levelCounter != MIDDLE_SCREEN2 && levelCounter != END_SCREEN){
                    g.drawString(message, camX + (cameraX - g.getFontMetrics().stringWidth(message))/2, camY + cameraY/2 - 50);
                    g.drawString("Press any key", camX + (cameraX - g.getFontMetrics().stringWidth("Press any key")) / 2, camY + cameraY/2 - 25);
                }
            } else{

                //animation of enemy for first level
                if(levelCounter == LEVEL_ONE) {
                    // animate enemy walking
                    for (int i = 0; i < alienList.size(); i++) {
                        Entity aliens = (Entity) alienList.get(i);
                        aliens.setGif(enemyCount);
                        enemyCount++;
                        lastEnemyWalk = System.currentTimeMillis();
                        if (enemyCount > 9) {
                            enemyCount = 7;
                        } // if
                    }// for
                }

                //animation of enemy for second level
                if(levelCounter == LEVEL_TWO) {
                    // animate enemy walking
                    for (int i = 0; i < alienList.size(); i++) {
                        Entity aliens = (Entity) alienList.get(i);
                        aliens.setGif(enemyCount);
                        enemyCount++;
                        lastEnemyWalk = System.currentTimeMillis();
                        if (enemyCount > 12) {
                            enemyCount = 10;
                        } // if
                    }// for
                }

                //animation of enemy for third level
                if(levelCounter == LEVEL_THREE) {
                    // animate enemy walking
                    for (int i = 0; i < alienList.size(); i++) {
                        Entity aliens = (Entity) alienList.get(i);
                        aliens.setGif(enemyCount);
                        enemyCount++;
                        lastEnemyWalk = System.currentTimeMillis();
                        if (enemyCount > 15) {
                            enemyCount = 13;
                        } // if
                    }// for
                }
            } //else

            // clear graphics and flip buffer
            g.dispose();
            strategy.show();

            // ship should not move without user input
            if(levelCounter != START_SCREEN  && levelCounter != STORY_SCREEN && levelCounter!= MIDDLE_SCREEN1 && levelCounter != MIDDLE_SCREEN2){
                ship.setHorizontalMovement(0);
                if (isFalling) {
                    ship.setVerticalMovement(gravity);
                }
                // respond to user moving ship
                if ((leftPressed) && (!rightPressed)) {
                    ship.setHorizontalMovement(-moveSpeed);
                    walk();
                } else if ((rightPressed) && (!leftPressed)) {
                    ship.setHorizontalMovement(moveSpeed);
                    walk();
                } // else

                //Sets boundaries for the enemies to walk in for first level
                if(levelCounter == LEVEL_ONE) {
                    if (alien.x <= 560) {
                        alien.dx = -alien.dx;
                    }

                    if (alien.x > 800) {
                        alien.dx = -alien.dx;
                    }

                    if (alien2.x <= 2240) {
                        alien2.dx = -alien2.dx;
                    }

                    if (alien2.x > 2438) {
                        alien2.dx = -alien2.dx;
                    }
                } //if

                //Sets boundaries for the enemies to walk in for second level
                if(levelCounter == LEVEL_TWO){
                    if (alien.x <= 640) {
                        alien.dx = -alien.dx;
                    }

                    if (alien.x > 800) {
                        alien.dx = -alien.dx;
                    }

                    if (alien2.x <= 1320) {
                        alien2.dx = -alien2.dx;
                    }

                    if (alien2.x > 1460) {
                        alien2.dx = -alien2.dx;
                    }
                } //if

                //Sets boundaries for the enemies to walk in for third level
                if(levelCounter == LEVEL_THREE){
                    if (alien.x < 960) {
                        alien.dx = -alien.dx;
                    }

                    if (alien.x > 1220) {
                        alien.dx = -alien.dx;
                    }

                    if (alien2.x <= 2280) {
                        alien2.dx = -alien2.dx;
                    }

                    if (alien2.x > 2500) {
                        alien2.dx = -alien2.dx;
                    }
                } //if

                //respond to user pressing up
                if (upPressed && !isFalling) {
                    ship.y -= 100;
                }

                //respond to user pressing down
                if (downPressed) {
                    pick();
                } // if

                // sets falling to true always unless on a block
                isFalling = true;


                // if spacebar pressed, try to fire
                if (firePressed) {
                    tryToFire();
                } // if
            }
            // pause
            try { Thread.sleep(100); } catch (Exception e) {}

        } // while

    } // gameLoop


    /* startGame
     * input: none
     * output: none
     * purpose: start a fresh game, clear old data
     */
    private void startGame() {
        // clear out any existing entities and initalize a new set
        entities.clear();
        alienList.clear();

        //clears all block locations
        for(int i = 0; i < blockCounter; i++){
            blockY[i] = 0;
            blockX[i] = 0;
        }

        //resets the number of blocks to 0
        blockCounter = 0;

        levelWin = false;
        initEntities();

        //stores the locations of the blocks
        for(int j = 0; j < blocks.length; j++){
            for(int i = 0; i < blocks[0].length; i++){
                if(blocks[j][i] == 1){
                    blockY[blockCounter] = j*40;
                    blockX[blockCounter] = i*40;
                    blockCounter++;
                }
            }
        }

        // blank out any keyboard settings that might exist
        leftPressed = false;
        upPressed = false;
        downPressed = false;
        rightPressed = false;
        firePressed = false;
        levelWin = false;
    } // startGame


    /* inner class KeyInputHandler
     * handles keyboard input from the user
     */
    private class KeyInputHandler extends KeyAdapter {

        private int pressCount = 0;  // the number of key presses since
        // waiting for 'any' key press

        /* The following methods are required
         * for any class that extends the abstract
         * class KeyAdapter.  They handle keyPressed,
         * keyReleased and keyTyped events.
         */
        public void keyPressed(KeyEvent e) {

            // if waiting for keypress to start game, do nothing
            if (waitingForKeyPress) {
                return;
            } // if

            // respond to move left, right or fire
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            } // if

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            } // if

            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = true;
            } // if

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            } // if

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                firePressed = true;
            } // if

        } // keyPressed

        public void keyReleased(KeyEvent e) {
            // if waiting for keypress to start game, do nothing
            if (waitingForKeyPress) {
                return;
            } // if

            // respond to move left, right or fire
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            } // if

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            } // if

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = false;
            } // if

            /*if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = false;
            } // if*/

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                firePressed = false;
            } // if

        } // keyReleased



        public void keyTyped(KeyEvent e) {

            // if waiting for key press to start game
            if (waitingForKeyPress) {
                if (pressCount == 0) {
                    waitingForKeyPress = false;

                    //goes to next level if waiting for key press
                    if(levelCounter == START_SCREEN){
                        waitingForKeyPress = true;
                        levelCounter = STORY_SCREEN;
                    }else if(levelCounter == STORY_SCREEN){
                        levelCounter = LEVEL_ONE;
                    }else if(levelCounter == MIDDLE_SCREEN1){
                        levelCounter = LEVEL_TWO;
                    }else if(levelCounter == MIDDLE_SCREEN2){
                        levelCounter = LEVEL_THREE;
                    }else if(levelCounter == END_SCREEN){
                        levelCounter = LEVEL_ONE;
                    }
                    startGame();
                    pressCount = 0;
                } else {
                    pressCount++;
                } // else
            } // if waitingForKeyPress

            // if escape is pressed, end game
            if (e.getKeyChar() == 27) {
                System.exit(0);
            } // if escape pressed

        } // keyTyped

    } // class KeyInputHandler

    public boolean getDownPressed() {
        return downPressed;
    } // getDownPressed

    public int getGemCount() {
        return gemCount;
    } // getGemCount

    public int[][] getBlock(){
        return blocks;
    }

    public int[] getBlockY(){
        return blockY;
    }

    public int[] getBlockX(){
        return blockX;
    }

    public int getBlockCounter(){
        return blockCounter;
    }

    public void setIsFalling(boolean x){
        isFalling = x;
    }

    /**
     * Main Program
     */
    public static void main(String [] args) {
        // instantiate this object
        new Game();
    } // main
} // Game