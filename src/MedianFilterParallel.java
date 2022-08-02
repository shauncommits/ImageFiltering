import java.awt.image.BufferedImage;

public class MedianFilterParallel {
    public static void main(String[] args) {
        String inputImageName = "";
        String outputImageName = "";
        int windowWidth = 0;
        try{
            inputImageName = args[0];
            outputImageName = args[1];
            windowWidth = Integer.parseInt(args[2]);
        }catch(IndexOutOfBoundsException e){
            System.out.println("Opps! Incorrect command line parameters");
            System.out.println("Your command line parameters must be in this order:");
            System.out.println("<inputImageName> <outputImageName> <windowWidth>");
        }
        
        
        if(windowWidth>=3&&windowWidth%2!=0){
            

        }else{
            System.out.println("The window width must be an odd, positive integer >=3.");
        }
    
    }
}
