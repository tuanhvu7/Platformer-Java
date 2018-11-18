package app.drawable.levels;

import app.Platformer;
import app.constants.Constants;
import app.drawable.IDrawable;
import app.drawable.blocks.Block;
import app.drawable.blocks.EventBlock;
import app.drawable.boundaries.EnemyTriggerVerticalBoundary;
import app.drawable.boundaries.HorizontalBoundary;
import app.drawable.boundaries.VerticalBoundary;
import app.drawable.characters.Enemy;
import app.drawable.characters.FlyingEnemy;
import app.drawable.characters.Player;
import app.drawable.viewbox.ViewBox;
import app.enums.ESongType;
import app.utils.ResourceUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Level two
 */
public class LevelTwo extends ALevel implements IDrawable {

    /**
     * sets properties, boundaries, and characters of this
     */
    public LevelTwo(Platformer mainSketch, boolean isActive, boolean loadPlayerFromCheckPoint) {
        super(mainSketch, isActive, loadPlayerFromCheckPoint, 4 * Constants.PLAYER_DIAMETER);
    }

    /**
     * setup and activate this
     */
    @Override
    public void setUpActivateLevel() {
        this.makeActive();

        ResourceUtils.loopSong(ESongType.LEVEL);

        final int startWrongSectionXPos = this.mainSketch.getCurrentActiveLevelWidth() / 2;


        if (this.loadPlayerFromCheckPoint) {

        } else {
            this.viewBox = new ViewBox(
                this.mainSketch,
                startWrongSectionXPos,
                0,
                this.isActive);
            this.player = new Player(
                this.mainSketch,
                (startWrongSectionXPos)
                    + (Constants.SCREEN_WIDTH / 2) + 75,
                0,
                Constants.PLAYER_DIAMETER,
                this.isActive);
        }


        this.setupActivateStartWrongSection(startWrongSectionXPos);
        this.setupActivateMiddleWrongSection(startWrongSectionXPos + 1750);
        this.setupActivateEndWrongSection(startWrongSectionXPos + 1750 + 2250);

        this.setupActivateCorrectSection();
    }

    private void setupActivateStartWrongSection(final int startXPos) {
        this.boundariesList.add(new VerticalBoundary(
            this.mainSketch,
            startXPos,
            Constants.LEVEL_FLOOR_Y_POSITION,
            -Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            this.isActive
        ));

        this.charactersList.add(new Enemy(
            this.mainSketch,
            startXPos + (Constants.BIG_ENEMY_DIAMETER / 2) + 1,
            0,
            Constants.BIG_ENEMY_DIAMETER,
            Constants.ENEMY_REGULAR_RUN_SPEED,
            true,
            true,
            this.isActive)
        );

        // section floor
        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            startXPos,
            Constants.LEVEL_FLOOR_Y_POSITION,
            1000,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        for (int i = 0; i < 7; i++) {
            this.charactersList.add(new FlyingEnemy(
                this.mainSketch,
                startXPos + 1100 + i * (Constants.REGULAR_ENEMY_DIAMETER + 30),
                Constants.LEVEL_FLOOR_Y_POSITION - Constants.REGULAR_ENEMY_DIAMETER,
                Constants.REGULAR_ENEMY_DIAMETER,
                0,
                Constants.ENEMY_SLOW_RUN_SPEED,
                200,
                Constants.LEVEL_FLOOR_Y_POSITION - Constants.REGULAR_ENEMY_DIAMETER,
                false,
                true,
                this.isActive
            ));
        }
    }

    private void setupActivateMiddleWrongSection(final int startXPos) {
        // stair section
        for (int i = 0; i < 3; i++) {
            this.blocksList.add(new Block(
                this.mainSketch,
                startXPos + 500 + (i + 1) * Constants.DEFAULT_BLOCK_SIZE,
                this.mainSketch.height - (i + 2) * Constants.DEFAULT_BLOCK_SIZE,
                Constants.DEFAULT_BLOCK_SIZE,
                (i + 1) * Constants.DEFAULT_BLOCK_SIZE,
                Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
                true,
                false,
                this.isActive
            ));
        }

        Enemy enemyToAddForTrigger = new Enemy(
            this.mainSketch,
            startXPos + 2000,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.REGULAR_ENEMY_DIAMETER,
            Constants.REGULAR_ENEMY_DIAMETER,
            -Constants.ENEMY_REGULAR_RUN_SPEED,
            false,
            true,
            false
        );
        this.charactersList.add(enemyToAddForTrigger);
        this.boundariesList.add(new EnemyTriggerVerticalBoundary(
            this.mainSketch,
            startXPos + 500 + 4 * Constants.DEFAULT_BLOCK_SIZE,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            this.isActive,
            enemyToAddForTrigger
        ));

        // section floor
        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            startXPos,
            Constants.LEVEL_FLOOR_Y_POSITION,
            2000,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));
    }

    private void setupActivateEndWrongSection(final int startXPos) {
        // section floor
        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            startXPos,
            Constants.LEVEL_FLOOR_Y_POSITION,
            this.mainSketch.getCurrentActiveLevelWidth() - startXPos - 2 * Constants.PLAYER_DIAMETER,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        this.blocksList.add(new EventBlock(
            this.mainSketch,
            startXPos + 50,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            Constants.DEFAULT_EVENT_BLOCK_HEIGHT,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        Set<Enemy> enemyAfterStairToTrigger = new HashSet<>();
        Enemy enemyToAdd = new Enemy(
            this.mainSketch,
            this.mainSketch.getCurrentActiveLevelWidth() - Constants.REGULAR_ENEMY_DIAMETER,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.REGULAR_ENEMY_DIAMETER,
            Constants.REGULAR_ENEMY_DIAMETER,
            -Constants.ENEMY_REGULAR_RUN_SPEED,
            false,
            true,
            false
        );
        this.charactersList.add(enemyToAdd);
        enemyAfterStairToTrigger.add(enemyToAdd);
        enemyToAdd = new Enemy(
            this.mainSketch,
            this.mainSketch.getCurrentActiveLevelWidth() - 2 * Constants.REGULAR_ENEMY_DIAMETER,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.REGULAR_ENEMY_DIAMETER,
            Constants.REGULAR_ENEMY_DIAMETER,
            -Constants.ENEMY_REGULAR_RUN_SPEED,
            false,
            true,
            false
        );
        this.charactersList.add(enemyToAdd);
        enemyAfterStairToTrigger.add(enemyToAdd);
        this.boundariesList.add(new EnemyTriggerVerticalBoundary(
            this.mainSketch,
            startXPos + 50 + Constants.DEFAULT_EVENT_BLOCK_WIDTH,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            this.isActive,
            enemyAfterStairToTrigger
        ));
    }

    /* ****** CORRECT SECTION ****** */

    private void setupActivateCorrectSection() {

    }

}

