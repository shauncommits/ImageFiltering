import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import javax.imageio.ImageIO;

public class MedianFilterSerial{

    /**
     * Drive class to run other classes
     * @param args receives arguments from the terminal and for the this task it takes in three arguments
     */
    public static void main(String[] args) {
        String inputImageName = "";
        String outputImageName = "";
        int windowWidth = 0;

        try{
            inputImageName = args[0];
            outputImageName = args[1];
            windowWidth = Integer.parseInt(args[2]);
            
        }catch(IndexOutOfBoundsException e){
            System.out.println("Opps! Incorrect command line, use the following format:");
            System.out.println("run-<fileName> ARGS(<inputImageName> <outputImageName> <windowWidth>)");
            e.printStackTrace();
        }
        BufferedImage originalImage = null;
        BufferedImage filteredImage = null;
        Color color = null;
        Color newColor = null;
        File file  = null;
        File file2 = null;
        if(windowWidth>=3&&windowWidth%2!=0){
            try{
                file = new File("image/"+inputImageName); 
                file2 = new File("image/"+outputImageName);
                
                originalImage = ImageIO.read(file);
                filteredImage = ImageIO.read(file2);

                int width1 = originalImage.getWidth()-windowWidth;
                int height1 = originalImage.getHeight()-windowWidth;
                int sub = windowWidth/2;
                int pixel1 = 0;
               
                int midRed = 0;
                int midGreen = 0;
                int midBlue = 0;

                int row = 0;
                int col = 0;
                int row2 = windowWidth;
                int col2 = windowWidth;
                List<Integer> arrRed = new ArrayList<>();
                List<Integer> arrGreen = new ArrayList<>();
                List<Integer> arrBlue = new ArrayList<>();

                while(row2<=height1){
                for(int i=row;i<row2;i++){
                    for(int j=col;j<col2;j++){
                        pixel1 = originalImage.getRGB(j,i);
                        color = new Color(pixel1);
                        arrRed.add(color.getRed());
                        arrGreen.add(color.getGreen());
                        arrBlue.add(color.getBlue());
                    }
                }
                Collections.sort(arrRed);
                Collections.sort(arrGreen);
                Collections.sort(arrBlue);

                int minR = arrRed.size()/2;
                int minB = arrBlue.size()/2;
                int minG = arrGreen.size()/2;

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
                
                filteredImage.setRGB(col2-sub, row2-sub, newColor.getRGB());
                arrRed.clear();
                arrGreen.clear();
                arrBlue.clear();
                
                
                if(col2>width1){
                col = 0; col2 = windowWidth;
                row++; row2++;
                }else{
                    col++; col2++;
                }
            }
            File outputFile = new File("image/output.jpg");
            ImageIO.write(filteredImage, "jpg", outputFile);
                
            }
            catch(IOException e){
                System.out.println("Error: "+e);
            }

        }else{
            System.out.println("The window width must be an odd, positive integer >=3.");
        }
    
    }
}