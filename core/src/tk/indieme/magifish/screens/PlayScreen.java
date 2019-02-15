package tk.indieme.magifish.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import tk.indieme.magifish.MagiFishGame;
import tk.indieme.magifish.gameobjects.Background;
import tk.indieme.magifish.gameobjects.Ground;
import tk.indieme.magifish.gameobjects.Obstacle;
import tk.indieme.magifish.gameobjects.Player;
import tk.indieme.magifish.helpers.Constants;
import tk.indieme.magifish.helpers.Hud;

public class PlayScreen extends AbstractScreen {

    //Orthographic Camera
    private OrthographicCamera camera;


    //Game States
    private enum GameState {
        READY, RUNNING, PAUSED, GAMEOVER
    }

    private GameState currentGameState;
    private boolean gameOverShown;
    //Game Objects
    private Background background;
    private Player player;
    private Ground ground;
    private Array<Obstacle> obstacleArray;
    private Hud hud;
    private int score;
    private ShapeRenderer shapeRenderer;
    private Sound sfxJump, sfxDie, sfxHurt;
    private boolean jumpSoundPlaying;
    private TextureRegion ready, tapRight, tapLeft, tapIndicator, tapUp, fishReady;

    public PlayScreen(MagiFishGame game) {
        super(game);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        camera.update();
    }

