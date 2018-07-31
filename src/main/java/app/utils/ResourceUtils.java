package app.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import processing.core.PImage;

import javax.swing.*;
import java.io.File;

public class ResourceUtils {

    /*** assets path ***/
    private static final String BACKGROUND_IMAGE_NAME = "sky-blue-bg.png";
    private static final String LEVEL_SONG_NAME = "level-song.mp3";
    private static final String PLAYER_DEATH_SONG_NAME = "player-death-song.mp3";
    private static final String LEVEL_COMPLETE_SONG_NAME = "level-complete-song.mp3";
    private static final String PLAYER_ACTION_SOUND_NAME = "player-action-sound.mp3";
    private static final String EVENT_BLOCK_DESCENT_SOUND_NAME = "event-block-descent-sound.mp3";

    private static final ImageIcon backgroundImage = new ImageIcon(getResourcePath(BACKGROUND_IMAGE_NAME));
    public static final PImage STAGE_BACKGROUND = new PImage(backgroundImage.getImage());

    /*** Media ***/
    public static final MediaPlayer LEVEL_SONG_PLAYER =
        new MediaPlayer(new Media(
            convertPathToValidUri(getResourcePath(LEVEL_SONG_NAME))));

    public static final MediaPlayer PLAYER_DEATH_SONG_PLAYER =
        new MediaPlayer(new Media(
            convertPathToValidUri(getResourcePath(PLAYER_DEATH_SONG_NAME))));

    public static final MediaPlayer LEVEL_COMPLETION_SONG_PLAYER =
        new MediaPlayer(new Media(
            convertPathToValidUri(getResourcePath(LEVEL_COMPLETE_SONG_NAME))));

    public static final MediaPlayer PLAYER_ACTION_SONG_PLAYER =
        new MediaPlayer(new Media(
            convertPathToValidUri(getResourcePath(PLAYER_ACTION_SOUND_NAME))));

    public static final MediaPlayer EVENT_BLOCK_DESCENT_SONG_PLAYEER =
        new MediaPlayer(new Media(
            convertPathToValidUri(getResourcePath(EVENT_BLOCK_DESCENT_SOUND_NAME))));

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

    private ResourceUtils() { }
}
