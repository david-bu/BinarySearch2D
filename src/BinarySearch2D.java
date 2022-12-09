import java.util.Arrays;
import java.util.Random;

public class BinarySearch2D {

    public static void main(String[] args) {
        int[][] haystack = generateArray(5, 5);
        System.out.println(Arrays.deepToString(haystack));
        System.out.println(Arrays.toString(binarySearch2D(haystack, 25)));

        int[][] haystackRandom = generateRandomArray(5, 5, 7, 70);
        System.out.println(Arrays.deepToString(haystackRandom));
        System.out.println(Arrays.toString(binarySearch2D(haystackRandom, 67)));
    }

    /**
     * Generates a 2D array with the numbers 1 to k*m, fills the rows first
     *  {
     *      { 1, 2, .., n },
     *      { n+1, n+2, ... },
     *      ...
     *  }
     * @param k the row count of the generated array
     * @param m the column count of the generated array
     * @return the generated array
     */
    public static int[][] generateArray(int k, int m) {
        if (k <= 0 || m <= 0) {
             System.err.println("Size of the 2D array has to be positive");
             System.exit(-1);
        }
        int[][] array = new int[k][m];

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < m; j++) {
                array[i][j] = i * m + j + 1;
            }
        }

        return array;
    }

    /**
     * Generates a 2D array with the numbers from low to high (exclusive) ordered, fills the rows first
     * the fist value of the next row is greater than the value in the previous row
     * @param k the row count of the generated array
     * @param m the column count of the generated array
     * @param low the smallest possible value in this array
     * @param high the greatest value in this array (exclusive)
     * @return the generated array
     */
    public static int[][] generateRandomArray(int k, int m, int low, int high) {
       if (k <= 0 || m <= 0) {
            System.err.println("Size of the random 2D array has to be positive");
            System.exit(-1);
        }
        if (low + k >= high) {
            System.err.println("Range for random array too small. each start value of an array can't be bigger " +
                    "than the previous");
            System.exit(-1);
        }

        int minValue = low;
        int maxValue = high - k;
        Random random = new Random();
        int[][] array = new int[k][m];

        for (int i = 0; i < k; i++) {
            minValue++;
            maxValue = high - (k - i);
            for (int j = 0; j < m; j++) {
                minValue = random.nextInt(maxValue - minValue) + minValue;
                array[i][j] = minValue;
            }
        }

        return array;
    }

    /**
     * searches for the index of the value needle in the 2D array haystack
     * @param haystack a 2D array containing ordered values; each starting value in a row
     *                 has to be greater than the fist value in the previous row
     * @param needle the value to search for
     * @return the index of value; { -1, -1 } if not found
     */
    public static int[] binarySearch2D(int[][] haystack, int needle) {
        if (haystack == null || haystack.length < 1) {
            System.err.println("haystack has a size of 0 or is null");
            System.exit(-1);
        }

        int row = findRow(haystack, 0, haystack.length - 1, needle);
        if (row == -1)
            return new int[]{ -1, -1 };

        int column = binarySearch(haystack[row], 0, haystack[row].length - 1, needle);
        if (column == -1)
            return new int[]{ -1, -1};
        
        return new int[]{ row, column };
    }

    /**
     * searches for the row possible containing the value x
     * @param a the 2D array where the right row should be searched for
     * @param l the left bound to look for the right row in a
     * @param r the right bound to look for the right row in a
     * @param x the value which should be found
     * @return the possible row index if it exists else -1
     */
    private static int findRow(int[][] a, int l, int r, int x) {
        if (l > r)
            return -1;

        int m = (l + r)/2;
        if (x >= a[m][0]) {
            if (m + 1 == a.length || x < a[m + 1][0])
                return m;
            return findRow(a, m + 1, r, x);
        }

        return findRow(a, l, m - 1, x);
    }

    /**
     * searches a value in an ordered int array
     * @param a the array where the value is searched
     * @param l the left bound to look for the value in a
     * @param r the right bound to look for the value in a
     * @param x the value to search for
     * @return the index if x is found; else -1
     */
    private static int binarySearch(int[] a, int l, int r, int x) {
        if (l > r)
            return -1;

        int m = (l + r)/2;
        if (x == a[m])
            return m;
        else if (x < a[m])
            return binarySearch(a, l, m - 1, x);
        return binarySearch(a, m + 1, r, x);
    }

}