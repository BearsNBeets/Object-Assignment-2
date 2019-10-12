/**
 * Sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 *
 * @author Eleanor McMurtry
 */

import bagel.*;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ShadowBounce extends AbstractGame {
    private Peg[] pegs;
    private ArrayList<Ball> balls = new ArrayList<>();
    private Powerup powerup = null;
    private Bucket bucket = new Bucket();
    private static final Point BALL_POSITION = new Point(512, 32);
    private static final int POWERUP_CHANCE = 10;
    private int boardNumber = 0;
    private int shotsLeft = 20;
    private int greenPegNumber = -1;


    //Check number of shots left
    private void checkShotsLeft(){
//        System.out.println(shotsLeft);
        if (shotsLeft > 0){
            return;
        }
        endGame();
    }

    //Check whether all red pegs have been destroyed
    private boolean checkAllRedPegs(){
        for (Peg peg : pegs) {
            if (peg instanceof RedPeg) {
                return false;
            }
        }
        return true;
    }

    private void endGame(){
        Arrays.fill(pegs, null);
        balls.clear();
        powerup = null;
        bucket = null;
    }

    //Prepare next board unless all boards have been played
    private void nextBoard(){
        if (boardNumber == 5){
            endGame();
        } else if (boardNumber < 6){
            // Set up next board and reset random variables
            int boardSize = boardSize(boardNumber);
            pegs = new Peg[boardSize];
            loadBoard(boardNumber);
            powerup = null;
            balls.clear();
            // Randomly choose red and green pegs, powerup and bucket at start of new board
            setRandomRedPegs();
            greenPegNumber = -1;
            setRandomGreenPeg();
            setPowerup();
            bucket = new Bucket();
            // Increase board number
            boardNumber++;
        }
    }

    // Calculate length of csv file to determine number of pegs
    private int boardSize(int boardNumber){
        int row = 0;
        try(BufferedReader csvReader = new BufferedReader(new FileReader("res/boards/" + boardNumber + ".csv"))){
            while (csvReader.readLine() != null){
                row++;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    // Load board from file
    private void loadBoard(int boardNumber){
        try(BufferedReader csvReader = new BufferedReader(new FileReader("res/boards/" + boardNumber + ".csv"))) {
            String text;
            int row = 0;
            while ((text = csvReader.readLine()) != null) {
                /*Arrangement of cells from reading csv is cells[0] = type, cells[1] = x, cells[2] = y*/
                String[] cells = text.split(",");
                Point point = new Point(Integer.parseInt(cells[1]), Integer.parseInt(cells[2]));
                String type = cells[0];
                //Initialise pegs on board as blue or grey
                switch (type) {
                    case "blue_peg":
                        pegs[row] = new BluePeg(point, "-");
                        break;
                    case "blue_peg_vertical":
                        pegs[row] = new BluePeg(point, "-vertical-");
                        break;
                    case "blue_peg_horizontal":
                        pegs[row] = new BluePeg(point, "-horizontal-");
                        break;
                    case "grey_peg":
                        pegs[row] = new GreyPeg(point, "-");
                        break;
                    case "grey_peg_vertical":
                        pegs[row] = new GreyPeg(point, "-vertical-");
                        break;
                    case "grey_peg_horizontal":
                        pegs[row] = new GreyPeg(point, "-horizontal-");
                        break;
                }
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // RED PEGS INITIALISATION: 1/5 of blue pegs turn red
    private void setRandomRedPegs(){
        Random rand = new Random();
        int redPegs = pegs.length / 5;
        int randomInt;
        for (int i = 0; i < redPegs; i++){
            randomInt = rand.nextInt(pegs.length);
            //If random peg is not blue, try a new random number
            while (!(pegs[randomInt] instanceof BluePeg)){
                randomInt = rand.nextInt(pegs.length);
            }
            //Change blue peg to red peg
            Point point = pegs[randomInt].getPoint();
            String shape = pegs[randomInt].getShape();
            pegs[randomInt] = new RedPeg(point, shape);
        }
    }

    // GREEN PEG INITIALISATION: 1 random blue peg to green peg
    private void setRandomGreenPeg(){
        //Only when game starts the value should be -1
        if (!(greenPegNumber == -1) && pegs[greenPegNumber]!= null){
            //Make previous green peg back to blue
            Point prevPoint = pegs[greenPegNumber].getPoint();
            String prevShape = pegs[greenPegNumber].getShape();
            pegs[greenPegNumber] = new BluePeg(prevPoint, prevShape);
        }

        //Get new random peg to become green
        Random rand = new Random();
        int randomInt = rand.nextInt(pegs.length);
        while (!(pegs[randomInt] instanceof BluePeg)){
            randomInt = rand.nextInt(pegs.length);
        }
        Point point = pegs[randomInt].getPoint();
        String shape = pegs[randomInt].getShape();
        pegs[randomInt] = new GreenPeg(point, shape);
        greenPegNumber = randomInt;
    }

    // POWERUP INITIALISATION: 1/10 chance for powerup to appear every turn
    private void setPowerup(){
        Random rand = new Random();
        int chance = 1;
        int randomInt = rand.nextInt(POWERUP_CHANCE);
        if (randomInt == chance){
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

        // Check collisions
        for (int i = 0; i < balls.size(); i++){
            // Unless green ball is hit, array list size should be 1
            Ball ball = balls.get(i);
            // Check all non-deleted pegs for intersection with the ball
            for (int j = 0; j < pegs.length; j++) {
                if (pegs[j] != null) {
                    if (ball.intersects(pegs[j])) {
                        pegs[j] = pegs[j].onCollision(balls, i, pegs);
                    }
                }
            }

            if (powerup != null && ball.intersects(powerup)){
                powerup = powerup.onCollision(balls, i);
            }
            if (ball.intersects(bucket)){
                shotsLeft = bucket.onCollision(shotsLeft);
            }
        }


        // Draw all active pegs
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
        if (checkAllRedPegs()){
            // Remove all pegs and load next board
            nextBoard();
            return;
        }

        checkShotsLeft();


        // If we don't have a ball and the mouse button was clicked, create one
        if (input.wasPressed(MouseButtons.LEFT) && balls.size() == 0) {
            balls.add(new Ball(BALL_POSITION, input.directionToMouse(BALL_POSITION), ""));
        }

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
                shotsLeft--;
                setRandomGreenPeg();
                setPowerup();
            }
        }

        //Debugging function for testing new boards
        if (input.wasPressed(Keys.SPACE)){
            nextBoard();
        }

    }


    public static void main(String[] args) {
        new ShadowBounce().run();
    }
}
