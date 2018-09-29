package app.drawable.levels;

import app.Platformer;
import app.constants.Constants;
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
            this.viewBox = new ViewBox(this.mainSketch, Constants.LEVELS_WIDTH_ARRAY[2], 0, this.isActive);
            this.player = new Player(this.mainSketch, Constants.LEVELS_WIDTH_ARRAY[2] + 200, 0, Constants.PLAYER_DIAMETER, this.isActive);
        }
    }

}

