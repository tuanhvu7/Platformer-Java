package app.drawable.levels;

import app.Platformer;
import app.constants.Constants;
import app.drawable.boundaries.HorizontalBoundary;
import app.drawable.characters.Enemy;
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
            this.viewBox = new ViewBox(
                this.mainSketch,
                Constants.LEVELS_WIDTH_ARRAY[this.mainSketch.getCurrentActiveLevelNumber()] / 2,
                0,
                this.isActive);
            this.player = new Player(
                this.mainSketch,
                (Constants.LEVELS_WIDTH_ARRAY[this.mainSketch.getCurrentActiveLevelNumber()] / 2)
                    + (Constants.SCREEN_WIDTH / 2) + 75,
                0,
                Constants.PLAYER_DIAMETER,
                this.isActive);
        }

        this.setupActivateWrongSection();
        this.setupActivateCorrectSection();
    }

    private void setupActivateWrongSection() {
        final int startWrongSectionXPos =
            Constants.LEVELS_WIDTH_ARRAY[this.mainSketch.getCurrentActiveLevelNumber()] / 2;

        this.charactersList.add(new Enemy(
            this.mainSketch,
            startWrongSectionXPos + (Constants.SCREEN_WIDTH / 2) - 250,
            0,
            Constants.BIG_ENEMY_DIAMETER,
            Constants.ENEMY_REGULAR_RUN_SPEED,
            false,
            true,
            true,
            this.isActive)
        );

        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            startWrongSectionXPos,
            Constants.LEVEL_FLOOR_Y_POSITION,
            1000,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        this.boundariesList.add(new HorizontalBoundary(
            this.mainSketch,
            startWrongSectionXPos + 3000,
            Constants.LEVEL_FLOOR_Y_POSITION,
            1500,
            Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
            true,
            this.isActive
        ));

        for (int i = 0; i < 5; i++) {
            this.boundariesList.add(new HorizontalBoundary(
                this.mainSketch,
                startWrongSectionXPos + 1000 + 200 * i,
                Constants.LEVEL_FLOOR_Y_POSITION - 200 - 225 * i,
                Constants.PLAYER_DIAMETER * 2,
                Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
                true,
                this.isActive
            ));
        }

        for (int i = 0; i < 4; i++) {
            this.boundariesList.add(new HorizontalBoundary(
                this.mainSketch,
                startWrongSectionXPos + 2000 + 200 * i,
                Constants.LEVEL_FLOOR_Y_POSITION - 975,
                Constants.PLAYER_DIAMETER * 2,
                Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
                true,
                this.isActive
            ));
        }

        for (int i = 0; i < 5; i++) {
            this.boundariesList.add(new HorizontalBoundary(
                this.mainSketch,
                startWrongSectionXPos + 1400 + 200 * i,
                Constants.LEVEL_FLOOR_Y_POSITION - 300,
                Constants.PLAYER_DIAMETER * 2,
                Constants.DEFAULT_BOUNDARY_LINE_THICKNESS,
                true,
                this.isActive
            ));
        }
    }

    private void setupActivateCorrectSection() {

    }

}

