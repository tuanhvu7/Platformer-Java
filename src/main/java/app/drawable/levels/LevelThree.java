package app.drawable.levels;

import app.Platformer;
import app.constants.Constants;
import app.drawable.boundaries.HorizontalBoundary;
import app.drawable.boundaries.VerticalBoundary;
import app.drawable.characters.FlyingEnemy;
import app.drawable.characters.Player;
import app.drawable.collectables.LevelGoal;
import app.drawable.viewbox.ViewBox;
import app.enums.ESongType;
import app.utils.ResourceUtils;

public class LevelThree extends ALevel {

    /**
     * sets properties, boundaries, and characters of this
     */
    public LevelThree(Platformer mainSketch, boolean isActive, boolean loadPlayerFromCheckPoint) {
        super(mainSketch, isActive, loadPlayerFromCheckPoint, Constants.BIG_ENEMY_DIAMETER + 200);
    }

    @Override
    void setUpActivateLevel() {
        this.makeActive();
        ResourceUtils.loopSong(ESongType.LEVEL);

        final int playerStartXPos = Constants.SCREEN_WIDTH / 6;
        final int playerStartYPos = Constants.SCREEN_HEIGHT / 4;

        this.viewBox = new ViewBox(
            this.mainSketch,
            0,
            playerStartYPos,
            true);
        this.player = new Player(
            this.mainSketch,
            playerStartXPos,
            playerStartYPos,
            Constants.PLAYER_DIAMETER,
            true);

        this.setupActivateBeforeCheckpoint(playerStartXPos, playerStartYPos);
    }

    private void setupActivateBeforeCheckpoint(final int playerStartXPos, final int playerStartYPos) {
        // extend left wall to bottom of level
        this.levelDrawableCollection.addDrawable(new VerticalBoundary(
            this.mainSketch,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            this.mainSketch.getCurrentActiveLevelHeight() - Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));


        this.levelDrawableCollection.addDrawable(new LevelGoal(
            this.mainSketch,
            playerStartXPos,
            playerStartYPos + 2 * Constants.PLAYER_DIAMETER,
            Constants.CHECKPOINT_WIDTH,
            Constants.SCREEN_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));

        this.levelDrawableCollection.addDrawable(new VerticalBoundary(
            this.mainSketch,
            2 * playerStartXPos,
            playerStartYPos,
            this.mainSketch.getCurrentActiveLevelHeight() - (playerStartYPos / 2),
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));

        this.levelDrawableCollection.addDrawable(new FlyingEnemy(
            this.mainSketch,
            2 * playerStartXPos,
            Constants.SMALL_ENEMY_DIAMETER,
            Constants.SMALL_ENEMY_DIAMETER,
            0,
            Constants.ENEMY_FAST_MOVEMENT_SPEED,
            false,
            false,
            true,
            true,
            true
        ));

        this.levelDrawableCollection.addDrawable(new VerticalBoundary(
            this.mainSketch,
            4 * playerStartXPos,
            playerStartYPos,
            this.mainSketch.getCurrentActiveLevelHeight() - (playerStartYPos / 2),
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));

        this.levelDrawableCollection.addDrawable(new HorizontalBoundary(
            this.mainSketch,
            4 * playerStartXPos,
            this.mainSketch.getCurrentActiveLevelHeight(),
            1000,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            true
        ));

        for (int i = 0; i < 10; i++) {
            this.levelDrawableCollection.addDrawable(new FlyingEnemy(
                this.mainSketch,
                5 * playerStartXPos,
                (Constants.SMALL_ENEMY_DIAMETER / 2) + (Constants.SMALL_ENEMY_DIAMETER * i),
                Constants.SMALL_ENEMY_DIAMETER,
                0,
                Constants.ENEMY_FAST_MOVEMENT_SPEED,
                false,
                false,
                true,
                true,
                true
            ));
        }

        for (int i = 0; i < 10; i++) {
            this.levelDrawableCollection.addDrawable(new FlyingEnemy(
                this.mainSketch,
                5 * playerStartXPos,
                (this.mainSketch.getCurrentActiveLevelHeight() - (Constants.SMALL_ENEMY_DIAMETER / 2)) - (Constants.SMALL_ENEMY_DIAMETER * i),
                Constants.SMALL_ENEMY_DIAMETER,
                0,
                -Constants.ENEMY_FAST_MOVEMENT_SPEED,
                true,
                false,
                true,
                true,
                true
            ));
        }

        this.levelDrawableCollection.addDrawable(new VerticalBoundary(
            this.mainSketch,
            6 * playerStartXPos,
            playerStartYPos,
            this.mainSketch.getCurrentActiveLevelHeight() - (playerStartYPos / 2),
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));
    }

}
