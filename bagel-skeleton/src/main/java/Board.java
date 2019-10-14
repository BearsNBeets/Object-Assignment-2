import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Random;

public class Board {
    private Peg[] pegs;
    private int boardNumber;
    private int greenPegNumber;
    private int boardSize;

    public Board(int boardNumber){
        this.boardNumber = boardNumber;
        boardSize = boardSize();
        pegs = new Peg[boardSize];
        loadBoard();
        setRandomRedPegs();
        greenPegNumber = -1;
        setRandomGreenPeg();
    }

    // Calculate length of csv file to determine number of pegs
    private int boardSize(){
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
    private void loadBoard(){
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
    public void setRandomGreenPeg(){
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

    //Check whether all red pegs have been destroyed
    public boolean checkAllRedPegs(){
        for (Peg peg : pegs) {
            if (peg instanceof RedPeg) {
                return false;
            }
        }
        return true;
    }

    public Peg[] getPegs() {
        return pegs;
    }

    public void clearPegs(){
        Arrays.fill(pegs, null);
    }
}
