import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * MeanFilterParallel class applies filer on an image using Fork/Join Framework, i.e. Parallel programming with concurrency
 * using Divide and Conqueror approach
 * @author Shaun Mbolompo 2022/08/06
 * 
 */


public class MeanFilterParallel{ 


    // Declare the variable static so it can be used by the static methods and also that it may be for used by all classes of MeanFilterParallel without 
    // will be the same for the instances of the class, meaning can be shared by all the instances of the class declared in
    static long startTime = 0; 

    /**
    * Method to start the timing of how low the system takes to do the mean filter using parallelism and concurrency
    * it initializes the static variable startTime when called
    */
    private static void tick(){
         startTime = System.currentTimeMillis();
    }
 
    /**
    * Method toc to return the time taken by the program to execute
    * @return the time it takes the program using the available processors to apply the mean filter on an image
    */
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
             
       // Create an object of Mean
       Mean obj = new Mean(windowWidth, width1,height1, 0, height1, cut, originalImg, filteredImg);
   
       // Use the object to run the tasks throught the pool of threads
       pool.invoke(obj);
       
       // Once the threads are finish filtering the image the we call the method last compute to write the results to the image called output
       obj.lastCompute();

       // call the toc method to return the time taken to execute the program and store the value on the variable time
       float time = toc();

       System.out.println("Mean filter run time using fork/join framework in seconds is: "+ time +" on an image with height "+height1+" and width of "+width1);
   
   }else{
       System.out.println("The window width must be an odd, positive integer >=3."); // Alert the user of the window size accepted
   }
   }
}
   /**
    * A RecursiveAction class to break tasks into smaller task and compute the tasks in parallel
    */
   class Mean extends RecursiveAction{
    
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
    Mean(int width, int w, int h, int hStart, int hEnd, int cut, BufferedImage ori,BufferedImage fil){
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

        // Get the number of pixels in a sliding-window
        int wProduct = winWidth*winWidth;

        // Use this value to position the co-ordinates of the median pixel to be set on the image to filtered
        int sub = winWidth/2;
        
        // Initialize pixel and the sum of the component number colours to zero
        int pixel1 = 0;
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;

        // Start the row of the image to be processed at the given height and keep both the width and height of the window to the window size whenever it moves across the image
        int row = heightStart;
        int col = 0;
        int row2 = heightStart + winWidth;
        int col2 = winWidth;

        // The while loop set as a bench mark to loop across the height of the image until the variable row is equal or greater than the set end of the image height to be processed
        while(row2<=heightEnd){

            // The two loops are used to slide the window on the image horizontal and vetically 
            // And from each window add sum individual number of the pixel components in order to late compute the average of each of those components
            for(int i=row;i<row2;i++){
                for(int j=col;j<col2;j++){
                    pixel1 = originalImage.getRGB(j,i);                    
                    color = new Color(pixel1);
                    sumRed = sumRed + color.getRed();
                    sumGreen = sumGreen + color.getGreen();
                    sumBlue = sumBlue + color.getBlue();
                }
            }
              
            // Put the number representing those colours in the constructor of Color to late produce a pixel from it 
            newColor = new Color(sumRed/wProduct, sumGreen/wProduct, sumBlue/wProduct);
             
            // set the pixel calculated to the co-ordinates of the window center across the image
            filteredImage.setRGB(col2-sub, row2-sub, newColor.getRGB());
            
            // resets the sums to zero for the next sliding window
            sumBlue = 0;
            sumGreen = 0;
            sumRed = 0;
                
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
            Mean left = new Mean(winWidth, widt, height, heightStart, split,cutPoint,originalImage,filteredImage);
            Mean right = new Mean(winWidth, widt, height, split, heightEnd,cutPoint,originalImage,filteredImage);
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
    
    

