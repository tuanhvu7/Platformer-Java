package app.characters;

import app.Platformer;
import app.constants.Constants;
import app.enums.ESongType;
import app.interfaces.IDrawable;
import app.utils.ResourceUtils;

/**
 * enemy
 */
public class Enemy extends ACharacter implements IDrawable {

    // true means flying enemy (not affected by gravity)
    protected boolean isFlying;

    // true means invulnerable; always kills player on contact
    protected boolean isInvulnerable;

    protected boolean isVisible;

    /**
     * set properties of this
     */
    public Enemy(Platformer mainSketch, int x, int y, int diameter, float runSpeed,
                 boolean isFlying, boolean isInvulnerable, boolean isVisible, boolean isActive) {
        super(mainSketch, x, y, diameter, isActive);
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
    public double collisionWithPlayer() {
        float xDifference = Math.abs(this.pos.x - this.mainSketch.getCurrentActivePlayer().pos.x); // TODO: encapsulate
        float yDifference = Math.abs(this.pos.y - this.mainSketch.getCurrentActivePlayer().pos.y); // TODO: encapsulate

        // distance between player and this must be sum of their radii for collision
        float distanceNeededForCollision = (this.diameter / 2) + (this.mainSketch.getCurrentActivePlayer().diameter / 2); // TODO: encapsulate

        // pythagorean theorem
        boolean isAtCollisionDistance =
            Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2)) <= distanceNeededForCollision;

        if(isAtCollisionDistance) {
            return Math.atan2(yDifference, xDifference);
        } else {
            return -1.0;
        }
    }

    /**
     * runs continuously. handles enemy movement and physics
     */
    public void draw() {
        this.checkHandleContactWithPlayer();
        this.handleMovement();

        if(this.isVisible) {
            this.mainSketch.fill(Constants.ENEMY_COLOR);
            this.show();
        }
    }

    /**
     *  check and handle contact with player
     */
    private void checkHandleContactWithPlayer() {
        if(this.mainSketch.getCurrentActivePlayer().isActive) {   // to prevent multiple consecutive deaths TODO: encapsulate
            double collisionAngle = this.collisionWithPlayer();
            if(collisionAngle >= 0) {
                System.out.println("coll angle: " + Math.toDegrees(collisionAngle));
                System.out.println("min angle: " + Constants.MIN_PLAYER_KILL_ENEMY_COLLISION_ANGLE);

                if(Math.toDegrees(collisionAngle) >= Constants.MIN_PLAYER_KILL_ENEMY_COLLISION_ANGLE
                    && this.pos.y > this.mainSketch.getCurrentActivePlayer().pos.y
                    && !this.isInvulnerable)  // player is above this // TODO: encapsulate
                {
                    System.out.println("killed enemy: " + Math.toDegrees(collisionAngle));
                    ResourceUtils.playSong(ESongType.PlayerAction);
                    this.makeNotActive();
                    this.mainSketch.getCurrentActiveCharactersList().remove(this);
                    this.mainSketch.getCurrentActivePlayer().handleJumpKillEnemyPhysics();

                } else {
                    this.isVisible = true;
                    System.out.println("killed player: " + Math.toDegrees(collisionAngle));
                    mainSketch.resetLevel();
                }
            }
        }
    }

    /**
     * handle movement (position, velocity)
     */
    protected void handleMovement() {
        if(!this.isFlying && this.numberOfFloorBoundaryContacts == 0) {
            this.handleInAirPhysics();
        }

        this.pos.add(this.vel);
    }

}
