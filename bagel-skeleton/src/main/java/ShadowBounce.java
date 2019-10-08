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
import java.util.Arrays;
import java.util.Random;

public class ShadowBounce extends AbstractGame {
    private Peg[] pegs;
    private Ball ball;
    private static final Point BALL_POSITION = new Point(512, 32);
    private static final double PEG_OFFSET = 100;
    private int boardNumber = 0;
    private int shotsLeft = 20;
    private int greenPegNumber = -1;

    //Check number of shots left
    private void checkShotsLeft(){
        System.out.println(shotsLeft);
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
    }

    //Prepare next board unless all boards have been played
    private void nextBoard(){
        pegs = new Peg[165];
        if (boardNumber == 4){
            endGame();
        } else if (boardNumber < 5){
            loadBoard(boardNumber);
            setRandomPegs();
            boardNumber++;
        }
    }

    //Load board from file
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
                if (type.equals("blue_peg")){
                    pegs[row] = new BluePeg(point);
                } else {
                    pegs[row] = new GreyPeg(point);
                }
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Randomly choose red and green pegs on board at start of board
    private void setRandomPegs(){
        setRandomRedPegs();
        setRandomGreenPeg();
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
            pegs[randomInt] = new RedPeg(point);
        }
    }

    //GREEN PEG INITIALISATION: 1 random blue peg to green peg
    private void setRandomGreenPeg(){
        //Only when game starts the value should be -1
        if (!(greenPegNumber == -1) && pegs[greenPegNumber]!= null){
            //Make previous green peg back to blue
            Point prevPoint = pegs[greenPegNumber].getPoint();
            pegs[greenPegNumber] = new BluePeg(prevPoint);
        }

        //Get new random peg to become green
        Random rand = new Random();
        int randomInt = rand.nextInt(pegs.length);
        while (!(pegs[randomInt] instanceof BluePeg)){
            randomInt = rand.nextInt(pegs.length);
        }
        Point point = pegs[randomInt].getPoint();
        pegs[randomInt] = new GreenPeg(point);
        greenPegNumber = randomInt;
    }

    //Initialise board before starting game
    private ShadowBounce() {
        nextBoard();
    }


    @Override
    protected void update(Input input) {
        // Check all non-deleted pegs for intersection with the ball
        for (int i = 0; i < pegs.length; i++) {
            if (pegs[i] != null) {
                if (ball != null && ball.intersects(pegs[i])) {
                    pegs[i] = pegs[i].onCollision(ball);
                } else {
                    pegs[i].update();
                }
            }
        }

        //Check for any red pegs left
        if (checkAllRedPegs()){
            ball = null;
            nextBoard();
            return;
        }

        checkShotsLeft();

//        //Debugging function for testing new boards
//        if (input.wasPressed(Keys.SPACE)){
//            nextBoard();
//        }

        // If we don't have a ball and the mouse button was clicked, create one
        if (input.wasPressed(MouseButtons.LEFT) && ball == null) {
            ball = new Ball(BALL_POSITION, input.directionToMouse(BALL_POSITION));
        }

        if (ball != null) {
            ball.update();
            System.out.println(ball.getVelocity().toString());

            // End of turn when ball leaves screen and delete ball
            if (ball.outOfScreen()) {
                ball = null;
                shotsLeft--;
                setRandomGreenPeg();

            }
        }


    }

    public static void main(String[] args) {
        new ShadowBounce().run();
    }
}
