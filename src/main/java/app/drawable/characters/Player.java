package app.drawable.characters;

import app.Platformer;
import app.constants.Constants;
import app.drawable.boundaries.EventBlockTopBoundary;
import app.drawable.boundaries.HorizontalBoundary;
import app.drawable.interfaces.IDrawable;
import app.enums.ESongType;
import app.utils.ResourceUtils;
import processing.core.PVector;
import processing.event.KeyEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * player controllable character in game
 */
public class Player extends ACharacter implements IDrawable {
    // number of wall-like boundaries this is touching;
    private int numberOfVerticalBoundaryContacts;

    // number of ceiling-like boundaries this is touching;
    private int numberOfCeilingBoundaryContacts;

    // top boundary of event blocks that this is touching
    private final Set<EventBlockTopBoundary> eventTopBoundaryContacts;

    // stores floor boundary that this cannot contact with
    // to prevent going on floor boundaries when walking from below
    private HorizontalBoundary previousFloorBoundaryContact;

    // true means should set previousFloorBoundaryContact
    // when this loses contact with floor boundary
    private boolean shouldSetPreviousFloorBoundaryContact;

    // player pressed control states
    private boolean moveLeftPressed;
    private boolean moveRightPressed;
    private boolean jumpPressed;

    private boolean ableToMoveRight;
    private boolean ableToMoveLeft;

    private boolean isDescendingDownEventBlock;

    /**
     * set properties of this
     */
    public Player(Platformer mainSketch, int x, int y, int diameter, boolean isActive) {
        super(mainSketch, x, y, diameter, isActive);

        this.fillColor = Constants.PLAYER_COLOR;

        this.numberOfVerticalBoundaryContacts = 0;
        this.numberOfFloorBoundaryContacts = 0;

        this.eventTopBoundaryContacts = new HashSet<>();
        this.previousFloorBoundaryContact = null;
        this.shouldSetPreviousFloorBoundaryContact = true;

        this.resetControlPressed();

        this.isDescendingDownEventBlock = false;

        this.ableToMoveRight = true;
        this.ableToMoveLeft = true;
    }

