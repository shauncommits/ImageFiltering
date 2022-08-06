import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class RecursiveActionDemo {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        BufferedImage image = null;
        File file = new File("/home/shaun/AssignmentPCP1/src/img1.jpg");
        try{
             image = ImageIO.read(file);
            
        }catch(IOException e){

        }
        
        Square app = new Square(image, 0,image.getHeight(),image.getWidth());
        pool.invoke(app);
        System.out.println(app.count);
        //System.out.println(app.result);
    }
}   
class Square extends RecursiveAction {
       final int LIMIT = 58;
       //keep static
       int w;
       int start, end;
       static int count = 0;
       BufferedImage image;
       
       Square(BufferedImage img, int start, int end, int width) {
           this.start = start;
           this.end = end;
           w = width;
           image = img;
       }
       @Override
       protected void compute() {
           if((end - start)< LIMIT){
            count++;
            //    for(int i= start;i<end;i++){
            //        result+= data[i]*data[i];   
            //    }
           }else {
             int mid = (start + end)/2;
             Square left = new Square(image, start, mid,w); 
             Square right = new Square(image, mid, end,w);
             left.fork();
             right.fork();
             left.join();
             right.join();
           }
       }
     }