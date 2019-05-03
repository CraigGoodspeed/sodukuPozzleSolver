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

    public Integer[] getVerticalReference(){
        Integer[] toReturn = new Integer[9];
        for(int vCord = 0; vCord < toReturn.length; vCord++){
            toReturn[vCord] = puzzle[verticalCoordinate][vCord].getNumber();
        }
        return toReturn;
    }


    public Integer[] getHorizontalReference(){
        Integer[] toReturn = new Integer[9];
        for(int hCord = 0; hCord < toReturn.length; hCord++){
            toReturn[hCord] = puzzle[hCord][horizontalCoordinate].getNumber();
        }
        return toReturn;
    }

    public Integer[][] getSquareReference(){
        int horizontalStartIndex = getCordinate(horizontalCoordinate) * 3;
        int verticalStartIndex = getCordinate(verticalCoordinate) * 3;
        Integer[][] toReturn = new Integer[3][3];
        for(int y = 0; y < 3; y++ ) {//vertical loop
            for(int x = 0; x < 3; x++){//horizontal loop
                toReturn[y][x] = puzzle[verticalStartIndex + y][horizontalStartIndex+x].getNumber();
            }
        }
        return toReturn;
    }

    public void setNumber(Integer number){
        System.out.println(String.format("setting number at cordinates %s,%s with value %s",verticalCoordinate,horizontalCoordinate,number));
        this.number = number;
        this.setEditable(false);
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
        Integer[] myVerticalLine = getVerticalReference();
        Integer[] myHorizontalLine = getHorizontalReference();
        for(int cnt = 1; cnt < 10; cnt++){
            boolean contains = bothLinesContain(line1,line2,myVerticalLine,myHorizontalLine,convertSquareToSingleLine(),cnt);
            if(contains){
                IndexHelper squareIndexes = getStraightIndexes(horizontalCoordinate);
                Integer v1 = getPuzzle()[verticalCoordinate][squareIndexes.getLine1()].getNumber();
                Integer v2 = getPuzzle()[verticalCoordinate][squareIndexes.getLine2()].getNumber();
                boolean bothSetAndNotSameSquare =
                        !getPuzzle()[verticalCoordinate][squareIndexes.getLine1()].isEditable()
                        &&
                        !getPuzzle()[verticalCoordinate][squareIndexes.getLine2()].isEditable()
                        &&
                        !(v1 != null && v1.equals(cnt))
                        &&
                        !(v2 != null && v2.equals(cnt));

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
        Integer[] myHorizontalLine = getHorizontalReference();
        Integer[] myVerticalLine = getVerticalReference();
        for(int cnt = 1; cnt < 10; cnt++){
            boolean contains = bothLinesContain(line1,line2,myVerticalLine, myHorizontalLine,convertSquareToSingleLine(),cnt);
            if(contains){
                IndexHelper squareIndexes = getStraightIndexes(verticalCoordinate);
                Integer v1 = getPuzzle()[squareIndexes.getLine1()][horizontalCoordinate].getNumber();
                Integer v2 = getPuzzle()[squareIndexes.getLine2()][horizontalCoordinate].getNumber();

                boolean bothSetAndNotSameSquare =
                        !getPuzzle()[squareIndexes.getLine1()][horizontalCoordinate].isEditable()
                        &&
                        !getPuzzle()[squareIndexes.getLine2()][horizontalCoordinate].isEditable()
                        &&
                        !(v1 != null && v1.equals(cnt))
                        &&
                        !(v2 != null && v2.equals(cnt));
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
                        !(arrayContains(thisSquare[cbCount].getHorizontalReference(), cnt))//should not contain horizontal
                        &&
                        !(arrayContains(thisSquare[cbCount].getVerticalReference(), cnt))//should not contain vertical
                        &&
                        !(arrayContains(thisSquare[cbCount].convertSquareToSingleLine(),cnt))//should not be in the square
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

    public void doImpliedChecks(){
        if(!isEditable())
            return;
        Integer[] horizontalCheck = getHorizontalReference();
        Integer nullCount = (new Long(Arrays.stream(horizontalCheck).filter(i -> i == null).count())).intValue();
        if(nullCount <= 3 && nullCount > 0 && sameSquare(horizontalCheck,nullCount)){
            //1) get implied values
            //we need to imply these 2 or 3 cells are values as an example 6,4,9
            //with the new values then check the square for complete items.
            Integer[] impliedValues = cleanWhenExists(constants.getALLNUMBERS(), horizontalCheck).toArray(new Integer[0]);
            //TODO: implement this method, set the implied values then checkSquare if we get a hit, great otherwise unset the values.
            Integer[] nullIndexes = new Integer[nullCount];
            int nullCounter = 0;
            for(int i = 0; i < 9; i++){
                if(horizontalCheck[i] == null){
                    nullIndexes[nullCounter] = i;
                    nullCounter++;
                }
            }
            int impliedCounter =0;
            for(int i = 0; i < nullIndexes.length;i++){
                getPuzzle()[verticalCoordinate][nullIndexes[i]].setNumber(impliedValues[i]);
            }
            checkSquare();
            for(int i = 0; i < nullIndexes.length;i++){
                getPuzzle()[verticalCoordinate][nullIndexes[i]].setNumber(null);
                getPuzzle()[verticalCoordinate][nullIndexes[i]].setEditable(true);
            }

        }
    }

    public boolean sameSquare(Integer[] toCheck,Integer nullCount){
        Integer[] items = new Integer[nullCount];
        int itemCounter = 0;
        for(int i = 0; i < toCheck.length && itemCounter < items.length;i++){
            if(toCheck[i] == null) {
                items[itemCounter] = i;
                itemCounter++;
            }
        }
        Arrays.sort(items);
        if(items.length == 1)
            return false;//let other methods handle this.
        //square bounds : 0:2, 3:5, 6:8
        return
                (items[0] >= 0 && items[items.length - 1] <= 2)
                ||
                (items[0] >= 3 && items[items.length - 1] <= 5)
                ||
                (items[0] >= 6 && items[items.length - 1] <= 8);
    }

    public void checkVertical(){
        Item[] vertical = getPuzzle()[verticalCoordinate];
        checkLine(vertical);
    }

    public void checkHorizontal(){
        Item[] horizontal = new Item[9];
        for(int i = 0; i < horizontal.length;i++){
            horizontal[i] = getPuzzle()[i][horizontalCoordinate];
        }
        checkLine(horizontal);
    }

    private void checkLine(Item[] toCheck){

        Integer index = 0;
        Integer canBeValue;
        for(int x = 0; x < 9;x++) {
            Boolean[] canBe = new Boolean[9];
            canBeValue = x + 1;
            for (int i = 0; i < toCheck.length; i++) {
                canBe[i] =
                        toCheck[i].isEditable()
                        &&
                        !arrayContains(toCheck[i].getHorizontalReference(), canBeValue)
                        &&
                        !arrayContains(toCheck[i].getVerticalReference(), canBeValue)
                        &&
                        !arrayContains(toCheck[i].convertSquareToSingleLine(), canBeValue)
                        &&
                        !arrayContains(getVerticalReference(), canBeValue)
                        &&
                        !arrayContains(getHorizontalReference(), canBeValue);

                if (canBe[i])
                    index = i;
            }
            if(Arrays.stream(canBe).filter(i -> i.equals(true)).count() == 1){
                System.out.println("setting via the line check");
                toCheck[index].setNumber(canBeValue);
            }
        }

    }

    private boolean arrayContains(Integer[] items, Integer findMe){
        return Arrays.stream(items).anyMatch(i -> i!= null && i.equals(findMe));

    }

    private boolean bothLinesContain(Integer[] line1, Integer[] line2, Integer[] myVertcalLine, Integer[] myHorizontalLine, Integer[] sqRef, Integer findMe){
        return
                arrayContains(line1, findMe)
                &&
                arrayContains(line2,findMe)
                &&
                !arrayContains(sqRef,findMe)
                &&
                !arrayContains(myVertcalLine,findMe)
                &&
                !arrayContains(myHorizontalLine,findMe)
                ;
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
                validateCollection(getVerticalReference())
                && validateCollection(getHorizontalReference())
                && validateSquare(getSquareReference());
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
