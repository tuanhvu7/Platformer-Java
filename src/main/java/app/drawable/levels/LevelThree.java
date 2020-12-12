package app.drawable.levels;

import app.Platformer;
import app.constants.Constants;
import app.drawable.blocks.Block;
import app.drawable.blocks.EventBlock;
import app.drawable.blocks.ItemBlock;
import app.drawable.boundaries.EnemyTriggerVerticalBoundary;
import app.drawable.boundaries.HorizontalBoundary;
import app.drawable.boundaries.VerticalBoundary;
import app.drawable.characters.ControllableEnemy;
import app.drawable.characters.Enemy;
import app.drawable.characters.FlyingEnemy;
import app.drawable.characters.Player;
import app.drawable.collectables.Checkpoint;
import app.drawable.collectables.HealthItem;
import app.drawable.collectables.LevelGoal;
import app.drawable.viewbox.ViewBox;
import app.enums.ESongType;
import app.utils.ResourceUtils;

import java.util.HashSet;
import java.util.Set;

public class LevelThree extends ALevel {

    /**
     * sets properties, boundaries, and characters of this
     */
    public LevelThree(Platformer mainSketch, boolean initAsActive, boolean loadPlayerFromCheckPoint) {
        super(mainSketch, initAsActive, loadPlayerFromCheckPoint, 200);
    }

    @Override
    void setUpActivateLevel() {
        final int playerStartXPos = Constants.SCREEN_WIDTH / 6;
        final int playerStartYPos = Constants.SCREEN_HEIGHT / 4;
        this.checkpointXPos = 15 * playerStartXPos + 5 * Constants.CHECKPOINT_WIDTH;

        this.makeActive();
        ResourceUtils.loopSong(ESongType.LEVEL);

        if (this.isLoadPlayerFromCheckPoint()) {
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

        int levelFloorXPosReference = this.setupActivateBeginningBeforeCheckpoint(playerStartXPos, playerStartYPos);
        levelFloorXPosReference = this.setupActivateEndBeforeCheckpoint(levelFloorXPosReference + 332);

        levelFloorXPosReference = this.setupActivateBeginningAfterCheckpoint(levelFloorXPosReference + 300);
        this.setupActivateEndAfterCheckpoint(levelFloorXPosReference);
    }

    /**
     * @return x-pos of left-most drawable in section
     */
    private int setupActivateBeginningBeforeCheckpoint(final int beginningGoalXPos, final int beginningGoalYPos) {
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
            beginningGoalXPos,
            beginningGoalYPos + 2 * Constants.PLAYER_DIAMETER,
            Constants.CHECKPOINT_WIDTH,
            Constants.SCREEN_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));

        this.levelDrawableCollection.addDrawable(new VerticalBoundary(
            this.mainSketch,
            2 * beginningGoalXPos,
            beginningGoalYPos,
            this.mainSketch.getCurrentActiveLevelHeight() - (beginningGoalYPos / 2),
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));

