package app.drawable.characters;

import app.Platformer;
import app.constants.Constants;
import app.enums.ESongType;
import app.drawable.interfaces.IDrawable;
import app.utils.ResourceUtils;

/**
 * enemy
 */
public class Enemy extends ACharacter implements IDrawable {

    // true means flying enemy (not affected by gravity)
    final boolean isFlying;

    // true means invulnerable; always kills player on contact
    private final boolean isInvulnerable;

    private boolean isVisible;

    /**
     * set properties of this
     */
    public Enemy(Platformer mainSketch, int x, int y, int diameter, float runSpeed,
                 boolean isFlying, boolean isInvulnerable, boolean isVisible, boolean isActive) {
        super(mainSketch, x, y, diameter, isActive);

        this.fillColor = Constants.ENEMY_COLOR;

        this.vel.x = runSpeed;

        this.isFlying = isFlying;
        this.isInvulnerable = isInvulnerable;
        this.isVisible = isVisible;
    }

    /**
     * return angle of collision between this and player;
     * range of collision angles (in degrees): [0, 90]
     * negative angle means no collision
     */
    private double collisionWithPlayer() {
        float xDifference = Math.abs(this.pos.x - this.mainSketch.getCurrentActivePlayer().getPos().x);
        float yDifference = Math.abs(this.pos.y - this.mainSketch.getCurrentActivePlayer().getPos().y);

        // distance between player and this must be sum of their radii for collision
        float distanceNeededForCollision = (this.diameter / 2) + (this.mainSketch.getCurrentActivePlayer().getDiameter() / 2);

        // pythagorean theorem
        boolean isAtCollisionDistance =
            Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2)) <= distanceNeededForCollision;

        if (isAtCollisionDistance) {
            return Math.atan2(yDifference, xDifference);
        } else {
            return -1.0;
        }
    }

    /**
     * runs continuously. handles enemy movement and physics
     */
    @Override
    public void draw() {
        this.checkHandleOffscreenDeath();

        this.checkHandleContactWithPlayer();
        this.handleMovement();

        if (this.isVisible) {
            this.show();
        }
    }

    /**
     * check and handle contact with player
     */
    private void checkHandleContactWithPlayer() {
        if (this.mainSketch.getCurrentActivePlayer().isActive()) {   // to prevent multiple consecutive deaths TODO: encapsulate
            double collisionAngle = this.collisionWithPlayer();
            if (collisionAngle >= 0) {
//                System.out.println("coll angle: " + Math.toDegrees(collisionAngle));
//                System.out.println("min angle: " + Constants.MIN_PLAYER_KILL_ENEMY_COLLISION_ANGLE);

                if (Math.toDegrees(collisionAngle) >= Constants.MIN_PLAYER_KILL_ENEMY_COLLISION_ANGLE
                    && this.pos.y > this.mainSketch.getCurrentActivePlayer().getPos().y
                    && !this.isInvulnerable)  // player is above this
                {
                    this.handleDeath(false);

                } else {
                    this.isVisible = true;
//                    System.out.println("killed player: " + Math.toDegrees(collisionAngle));
                    this.mainSketch.getCurrentActivePlayer().handleDeath(false);
                }
            }
        }
    }

    /**
     * handle movement (position, velocity)
     */
    void handleMovement() {
        if (!this.isFlying && this.numberOfFloorBoundaryContacts == 0) {
            this.handleInAirPhysics();
        }

        this.pos.add(this.vel);
    }

    /**
     * handle death
     */
    @Override
    void handleDeath(boolean isOffscreenDeath) {
//                    System.out.println("killed enemy: " + Math.toDegrees(collisionAngle));
        this.makeNotActive();
        this.mainSketch.getCurrentActiveCharactersList().remove(this);
        if (!isOffscreenDeath) {
            ResourceUtils.playSong(ESongType.PlayerAction);
            this.mainSketch.getCurrentActivePlayer().handleJumpKillEnemyPhysics();
        }
    }

}
