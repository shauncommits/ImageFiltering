import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
/**
 * MedianFilterParallel class applys median filer on an image using Fork/Join Framework
 * @author Shaun Mbolompo 2022/08/06
 * 
 */

public class MedianFilterParallel{ 

/**
 * Drive class to run other classes
 * @param args receives arguments from the terminal and for the this task it takes in three arguments
 */
public static void main(String[] args) {

    // The ForkJoinPool to fork a pool of threads to run the lists of tasks using the available processes
    ForkJoinPool pool = new ForkJoinPool();

    // Initilize the images name by default to empty string to later use the variable in the code 
    String inputImageName = "";
    String outputImageName = "";

    int windowWidth = 0;// intialize the window size
    try{
        // set the images variables and window size declared early on to the argument values
        inputImageName = args[0];
        outputImageName = args[1];
        windowWidth = Integer.parseInt(args[2]);

        
    }catch(IndexOutOfBoundsException e){ // An exception caught is gracefully by printing out on the console the sting below 
        System.out.println("Opps! Incorrect command line parameters");
        System.out.println("Your command line parameters must be in this order:");
        System.out.println("<inputImageName> <outputImageName> <windowWidth>");
    }
    
    // This statement ensures the window size entered on the console is either greater than 3 and is an odd number
    if(windowWidth>=3&&windowWidth%2!=0){

        Runtime run = Runtime.getRuntime();

        // Initialize the images through the Buffered class to null
        BufferedImage originalImg = null;
        BufferedImage filteredImg = null;

        // Read in the images paths
        File fil  = new File("image/"+inputImageName);
        File fil2 = new File("image/"+outputImageName);

        try{ // Use the BufferedImage class to read the images stored in the files paths
            originalImg = ImageIO.read(fil);
            filteredImg = ImageIO.read(fil2);
            } 
            catch(IOException e){System.out.println("The image does not exist!");} // When the images are not found the message is made know to the user
        
        // Get the both the height and width of the image to be filtered
        int width1 = originalImg.getWidth()-windowWidth;
        int height1 = originalImg.getHeight();

        // Get the number of processes used by the computer and store them on the variable cut
        int cut = height1/Runtime.getRuntime().availableProcessors()-1;
              
        // Create an object of Median
        Median obj = new Median(windowWidth, width1,height1, 0, height1, cut, originalImg, filteredImg);
    
        // Use the object to run the tasks throught the pool of threads
        pool.invoke(obj);
        
        // Once the threads are finish filtering the image the we call the method last compute to write the results to the image called output
        obj.lastCompute();

        
    
    }else{
        System.out.println("The window width must be an odd, positive integer >=3."); // Alert the user of the window size accepted
    }
    }
}
    /**
     * A RecursiveAction class to break tasks into smaller task and compute the tasks in parallel
     */
   class Median extends RecursiveAction{
    
    // Attributes of the class
    File outputFile = new File("image/output.jpg");
    int winWidth;
    int heightStart;
    int heightEnd;
    int height;
    int widt;
    BufferedImage filteredImage;
    BufferedImage originalImage;
    int cutPoint;
    int halfWidth = winWidth/2;

    /**
     * The class Constructor to initialize the attributes of the class
     * @param width is the width of the window used to get the pixel components
     * @param w is the actual width of the image being processed for filtering
     * @param h is the actual height of the image to be processed
     * @param hStart depics the the start of the image  height to be processed
     * @param hEnd depics the end of the image height to be processed
     * @param cut is the cut off value that determines the leght of the difference of the start and end length to be processed
     * @param ori is the origial image 
     * @param fil the image used to produce a filter from
     */
    Median(int width, int w, int h, int hStart, int hEnd, int cut, BufferedImage ori,BufferedImage fil){
        winWidth = width;
        heightStart = hStart;
        heightEnd = hEnd;
        widt = w;
        originalImage = ori;
        filteredImage = fil;
        cutPoint = cut;
    }
    /**
     * Compute method to run the tasks
     */
    protected void compute(){
        
        // Checks the cuff off value for the processing of the image to take place
        if(heightEnd-heightStart<cutPoint){
        
        // Initialize the variable of type Color to null to be used to both extract and put colors from the original image to the image used to produce a filter
        Color color = null;
        Color newColor = null;

        // Use this value to position the co-ordinates of the median pixel to be set on the image to filtered
        int sub = winWidth/2;

        // Initialize pixel and its colours to zero
        int pixel1 = 0;
        int midRed = 0;
        int midGreen = 0;
        int midBlue = 0;

        // Start the row of the image to be processed at the given height and keep both the width and height of the window to the window size whenever it moves across the image
        int row = heightStart;
        int col = 0;
        int row2 = heightStart + winWidth;
        int col2 = winWidth;

        // Create the lists for all the number of the color components to be use to calculate the median color 
        List<Integer> arrRed = new ArrayList<>();
        List<Integer> arrGreen = new ArrayList<>();
        List<Integer> arrBlue = new ArrayList<>();

        // The while loop set as a bench mark to loop across the height of the image until the variable row is equal or greater than the set end of the image height to be processed
                
        while(row2<=heightEnd){

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
                
            // Put the colours in the constructor of Color to late produce a pixel from it
            newColor = new Color(midRed, midGreen, midBlue);
                
            // set the pixel calculated to the co-ordinates of the window center across the image
            filteredImage.setRGB(col2-sub, row2-sub, newColor.getRGB());

            // Clears the array list to use it for the next loop
            arrRed.clear();
            arrGreen.clear();
            arrBlue.clear();
            
            // I use this to determine when the window can move one unit down the image and thus increment the row and row2 to continue 
            // processing the image. When the colomn chech value has not reached the edege of the width of the image the window contimue processing it 
            // across. Else when the edge is reached the window goes down then across again and repeat the process until the bottom 
            // edege of the image is reached 
            if(col2>widt){
                col = 0; col2 = winWidth;
                row++; row2++;
            }else{
                col++; col2++;
            }  
            
        // When the cut off is not yet reached the program divide the tasks to smaller tasks to be processed. The divide and conqueror algorithm
        }
        }else{
            int split = (heightStart+heightEnd)/2;
            Median left = new Median(winWidth, widt, height, heightStart, split,cutPoint,originalImage,filteredImage);
            Median right = new Median(winWidth, widt, height, split, heightEnd,cutPoint,originalImage,filteredImage);
            left.fork();
            right.compute();
            left.join();
        }   
    }

    public void lastCompute(){
        try{
            ImageIO.write(filteredImage, "jpg", outputFile);}catch(IOException e){System.out.println("The image could not be generated!");}       
    }
}