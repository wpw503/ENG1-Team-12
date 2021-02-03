package com.teamonehundred.pixelboat.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the user player's boat.
 *
 * @author James Frost
 * @author William Walton
 * JavaDoc by Umer Fakher
 */
public class PlayerBoat extends Boat {
    /* ################################### //
                   ATTRIBUTES
    // ################################### */

    protected OrthographicCamera camera;

    protected Texture stamina_texture;
    protected Texture durability_texture;

    protected Sprite stamina_bar;
    protected Sprite durability_bar;

    public int ui_bar_width = 500;

    /* ################################### //
                  CONSTRUCTORS
    // ################################### */

    /**
     * Construct a PlayerBoat object at point (x,y) with default size, texture and animation.
     *
     * @param x int coordinate for the bottom left point of the boat
     * @param y int coordinate for the bottom left point of the boat
     * @author William Walton
     */
    public PlayerBoat(int x, int y) {
        super(x, y);

        initialise();
    }

    /**
     * Construct a PlayerBoat object with at point (x,y) with width and height and texture path
     * with default stats (stamina usage, durability, etc).
     *
     * @param x            int coordinate for the bottom left point of the boat
     * @param y            int coordinate for the bottom left point of the boat
     * @param w            int width of the new boat
     * @param h            int height of the new boat
     * @param texture_path String relative path from the core/assets folder of the boats texture image
     * @author William Walton
     */
    PlayerBoat(int x, int y, int w, int h, String texture_path) {
        super(x, y, w, h, texture_path);

        initialise();
    }

    /**
     * Construct a PlayerBoat object with all parameters specified.
     *
     * @param x                  int coordinate for the bottom left point of the boat
     * @param y                  int coordinate for the bottom left point of the boat
     * @param w                  int width of the new boat
     * @param h                  int height of the new boat
     * @param texture_path       String relative path from the core/assets folder of the boats texture image
     * @param durability_per_hit float percentage (0-1) of the max durability taken each hit
     * @param name               String of the boat seen when the game ends
     * @param stamina_regen      float percentage of stamina regenerated each frame (0-1)
     * @param stamina_usage      float percentage of stamina used each frame when accelerating (0-1)
     * @author William Walton
     */
    PlayerBoat(int x, int y, int w, int h, String texture_path, String name, float durability_per_hit, float stamina_usage, float stamina_regen) {
        super(x, y, w, h, texture_path, name, durability_per_hit, stamina_usage, stamina_regen);

        initialise();
    }

    /**
     * Destructor disposes of this texture once it is no longer referenced.
     */
    protected void finalize() {
        stamina_texture.dispose();
        durability_texture.dispose();
    }

    /* ################################### //
                    METHODS
    // ################################### */

    /**
     * Shared initialisation functionality among all constructors.
     * <p>
     * Sets stamina bar and durability bar textures and sprites.
     * Initialises the bars' size and position.
     * Initialises camera position.
     */
    public void initialise() {
        stamina_texture = new Texture("stamina_texture.png");
        durability_texture = new Texture("durability_texture.png");

        stamina_bar = new Sprite(stamina_texture);
        durability_bar = new Sprite(durability_texture);

        stamina_bar.setSize(ui_bar_width, 10);
        durability_bar.setSize(ui_bar_width, 10);


        stamina_bar.setPosition(-ui_bar_width / 2, 5);
        durability_bar.setPosition(-ui_bar_width / 2, 20);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0, Gdx.graphics.getHeight() / 3, 0);
        camera.update();
    }

    /**
     * Sets the spec type of boat.
     * <p>
     * Can be in these states:
     * - debug
     * - default
     * - fast low durability
     *
     * @param spec_id int for boat spec
     */
    public void setSpec(int spec_id) {
        switch (spec_id) {
            case 0:
                // debug
                stamina_usage = 0f;
                durability_per_hit = 0f;
                break;
            case 1:
                // default
                break;
            case 2:
                // fast low durability
                max_speed = 20;
                durability_per_hit = .2f;
            default:
                break;
        }
    }
    public void setDiff(float diffDecrease){
        stamina_regen *= diffDecrease;
        stamina_usage *= diffDecrease;
        durability_per_hit *= 1f + diffDecrease;
    }

    /**
     * Updates the position based on the user's input.
     * <p>
     * 'W' key accelerates the boat.
     * 'A' Turns the boat to the left
     * 'D' Turns the boat to the right
     * <p>
     * Updates the x and y position of the sprite with new x and y according to which input has been requested.
     * The camera will follow the player's boat
     *
     * @author William Walton
     */
    @Override
    public void updatePosition() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.accelerate();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            // nothing atm
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.turn(1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.turn(-1);
        }

        float old_x = sprite.getX();
        float old_y = sprite.getY();

        super.updatePosition();

        // only follow player in x axis if they go off screen
        float dx = Math.abs(sprite.getX()) > Gdx.graphics.getWidth() / 3 ? sprite.getX() - old_x : 0;
        float dy = sprite.getY() - old_y;

        // move camera to follow player
        camera.translate(dx, dy, 0);
    }

    /**
     * Returns the all sprites for PlayerBoat UI.
     * <p>
     * This includes the stamina bar and durability bar.
     *
     * @return List of Sprites
     */
    public List<Sprite> getUISprites() {
        updateUISprites();  // TODO: probably move this to only when they change rather than every frame

        List<Sprite> ret = new ArrayList<Sprite>();
        ret.add(stamina_bar);
        ret.add(durability_bar);
        return ret;
    }

    /**
     * Getter for PlayerBoat Camera.
     *
     * @return OrthographicCamera
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Resets PlayerBoat Camera position
     */
    public void resetCameraPos() {
        camera.position.set(sprite.getX(), Gdx.graphics.getHeight() / 3, 0);
        camera.update();
    }

    /**
     * Update the position and size of the UI elements (e.g. stamina bar and durability bar) according to their values.
     * <p>
     * The stamina decreases as player requests the boat to row and move. It increases when this is not the case.
     * Durability decreases according to the collisions with other obstacles.
     * Dynamically updates the size of the stamina bar and durability bar
     * based on the PlayerBoat attributes as they change.
     */
    private void updateUISprites() {
        stamina_bar.setPosition(-ui_bar_width / 2 + sprite.getX() + sprite.getWidth() / 2, -50 + sprite.getY());
        durability_bar.setPosition(-ui_bar_width / 2 + sprite.getX() + sprite.getWidth() / 2, -35 + sprite.getY());

        stamina_bar.setSize((int) (ui_bar_width * stamina), 10);
        durability_bar.setSize((int) (ui_bar_width * durability), 10);
    }

}
