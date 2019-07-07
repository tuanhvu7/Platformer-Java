package app.drawable.characters;

import processing.event.KeyEvent;

/**
 * Required methods for controllable character classes;
 * implement this in character classes that have "public void keyEvent(KeyEvent keyEvent)"
 */
public interface IControllableCharacter {
    void keyEvent(KeyEvent keyEvent);
}
