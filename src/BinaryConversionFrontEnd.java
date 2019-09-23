import java.io.File;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class BinaryConversionFrontEnd {
    /* Make sure you check
     * -128 which should give you    - 10000000 (positive/neg)
     *  127 which should give you    - 01111111
     *  -32768 which should give you - 1000000000000000 (positive/neg)
     *  32767 which should give you  - 0111111111111111
     *  -2^31 (-2147483648) which is - 10000000000000000000000000000000 (positive/neg)
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
          s = new Scanner(new File("testClass.txt"));
          //System.out.println("It's open");
          allGood = true;
        } catch( Exception e ) {
            System.out.println("It's not open.  There is an issue opening your file.");
        }
 
        if(allGood) {
            while(s != null && s.hasNextLine()) {
                String nextNumIn = s.nextLine();
                
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
                        //This is my conversion method...
                        binInt = binConvertInt(x);
                        System.out.println(binInt);
                        
                    } catch (Exception e) {
                        System.out.println(nextNumIn + " -- Number is too Large...");    
                        binInt = "" + nextNumIn + " is too big for Long";
                    }
                 
                } else if(dataType == FLOAT_TYPE) {
                    // Create the 32 bit float method....
                } else {
                    // Strings??
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
    
    
    public static String binConvertInt(long num) {
       //This is your code for binary byte, short, int and long conversion
        String binString = "";
        //Your code here....
        return binString;
    }
   

}
