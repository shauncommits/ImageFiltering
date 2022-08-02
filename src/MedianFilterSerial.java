import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MedianFilterSerial{
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
        File file  = null;
        File file2 = null;
        if(windowWidth>=3&&windowWidth%2!=0){
            try{
                file = new File("image/"+inputImageName); ///home/shaun/AssignmentPCP1/image/img1.jpg
                file2 = new File("image/"+outputImageName);
                
                originalImage = ImageIO.read(file);
                int width1 = originalImage.getWidth();
                int height1 = originalImage.getHeight();
                int pixel1 = 0;

                filteredImage = ImageIO.read(file2);
                int width2 = filteredImage.getWidth();
                int height2 = filteredImage.getHeight();
                int pixel2 = 0;
                
                int count = 0;

                HashMap<Integer,ArrayList<Integer>> map = new HashMap<>();
                ArrayList<Integer> list = new ArrayList<>();
                int row = 0;
                int col = 0;
                int row2 = windowWidth;
                int col2 = windowWidth;

                while(row<height1){
                for(int i=row;i<row2;i++){
                    for(int j=col;j<col2;j++){
                        pixel1 = originalImage.getRGB(i, j);
                        //list.add(pixel1);
                    }
                    map.put(i, list);
                }
                if(col2>windowWidth){
                col = 0; col2 = windowWidth;
                row++; row2++;
                }else{
                    col++; col2++;
                }
            }
                
            }
            catch(IOException e){
                System.out.println("Error: "+e);
            }

        }else{
            System.out.println("The window width must be an odd, positive integer >=3.");
        }
    
    }
}