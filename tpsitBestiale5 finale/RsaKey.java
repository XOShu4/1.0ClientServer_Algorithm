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
/**
 * @author XOShu4
 * @author NeutronSun
 * classe che genera KeyPub e KeyPrv partendo da p e q generati in maniera casuale partendo da lastPrime 
 * NeutronSun: "Classe molto interessante."
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
    /**
     * numero minimo per la generazione delle chiavi, sovrascritto ogni volta che un client si collega.
     */
    private BigInteger lastPrime = new BigInteger("100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000367");

    public RsaKey() {
        
        lastPrime = new BigInteger(lastPrime.bitLength(), new Random());
        p = newPrime(lastPrime);
        q = newPrime(p);
        e = newPrime(q);
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

    public BigInteger newPrime(BigInteger prime) {
        BigInteger a = new BigInteger("2");
        if(prime.mod(BigInteger.TWO).equals(BigInteger.ZERO))
            prime = prime.add(BigInteger.ONE);
        do {
            prime = prime.add(BigInteger.TWO);
        } while (!a.modPow(prime.subtract(BigInteger.ONE), prime).equals(BigInteger.ONE));
        lastPrime = prime;
        return prime;
    }

}
