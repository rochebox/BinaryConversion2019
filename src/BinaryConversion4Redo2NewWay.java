import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class BinaryConversion4Redo2NewWay {
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
	//super happy
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
          s = new Scanner(new File("decimalTest2.txt"));
          allGood = true;
        } catch( Exception e ) {
            System.out.println("It's not open");
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
                        binInt = binConvertIntNew(x);
                        System.out.println(binInt);
                        
                    } catch (Exception e) {
                        System.out.println(nextNumIn + " -- Number is too Large...");    
                        
                    }
                 
                } else if(dataType == FLOAT_TYPE) {
                	String binFloat = "";
                	try {
                		double dNum = Double.parseDouble(nextNumIn);
                		binFloat = binConvertFloat(dNum);
                		System.out.println(binFloat);
                		
                	} catch (Exception e) {
                		System.out.println(" Issue with decimal number....");
                	}
                    
                	
                } else {
                    System.out.println("We have an error as not int or float type");
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
                bitArray[loc++] = (byte)(theNum % 2);
                theNum /=2;
                
            }
            
            for(int d = bitArray.length-1; d >=0; d--) {
                binString2 += Byte.toString(bitArray[d]);
            }
            if(isNeg) {
                binString2 = twosComplement(binString2);
            }
        }
        return binString2;
    }

    
    
    public static String binConvertInt(long num) {
        //System.out.println("In binConvertInt is " + num);
        String binString = "";
        
        //We will use 2's complement later.
        boolean isNeg = false;
        
        //check for 64 bit overflow
        //System.out.println("((long)(Math.pow(2.0, 63.0)) * -1)-1 is: " + (((long)(Math.pow(2.0, 63.0)) * -1)-1) );
        //System.out.println("((long)(Math.pow(2.0, 63.0))) is: " + (((long)(Math.pow(2.0, 63.0)))) );
        if(num < ((long)(Math.pow(2.0, 63.0)) * -1) -1 ||
                num > (long)(Math.pow(2.0, 63.0))) {
            System.out.println("Overflow Error....");
            return binString;
        } else if (num == (((long)(Math.pow(2.0, 63.0)) * -1)-1) ) {
            return "1000000000000000000000000000000000000000000000000000000000000000";
        } else if (num == (((long)(Math.pow(2.0, 63.0))))) {
            return "0111111111111111111111111111111111111111111111111111111111111111";
        } else {
            int fitBits = getBitsForDecInt(num);

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
            String binString2 = "";
            for(int d = bitArray.length-1; d >=0; d--) {
                binString2 += Byte.toString(bitArray[d]);
            }
            if(isNeg) {
                binString2 = twosComplement(binString2);
            }
            //System.out.println("Using New way: " + binString2);
            
            
            
            
            //for(int b = MAX_BITS-1; b >= 0; b--) {
            for(int b = fitBits-1; b >= 0; b--) {
                //System.out.println("num is " + num + " b is " + b);
                //System.out.println("checking num vs: "  + (long)(Math.pow(2.0, (double)b )));
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
             
            //System.out.println("At end of Int conversion is " + binString);
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
    
    public static String binConvertFloat(double num) {
        //System.out.println("dec num is " + num);
        // Set up for the return
        String binString = "";  //32 bits
        String signString = ""; // 1 bit
        String exponent = "";   // 8 bits using 127 bias
        String mantissa = "";   // 23
        String intBits = "";
        String mantissaBitString = "";
        String floatBin = "";
        String scientificFormat = "";
        int expNum;
        int intBitsNum;
        final int MANTISSA_LEN = 23;
        final int BIAS = 127;   
        
        //This is for the sign bit
        boolean isNeg = false;
        if(num < 0) {
        	isNeg = true;
        	signString += "1";
        } else {
        	signString += "0";
        }
        
        //separate the number into integer and decimal parts
        long integerPart = (long) num;
        //get bits for the integer part
        intBitsNum = getBitsForDecInt(integerPart);
        //System.out.println("Number of bits for int is: " + intBitsNum);
        intBits = binConvertIntNew(integerPart);
        //isolate the decimal part
        double decimalPart = num - integerPart;
        //System.out.println("In dec convert decimalPart is " + decimalPart);
        
    
        byte[] mantissaBits = new byte[MANTISSA_LEN+1];  //mantissa is at most 23 bits
        
        for(int b = 0; b < mantissaBits.length; b++) {
            decimalPart *=2;
            if((int)(decimalPart) >0) {  //we have a one
                mantissaBits[b] = 1;
                decimalPart -= (int)(decimalPart);
            } else {
                mantissaBits[b] = 0;
            }
        }
        
        //System.out.println("Mantissa Part Array: " + Arrays.toString(mantissaBits));
        
        //convert it to a string
        for(int b = 0; b < mantissaBits.length; b++) {
        	mantissaBitString += Byte.toString(mantissaBits[b]);
        }
        
        //now create one whole base 2 binary number
        String nonScientific = intBits + "." + mantissaBitString;
        //System.out.println("Nonscientific binary: " + nonScientific);
        
        //Lets find the exponent
        if(intBits.indexOf('1') >= 0) {
        	expNum = intBitsNum - intBits.indexOf('1') - 1;
        	//now make it scientific format
        	for(int b = intBits.indexOf('1'); b < intBits.length(); b++) {
        		scientificFormat += intBits.charAt(b);
        		if(scientificFormat.length() == 1) {
        			scientificFormat += ".";
        		}
        	}
        	
        	//System.out.println("Scientific Format length: " + scientificFormat.length() + " Scientific Format " + scientificFormat);
        	//System.out.println("The number of decimal bits to copy is " + (MANTISSA_LEN - scientificFormat.length() + 2));
        	int mantissaLengthToAdd = MANTISSA_LEN - scientificFormat.length() + 2;
        	for(int b = 0; b < mantissaLengthToAdd; b++) {
        		//System.out.println("b = " + b);
        		scientificFormat += mantissaBitString.charAt(b);
        	}
        	
        	//System.out.println("Scientific format: " + scientificFormat + " x 2^" + expNum);
        	
        } else {
        	if(mantissaBitString.indexOf('1') >= 0) {
        		expNum = (mantissaBitString.indexOf('1') * -1) -1;
            	//now make it scientific format
        		int b;
        		for(b = mantissaBitString.indexOf('1'); b < mantissaBitString.length(); b++) {
            		scientificFormat += mantissaBitString.charAt(b);
            		if(scientificFormat.length() == 1) {
            			scientificFormat += ".";
            		}
            	}
        		for( int extraB = scientificFormat.length(); extraB < MANTISSA_LEN; extraB++ ) {
        			scientificFormat += "0";
        		}
        		
        	} else {
        		scientificFormat = "0.00000000000000000000000";
        		expNum = 0;
        	}
        	//System.out.println("Scientific format: " + scientificFormat + " x 2^" + expNum);
        	
        }
        
        //System.out.println("The exponent number is " + expNum);
        //get the bias number
        if(expNum == 0) {
        	exponent = "00000000";
        } else {
        	long bias = (long)(BIAS + expNum);
            //System.out.println("The long bias is " + bias);
            exponent =  binConvertIntNew(bias);    
            //System.out.println("The exponent is " + exponent);
            if(exponent.length() > 8) {
            	exponent = exponent.substring(BYTE);
            }
        	
        }
        
   
        String rString = signString + exponent + scientificFormat.substring(2);
        //System.out.println("The float string has len = " + rString.length());
        return rString;
    }  
        
        
      

    public static int getBitsForDecInt(long num) {
    	//Happy
       
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
