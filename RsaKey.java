
import java.math.BigInteger;
import java.util.*;
import java.io.*;

public class RsaKey {
    private BigInteger p;
    private BigInteger q;
    private BigInteger n = new BigInteger("0");
    private BigInteger e = new BigInteger("0");
    private BigInteger d = new BigInteger("0");
    private BigInteger phi = new BigInteger("0");

    public RsaKey() {
        try {
            ArrayList<String> Numbers = new ArrayList<String>();
            BigInteger[] chosenNumbers = new BigInteger[3];
            File inputFile = new File("primi.txt");
            Scanner scannerDaFile = new Scanner(inputFile);
            while (scannerDaFile.hasNextLine())
                Numbers.add(scannerDaFile.nextLine());
            int j = 0;
            for (int i = 0; j < 3; i++) {
                if (Numbers.get(i).charAt(0) != '/') {
                    chosenNumbers[j] = new BigInteger(Numbers.get(i));
                    Numbers.set(i, "/" + Numbers.get(i));
                    j++;
                }
            }
            FileWriter fileout = new FileWriter("primi.txt");
            for (String Number : Numbers) {
                fileout.write(Number + "\n");
            }
            p = chosenNumbers[0];
            q = chosenNumbers[1];
            e = chosenNumbers[2];
            scannerDaFile.close();
            fileout.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        d = e.modInverse(phi);
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getD() {
        return d;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getQ() {
        return q;
    }
}