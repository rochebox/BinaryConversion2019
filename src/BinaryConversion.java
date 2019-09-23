import javax.swing.JOptionPane;

public class BinaryConversion {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        boolean ok = false;
        while(!ok) {
            String theInput= JOptionPane.showInputDialog("Give me an integer, please");
            
            try {
                int x = Integer.parseInt(theInput);
                System.out.println("Here is the binary number \n" + binConvert(x));
                ok = true;
                
            } catch (NumberFormatException ne) {
                System.out.println("Sorry I wanted a number...");
                
            }
        }
    }
    
    public static String binConvert(int num) {
        // Set up for the return
        String binString = "";
        
        //Now check to see if you have a negative 
        //We will use 2's complement later.
        boolean isNeg = false;
        
        if(num < 0) {
            isNeg = true;
            num *= -1;
        }
        
        int numOfBits;
        //Now find out whether you are dealing with a 
        // byte, short, int or long
        int[] sizes = {8, 16, 32, 64};
        int mySize = 0;
        for(int i = 0; i < 4; i++) {
            System.out.println("bits is:" + (sizes[i]-1));
            
            
        }
        
        System.out.println("The bits for " + num + " is " + mySize);
        
        
        
        
        
        
        return binString;
    }
    
    

}
