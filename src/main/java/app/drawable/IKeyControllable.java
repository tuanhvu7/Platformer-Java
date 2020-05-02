package app.drawable;

import processing.event.KeyEvent;

/**
 * Required methods for classes with key controls;
 * implement this in classes that use registerMethod(EProcessingMethods.KEY_EVENT.toString(), ...)
 */
public interface IKeyControllable {
    void keyEvent(KeyEvent keyEvent);
}