    //Getters
    public Player getPlayer() {
        return player;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public void show() {
        //Loading sound resource
        sfxDie = game.assetLoader.sfxJump;
        sfxHurt = game.assetLoader.sfxHurt;
        sfxJump = game.assetLoader.sfxJump;
        //Creating game objects
        player = new Player(Constants.WORLD_WIDTH / 4, Constants.WORLD_HEIGHT / 2, game);
        background = new Background(0, 0, game);
        ground = new Ground(0, 0, game);
        obstacleArray = new Array<Obstacle>();

        for (int i = 0; i < Constants.OBSTACLE_COUNT; i++) {
            obstacleArray.add(new Obstacle(Constants.WORLD_WIDTH + i * Constants.GAP_BETWEEN_OBSTACLE, game));
        }

        ready = game.assetLoader.uiAtlas.findRegion("ready");
        tapRight = game.assetLoader.uiAtlas.findRegion("tapRight");
        tapLeft = game.assetLoader.uiAtlas.findRegion("tapLeft");
        tapIndicator = game.assetLoader.uiAtlas.findRegion("tapIndicator");
        tapUp = game.assetLoader.uiAtlas.findRegion("tapUp");
        fishReady = game.assetLoader.uiAtlas.findRegion("fish");

        shapeRenderer = new ShapeRenderer();

        hud = new Hud(game);
        hud.createUI();

        currentGameState = GameState.READY;

        hud.btnPause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentGameState = GameState.PAUSED;
                hud.showPauseMenu();

            }
        });

        //Fade-In
        //Fade-In Transition
        hud.stage.getRoot().getColor().a = 0;
        hud.stage.getRoot().addAction(Actions.fadeIn(0.5f));
    }

    @Override
    public void render(float delta) {
        updateScene(delta);
        drawScene();
        if (game.debug) {
            drawDebug();
        }
        hud.stage.act();
        hud.stage.draw();

    }

    /**
     * Draws all our game objects
     */
    private void drawScene() {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        background.draw(game.batch);

        for (Obstacle obstacle : obstacleArray) {
            obstacle.draw(game.batch);
        }
        ground.draw(game.batch);
        player.draw(game.batch);
        //Drawing game ready UI
        if (currentGameState == GameState.READY) {
            game.batch.draw(ready, Constants.WORLD_WIDTH / 2 - ready.getRegionWidth() / 2, Constants.WORLD_HEIGHT - Constants.WORLD_HEIGHT / 4);
            game.batch.draw(tapRight, Constants.WORLD_WIDTH / 4 + tapRight.getRegionWidth(), Constants.WORLD_HEIGHT / 2 - 40);
            game.batch.draw(tapLeft, Constants.WORLD_WIDTH / 4 + Constants.TAP_OFFSET + tapLeft.getRegionWidth(), Constants.WORLD_HEIGHT / 2 - 40);
            game.batch.draw(tapIndicator, Constants.WORLD_WIDTH / 4 + Constants.TAP_OFFSET, Constants.WORLD_HEIGHT / 2 - 50);
            game.batch.draw(tapUp, Constants.WORLD_WIDTH / 4 + Constants.TAP_OFFSET, Constants.WORLD_HEIGHT / 2);
            game.batch.draw(fishReady, Constants.WORLD_WIDTH / 4 + Constants.TAP_OFFSET, Constants.WORLD_HEIGHT / 2 + 30);

        }
        game.batch.end();
    }

    private void drawDebug() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Obstacle obstacle : obstacleArray) {
            obstacle.drawDebug(shapeRenderer);
        }
        player.drawDebug(shapeRenderer);
        shapeRenderer.end();
    }

    /**
     * Updates all our game objects
     **/
    private void updateScene(float delta) {

        switch (currentGameState) {
            case READY:
                updateReady();
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case PAUSED:
                updatePaused();
                break;
            case GAMEOVER:
                updateGameOver();
                break;
        }
    }

    /**
     * Runs when current game state is ready
     **/
    private void updateReady() {
        if (Gdx.input.justTouched()) {
            hud.scoreLabel.setVisible(true);
            hud.btnPause.setVisible(true);
            currentGameState = GameState.RUNNING;
        }
    }

    /**
     * Runs when current game state is paused
     **/
    private void updatePaused() {
        if (Gdx.input.justTouched()) {
            hud.hidePauseMenu();
            currentGameState = GameState.RUNNING;
        }
    }

    /**
     * Runs when current game state is running
     **/
    private void updateRunning(float delta) {

        //Input Handling
        if (Gdx.input.isTouched()) {
            player.moveUp();
            if (!jumpSoundPlaying && player.alive) {
                sfxJump.play();
                jumpSoundPlaying = true;
            }
        } else {

            jumpSoundPlaying = false;
        }


        //Updating objects
        background.update(delta);
        player.update(delta);
        updateObsatcles(delta);
        ground.update(delta);
        //Collision Checking
        checkCollision();

    }

    /**
     * Runs when current game state is game over
     **/
    private void updateGameOver() {
        if (!gameOverShown) {
            hud.updateScoreBoard(score);
            hud.showGameOver();
            gameOverShown = true;
            sfxDie.play();

        }
    }

    /**
     * Updates obstacle and check collision with player
     **/
    private void updateObsatcles(float delta) {
        for (Obstacle obstacle : obstacleArray) {
            obstacle.update(delta);

            if (obstacle.getX() + obstacle.getWidth() < 0) {
                obstacle.reposition(obstacle.getX() + Constants.OBSTACLE_COUNT * Constants.GAP_BETWEEN_OBSTACLE);
            }

            //Collision detection with obstacle
            if (Intersector.overlaps(player.getHitBox(), obstacle.getBottomHitBox())
                    || Intersector.overlaps(player.getHitBox(), obstacle.getTopHitBox())) {
                if (player.alive) {
                    sfxHurt.play();
                }
                player.speed = 0;
                player.alive = false;

            }
            //Updating Score
            if (player.position.x > obstacle.getX() + obstacle.getWidth() && !obstacle.isPointClaimed()) {
                obstacle.markPointClaimed();
                score++;
                 hud.updateScore(score);
            }
        }
    }

    /**
     * Checks collision with ground
     **/
    private void checkCollision() {
        //Collision detection code starts here
        //Player-Ground
        if (player.position.y < ground.getY() + ground.getHeight()) {
            player.alive = false;
            player.speed = 0;
            player.position.y = ground.getY() + ground.getHeight();
            currentGameState = GameState.GAMEOVER;
        } else if (player.position.y + player.getHeight() > Constants.WORLD_HEIGHT) {
            player.position.y = Constants.WORLD_HEIGHT - player.getHeight();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        hud.stage.getViewport().update(width, height, true);
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
        hud.stage.dispose();
    }


}
