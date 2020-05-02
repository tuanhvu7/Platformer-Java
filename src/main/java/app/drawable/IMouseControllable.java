package app.drawable;

import processing.event.MouseEvent;

/**
 * Required methods for classes with mouse controls;
 * implement this in classes that use registerMethod(EProcessingMethods.MOUSE_EVENT.toString(), ...)
 */
public interface IMouseControllable {
    void mouseEvent(MouseEvent event);
}
