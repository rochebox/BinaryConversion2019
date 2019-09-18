import javax.swing.JOptionPane;

public class BinaryConversion2 {
    /* Make sure you check
     * -128 which should give you    - 10000000 (positive or negative)
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
    
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        
        boolean ok = false;
        while(!ok) {
            String theInput= JOptionPane.showInputDialog("Give me an integer, please");
            if(theInput == null) {
                ok = true;
                System.out.println("End program");
            } else {
                try {
                    long x = Long.parseLong(theInput);
                    System.out.println("Here is the binary number: \n" + binConvert(x));
                    ok = true;
                    
                } catch (Exception e) {
                    
                    System.out.println(e.getMessage());
                    System.out.println(e.toString());
                    System.out.println(Thread.currentThread().getStackTrace());
                    //System.out.println("Sorry I wanted a number...");     
                }
            }
        }
    }
    
    public static String binConvert(long num) {
        System.out.println("num is " + num);
        // Set up for the return
        String binString = "";
        
        //Now check to see if you have a negative 
        //We will use 2's complement later.
        boolean isNeg = false;
        
        //check for 64 bit overflow
        System.out.println("((long)(Math.pow(2.0, 63.0)) * -1)-1 is: " + (((long)(Math.pow(2.0, 63.0)) * -1)-1) );
        System.out.println("((long)(Math.pow(2.0, 63.0))) is: " + (((long)(Math.pow(2.0, 63.0)))) );
        if(num < ((long)(Math.pow(2.0, 63.0)) * -1) -1 ||
                num > (long)(Math.pow(2.0, 63.0))) {
            System.out.println("Overflow Error....");
            return binString;
        } else if (num == (((long)(Math.pow(2.0, 63.0)) * -1)-1) ) {
            return "1000000000000000000000000000000000000000000000000000000000000000";
        } else if (num == (((long)(Math.pow(2.0, 63.0))))) {
            return "0111111111111111111111111111111111111111111111111111111111111111";
        } else {
            int fitBits = getBitsForNum(num);
//            System.out.println("Back from getBits--For: " + num 
//                    + " fitBits is: " + fitBits);
//            System.out.println("Before Neg check num is: " + num);
            if(num < 0) {
                //System.out.println("We have neg num");
                isNeg = true;
                num *= -1;
            }
            System.out.println("After Neg check num is: " + num);
            
            
            //for(int b = MAX_BITS-1; b >= 0; b--) {
            for(int b = fitBits-1; b >= 0; b--) {
                System.out.println("num is " + num + " b is " + b);
                System.out.println("checking num vs: "  + (long)(Math.pow(2.0, (double)b )));
                if(num >= (long)(Math.pow(2.0, (double)b ))) {
                    num -= (long)(Math.pow(2.0, (double)b ));
                    binString +="1";
                } else {
                    binString +="0";
                }              
            }
           
            if(isNeg) {
                binString = twosComplement(binString);
            }
             
            //System.out.println("FixStr is " + fixStr);
            return(binString);
        }  
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

    public static int getBitsForNum(long num) {
       
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
