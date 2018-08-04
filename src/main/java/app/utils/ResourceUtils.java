package app.utils;

import app.enums.ESongType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import processing.core.PImage;

import javax.swing.*;
import java.io.File;

public class ResourceUtils {

    /**
     * loop song
     */
    public static void loopSong(ESongType songType) {
        switch(songType) {
            case Level:
                LEVEL_SONG_PLAYER.setCycleCount(Integer.MAX_VALUE);
                LEVEL_SONG_PLAYER.play();
                break;

            default:
                break;
        }
    }

    /**
     * play song
     */
    public static void playSong(ESongType songType) {
        switch(songType) {
            case PlayerDeath:
                PLAYER_DEATH_SONG_PLAYER.setCycleCount(1);
                PLAYER_DEATH_SONG_PLAYER.play();
                break;

            case LevelComplete:
                LEVEL_COMPLETION_SONG_PLAYER.setCycleCount(1);
                LEVEL_COMPLETION_SONG_PLAYER.play();
                break;

            case PlayerAction:
                // to reset level after player death song finishes without freezing game
                new Thread( new Runnable() {
                    public void run()  {
                        try  {
                            PLAYER_ACTION_SONG_PLAYER.setCycleCount(1);
                            PLAYER_ACTION_SONG_PLAYER.play();
                            Thread.sleep( (long) PLAYER_ACTION_SONG.getDuration().toMillis() );  // wait for song duration
                            PLAYER_ACTION_SONG_PLAYER.stop();
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
                            EVENT_BLOCK_DESCENT_SONG_PLAYER.setCycleCount(1);
                            EVENT_BLOCK_DESCENT_SONG_PLAYER.play();
                            Thread.sleep( (long) EVENT_BLOCK_DESCENT_SONG.getDuration().toMillis() );  // wait for song duration
                            EVENT_BLOCK_DESCENT_SONG_PLAYER.stop();
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
    public static void stopSong() {
        LEVEL_SONG_PLAYER.stop();
        PLAYER_DEATH_SONG_PLAYER.stop();
        LEVEL_COMPLETION_SONG_PLAYER.stop();
        PLAYER_ACTION_SONG_PLAYER.stop();
        EVENT_BLOCK_DESCENT_SONG_PLAYER.stop();
    }

    /*** assets path ***/
    private static final String BACKGROUND_IMAGE_NAME = "sky-blue-bg.png";
    private static final ImageIcon BACKGROUND_IMAGE = new ImageIcon(getResourcePath(BACKGROUND_IMAGE_NAME));
    public static final PImage STAGE_BACKGROUND_IMAGE = new PImage(BACKGROUND_IMAGE.getImage());

    // level song
    private static final String LEVEL_SONG_NAME = "level-song.mp3";
    private static final Media LEVEL_SONG = new Media(
        convertPathToValidUri(getResourcePath(LEVEL_SONG_NAME)));
    private static final MediaPlayer LEVEL_SONG_PLAYER =
        new MediaPlayer(LEVEL_SONG);

    // player death song
    private static final String PLAYER_DEATH_SONG_NAME = "player-death-song.mp3";
    public static final Media PLAYER_DEATH_SONG = new Media(
        convertPathToValidUri(getResourcePath(PLAYER_DEATH_SONG_NAME)));
    private static final MediaPlayer PLAYER_DEATH_SONG_PLAYER =
        new MediaPlayer(PLAYER_DEATH_SONG);

    // level complete song
    private static final String LEVEL_COMPLETE_SONG_NAME = "level-complete-song.mp3";
    public static final Media LEVEL_COMPLETE_SONG_ = new Media(
        convertPathToValidUri(getResourcePath(LEVEL_COMPLETE_SONG_NAME)));
    private static final MediaPlayer LEVEL_COMPLETION_SONG_PLAYER =
        new MediaPlayer(LEVEL_COMPLETE_SONG_);

    // player action song
    private static final String PLAYER_ACTION_SONG_NAME = "player-action-sound.mp3";
    private static final Media PLAYER_ACTION_SONG = new Media(
        convertPathToValidUri(getResourcePath(PLAYER_ACTION_SONG_NAME)));
    private static final MediaPlayer PLAYER_ACTION_SONG_PLAYER =
        new MediaPlayer(PLAYER_ACTION_SONG);

    // event block descent song
    private static final String EVENT_BLOCK_DESCENT_SONG_NAME = "event-block-descent-sound.mp3";
    private static final Media EVENT_BLOCK_DESCENT_SONG = new Media(
        convertPathToValidUri(getResourcePath(EVENT_BLOCK_DESCENT_SONG_NAME)));
    private static final MediaPlayer EVENT_BLOCK_DESCENT_SONG_PLAYER =
        new MediaPlayer(EVENT_BLOCK_DESCENT_SONG);

    /**
     * convert given string to valid uri path and return result
     */
    private static String convertPathToValidUri(String path) {
        return path
            .replace(" ", "%20")            // space is illegal character
            .replace("\\", "/")             // back-slash illegal character
            .replace("C:/", "file:///C:/"); // prevent unsupported protocol c
    }

    /**
     * @param fileName name of file in resources
     * @return path to file in resources
     */
    private static String getResourcePath(String fileName) {
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("src/main/resources/");
        pathBuilder.append(fileName);
        File file = new File(pathBuilder.toString());
        return file.getAbsolutePath();
    }
}
