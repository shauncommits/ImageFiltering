import java.io.*;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class Check {
    public static void main(String[] args) {

        int[][] num = new int[4][4];

        int count = 1;
        for(int i = 0; i< num.length;i++){
            for(int j=0;j<num[0].length;j++){
                num[i][j] = count;
                count++;
            }
        }

        int sum1 = 0, r = 0, r2 = 3, c = 0, c22 = 3;

        while(r2<5){
            for(int i = r;i<r2;i++){
                for(int j=c;j<c22;j++){
                    System.out.println("i = "+i+" j = "+j + " from "+num[i][j]);
                    sum1 += num[i][j];
                }  
            }
                System.out.println("Sum "+sum1);
                System.out.println("Avg "+sum1/9);
                System.out.println("Pixel position to be used to filter "+r2/2+" "+c22/2);
                sum1 = 0;
                
                System.out.println("c22 "+c22);
                System.out.println("r2 "+r2);
    
                if(c22>3){
                    System.out.println("True");
                    c = 0; c22 = 3; r++; r2++;
                }else{
                    c++; c22++;
                }     
        } 
  }
} 