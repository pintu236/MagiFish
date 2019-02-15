package tk.indieme.magifish;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.golfgl.gdxgamesvcs.IGameServiceClient;
import de.golfgl.gdxgamesvcs.IGameServiceListener;
import de.golfgl.gdxgamesvcs.NoGameServiceClient;
import tk.indieme.magifish.helpers.AssetLoader;
import tk.indieme.magifish.screens.PlayScreen;
import tk.indieme.magifish.screens.SplashScreen;
import tk.indieme.magifish.screens.StartScreen;

public class MagiFishGame extends Game implements IGameServiceListener {
    public SpriteBatch batch;
    public AssetLoader assetLoader;
    //Preferance for saving score
    public Preferences gamePref;
    //True for debugging
    public boolean debug = false;
    //PlayService Client
    public IGameServiceClient gameServiceClient;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gamePref = Gdx.app.getPreferences("gamePref");

        assetLoader = new AssetLoader();
        assetLoader.init();
        //Setting our AssetManager
        Texture.setAssetManager(assetLoader.getAssetManager());
        //Setting our Game Service

        if (gameServiceClient == null) {
            gameServiceClient = new NoGameServiceClient();
        }
        // for getting callbacks from the client
        gameServiceClient.setListener(this);

        // establish a connection to the game service without error messages or login screens
        gameServiceClient.logIn();


        setScreen(new SplashScreen(this));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        super.render();
    }

    @Override
    public void pause() {
        super.pause();
        gameServiceClient.pauseSession();
    }

    @Override
    public void resume() {
        if (assetLoader.getAssetManager().update()) {
            super.resume();
        }
        gameServiceClient.resumeSession();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetLoader.dispose();
        super.dispose();
    }

    @Override
    public void gsOnSessionActive() {

    }

    @Override
    public void gsOnSessionInactive() {

    }

    @Override
    public void gsShowErrorToUser(GsErrorType et, String msg, Throwable t) {

    }
}
