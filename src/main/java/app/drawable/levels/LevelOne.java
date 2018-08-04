package app.drawable.levels;

import app.Platformer;
import app.drawable.blocks.Block;
import app.drawable.boundaries.EnemyTriggerVerticalBoundary;
import app.drawable.boundaries.HorizontalBoundary;
import app.drawable.characters.ControllableEnemy;
import app.drawable.characters.Enemy;
import app.drawable.characters.Player;
import app.drawable.collectables.Checkpoint;
import app.constants.Constants;
import app.enums.ESongType;
import app.drawable.interfaces.IDrawable;
import app.utils.ResourceUtils;
import app.drawable.viewbox.ViewBox;

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
        super(mainSketch, isActive, loadPlayerFromCheckPoint);
    }

    /**
     * setup and activate this
     */
    @Override
    public void setUpActivateLevel() {
        this.bigEnemyTriggerActivated = false;
        this.bigEnemyTriggerCharacterListSizeCondition = 0;
        this.checkpointXPos = 1200;

        this.makeActive();

        if (this.loadPlayerFromCheckPoint) {
            this.viewBox = new ViewBox(this.mainSketch, this.checkpointXPos - 200, 0, this.isActive);
            this.player = new Player(this.mainSketch, this.checkpointXPos, 0, Constants.PLAYER_DIAMETER, this.isActive);
        } else {
            this.viewBox = new ViewBox(this.mainSketch, 0, 0, this.isActive);
            this.player = new Player(this.mainSketch, 200, 0, Constants.PLAYER_DIAMETER, this.isActive);

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

        ResourceUtils.loopSong(ESongType.Level);

        charactersList.add(new ControllableEnemy(
            this.mainSketch,
            this.mainSketch.getCurrentActiveLevelWidth() - 500,
            0,
            Constants.REGULAR_ENEMY_DIAMETER,
            -Constants.ENEMY_RUN_SPEED,
            false,
            false,
            true,
            this.isActive)
        );

        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            0,
            this.mainSketch.height - 200,
            100,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            100,
            this.mainSketch.height - 400,
            100,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            200,
            this.mainSketch.height - 600,
            100,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            false,
            this.isActive
        ));

        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            100,
            this.mainSketch.height - 800,
            100,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        /*** START Blocks ***/

//         this.blocksList.add(new EventBlock( // launch event
//             this.mainSketch,
//             this.mainSketch.getCurrentActiveLevelWidth() / 2 - 300,
//             Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
//             Constants.DEFAULT_EVENT_BLOCK_WIDTH,
//             Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
//             Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
//             true,
//             this.isActive
//         ));

//         int playerWarpEndXPos = 1000;
//         // int playerWarpEndXPos = getCurrentActiveLevelWidth() - Constants.PLAYER_DIAMETER - 1;  // test end of state
//         // int playerWarpEndXPos = Constants.PLAYER_DIAMETER / 2 + 1;   // test beginning of stage
//
//         this.blocksList.add(new EventBlock( // warp event
//             this.mainSketch,
//             this.mainSketch.getCurrentActiveLevelWidth() / 2 - 300,
//             Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
//             Constants.DEFAULT_EVENT_BLOCK_WIDTH,
//             Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
//             Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
//             playerWarpEndXPos,
//             750,
//             true,
//             this.isActive
//         ));

        this.blocksList.add(new Block(
            this.mainSketch,
            this.mainSketch.getCurrentActiveLevelWidth() / 2 - 300,
            this.mainSketch.height - 300,
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            false,
            false,
            this.isActive
        ));

        this.blocksList.add(new Block(
            this.mainSketch,
            this.mainSketch.getCurrentActiveLevelWidth() / 2 - 300 + Constants.DEFAULT_BLOCK_SIZE,
            this.mainSketch.height - 300 - Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            true,
            this.isActive
        ));

        /*** END Blocks ***/
    }

    /**
     * handle conditional enemy triggers in this;
     * to override in extended classes
     */
    @Override
    public void handleConditionalEnemyTriggers() {
        if (!bigEnemyTriggerActivated && this.charactersList.size() == this.bigEnemyTriggerCharacterListSizeCondition) {
            Set<Enemy> enemySet = new HashSet<>();
            Enemy triggerEnemy = new Enemy(
                this.mainSketch,
                1200,
                0,
                Constants.REGULAR_ENEMY_DIAMETER,
                -Constants.ENEMY_RUN_SPEED,
                false,
                false,
                true,
                false
            );

            enemySet.add(triggerEnemy);
            charactersList.add(triggerEnemy);

            this.boundariesList.add(new EnemyTriggerVerticalBoundary(
                this.mainSketch,
                1000,
                0,
                Constants.LEVEL_FLOOR_Y_POSITION,
                Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
                true,
                this.isActive,
                enemySet
            ));

            this.bigEnemyTriggerActivated = true;
        }
    }

}

