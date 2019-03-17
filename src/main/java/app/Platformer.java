package app;

import app.drawable.blocks.ABlock;
import app.drawable.characters.ACharacter;
import app.drawable.characters.Player;
import app.drawable.collectables.ACollectable;
import app.constants.Constants;
import app.enums.ESongType;
import app.factories.LevelFactory;
import app.drawable.levels.ALevel;
import app.drawable.menus.LevelSelectMenu;
import app.utils.ResourceUtils;
import app.drawable.viewbox.ViewBox;
import javafx.embed.swing.JFXPanel;
import processing.core.PApplet;
import processing.core.PVector;

import java.lang.ref.WeakReference;
import java.util.Set;

/**
 * main app
 */
public class Platformer extends PApplet {

    // to pass into threads
    private final Platformer mainSketch = this;

    // level select menu
    private LevelSelectMenu levelSelectMenu;

    // stores current active level
    private WeakReference<ALevel> currentActiveLevel;

    // stores currently active level number
    private int currentActiveLevelNumber;

    // thread to handle level completion; stored in variable to be able interrupt
    private Thread levelCompleteThread;

    /**
     * runs once
     */
    public void settings() {
        new JFXPanel(); // initialize JavaFx toolkit
        size(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.levelSelectMenu = new LevelSelectMenu(this, true);
    }

    /**
     * runs continuously; need this to run draw() for levels
     */
    public void draw() {
    }


    public static void main(String[] args) {
        PApplet.main("app.Platformer");
    }

    /**
     * reset level
     */
    public void resetLevel() {
        // to reset level after player death song finishes without freezing game
        new Thread(() -> {
            try {
                if (levelCompleteThread != null) {
                    levelCompleteThread.interrupt();
                    levelCompleteThread = null;
                }

                this.getCurrentActivePlayer().makeNotActive();
                this.currentActiveLevel.get().setPlayer(null);  // to stop interactions with player

                ResourceUtils.stopSong();
                ResourceUtils.playSong(ESongType.PLAYER_DEATH);
                Thread.sleep((long) ResourceUtils.getSongDuration(ESongType.PLAYER_DEATH));  // wait for song duration

                boolean loadPlayerFromCheckPoint = getCurrentActiveLevel().isLoadPlayerFromCheckPoint();
                getCurrentActiveLevel().deactivateLevel();
                LevelFactory levelFactory = new LevelFactory();
                currentActiveLevel = new WeakReference<>(levelFactory.getLevel(mainSketch, true, loadPlayerFromCheckPoint));
            } catch (InterruptedException ie) {
            }
        }).start();

    }

    /**
     * complete level
     */
    public void handleLevelComplete() {
        this.levelCompleteThread =
            new Thread(() -> {
                try {
                    getCurrentActiveLevel().setHandlingLevelComplete(true);
                    getCurrentActivePlayer().resetControlPressed();
                    getCurrentActivePlayer().setVel(new PVector(Constants.PLAYER_LEVEL_COMPLETE_SPEED, 0));
                    unregisterMethod("keyEvent", getCurrentActivePlayer()); // disconnect this keyEvent() from main keyEvent()

                    ResourceUtils.stopSong();
                    ResourceUtils.playSong(ESongType.LEVEL_COMPLETE);
                    Thread.sleep((long) ResourceUtils.getSongDuration(ESongType.LEVEL_COMPLETE));  // wait for song duration

                    getCurrentActiveLevel().deactivateLevel();
                    currentActiveLevelNumber = 0;
                    levelSelectMenu.setupActivateMenu();
                } catch (InterruptedException ie) {
                }
            });
        this.levelCompleteThread.start();
    }


    /*** getters and setters ***/
    public LevelSelectMenu getLevelSelectMenu() {
        return this.levelSelectMenu;
    }

    public ALevel getCurrentActiveLevel() {
        return this.currentActiveLevel.get();
    }

    public void setCurrentActiveLevel(ALevel currentActiveLevel) {
        this.currentActiveLevel = new WeakReference<>(currentActiveLevel);
    }

    public int getCurrentActiveLevelNumber() {
        return this.currentActiveLevelNumber;
    }

    public void setCurrentActiveLevelNumber(int currentActiveLevelNumber) {
        this.currentActiveLevelNumber = currentActiveLevelNumber;
    }

    /**
     * return player of current active level
     */
    public Player getCurrentActivePlayer() {
        return this.currentActiveLevel.get().getPlayer();
    }

    /**
     * return non-player characters of current active level
     */
    public Set<ACharacter> getCurrentActiveCharactersList() {
        return this.currentActiveLevel.get().getLevelDrawableCollection().getCharactersList();
    }

    /**
     * return blocks of current active level
     */
    public Set<ABlock> getCurrentActiveBlocksList() {
        return this.currentActiveLevel.get().getLevelDrawableCollection().getBlocksList();
    }

    /**
     * return collectables of current active level
     */
    public Set<ACollectable> getCurrentActiveLevelCollectables() {
        return this.currentActiveLevel.get().getLevelDrawableCollection().getCollectablesList();
    }

    /**
     * return viewbox of current active level
     */
    public ViewBox getCurrentActiveViewBox() {
        return this.currentActiveLevel.get().getViewBox();
    }

    /**
     * return width of current active level
     */
    public int getCurrentActiveLevelWidth() {
        return Constants.LEVELS_WIDTH_ARRAY[this.currentActiveLevelNumber];
    }

    /**
     * return height of current active level
     */
    public int getCurrentActiveLevelHeight() {
        return Constants.LEVELS_HEIGHT_ARRAY[this.currentActiveLevelNumber];
    }

}
