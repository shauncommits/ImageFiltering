import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MeanFilterParallel{ 
public static void main(String[] args) {
    ForkJoinPool pool = new ForkJoinPool();
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
        Runtime run = Runtime.getRuntime();
        BufferedImage originalImg = null;
        BufferedImage filteredImg = null;
        File fil  = null;
        File fil2 = null;
        fil = new File("image/"+inputImageName); 
            fil2 = new File("image/"+outputImageName);
            try{
            originalImg = ImageIO.read(fil);
           
            }
            catch(IOException e){

            }
            int width1 = originalImg.getWidth()-windowWidth;
            int height1 = originalImg.getHeight();
            int cut = height1/Runtime.getRuntime().availableProcessors()-1;
                      
                     Mean obj = new Mean(windowWidth, width1,height1, 0, height1, inputImageName, outputImageName,cut,originalImg,filteredImg);
                     try {
                        obj.filteredImage = ImageIO.read(fil2);
                        obj.originalImage = ImageIO.read(fil);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } 
                     pool.invoke(obj);
                      obj.lastCompute();
    System.out.println("Here is the number of available processors "+run.availableProcessors());
        

    }else{
        System.out.println("The window width must be an odd, positive integer >=3.");
    }
    


}

}
   class Mean extends RecursiveAction{
    
    File outputFile = new File("image/output.jpg");
    int winWidth;
    int heightStart;
    int heightEnd;
    int height;
    int widt;
    String file1;
    String file2;
    BufferedImage filteredImage;
    BufferedImage originalImage;
    int cutPoint;

    Mean(int width, int w, int h, int hStart, int hEnd, String f1, String f2, int cut, BufferedImage ori,BufferedImage fil){
        winWidth = width;
        heightStart = hStart;
        heightEnd = hEnd;
        widt = w;
        file1 = f1;
        file2 = f2;
        originalImage = ori;
        filteredImage = fil;
        cutPoint = cut;
    }

    protected void compute(){
        
        if(heightEnd-heightStart<cutPoint){
        
        Color color = null;
        Color newColor = null;
        int wProduct = winWidth*winWidth;
        int sub = winWidth/2;

        int pixel1 = 0;
        int sumRed = 0;
            int sumGreen = 0;
            int sumBlue = 0;

            int row = heightStart;
            int col = 0;
            int row2 = heightStart + winWidth;
            int col2 = winWidth;

            System.out.println("HeiStart "+heightStart + " HeiEnd "+heightEnd);
                
                while(row2<=heightEnd){
                for(int i=row;i<row2;i++){
                    for(int j=col;j<col2;j++){
                        pixel1 = originalImage.getRGB(j,i);
                        
                        color = new Color(pixel1);
                        sumRed = sumRed + color.getRed();
                        sumGreen = sumGreen + color.getGreen();
                        sumBlue = sumBlue + color.getBlue();
                    }
                }
                
                newColor = new Color(sumRed/wProduct, sumGreen/wProduct, sumBlue/wProduct);
                
                filteredImage.setRGB(col2-sub, row2-sub, newColor.getRGB());
                sumBlue = 0;
                sumGreen = 0;
                sumRed = 0;
                
                
                if(col2>widt){
                col = 0; col2 = winWidth;
                row++; row2++;
                }else{
                    col++; col2++;
                }
            
        
                }
        }else{
            int split = (heightStart+heightEnd)/2;
            Mean left = new Mean(winWidth, widt, height, heightStart, split,file1, file2,cutPoint,originalImage,filteredImage);
            Mean right = new Mean(winWidth, widt, height, split, heightEnd,file1, file2,cutPoint,originalImage,filteredImage);
            left.fork();
            right.compute();
            left.join();
        }
       
    }
    public void lastCompute(){
        try{
            ImageIO.write(filteredImage, "jpg", outputFile);}catch(IOException e){}
                
    }
}
    
    

