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
            case LEVEL_SELECT_MENU:
                LEVEL_SELECT_MENU_SONG_PLAYER.setCycleCount(Integer.MAX_VALUE);
                LEVEL_SELECT_MENU_SONG_PLAYER.play();
                break;

            case LEVEL:
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
            case PLAYER_DEATH:
                PLAYER_DEATH_SONG_PLAYER.setCycleCount(1);
                PLAYER_DEATH_SONG_PLAYER.play();
                break;

            case LEVEL_COMPLETE:
                LEVEL_COMPLETION_SONG_PLAYER.setCycleCount(1);
                LEVEL_COMPLETION_SONG_PLAYER.play();
                break;

            case PLAYER_DAMAGE:
                // to player damage song in parallel with level song
                new Thread(() -> {
                    try {
                        PLAYER_DAMAGE_SONG_PLAYER.setCycleCount(1);
                        PLAYER_DAMAGE_SONG_PLAYER.play();
                        Thread.sleep((long) PLAYER_DAMAGE_SONG.getDuration().toMillis());  // wait for song duration
                        PLAYER_DAMAGE_SONG_PLAYER.stop();
                    } catch (InterruptedException ie) {
                    }
                }).start();
                break;

            case PLAYER_ACTION:
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

            case EVENT_BLOCK_DESCENT:
                // to play event block descent song in parallel with level song
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
     * get song duration in milliseconds
     */
    public static double getSongDurationMilliSec(ESongType songType) {
        switch (songType) {
            case PLAYER_DEATH:
                return PLAYER_DEATH_SONG.getDuration().toMillis();
            case PLAYER_DAMAGE:
                return PLAYER_DAMAGE_SONG.getDuration().toMillis();
            case LEVEL_COMPLETE:
                return LEVEL_COMPLETE_SONG_.getDuration().toMillis();
            default:
                return 0;
        }
    }

    /**
     * stop song
     */
    public static void stopSong() {
        LEVEL_SELECT_MENU_SONG_PLAYER.stop();
        LEVEL_SONG_PLAYER.stop();
        PLAYER_DEATH_SONG_PLAYER.stop();
        LEVEL_COMPLETION_SONG_PLAYER.stop();
        PLAYER_ACTION_SONG_PLAYER.stop();
        EVENT_BLOCK_DESCENT_SONG_PLAYER.stop();
    }

    /*** assets ***/
    private static final String LEVEL_BACKGROUND_IMAGE_NAME = "sky-bg.png";
    private static final ImageIcon LEVEL_BACKGROUND_IMAGE_ICON =
        new ImageIcon(ResourceUtils.class.getClassLoader().getResource(LEVEL_BACKGROUND_IMAGE_NAME));
    public static final PImage LEVEL_BACKGROUND_IMAGE = new PImage(LEVEL_BACKGROUND_IMAGE_ICON.getImage());

    // level select menu song
    private static final String LEVEL_SELECT_MENU_SONG_NAME = "level-select-menu-song.mp3";
    private static final Media LEVEL_SELECT_MENU_SONG = new Media(
        getResourcePathUri(LEVEL_SELECT_MENU_SONG_NAME));
    private static final MediaPlayer LEVEL_SELECT_MENU_SONG_PLAYER =
        new MediaPlayer(LEVEL_SELECT_MENU_SONG);

    // level song
    private static final String LEVEL_SONG_NAME = "level-song.mp3";
    private static final Media LEVEL_SONG = new Media(
        getResourcePathUri(LEVEL_SONG_NAME));
    private static final MediaPlayer LEVEL_SONG_PLAYER =
        new MediaPlayer(LEVEL_SONG);

    // player damage song
    private static final String PLAYER_DAMAGE_SONG_NAME = "player-damage-song.mp3";
    private static final Media PLAYER_DAMAGE_SONG = new Media(
        getResourcePathUri(PLAYER_DAMAGE_SONG_NAME));
    private static final MediaPlayer PLAYER_DAMAGE_SONG_PLAYER =
        new MediaPlayer(PLAYER_DAMAGE_SONG);

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
    private static final String PLAYER_ACTION_SONG_NAME = "player-action-song.mp3";
    private static final Media PLAYER_ACTION_SONG = new Media(
        getResourcePathUri(PLAYER_ACTION_SONG_NAME));
    private static final MediaPlayer PLAYER_ACTION_SONG_PLAYER =
        new MediaPlayer(PLAYER_ACTION_SONG);

    // event block descent song
    private static final String EVENT_BLOCK_DESCENT_SONG_NAME = "event-block-descent-song.mp3";
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