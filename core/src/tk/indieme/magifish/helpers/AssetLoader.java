package tk.indieme.magifish.helpers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

public class AssetLoader implements Disposable {
    private AssetManager assetManager;
    //Textures
    public Texture background, ground;
    //SpriteSheet
    private TextureAtlas playerAtlas;

    public void init() {
        if (assetManager == null) {
            assetManager = new AssetManager();
        }
        //Loading game objects
        assetManager.load("bg_01.png", Texture.class);
        assetManager.load("characters/player.atlas", TextureAtlas.class);

        assetManager.finishLoading();
        load();
    }

    private void load() {
        background = assetManager.get("bg_01.png", Texture.class);
        playerAtlas = assetManager.get("characters/player.atlas", TextureAtlas.class);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
