package za.co.goodspeed.craig.soduku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class constants {
    //public static final Integer[] ALLNUMBERS = new Integer[]{1,2,3,4,5,6,7,8,9};
    private static final Integer[] _allNumbers = new Integer[]{1,2,3,4,5,6,7,8,9};
    private static List<Integer>    ALLNUMBERS;
    public static List<Integer>  getALLNUMBERS() {
        ALLNUMBERS = new ArrayList<Integer>();
        ALLNUMBERS.addAll(Arrays.asList(_allNumbers));
        return ALLNUMBERS;
    }
}
