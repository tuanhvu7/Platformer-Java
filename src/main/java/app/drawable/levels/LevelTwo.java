package app.drawable.levels;

import app.Platformer;
import app.constants.Constants;
import app.drawable.blocks.Block;
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

import java.util.HashSet;
import java.util.Set;

/**
 * Level two
 */
public class LevelTwo extends ALevel {

    /**
     * sets properties, boundaries, and characters of this
     */
    public LevelTwo(Platformer mainSketch, boolean initAsActive, boolean loadPlayerFromCheckPoint) {
        super(mainSketch, initAsActive, loadPlayerFromCheckPoint, 4 * Constants.PLAYER_DIAMETER);
    }

    /**
     * setup and activate this
     */
    @Override
    public void setUpActivateLevel() {
        this.makeActive();

        ResourceUtils.loopSong(ESongType.LEVEL);

        final int levelMiddleXPos = this.mainSketch.getCurrentActiveLevelWidth() / 2;
        this.checkpointXPos = levelMiddleXPos - 1750 - 50;
        if (this.loadPlayerFromCheckPoint) {
            this.viewBox = new ViewBox(
                this.mainSketch,
                this.checkpointXPos - ((Constants.SCREEN_WIDTH / 2) + 75),
                0,
                true);
            this.player = new Player(
                this.mainSketch,
                this.checkpointXPos,
                0,
                Constants.PLAYER_DIAMETER,
                true);
        } else {
            this.viewBox = new ViewBox(
                this.mainSketch,
                levelMiddleXPos,
                0,
                true);
            this.player = new Player(
                this.mainSketch,
                levelMiddleXPos + (Constants.SCREEN_WIDTH / 2) + 75,
                0,
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

        int levelFloorXPosReference = this.setupActivateStartWrongSection(levelMiddleXPos);
        levelFloorXPosReference = this.setupActivateMiddleWrongSection(levelFloorXPosReference + 750);
        this.setupActivateEndWrongSection(levelFloorXPosReference + 250);

        levelFloorXPosReference = this.setupActivateStartCorrectSection(levelMiddleXPos);
        levelFloorXPosReference = this.setupActivateMiddleCorrectSection(levelFloorXPosReference - 750);
        this.setupActivateEndCorrectSection(levelFloorXPosReference - 250);
    }

    /* ****** WRONG SECTION ****** */

    /**
     * @return sum of given startXPos and section floor x offset
     */
    private int setupActivateStartWrongSection(final int startXPos) {
        final int sectionFloorXOffset = 1000;
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

        // level half split boundary
        this.levelDrawableCollection.addDrawable(new VerticalBoundary(
            this.mainSketch,
            startXPos,
            Constants.LEVEL_FLOOR_Y_POSITION,
            -Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true
        ));

        for (int i = 0; i < 5; i++) {
            this.levelDrawableCollection.addDrawable(new FlyingEnemy(
                this.mainSketch,
                (startXPos + 2 * Constants.SMALL_ENEMY_DIAMETER) - (i * Constants.SMALL_ENEMY_DIAMETER),
                (Constants.SCREEN_HEIGHT - this.mainSketch.getCurrentActiveLevelHeight()) / 2,
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

        this.levelDrawableCollection.addDrawable(new Enemy(
            this.mainSketch,
            startXPos + (Constants.BIG_ENEMY_DIAMETER / 2),
            0,
            Constants.BIG_ENEMY_DIAMETER,
            Constants.ENEMY_REGULAR_MOVEMENT_SPEED,
            true,
            true,
            true
        ));

        for (int i = 0; i < 7; i++) {
            this.levelDrawableCollection.addDrawable(new FlyingEnemy(
                this.mainSketch,
                startXPos + 1100 + i * (Constants.SMALL_ENEMY_DIAMETER + 30),
                Constants.LEVEL_FLOOR_Y_POSITION - Constants.SMALL_ENEMY_DIAMETER,
                Constants.SMALL_ENEMY_DIAMETER,
                0,
                Constants.ENEMY_SLOW_MOVEMENT_SPEED,
                200,
                Constants.LEVEL_FLOOR_Y_POSITION - Constants.SMALL_ENEMY_DIAMETER,
                false,
                false,
                false,
                true,
                true
            ));
        }

        return startXPos + sectionFloorXOffset;
    }

    /**
     * @return sum of given startXPos and section floor x offset
     */
    private int setupActivateMiddleWrongSection(final int startXPos) {
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

        // item block before stair section
        HealthItem healthItemForBlock =
            new HealthItem(
                this.mainSketch,
                -1,
                0,
                0,
                Constants.HEALTH_ITEM_SIZE,
                Constants.HEALTH_ITEM_SIZE,
                1,
                false
            );
        this.levelDrawableCollection.addDrawable((healthItemForBlock));
        this.levelDrawableCollection.addDrawable(new ItemBlock(
            this.mainSketch,
            startXPos + 250,
            Constants.LEVEL_FLOOR_Y_POSITION - 3 * Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BLOCK_SIZE,
            healthItemForBlock,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            false,
            true
        ));

        // stair section
        for (int i = 0; i < 3; i++) {
            this.levelDrawableCollection.addDrawable(new Block(
                this.mainSketch,
                startXPos + 500 + (i + 1) * Constants.DEFAULT_BLOCK_SIZE,
                this.mainSketch.height - (i + 2) * Constants.DEFAULT_BLOCK_SIZE,
                Constants.DEFAULT_BLOCK_SIZE,
                (i + 1) * Constants.DEFAULT_BLOCK_SIZE,
                Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
                false,
                true
            ));
        }

        Enemy enemyToAddForTrigger = new Enemy(
            this.mainSketch,
            startXPos + 2000,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.SMALL_ENEMY_DIAMETER,
            Constants.SMALL_ENEMY_DIAMETER,
            -Constants.ENEMY_REGULAR_MOVEMENT_SPEED,
            false,
            true,
            false
        );
        this.levelDrawableCollection.addDrawable(enemyToAddForTrigger);
        this.levelDrawableCollection.addDrawable(new EnemyTriggerVerticalBoundary(
            this.mainSketch,
            startXPos + 500 + 4 * Constants.DEFAULT_BLOCK_SIZE + 2 * Constants.DEFAULT_BLOCK_SIZE,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            enemyToAddForTrigger
        ));

        return startXPos + sectionFloorXOffset;
    }

    private void setupActivateEndWrongSection(final int startXPos) {
        // section floor
        this.levelDrawableCollection.addDrawable(new HorizontalBoundary(
            this.mainSketch,
            startXPos,
            Constants.LEVEL_FLOOR_Y_POSITION,
            this.mainSketch.getCurrentActiveLevelWidth() - startXPos - 2 * Constants.PLAYER_DIAMETER,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            true
        ));

        this.levelDrawableCollection.addDrawable(new EventBlock(    // launch event
            this.mainSketch,
            startXPos,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            Constants.CHARACTER_LAUNCH_EVENT_VERTICAL_VELOCITY,
            true,
            true
        ));

        // enemies at end
        Set<Enemy> enemyAtEndToTrigger = new HashSet<>();
        for (int i = 0; i < 2; i++) {
            Enemy enemyToAdd = new FlyingEnemy(
                this.mainSketch,
                this.mainSketch.getCurrentActiveLevelWidth() - (i + 1) * Constants.SMALL_ENEMY_DIAMETER,
                Constants.LEVEL_FLOOR_Y_POSITION - (Constants.SMALL_ENEMY_DIAMETER / 2),
                Constants.SMALL_ENEMY_DIAMETER,
                -Constants.ENEMY_REGULAR_MOVEMENT_SPEED,
                0,
                true,
                true,
                false,
                true,
                false
            );
            this.levelDrawableCollection.addDrawable(enemyToAdd);
            enemyAtEndToTrigger.add(enemyToAdd);
        }
        this.levelDrawableCollection.addDrawable(new EnemyTriggerVerticalBoundary(
            this.mainSketch,
            startXPos + Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            enemyAtEndToTrigger
        ));
    }

    /* ****** CORRECT SECTION ****** */

    /**
     * @return sum of given startXPos and section floor x offset
     */
    private int setupActivateStartCorrectSection(final int startXPos) {
        final int sectionFloorXOffset = -1000;
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

        Enemy enemyToAddForTrigger = new Enemy(
            this.mainSketch,
            startXPos - 700,
            0,
            Constants.BIG_ENEMY_DIAMETER,
            Constants.ENEMY_REGULAR_MOVEMENT_SPEED,
            true,
            true,
            false);
        this.levelDrawableCollection.addDrawable(enemyToAddForTrigger);
        this.levelDrawableCollection.addDrawable(new EnemyTriggerVerticalBoundary(
            this.mainSketch,
            startXPos - 500,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            enemyToAddForTrigger
        ));

        for (int i = 0; i < 7; i++) {
            this.levelDrawableCollection.addDrawable(new FlyingEnemy(
                this.mainSketch,
                startXPos - (1100 + i * (Constants.SMALL_ENEMY_DIAMETER + 30)),
                Constants.LEVEL_FLOOR_Y_POSITION - Constants.SMALL_ENEMY_DIAMETER,
                Constants.SMALL_ENEMY_DIAMETER,
                0,
                Constants.ENEMY_SLOW_MOVEMENT_SPEED,
                200,
                Constants.LEVEL_FLOOR_Y_POSITION - Constants.SMALL_ENEMY_DIAMETER,
                false,
                false,
                i % 2 == 0,
                true,
                true
            ));
        }

        return startXPos + sectionFloorXOffset;
    }

    /**
     * @return sum of given startXPos and section floor x offset
     */
    private int setupActivateMiddleCorrectSection(final int startXPos) {
        final int sectionFloorXOffset = -2000;
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

        // item block before stair section
        HealthItem healthItemForBlock =
            new HealthItem(
                this.mainSketch,
                -1,
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
            startXPos - 250 - Constants.DEFAULT_BLOCK_SIZE,
            Constants.LEVEL_FLOOR_Y_POSITION - 3 * Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BLOCK_SIZE,
            healthItemForBlock,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            false,
            true
        ));

        // stair section
        int leftXPosOfStairsSection = 0;
        for (int i = 0; i < 3; i++) {
            leftXPosOfStairsSection = startXPos - (500 + (i + 2) * Constants.DEFAULT_BLOCK_SIZE);
            this.levelDrawableCollection.addDrawable(new Block(
                this.mainSketch,
                leftXPosOfStairsSection,
                this.mainSketch.height - (i + 2) * Constants.DEFAULT_BLOCK_SIZE,
                Constants.DEFAULT_BLOCK_SIZE,
                (i + 1) * Constants.DEFAULT_BLOCK_SIZE,
                Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
                false,
                true
            ));
        }

        Enemy enemyToAddForTrigger = new Enemy(
            this.mainSketch,
            startXPos - 2000,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.SMALL_ENEMY_DIAMETER,
            Constants.SMALL_ENEMY_DIAMETER,
            Constants.ENEMY_REGULAR_MOVEMENT_SPEED,
            false,
            true,
            false
        );
        this.levelDrawableCollection.addDrawable(enemyToAddForTrigger);
        this.levelDrawableCollection.addDrawable(new EnemyTriggerVerticalBoundary(
            this.mainSketch,
            startXPos - (500 + 4 * Constants.DEFAULT_BLOCK_SIZE) - 2 * Constants.DEFAULT_BLOCK_SIZE,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            enemyToAddForTrigger
        ));

        // calculated using distance from pit to start from left most x pos of stairs
        final int numTimesBlockIterate =
            ((Math.abs(sectionFloorXOffset) - (startXPos - leftXPosOfStairsSection))
                / Constants.DEFAULT_BLOCK_SIZE) - 1; // -1 to not have block at end
        for (int i = 0; i < numTimesBlockIterate; i++) {
            if (i == 0) {   // block closest to stairs is item block
                healthItemForBlock =
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
                    leftXPosOfStairsSection - (i + 1) * Constants.DEFAULT_BLOCK_SIZE, // start from left most x pos of stairs
                    Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_BLOCK_SIZE - Constants.PLAYER_DIAMETER - 10,
                    Constants.DEFAULT_BLOCK_SIZE,
                    Constants.DEFAULT_BLOCK_SIZE,
                    healthItemForBlock,
                    Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
                    false,
                    false,
                    true
                ));
            } else {
                this.levelDrawableCollection.addDrawable(new Block(
                    this.mainSketch,
                    leftXPosOfStairsSection - (i + 1) * Constants.DEFAULT_BLOCK_SIZE, // start from left most x pos of stairs
                    Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_BLOCK_SIZE - Constants.PLAYER_DIAMETER - 10,
                    Constants.DEFAULT_BLOCK_SIZE,
                    Constants.DEFAULT_BLOCK_SIZE,
                    Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
                    false,
                    false,
                    true
                ));
            }
        }

        return startXPos + sectionFloorXOffset;
    }

    private void setupActivateEndCorrectSection(final int startXPos) {
        // section floor
        this.levelDrawableCollection.addDrawable(new HorizontalBoundary(
            this.mainSketch,
            startXPos,
            Constants.LEVEL_FLOOR_Y_POSITION,
            -(this.mainSketch.getCurrentActiveLevelWidth() - (this.mainSketch.getCurrentActiveLevelWidth() / 2 + 1750 + 2250) - 2 * Constants.PLAYER_DIAMETER),
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            true
        ));

        // event block with invincible enemy
        final int eventBlockInvulnerableEnemyXReference = startXPos - Constants.DEFAULT_EVENT_BLOCK_WIDTH;
        this.levelDrawableCollection.addDrawable(new EventBlock(    // launch event
            this.mainSketch,
            eventBlockInvulnerableEnemyXReference,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            Constants.CHARACTER_LAUNCH_EVENT_VERTICAL_VELOCITY,
            true,
            true
        ));
        this.levelDrawableCollection.addDrawable(new Enemy(
            this.mainSketch,
            eventBlockInvulnerableEnemyXReference + (Constants.DEFAULT_EVENT_BLOCK_WIDTH / 2),
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT - Constants.SMALL_ENEMY_DIAMETER,
            Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            0,
            true,
            false,
            true
        ));

        // enemies at end
        Set<Enemy> enemyAtEndToTrigger = new HashSet<>();
        for (int i = 0; i < 2; i++) {
            Enemy enemyToAdd = new FlyingEnemy(
                this.mainSketch,
                (i + 1) * Constants.SMALL_ENEMY_DIAMETER,
                Constants.LEVEL_FLOOR_Y_POSITION - (Constants.SMALL_ENEMY_DIAMETER / 2),
                Constants.SMALL_ENEMY_DIAMETER,
                Constants.ENEMY_REGULAR_MOVEMENT_SPEED,
                0,
                true,
                true,
                true,
                true,
                false
            );
            this.levelDrawableCollection.addDrawable(enemyToAdd);
            enemyAtEndToTrigger.add(enemyToAdd);
        }
        this.levelDrawableCollection.addDrawable(new EnemyTriggerVerticalBoundary(
            this.mainSketch,
            startXPos - Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            enemyAtEndToTrigger
        ));

        // correct goal post
        this.levelDrawableCollection.addDrawable(new LevelGoal(
            this.mainSketch,
            Constants.LEVEL_GOAL_WIDTH + 4 * Constants.PLAYER_DIAMETER,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.LEVEL_GOAL_HEIGHT,
            Constants.LEVEL_GOAL_WIDTH,
            Constants.LEVEL_GOAL_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true)
        );
    }

}

