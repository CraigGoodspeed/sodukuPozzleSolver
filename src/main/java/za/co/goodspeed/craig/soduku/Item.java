package za.co.goodspeed.craig.soduku;

import javafx.util.Pair;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@Data
public class Item {
    @Data
    public class IndexHelper{
        Integer line1,line2;
    }
    boolean editable;
    Integer number;
    Integer verticalCoordinate,horizontalCoordinate;
    Integer[] verticalReference,horizontalReference;
    Integer[][] squareReference;
    Item[][] puzzle;

    public Item(int verticalCoordinate, int horizontalCoordinate, Item[][] puzzle){
        this.setVerticalCoordinate(verticalCoordinate);
        this.setHorizontalCoordinate(horizontalCoordinate);
        this.setPuzzle(puzzle);

        editable = true;
    }
    public Item(Integer number, short verticalCoordinate, short horizontalCoordinate,Item[][] puzzle){
        this(verticalCoordinate, horizontalCoordinate,puzzle);
        this.setNumber(number);
        editable = false;
    }

    public void setVerticalReference(){
        verticalReference = new Integer[9];
        for(int vCord = 0; vCord < verticalReference.length; vCord++){
            verticalReference[vCord] = puzzle[verticalCoordinate][vCord].getNumber();
        }
    }
    public Integer[] getVerticalReference(){
        if(verticalReference == null)
            setVerticalReference();
        return verticalReference;

    }
    public void refreshReferences(){
        setVerticalReference();
        setHorizontalReference();
        setSquareReference();
    }
    public void setHorizontalReference(){
        horizontalReference = new Integer[9];
        for(int hCord = 0; hCord < horizontalReference.length; hCord++){
            horizontalReference[hCord] = puzzle[hCord][horizontalCoordinate].getNumber();
        }
    }
    public Integer[] getHorizontalReference(){
        if(horizontalReference == null)
            setHorizontalReference();
        return horizontalReference;
    }

    public Integer[][] getSquareReference(){
        if(squareReference == null)
            setSquareReference();
        return squareReference;
    }

    public void setSquareReference(){
        int horizontalStartIndex = getCordinate(horizontalCoordinate) * 3;
        int verticalStartIndex = getCordinate(verticalCoordinate) * 3;
        squareReference = new Integer[3][3];
        for(int y = 0; y < 3; y++ ) {//vertical loop
            for(int x = 0; x < 3; x++){//horizontal loop
                squareReference[y][x] = puzzle[verticalStartIndex + y][horizontalStartIndex+x].getNumber();
            }
        }
    }
    public void setNumber(Integer number){
        System.out.println(String.format("setting number at cordinates %s,%s with value %s",verticalCoordinate,horizontalCoordinate,number));
        this.number = number;
        this.setEditable(false);
        refreshReferences();
    }


    private static short getCordinate(Integer testMe){
        if(testMe <= 2){
            return 0;
        }
        else if(testMe <= 5)
            return 1;
        else return 2;
    }

    public Integer[] getMissingNumbers(){
        Integer[] vertical = getVerticalReference();
        Integer[] horizontal = getHorizontalReference();
        Integer[] square = convertSquareToSingleLine();
        List<Integer> toReturn = new ArrayList<>(constants.getALLNUMBERS());
        cleanWhenExists(toReturn,vertical);
        cleanWhenExists(toReturn,horizontal);
        cleanWhenExists(toReturn,square);
        return toReturn.toArray(new Integer[0]);
    }

    public void checkStraightLines(){
        //coordinates,
        //when 2,5,8 check lines x - 1 and x -2
        //when 1,4,7 check lines x - -1 and x + 1
        //when 0,3,6 check lines x + 1 and x + 2
        if(isEditable()) {
            doVerticalCheck();
            doHorizontalCheck();
            refreshReferences();
        }
    }

    public IndexHelper getStraightIndexes(Integer line){
        IndexHelper toReturn = new IndexHelper();

        switch (line){
            case 2 :
            case 5 :
            case 8  :{
                toReturn.setLine1(line - 1);
                toReturn.setLine2(line - 2);
                break;
            }
            case 1:
            case 4:
            case 7:{
                toReturn.setLine1(line - 1);
                toReturn.setLine2(line + 1);
                break;
            }
            case 0:
            case 3:
            case 6:{
                toReturn.setLine1(line + 1);
                toReturn.setLine2(line + 2);
                break;
            }

        }
        return toReturn;
    }

