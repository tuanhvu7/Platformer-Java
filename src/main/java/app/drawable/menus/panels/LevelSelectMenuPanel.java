package app.drawable.menus.panels;

import app.Platformer;
import app.constants.Constants;
import app.enums.EProcessingMethods;
import app.factories.LevelFactory;
import app.utils.ResourceUtils;

/**
 * panel in level select menu
 */
public class LevelSelectMenuPanel extends APanel {

    // level associated with this
    private final int panelLevel;

    // true means load level from checkpoint
    private boolean loadLevelFromCheckpoint;

    /**
     * set properties of this
     */
    public LevelSelectMenuPanel(Platformer mainSketch, int panelLevel, int leftX, int topY, int width, int height, boolean initAsActive) {
        super(mainSketch, Constants.DEFAULT_PANEL_COLOR, panelLevel + "", leftX, topY, width, height, initAsActive);
        this.panelLevel = panelLevel;
        this.loadLevelFromCheckpoint = false;
    }

    /**
     * active and add this to game
     */
    @Override
    void makeActive() {
        this.mainSketch.registerMethod(EProcessingMethods.DRAW.toString(), this); // connect this draw() from main draw()
        this.mainSketch.registerMethod(EProcessingMethods.MOUSE_EVENT.toString(), this); // connect this mouseEvent() from main mouseEvent()
    }

    /**
     * deactivate and remove this from game
     */
    @Override
    public void makeNotActive() {
        this.mainSketch.unregisterMethod(EProcessingMethods.DRAW.toString(), this); // disconnect this draw() from main draw()
        this.mainSketch.unregisterMethod(EProcessingMethods.MOUSE_EVENT.toString(), this); // connect this mouseEvent() from main mouseEvent()
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
        this.mainSketch.setCurrentActiveLevel(levelFactory.getLevel(this.mainSketch, true, this.loadLevelFromCheckpoint));
    }

    /**
     * toggle loadLevelFromCheckpoint
     */
    public void toggleLoadLevelFromCheckpoint() {
        this.loadLevelFromCheckpoint = !this.loadLevelFromCheckpoint;
        if (this.loadLevelFromCheckpoint) {
            this.panelColor = Constants.ALTERNATE_PANEL_COLOR;
        } else {
            this.panelColor = Constants.DEFAULT_PANEL_COLOR;
        }
    }
}

