import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import javax.imageio.ImageIO;

/**
 * MedianFilterSerial class applies median filer on an image using serial programming
 * @author Shaun Mbolompo 2022/08/06
 * 
 */
public class MedianFilterSerial{


    // Declare the variable static so it can be used by the static methods and also that it may be for used by all classes of MedianFIlterSerial
    // will be the same for all the instances of the class, meaning can be shared by all the instances of the class declared in
    static long startTime = 0; 

    
    // Method to start the timing of how low the system takes to do the median filter using serial programming
    // it initializes the static variable startTime when called
    
    private static void tick(){
         startTime = System.currentTimeMillis();
    }
 
    
    // Method toc to return the time taken by the program to execute
    // return the time it takes the program using the available processors to apply the median filter on an image
    
    private static float toc(){
         return (System.currentTimeMillis()-startTime)/1000.0f;
    }


    /**
     * Drive class to run other classes
     * @param args receives arguments from the terminal and for the this task it takes in three arguments
     */
    public static void main(String[] args) {

        // start timing when the driver method is called
        tick();

        // Initilize the images name by default to empty string to later use the variable in the code 
        String inputImageName = "";
        String outputImageName = "";
        
        int windowWidth = 0; // intialize the window size

        try{
            // set the images variables and window size declared early on to the argument values
            inputImageName = args[0];
            outputImageName = args[1];
            windowWidth = Integer.parseInt(args[2]);
            
        // An exception caught is gracefully by printing out on the console the sting below 
        }catch(IndexOutOfBoundsException e){
            System.out.println("Opps! Incorrect command line, use the following format:");
            System.out.println("run-<fileName> ARGS(<inputImageName> <outputImageName> <windowWidth>)");
            e.printStackTrace();
        }

        // Initialize the images through the Buffered class to null
        BufferedImage originalImage = null;
        BufferedImage filteredImage = null;

        // Initialize the variable of type Color to null to be used to both extract and put colors from the original image to the image used to produce a filter
        Color color = null;
        Color newColor = null;

        // Sets the files to default null values to later initial with the images paths inside the while loop
        File file  = null;
        File file2 = null;

        // This statement ensures the window size entered on the console is either greater than 3 and is an odd number
        if(windowWidth>=3&&windowWidth%2!=0){
            try{
                // Read in the images paths
                file = new File("image/"+inputImageName); 
                file2 = new File("image/"+outputImageName);
                
                // Use the BufferedImage class to read the images stored in the files paths
                originalImage = ImageIO.read(file);
                filteredImage = ImageIO.read(file2);

                // Get the both the height and width of the image to be filtered
                int width1 = originalImage.getWidth()-windowWidth;
                int height1 = originalImage.getHeight()-windowWidth;
                
                // Use this value to position the co-ordinates of the median pixel to be set on the image to filtered
                int sub = windowWidth/2;

                // Initialize pixel variable to zero
                int pixel1 = 0;
               
                // Initializes each median of a pixel colour variables to zero for each sliding-window
                int midRed = 0;
                int midGreen = 0;
                int midBlue = 0;

                // Initializes the row and column of the sliding-window to the top left corner which is co-ordinate (0,0) and the
                // bottom eqaul to the shift of the window width for the left and right co-ordinate
                int row = 0;
                int col = 0;
                int row2 = windowWidth;
                int col2 = windowWidth;


                List<Integer> arrRed = new ArrayList<>();
                List<Integer> arrGreen = new ArrayList<>();
                List<Integer> arrBlue = new ArrayList<>();

                // The while loop set as a bench mark to loop across the height of the image until the variable row is equal or greater than the set end of the image height to be processed
                while(row2<=height1){

                // The two loops are used to slide the window on the image horizontal and vetically 
                // And from each window add the individual components of the image to an array list
                for(int i=row;i<row2;i++){
                    for(int j=col;j<col2;j++){
                        pixel1 = originalImage.getRGB(j,i);
                        color = new Color(pixel1);
                        arrRed.add(color.getRed());
                        arrGreen.add(color.getGreen());
                        arrBlue.add(color.getBlue());
                    }
                }

                // Sort the values within the different arraylists 
                Collections.sort(arrRed);
                Collections.sort(arrGreen);
                Collections.sort(arrBlue);

                // Calculate the the median value position from the array list
                int minR = arrRed.size()/2;
                int minB = arrBlue.size()/2;
                int minG = arrGreen.size()/2;

                // Checks to see if the the size of the array is even or not and then get the median value from the array
                // for all the 3 colours
                if(arrRed.size()%2!=0){
                    midRed = arrRed.get(minR);
                }
                else{
                    midRed = (arrRed.get(minR-1)+arrRed.get(minR))/2;
                }

                if(arrGreen.size()%2!=0){
                    midGreen = arrGreen.get(minG);
                }
                else{
                    midGreen = (arrGreen.get(minG-1)+arrGreen.get(minG))/2;
                }

                if(arrBlue.size()%2!=0){
                    midBlue = arrBlue.get(minB);
                }
                else{
                    midBlue = (arrBlue.get(minB-1)+arrBlue.get(minB))/2;
                }

                newColor = new Color(midRed, midGreen, midBlue);
                
                // Put the colours in the constructor of Color to late produce a pixel from it
                filteredImage.setRGB(col2-sub, row2-sub, newColor.getRGB());
                
                // Clears the array list to use it for the next loop
                arrRed.clear();
                arrGreen.clear();
                arrBlue.clear();
                
                // I use this to determine when the window can move one unit down the image and thus increment the row and row2 to continue 
                // processing the image. When the colomn chech value has not reached the edege of the width of the image the window contimue processing it 
                // across. Else when the edge is reached the window goes down then across again and repeat the process until the bottom 
                // edege of the image is reached 
                if(col2>width1){
                col = 0; col2 = windowWidth;
                row++; row2++;
                }else{
                    col++; col2++;
                }
            }
            // Creates the output image path with extension of jpg and writes the filtered image result to the new image created
            File outputFile = new File("image/output.jpg");
            ImageIO.write(filteredImage, "jpg", outputFile);

            // call the toc method to return the time taken to execute the program and store the value on the variable time
            float time = toc();

            System.out.println("Median filter run time using serial programming in seconds is: "+ time +" on an image with height "+height1+" and width of "+width1+ " using a "+windowWidth+"x"+windowWidth+" size");
                
            }
            catch(IOException e){
                // Gracefully informs the programmer the error when the image cannot be processed
                System.out.println("Error: "+e);
            }

        }else{
            // Informs the user the accepted window width size
            System.out.println("The window width must be an odd, positive integer >=3.");
        }
    
    }
}