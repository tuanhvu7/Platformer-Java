package app.drawable.levels;

import app.Platformer;
import app.constants.Constants;
import app.drawable.boundaries.HorizontalBoundary;
import app.drawable.characters.Player;
import app.drawable.interfaces.IDrawable;
import app.drawable.viewbox.ViewBox;
import app.enums.ESongType;
import app.utils.ResourceUtils;

/**
 * Level two
 */
public class LevelTwo extends ALevel implements IDrawable {

    /**
     * sets properties, boundaries, and characters of this
     */
    public LevelTwo(Platformer mainSketch, boolean isActive, boolean loadPlayerFromCheckPoint) {
        super(mainSketch, isActive, loadPlayerFromCheckPoint, 0);
    }

    /**
     * setup and activate this
     */
    @Override
    public void setUpActivateLevel() {
        this.makeActive();

        ResourceUtils.loopSong(ESongType.LEVEL);

        if (this.loadPlayerFromCheckPoint) {

        } else {
            this.viewBox = new ViewBox(this.mainSketch, 0, 0, this.isActive);
            this.player = new Player(this.mainSketch, 200, 0, Constants.PLAYER_DIAMETER, this.isActive);
        }

        this.setupActivateWrongSection();
        this.setupActivateCorrectSection();
    }

    private void setupActivateWrongSection() {
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
    }

    private void setupActivateCorrectSection() {

    }

}

