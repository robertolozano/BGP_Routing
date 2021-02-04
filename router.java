import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;

public class router{
    public static void main(String[] args){

        //ArrayList of IP Obj from IPList.txt
        ArrayList<IP> ipList = new ArrayList<IP>();
        ArrayList<Address> LookupTable = new ArrayList<Address>();

        //Read 
        try{
            //Reading in IPList.txt
            File myObj = new File("IPlist.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()){
                String data = myReader.nextLine();

                //Splitting string at "."
                String[] ipArrayString = data.split("\\.");
                int size = ipArrayString.length;

                //Convert String Array to int Array
                int [] ipArrayInt = new int [size];
                for(int i=0; i<size; i++){
                    ipArrayInt[i] = Integer.parseInt(ipArrayString[i]);
                }

                //Create IP obj from ip segments and add to IPList
                String bit32 = convertTo32(ipArrayInt[0], ipArrayInt[1], ipArrayInt[2], ipArrayInt[3]);
                IP ipObj = new IP(ipArrayInt[0], ipArrayInt[1], ipArrayInt[2], ipArrayInt[3], bit32);
                ipList.add(ipObj);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //Print out IPList
        // System.out.println("Input IP's");
        // for(int i = 0; i < ipList.size(); i++) {
        //     ipList.get(i).display();
        // }

        // System.out.println("");

        try{
            //Reading in DB_091803.txt
            File myObj = new File("DB_091803.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()){
                String data = myReader.nextLine();

                //Splitting string at "."
                String[] dataArrayStringFormat = data.split(" ");
                int size = dataArrayStringFormat.length;
                String ipData = dataArrayStringFormat[0];
                                
                //Splitting string at "."
                String[] ipArrayString = ipData.split("\\.");
                int ipArraySize = ipArrayString.length;

                boolean valid = true;

                if(ipArraySize != 4){
                    valid = false;
                }

                //Convert String Array to int Array
                int [] ipArrayInt = new int [ipArraySize];
                for(int i=0; i<ipArraySize; i++){
                    if(ipArrayString[i].isEmpty()){
                        // System.out.println("Invalid IP");
                        valid = false;
                        break;
                    }
                    else{
                        // System.out.println(ipArrayString[i] + " " + i);
                        ipArrayInt[i] = Integer.parseInt(ipArrayString[i]);
                        if(ipArrayInt[i] > 255){
                            valid = false;
                            break;
                        }
                    }
                }

                if(valid){
                    // Create IP obj from ip segments and add to IPList
                    String bit32 = convertTo32(ipArrayInt[0], ipArrayInt[1], ipArrayInt[2], ipArrayInt[3]);
                    IP ipObj = new IP(ipArrayInt[0], ipArrayInt[1], ipArrayInt[2], ipArrayInt[3], bit32);
                    int matchingTemp = Integer.parseInt(dataArrayStringFormat[2]);
                    int AStemp = Integer.parseInt(dataArrayStringFormat[1]);
                    Address addressObj = new Address(ipObj, matchingTemp, AStemp);
                    LookupTable.add(addressObj);
                }
                if(!valid){
                    // System.out.println(ipData + " INVALID IP");

                }

            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // System.out.println("\nTotal number of IP: " + LookupTable.size());
        findBestAddress(ipList, LookupTable);


    }

    public static String convertTo32(int num1, int num2, int num3, int num4){
        String leadingZeros = "00000000";

        String first8 = leadingZeros + Integer.toString(num1,2);
        String second8 = leadingZeros + Integer.toString(num2,2);
        String third8 = leadingZeros + Integer.toString(num3,2);
        String fourth8 = leadingZeros + Integer.toString(num4,2);

        first8 = first8.substring(first8.length() - 8);
        second8 = second8.substring(second8.length() - 8);
        third8 = third8.substring(third8.length() - 8);
        fourth8 = fourth8.substring(fourth8.length() - 8);

        String bit32 = first8 + second8 + third8 + fourth8;

        return bit32;
    }

    // function to look through table to find best address
    public static void findBestAddress(ArrayList<IP> ipList, ArrayList<Address> LookupTable){ 
        for(int i = 0; i < ipList.size(); i++) {
            IP testIP = ipList.get(i);

            // storing best output for each ip value
            IP bestIP = testIP;
            int bestLeadingPrefix = 0;
            int bestASNumber = 0;

            // subtracting the invalid IP addresses
            int index = LookupTable.size();
            index = index - 7;

            // comparing ip address to each address in the look up table
            for(int j = 0; j <= index; j++) {
                Address a = LookupTable.get(j);

                if (a.ip.ip_1 == testIP.ip_1) {  // compare the first 8 bits to save time
                    if (testIP.bit32.regionMatches(0, a.ip.bit32, 0, a.AS)) { 
                        if (a.AS > bestLeadingPrefix) {
                            bestIP = a.ip;
                            bestLeadingPrefix = a.AS;
                            bestASNumber = a.numMatchingBits;
                        }
                    }
                }
            }

            // output format
            String bestAnswer = "/" + String.valueOf(bestLeadingPrefix) + " " + String.valueOf(bestASNumber) + " ";
            
            bestIP.output();
            System.out.print(bestAnswer);
            testIP.output();
            System.out.print("\n");
        }
    }
}

