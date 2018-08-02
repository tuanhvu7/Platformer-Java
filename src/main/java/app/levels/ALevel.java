package app.levels;

import app.Platformer;
import app.blocks.ABlock;
import app.boundaries.ABoundary;
import app.boundaries.HorizontalBoundary;
import app.boundaries.VerticalBoundary;
import app.characters.ACharacter;
import app.characters.Player;
import app.collectables.ACollectable;
import app.constants.Constants;
import app.enums.ESongType;
import app.utils.ResourceUtils;
import processing.event.KeyEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * common for levels
 */
public abstract class ALevel {

    // main sketch
    protected Platformer mainSketch;

    // true means this is active
    protected boolean isActive;

    // player-controllable character
    protected Player player;

    // level viewbox
    protected ViewBox viewBox;

    // set of all non-playable characters in level
    protected Set<ACharacter> charactersList;

    // set of all boundaries in level
    protected Set<ABoundary> boundariesList;

    // set of all blocks in level
    protected Set<ABlock> blocksList;

    // set of all collectables in level
    protected Set<ACollectable> collectablesList;

    // pause menu for level
    private PauseMenu pauseMenu;

    // true means level is paused and menu appears
    protected boolean isPaused;

    // checkpoint x position
    protected int checkpointXPos;

    // true means load player at checkpoint position
    protected boolean loadPlayerFromCheckPoint;

    // true means running handleLevelComplete thread
    protected boolean isHandlingLevelComplete;

    /**
     * sets properties of this
     */
    public ALevel(Platformer mainSketch, boolean isActive, boolean loadPlayerFromCheckPoint) {

        this.mainSketch = mainSketch;

        this.charactersList = new HashSet<ACharacter>();
        this.boundariesList = new HashSet<ABoundary>();
        this.blocksList = new HashSet<ABlock>();
        this.collectablesList = new HashSet<ACollectable>();

        this.isPaused = false;

        this.loadPlayerFromCheckPoint = loadPlayerFromCheckPoint;

        this.isHandlingLevelComplete = false;

        if(isActive) {
            this.setUpActivateLevel();
            this.setUpActivateFloorWallsGoal();
        }
    }

    /**
     * active and add this to game
     */
    public void makeActive() {
        this.isActive = true;
        this.mainSketch.registerMethod("keyEvent", this);   // connect this keyEvent() from main keyEvent()
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()
    }

    /**
     * setup and activate this; to override in extended classes
     */
    public void setUpActivateLevel() { }

    /**
     * handle conditional enemy triggers in this;
     * to override in extended classes if needed
     */
    public void handleConditionalEnemyTriggers() { }


    /**
     * deactiviate this
     */
    public void deactivateLevel() {
        this.viewBox.makeNotActive();

        for(ACharacter curCharacter : this.charactersList) {
            curCharacter.makeNotActive();
        }

        for(ABoundary curBoundary : this.boundariesList) {
            curBoundary.makeNotActive();
        }

        for(ABlock curBlock : this.blocksList) {
            curBlock.makeNotActive();
        }

        for(ACollectable curCollectable: this.collectablesList) {
            curCollectable.makeNotActive();
        }

        this.charactersList.clear();
        this.boundariesList.clear();
        this.blocksList.clear();
        this.collectablesList.clear();

        // make this not active
        this.isActive = false;
        this.mainSketch.unregisterMethod("keyEvent", this);   // connect this keyEvent() from main keyEvent()
        this.mainSketch.unregisterMethod("draw", this); // disconnect this draw() from main draw()
    }

    /**
     * close pause menu
     */
    public void closePauseMenu() {
        this.pauseMenu.deactivateMenu();
    }

    /**
     * runs continuously
     */
    public void draw() {
        // draw background image horizontally until level width is filled
        int levelWidthLeftToDraw = this.mainSketch.getCurrentActiveLevelWidth();
        int numberHorizontalBackgroundIterations =
            (int) Math.ceil( (double) this.mainSketch.getCurrentActiveLevelWidth() / global_background_image.width);

        for(int i = 0; i < numberHorizontalBackgroundIterations; i++) {
            int widthToDraw =
                Math.min(
                    ResourceUtils.STAGE_BACKGROUND.width,
                    levelWidthLeftToDraw);

            this.mainSketch.image(
                ResourceUtils.STAGE_BACKGROUND,
                i * ResourceUtils.STAGE_BACKGROUND.width,
                0,
                widthToDraw,
                ResourceUtils.STAGE_BACKGROUND.height);

            levelWidthLeftToDraw -= widthToDraw;
        }

        this.handleConditionalEnemyTriggers();
    }

    /**
     * handle character keypress controls
     */
    private void keyEvent(KeyEvent keyEvent) {
        if(this.player.isActive && !this.isHandlingLevelComplete) {  // only allow pause if player is active // TODO: encapsulate
            // press 'p' for pause
            if(keyEvent.getAction() == KeyEvent.PRESS) {
                char keyPressed = keyEvent.getKey();

                if(keyPressed == 'p') {   // pause
                    this.isPaused = !this.isPaused;

                    if(this.isPaused) {
                        ResourceUtils.stopSong();
                        this.mainSketch.noLoop();
                        this.pauseMenu = new PauseMenu(
                            (int) this.viewBox.pos.x, // TODO: encapsulate
                            true);

                    } else {
                        ResourceUtils.loopSong(ESongType.Level);
                        this.mainSketch.loop();
                        this.closePauseMenu();
                    }
                }
            }
        }
    }

    /**
     * setup activate floor, walls, and goal
     */
    protected void setUpActivateFloorWallsGoal() {
        // stage goal
        this.collectablesList.add(new LevelGoal(
            this.mainSketch.getCurrentActiveLevelWidth() - Constants.LEVEL_GOAL_BLOCK_WIDTH - 10,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.LEVEL_GOAL_BLOCK_HEIGHT,
            Constants.LEVEL_GOAL_BLOCK_WIDTH,
            Constants.LEVEL_GOAL_BLOCK_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            this.isActive)
        );


        // stage floor
        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            this.mainSketch.getCurrentActiveLevelWidth(),
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        // stage right and left walls
        this.boundariesList.add(new VerticalBoundary(
            this.mainSketch,
            0,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            this.isActive
        ));

        this.boundariesList.add(new VerticalBoundary(
            this.mainSketch,
            this.mainSketch.getCurrentActiveLevelWidth(),
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            this.isActive
        ));
    }
}

