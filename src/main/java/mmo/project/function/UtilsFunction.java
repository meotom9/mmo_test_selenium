package mmo.project.function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class UtilsFunction {
    private static final Logger logger = LogManager.getLogger(UtilsFunction.class);

    public static final Random random;

    static {
        random = new Random();
    }

    public static int randomRange(int min, int max){
        // Create a Random object
        Random random = new Random();

        // Generate a random integer within the range
        int a = max - min + 1;
        if(a <= 0) {
            a = 1;
        }
        int randomNumber = random.nextInt(a) + min;

        // Print the random number
//        logger.info("Random number between " + min + " and " + max + ": " + randomNumber);

        // Return result
        return randomNumber;
    }

    public int fuzzyMatch(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;  // Insertions
                } else if (j == 0) {
                    dp[i][j] = i;  // Deletions
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1),
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }
        return dp[a.length()][b.length()];
    }

//    public static int rdRange(int min, int max){
//        int randomNumber = ThreadLocalRandom.current().nextInt(min, max);
//
//        // Print the random number
//        logger.info("Random number between " + min + " and " + max + ": " + randomNumber);
//
//        // Return result
//        return randomNumber;
//    }
}
