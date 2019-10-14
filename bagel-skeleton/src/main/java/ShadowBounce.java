/**
 * Sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 *
 * @author Eleanor McMurtry
 */

import bagel.*;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.Random;

public class ShadowBounce extends AbstractGame {
    private ArrayList<Ball> balls = new ArrayList<>();
    private Powerup powerup = null;
    private Bucket bucket = new Bucket();
    private int boardNumber = 0;
    private Board board = new Board(boardNumber);

    private static final Point BALL_POSITION = new Point(512, 32);
    private static final int POWERUP_CHANCE = 10;
    private int shotsLeft = 20;

    //Check number of shots left
    private void checkShotsLeft(){
        System.out.println(shotsLeft + " shots remaining");
        if (shotsLeft > 0){
            return;
        }
        // No shots left then end game
        endGame();
    }

    // Clear all values and show blank screen at end of game
    private void endGame(){
        board.clearPegs();
        balls.clear();
        powerup = null;
        bucket = null;
    }

    // Prepare next board unless all boards have been played
    private void nextBoard(){
        if (boardNumber == 5){
            // End game when all boards have been completed
            endGame();
        } else if (boardNumber < 6){
            // Set up next board and reset random variables
            board = new Board(boardNumber);
            powerup = null;
            balls.clear();
            // Randomly choose red and green pegs, powerup and bucket at start of new board
            setPowerup();
            bucket = new Bucket();
            // Increase board number
            boardNumber++;
        }
    }

    // POWERUP INITIALISATION: 1/10 chance for powerup to appear every turn
    private void setPowerup(){
        Random rand = new Random();
        int chance = 1;
        int randomInt = rand.nextInt(POWERUP_CHANCE);
        if (randomInt == chance){
            // Choose random locations for peg to start and go towards
            Point randomStart = new Point(rand.nextInt(Window.getWidth()), rand.nextInt(Window.getHeight()));
            Point randomDestination = new Point(rand.nextInt(Window.getWidth()), rand.nextInt(Window.getHeight()));
            powerup = new Powerup(randomStart, randomDestination);
        } else {
            powerup = null;
        }
    }

    // Initialise board before starting game
    private ShadowBounce() {
        nextBoard();
    }

    @Override
    protected void update(Input input) {
        Peg[] pegs = board.getPegs();

        // Check for collisions for each ball on screen
        for (int i = 0; i < balls.size(); i++){
            // Unless green ball is hit, arraylist size for balls should be 1
            Ball ball = balls.get(i);
            // Check all non-deleted pegs for intersection with the ball
            for (int j = 0; j < pegs.length; j++) {
                if (pegs[j] != null) {
                    if (ball.intersects(pegs[j])) {
                        pegs[j] = pegs[j].onCollision(balls, i, pegs);
                    }
                }
            }

            // Check for collision with powerup
            if (powerup != null && ball.intersects(powerup)){
                powerup = powerup.onCollision(balls, i);
            }

            // Check for collision with bucket
            if (ball.intersects(bucket)){
                shotsLeft = bucket.onCollision(shotsLeft);
            }
        }


        // Draw all active pegs, bucket and possible powerup
        for (int j = 0; j < pegs.length; j++){
            if (pegs[j] != null) {
                pegs[j].update();
            }
        }
        if (powerup != null){
            powerup.update();
        }
        if (bucket != null){
            bucket.update();
        }

        // Check for any red pegs left
        if (board.checkAllRedPegs()){
            // If all red pegs are destroyed, load next board
            nextBoard();
            return;
        }

        // If we don't have a ball and left mouse button was clicked, create one
        if (input.wasPressed(MouseButtons.LEFT) && balls.size() == 0) {
            balls.add(new Ball(BALL_POSITION, input.directionToMouse(BALL_POSITION), ""));
        }

        // Check for balls still on screen and display
        if (balls.size() != 0) {
            int arraySize = balls.size() - 1;
            while(arraySize >= 0){
                balls.get(arraySize).update();

                // When ball leaves screen, delete ball
                if (balls.get(arraySize).outOfScreen()) {
                    balls.remove(arraySize);
                }
                arraySize--;
            }
            // When all balls are off screen, end turn
            if (balls.size() == 0){
                checkShotsLeft();
                shotsLeft--;
                board.setRandomGreenPeg();
                setPowerup();
            }
        }


//        // Debugging function for testing new boards using space to load next board
//        if (input.wasPressed(Keys.SPACE)){
//            nextBoard();
//        }

    }


    public static void main(String[] args) {
        new ShadowBounce().run();
    }
}
