package tk.indieme.magifish.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import tk.indieme.magifish.MagiFishGame;
import tk.indieme.magifish.screens.PlayScreen;

public class Obstacle {
    //Physics
    private Vector2 positionTopObstacle, positionBottomObstacle, velocity;

    private static final int MAX_HEIGHT = 50;// INITIAL MAX_HEIGHT
    private static final int OBSTACLE_GAP = 125;    //100
    private static final int LOWEST_OPENING = 220;//215
    //Texture
    private Texture obstacleDown, obstacleUp;
    //Target Object
    private Player player;
    //HitBox
    private Rectangle topHitBox, bottomHitBox;
    private boolean pointClaimed;

    public Obstacle(float x, MagiFishGame game) {
        obstacleDown = game.assetLoader.obstacleDown;
        obstacleUp = game.assetLoader.obstacleUp;
        player = ((PlayScreen) game.getScreen()).getPlayer();
        velocity = new Vector2(0, 0);

        positionTopObstacle = new Vector2(x, MathUtils.random(MAX_HEIGHT) + OBSTACLE_GAP + LOWEST_OPENING);
        positionBottomObstacle = new Vector2(x, positionTopObstacle.y - OBSTACLE_GAP - obstacleDown.getHeight());

        topHitBox = new Rectangle(positionTopObstacle.x, positionTopObstacle.y, obstacleUp.getWidth(), obstacleUp.getHeight());
        bottomHitBox = new Rectangle(positionBottomObstacle.x, positionBottomObstacle.y, obstacleDown.getWidth(), obstacleDown.getHeight());

    }
    //Getters

    public float getX() {
        return positionBottomObstacle.x;
    }
    public Rectangle getBottomHitBox() {
        return  bottomHitBox;
    }
    public Rectangle getTopHitBox(){
        return  topHitBox;
    }
    public float getWidth() {
        return obstacleDown.getWidth();
    }

    public boolean isPointClaimed() {
        return pointClaimed;
    }
    public void markPointClaimed(){
        pointClaimed = true;
    }
    public void update(float delta) {
        if (player.isAlive()) {
            //Scrolls Pipe
            velocity.x = -player.speed;
            //Updating position
            positionTopObstacle.mulAdd(velocity, delta);
            positionBottomObstacle.mulAdd(velocity, delta);

            //Updating hitBox;
            bottomHitBox.setPosition(positionBottomObstacle.x, positionBottomObstacle.y);
            topHitBox.setPosition(positionTopObstacle.x, positionTopObstacle.y);
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(obstacleDown, positionBottomObstacle.x, positionBottomObstacle.y);
        batch.draw(obstacleUp, positionTopObstacle.x, positionTopObstacle.y);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(bottomHitBox.x, bottomHitBox.y, bottomHitBox.width, bottomHitBox.height);
        shapeRenderer.rect(topHitBox.x, topHitBox.y, topHitBox.width, topHitBox.height);
    }
    /**
     * Reset Obstacle Position
     * **/
    public void reposition(float x) {
        pointClaimed = false;
        positionTopObstacle.set(x, MathUtils.random(MAX_HEIGHT) + OBSTACLE_GAP + LOWEST_OPENING);
        positionBottomObstacle.set(x, positionTopObstacle.y - OBSTACLE_GAP - obstacleDown.getHeight());
    }


}
