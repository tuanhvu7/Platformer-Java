package app;

import app.blocks.ABlock;
import app.characters.ACharacter;
import app.characters.Player;
import app.collectables.ACollectable;
import app.constants.Constants;
import app.enums.ESongType;
import app.factories.LevelFactory;
import app.levels.ALevel;
import app.menus.LevelSelectMenu;
import app.utils.ResourceUtils;
import app.viewbox.ViewBox;
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
    private Platformer mainSketch = this;

//    /*** LEVEL ***/
    // level select menu
    private LevelSelectMenu levelSelectMenu;

    /*** MUSIC ***/
    // stores current active level
    private WeakReference<ALevel> currentActiveLevel;

    // stores currently active level number
    private int currentActiveLevelNumber;

    private WeakReference<Thread> levelCompleteThread;

    public void settings() {
        new JFXPanel(); // initialize JavaFx toolkit
        size(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.levelSelectMenu = new LevelSelectMenu(this, true);
    }

    public void draw() { }


    public static void main(String[] args) {
        String str = "app.Platformer";
        PApplet.main(str);
    }

    /**
     * reset level
     */
    public void resetLevel() {
        ResourceUtils.stopSong();
        ResourceUtils.playSong(ESongType.PlayerDeath);

        // to reset level after player death song finishes without freezing game
        new Thread( new Runnable() {
            public void run()  {
                try  {
                    println("running reset level thread!!!");
                    if(levelCompleteThread != null) {
                        levelCompleteThread.get().interrupt();
                    }
                    getCurrentActivePlayer().makeNotActive();
                    Thread.sleep( (long) ResourceUtils.PLAYER_DEATH_SONG.getDuration().toMillis() );  // wait for song duration

                    boolean loadPlayerFromCheckPoint = getCurrentActiveLevel().isLoadPlayerFromCheckPoint();    // TODO: encapsulate
                    getCurrentActiveLevel().deactivateLevel();
                    LevelFactory levelFactory = new LevelFactory();
                    currentActiveLevel = new WeakReference( levelFactory.getLevel(mainSketch, true, loadPlayerFromCheckPoint) );
                }
                catch (InterruptedException ie)  { }
            }
        } ).start();

    }

    /**
     * complete level
     */
    public void handleLevelComplete() {
        ResourceUtils.stopSong();
        ResourceUtils.playSong(ESongType.LevelComplete);

        this.levelCompleteThread = new WeakReference(
            new Thread( new Runnable() {
                public void run()  {
                    try  {
                        println("running level complete thread!!!");
                        getCurrentActiveLevel().setHandlingLevelComplete(true);
                        getCurrentActivePlayer().resetControlPressed();
                        getCurrentActivePlayer().setVelocity(new PVector(Constants.PLAYER_LEVEL_COMPLETE_SPEED, 0));
                        unregisterMethod("keyEvent", getCurrentActivePlayer()); // disconnect this keyEvent() from main keyEvent()

                        Thread.sleep( (long) ResourceUtils.LEVEL_COMPLETE_SONG_.getDuration().toMillis() );  // wait for song duration
                        getCurrentActivePlayer().makeNotActive();
                        getCurrentActiveLevel().deactivateLevel();
                        currentActiveLevelNumber = 0;
                        levelSelectMenu.setupActivateMenu();
                    }
                    catch (InterruptedException ie)  { }
                }
            } )
        );
        this.levelCompleteThread.get().start();
    }



    //////////////

    public LevelSelectMenu getLevelSelectMenu() {
        return levelSelectMenu;
    }

    public ALevel getCurrentActiveLevel() {
        return currentActiveLevel.get();
    }

    public void setCurrentActiveLevel(ALevel currentActiveLevel) {
        this.currentActiveLevel = new WeakReference<ALevel>(currentActiveLevel);
    }

    public int getCurrentActiveLevelNumber() {
        return currentActiveLevelNumber;
    }

    public void setCurrentActiveLevelNumber(int currentActiveLevelNumber) {
        this.currentActiveLevelNumber = currentActiveLevelNumber;
    }


    ////////////////

    /**
     * return player of current active level
     */
    public Player getCurrentActivePlayer() {
        return this.currentActiveLevel.get().getPlayer();    // TODO: encapsulate
    }

    /**
     * return non-player app.characters of current active level
     */
    public Set<ACharacter> getCurrentActiveCharactersList() {
        return this.currentActiveLevel.get().getCharactersList();    // TODO: encapsulate
    }

    /**
     * return app.blocks of current active level
     */
    public Set<ABlock> getCurrentActiveBlocksList() {
        return this.currentActiveLevel.get().getBlocksList();    // TODO: encapsulate
    }

    /**
     * return app.collectables of current active level
     */
    public Set<ACollectable> getCurrentActiveLevelCollectables() {
        return this.currentActiveLevel.get().getCollectablesList();    // TODO: encapsulate
    }

    /**
     * return viewbox of current active level
     */
    public ViewBox getCurrentActiveViewBox() {
        return this.currentActiveLevel.get().getViewBox(); // TODO: encapsulate
    }

    /**
     * return width of current active level
     */
    public int getCurrentActiveLevelWidth() {
        return Constants.LEVELS_WIDTH_ARRAY[currentActiveLevelNumber];
    }

}
