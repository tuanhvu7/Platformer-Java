package app.drawable.levels;

import app.drawable.IDrawable;
import app.drawable.blocks.ABlock;
import app.drawable.boundaries.ABoundary;
import app.drawable.characters.ACharacter;
import app.drawable.collectables.ACollectable;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * For storing and managing drawables in levels
 */
public class LevelDrawableCollection {
    // set of all non-playable characters
    private final Set<ACharacter> charactersList;

    // set of all boundaries
    private final Set<ABoundary> boundariesList;

    // set of all blocks
    private final Set<ABlock> blocksList;

    // set of all collectables
    private final Set<ACollectable> collectablesList;

    /**
     * sets properties of this
     */
    LevelDrawableCollection() {
        this.charactersList = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.boundariesList = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.blocksList = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.collectablesList = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    /**
     * remove given drawable from appropriate set
     */
    public void removeDrawable(IDrawable drawable) {
        if (drawable instanceof ACharacter) {
            this.charactersList.remove(drawable);

        } else if (drawable instanceof ABoundary) {
            this.boundariesList.remove(drawable);

        } else if (drawable instanceof ABlock) {
            this.blocksList.remove(drawable);

        } else if (drawable instanceof ACollectable) {
            this.collectablesList.remove(drawable);
        }
    }

    /**
     * add given drawable to correct set
     */
    void addDrawable(IDrawable drawable) {
        if (drawable instanceof ACharacter) {
            this.charactersList.add((ACharacter) drawable);

        } else if (drawable instanceof ABoundary) {
            this.boundariesList.add((ABoundary) drawable);

        } else if (drawable instanceof ABlock) {
            this.blocksList.add((ABlock) drawable);

        } else if (drawable instanceof ACollectable) {
            this.collectablesList.add((ACollectable) drawable);
        }
    }

    /**
     * make all drawables not active and clears all sets
     */
    void deactivateClearAllDrawable() {
        for (ACharacter curCharacter : this.charactersList) {
            curCharacter.makeNotActive();
        }

        for (ABoundary curBoundary : this.boundariesList) {
            curBoundary.makeNotActive();
        }

        for (ABlock curBlock : this.blocksList) {
            curBlock.makeNotActive();
        }

        for (ACollectable curCollectable : this.collectablesList) {
            curCollectable.makeNotActive();
        }

        this.charactersList.clear();
        this.boundariesList.clear();
        this.blocksList.clear();
        this.collectablesList.clear();
    }

    public Set<ACharacter> getCharactersList() {
        return charactersList;
    }
}
