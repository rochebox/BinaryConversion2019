import javax.swing.JOptionPane;

public class BinaryConversion3 {
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
    
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        boolean ok = false;
        while(!ok) {
            String theInput= JOptionPane.showInputDialog("Give me a decimal number, please");
            if(theInput == null) {
                ok = true;
                System.out.println("End program");
            } else {
                try {
                    double x = Double.parseDouble(theInput);
                    System.out.println("the number you input is: " + x);
                    System.out.println("Binary decimal number: \n" + binConvertDecimal(x));
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
    
    public static String binConvertDecimal(double num) {
        System.out.println("dec num is " + num);
        // Set up for the return
        String binString = "";
        
        //Now check to see if you have a negative 
        //We will use 2's complement later.
        boolean isNeg = false;
        
        //check for 64 bit overflow
        //System.out.println("((long)(Math.pow(2.0, 63.0)) * -1)-1 is: " + (((long)(Math.pow(2.0, 63.0)) * -1)-1) );
        //System.out.println("((long)(Math.pow(2.0, 63.0))) is: " + (((long)(Math.pow(2.0, 63.0)))) );
//        if(num < ((long)(Math.pow(2.0, 63.0)) * -1) -1 ||
//                num > (long)(Math.pow(2.0, 63.0))) {
//            System.out.println("Overflow Error....");
//            return binString;
//        } else if (num == (((long)(Math.pow(2.0, 63.0)) * -1)-1) ) {
//            return "1000000000000000000000000000000000000000000000000000000000000000";
//        } else if (num == (((long)(Math.pow(2.0, 63.0))))) {
//            return "0111111111111111111111111111111111111111111111111111111111111111";
//        } else {
//            int fitBits = getBitsForDecNum(num);
//            System.out.println("Back from getBits--For: " + num 
//                   + " fitBits is: " + fitBits);
//            System.out.println("Before Neg check num is: " + num);
//            if(num < 0) {
//                //System.out.println("We have neg num");
//                isNeg = true;
//                num *= -1;
//            }
//            System.out.println("After Neg check num is: " + num);
            
            double thePower = 32.0;
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
            
            
            return(binString);
        }  
    //}  from error checking

    public static int getBitsForDecNum(double num) {
       
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
