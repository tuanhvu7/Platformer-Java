package app.boundaries;

import app.characters.ACharacter;

/**
 * required methods for boundaries
 */
interface IBoundary {
    boolean contactWithCharacter(ACharacter character);
}
