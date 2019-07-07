package app.drawable.levels;

import app.Platformer;
import app.constants.Constants;
import app.drawable.blocks.EventBlock;
import app.drawable.blocks.ItemBlock;
import app.drawable.boundaries.EnemyTriggerVerticalBoundary;
import app.drawable.boundaries.HorizontalBoundary;
import app.drawable.boundaries.VerticalBoundary;
import app.drawable.characters.Enemy;
import app.drawable.characters.FlyingEnemy;
import app.drawable.characters.Player;
import app.drawable.collectables.Checkpoint;
import app.drawable.collectables.HealthItem;
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
        final int playerStartXPos = Constants.SCREEN_WIDTH / 6;
        final int playerStartYPos = Constants.SCREEN_HEIGHT / 4;
        this.checkpointXPos = 15 * playerStartXPos + 5 * Constants.CHECKPOINT_WIDTH;

        this.makeActive();
        ResourceUtils.loopSong(ESongType.LEVEL);

        if (true) {
            this.viewBox = new ViewBox(
                this.mainSketch,
                this.checkpointXPos - 200,
                playerStartYPos,
                true);
            this.player = new Player(
                this.mainSketch,
                this.checkpointXPos,
                playerStartYPos,
                Constants.PLAYER_DIAMETER,
                true);
        } else {
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

            this.levelDrawableCollection.addDrawable(new Checkpoint(
                this.mainSketch,
                this.checkpointXPos,
                Constants.LEVEL_FLOOR_Y_POSITION - Constants.CHECKPOINT_HEIGHT,
                Constants.CHECKPOINT_WIDTH,
                Constants.CHECKPOINT_HEIGHT,
                Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
                true)
            );
        }

        this.setupActivateBeginningBeforeCheckpoint(playerStartXPos, playerStartYPos);
        final int endXPosBeforeCheckpoint = this.setupActivateEndBeforeCheckpoint(playerStartXPos);
        this.setupActivateBeginningAfterCheckpoint(endXPosBeforeCheckpoint, 500);
    }

    private void setupActivateBeginningBeforeCheckpoint(final int playerStartXPos, final int playerStartYPos) {
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
            this.mainSketch.getCurrentActiveLevelHeight() / 2,
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));
        HealthItem healthItemForBlock =
            new HealthItem(
                this.mainSketch,
                1,
                0,
                0,
                Constants.HEALTH_ITEM_SIZE,
                Constants.HEALTH_ITEM_SIZE,
                Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
                false
            );
        this.levelDrawableCollection.addDrawable((healthItemForBlock));
        this.levelDrawableCollection.addDrawable(new ItemBlock(
            this.mainSketch,
            4 * playerStartXPos + Constants.PLAYER_DIAMETER,
            (this.mainSketch.getCurrentActiveLevelHeight() / 2) - (2 * Constants.DEFAULT_BLOCK_SIZE),
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BLOCK_SIZE,
            healthItemForBlock,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            false,
            false,
            true
        ));

        // double flying enemy groups
        this.levelDrawableCollection.addDrawable(new VerticalBoundary(
            this.mainSketch,
            6 * playerStartXPos,
            playerStartYPos,
            this.mainSketch.getCurrentActiveLevelHeight() - (playerStartYPos / 2),
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));
        this.levelDrawableCollection.addDrawable(new HorizontalBoundary(
            this.mainSketch,
            6 * playerStartXPos,
            this.mainSketch.getCurrentActiveLevelHeight(),
            2 * playerStartXPos,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            true
        ));
        for (int i = 0; i < 10; i++) {
            this.levelDrawableCollection.addDrawable(new FlyingEnemy(
                this.mainSketch,
                7 * playerStartXPos,
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
                7 * playerStartXPos,
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
            8 * playerStartXPos,
            playerStartYPos,
            this.mainSketch.getCurrentActiveLevelHeight() - (playerStartYPos / 2),
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));


    }

    private int setupActivateEndBeforeCheckpoint(final int playerStartXPos) {

        final int smallMiddleGapWithEnemiesXPos = 10 * playerStartXPos;
        // small middle gap with enemies
        this.levelDrawableCollection.addDrawable(new VerticalBoundary(
            this.mainSketch,
            smallMiddleGapWithEnemiesXPos,
            0,
            this.mainSketch.getCurrentActiveLevelHeight() / 2 - Constants.PLAYER_DIAMETER,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));
        this.levelDrawableCollection.addDrawable(new VerticalBoundary(
            this.mainSketch,
            smallMiddleGapWithEnemiesXPos,
            this.mainSketch.getCurrentActiveLevelHeight() / 2 + Constants.PLAYER_DIAMETER,
            this.mainSketch.getCurrentActiveLevelHeight()
                - (this.mainSketch.getCurrentActiveLevelHeight() / 2 + Constants.PLAYER_DIAMETER),
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));
        this.levelDrawableCollection.addDrawable(new HorizontalBoundary(
            this.mainSketch,
            smallMiddleGapWithEnemiesXPos,
            this.mainSketch.getCurrentActiveLevelHeight() / 3,
            5 * Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            true
        ));
        for (int i = 0; i < 4; i++) {
            this.levelDrawableCollection.addDrawable(new FlyingEnemy(
                this.mainSketch,
                smallMiddleGapWithEnemiesXPos + (3 * Constants.SMALL_ENEMY_DIAMETER / 2),
                this.mainSketch.getCurrentActiveLevelHeight() / 2 + i * Constants.PLAYER_DIAMETER,
                Constants.SMALL_ENEMY_DIAMETER,
                0,
                0,
                false,
                false,
                true,
                true,
                true
            ));
        }

        final int mediumEnemyWallXPos = 15 * playerStartXPos;
        // medium enemy wall
        for (int i = 0; i < 3; i++) {
            this.levelDrawableCollection.addDrawable(new FlyingEnemy(
                this.mainSketch,
                mediumEnemyWallXPos,
                Constants.MEDIUM_ENEMY_DIAMETER / 2 + i * Constants.MEDIUM_ENEMY_DIAMETER,
                Constants.MEDIUM_ENEMY_DIAMETER,
                0,
                0,
                false,
                false,
                true,
                true,
                true
            ));
        }
        this.levelDrawableCollection.addDrawable(new HorizontalBoundary(
            this.mainSketch,
            mediumEnemyWallXPos,
            Constants.LEVEL_FLOOR_Y_POSITION,
            this.checkpointXPos - (mediumEnemyWallXPos),
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            true,
            false,
            true,
            true
        ));

        return mediumEnemyWallXPos;
    }

    private void setupActivateBeginningAfterCheckpoint(final int playerStartXPos, final int gapWidth) {

        final int floorStartXPosAfterGap = playerStartXPos + gapWidth;
        this.levelDrawableCollection.addDrawable(new HorizontalBoundary(
            mainSketch,
            floorStartXPosAfterGap,
            Constants.LEVEL_FLOOR_Y_POSITION,
            2000,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            true
        ));

        // fast flying enemy and trigger
        Enemy enemyToAddForTrigger = new FlyingEnemy(
            this.mainSketch,
            playerStartXPos + 350,
            Constants.SCREEN_HEIGHT,
            Constants.MEDIUM_ENEMY_DIAMETER,
            0,
            -Constants.ENEMY_FAST_MOVEMENT_SPEED * 3,
            -Constants.MEDIUM_ENEMY_DIAMETER,
            Constants.SCREEN_HEIGHT,
            false,
            false,
            true,
            true,
            false
        );
        this.levelDrawableCollection.addDrawable(enemyToAddForTrigger);
        this.levelDrawableCollection.addDrawable(new EnemyTriggerVerticalBoundary(
            this.mainSketch,
            playerStartXPos + 250,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            enemyToAddForTrigger
        ));

        this.levelDrawableCollection.addDrawable(new EventBlock(
            this.mainSketch,
            floorStartXPosAfterGap,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            Constants.CHARACTER_LAUNCH_EVENT_VERTICAL_VELOCITY / 2,
            true,
            true
        ));
    }

}
