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
import static java.lang.Float.parseFloat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author nach
 */
public class Sort {
    public static void main_sort() throws FileNotFoundException
    {
        HashMap<String, Float> hm_sort = new HashMap <String, Float>();
        Scanner scan = new Scanner(new FileInputStream("C:\\Users\\nach\\Documents\\NetBeansProjects\\feature.txt"));
        PrintWriter out = new PrintWriter(new FileOutputStream(new File("C:\\Users\\nach\\Documents\\NetBeansProjects\\feature_sort.txt") ));       
        String feature;
        Float nb;
        while(scan.hasNext())
        {
            Scanner scan_line=new Scanner(scan.nextLine());
            scan_line.useDelimiter(",");
            feature=scan_line.next();
            nb=parseFloat(scan_line.next());
            hm_sort.put(feature,nb); 
            
        }
        int size=hm_sort.size();
        System.out.println(size);
        while(size>0)
        {
            Float lowest_value=(float)1;
            String lowest_key="";
            for(Map.Entry<String, Float> e : hm_sort.entrySet())
            {  
            
                feature =e.getKey();
                nb=e.getValue();
                
                if(nb<lowest_value&&!e.getValue().equals("NaN"))
                {
                   lowest_value=nb;
                   lowest_key=feature;

                }        
            }
            System.out.println(lowest_key+","+lowest_value);
            out.println(lowest_key+","+lowest_value);
            hm_sort.remove(lowest_key);
            size=size-1;
        }
        scan.close();
        out.close();
    }
}
