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
    public RsaKey() {
        p = newPrime(SafJNest.randomBighi(1024));       //crea un numero casuale con la funzioine randomBighi nella classe SafJNest
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
        if(prime.mod(BigInteger.TWO).equals(BigInteger.ZERO)) prime = prime.add(BigInteger.ONE);
        do {prime = prime.add(BigInteger.TWO);} while (!BigInteger.TWO.modPow(prime.subtract(BigInteger.ONE), prime).equals(BigInteger.ONE));
        return prime;
    }

}
