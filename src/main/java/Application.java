import za.co.goodspeed.craig.soduku.Puzzle;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        //Puzzle p = Puzzle.generateEasyPuzzle(); //--> this is solvable
        //Puzzle p = Puzzle.generateMediumPuzzle();//also solves
        Puzzle p = Puzzle.generateToughPuzzle();//SOLVED!
        //Puzzle p = Puzzle.generateExtremePuzzle();

        Integer[][] solution = p.solvePuzzle();

        for(int hor = 0; hor < 9; hor++){
            for(int ver = 0; ver < 9; ver++){
                System.out.print(String.join("   ",solution[ver][hor].toString()));
            }
            System.out.println();
        }
        System.in.read();
    }
}
