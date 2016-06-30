/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fyp_backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static javax.xml.bind.DatatypeConverter.parseFloat;

/**
 *
 * @author nach
 */
public class Test {
    public static void main_test() throws FileNotFoundException
    {
         Scanner class_scan = new Scanner(new File("class.txt"));
                String  feature;
                Float nb_value;
                Float instances;
                
        Float total_nb=(float)0;
        Float total_instances=(float)0;
        
        Float total_nb_neg=(float)0;
        Float total_instances_neg=(float)0;
        
        Float total_nb_pos=(float)0;
        Float total_instances_pos=(float)0;
        
        while (class_scan.hasNextLine())
        {
            String line=class_scan.nextLine();
            System.out.println(line);
            Scanner scan=new Scanner(line);
            
            scan.useDelimiter(",");
            feature=scan.next();
            nb_value=parseFloat(scan.next());
            instances=parseFloat(scan.next());
            
            if(nb_value!=(float)1 || nb_value!=(float)0)
            {
            total_nb+=instances*nb_value;
            total_instances+=instances;
            }
            
            if( nb_value>(float)0.5)
            {
            total_nb_pos+=instances*nb_value;
            total_instances_pos+=instances;
            }
            
            if( nb_value<(float)0.5)
            {
            total_nb_neg+=instances*nb_value;
            total_instances_neg+=instances;
            }
            
        }
        
        
        System.out.println("Biased:"+(total_nb/total_instances));
        
        System.out.println("Biased Positive:"+(total_nb_pos/total_instances_pos));
        
        System.out.println("Biased Negative:"+(total_nb_neg/total_instances_neg));
        
        
    }
}
