package app.drawable.levels;

import app.Platformer;
import app.constants.Constants;
import app.drawable.IDrawable;
import app.drawable.IKeyControllable;
import app.drawable.boundaries.VerticalBoundary;
import app.drawable.characters.Player;
import app.drawable.collectables.LevelGoal;
import app.drawable.menus.PauseMenu;
import app.drawable.viewbox.ViewBox;
import app.enums.EProcessingMethods;
import app.enums.ESongType;
import app.utils.ReservedControlUtils;
import app.utils.ResourceUtils;
import processing.event.KeyEvent;

/**
 * common for levels
 */
public abstract class ALevel implements IDrawable, IKeyControllable {

    // main sketch
    final Platformer mainSketch;

    // player-controllable character
    Player player;

    // level viewbox
    ViewBox viewBox;

    // drawables in this
    final LevelDrawableCollection levelDrawableCollection;

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
    ALevel(Platformer mainSketch, boolean initAsActive, boolean loadPlayerFromCheckPoint, int goalRightSideOffsetWithStageWidth) {

        this.mainSketch = mainSketch;

        this.levelDrawableCollection = new LevelDrawableCollection();

        this.isPaused = false;

        this.loadPlayerFromCheckPoint = loadPlayerFromCheckPoint;

        this.isHandlingLevelComplete = false;

        if (initAsActive) {
            this.setUpActivateLevel();
            this.setUpActivateWallsGoal(goalRightSideOffsetWithStageWidth);
        }
    }

    /**
     * active and add this to game
     */
    void makeActive() {
        this.mainSketch.registerMethod(EProcessingMethods.KEY_EVENT.toString(), this);   // connect this keyEvent() from main keyEvent()
        this.mainSketch.registerMethod(EProcessingMethods.DRAW.toString(), this); // connect this draw() from main draw()
    }

    /**
     * setup and activate this; to override in extended classes
     */
    abstract void setUpActivateLevel();

    /**
     * deactivate this;
     */
    public void deactivateLevel() {
        if (this.player != null) {
            this.player.makeNotActive();
        }

        this.viewBox.makeNotActive();

        this.levelDrawableCollection.deactivateClearAllDrawable();

        // make this not active
        this.mainSketch.unregisterMethod(EProcessingMethods.KEY_EVENT.toString(), this);   // connect this keyEvent() from main keyEvent()
        this.mainSketch.unregisterMethod(EProcessingMethods.DRAW.toString(), this); // disconnect this draw() from main draw()
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
        this.drawBackground();
    }

    /**
     * handle character keypress controls
     */
    @Override
    public void keyEvent(KeyEvent keyEvent) {
        if (this.player != null && !this.isHandlingLevelComplete) {  // only allow pause if player is active
            // press 'p' for pause
            if (keyEvent.getAction() == KeyEvent.PRESS) {
                String keyPressed = keyEvent.getKey() + "";

                if (ReservedControlUtils.EReservedControlKeys.p.toString().equalsIgnoreCase(keyPressed)) {   // pause
                    this.isPaused = !this.isPaused;

                    if (this.isPaused) {
                        ResourceUtils.stopSong();
                        this.mainSketch.noLoop();
                        this.pauseMenu = new PauseMenu(
                            this.mainSketch,
                            (int) this.viewBox.getPos().x,
                            true);

                    } else {
                        ResourceUtils.loopSong(ESongType.LEVEL);
                        this.mainSketch.loop();
                        this.closePauseMenu();
                    }
                }
            }
        }
    }

    /**
     * draw background at proper location
     */
    void drawBackground() {
        // for scrolling background
        this.mainSketch.translate(-this.viewBox.getPos().x, -this.viewBox.getPos().y);

        int levelWidthLeftToDraw = this.mainSketch.getCurrentActiveLevelWidth();
        int numberHorizontalBackgroundIterations =
            (int) Math.ceil((double) this.mainSketch.getCurrentActiveLevelWidth() / Constants.SCREEN_WIDTH);
        for (int curHorizontalItr = 0; curHorizontalItr < numberHorizontalBackgroundIterations; curHorizontalItr++) {
            int curIterationWidthToDraw =
                Math.min(
                    Constants.SCREEN_WIDTH,
                    levelWidthLeftToDraw);

            // lazy load level background
            final int curItrLeftX = curHorizontalItr * Constants.SCREEN_WIDTH;
            final boolean viewBoxInCurXRange =
                (curItrLeftX <= this.viewBox.getPos().x
                    && curItrLeftX + Constants.SCREEN_WIDTH >= this.viewBox.getPos().x)
                    || (curItrLeftX <= this.viewBox.getPos().x + Constants.SCREEN_WIDTH
                    && curItrLeftX + Constants.SCREEN_WIDTH >= this.viewBox.getPos().x + Constants.SCREEN_WIDTH);

            if (viewBoxInCurXRange) {
                int levelHeightLeftToDraw = this.mainSketch.getCurrentActiveLevelHeight();
                int numberVerticalBackgroundIterations =
                    (int) Math.ceil((double) this.mainSketch.getCurrentActiveLevelHeight() / Constants.SCREEN_HEIGHT);

                for (int curVerticalItr = 0; curVerticalItr < numberVerticalBackgroundIterations; curVerticalItr++) {
                    int curIterationHeightToDraw =
                        Math.min(
                            Constants.SCREEN_HEIGHT,
                            levelHeightLeftToDraw);

                    int startYPosToDraw =
                        -curVerticalItr * Constants.SCREEN_HEIGHT
                            + (Constants.SCREEN_HEIGHT - curIterationHeightToDraw);

                    this.mainSketch.image(
                        ResourceUtils.LEVEL_BACKGROUND_IMAGE,
                        (curHorizontalItr * Constants.SCREEN_WIDTH), // start x pos
                        startYPosToDraw,  // start y pos
                        curIterationWidthToDraw,
                        curIterationHeightToDraw,

                        0,
                        0,
                        curIterationWidthToDraw,
                        curIterationHeightToDraw);

                    levelHeightLeftToDraw -= curIterationHeightToDraw;
                }
            }
            levelWidthLeftToDraw -= curIterationWidthToDraw;
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
        this.levelDrawableCollection.addDrawable(new LevelGoal(
            this.mainSketch,
            this.mainSketch.getCurrentActiveLevelWidth() - Constants.LEVEL_GOAL_WIDTH - goalRightSideOffsetWithStageWidth,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.LEVEL_GOAL_HEIGHT,
            Constants.LEVEL_GOAL_WIDTH,
            Constants.LEVEL_GOAL_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true)
        );

        // stage right and left walls
        this.levelDrawableCollection.addDrawable(new VerticalBoundary(
            this.mainSketch,
            0,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));

        this.levelDrawableCollection.addDrawable(new VerticalBoundary(
            this.mainSketch,
            this.mainSketch.getCurrentActiveLevelWidth(),
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));
    }

    /*** getters and setters ***/
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ViewBox getViewBox() {
        return viewBox;
    }

    public LevelDrawableCollection getLevelDrawableCollection() {
        return levelDrawableCollection;
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
