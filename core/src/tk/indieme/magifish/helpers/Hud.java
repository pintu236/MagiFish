package tk.indieme.magifish.helpers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import tk.indieme.magifish.MagiFishGame;
import tk.indieme.magifish.screens.PlayScreen;
import tk.indieme.magifish.screens.StartScreen;

public class Hud implements Disposable {
    public Stage stage;
    private MagiFishGame game;
    //Hud
    public Label scoreLabel;
    private Label pausedLabel;
    public ImageButton btnPause;
    //Game over
    private Image gameover, scoreboard;
    private ImageButton btnOK, btnMenu, btnShare;
    private HorizontalGroup btnGroup, gameoverScoreGroup;
    private Label gameoverScoreLabel, gameoverHighScoreLabel;


    public Hud(MagiFishGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);
    }

    public void createUI() {
        //Label Style
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.assetLoader.font;
        labelStyle.fontColor = Color.BLACK;

        //score label
        scoreLabel = new Label("0", labelStyle);
        scoreLabel.setVisible(false);
        scoreLabel.setPosition(Constants.WORLD_WIDTH / 2 - 16, Constants.WORLD_HEIGHT * 3 / 4);

        //game over score and highscore
        //change font to font16
        labelStyle.font = game.assetLoader.font16;
        gameoverScoreLabel = new Label("0", labelStyle);

        gameoverHighScoreLabel = new Label("0", labelStyle);
        //Paused label
        pausedLabel = new Label("Paused", labelStyle);

        //Gameover image
        gameover = new Image(game.assetLoader.uiAtlas.findRegion("gameover"));
        gameover.setPosition(Constants.WORLD_WIDTH / 2 - gameover.getWidth() / 2, Constants.WORLD_HEIGHT);
        //Scoreboard Image
        scoreboard = new Image(game.assetLoader.uiAtlas.findRegion("window"));
        scoreboard.setPosition(Constants.WORLD_WIDTH / 2 - scoreboard.getWidth() / 2, Constants.WORLD_HEIGHT / 2 - scoreboard.getHeight() / 2);

        //Pause Btn
        final ImageButton.ImageButtonStyle btnPauseStyle = new ImageButton.ImageButtonStyle();
        btnPauseStyle.imageUp = new TextureRegionDrawable(game.assetLoader.uiAtlas.findRegion("pause"));
        btnPauseStyle.imageDown = new TextureRegionDrawable(game.assetLoader.uiAtlas.findRegion("pause"));

        btnPause = new ImageButton(btnPauseStyle);
        btnPause.setVisible(false);
        btnPause.setPosition(Constants.WORLD_WIDTH / 2 - btnPause.getWidth() / 2, 0);
        //Share Btn
        ImageButton.ImageButtonStyle ibStyle1 = new ImageButton.ImageButtonStyle();
        ibStyle1.imageUp = new TextureRegionDrawable(game.assetLoader.uiAtlas.findRegion("share"));

        ImageButton btnShare = new ImageButton(ibStyle1);
        //Menu Btn
        ImageButton.ImageButtonStyle ibStyle2 = new ImageButton.ImageButtonStyle();
        ibStyle2.imageUp = new TextureRegionDrawable(game.assetLoader.uiAtlas.findRegion("menu"));

        ImageButton btnMenu = new ImageButton(ibStyle2);
        //Ok Btn
        ImageButton.ImageButtonStyle ibStyle3 = new ImageButton.ImageButtonStyle();
        ibStyle3.imageUp = new TextureRegionDrawable(game.assetLoader.uiAtlas.findRegion("ok"));

        ImageButton btnOk = new ImageButton(ibStyle3);

        //Horizontal group for buttons
        btnGroup = new HorizontalGroup();
        btnGroup.space(18);
        btnGroup.addActor(btnMenu);
        btnGroup.addActor(btnOk);
        btnGroup.addActor(btnShare);
        btnGroup.setPosition(Constants.WORLD_WIDTH / 2 - scoreboard.getWidth() / 2, -btnGroup.getHeight());

        //Horizontal group to align our game over scores
        gameoverScoreGroup = new HorizontalGroup();
        gameoverScoreGroup.space(148);
        gameoverScoreGroup.addActor(gameoverScoreLabel);
        gameoverScoreGroup.addActor(gameoverHighScoreLabel);

        gameoverScoreGroup.setPosition(Constants.WORLD_WIDTH / 4, Constants.WORLD_HEIGHT / 2);
        //Adding components to stage
        stage.addActor(scoreLabel);
        stage.addActor(btnPause);


        //Adding Animation gameover
        MoveToAction actionMove = Actions.action(MoveToAction.class);
        actionMove.setPosition(Constants.WORLD_WIDTH / 2 - gameover.getWidth() / 2, Constants.GAMEOVER_POSITION);

        actionMove.setDuration(0.25f);
        //actionMove.setInterpolation(Interpolation.elasticOut);
        gameover.addAction(actionMove);

        //Adding animation to scoreboard
        MoveToAction actionMove2 = Actions.action(MoveToAction.class);
        actionMove2.setPosition(Constants.WORLD_WIDTH / 2 - scoreboard.getWidth() / 2, Constants.GROUP_POSITION);
        actionMove2.setDuration(0.25f);

        btnGroup.addAction(actionMove2);

        btnOk.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchScreen(game, new PlayScreen(game));
            }
        });

        btnMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchScreen(game, new StartScreen(game));
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

    /**
     * Updates score
     **/
    public void updateScore(int score) {
        scoreLabel.setText(String.valueOf(score));
    }

    public void hideHud() {
        scoreLabel.setVisible(false);
        btnPause.setVisible(false);
    }

    public void showHud() {
        stage.addActor(scoreLabel);
        stage.addActor(btnPause);
    }

    /**
     * Updates scores on scoreboard
     **/
    public void updateScoreBoard(int currentScore) {
        int currentHighScore = game.gamePref.getInteger("highscore", 0);

        //Update highscore
        if (currentScore > currentHighScore) {
            game.gamePref.putInteger("highscore", currentScore);
            game.gamePref.flush();  //Persists score

            currentHighScore = currentScore;
        }

        gameoverScoreLabel.setText(String.valueOf(currentScore));
        gameoverHighScoreLabel.setText(String.valueOf(currentHighScore));
        game.gameServiceClient.submitToLeaderboard(Constants.LEADERBOARD_ID, currentHighScore, "score");
    }

    /**
     * Shows game over UI
     **/
    public void showGameOver() {
        //hide hud score label,pause button
        scoreLabel.setVisible(false);
        btnPause.setVisible(false);

        stage.addActor(gameover);
        stage.addActor(scoreboard);
        stage.addActor(gameoverScoreGroup);
        stage.addActor(btnGroup);


    }

    /**
     * Hide game over
     **/
    public void removeGameOver() {
        gameover.remove();
    }

    /**
     * Shows Pause Menu
     **/
    public void showPauseMenu() {
        pausedLabel.setPosition(Constants.WORLD_WIDTH / 2 - pausedLabel.getWidth() / 2, Constants.WORLD_HEIGHT / 2);

        stage.addActor(pausedLabel);
    }

    /**
     * Hides Pause Menu
     */
    public void hidePauseMenu() {
        pausedLabel.remove();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
