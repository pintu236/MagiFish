package tk.indieme.magifish.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import tk.indieme.magifish.MagiFishGame;
import tk.indieme.magifish.helpers.Constants;

public class SplashScreen extends AbstractScreen {

    private OrthographicCamera camera;

    private Texture splashTexture, splashTitle;
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> splashAnim;
    private float stateTimer;

    public SplashScreen(MagiFishGame game) {
        super(game);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        camera.update();
    }

    @Override
    public void show() {
        super.show();
        frames = new Array<TextureRegion>();
        splashTexture = new Texture(Gdx.files.internal("splash_anim.png"));
        splashTitle = new Texture(Gdx.files.internal("splash_title.png"));
        TextureRegion[][] tmp = TextureRegion.split(splashTexture, 64, 64);
        for (int i = 0; i < 3; i++) {
            frames.add(tmp[0][i]);
        }
        splashAnim = new Animation<TextureRegion>(0.15f, frames, Animation.PlayMode.NORMAL);
    }

    @Override
    public void render(float delta) {
        stateTimer += delta;

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(splashAnim.getKeyFrame(stateTimer), Constants.WORLD_WIDTH / 2 - 32, Constants.WORLD_HEIGHT / 2);
        game.batch.draw(splashTitle, Constants.WORLD_WIDTH / 2 - 32, Constants.WORLD_HEIGHT / 2 - 32);
        game.batch.end();

        //Check if asset loaded
        if (game.assetLoader.getAssetManager().update() && splashAnim.isAnimationFinished(stateTimer)) {
            game.assetLoader.load();
            game.setScreen(new StartScreen(game));
        }

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
        splashTexture.dispose();
        splashTitle.dispose();
    }

}
