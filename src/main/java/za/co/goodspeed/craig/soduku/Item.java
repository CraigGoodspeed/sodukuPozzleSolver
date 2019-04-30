package za.co.goodspeed.craig.soduku;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Item {
    boolean editable;
    Integer number;
    Short verticalCoordinate,horizontalCoordinate;
    Integer[] verticalReference,horizontalReference;
    Integer[][] squareReference;
    Item[][] puzzle;

    public Item(short verticalCoordinate, short horizontalCoordinate, Item[][] puzzle){
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
        this.number = number;
        this.setEditable(false);
        setHorizontalReference();
        setVerticalReference();
        setSquareReference();
    }


    private static short getCordinate(short testMe){
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
