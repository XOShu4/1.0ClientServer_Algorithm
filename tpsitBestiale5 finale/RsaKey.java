/**
 * Copyright (c) 22 Giugno anno 0, 2021, SafJNest and/or its affiliates. All rights reserved.
 * SAFJNEST PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * 
 * 
 * 
 * 
 */
import java.math.BigInteger;
import java.util.*;
import java.io.*;
/**
 * @author XOShu4
 * classe che genera KeyPub e KeyPrv partendo da p e q
 */
public class RsaKey {
    /**
     * presa da file
     */
    private BigInteger p;
     /**
     * presa da file
     */
    private BigInteger q;
    /**
     * presa da file
    */
    private BigInteger e;
     /**
     * p*q
     */
    private BigInteger n;
  
     /**
     * e^-1 mod phi
     */
    private BigInteger d;
    /**
     * (p-1)*(q-1)
     */
    private BigInteger phi;

    public RsaKey() {
        try {
            ArrayList<String> Numbers = new ArrayList<String>();        //trasferisco tutto il file su Numbers
            BigInteger[] chosenNumbers = new BigInteger[3];             //scelgo tre numeri 
            File inputFile = new File("primi.txt");
            Scanner scannerDaFile = new Scanner(inputFile);
            while (scannerDaFile.hasNextLine())
                Numbers.add(scannerDaFile.nextLine());
            int j = 0;
            for (int i = 0; j < 3; i++) {
                if (Numbers.get(i).charAt(0) != '/') {                  //prendo solo i nomeri che non iniziano con /
                    chosenNumbers[j] = new BigInteger(Numbers.get(i));
                    Numbers.set(i, "/" + Numbers.get(i));               //quelli che scelgo li riscrivo con / prima
                    j++;
                }
            }
            FileWriter fileout = new FileWriter("primi.txt");
            for (String Number : Numbers) {                             //riscrivo tutto nel file
                fileout.write(Number + "\n");
            }
            p = chosenNumbers[0];                                       //inserisco i numeri dentro le variabili
            q = chosenNumbers[1];
            e = chosenNumbers[2];
            scannerDaFile.close();
            fileout.close();
        } catch (Exception e) {
        }
        n = p.multiply(q);                                              //calcolo le key
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

}