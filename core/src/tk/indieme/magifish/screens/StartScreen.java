package tk.indieme.magifish.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.golfgl.gdxgamesvcs.GameServiceException;
import tk.indieme.magifish.MagiFishGame;
import tk.indieme.magifish.helpers.Constants;
import tk.indieme.magifish.helpers.InfiniteScrolling;

public class StartScreen extends AbstractScreen {
    private Stage stage;

    public StartScreen(MagiFishGame game) {
        super(game);
        stage = new Stage(new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        //Fade-In Transition
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(Actions.fadeIn(0.5f));
        //Background Image
        Image bg = new Image(game.assetLoader.background);

        //Ground Image
        Image ground = new Image(game.assetLoader.ground);
        //Title Label
        Label.LabelStyle titleLabelStyle = new Label.LabelStyle();
        titleLabelStyle.font = game.assetLoader.font;

        Label titleLabel = new Label("MAGGYFISH", titleLabelStyle);

        //Play Button
        ImageButton.ImageButtonStyle ibStyle1 = new ImageButton.ImageButtonStyle();
        ibStyle1.imageUp = new TextureRegionDrawable(game.assetLoader.uiAtlas.findRegion("btnPlay"));
        ibStyle1.imageDown = new TextureRegionDrawable(game.assetLoader.uiAtlas.findRegion("btnPlayPressed"));

        ImageButton btnPlay = new ImageButton(ibStyle1);

        //Rate Button
        ImageButton.ImageButtonStyle ibStyle2 = new ImageButton.ImageButtonStyle();
        ibStyle2.imageUp = new TextureRegionDrawable(game.assetLoader.uiAtlas.findRegion("btnRate"));
        ibStyle2.imageDown = new TextureRegionDrawable(game.assetLoader.uiAtlas.findRegion("btnRate"));

        ImageButton btnRate = new ImageButton(ibStyle2);

        //Leaderboard Button
        ImageButton.ImageButtonStyle ibStyle3 = new ImageButton.ImageButtonStyle();
        ibStyle3.imageUp = new TextureRegionDrawable(game.assetLoader.uiAtlas.findRegion("btnLeaderboard"));
        ibStyle3.imageDown = new TextureRegionDrawable(game.assetLoader.uiAtlas.findRegion("btnLeaderboardPressed"));

        ImageButton btnLeaderboard = new ImageButton(ibStyle3);

        //Horizontal group for play and leaderboard button
        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.space(16);
        horizontalGroup.addActor(btnPlay);
        horizontalGroup.addActor(btnLeaderboard);

        //Vertical Group for btn rate and horizontal group
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(16);
        verticalGroup.addActor(btnRate);
        verticalGroup.addActor(horizontalGroup);
        //Creating Table for UI
        Table table = new Table();
        table.setFillParent(true);
        //Adding components to table
        table.add(titleLabel).padBottom(148);
        table.row();
        //table.add(new StartFish(game)).spaceBottom(bottomMargin);    //Bottom margin below fish
        table.add(verticalGroup).padBottom(128);
        //Adding to stage
        stage.addActor(bg);
        stage.addActor(table);
        //Infinite scrolling ground
        stage.addActor(new InfiniteScrolling(ground.getWidth(), ground.getHeight(), game));

        //Adding listeners
        btnPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchScreen(game, new PlayScreen(game));

            }
        });

        btnLeaderboard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    game.gameServiceClient.showLeaderboards(Constants.LEADERBOARD_ID);
                } catch (GameServiceException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Switches screen with fade animation
     **/
    public void switchScreen(final Game game, final Screen newScreen) {
        stage.getRoot().setColor(1, 1, 1, 0);
        stage.getRoot().getColor().a = 1;
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeOut(0.5f));
        sequenceAction.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(newScreen);
                dispose();
            }
        }));
        stage.getRoot().addAction(sequenceAction);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}
