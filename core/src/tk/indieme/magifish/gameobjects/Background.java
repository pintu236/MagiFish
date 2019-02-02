package tk.indieme.magifish.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tk.indieme.magifish.MagiFishGame;

public class Background {

    //Texture
    private Texture background;

    public Background(float x, float y, MagiFishGame game) {
        //Assetloader reference to background texture
        background = game.assetLoader.background;
    }


    public void update(float delta) {

    }

    public void draw(SpriteBatch batch) {
        batch.draw(background,0,0);
    }
}
