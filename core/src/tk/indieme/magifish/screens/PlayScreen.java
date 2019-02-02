package tk.indieme.magifish.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import tk.indieme.magifish.MagiFishGame;
import tk.indieme.magifish.gameobjects.Background;
import tk.indieme.magifish.helpers.Constants;

public class PlayScreen extends AbstractScreen {

    //Orthographic Camera
    private OrthographicCamera camera;
    //Game Objects
    private Background background;

    public PlayScreen(MagiFishGame game) {
        super(game);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
    }

    @Override
    public void show() {
        //Creating game objects
        background = new Background(0, 0, game);

    }

    @Override
    public void render(float delta) {
        updateScene(delta);
        drawScene();

    }

    /**
     * Draws all our game objects
     */
    private void drawScene() {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        background.draw(game.batch);
        game.batch.end();
    }

    /**
     * Updates all our game objects
     **/
    private void updateScene(float delta) {
        background.update(delta);
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
    }
}
