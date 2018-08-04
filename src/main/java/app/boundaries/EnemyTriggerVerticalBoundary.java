package app.boundaries;

import app.Platformer;
import app.characters.Enemy;
import app.characters.Player;

import java.util.Set;

/**
 * boundary to add enemies upon player contact
 */
public class EnemyTriggerVerticalBoundary extends VerticalBoundary {
    // set of enemies to be added
    private final Set<Enemy> enemiesToAddSet;

    /**
     * set properties of this
     * sets this to affect all characters
     */
    public EnemyTriggerVerticalBoundary(Platformer mainSketch, int startXPoint, int startYPoint, int y2Offset, int boundaryLineThickness,
                                        boolean isVisible, boolean isActive, Set<Enemy> enemySet) {
        super(mainSketch, startXPoint, startYPoint, y2Offset, boundaryLineThickness,
            isVisible, isActive);
        this.enemiesToAddSet = enemySet;
    }

    /**
     * runs continuously. checks and handles contact between this and characters
     */
    @Override
    public void draw() {
        this.show();
        this.checkHandleContactWithPlayer();
    }

    /**
     * check and handle contact with player
     */
    private void checkHandleContactWithPlayer() {
        Player curPlayer = this.mainSketch.getCurrentActivePlayer();

        if (this.doesAffectPlayer && curPlayer.isActive()) {
            // boundary collision for player
            if (contactWithCharacter(curPlayer)) {  // this has contact with non-player
                for (Enemy curEnemy : enemiesToAddSet) {
                    curEnemy.makeActive();
                }
                this.makeNotActive();
            }
        }
    }
}
