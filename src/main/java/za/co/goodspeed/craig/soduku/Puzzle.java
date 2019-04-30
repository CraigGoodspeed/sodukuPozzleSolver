package za.co.goodspeed.craig.soduku;

public class Puzzle {
    Item[][] thePuzzle = new Item[9][9];

    public Puzzle(){
        for(short vertical = 0; vertical < thePuzzle.length; vertical++){
            for(short horizontal = 0; horizontal < thePuzzle[vertical].length; horizontal++){
                thePuzzle[vertical][horizontal] = new Item(vertical, horizontal, thePuzzle);
            }
        }
    }

    private void setItem(int vertical, int horizontal, Integer value ){
        thePuzzle[vertical][horizontal].setNumber(value);
        thePuzzle[vertical][horizontal].setEditable(false);
    }

    private void refreshReferences(){
        for(short vertical = 0; vertical < thePuzzle.length; vertical++){
            for(short horizontal = 0; horizontal < thePuzzle[vertical].length; horizontal++){
                thePuzzle[vertical][horizontal].setHorizontalReference();
                thePuzzle[vertical][horizontal].setVerticalReference();
                thePuzzle[vertical][horizontal].setSquareReference();
            }
        }
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
            for (int vert = 0; vert < 9; vert++) {
                for (int hor = 0; hor < 9; hor++) {
                    if (thePuzzle[vert][hor].isEditable()) {
                        Integer[] possibles = thePuzzle[vert][hor].getMissingNumbers();
                        if (possibles.length == 1) {
                            thePuzzle[vert][hor].setNumber(possibles[0]);
                            thePuzzle[vert][hor].setEditable(false);
                            toReturn[vert][hor] = possibles[0];
                            refreshReferences();
                        }
                    } else {
                        toReturn[vert][hor] = thePuzzle[vert][hor].getNumber();
                    }
                }
            }
        }
        return toReturn;
    }

    private boolean hasEmpties(Integer[][] toCheck){

        for(int ver = 0; ver<9;ver++){
            for(int hor=0; hor < 9; hor++){
                if(toCheck[ver][hor] == null)
                    return true;
            }
        }
        return false;
    }

    public Integer[] getMissingNumbers(int vertical, int horizontal){
        return thePuzzle[1][4].getMissingNumbers();
    }
}
