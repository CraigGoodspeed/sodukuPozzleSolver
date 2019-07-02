package za.co.goodspeed.craig.soduku;

import lombok.SneakyThrows;


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
    public void loopThroughPuzzle(loopHelper toCall
    ){

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

    public static Puzzle generateMediumPuzzle() {
        Puzzle toReturn = new Puzzle();


        toReturn.setItem(0, 1, 9);
        toReturn.setItem(4, 1, 4);
        toReturn.setItem(7, 1, 7);
        toReturn.setItem(8, 1, 2);

        toReturn.setItem(2, 2, 4);
        toReturn.setItem(4, 2, 6);
        toReturn.setItem(5, 2, 8);
        toReturn.setItem(8, 2, 3);

        toReturn.setItem(0, 3, 7);
        toReturn.setItem(2, 3, 1);
        toReturn.setItem(4, 3, 8);
        toReturn.setItem(5, 3, 3);
        toReturn.setItem(6, 3, 5);
        toReturn.setItem(7, 3, 2);

        toReturn.setItem(1, 4, 2);
        toReturn.setItem(7, 4, 3);

        toReturn.setItem(1, 5, 5);
        toReturn.setItem(2, 5, 6);
        toReturn.setItem(3, 5, 1);
        toReturn.setItem(4, 5, 2);
        toReturn.setItem(6, 5, 9);
        toReturn.setItem(8, 5, 8);

        toReturn.setItem(0, 6, 5);
        toReturn.setItem(3, 6, 3);
        toReturn.setItem(4, 6, 1);
        toReturn.setItem(6, 6, 2);

        toReturn.setItem(0, 7, 1);
        toReturn.setItem(1, 7, 3);
        toReturn.setItem(4, 7, 7);
        toReturn.setItem(8, 7, 4);


        return toReturn;

    }

    public static Puzzle generateExtremePuzzle() {
        Puzzle toReturn = new Puzzle();

        toReturn.setItem(1, 0, 1);
        toReturn.setItem(2, 0, 2);
        toReturn.setItem(3, 0, 3);
        toReturn.setItem(4, 0, 6);

        toReturn.setItem(0, 1, 5);
        toReturn.setItem(1, 1, 7);
        toReturn.setItem(8, 1, 9);

        toReturn.setItem(3, 2, 8);

        toReturn.setItem(6, 3, 4);

        toReturn.setItem(1, 4, 6);
        toReturn.setItem(3, 4, 2);

        toReturn.setItem(4, 5, 9);
        toReturn.setItem(5, 5, 1);

        toReturn.setItem(0, 6, 8);
        toReturn.setItem(3, 6, 9);
        toReturn.setItem(5, 6, 4);
        toReturn.setItem(6, 6, 5);
        toReturn.setItem(8, 6, 7);


        toReturn.setItem(8, 7, 1);

        toReturn.setItem(1, 8, 9);
        toReturn.setItem(2, 8, 4);
        toReturn.setItem(7, 8, 6);
        toReturn.setItem(8, 8, 3);

        return toReturn;

    }

    public static Puzzle generateExtremePuzzleV2() {
        Puzzle toReturn = new Puzzle();

        toReturn.setItem(2, 0, 8);
        toReturn.setItem(5, 0, 1);
        toReturn.setItem(8, 0, 2);

        toReturn.setItem(1, 1, 2);
        toReturn.setItem(4, 1, 9);
        toReturn.setItem(7, 1, 7);

        toReturn.setItem(0, 2, 3);
        toReturn.setItem(3, 2, 5);
        toReturn.setItem(6, 2, 1);

        toReturn.setItem(0, 3, 8);
        toReturn.setItem(3, 3, 4);
        toReturn.setItem(6, 3, 2);

        toReturn.setItem(1, 4, 1);
        toReturn.setItem(4, 4, 8);
        toReturn.setItem(7, 4, 3);

        toReturn.setItem(2, 5, 3);
        toReturn.setItem(5, 5, 2);
        toReturn.setItem(8, 5, 9);

        toReturn.setItem(2, 6, 9);
        toReturn.setItem(5, 6, 5);
        toReturn.setItem(8, 6, 6);

        toReturn.setItem(1, 7, 6);
        toReturn.setItem(4, 7, 7);
        toReturn.setItem(7, 7, 8);

        toReturn.setItem(0, 8, 4);
        toReturn.setItem(3, 8, 6);
        toReturn.setItem(6, 8, 5);

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

        //toReturn.setItem(8,7,9);

        return toReturn;

    }

    public void solvePuzzle(){
        int loopCount = 0;
        System.out.println(outputPuzzle());
        while(!allSet()) {
            //TODO : cleanup loopCount should call complex checks (implied checks) when nothing gets set from the simple checks method.
            //TODO : cleanup loopCount should call complex checks (implied checks) when nothing gets set from the simple checks method.
            loopThroughPuzzle((loopCount <= 5) ? (int vertical, int horizontal) -> {

                        if (thePuzzle[vertical][horizontal].isEditable()) {
                            thePuzzle[vertical][horizontal].simpleEliminate();
                            thePuzzle[vertical][horizontal].checkSquare();
                            thePuzzle[vertical][horizontal].checkStraightLines();
                            thePuzzle[vertical][horizontal].checkHorizontal();
                            thePuzzle[vertical][horizontal].checkVertical();
                            thePuzzle[vertical][horizontal].doImpliedHorizontalChecks();
                            thePuzzle[vertical][horizontal].doImpliedVerticalChecks();
                        }

                    } :
                    (int vertical, int horizontal) -> {
                        if (thePuzzle[vertical][horizontal].isEditable()) {
                            thePuzzle[vertical][horizontal].simpleEliminate();
                            thePuzzle[vertical][horizontal].checkSquare();
                            thePuzzle[vertical][horizontal].checkStraightLines();
                            thePuzzle[vertical][horizontal].checkHorizontal();
                            thePuzzle[vertical][horizontal].checkVertical();
                            thePuzzle[vertical][horizontal].doImpliedHorizontalDifferentSquare();
                            thePuzzle[vertical][horizontal].doImpliedVerticalDifferentSquare();
                        }
                    }
            );
            loopCount++;
            System.out.println(outputPuzzle());
        }
    }

    private boolean allSet(){
        boolean toReturn = true;
        for(int vertical = 0; vertical < 9 && toReturn; vertical++ ){
            for (int horizontal = 0;horizontal<9 && toReturn;horizontal++){
                toReturn = !thePuzzle[vertical][horizontal].isEditable() && toReturn;
            }
        }
        return toReturn;
    }
}
