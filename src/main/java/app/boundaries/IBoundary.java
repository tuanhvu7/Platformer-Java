package app.boundaries;

import app.characters.ACharacter;

/**
 * required methods for boundaries
 */
public interface IBoundary {
    boolean contactWithCharacter(ACharacter character);
}
