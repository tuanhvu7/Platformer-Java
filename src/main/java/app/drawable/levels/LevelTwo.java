package app.drawable.levels;

import app.Platformer;
import app.drawable.boundaries.HorizontalBoundary;
import app.drawable.boundaries.VerticalBoundary;
import app.drawable.characters.Enemy;
import app.drawable.characters.Player;
import app.constants.Constants;
import app.enums.ESongType;
import app.drawable.interfaces.IDrawable;
import app.utils.ResourceUtils;
import app.drawable.viewbox.ViewBox;

/**
 * Level two
 */
public class LevelTwo extends ALevel implements IDrawable {

    /**
     * sets properties, boundaries, and characters of this
     */
    public LevelTwo(Platformer mainSketch, boolean isActive, boolean loadPlayerFromCheckPoint) {
        super(mainSketch, isActive, loadPlayerFromCheckPoint);
    }

    /**
     * setup and activate this
     */
    @Override
    public void setUpActivateLevel() {
        this.makeActive();

        this.viewBox = new ViewBox(this.mainSketch, 0, 0, this.isActive);
        this.player = new Player(this.mainSketch, 200, 0, Constants.PLAYER_DIAMETER, this.isActive);

        ResourceUtils.loopSong(ESongType.Level);

        charactersList.add(new Enemy(
            this.mainSketch,
            this.mainSketch.getCurrentActiveLevelWidth() - 500,
            0,
            Constants.BIG_ENEMY_DIAMETER,
            -Constants.ENEMY_RUN_SPEED,
            false,
            false,
            true,
            this.isActive)
        );

        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            this.mainSketch.width / 2,
            this.mainSketch.height / 2,
            100,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            this.mainSketch.width / 2,
            this.mainSketch.height / 4,
            100,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            this.mainSketch.width / 2,
            this.mainSketch.height / 6,
            100,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            false,
            this.isActive
        ));

        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            this.mainSketch.width / 2,
            this.mainSketch.height / 8,
            100,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        this.boundariesList.add(new VerticalBoundary(
            this.mainSketch,
            this.mainSketch.width / 2,
            this.mainSketch.height / 2,
            this.mainSketch.height / 2 - 100,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            this.isActive
        ));
    }

}

