package app.drawable.characters;

import app.Platformer;
import app.constants.Constants;
import app.drawable.boundaries.EventBlockTopBoundary;
import app.drawable.boundaries.HorizontalBoundary;
import app.enums.EProcessingMethods;
import app.enums.ESongType;
import app.utils.PlayerControlSettings;
import app.utils.ResourceUtils;
import processing.core.PVector;
import processing.event.KeyEvent;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * player controllable character in game
 */
public class Player extends ACharacter implements IControllableCharacter {

    // health of this, 0 means dead
    private int health;

    // true means this does interact with enemies
    private boolean canHaveContactWithEnemies;

    // number of wall-like boundaries this is touching;
    private int numberOfVerticalBoundaryContacts;

    // number of ceiling-like boundaries this is touching;
    private int numberOfCeilingBoundaryContacts;

    // top boundary of event blocks that this is touching
    private final Set<EventBlockTopBoundary> eventBlockTopBoundaryContacts;

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
     * set properties of this;
     * set this to have 1 health
     */
    public Player(Platformer mainSketch, int x, int y, int diameter, boolean isActive) {
        super(mainSketch, x, y, diameter, isActive);

        this.health = 1;
        this.canHaveContactWithEnemies = true;
        this.fillColor = Constants.PLAYER_DEFAULT_COLOR;

        this.numberOfVerticalBoundaryContacts = 0;
        this.numberOfFloorBoundaryContacts = 0;

        this.eventBlockTopBoundaryContacts = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.previousFloorBoundaryContact = null;
        this.shouldSetPreviousFloorBoundaryContact = true;

        this.resetControlPressed();

        this.isDescendingDownEventBlock = false;

        this.ableToMoveRight = true;
        this.ableToMoveLeft = true;
    }

    /**
     * set properties of this
     */
    public Player(Platformer mainSketch, int x, int y, int diameter, int health, boolean isActive) {
        super(mainSketch, x, y, diameter, isActive);
        if (health < 1) {
            throw new IllegalArgumentException("Initial player health must be at least 1");
        }

        this.health = health;
        this.canHaveContactWithEnemies = true;
        this.fillColor = Constants.PLAYER_DEFAULT_COLOR;

        this.numberOfVerticalBoundaryContacts = 0;
        this.numberOfFloorBoundaryContacts = 0;

        this.eventBlockTopBoundaryContacts = Collections.newSetFromMap(new ConcurrentHashMap<>());
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
            if ((PlayerControlSettings.getPlayerDown() == keyCode)
                && this.eventBlockTopBoundaryContacts.size() == 1 && !isDescendingDownEventBlock) {
                this.isDescendingDownEventBlock = true;
            }

        } else if (keyEvent.getAction() == KeyEvent.RELEASE) {
            if (PlayerControlSettings.getPlayerLeft() == (keyCode)) {       //left
                this.moveLeftPressed = false;
            }
            if (PlayerControlSettings.getPlayerRight() == (keyCode)) {       //right
                this.moveRightPressed = false;
            }
            if (PlayerControlSettings.getPlayerUp() == (keyCode)) {
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
        this.handleMovement();
        this.show();
    }

    @Override
    void show() {
        this.mainSketch.fill(this.fillColor);
        this.mainSketch.strokeWeight(0);
        this.mainSketch.ellipse(this.pos.x, this.pos.y, this.diameter, this.diameter);

        this.mainSketch.fill(Constants.PLAYER_HEALTH_TEXT_COLOR);
        this.mainSketch.textAlign(this.mainSketch.CENTER, this.mainSketch.CENTER);
        this.mainSketch.textSize(this.diameter / 2);
        this.mainSketch.text(
            this.health + "",
            this.pos.x - (this.diameter / 2), this.pos.y - (this.diameter / 2),
            this.diameter, this.diameter);
    }

    /**
     * active and add this to game
     */
    @Override
    public void makeActive() {
        this.mainSketch.registerMethod(EProcessingMethods.KEY_EVENT.toString(), this);   // connect this keyEvent() from main keyEvent()
        this.mainSketch.registerMethod(EProcessingMethods.DRAW.toString(), this); // connect this draw() from main draw()
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
        if (this.isDescendingDownEventBlock) {
            this.handleEventBlockDescent();
        } else {
            // this is NOT controllable horizontally when level is finished
            if (!this.mainSketch.getCurrentActiveLevel().isHandlingLevelComplete()) {
                this.handleHorizontalMovement();
            }
            this.handleVerticalMovement();
        }

        this.pos.add(this.vel);
    }

    /**
     * handle death of this
     */
    @Override
    void handleDeath(boolean isOffscreenDeath) {
        this.canHaveContactWithEnemies = false; // prevent enemy contact with this after this' death
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
            this.pos.x = boundaryXPoint + this.diameter / 2;
        } else {    // right boundary
            this.ableToMoveRight = false;
            this.pos.x = boundaryXPoint - this.diameter / 2;
        }
    }

