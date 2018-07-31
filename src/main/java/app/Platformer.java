package app;

import app.constants.Constants;
import app.enums.ESongType;
import app.utils.ResourceUtils;
import javafx.embed.swing.JFXPanel;
import processing.core.PApplet;

/**
 * main app
 */
public class Platformer extends PApplet {

    public void settings() {
        new JFXPanel(); // initialize JavaFx toolkit
        size(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        ResourceUtils.LEVEL_SONG_PLAYER.setCycleCount(Integer.MAX_VALUE);
        ResourceUtils.LEVEL_SONG_PLAYER.play();
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
        stopSong();
        playSong(ESongType.PlayerDeath);

        // to reset level after player death song finishes without freezing game
        new Thread( new Runnable() {
            public void run()  {
                try  {
                    println("running reset level thread!!!");
                    if(global_level_complete_thread != null) {
                        global_level_complete_thread.get().interrupt();
                    }
                    getCurrentActivePlayer().makeNotActive();
                    Thread.sleep( (long) global_player_death_song.getDuration().toMillis() );  // wait for song duration

                    boolean loadPlayerFromCheckPoint = global_current_active_level.get().loadPlayerFromCheckPoint;    // TODO: encapsulate
                    global_current_active_level.get().deactivateLevel();
                    LevelFactory levelFactory = new LevelFactory();
                    global_current_active_level = new WeakReference( levelFactory.getLevel(true, loadPlayerFromCheckPoint) );
                }
                catch (InterruptedException ie)  { }
            }
        } ).start();

    }

    /**
     * complete level
     */
    public void handleLevelComplete() {
        stopSong();
        playSong(ESongType.LevelComplete);

        global_level_complete_thread = new WeakReference(
            new Thread( new Runnable() {
                public void run()  {
                    try  {
                        println("running level complete thread!!!");
                        global_current_active_level.get().isHandlingLevelComplete = true;    // TODO: encapsulate
                        getCurrentActivePlayer().resetControlPressed();
                        getCurrentActivePlayer().setVelocity(new PVector(Constants.PLAYER_LEVEL_COMPLETE_SPEED, 0));
                        unregisterMethod("keyEvent", getCurrentActivePlayer()); // disconnect this keyEvent() from main keyEvent()

                        Thread.sleep( (long) global_level_complete_song.getDuration().toMillis() );  // wait for song duration
                        getCurrentActivePlayer().makeNotActive();
                        global_current_active_level.get().deactivateLevel();
                        global_current_active_level_number = 0;
                        global_level_select_menu.setupActivateMenu();
                    }
                    catch (InterruptedException ie)  { }
                }
            } )
        );
        global_level_complete_thread.get().start();
    }

    /**
     * loop song
     */
    private void loopSong(ESongType songType) {
        switch(songType) {
            case Level:
                ResourceUtils.LEVEL_SONG_PLAYER.setCycleCount(Integer.MAX_VALUE);
                ResourceUtils.LEVEL_SONG_PLAYER.play();
                break;

            default:
                break;
        }
    }

    /**
     * play song
     */
    private void playSong(ESongType songType) {
        switch(songType) {
            case PlayerDeath:
                global_player_death_song_player.setCycleCount(1);
                global_player_death_song_player.play();
                break;

            case LevelComplete:
                global_level_complete_song_player.setCycleCount(1);
                global_level_complete_song_player.play();
                break;

            case PlayerAction:
                // to reset level after player death song finishes without freezing game
                new Thread( new Runnable() {
                    public void run()  {
                        try  {
                            global_player_action_song_player.setCycleCount(1);
                            global_player_action_song_player.play();
                            Thread.sleep( (long) global_player_action_song.getDuration().toMillis() );  // wait for song duration
                            global_player_action_song_player.stop();
                        }
                        catch (InterruptedException ie)  { }
                    }
                } ).start();
                break;

            case EventBlockDescent:
                // to reset level after player death song finishes without freezing game
                new Thread( new Runnable() {
                    public void run()  {
                        try  {
                            global_event_block_descent_song_player.setCycleCount(1);
                            global_event_block_descent_song_player.play();
                            Thread.sleep( (long) global_event_block_descent_song.getDuration().toMillis() );  // wait for song duration
                            global_event_block_descent_song_player.stop();
                        }
                        catch (InterruptedException ie)  { }
                    }
                } ).start();
                break;

            default:
                break;
        }
    }

    /**
     * stop song
     */
    private void stopSong() {
        ResourceUtils.LEVEL_SONG_PLAYER.stop();
        global_player_death_song_player.stop();
        global_level_complete_song_player.stop();
        global_player_action_song_player.stop();
        global_event_block_descent_song_player.stop();
    }

}
