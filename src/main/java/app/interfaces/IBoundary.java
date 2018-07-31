package app.interfaces;

import app.characters.ACharacter;

/**
 * states required methods for boundaries
 */
public interface IBoundary {
    boolean contactWithCharacter(ACharacter character);
}
