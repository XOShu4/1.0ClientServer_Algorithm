import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Classe officiale del <a href="https://github.com/SafJNest">SafJNest Team.</a>
 * <p>
 * Nella classe sono presenti alcuni dei metodi piu' importanti e significativi.
 */
public class SafJNest extends Thread {

    public static void loadingBee(int speed) {
        printDbmrStyle("\033[46;30m "
                + "                                                      __            \n"
                + "                                                      // \\           \n"
                + "                                                      \\\\_/ //        \n"
                + "                                     '-.._.-''-.._.. -(||)(')        \n"
                + "                                                       '''           ", speed);
        printDbmrStyle(""
                + "\033[90m███████╗\033[93m █████╗ \033[90m███████╗\033[93m   ██╗    \033[90m███╗   ██╗\033[93m███████╗\033[90m███████╗\033[93m████████╗\033[40m\n"
                + "\033[90m██╔════╝\033[93m██╔══██╗\033[90m██╔════╝\033[93m   ██║    \033[90m████╗  ██║\033[93m██╔════╝\033[90m██╔════╝\033[93m╚══██╔══╝\n"
                + "\033[90m███████╗\033[93m███████║\033[90m█████╗  \033[93m   ██║    \033[90m██╔██╗ ██║\033[93m█████╗  \033[90m███████╗\033[93m   ██║   \n"
                + "\033[90m╚════██║\033[93m██╔══██║\033[90m██╔══╝\033[93m██   ██║    \033[90m██║╚██╗██║\033[93m██╔══╝  \033[90m╚════██║\033[93m   ██║   \n"
                + "\033[90m███████║\033[93m██║  ██║\033[90m██║   \033[93m╚█████╔╝    \033[90m██║ ╚████║\033[93m███████╗\033[90m███████║\033[93m   ██║   \n"
                + "\033[90m╚══════╝\033[93m╚═╝  ╚═╝\033[90m╚═╝   \033[93m ╚════╝     \033[90m╚═╝  ╚═══╝\033[93m╚══════╝\033[90m╚══════╝\033[93m   ╚═╝\n\033[0m",
                speed/2);
    }

    public static void printDbmrStyle(String line, int speed) {
        try {
            for (char c : line.toCharArray()) {
                System.out.print(c);
                Thread.sleep(speed);
            }
            System.out.println();
        } catch (Exception e) {
        }
    }

    public static void printDbmrStyle(String line) {
        try {
            for (char c : line.toCharArray()) {
                System.out.print(c);
                Thread.sleep(15);
            }
            System.out.println();
        } catch (Exception e) {
        }
    }

    public static int divideandconquer(int n) {
        n = abs(n);
        if (n < 100000) {
            if (n < 100) {
                if (n < 10)
                    return 1;
                else
                    return 2;
            } else {
                if (n < 1000)
                    return 3;
                else {
                    if (n < 10000)
                        return 4;
                    else
                        return 5;
                }
            }
        } else {
            if (n < 10000000) {
                if (n < 1000000)
                    return 6;
                else
                    return 7;
            } else {
                if (n < 100000000)
                    return 8;
                else {
                    if (n < 1000000000)
                        return 9;
                    else
                        return 10;
                }
            }
        }
    }

    public static int abs(int x) {
        return (x > 0) ? x : -x;
    }

    public static BigInteger randomBighi(int numBits) {
        if (numBits < 1)
            throw new IllegalArgumentException("SafJNest doesnt like negative numbers");
        numBits--;
        int numBytes = (int) (((long) numBits + 7) / 8);
        byte[] randomBits = new byte[numBytes];
        if (numBytes > 0) {
            try {
                SecureRandom.getInstanceStrong().nextBytes(randomBits);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            int excessBits = 8 * numBytes - numBits;
            randomBits[0] &= (1 << (8 - excessBits)) - 1;
        }
        return new BigInteger(1, randomBits).add(BigInteger.TWO.pow(numBits));
    }

    public static float fastInvSquareRoot(float x) {
        float xhalf = 0.5f * x;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i >> 1);
        x = Float.intBitsToFloat(i);
        x *= (1.5f - xhalf * x * x);
        return x;
    }

    public static String checkEmoji(String msg) {
        msg = msg.replaceAll("<3", new StringBuilder().appendCodePoint(0x1F497).toString());
        msg = msg.replaceAll(":143:", new StringBuilder().appendCodePoint(0x1F618).toString());
        msg = msg.replaceAll(":pantano:", new StringBuilder().appendCodePoint(0x1F62C).toString());
        msg = msg.replaceAll(":mario:", new StringBuilder().appendCodePoint(0x1F921).toString());
        msg = msg.replaceAll(":safj:", new StringBuilder().appendCodePoint(0x1F41D).toString());
        msg = msg.replaceAll(":skull:", new StringBuilder().appendCodePoint(0x1F480).toString());
        msg = msg.replaceAll(":sad:", new StringBuilder().appendCodePoint(0x1F614).toString());
        msg = msg.replaceAll(":merio:", new StringBuilder().appendCodePoint(0x1F533).toString());
        msg = msg.replaceAll(":baco:", new StringBuilder().appendCodePoint(0x1F41B).toString());
        msg = msg.replaceAll(":swag:", new StringBuilder().appendCodePoint(0x1F60E).toString());
        msg = msg.replaceAll(":stonks:", new StringBuilder().appendCodePoint(0x1F4C8).toString());
        msg = msg.replaceAll(":diablo:", new StringBuilder().appendCodePoint(0x1F608).toString());
        msg = msg.replaceAll(":deltoide:", new StringBuilder().appendCodePoint(0x00394).toString());
        msg = msg.replaceAll(":squidgame:", (new StringBuilder().appendCodePoint(0x1F991).toString()
                + new StringBuilder().appendCodePoint(0x1F3B2).toString()));
        return msg;
    }

    public static boolean isPrime(BigInteger p) {
        return (BigInteger.TWO.modPow(p, p).compareTo(BigInteger.TWO) == 0) ? 1>0 : 1<0;
    }

    public static String getJSalt(int byteNum) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBits(byteNum));
    }

    private static byte[] randomBits(int n) {
        byte bytes[] = new byte[n];
        (new SecureRandom()).nextBytes(bytes);
        return bytes;
    }

}
