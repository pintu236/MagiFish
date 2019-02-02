package tk.indieme.magifish.screens;

import com.badlogic.gdx.Screen;
import tk.indieme.magifish.MagiFishGame;

public abstract class AbstractScreen implements Screen {
    MagiFishGame game;
    AbstractScreen(MagiFishGame game){
        this.game = game;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
