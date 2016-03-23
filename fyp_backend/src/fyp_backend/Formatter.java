/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fyp_backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author nach
 */
public class Formatter {
    
    boolean extract=false;
    
    public void master() throws FileNotFoundException
    {
        Formatter obj_format=new Formatter();
        //Scanner scan_class1 = new Scanner(new File("positive.review"));//V good
        Scanner scan_class1 = new Scanner(new FileInputStream(new File("positive_dataset.txt")));
        PrintWriter out = new PrintWriter(new FileOutputStream(new File("positive_formatted.txt"), false ));
       
         while(scan_class1.hasNextLine())
       {
           String review=scan_class1.nextLine();
           System.out.println(review);
            review=obj_format.format(review);
           //System.out.println(review);
           if(review!=null && !review.equals("")&&!review.equals(" "))
           {
              // System.out.println(review);
               out.println(review);
           }
       }
         out.close();
       
    }
    public String format(String input)
   {
       
       
       String output=null;
       
       String pattern1 = "<review_text>";
        String pattern2 = "</review_text>";
        if (input.equals(pattern1)||input.equals(pattern2))
        {
            
            extract=!extract;
            return output;
        }
       

        if(extract)
        {
            return input;
        }
       
       return output;
   }
    
}
