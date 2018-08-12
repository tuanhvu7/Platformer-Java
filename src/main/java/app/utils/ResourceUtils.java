package app.utils;

import app.enums.ESongType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import processing.core.PImage;

import javax.swing.*;
import java.net.URISyntaxException;

public class ResourceUtils {

    /**
     * loop song
     */
    public static void loopSong(ESongType songType) {
        switch (songType) {
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
        switch (songType) {
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
                new Thread(() -> {
                    try {
                        PLAYER_ACTION_SONG_PLAYER.setCycleCount(1);
                        PLAYER_ACTION_SONG_PLAYER.play();
                        Thread.sleep((long) PLAYER_ACTION_SONG.getDuration().toMillis());  // wait for song duration
                        PLAYER_ACTION_SONG_PLAYER.stop();
                    } catch (InterruptedException ie) {
                    }
                }).start();
                break;

            case EventBlockDescent:
                // to reset level after player death song finishes without freezing game
                new Thread(() -> {
                    try {
                        EVENT_BLOCK_DESCENT_SONG_PLAYER.setCycleCount(1);
                        EVENT_BLOCK_DESCENT_SONG_PLAYER.play();
                        Thread.sleep((long) EVENT_BLOCK_DESCENT_SONG.getDuration().toMillis());  // wait for song duration
                        EVENT_BLOCK_DESCENT_SONG_PLAYER.stop();
                    } catch (InterruptedException ie) {
                    }
                }).start();
                break;

            default:
                break;
        }
    }

    /**
     * get song duration
     */
    public static double getSongDuration(ESongType songType) {
        switch (songType) {
            case PlayerDeath:
                return PLAYER_DEATH_SONG.getDuration().toMillis();
            case LevelComplete:
                return LEVEL_COMPLETE_SONG_.getDuration().toMillis();
            default:
                return 0;
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

    /*** assets ***/
    private static final String BACKGROUND_IMAGE_NAME = "sky-blue-bg.png";
    private static final ImageIcon BACKGROUND_IMAGE =
        new ImageIcon(ResourceUtils.class.getClassLoader().getResource(BACKGROUND_IMAGE_NAME));
    public static final PImage LEVEL_BACKGROUND_IMAGE = new PImage(BACKGROUND_IMAGE.getImage());

    // level song
    private static final String LEVEL_SONG_NAME = "level-song.mp3";
    private static final Media LEVEL_SONG = new Media(
        getResourcePathUri(LEVEL_SONG_NAME));
    private static final MediaPlayer LEVEL_SONG_PLAYER =
        new MediaPlayer(LEVEL_SONG);

    // player death song
    private static final String PLAYER_DEATH_SONG_NAME = "player-death-song.mp3";
    private static final Media PLAYER_DEATH_SONG = new Media(
        getResourcePathUri(PLAYER_DEATH_SONG_NAME));
    private static final MediaPlayer PLAYER_DEATH_SONG_PLAYER =
        new MediaPlayer(PLAYER_DEATH_SONG);

    // level complete song
    private static final String LEVEL_COMPLETE_SONG_NAME = "level-complete-song.mp3";
    private static final Media LEVEL_COMPLETE_SONG_ = new Media(
        getResourcePathUri(LEVEL_COMPLETE_SONG_NAME));
    private static final MediaPlayer LEVEL_COMPLETION_SONG_PLAYER =
        new MediaPlayer(LEVEL_COMPLETE_SONG_);

    // player action song
    private static final String PLAYER_ACTION_SONG_NAME = "player-action-sound.mp3";
    private static final Media PLAYER_ACTION_SONG = new Media(
        getResourcePathUri(PLAYER_ACTION_SONG_NAME));
    private static final MediaPlayer PLAYER_ACTION_SONG_PLAYER =
        new MediaPlayer(PLAYER_ACTION_SONG);

    // event block descent song
    private static final String EVENT_BLOCK_DESCENT_SONG_NAME = "event-block-descent-sound.mp3";
    private static final Media EVENT_BLOCK_DESCENT_SONG = new Media(
        getResourcePathUri(EVENT_BLOCK_DESCENT_SONG_NAME));
    private static final MediaPlayer EVENT_BLOCK_DESCENT_SONG_PLAYER =
        new MediaPlayer(EVENT_BLOCK_DESCENT_SONG);


    /**
     * Used to create URI for JavaFx Media
     *
     * @param fileName name of file to get uri path of
     * @return uri path to resource
     */
    private static String getResourcePathUri(String fileName) {
        try {
            return
                ResourceUtils.class.getClassLoader().getResource(fileName).toURI().toString();
        } catch (URISyntaxException e) {
            return "";
        }
    }

    /**
     * make this class "static"
     */
    private ResourceUtils() {
    }
}
