import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class BinaryConversion4Redo {
    /* Make sure you check
     * -128 which should give you    - 10000000 (positive)
     *  127 which should give you    - 01111111
     *  -32768 which should give you - 1000000000000000 (positive)
     *  32767 which should give you  - 0111111111111111
     *  -2^31 (-2147483648) which is - 10000000000000000000000000000000 (positive)
     *  2^31-1 (2147483647) which is - 01111111111111111111111111111111
     *  2^63 (-9223372036854775808) - 1000000000000000000000000000000000000000000000000000000000000000
     *  2^63-1(9223372036854775807) - 0111111111111111111111111111111111111111111111111111111111111111
     */
    public static final int MAX_BITS = 64;
    public static final int LONG = 64;  //-9223372036854775808  9223372036854775807
    public static final int INT = 32;
    public static final int SHORT = 16;
    public static final int BYTE = 8;
    // Data Type Codes...
    public static final int STRING_TYPE = 0;
    public static final int INT_TYPE = 1;
    public static final int FLOAT_TYPE = 2;
    
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        int dataType = -1;
        Scanner s = null;
        boolean allGood = false;
        try {
          s = new Scanner(new File("testClassDecTest.txt"));
          //System.out.println("It's open");
          allGood = true;
        } catch( Exception e ) {
            System.out.println("It's not open");
        }
 
        if(allGood) {
            while(s != null && s.hasNextLine()) {
                String nextNumIn = s.nextLine();
                //System.out.println(nextNumIn);
                
                if(itsNotANumber(nextNumIn)) {
                    //check for bad input
                    System.out.println(nextNumIn + " is bad input");
                    dataType = STRING_TYPE;
                } else if (isADecimal(nextNumIn)) {
                    //check for a decimal
                    System.out.println(nextNumIn + " is a decimal");
                    dataType = FLOAT_TYPE;
                } else {
                    //its an integer
                    System.out.println(nextNumIn + " is an integer");
                    dataType = INT_TYPE;
                }
                
                //now process the numbers....
                if(dataType == INT_TYPE) {
                    String binInt = "";
                    try {
                        long x = Long.parseLong(nextNumIn);
                        binInt = binConvertIntNew(x);
                        System.out.println(binInt);
                        
                    } catch (Exception e) {
                        System.out.println(nextNumIn + " -- Number is too Large...");    
                        binInt = "" + nextNumIn + " is too big for Long";
                    }
                 
                } else if(dataType == FLOAT_TYPE) {
                    System.out.println("float (32 Bit is " );
                } else {
                    
                }
            }
            s.close();
        }
    }
    
    public static boolean itsNotANumber(String nextNumIn) {
        boolean itsNotANumber = false;
        String nonNums = "abcdefghijklmnopqrstuvwxyz!@#$%^&*()_+= {[}]|\\:;\"'<,>?/";
        
        for(int l= 0; l < nonNums.length(); l++) {
            if(nextNumIn.contains("" + nonNums.charAt(l))) {
                itsNotANumber = true;
            }
        }
        return itsNotANumber;
    }
    
    public static boolean isADecimal(String nextNumIn) {
        boolean itsADecimal = false;
        if(nextNumIn.contains(".")) {
            itsADecimal = true;
        }
        return itsADecimal;
    }
    
    public static String binConvertIntNew(long num) {
        //set up final binString
        String binString2 = "";
        
        //We will use 2's complement later.
        boolean isNeg = false;
        
        //check for 64 bit overflow
        if(num < ((long)(Math.pow(2.0, 63.0)) * -1) -1 ||
                num > (long)(Math.pow(2.0, 63.0))) {
            System.out.println("Overflow Error....");
            return binString2;
        //I let this be a special case, because the Math Pow numbers I was getting would fill the long
        } else if (num == (((long)(Math.pow(2.0, 63.0)) * -1)-1) ) {
            return "1000000000000000000000000000000000000000000000000000000000000000";
        } else if (num == (((long)(Math.pow(2.0, 63.0))))) {
            return "0111111111111111111111111111111111111111111111111111111111111111";
        } else {         
            //Get the number of bits according to Java standard sizes...
            int fitBits = getBitsForDecInt(num);
            
            //Check for negativity (for 2's complement)
            if(num < 0) {
                isNeg = true;
                num *= -1;
            }
            
            //New way dividing by 2
            byte[] bitArray = new byte[fitBits];
            
            long theNum = num;
            int loc = 0;
            while(theNum > 0) {
                //System.out.println("In the loop theNum is: " + theNum);
                bitArray[loc++] = (byte)(theNum % 2);
                theNum /=2;
                
            }
            
            //System.out.println("For theNum, bitArray is " + Arrays.toString(bitArray));
            for(int d = bitArray.length-1; d >=0; d--) {
                binString2 += Byte.toString(bitArray[d]);
            }
            if(isNeg) {
                binString2 = twosComplement(binString2);
            }
            System.out.println("Using New way: " + binString2);
        }
        return binString2;
    }

    
    public static String twosComplement(String binString ) {
        //Flip bits
        String newString = "";
        for(int b = 0; b < binString.length(); b++) {
            if(binString.charAt(b) == '1') {
                newString += "0";
            } else {
                newString += "1";
            }
        }
        
        //Add One
        int curBit = newString.length() - 1; //start at end
        while(curBit >= 0 && newString.charAt(curBit) == '1') {
            curBit--;  
        }
        
        if(curBit >= 0) {
            newString = newString.substring(0, curBit) + "1" + newString.substring(curBit + 1);
        }
 
        
        return newString;
        
    }
    
    public static String binConvertDouble(double num) {
        System.out.println("dec num is " + num);
        
        String binString = "";
        
        //for the negativity bit
        boolean isNeg = false;
        
        //separate the number into Int and decimal parts
        long integerPart = (long) num;
        //get bits for the integer part
        String intBits = binConvertIntNew(integerPart);
        //isolate the decimal part
        double decimalPart = num - integerPart;
        
        double thePower = 32.0; //floating point has 23 bit mantissa
        byte[] decBits = new byte[32];  //more than we need
        
        for(int b = 0; b < decBits.length; b++) {
            decimalPart *=2;
            if((int)(decimalPart) >0) {
                decBits[b] = 1;
                decimalPart -= (int)(decimalPart);
            } else {
                decBits[b] = 0;
            }
        }
        
        System.out.println("Dec Part Array: " + Arrays.toString(decBits));
        
        String decimalSideBits = "";
        for(int b= 0; b < decBits.length; b++) {
            decimalSideBits += Byte.toString(decBits[b]);
        }
        //Floating Point number (before scientific notation)
        String floatBin = intBits + "." + decimalSideBits;
        System.out.println("Floating Point is: " + floatBin);
        
        
        /*
            int thePointPlaced = 0;
            int thePointMoves = 0;
            
            for(int b = MAX_BITS-1; b >= 0; b--) 
            {
                
            
                System.out.println("num is " + num + " b is " + b);
                System.out.println("num is " + num + " thePower is " + thePower);
                double theExpo;
                if(thePower < 0) {
                    theExpo = 1.0/Math.pow(2.0, (thePower * -1.0));
                    System.out.println("TheExpo is: " + theExpo);
                } else {
                    theExpo = thePower;
                    System.out.println("TheExpo is: " + theExpo);
                }
                   
                System.out.println("checking num vs: "  + (long)(Math.pow(2.0, (double)theExpo )));
                
                if(thePower >= 0) {
                    if(num >= (Math.pow(2.0, theExpo ))) {
                        num -= (Math.pow(2.0, theExpo ));
                        binString +="1";
                    } else {
                        binString +="0";
                    } 
                } else {
                    if(num >= theExpo ) {
                        num -= theExpo;
                        binString +="1";
                    } else {
                        binString +="0";
                    } 
                    
                }
                
                //decimal point finding and changing thePower value
                if(thePointPlaced == 0 && thePower <= 0) 
                {
                    System.out.println("the Point should be at " + thePointMoves +" from 64.");
                    thePointPlaced = thePointMoves;
                }
                
                thePointMoves++;
                thePower -= 1.0;
            }
            
            */
            return(floatBin);
        }  
    //}  from error checking

    public static int getBitsForDecInt(long num) {
       
        if(num >= -128 && num <= 127) {
            return  BYTE;
        } else if (num >= -32768 && num <= 32767) {
            return SHORT;
        } else if (num >= -(long)(Math.pow(2.0, 31.0)) 
                && num <= (long)(Math.pow(2.0, 31.0)) - 1) {
            return INT;
        } else {
            return LONG;
        }
    }
}
