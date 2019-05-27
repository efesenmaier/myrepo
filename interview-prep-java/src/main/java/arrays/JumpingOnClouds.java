package arrays;

public class JumpingOnClouds {
    // Complete the jumpingOnClouds function below.
    static int jumpingOnClouds(int[] c) {

        int numJumps = 0;
        int position = 0;

        while (position < c.length - 1) {
            int bigJump = position + 2;
            if (bigJump < c.length && c[bigJump] != 1) {
                position = bigJump;
                numJumps++;
            } else {
                int littleJump = position + 1;
                if (c[littleJump] != 1) {
                    position = littleJump;
                    numJumps++;
                } else {
                    assert false;
                }
            }
        }
        return numJumps;
    }
}