        this.levelDrawableCollection.addDrawable(new FlyingEnemy(
            this.mainSketch,
            2 * beginningGoalXPos,
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
            4 * beginningGoalXPos,
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
            4 * beginningGoalXPos + Constants.PLAYER_DIAMETER,
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
            6 * beginningGoalXPos,
            beginningGoalYPos,
            this.mainSketch.getCurrentActiveLevelHeight() - (beginningGoalYPos / 2),
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));
        this.levelDrawableCollection.addDrawable(new HorizontalBoundary(
            this.mainSketch,
            6 * beginningGoalXPos,
            this.mainSketch.getCurrentActiveLevelHeight(),
            2 * beginningGoalXPos,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            true
        ));
        for (int i = 0; i < 10; i++) {
            this.levelDrawableCollection.addDrawable(new FlyingEnemy(
                this.mainSketch,
                7 * beginningGoalXPos,
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
                7 * beginningGoalXPos,
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

        final int leftMostDrawableXPos = 8 * beginningGoalXPos;
        this.levelDrawableCollection.addDrawable(new VerticalBoundary(
            this.mainSketch,
            leftMostDrawableXPos,
            beginningGoalYPos,
            this.mainSketch.getCurrentActiveLevelHeight() - (beginningGoalYPos / 2),
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));

        return leftMostDrawableXPos;

    }

    /**
     * @return x-pos of left-most drawable in section
     */
    private int setupActivateEndBeforeCheckpoint(final int startXPos) {
        // small middle gap with enemies
        this.levelDrawableCollection.addDrawable(new VerticalBoundary(
            this.mainSketch,
            startXPos,
            0,
            this.mainSketch.getCurrentActiveLevelHeight() / 2 - Constants.PLAYER_DIAMETER,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));
        this.levelDrawableCollection.addDrawable(new VerticalBoundary(
            this.mainSketch,
            startXPos,
            this.mainSketch.getCurrentActiveLevelHeight() / 2 + Constants.PLAYER_DIAMETER,
            this.mainSketch.getCurrentActiveLevelHeight()
                - (this.mainSketch.getCurrentActiveLevelHeight() / 2 + Constants.PLAYER_DIAMETER),
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));
        this.levelDrawableCollection.addDrawable(new HorizontalBoundary(
            this.mainSketch,
            startXPos,
            this.mainSketch.getCurrentActiveLevelHeight() / 3,
            5 * Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            true
        ));
        for (int i = 0; i < 4; i++) {
            this.levelDrawableCollection.addDrawable(new FlyingEnemy(
                this.mainSketch,
                startXPos + (3 * Constants.SMALL_ENEMY_DIAMETER / 2),
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

        final int mediumEnemyWallXPos = (startXPos * 3) / 2;
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

        final int sectionFloorXOffset = this.checkpointXPos - (mediumEnemyWallXPos);
        this.levelDrawableCollection.addDrawable(new HorizontalBoundary(
            this.mainSketch,
            mediumEnemyWallXPos,
            Constants.LEVEL_FLOOR_Y_POSITION,
            sectionFloorXOffset,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            true,
            false,
            true,
            true
        ));

        return mediumEnemyWallXPos + sectionFloorXOffset;
    }

    /**
     * @return sum of given startXPos and section floor x offset
     */
    private int setupActivateBeginningAfterCheckpoint(final int startXPos) {
        final int sectionFloorXOffset = 2000;
        // section floor
        this.levelDrawableCollection.addDrawable(new HorizontalBoundary(
            this.mainSketch,
            startXPos,
            Constants.LEVEL_FLOOR_Y_POSITION,
            sectionFloorXOffset,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            true
        ));

        // fast up flying enemy and trigger
        Enemy enemyToAddForTrigger = new FlyingEnemy(
            this.mainSketch,
            startXPos - 150,
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
            startXPos - 250,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            enemyToAddForTrigger
        ));

        this.levelDrawableCollection.addDrawable(new EventBlock(    // launch event
            this.mainSketch,
            startXPos,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            Constants.CHARACTER_LAUNCH_EVENT_VERTICAL_VELOCITY / 2,
            true,
            true
        ));
        this.levelDrawableCollection.addDrawable(new EventBlock(    // launch event
            this.mainSketch,
            startXPos + Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            Constants.CHARACTER_LAUNCH_EVENT_VERTICAL_VELOCITY,
            true,
            true
        ));

        // small flying enemy group with trigger
        Set<Enemy> enemiesToAddForTrigger = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            enemyToAddForTrigger = new FlyingEnemy(
                this.mainSketch,
                startXPos + 8 * Constants.DEFAULT_EVENT_BLOCK_WIDTH,
                Constants.LEVEL_FLOOR_Y_POSITION - (Constants.SMALL_ENEMY_DIAMETER / 2) - (Constants.SMALL_ENEMY_DIAMETER * i),
                Constants.SMALL_ENEMY_DIAMETER,
                -Constants.ENEMY_FAST_MOVEMENT_SPEED,
                0,
                false,
                true,
                false,
                true,
                false
            );
            enemiesToAddForTrigger.add(enemyToAddForTrigger);
            this.levelDrawableCollection.addDrawable(enemyToAddForTrigger);
        }
        this.levelDrawableCollection.addDrawable(new EnemyTriggerVerticalBoundary(
            this.mainSketch,
            startXPos + 4 * Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            enemiesToAddForTrigger
        ));

        // flying enemy wall that covers height of screen and trigger
        enemiesToAddForTrigger = new HashSet<>();
        int currFlyingEnemyYPos = Constants.LEVEL_FLOOR_Y_POSITION - (Constants.SMALL_ENEMY_DIAMETER / 2);
        while (currFlyingEnemyYPos > Constants.SMALL_ENEMY_DIAMETER / 2) {
            enemyToAddForTrigger = new FlyingEnemy(
                this.mainSketch,
                startXPos + 11 * Constants.DEFAULT_EVENT_BLOCK_WIDTH,
                currFlyingEnemyYPos,
                Constants.SMALL_ENEMY_DIAMETER,
                -Constants.ENEMY_FAST_MOVEMENT_SPEED,
                0,
                false,
                true,
                false,
                true,
                false
            );
            enemiesToAddForTrigger.add(enemyToAddForTrigger);
            this.levelDrawableCollection.addDrawable(enemyToAddForTrigger);
            currFlyingEnemyYPos = currFlyingEnemyYPos - Constants.SMALL_ENEMY_DIAMETER;
        }
        this.levelDrawableCollection.addDrawable(new EnemyTriggerVerticalBoundary(
            this.mainSketch,
            startXPos + 6 * Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            enemiesToAddForTrigger
        ));

        return startXPos + sectionFloorXOffset;
    }

    private void setupActivateEndAfterCheckpoint(final int startXPos) {
        // section floor
        this.levelDrawableCollection.addDrawable(new HorizontalBoundary(
            this.mainSketch,
            startXPos,
            Constants.LEVEL_FLOOR_Y_POSITION,
            3500,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            true
        ));

        // big falling enemy with trigger; and block
        Enemy enemyToAddForTrigger = new Enemy(
            this.mainSketch,
            startXPos + 250,
            -Constants.BIG_ENEMY_DIAMETER / 2 + 1,
            Constants.BIG_ENEMY_DIAMETER,
            0,
            true,
            true,
            false
        );
        this.levelDrawableCollection.addDrawable(enemyToAddForTrigger);
        this.levelDrawableCollection.addDrawable(new EnemyTriggerVerticalBoundary(
            this.mainSketch,
            startXPos + 250,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            enemyToAddForTrigger
        ));
        this.levelDrawableCollection.addDrawable(new Block(
            this.mainSketch,
            startXPos + 250,
            Constants.SCREEN_HEIGHT / 2,
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            false,
            false,
            true
        ));

        this.levelDrawableCollection.addDrawable(new HealthItem(
            this.mainSketch,
            1,
            startXPos + 1000,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.HEALTH_ITEM_SIZE,
            Constants.HEALTH_ITEM_SIZE,
            Constants.HEALTH_ITEM_SIZE,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));

        // zig-zag enemies and trigger
        Set<Enemy> enemiesToAddForTrigger = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            enemyToAddForTrigger = new FlyingEnemy(
                this.mainSketch,
                startXPos + 1500 + (250 * i),
                Constants.LEVEL_FLOOR_Y_POSITION - (2 * Constants.SMALL_ENEMY_DIAMETER),
                Constants.SMALL_ENEMY_DIAMETER,
                -Constants.ENEMY_FAST_MOVEMENT_SPEED,
                Constants.ENEMY_FAST_MOVEMENT_SPEED,
                Constants.LEVEL_FLOOR_Y_POSITION - (4 * Constants.SMALL_ENEMY_DIAMETER),
                Constants.LEVEL_FLOOR_Y_POSITION,
                false,
                true,
                false,
                true,
                false
            );
            enemiesToAddForTrigger.add(enemyToAddForTrigger);
            this.levelDrawableCollection.addDrawable(enemyToAddForTrigger);
        }
        this.levelDrawableCollection.addDrawable(new EnemyTriggerVerticalBoundary(
            this.mainSketch,
            startXPos + 1000,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            enemiesToAddForTrigger
        ));

        // invincible enemy near end
        this.levelDrawableCollection.addDrawable(new Enemy(
            this.mainSketch,
            startXPos + 2000,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.BIG_ENEMY_DIAMETER,
            Constants.BIG_ENEMY_DIAMETER,
            0,
            true,
            true,
            true
        ));

        // controllable enemy at end
        enemyToAddForTrigger = new ControllableEnemy(
            this.mainSketch,
            this.mainSketch.getCurrentActiveLevelWidth() - Constants.SMALL_ENEMY_DIAMETER,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.SMALL_ENEMY_DIAMETER,
            Constants.SMALL_ENEMY_DIAMETER,
            true,
            true,
            Constants.PLAYER_MOVEMENT_SPEED,
            false,
            true,
            false
        );
        this.levelDrawableCollection.addDrawable(enemyToAddForTrigger);
        this.levelDrawableCollection.addDrawable(new EnemyTriggerVerticalBoundary(
            this.mainSketch,
            startXPos + 1500,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            enemyToAddForTrigger
        ));
    }

}