    /**
     * handle character keypress controls
     */
    public void keyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.PRESS) {
            if (keyEvent.getKey() == 'a') {   //left
                this.moveLeftPressed = true;
            }
            if (keyEvent.getKey() == 'd') {   //right
                this.moveRightPressed = true;
            }
            if (keyEvent.getKey() == 'w') {
                this.jumpPressed = true;
            }
            if (keyEvent.getKey() == 's' && this.eventTopBoundaryContacts.size() == 1 && !isDescendingDownEventBlock) {
                this.isDescendingDownEventBlock = true;
            }

        } else if (keyEvent.getAction() == KeyEvent.RELEASE) {
            if (keyEvent.getKey() == 'a') {       //left
                this.moveLeftPressed = false;
            }
            if (keyEvent.getKey() == 'd') {       //right
                this.moveRightPressed = false;
            }
            if (keyEvent.getKey() == 'w') {
                this.jumpPressed = false;
            }
        }
    }

    /**
     * runs continuously. handles player movement and physics
     */
    @Override
    public void draw() {
        this.checkHandleOffscreenDeath();

        if (this.isDescendingDownEventBlock) {
            this.handleEventBlockDescent();
        } else {
            if (!this.mainSketch.getCurrentActiveLevel().isHandlingLevelComplete()) {
                this.handleHorizontalMovement();
            }
            this.handleVerticalMovement();
        }

        this.pos.add(this.vel);

        this.show();
    }

    /**
     * active and add this to game
     */
    @Override
    public void makeActive() {
        this.isActive = true;
        this.mainSketch.registerMethod("keyEvent", this);   // connect this keyEvent() from main keyEvent()
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()
    }

    /**
     * deactivate and remove this from game
     */
    @Override
    public void makeNotActive() {
        this.isActive = false;
        this.mainSketch.unregisterMethod("draw", this); // disconnect this draw() from main draw()
        this.mainSketch.unregisterMethod("keyEvent", this); // disconnect this keyEvent() from main keyEvent()
    }

    /**
     *
     */
    @Override
    void handleDeath(boolean isOffscreenDeath) {
        this.mainSketch.resetLevel();
    }

    /**
     * handle contact with horizontal boundary
     */
    @Override
    public void handleContactWithHorizontalBoundary(float boundaryYPoint, boolean isFloorBoundary) {
        if (isFloorBoundary) { // floor-like boundary
            if (this.vel.y >= 0) {    // boundary only act like floor if this is on top or falling onto boundary
                this.vel.y = 0;
                this.pos.y = boundaryYPoint - this.diameter / 2;
                this.shouldSetPreviousFloorBoundaryContact = true;
            }
        } else {    // ceiling-like boundary
            if (this.vel.y < 0) {    // boundary only act like ceiling if this is rising into boundary
                this.vel.y = 1;
                this.pos.y = boundaryYPoint + this.diameter / 2;
                this.pos.add(this.vel);
            }
        }
    }

    /**
     * handle contact with vertical boundary
     */
    @Override
    public void handleContactWithVerticalBoundary(float boundaryXPoint) {
        this.vel.x = 0;
        if (this.pos.x > boundaryXPoint) {   // left boundary
            this.ableToMoveLeft = false;
        } else {    // right boundary
            this.ableToMoveRight = false;
        }
    }

    /**
     * handle contact with this and event boundary
     */
    public void handleContactWithEventBoundary(EventBlockTopBoundary eventBlockTopBoundary, PVector endWarpPosition) {
        this.mainSketch.registerMethod("keyEvent", this); // connect this draw() from main draw()
        this.isDescendingDownEventBlock = false;
        if (endWarpPosition == null) {
            this.vel.y = Constants.CHARACTER_LAUNCH_EVENT_VERTICAL_VELOCITY;
        } else {
            this.pos.x = endWarpPosition.x;
            this.pos.y = endWarpPosition.y;

            this.mainSketch.getCurrentActiveViewBox().setViewBoxHorizontalPosition(this.pos.x);
            this.vel.y = Constants.CHARACTER_WARP_EVENT_VERTICAL_VELOCITY;
        }
        eventBlockTopBoundary.setDoesAffectPlayer(true);
    }

    /**
     * set controls pressed to false
     */
    public void resetControlPressed() {
        this.moveLeftPressed = false;
        this.moveRightPressed = false;
        this.jumpPressed = false;
    }

    /**
     * handle wall sliding physics
     */
    private void handleOnWallPhysics() {
        this.vel.y = Math.min(this.vel.y + Constants.WALL_SLIDE_ACCELERATION.y, Constants.MAX_VERTICAL_VELOCITY);
    }

    /**
     * handle jump on enemy physics
     */
    void handleJumpKillEnemyPhysics() {
        this.vel.y = Constants.PLAYER_JUMP_KILL_ENEMY_HOP_VERTICAL_VELOCITY;
    }

    /**
     * handle this descent down event block
     */
    private void handleEventBlockDescent() {
        if (this.eventTopBoundaryContacts.size() == 1) {
            this.resetControlPressed();
            this.mainSketch.unregisterMethod("keyEvent", this); // disconnect this keyEvent() from main keyEvent()

            EventBlockTopBoundary firstEventTopBoundaryContacts =
                this.eventTopBoundaryContacts.stream().findFirst().get();

            int middleOfBoundary = Math.round(
                (firstEventTopBoundaryContacts.getEndPoint().x + firstEventTopBoundaryContacts.getStartPoint().x) / 2);

            firstEventTopBoundaryContacts.setDoesAffectPlayer(false);
            this.pos.x = middleOfBoundary;
            this.vel.x = 0;
            this.vel.y = Constants.EVENT_BLOCK_DESCENT_VERTICAL_VELOCITY;
            ResourceUtils.playSong(ESongType.EventBlockDescent);
        }
    }

    /**
     * handle horizontal movement of this
     */
    private void handleHorizontalMovement() {
        if (this.moveLeftPressed && this.ableToMoveLeft) {
            this.vel.x = -Constants.PLAYER_RUN_SPEED;
        }
        if (this.moveRightPressed && this.ableToMoveRight) {
            this.vel.x = Constants.PLAYER_RUN_SPEED;
        }
        if (!this.moveLeftPressed && !this.moveRightPressed) {
            this.vel.x = 0;
        }
    }

    /**
     * handle vertical movement of this
     */
    private void handleVerticalMovement() {
        if (this.jumpPressed) {    // jump button pressed/held
            if (this.numberOfFloorBoundaryContacts > 0 ||
                (this.numberOfVerticalBoundaryContacts > 0 && this.numberOfCeilingBoundaryContacts == 0)) { // able to jump
                ResourceUtils.playSong(ESongType.PlayerAction);
                this.vel.y = Constants.PLAYER_JUMP_VERTICAL_VELOCITY;

                this.shouldSetPreviousFloorBoundaryContact = false;
                this.previousFloorBoundaryContact = null;
            } else {
                // for jumping higher the longer jump button is held
                this.vel.y = Math.min(
                    this.vel.y + Constants.GRAVITY.y * Constants.VARIABLE_JUMP_GRAVITY_MULTIPLIER,
                    Constants.MAX_VERTICAL_VELOCITY);
            }

        } else {    // jump button not pressed
            if (this.numberOfVerticalBoundaryContacts > 0) {   // touching wall
                this.handleOnWallPhysics();
            } else if (this.numberOfFloorBoundaryContacts == 0) {    // in air
                this.handleInAirPhysics();
            }
        }
    }

    /**
     * change numberOfCeilingBoundaryContacts by given amount
     */
    public void changeNumberOfCeilingBoundaryContacts(int amount) {
        this.numberOfCeilingBoundaryContacts += amount;
    }

    /**
     * change numberOfCeilingBoundaryContacts by given amount
     */
    public void changeNumberOfVerticalBoundaryContacts(int amount) {
        this.numberOfVerticalBoundaryContacts += amount;
    }


    /*** getters and setters ***/
    public Set<EventBlockTopBoundary> getEventTopBoundaryContacts() {
        return eventTopBoundaryContacts;
    }

    public boolean isMoveLeftPressed() {
        return moveLeftPressed;
    }

    public boolean isMoveRightPressed() {
        return moveRightPressed;
    }

    public void setAbleToMoveRight(boolean ableToMoveRight) {
        this.ableToMoveRight = ableToMoveRight;
    }

    public void setAbleToMoveLeft(boolean ableToMoveLeft) {
        this.ableToMoveLeft = ableToMoveLeft;
    }

    public HorizontalBoundary getPreviousFloorBoundaryContact() {
        return previousFloorBoundaryContact;
    }

    public void setPreviousFloorBoundaryContact(HorizontalBoundary previousFloorBoundaryContact) {
        this.previousFloorBoundaryContact = previousFloorBoundaryContact;
    }

    public boolean isShouldSetPreviousFloorBoundaryContact() {
        return shouldSetPreviousFloorBoundaryContact;
    }
}
