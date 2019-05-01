package za.co.goodspeed.craig.soduku;

import lombok.SneakyThrows;

import java.util.concurrent.Callable;

public class Puzzle {
    interface loopHelper{
        void method(int vertical,int horizontal);
    }
    interface  loopHelperReturn<T1>{
        T1 method(int vertical, int horizontal);
    }
    Item[][] thePuzzle = new Item[9][9];

    public Puzzle(){
        loopThroughPuzzle(
                (int vertical, int horizontal) ->{
                    thePuzzle[vertical][horizontal] = new Item(vertical, horizontal, thePuzzle);
                }
        );
    }

    private void setItem(int vertical, int horizontal, Integer value ){
        thePuzzle[vertical][horizontal].setNumber(value);
        thePuzzle[vertical][horizontal].setEditable(false);
    }

    private void refreshReferences(){
        loopThroughPuzzle((int vertical, int horizontal) -> {
                thePuzzle[vertical][horizontal].setHorizontalReference();
                thePuzzle[vertical][horizontal].setVerticalReference();
                thePuzzle[vertical][horizontal].setSquareReference();
        });
    }

    public String outputPuzzle(){
        StringBuilder data = new StringBuilder();
        loopThroughPuzzle(
                (int vertical, int horizontal) -> {
                    data.append(thePuzzle[horizontal][vertical].getNumber() == null ? "" : thePuzzle[horizontal][vertical].getNumber());
                    data.append("\t");
                    if(horizontal > 0 && horizontal % 8 == 0)
                        data.append("\n");
                }
        );
        return data.toString();
    }

    @SneakyThrows
    public void loopThroughPuzzle(loopHelper toCall){
        for(int vertical = 0; vertical < thePuzzle.length; vertical++) {
            for (int horizontal = 0; horizontal < thePuzzle[vertical].length; horizontal++) {
                toCall.method(vertical,horizontal);
            }
        }
    }

    @SneakyThrows
    public Object loopThroughPuzzle(loopHelperReturn toCall, Object defaultValue){
        Object returned = null;
        for(int vertical = 0; vertical < thePuzzle.length; vertical++) {
            for (int horizontal = 0; horizontal < thePuzzle[vertical].length; horizontal++) {
                returned = toCall.method(vertical,horizontal);
                if(returned.equals(defaultValue))
                    continue;
                else return returned;
            }
        }
        return defaultValue;
    }

    public static Puzzle generateEasyPuzzle(){
        Puzzle toReturn = new Puzzle();

        toReturn.setItem(1,0,9);
        toReturn.setItem(2,0,8);
        toReturn.setItem(3,0,7);
        toReturn.setItem(6,0,2);

        toReturn.setItem(0,1,2);
        toReturn.setItem(3,1,4);
        toReturn.setItem(6,1,5);

        toReturn.setItem(4,2,2);
        toReturn.setItem(6,2,7);

        toReturn.setItem(1,3,7);
        toReturn.setItem(2,3,5);
        toReturn.setItem(5,3,8);
        toReturn.setItem(7,3,1);
        toReturn.setItem(8,3,2);

        toReturn.setItem(0,4,8);
        toReturn.setItem(2,4,2);
        toReturn.setItem(3,4,1);
        toReturn.setItem(4,4,9);
        toReturn.setItem(5,4,7);
        toReturn.setItem(6,4,3);
        toReturn.setItem(8,4,4);

        toReturn.setItem(0,5,1);
        toReturn.setItem(1,5,4);
        toReturn.setItem(3,5,2);
        toReturn.setItem(6,5,6);
        toReturn.setItem(7,5,8);

        toReturn.setItem(2,6,4);
        toReturn.setItem(4,6,3);

        toReturn.setItem(2,7,3);
        toReturn.setItem(5,7,6);
        toReturn.setItem(8,7,5);

        toReturn.setItem(2,8,6);
        toReturn.setItem(5,8,4);
        toReturn.setItem(6,8,1);
        toReturn.setItem(7,8,9);



        return toReturn;

    }

    public static Puzzle generateToughPuzzle(){
        Puzzle toReturn = new Puzzle();

        toReturn.setItem(0,0,1);
        toReturn.setItem(3,0,4);

        toReturn.setItem(1,1,4);
        toReturn.setItem(2,1,3);
        toReturn.setItem(6,1,2);

        toReturn.setItem(3,2,3);
        toReturn.setItem(4,2,8);
        toReturn.setItem(7,2,7);
        toReturn.setItem(8,2,1);

        toReturn.setItem(3,3,9);
        toReturn.setItem(4,3,7);
        toReturn.setItem(7,3,2);

        toReturn.setItem(0,4,7);
        toReturn.setItem(2,4,1);
        toReturn.setItem(6,4,9);
        toReturn.setItem(8,4,5);

        toReturn.setItem(1,5,5);
        toReturn.setItem(4,5,6);
        toReturn.setItem(5,5,1);

        toReturn.setItem(0,6,4);
        toReturn.setItem(1,6,8);
        toReturn.setItem(4,6,9);
        toReturn.setItem(5,6,6);

        toReturn.setItem(2,7,6);
        toReturn.setItem(6,7,5);
        toReturn.setItem(7,7,4);

        toReturn.setItem(5,8,3);
        toReturn.setItem(8,8,2);

        return toReturn;

    }

    public Integer[][] solvePuzzle(){
        Integer[][] toReturn = new Integer[9][9];
        while(hasEmpties(toReturn)) {
            loopThroughPuzzle((int vertical, int horizontal) -> {
                        if (thePuzzle[vertical][horizontal].isEditable()) {
                            Integer[] possibles = thePuzzle[vertical][horizontal].getMissingNumbers();
                            if (possibles.length == 1) {
                                thePuzzle[vertical][horizontal].setNumber(possibles[0]);
                                thePuzzle[vertical][horizontal].setEditable(false);
                                toReturn[vertical][horizontal] = possibles[0];
                                refreshReferences();
                            }
                            thePuzzle[vertical][horizontal].checkStraightLines();
                            thePuzzle[vertical][horizontal].checkSquare();

                        } else {
                            toReturn[vertical][horizontal] = thePuzzle[vertical][horizontal].getNumber();
                        }

                    }
            );
            System.out.println(outputPuzzle());

        }
        return toReturn;
    }

    private boolean hasEmpties(Integer[][] toCheck){
        return (boolean)loopThroughPuzzle((int vertical, int horizontal) -> {
            return Boolean.valueOf(toCheck[vertical][horizontal] == null);
            },Boolean.valueOf(false)
        );
    }

    public Integer[] getMissingNumbers(int vertical, int horizontal){
        return thePuzzle[1][4].getMissingNumbers();
    }
}
