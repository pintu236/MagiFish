package tk.indieme.magifish.gameobjects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tk.indieme.magifish.MagiFishGame;
import tk.indieme.magifish.screens.PlayScreen;

public class Background {
    //For Scrolling
    private float posXBck1, posXBck2, yPos;
    private Vector2 velocity;
    //Texture
    private Texture background;

    //Player
    private Player player;
    //Camera
    private OrthographicCamera camera;

    public Background(float x, float y, MagiFishGame game) {
        //Assetloader reference to background texture
        background = game.assetLoader.background;
        player = ((PlayScreen) game.getScreen()).getPlayer();
        camera = ((PlayScreen) game.getScreen()).getCamera();
        posXBck1 = x;
        posXBck2 = posXBck1 + background.getWidth();
        yPos = y;
        velocity = new Vector2(-player.speed, 0);


    }


    public void update(float delta) {
        if (player.isAlive()) {
            //Infinte Scrolling Code

            posXBck1 = posXBck1 + velocity.x * delta;
            posXBck2 = posXBck2 + velocity.x * delta;

            if (posXBck1 + background.getWidth() <= camera.position.x - camera.viewportWidth / 2) {
                posXBck1 = camera.position.x + camera.viewportWidth / 2 - player.speed * delta;
            } else if (posXBck2 + background.getWidth() <= camera.position.x - camera.viewportWidth / 2) {
                posXBck2 = camera.position.x + camera.viewportWidth / 2 - player.speed * delta;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(background, posXBck1, yPos);
        batch.draw(background, posXBck2, yPos);
    }
}
