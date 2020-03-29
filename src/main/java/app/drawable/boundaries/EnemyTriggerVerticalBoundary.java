package app.drawable.boundaries;

import app.Platformer;
import app.drawable.characters.Enemy;
import app.drawable.characters.Player;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * boundary to add enemies upon player contact
 */
public class EnemyTriggerVerticalBoundary extends VerticalBoundary {
    // set of enemies to be added
    private final Set<Enemy> enemiesToAddSet;

    /**
     * set properties of this;
     * only one enemy to trigger
     */
    public EnemyTriggerVerticalBoundary(Platformer mainSketch, int startXPoint, int startYPoint, int y2Offset, int boundaryLineThickness,
                                        boolean isActive, Enemy enemy) {
        super(mainSketch, startXPoint, startYPoint, y2Offset, boundaryLineThickness,
            false, isActive);
        Set<Enemy> set = Collections.newSetFromMap(new ConcurrentHashMap<>());
        set.add(enemy);
        this.enemiesToAddSet = set;
    }

    /**
     * set properties of this;
     * set of enemies to trigger
     */
    public EnemyTriggerVerticalBoundary(Platformer mainSketch, int startXPoint, int startYPoint, int y2Offset, int boundaryLineThickness,
                                        boolean isActive, Set<Enemy> enemySet) {
        super(mainSketch, startXPoint, startYPoint, y2Offset, boundaryLineThickness,
            false, isActive);
        this.enemiesToAddSet = enemySet;
    }

    /**
     * runs continuously. checks and handles contact between this and characters
     */
    @Override
    public void draw() {
        this.show();
        if (this.mainSketch.getCurrentActivePlayer() != null) {
            this.checkHandleContactWithPlayer();
        }
    }

    /**
     * check and handle contact with player
     */
    @Override
    void checkHandleContactWithPlayer() {
        Player curPlayer = this.mainSketch.getCurrentActivePlayer();

        if (this.doesAffectPlayer) {
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
