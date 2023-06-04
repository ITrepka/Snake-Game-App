import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;



public class SnakeGameTest {
    private SnakeGame snakeGame;

    @BeforeEach
    public void setup() {
        snakeGame = new SnakeGame();
    }

    @Test
    public void testInitialGameSetup() {
        Assertions.assertEquals(3, snakeGame.dots);
        Assertions.assertEquals(50, snakeGame.x[0]);
        Assertions.assertEquals(50, snakeGame.y[0]);
        Assertions.assertEquals(40, snakeGame.x[1]);
        Assertions.assertEquals(50, snakeGame.y[1]);
        Assertions.assertEquals(30, snakeGame.x[2]);
        Assertions.assertEquals(50, snakeGame.y[2]);
    }

    @Test
    public void testSnakeMovement_RightDirection() {
        snakeGame.rightDirection = true;

        snakeGame.move();

        Assertions.assertEquals(60, snakeGame.x[0]);
        Assertions.assertEquals(50, snakeGame.y[0]);
        Assertions.assertEquals(50, snakeGame.x[1]);
        Assertions.assertEquals(50, snakeGame.y[1]);
        Assertions.assertEquals(40, snakeGame.x[2]);
        Assertions.assertEquals(50, snakeGame.y[2]);
    }

    @Test
    public void testSnakeMovement_UpDirection() {
        snakeGame.rightDirection = false;
        snakeGame.upDirection = true;

        snakeGame.move();

        Assertions.assertEquals(50, snakeGame.x[0]);
        Assertions.assertEquals(40, snakeGame.y[0]);
        Assertions.assertEquals(50, snakeGame.x[1]);
        Assertions.assertEquals(50, snakeGame.y[1]);
        Assertions.assertEquals(40, snakeGame.x[2]);
        Assertions.assertEquals(50, snakeGame.y[2]);
    }

    @Test
    public void testSnakeMovement_DownDirection() {
        snakeGame.rightDirection = false;
        snakeGame.downDirection = true;

        snakeGame.move();

        Assertions.assertEquals(50, snakeGame.x[0]);
        Assertions.assertEquals(60, snakeGame.y[0]);
        Assertions.assertEquals(50, snakeGame.x[1]);
        Assertions.assertEquals(50, snakeGame.y[1]);
        Assertions.assertEquals(40, snakeGame.x[2]);
        Assertions.assertEquals(50, snakeGame.y[2]);
    }


    @Test
    public void testKeyPressed_Right() {
        KeyEvent keyEvent = new KeyEvent(snakeGame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        snakeGame.keyPressed(keyEvent);

        Assertions.assertFalse(snakeGame.leftDirection);
        Assertions.assertTrue(snakeGame.rightDirection);
        Assertions.assertFalse(snakeGame.upDirection);
        Assertions.assertFalse(snakeGame.downDirection);
    }

    @Test
    public void testKeyPressed_Up() {
        KeyEvent keyEvent = new KeyEvent(snakeGame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED);
        snakeGame.keyPressed(keyEvent);

        Assertions.assertFalse(snakeGame.leftDirection);
        Assertions.assertFalse(snakeGame.rightDirection);
        Assertions.assertTrue(snakeGame.upDirection);
        Assertions.assertFalse(snakeGame.downDirection);
    }

    @Test
    public void testKeyPressed_Left() {
        /*in the start settings, the snake moves to the right, so we do not have the ability to switch its movement
        directly to the left, so before pressing the left arrow, we simulate pressing the up arrow*/
        KeyEvent keyEvent = new KeyEvent(snakeGame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED);
        snakeGame.keyPressed(keyEvent);

        keyEvent = new KeyEvent(snakeGame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);
        snakeGame.keyPressed(keyEvent);

        Assertions.assertTrue(snakeGame.leftDirection);
        Assertions.assertFalse(snakeGame.rightDirection);
        Assertions.assertFalse(snakeGame.upDirection);
        Assertions.assertFalse(snakeGame.downDirection);
    }

    @Test
    public void testKeyPressed_Down() {
        KeyEvent keyEvent = new KeyEvent(snakeGame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, KeyEvent.CHAR_UNDEFINED);
        snakeGame.keyPressed(keyEvent);

        Assertions.assertFalse(snakeGame.leftDirection);
        Assertions.assertFalse(snakeGame.rightDirection);
        Assertions.assertFalse(snakeGame.upDirection);
        Assertions.assertTrue(snakeGame.downDirection);
    }


    @Test
    public void testAppleLocation() {
        snakeGame.locateApple();

        Assertions.assertTrue(snakeGame.appleX >= 0 && snakeGame.appleX <= 290);
        Assertions.assertTrue(snakeGame.appleY >= 0 && snakeGame.appleY <= 290);
    }

    @Test
    public void testCollisionWithApple() {
        snakeGame.x[0] = 50;
        snakeGame.y[0] = 50;
        snakeGame.appleX = 50;
        snakeGame.appleY = 50;

        snakeGame.checkApple();

        Assertions.assertEquals(4, snakeGame.dots);
        Assertions.assertNotEquals(50, snakeGame.appleX);
        Assertions.assertNotEquals(50, snakeGame.appleY);
    }

    @Test
    public void testCollisionWithWall() {
        snakeGame.x[0] = 310;
        snakeGame.y[0] = 10;

        snakeGame.checkCollision();

        Assertions.assertFalse(snakeGame.inGame);

        snakeGame.x[0] = 10;
        snakeGame.y[0] = -10;

        snakeGame.checkCollision();

        Assertions.assertFalse(snakeGame.inGame);
    }
}