    /**
     * handle contact with this and event boundary;
     * null endWarpPosition means launch event, non-null endWarpPosition means warp event
     */
    public void handleContactWithEventBoundary(EventBlockTopBoundary eventBlockTopBoundary,
                                               int launchEventVerticalVelocity, PVector endWarpPosition) {
        this.isDescendingDownEventBlock = false;
        if (endWarpPosition == null) {
            this.vel.y = launchEventVerticalVelocity;
        } else {
            this.pos.x = endWarpPosition.x;
            this.pos.y = endWarpPosition.y;

            this.mainSketch.getCurrentActiveViewBox().setViewBoxHorizontalPosition(this.pos.x);
            this.vel.y = Constants.CHARACTER_WARP_EVENT_VERTICAL_VELOCITY;
        }

        this.previousFloorBoundaryContact = null;   // to prevent this going through floor boundaries

        // event block top boundary can affect player again
        this.shouldSetPreviousFloorBoundaryContact = false;
        eventBlockTopBoundary.setDoesAffectPlayer(true);
        this.mainSketch.registerMethod(EProcessingMethods.KEY_EVENT.toString(), this); // enable controls for this
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
        //  prevent fall through floor after killing enemy while touching floor
        this.shouldSetPreviousFloorBoundaryContact = false;
        this.previousFloorBoundaryContact = null;
    }

    /**
     * change health of this by given amount;
     * handle death if death is 0
     */
    public void changeHealth(int healthChangeAmount) {
        this.health += healthChangeAmount;
        if (this.health <= 0) {
            this.handleDeath(false);
        } else if (healthChangeAmount < 0) {
            this.canHaveContactWithEnemies = false;
            this.fillColor = Constants.PLAYER_DAMAGED_COLOR;

            // make this unaffected by enemies for a duration
            new Thread(() -> {
                try {
                    ResourceUtils.playSong(ESongType.PLAYER_DAMAGE);
                    Thread.sleep((long) ResourceUtils.getSongDurationMilliSec(ESongType.PLAYER_DAMAGE));  // wait for song duration
                    this.canHaveContactWithEnemies = true;
                    this.fillColor = Constants.PLAYER_DEFAULT_COLOR;
                } catch (InterruptedException ie) {
                }
            }).start();
        }
    }

    /**
     * handle this descent down event block
     */
    private void handleEventBlockDescent() {
        if (this.eventBlockTopBoundaryContacts.size() == 1) {
            this.resetControlPressed();
            this.mainSketch.unregisterMethod(EProcessingMethods.KEY_EVENT.toString(), this); // disconnect this keyEvent() from main keyEvent()

            EventBlockTopBoundary firstEventTopBoundaryContacts =
                this.eventBlockTopBoundaryContacts.stream().findFirst().get();

            int middleOfBoundary = Math.round(
                (firstEventTopBoundaryContacts.getEndPoint().x + firstEventTopBoundaryContacts.getStartPoint().x) / 2);

            firstEventTopBoundaryContacts.setDoesAffectPlayer(false);
            this.pos.x = middleOfBoundary;
            this.vel.x = 0;
            this.vel.y = Constants.EVENT_BLOCK_DESCENT_VERTICAL_VELOCITY;
            ResourceUtils.playSong(ESongType.EVENT_BLOCK_DESCENT);
        }
    }

    /**
     * handle horizontal movement of this
     */
    private void handleHorizontalMovement() {
        if (this.moveLeftPressed && this.ableToMoveLeft) {
            this.vel.x = -Constants.PLAYER_MOVEMENT_SPEED;
        }
        if (this.moveRightPressed && this.ableToMoveRight) {
            this.vel.x = Constants.PLAYER_MOVEMENT_SPEED;
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
                ResourceUtils.playSong(ESongType.PLAYER_ACTION);
                this.vel.y = Constants.CHARACTER_JUMP_VERTICAL_VELOCITY;

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
    boolean isCanHaveContactWithEnemies() {
        return canHaveContactWithEnemies;
    }

    public Set<EventBlockTopBoundary> getEventBlockTopBoundaryContacts() {
        return eventBlockTopBoundaryContacts;
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
