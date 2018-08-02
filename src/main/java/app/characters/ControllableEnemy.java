package app.characters;

import app.Platformer;
import app.constants.Constants;
import processing.event.KeyEvent;

/**
 * controllable enemy
 */
public class ControllableEnemy extends Enemy {

    private boolean jumpPressed;

    /**
     * set properties of this
     */
    public ControllableEnemy(Platformer mainSketch, int x, int y, int diameter, float runSpeed,
                             boolean isFlying, boolean isInvulnerable, boolean isVisible, boolean isActive) {
        super(mainSketch, x, y, diameter, runSpeed, isFlying, isInvulnerable, isVisible, isActive);
        this.jumpPressed = false;
    }


    /**
     * active and add this to game
     */
    public void makeActive() {
        this.isActive = true;
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()
        this.mainSketch.registerMethod("keyEvent", this); // disconnect this keyEvent() from main keyEvent()
    }

    /**
     * deactivate and remove this from game
     */
    public void makeNotActive() {
        this.isActive = false;
        this.mainSketch.unregisterMethod("draw", this); // disconnect this draw() from main draw()
        this.mainSketch.unregisterMethod("keyEvent", this); // disconnect this keyEvent() from main keyEvent()
    }

    /**
     * handle movement (position, velocity)
     */
    protected void handleMovement() {
        if(!this.isFlying) {

            if(this.jumpPressed) {    // jump button pressed/held
                if( this.numberOfFloorBoundaryContacts > 0 )
                { // able to jump
                    this.vel.y = Constants.PLAYER_JUMP_VERTICAL_VELOCITY;
                } else {
                    // for jumping higher the longer jump button is held
                    this.vel.y = Math.min(
                        this.vel.y + Constants.GRAVITY.y * Constants.VARIABLE_JUMP_GRAVITY_MULTIPLIER,
                        Constants.MAX_VERTICAL_VELOCITY);
                }

            } else if(this.numberOfFloorBoundaryContacts == 0) {    // in air
                this.handleInAirPhysics();
            }
        }

        this.pos.add(this.vel);
    }

    /**
     * handle character keypress controls
     */
    public void keyEvent(KeyEvent keyEvent) {
        if(keyEvent.getAction() == KeyEvent.PRESS) {
            if(keyEvent.getKey() == 'w') {
                this.jumpPressed = true;
            }

        } else if(keyEvent.getAction() == KeyEvent.RELEASE) {
            if(keyEvent.getKey() == 'w') {
                this.jumpPressed = false;
            }
        }
    }

}
