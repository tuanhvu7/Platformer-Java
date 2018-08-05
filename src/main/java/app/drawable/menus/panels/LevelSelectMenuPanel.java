package app.drawable.menus.panels;

import app.Platformer;
import app.factories.LevelFactory;
import app.drawable.interfaces.IDrawable;

/**
 * panel in level select menu
 */
public class LevelSelectMenuPanel extends APanel implements IDrawable {

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
        // setup and load level associated with this
        this.mainSketch.getLevelSelectMenu().deactivateMenu();
        this.mainSketch.setCurrentActiveLevelNumber(this.panelLevel);
        LevelFactory levelFactory = new LevelFactory();
        this.mainSketch.setCurrentActiveLevel(levelFactory.getLevel(this.mainSketch, true, false));
    }
}

