package app.drawable.levels;

import app.Platformer;
import app.constants.Constants;
import app.drawable.blocks.ItemBlock;
import app.drawable.boundaries.HorizontalBoundary;
import app.drawable.characters.Enemy;
import app.drawable.characters.Player;
import app.drawable.collectables.HealthItem;
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

        this.viewBox = new ViewBox(this.mainSketch, 0, 0, true);
        this.player = new Player(this.mainSketch, 200, 0, Constants.PLAYER_DIAMETER, true);

        this.levelDrawableCollection.addDrawable(new HealthItem(
            this.mainSketch,
            -1,
            this.checkpointXPos,
            Constants.LEVEL_FLOOR_Y_POSITION - Constants.HEALTH_ITEM_SIZE,
            Constants.HEALTH_ITEM_SIZE,
            Constants.HEALTH_ITEM_SIZE,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true)
        );

        this.setupActivateBeforeCheckpoint();
    }

    private void setupActivateBeforeCheckpoint() {
        // stage floor
        this.levelDrawableCollection.addDrawable(new HorizontalBoundary(
            this.mainSketch,
            0,
            Constants.LEVEL_FLOOR_Y_POSITION,
            2500,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            true
        ));

        this.levelDrawableCollection.addDrawable(new Enemy(
            mainSketch,
            500,
            Constants.LEVEL_FLOOR_Y_POSITION - (2 * Constants.REGULAR_ENEMY_DIAMETER),
            Constants.REGULAR_ENEMY_DIAMETER,
            -Constants.ENEMY_SLOW_RUN_SPEED,
            false,
            true,
            true
        ));

        HealthItem itemForBlockNearStart =
            new HealthItem(
                mainSketch,
                1,
                0,
                0,
                Constants.HEALTH_ITEM_SIZE,
                Constants.HEALTH_ITEM_SIZE,
                1,
                false
            );
        this.levelDrawableCollection.addDrawable((itemForBlockNearStart));
        this.levelDrawableCollection.addDrawable(new ItemBlock(
            mainSketch,
            500,
            Constants.LEVEL_FLOOR_Y_POSITION - 2 * Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BLOCK_SIZE,
            Constants.DEFAULT_BLOCK_SIZE,
            itemForBlockNearStart,
            1,
            false,
            true
        ));
    }

}
