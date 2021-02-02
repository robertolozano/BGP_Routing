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
                IP ipObj = new IP(ipArrayInt[0], ipArrayInt[1], ipArrayInt[2], ipArrayInt[3]);
                ipList.add(ipObj);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //Print out IPList
        for(int i = 0; i < ipList.size(); i++) {
            ipList.get(i).display();
        }

        System.out.println("");

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
                    IP ipObj = new IP(ipArrayInt[0], ipArrayInt[1], ipArrayInt[2], ipArrayInt[3]);
                    int matchingTemp = Integer.parseInt(dataArrayStringFormat[2]);
                    int AStemp = Integer.parseInt(dataArrayStringFormat[1]);
                    Address addressObj = new Address(ipObj, matchingTemp, AStemp);
                    LookupTable.add(addressObj);
                }
                if(!valid){
                    System.out.println(ipData);

                }

            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println(LookupTable.size());
    }
}