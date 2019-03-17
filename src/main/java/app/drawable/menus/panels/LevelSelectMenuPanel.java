package app.drawable.menus.panels;

import app.Platformer;
import app.factories.LevelFactory;
import app.utils.ResourceUtils;

/**
 * panel in level select menu
 */
public class LevelSelectMenuPanel extends APanel {

    // level associated with this
    private final int panelLevel;

    /**
     * set properties of this
     */
    public LevelSelectMenuPanel(Platformer mainSketch, int panelLevel, int leftX, int topY, int width, int height, boolean isActive) {
        super(mainSketch, panelLevel + "", leftX, topY, width, height, isActive);
        this.panelLevel = panelLevel;
    }

    /**
     * to execute when this panel is clicked
     */
    @Override
    void executeWhenClicked() {
        this.mainSketch.getLevelSelectMenu().deactivateMenu();
        ResourceUtils.stopSong();
        // setup and load level associated with this
        this.mainSketch.setCurrentActiveLevelNumber(this.panelLevel);
        LevelFactory levelFactory = new LevelFactory();
        this.mainSketch.setCurrentActiveLevel(levelFactory.getLevel(this.mainSketch, true, false));
    }
}

