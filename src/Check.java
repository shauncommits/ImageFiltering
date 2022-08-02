import java.io.*;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class Check {
    public static void main(String[] args) {


        int[][] num = new int[4][4];
        num[0][0] = 1;
        num[0][1] = 2;
        num[0][2] = 3;
        num[0][3] = 4;
        num[1][0] = 5;
        num[1][1] = 6;
        num[1][2] = 7;
        num[1][3] = 8;
        num[2][0] = 9;
        num[2][1] = 10;
        num[2][2] = 11;
        num[2][3] = 12;
        num[3][0] = 13;
        num[3][1] = 14;
        num[3][2] = 15;
        num[3][3] = 16;
        int sum1 = 0;
        int r = 0;
        int r2 = 3;
        int c = 0;
        int c22 = 3;
        while(r2<5){
        for(int i = r;i<r2;i++){
            for(int j=c;j<c22;j++){
                System.out.println("i = "+i+" j = "+j + " from "+num[i][j]);
                sum1 += num[i][j];
            }  
        }
            System.out.println("sum "+sum1);
            System.out.println("Avg "+sum1/9);
            System.out.println("Pix "+r2/2+" "+c22/2);
            sum1 = 0;
            
            System.out.println("c22 "+c22);
            System.out.println("r2 "+r2);

            if(c22>3){
                System.out.println("True");
            c = 0;c22 = 3;
            r++;
            r2++;
            }else{
                c++;
            c22++;
            }
            
            
    }        
    }
    
}
