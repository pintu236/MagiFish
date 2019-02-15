package tk.indieme.magifish.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tk.indieme.magifish.MagiFishGame;
import tk.indieme.magifish.helpers.Constants;
import tk.indieme.magifish.screens.PlayScreen;

import java.awt.*;

public class Ground {
    //Physics
    private Vector2 velocity;
    private float posXBck1, posXBck2, yPos;
    //Texture
    private Texture ground;
    private Player player;
    //Camera
    private OrthographicCamera camera;

    public Ground(float x, float y, MagiFishGame game) {
        ground = game.assetLoader.ground;
        yPos = y;
        posXBck1 = x;
        posXBck2 = posXBck1 + ground.getWidth();

        this.camera = ((PlayScreen) game.getScreen()).getCamera();
        player = ((PlayScreen) game.getScreen()).getPlayer();
        velocity = new Vector2(-player.speed, 0);

    }

    //Getters
    public float getY() {
        return yPos;
    }

    public float getHeight() {
        return ground.getHeight();
    }

    public void update(float delta) {
        if (player.isAlive()) {
            //Infinte Scrolling Code
            posXBck1 = posXBck1 + velocity.x * delta;
            posXBck2 = posXBck2 + velocity.x * delta;

            if (posXBck1 + ground.getWidth() <= camera.position.x - camera.viewportWidth / 2) {
                posXBck1 = camera.position.x + camera.viewportWidth / 2 - player.speed * delta;
            } else if (posXBck2 + ground.getWidth() <= camera.position.x - camera.viewportWidth / 2) {
                posXBck2 = camera.position.x + camera.viewportWidth / 2 - player.speed * delta;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(ground, posXBck1, yPos);
        batch.draw(ground, posXBck2, yPos);
    }


}
