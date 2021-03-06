package app.drawable.characters;

import app.Platformer;
import app.constants.Constants;
import app.drawable.IKeyControllable;
import app.enums.EProcessingMethods;
import app.utils.PlayerControlSettings;
import processing.event.KeyEvent;

/**
 * controllable enemy
 */
public class ControllableEnemy extends Enemy implements IKeyControllable {

    // true means left and right button controls affect this
    final private boolean isHorizontalControllable;
    private boolean moveLeftPressed;
    private boolean moveRightPressed;


    // true means jump button controls affect this
    final private boolean isJumpControllable;
    private boolean jumpPressed;

    private boolean ableToMoveRight;
    private boolean ableToMoveLeft;

    final private float horizontalMoveSpeed;

    /**
     * set properties of this;
     * horizontalVel is vel of this if this.isHorizontalControllable is false
     */
    public ControllableEnemy(Platformer mainSketch, int x, int y, int diameter,
                             boolean isJumpControllable, boolean isHorizontalControllable,
                             float horizontalVel,
                             boolean isInvulnerable, boolean isVisible, boolean initAsActive) {
        super(mainSketch, x, y, diameter, horizontalVel, isInvulnerable, isVisible, initAsActive);
        this.isJumpControllable = isJumpControllable;
        this.jumpPressed = false;

        this.isHorizontalControllable = isHorizontalControllable;
        this.moveLeftPressed = false;
        this.moveRightPressed = false;

        this.ableToMoveRight = true;
        this.ableToMoveLeft = true;

        this.horizontalMoveSpeed = Math.abs(horizontalVel); // used for left and right control
    }


    /**
     * active and add this to game
     */
    @Override
    public void makeActive() {
        this.mainSketch.registerMethod(EProcessingMethods.DRAW.toString(), this); // connect this draw() from main draw()
        this.mainSketch.registerMethod(EProcessingMethods.KEY_EVENT.toString(), this); // connect this keyEvent() from main keyEvent()
    }

    /**
     * deactivate and remove this from game
     */
    @Override
    public void makeNotActive() {
        this.mainSketch.unregisterMethod(EProcessingMethods.DRAW.toString(), this); // disconnect this draw() from main draw()
        this.mainSketch.unregisterMethod(EProcessingMethods.KEY_EVENT.toString(), this); // disconnect this keyEvent() from main keyEvent()
    }

    /**
     * handle movement (position, velocity)
     */
    @Override
    void handleMovement() {
        this.handleVerticalMovement();
        // this is NOT controllable horizontally when level is finished
        if (!this.mainSketch.getCurrentActiveLevel().isHandlingLevelComplete()) {
            this.handleHorizontalMovement();
        }
        this.pos.add(this.vel);
    }


    /**
     * handle contact with vertical boundary
     */
    @Override
    public void handleContactWithVerticalBoundary(float boundaryXPoint) {
        if (this.isHorizontalControllable) {
            this.vel.x = 0;
            if (this.pos.x > boundaryXPoint) {   // left boundary
                this.ableToMoveLeft = false;
                this.pos.x = boundaryXPoint + this.diameter / 2;
            } else {    // right boundary
                this.ableToMoveRight = false;
                this.pos.x = boundaryXPoint - this.diameter / 2;
            }

        } else {
            super.handleContactWithVerticalBoundary(boundaryXPoint);
        }
    }

    /**
     * handle character keypress controls
     */
    @Override
    public void keyEvent(KeyEvent keyEvent) {
        int keyCode = Character.toLowerCase(keyEvent.getKeyCode());
        if (keyEvent.getAction() == KeyEvent.PRESS) {
            if (PlayerControlSettings.getPlayerLeft() == keyCode) {   //left
                this.moveLeftPressed = true;
            }
            if (PlayerControlSettings.getPlayerRight() == keyCode) {   //right
                this.moveRightPressed = true;
            }
            if (PlayerControlSettings.getPlayerUp() == keyCode) {
                this.jumpPressed = true;
            }

        } else if (keyEvent.getAction() == KeyEvent.RELEASE) {
            if (PlayerControlSettings.getPlayerLeft() == (keyCode)) {  //left
                this.moveLeftPressed = false;
            }
            if (PlayerControlSettings.getPlayerRight() == (keyCode)) { //right
                this.moveRightPressed = false;
            }
            if (PlayerControlSettings.getPlayerUp() == (keyCode)) {
                this.jumpPressed = false;
            }
        }
    }

    private void handleVerticalMovement() {
        if (this.isJumpControllable && this.jumpPressed) {    // jump button pressed/held
            if (this.numberOfFloorBoundaryContacts > 0) { // able to jump
                this.vel.y = Constants.CHARACTER_JUMP_VERTICAL_VELOCITY;
            } else {
                // for jumping higher the longer jump button is held
                this.vel.y = Math.min(
                    this.vel.y + Constants.GRAVITY.y * Constants.VARIABLE_JUMP_GRAVITY_MULTIPLIER,
                    Constants.MAX_VERTICAL_VELOCITY);
            }

        } else if (this.numberOfFloorBoundaryContacts == 0) {    // in air
            this.handleInAirPhysics();
        }
    }

    private void handleHorizontalMovement() {
        if (this.isHorizontalControllable) {
            if (this.moveLeftPressed && this.ableToMoveLeft) {
                this.vel.x = -this.horizontalMoveSpeed;
            }
            if (this.moveRightPressed && this.ableToMoveRight) {
                this.vel.x = this.horizontalMoveSpeed;
            }
            if (!this.moveLeftPressed && !this.moveRightPressed) {
                this.vel.x = 0;
            }

        }
    }

    /*** getters and setters ***/
    public void setAbleToMoveRight(boolean ableToMoveRight) {
        this.ableToMoveRight = ableToMoveRight;
    }

    public void setAbleToMoveLeft(boolean ableToMoveLeft) {
        this.ableToMoveLeft = ableToMoveLeft;
    }
}