    public void doVerticalCheck(){
        IndexHelper puzzleIndexes = getStraightIndexes(verticalCoordinate);
        Integer[] line1 = getPuzzle()[puzzleIndexes.getLine1()][horizontalCoordinate].getVerticalReference();
        Integer[] line2 = getPuzzle()[puzzleIndexes.getLine2()][horizontalCoordinate].getVerticalReference();
        for(int cnt = 1; cnt < 10; cnt++){
            boolean contains = bothLinesContain(line1,line2,convertSquareToSingleLine(),cnt);
            if(contains){
                IndexHelper squareIndexes = getStraightIndexes(horizontalCoordinate);
                boolean bothSetAndNotSameSquare =
                        !getPuzzle()[verticalCoordinate][squareIndexes.getLine1()].isEditable()
                        &&
                        !getPuzzle()[verticalCoordinate][squareIndexes.getLine2()].isEditable();
                        /*&&
                        !(
                                Arrays.deepEquals(getSquareReference(),getPuzzle()[verticalCoordinate][squareIndexes.getLine1()].getSquareReference())
                                ||
                                Arrays.deepEquals(getSquareReference(),getPuzzle()[verticalCoordinate][squareIndexes.getLine2()].getSquareReference())
                        );*/

                if(bothSetAndNotSameSquare) {//no where else for this number, this value must appear here.
                    System.out.println("setting via vertical");
                    getPuzzle()[verticalCoordinate][horizontalCoordinate].setNumber(cnt);
                }
            }
        }

    }
    public void doHorizontalCheck(){
        IndexHelper puzzleIndexes = getStraightIndexes(horizontalCoordinate);
        Integer[] line1 = getPuzzle()[verticalCoordinate][puzzleIndexes.getLine1()].getHorizontalReference();
        Integer[] line2 = getPuzzle()[verticalCoordinate][puzzleIndexes.getLine2()].getHorizontalReference();
        for(int cnt = 1; cnt < 10; cnt++){
            boolean contains = bothLinesContain(line1,line2,convertSquareToSingleLine(),cnt);
            if(contains){
                IndexHelper squareIndexes = getStraightIndexes(verticalCoordinate);
                boolean bothSetAndNotSameSquare =
                        !getPuzzle()[squareIndexes.getLine1()][horizontalCoordinate].isEditable()
                        &&
                        !getPuzzle()[squareIndexes.getLine2()][horizontalCoordinate].isEditable();
/*                        //&&
                        //!(
                                Arrays.deepEquals(getSquareReference(),getPuzzle()[squareIndexes.getLine2()][horizontalCoordinate].getSquareReference())
                                ||
                                Arrays.deepEquals(getSquareReference(),getPuzzle()[squareIndexes.getLine1()][horizontalCoordinate].getSquareReference())
                        );*/

                if(bothSetAndNotSameSquare) {//no where else for this number, this value must appear here.
                    System.out.println("setting via horizontal");
                    getPuzzle()[verticalCoordinate][horizontalCoordinate].setNumber(cnt);
                }
            }
        }
    }

    public void checkSquare(){
        Item[] thisSquare = new Item[9];
        int horizontalStartIndex = getCordinate(horizontalCoordinate) * 3;
        int verticalStartIndex = getCordinate(verticalCoordinate) * 3;
        int index = 0;
        for(int y = 0; y < 3; y++ ) {//vertical loop
            for(int x = 0; x < 3; x++){//horizontal loop
                thisSquare[index] = puzzle[verticalStartIndex + y][horizontalStartIndex+x];
                index++;
            }
        }
        index = -1;
        for(int cnt = 1; cnt < 10; cnt++){
            Boolean[] canBe = new Boolean[9];
            for(int cbCount = 0; cbCount < canBe.length; cbCount++) {
                canBe[cbCount] = thisSquare[cbCount].isEditable()
                        &&
                        !(
                            checkLines(thisSquare[cbCount].getHorizontalReference(),
                                    thisSquare[cbCount].getVerticalReference(),
                                    cnt
                                    )
                        )
                        &&
                        (
                            wrapTheMethod(thisSquare[cbCount].convertSquareToSingleLine(),cnt)
                        )
                ;
                if(canBe[cbCount])
                    index = cbCount;

            }
            if (Arrays.stream(canBe).filter(i -> i.equals(true)).count() == 1) {
                System.out.println("setting via square check");
                thisSquare[index].setNumber(cnt);
            }
        }

    }
    private boolean wrapTheMethod(Integer[] data, Integer toFind){
        return !Arrays.stream(data).anyMatch(i -> i != null && i.equals(toFind));
    }
    private boolean checkLines(Integer[] horizontal, Integer[] vertical, Integer toFind){
        return
                (Arrays.stream(horizontal).anyMatch(i -> i != null&& i.equals(toFind))
                        ||
                        Arrays.stream(vertical).anyMatch(i -> i != null&& i.equals(toFind)));
    }
    private boolean bothLinesContain(Integer[] line1, Integer[] line2, Integer[] sqRef, Integer findMe){
        return
                Arrays.stream(line1).anyMatch(i -> i != null && i.equals(findMe))
                &&
                Arrays.stream(line2).anyMatch( i -> i != null && i.equals(findMe))
                &&
                !Arrays.stream(sqRef).anyMatch( i -> i != null && i.equals(findMe));
    }


    private List<Integer> cleanWhenExists(List<Integer> toClean, Integer[] items){
        for(Integer i : items){
            if(toClean.contains(i))
                toClean.remove(i);
        }
        return toClean;
    }

    public boolean validateMe(){
        return
                validateCollection(verticalReference)
                && validateCollection(horizontalReference)
                && validateSquare(squareReference);
    }
    private boolean validateCollection(Integer[] validateMe){
        return Arrays.asList(validateMe).containsAll(constants.getALLNUMBERS());
    }
    private boolean validateSquare(Integer[][] square){

        return validateCollection(convertSquareToSingleLine());
    }
    private Integer[] convertSquareToSingleLine(){
        Integer[] singleLine = new Integer[9];
        System.arraycopy(getSquareReference()[0],0,singleLine,0,3);
        System.arraycopy(getSquareReference()[1],0,singleLine,3,3);
        System.arraycopy(getSquareReference()[2],0,singleLine,6,3);
        return singleLine;
    }
}
