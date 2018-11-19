package app.drawable.levels;

import app.Platformer;
import app.constants.Constants;
import app.drawable.blocks.Block;
import app.drawable.blocks.EventBlock;
import app.drawable.boundaries.EnemyTriggerVerticalBoundary;
import app.drawable.boundaries.HorizontalBoundary;
import app.drawable.characters.ControllableEnemy;
import app.drawable.characters.Enemy;
import app.drawable.characters.FlyingEnemy;
import app.drawable.characters.Player;
import app.drawable.collectables.Checkpoint;
import app.drawable.IDrawable;
import app.drawable.viewbox.ViewBox;
import app.enums.ESongType;
import app.utils.ResourceUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Level one
 */
public class LevelOne extends ALevel implements IDrawable {

    // true means big enemy trigger boundary has been activated
    private boolean bigEnemyTriggerActivated;
    // size of this characters list to make big enemy trigger boundary active
    private int bigEnemyTriggerCharacterListSizeCondition;

    /**
     * sets properties, boundaries, and characters of this
     */
    public LevelOne(Platformer mainSketch, boolean isActive, boolean loadPlayerFromCheckPoint) {
        super(mainSketch, isActive, loadPlayerFromCheckPoint, Constants.BIG_ENEMY_DIAMETER + 200);
    }

    /**
     * setup and activate this
     */
    @Override
    public void setUpActivateLevel() {
        this.bigEnemyTriggerActivated = false;
        this.checkpointXPos = 3100;

        this.makeActive();
        ResourceUtils.loopSong(ESongType.LEVEL);

        if (this.loadPlayerFromCheckPoint) {
            this.viewBox = new ViewBox(this.mainSketch, this.checkpointXPos - 200, 0, this.isActive);
            this.player = new Player(this.mainSketch, this.checkpointXPos, 0, Constants.PLAYER_DIAMETER, this.isActive);
        } else {
            this.viewBox = new ViewBox(this.mainSketch, 0, 0, this.isActive);
            this.player = new Player(this.mainSketch, 200, 0, Constants.PLAYER_DIAMETER, this.isActive);
//            this.viewBox = new ViewBox(this.mainSketch, 5500 - 200, 0, this.isActive);
//            this.player = new Player(this.mainSketch, 5500, 0, Constants.PLAYER_DIAMETER, this.isActive);

            this.collectablesList.add(new Checkpoint(
                this.mainSketch,
                this.checkpointXPos,
                Constants.LEVEL_FLOOR_Y_POSITION - Constants.CHECKPOINT_HEIGHT,
                Constants.CHECKPOINT_WIDTH,
                Constants.CHECKPOINT_HEIGHT,
                Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
                this.isActive)
            );
        }

        this.setupActivateBeforeCheckpoint();

        final int startMiddleSectionXPos = 3000;
        this.setupActivateMiddleSectionAfterCheckpoint(startMiddleSectionXPos);
        this.setupActivateEndSection(startMiddleSectionXPos + 2000);
        this.bigEnemyTriggerCharacterListSizeCondition = this.charactersList.size() - 2;
    }

    /**
     * handle conditional enemy triggers in this;
     * to override in extended classes
     */
    @Override
    public void handleConditionalEnemyTriggers() {
        if (!bigEnemyTriggerActivated && this.charactersList.size() == this.bigEnemyTriggerCharacterListSizeCondition) {
            Enemy triggerEnemy = new Enemy(
                this.mainSketch,
                3000,
                0,
                Constants.BIG_ENEMY_DIAMETER,
                -Constants.ENEMY_REGULAR_RUN_SPEED,
                false,
                true,
                false
            );

            charactersList.add(triggerEnemy);

            this.boundariesList.add(new EnemyTriggerVerticalBoundary(
                this.mainSketch,
                2900,
                0,
                Constants.LEVEL_FLOOR_Y_POSITION,
                Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
                this.isActive,
                triggerEnemy
            ));

            this.bigEnemyTriggerActivated = true;
        }
    }

