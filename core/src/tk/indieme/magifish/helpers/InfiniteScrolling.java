package tk.techackers.taptapsplash.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import tk.techackers.taptapsplash.TapTapSplash;

/**
 * This Class is a actor which acts as Infinite Scrolling ground in start screen
 *
 * @author Sourabh jurri
 * @date Aug-11-18
 */
public class InfiniteScrolling extends Actor {
    private final TapTapSplash game;
    private final float scrollSpeed = 150;    //Large is faster
    private int srcX = 0;
    private Texture ground;

    public InfiniteScrolling(float width, float height, TapTapSplash game) {
        this.game = game;
        setBounds(0, 0, width, height);
        setPosition(0, 0);
        ground = game.assetLoader.ground;
        //This moves ground infinitely
        ground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        //This moves ground infinitely
        srcX += scrollSpeed * delta;
        srcX = srcX % ground.getWidth();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        //This draws ground infinitely
        batch.draw(ground, getX(), getY(), srcX, 0, ground.getWidth(), ground.getHeight());
    }
}
