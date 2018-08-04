package app.drawable.boundaries;

import app.drawable.characters.ACharacter;

/**
 * required methods for boundaries
 */
interface IBoundary {
    boolean contactWithCharacter(ACharacter character);
}
