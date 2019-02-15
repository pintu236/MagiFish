package tk.indieme.magifish.gameobjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import tk.indieme.magifish.MagiFishGame;

public class Player {


    //Physics
    public Vector2 position, velocity, gravity, origin;
    private float rotation;
    private float upSpeed = 200;
    private static final float MAX_GRAVITY = -220;
    public float speed = 100;     //Scrolling Speed
    //Texture,Animation
    private Animation<TextureRegion> splashAnim;
    private Array<TextureAtlas.AtlasRegion> frames;
    public boolean alive;
    //Hitbox
    private Circle hitBox;
    private static final float COLLISION_RADIUS = 12;
    private float WIDTH, HEIGHT;
    private float target_rotation;

    private float stateTimer;

    public Player(float x, float y, MagiFishGame game) {

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        gravity = new Vector2(0, -780);
        origin = new Vector2(x, y);

        frames = game.assetLoader.playerAtlas.findRegions("normalfish");
        WIDTH = frames.get(0).getRegionWidth();
        HEIGHT = frames.get(0).getRegionHeight();
        splashAnim = new Animation<TextureRegion>(0.15f, frames, Animation.PlayMode.LOOP);
        hitBox = new Circle(x + WIDTH / 2, y + HEIGHT / 2, COLLISION_RADIUS);

        alive = true;
    }
    //Getters

    public boolean isAlive() {
        return alive;
    }

    public Circle getHitBox() {
        return hitBox;
    }

    public float getHeight() {
        return HEIGHT;
    }

    public void update(float delta) {
        stateTimer += delta;

        //Applying scalar graivty
        velocity.mulAdd(gravity, delta); //vel.x += accl.x * delta
        if (velocity.y < MAX_GRAVITY) {
            velocity.y = MAX_GRAVITY;
        }
        //if falling change rotation
        if (velocity.y < -120) {
            target_rotation = -15;
        }

        //Updating rotation
        rotation = MathUtils.lerp(rotation, target_rotation, 0.25f);
        //Updating position
        position.mulAdd(velocity, delta); //pos.x += vel.x *delta
        //Updating hitBox
        hitBox.setPosition(position.x + WIDTH / 2, position.y + HEIGHT / 2);

    }


    public void moveUp() {
        if (alive) {
            velocity.y = upSpeed;
            target_rotation = 15;
        }
    }

    public void draw(SpriteBatch batch) {
        TextureRegion region = null;
        region = splashAnim.getKeyFrame(stateTimer);

        //batch.draw(region,position.x,position.y);
        batch.draw(region, position.x, position.y, WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, 1, 1, rotation);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(hitBox.x, hitBox.y, hitBox.radius);
    }

}
