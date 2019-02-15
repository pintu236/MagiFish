package tk.indieme.magifish.helpers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

public class AssetLoader implements Disposable {
    private AssetManager assetManager;
    //Textures
    public Texture background, ground, obstacleUp, obstacleDown;
    //Fonts
    public BitmapFont font, font16;
    //SpriteSheet
    public TextureAtlas playerAtlas, uiAtlas;
    //Sounds
    public Sound sfxJump, sfxHurt, sfxDie;

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void init() {
        if (assetManager == null) {
            assetManager = new AssetManager();
        }
        //Loading game objects
        assetManager.load("bg_01.png", Texture.class);
        assetManager.load("characters/player.atlas", TextureAtlas.class);
        assetManager.load("ground.png", Texture.class);
        assetManager.load("obstacles/pipeDown.png", Texture.class);
        assetManager.load("obstacles/pipeUp.png", Texture.class);
        assetManager.load("ui/hud.atlas", TextureAtlas.class);
        //Loading Sounds
        assetManager.load("sfx/die.wav", Sound.class);
        assetManager.load("sfx/hurt.wav", Sound.class);
        assetManager.load("sfx/jump.wav", Sound.class);


        //loading font
        assetManager.load("font/font16.fnt", BitmapFont.class);
        assetManager.load("font/font.fnt", BitmapFont.class);

        //assetManager.finishLoading();
        //load();
    }

    public void load() {
        background = assetManager.get("bg_01.png", Texture.class);
        playerAtlas = assetManager.get("characters/player.atlas", TextureAtlas.class);
        obstacleDown = assetManager.get("obstacles/pipeDown.png", Texture.class);
        obstacleUp = assetManager.get("obstacles/pipeUp.png", Texture.class);
        ground = assetManager.get("ground.png", Texture.class);
        uiAtlas = assetManager.get("ui/hud.atlas");
        //Getting sounds
        sfxDie = assetManager.get("sfx/die.wav", Sound.class);
        sfxHurt = assetManager.get("sfx/hurt.wav", Sound.class);
        sfxJump = assetManager.get("sfx/jump.wav", Sound.class);
        //loading fonts
        font16 = assetManager.get("font/font16.fnt", BitmapFont.class);
        font = assetManager.get("font/font.fnt", BitmapFont.class);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

}
