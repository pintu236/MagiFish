package tk.indieme.magifish;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tk.indieme.magifish.helpers.AssetLoader;
import tk.indieme.magifish.screens.PlayScreen;

public class MagiFishGame extends Game {
	public SpriteBatch batch;
	public AssetLoader assetLoader;
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetLoader =new AssetLoader();
		assetLoader.init();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assetLoader.dispose();
		super.dispose();
	}
}
