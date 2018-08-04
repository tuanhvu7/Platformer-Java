package app.factories;

import app.Platformer;
import app.drawable.levels.ALevel;
import app.drawable.levels.LevelOne;
import app.drawable.levels.LevelTwo;

/**
 * for creating appropriate level
 */
public class LevelFactory {

    /**
     * return appropriate level
     */
    public ALevel getLevel(Platformer mainSketch, boolean makeActive, boolean loadPlayerFromCheckPoint) {
        switch (mainSketch.getCurrentActiveLevelNumber()) {
            case 1:
                return new LevelOne(mainSketch, makeActive, loadPlayerFromCheckPoint);

            case 2:
                return new LevelTwo(mainSketch, makeActive, loadPlayerFromCheckPoint);

            default:
                return null;
        }
    }
}
