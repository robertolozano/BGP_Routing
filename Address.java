import java.io.*;

public class Address {
    IP ip;
    int numMatchingBits;
    int AS;
    
    public Address(IP ipObj, int matching, int asNum){
        this.ip = ipObj;
        this.numMatchingBits = matching;
        this.AS = asNum;
    }
}