package app.drawable.levels;

import app.Platformer;
import app.constants.Constants;
import app.drawable.blocks.ABlock;
import app.drawable.boundaries.ABoundary;
import app.drawable.boundaries.VerticalBoundary;
import app.drawable.characters.ACharacter;
import app.drawable.characters.Player;
import app.drawable.collectables.ACollectable;
import app.drawable.collectables.LevelGoal;
import app.drawable.interfaces.IDrawable;
import app.drawable.menus.PauseMenu;
import app.drawable.viewbox.ViewBox;
import app.enums.ESongType;
import app.utils.ResourceUtils;
import processing.event.KeyEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * common for levels
 */
public abstract class ALevel implements IDrawable {

    // main sketch
    final Platformer mainSketch;

    // true means this is active
    boolean isActive;

    // player-controllable character
    Player player;

    // level viewbox
    ViewBox viewBox;

    // set of all non-playable characters in level
    final Set<ACharacter> charactersList;

    // set of all boundaries in level
    final Set<ABoundary> boundariesList;

    // set of all blocks in level
    final Set<ABlock> blocksList;

    // set of all collectables in level
    final Set<ACollectable> collectablesList;

    // pause menu for level
    private PauseMenu pauseMenu;

    // true means level is paused and menu appears
    private boolean isPaused;

    // checkpoint x position
    int checkpointXPos;

    // true means load player at checkpoint position
    boolean loadPlayerFromCheckPoint;

    // true means running handleLevelComplete thread
    private boolean isHandlingLevelComplete;

    /**
     * sets properties of this
     */
    ALevel(Platformer mainSketch, boolean isActive, boolean loadPlayerFromCheckPoint, int goalRightSideOffsetWithStageWidth) {

        this.mainSketch = mainSketch;

        this.charactersList = new HashSet<>();
        this.boundariesList = new HashSet<>();
        this.blocksList = new HashSet<>();
        this.collectablesList = new HashSet<>();

        this.isPaused = false;

        this.loadPlayerFromCheckPoint = loadPlayerFromCheckPoint;

        this.isHandlingLevelComplete = false;

        if (isActive) {
            this.setUpActivateLevel();
            this.setUpActivateWallsGoal(goalRightSideOffsetWithStageWidth);
        }
    }

    /**
     * active and add this to game
     */
    void makeActive() {
        this.isActive = true;
        this.mainSketch.registerMethod("keyEvent", this);   // connect this keyEvent() from main keyEvent()
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()
    }

    /**
     * setup and activate this; to override in extended classes
     */
    void setUpActivateLevel() {
    }

    /**
     * handle conditional enemy triggers in this;
     * to override in extended classes if needed
     */
    void handleConditionalEnemyTriggers() {
    }


    /**
     * deactivate this;
     */
    public void deactivateLevel() {
        this.player.makeNotActive();

        this.viewBox.makeNotActive();

        for (ACharacter curCharacter : this.charactersList) {
            curCharacter.makeNotActive();
        }

        for (ABoundary curBoundary : this.boundariesList) {
            curBoundary.makeNotActive();
        }

        for (ABlock curBlock : this.blocksList) {
            curBlock.makeNotActive();
        }

        for (ACollectable curCollectable : this.collectablesList) {
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
    @Override
    public void draw() {
        // draw background image horizontally until level width is filled
        int levelWidthLeftToDraw = this.mainSketch.getCurrentActiveLevelWidth();
        int numberHorizontalBackgroundIterations =
            (int) Math.ceil((double) this.mainSketch.getCurrentActiveLevelWidth() / ResourceUtils.LEVEL_BACKGROUND_IMAGE.width);

        for (int i = 0; i < numberHorizontalBackgroundIterations; i++) {
            int widthToDraw =
                Math.min(
                    ResourceUtils.LEVEL_BACKGROUND_IMAGE.width,
                    levelWidthLeftToDraw);

            this.mainSketch.image(
                ResourceUtils.LEVEL_BACKGROUND_IMAGE,
                i * ResourceUtils.LEVEL_BACKGROUND_IMAGE.width,
                0,
                widthToDraw,
                ResourceUtils.LEVEL_BACKGROUND_IMAGE.height);

            levelWidthLeftToDraw -= widthToDraw;
        }

        this.handleConditionalEnemyTriggers();
    }

    /**
     * handle character keypress controls
     */
    public void keyEvent(KeyEvent keyEvent) {
        if (this.player.isActive() && !this.isHandlingLevelComplete) {  // only allow pause if player is active
            // press 'p' for pause
            if (keyEvent.getAction() == KeyEvent.PRESS) {
                char keyPressed = keyEvent.getKey();

                if (keyPressed == 'p') {   // pause
                    this.isPaused = !this.isPaused;

                    if (this.isPaused) {
                        ResourceUtils.stopSong();
                        this.mainSketch.noLoop();
                        this.pauseMenu = new PauseMenu(
                            this.mainSketch,
                            (int) this.viewBox.getPos().x,
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
     * setup activate walls, and goal
     *
     * @param goalRightSideOffsetWithStageWidth offset of goal's right side relative to stage width
     *                                          (example: 50 means goal is 50 pixels less than stage width
     */
    private void setUpActivateWallsGoal(int goalRightSideOffsetWithStageWidth) {
        // stage goal
        this.collectablesList.add(new LevelGoal(
            this.mainSketch,
            this.mainSketch.getCurrentActiveLevelWidth() - Constants.LEVEL_GOAL_WIDTH - goalRightSideOffsetWithStageWidth,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.LEVEL_GOAL_HEIGHT,
            Constants.LEVEL_GOAL_WIDTH,
            Constants.LEVEL_GOAL_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            this.isActive)
        );

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

    /*** getters and setters ***/
    public Player getPlayer() {
        return player;
    }

    public ViewBox getViewBox() {
        return viewBox;
    }

    public Set<ACharacter> getCharactersList() {
        return charactersList;
    }

    public Set<ABlock> getBlocksList() {
        return blocksList;
    }

    public Set<ACollectable> getCollectablesList() {
        return collectablesList;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isLoadPlayerFromCheckPoint() {
        return loadPlayerFromCheckPoint;
    }

    public void setLoadPlayerFromCheckPoint(boolean loadPlayerFromCheckPoint) {
        this.loadPlayerFromCheckPoint = loadPlayerFromCheckPoint;
    }

    public boolean isHandlingLevelComplete() {
        return isHandlingLevelComplete;
    }

    public void setHandlingLevelComplete(boolean handlingLevelComplete) {
        isHandlingLevelComplete = handlingLevelComplete;
    }
}

