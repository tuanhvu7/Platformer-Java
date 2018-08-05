package app.drawable.menus.panels;

import app.Platformer;
import app.enums.EPauseMenuButtonType;
import app.enums.ESongType;
import app.drawable.interfaces.IDrawable;
import app.utils.ResourceUtils;

/**
 * panels in pause menu
 */
public class PauseMenuPanel extends APanel implements IDrawable {

    // horizontal offset of this due to viewbox
    private final int horizontalOffset;

    // panel type
    private final EPauseMenuButtonType panelType;

    /**
     * set properties of this
     */
    public PauseMenuPanel(Platformer mainSketch,
                          EPauseMenuButtonType panelType,
                          int leftX, int topY, int width, int height,
                          int horizontalOffset, boolean isActive) {
        super(mainSketch, panelType.name(), leftX, topY, width, height, isActive);
        this.horizontalOffset = horizontalOffset;
        this.panelType = panelType;
    }

    /**
     * to execute when this panel is clicked
     */
    @Override
    void executeWhenClicked() {
        if (panelType == EPauseMenuButtonType.Continue) {
            ResourceUtils.loopSong(ESongType.Level);
            this.mainSketch.getCurrentActiveLevel().setPaused(false);
            this.mainSketch.getCurrentActiveLevel().closePauseMenu();

        } else {
            this.mainSketch.getCurrentActiveLevel().closePauseMenu();
            this.mainSketch.getCurrentActiveLevel().deactivateLevel();
            this.mainSketch.setCurrentActiveLevelNumber(0);
            this.mainSketch.getLevelSelectMenu().setupActivateMenu();
        }

        this.mainSketch.loop();
    }

    /**
     * return if mouse position inside this panel
     */
    @Override
    boolean isMouseIn() {
        return this.mainSketch.mouseX > this.leftX - horizontalOffset &&   // subtract offset since mouseX is unaffected by viewbox
            this.mainSketch.mouseX < this.rightX - horizontalOffset &&  // subtract offset since mouseX is unaffected by viewbox
            this.mainSketch.mouseY > this.topY &&
            this.mainSketch.mouseY < this.bottomY;
    }

}

