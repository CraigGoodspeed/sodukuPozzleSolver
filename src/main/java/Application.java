import za.co.goodspeed.craig.soduku.Puzzle;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        //Puzzle p = Puzzle.generateEasyPuzzle(); //--> this is solvable
        //p.solvePuzzle();
        //Puzzle pMed = Puzzle.generateMediumPuzzle();//also solves
        //pMed.solvePuzzle();
        //Puzzle pHard = Puzzle.generateToughPuzzle();//SOLVED!
        //pHard.solvePuzzle();
        Puzzle pExtreme = Puzzle.generateExtremePuzzleV2();
        pExtreme.solvePuzzle();

        /*for(int hor = 0; hor < 9; hor++){
            for(int ver = 0; ver < 9; ver++){
                System.out.print(String.join("   ",solution[ver][hor].toString()));
            }
            System.out.println();
        }*/
        System.in.read();
    }
}
