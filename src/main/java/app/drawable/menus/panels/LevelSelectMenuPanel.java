package app.drawable.menus.panels;

import app.Platformer;
import app.constants.Constants;
import app.factories.LevelFactory;
import app.utils.ResourceUtils;
import processing.event.KeyEvent;

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
    public LevelSelectMenuPanel(Platformer mainSketch, int panelLevel, int leftX, int topY, int width, int height, boolean isActive) {
        super(mainSketch, Constants.DEFAULT_PANEL_COLOR, panelLevel + "", leftX, topY, width, height, isActive);
        this.panelLevel = panelLevel;
        this.loadLevelFromCheckpoint = false;
    }

    /**
     * active and add this to game
     */
    @Override
    void makeActive() {
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()
        this.mainSketch.registerMethod("mouseEvent", this); // connect this mouseEvent() from main mouseEvent()
        this.mainSketch.registerMethod("keyEvent", this); // connect this keyEvent() from main keyEvent()
    }

    /**
     * deactivate and remove this from game
     */
    @Override
    public void makeNotActive() {
        this.mainSketch.unregisterMethod("draw", this); // disconnect this draw() from main draw()
        this.mainSketch.unregisterMethod("mouseEvent", this); // connect this mouseEvent() from main mouseEvent()
        this.mainSketch.unregisterMethod("keyEvent", this); // connect this keyEvent() from main keyEvent()
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
     * handle panel keypress controls
     */
    public void keyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.PRESS) {
            String keyPressed = keyEvent.getKey() + "";
            if ("c".equalsIgnoreCase(keyPressed)) {
                this.loadLevelFromCheckpoint = !this.loadLevelFromCheckpoint;
                if (this.loadLevelFromCheckpoint) {
                    this.panelColor = Constants.LEVEL_CHECKPOINT_LOAD_PANEL_COLOR;
                } else {
                    this.panelColor = Constants.DEFAULT_PANEL_COLOR;
                }
            }
        }
    }
}

