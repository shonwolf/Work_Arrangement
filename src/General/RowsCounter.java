package General;

public class RowsCounter {
    
    /*
     * This function counts the number of rows in the string she gets by the number of '\n' it has + 1 for last row.
     */
    public static int countRows(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '\n') {
                count++;
            }
        }
        return count + 1;
    }
}