    /**
     * setup activate section before checkpoint
     */
    private void setupActivateBeforeCheckpoint() {
        // stage floor
        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            2500,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        this.charactersList.add(new Enemy(
            this.mainSketch,
            500,
            0,
            Constants.BIG_ENEMY_DIAMETER,
            -Constants.ENEMY_REGULAR_RUN_SPEED,
            false,
            true,
            this.isActive)
        );

        this.blocksList.add(new Block(
            this.mainSketch,
            750,
            this.mainSketch.height - 300 - Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        this.charactersList.add(new Enemy(
            this.mainSketch,
            1750,
            0,
            Constants.REGULAR_ENEMY_DIAMETER,
            -Constants.ENEMY_SLOW_RUN_SPEED,
            false,
            true,
            this.isActive)
        );
        this.charactersList.add(new Enemy(
            this.mainSketch,
            1750 + Constants.REGULAR_ENEMY_DIAMETER,
            0,
            Constants.REGULAR_ENEMY_DIAMETER,
            -Constants.ENEMY_SLOW_RUN_SPEED,
            false,
            true,
            this.isActive)
        );
        this.charactersList.add(new Enemy(
            this.mainSketch,
            1750 + 2 * Constants.REGULAR_ENEMY_DIAMETER,
            0,
            Constants.REGULAR_ENEMY_DIAMETER,
            -Constants.ENEMY_SLOW_RUN_SPEED,
            false,
            true,
            this.isActive)
        );
        this.charactersList.add(new Enemy(
            this.mainSketch,
            1750 + 3 * Constants.REGULAR_ENEMY_DIAMETER,
            0,
            Constants.REGULAR_ENEMY_DIAMETER,
            -Constants.ENEMY_SLOW_RUN_SPEED,
            false,
            true,
            this.isActive)
        );
        this.charactersList.add(new Enemy(
            this.mainSketch,
            1750 + 4 * Constants.REGULAR_ENEMY_DIAMETER,
            0,
            Constants.REGULAR_ENEMY_DIAMETER,
            -Constants.ENEMY_SLOW_RUN_SPEED,
            false,
            true,
            this.isActive)
        );
        this.charactersList.add(new Enemy(
            this.mainSketch,
            1750 + 5 * Constants.REGULAR_ENEMY_DIAMETER,
            0,
            Constants.REGULAR_ENEMY_DIAMETER,
            -Constants.ENEMY_SLOW_RUN_SPEED,
            false,
            true,
            this.isActive)
        );
        this.charactersList.add(new Enemy(
            this.mainSketch,
            1750 + 6 * Constants.REGULAR_ENEMY_DIAMETER,
            0,
            Constants.REGULAR_ENEMY_DIAMETER,
            -Constants.ENEMY_SLOW_RUN_SPEED,
            false,
            true,
            this.isActive)
        );

        this.blocksList.add(new EventBlock( // launch event
            this.mainSketch,
            2000,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        int playerWarpEndXPos = 2800;
        this.blocksList.add(new EventBlock( // warp event
            this.mainSketch,
            2000 + Constants.DEFAULT_EVENT_BLOCK_WIDTH + Constants.PLAYER_DIAMETER + 100,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            playerWarpEndXPos,
            750,
            true,
            this.isActive
        ));

        this.blocksList.add(new Block(
            this.mainSketch,
            2550,
            Constants.LEVEL_FLOOR_Y_POSITION - 300 - Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            false,
            false,
            this.isActive
        ));
    }

    /**
     * setup activate middle section after checkpoint
     */
    private void setupActivateMiddleSectionAfterCheckpoint(final int startXPos) {
        // stage floor
        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            startXPos,
            Constants.LEVEL_FLOOR_Y_POSITION,
            2000,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            startXPos + 250,
            Constants.LEVEL_FLOOR_Y_POSITION - 4 * Constants.PLAYER_DIAMETER,
            4 * Constants.PLAYER_DIAMETER,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        // controllable enemy
        Enemy enemyToAdd = new ControllableEnemy(
            this.mainSketch,
            startXPos + 1000 + 4 * Constants.PLAYER_DIAMETER,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.REGULAR_ENEMY_DIAMETER - 10,
            Constants.REGULAR_ENEMY_DIAMETER,
            -Constants.ENEMY_FAST_RUN_SPEED,
            false,
            true,
            false
        );
        this.charactersList.add(enemyToAdd);
        this.boundariesList.add(new EnemyTriggerVerticalBoundary(
            this.mainSketch,
            startXPos + 500 + 4 * Constants.PLAYER_DIAMETER,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            this.isActive,
            enemyToAdd
        ));

        // flying enemies
        Set<Enemy> triggerEnemySet = new HashSet<>();
        enemyToAdd = new FlyingEnemy(
            this.mainSketch,
            startXPos + 1600 + 4 * Constants.PLAYER_DIAMETER,
            Constants.LEVEL_FLOOR_Y_POSITION - 4 * Constants.PLAYER_DIAMETER,
            Constants.REGULAR_ENEMY_DIAMETER,
            -Constants.ENEMY_FAST_RUN_SPEED,
            0,
            false,
            true,
            false);
        triggerEnemySet.add(enemyToAdd);
        this.charactersList.add(enemyToAdd);
        enemyToAdd = new FlyingEnemy(
            this.mainSketch,
            startXPos + 1600 + 8 * Constants.PLAYER_DIAMETER,
            Constants.LEVEL_FLOOR_Y_POSITION - 6 * Constants.PLAYER_DIAMETER,
            Constants.REGULAR_ENEMY_DIAMETER,
            -Constants.ENEMY_FAST_RUN_SPEED,
            0,
            false,
            true,
            false);
        triggerEnemySet.add(enemyToAdd);
        this.charactersList.add(enemyToAdd);
        enemyToAdd = new FlyingEnemy(
            this.mainSketch,
            startXPos + 1600 + 14 * Constants.PLAYER_DIAMETER,
            Constants.LEVEL_FLOOR_Y_POSITION - 5 * Constants.PLAYER_DIAMETER,
            Constants.BIG_ENEMY_DIAMETER,
            -Constants.ENEMY_FAST_RUN_SPEED,
            0,
            false,
            true,
            false);
        triggerEnemySet.add(enemyToAdd);
        this.charactersList.add(enemyToAdd);

        this.boundariesList.add(new EnemyTriggerVerticalBoundary(
            this.mainSketch,
            startXPos + 1100 + 4 * Constants.PLAYER_DIAMETER,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            this.isActive,
            triggerEnemySet
        ));
    }

    /**
     * setup activate middle section after checkpoint
     */
    private void setupActivateEndSection(final int startXPos) {
        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            startXPos,
            Constants.LEVEL_FLOOR_Y_POSITION,
            250,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            false,
            true,
            false,
            true,
            this.isActive
        ));
        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            startXPos + 250,
            Constants.LEVEL_FLOOR_Y_POSITION,
            250,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            false,
            false,
            true,
            this.isActive
        ));

        final int endStageFloorPosition = startXPos + 500;
        // stage floor
        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            endStageFloorPosition,
            Constants.LEVEL_FLOOR_Y_POSITION,
            this.mainSketch.getCurrentActiveLevelWidth() - startXPos,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        // event block with invincible enemy
        final int eventBlockInvulnerableEnemyXReference = endStageFloorPosition + 300;
        this.blocksList.add(new EventBlock( // launch event
            this.mainSketch,
            eventBlockInvulnerableEnemyXReference,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));
        this.charactersList.add(new Enemy(
            this.mainSketch,
            eventBlockInvulnerableEnemyXReference + (Constants.DEFAULT_EVENT_BLOCK_WIDTH / 2),
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT - Constants.REGULAR_ENEMY_DIAMETER,
            Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            0,
            true,
            false,
            this.isActive
        ));

        /*** START two event blocks trap ***/
        final int eventBlockGoingToTrapXReference = endStageFloorPosition + 750;
        final int eventBlockTrapXReference = endStageFloorPosition + 2000;
        this.blocksList.add(new EventBlock( // warp event
            this.mainSketch,
            eventBlockGoingToTrapXReference,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            eventBlockTrapXReference + (Constants.DEFAULT_EVENT_BLOCK_WIDTH / 2),
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT + Constants.REGULAR_ENEMY_DIAMETER,
            true,
            this.isActive
        ));

        this.blocksList.add(new Block(  // block left of event block trap
            this.mainSketch,
            eventBlockTrapXReference - Constants.DEFAULT_BLOCK_SIZE,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT - (4 * Constants.PLAYER_DIAMETER),
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_EVENT_BLOCK_HEIGHT + (4 * Constants.PLAYER_DIAMETER),
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            false,
            this.isActive
        ));
        this.blocksList.add(new Block(  // block above event block trap
            this.mainSketch,
            eventBlockTrapXReference,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT - (4 * Constants.PLAYER_DIAMETER),
            Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            false,
            this.isActive
        ));
        this.blocksList.add(new EventBlock( // warp event
            this.mainSketch,
            eventBlockTrapXReference,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            eventBlockInvulnerableEnemyXReference + (Constants.DEFAULT_EVENT_BLOCK_WIDTH / 2),
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT + Constants.REGULAR_ENEMY_DIAMETER,
            true,
            this.isActive
        ));
        this.blocksList.add(new Block(  // block right of event block trap
            this.mainSketch,
            eventBlockTrapXReference + Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT - (4 * Constants.PLAYER_DIAMETER),
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_EVENT_BLOCK_HEIGHT + (4 * Constants.PLAYER_DIAMETER),
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            false,
            this.isActive
        ));
        /*** END two event blocks trap ***/


        /*** START two event blocks NOT trap ***/
        final int doubleEventBlockXReference = endStageFloorPosition + 1000;
        this.blocksList.add(new EventBlock( // warp event
            this.mainSketch,
            doubleEventBlockXReference,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            doubleEventBlockXReference + (2 * Constants.DEFAULT_EVENT_BLOCK_WIDTH) + (Constants.DEFAULT_EVENT_BLOCK_WIDTH / 2),
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT + Constants.REGULAR_ENEMY_DIAMETER,
            true,
            this.isActive
        ));

        // invincible enemy at end of level
        Enemy triggerEnemy = new Enemy(
            this.mainSketch,
            this.mainSketch.getCurrentActiveLevelWidth() - (Constants.BIG_ENEMY_DIAMETER / 2),
            Constants.LEVEL_FLOOR_Y_POSITION - (Constants.BIG_ENEMY_DIAMETER / 2),
            Constants.BIG_ENEMY_DIAMETER,
            0,
            true,
            true,
            false
        );
        charactersList.add(triggerEnemy);
        this.boundariesList.add(new EnemyTriggerVerticalBoundary(
            this.mainSketch,
            doubleEventBlockXReference + (int) (1.5 * Constants.DEFAULT_EVENT_BLOCK_WIDTH),
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            this.isActive,
            triggerEnemy
        ));
        // warp to event block with invincible enemy
        this.blocksList.add(new EventBlock( // warp event
            this.mainSketch,
            doubleEventBlockXReference + (2 * Constants.DEFAULT_EVENT_BLOCK_WIDTH),
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            eventBlockInvulnerableEnemyXReference + (Constants.DEFAULT_EVENT_BLOCK_WIDTH / 2),
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT + Constants.REGULAR_ENEMY_DIAMETER,
            true,
            this.isActive
        ));
        /*** END two event blocks NOT trap ***/
    }

}

