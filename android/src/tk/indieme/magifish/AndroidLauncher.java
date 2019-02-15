package tk.indieme.magifish;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import de.golfgl.gdxgamesvcs.GpgsClient;
import tk.indieme.magifish.MagiFishGame;

public class AndroidLauncher extends AndroidApplication {
    private GpgsClient gpgsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        MagiFishGame game = new MagiFishGame();
        game.gameServiceClient = new GpgsClient().initialize(this, false);
        initialize(game, config);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (gpgsClient != null)
            gpgsClient.onGpgsActivityResult(requestCode, resultCode, data);
    }
}
