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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author nach
 */
public class Result {
    
    public void overall() throws FileNotFoundException
    {
        Scanner scan = new Scanner(new FileInputStream("tuple.txt"));
        
        int total_n=0;
        Float total=null;
        String nb="";
        
        while(scan.hasNextLine())
        {
            
            Scanner scan_split=new Scanner(scan.nextLine());
            scan_split.useDelimiter(";");
            
            while(scan_split.hasNext())
            {
                nb=scan_split.next();
                
            }
            if(!nb.equals("NaN"))
            {   
                Float tmp=Float.valueOf(nb);
                total_n++;
                if(total==null)
                {
                    total=tmp;
                }
                else{
                total=(float)total+(float)tmp;
                }
                //System.out.println(total+ " "+total_n);
                
            }
            

            //System.out.println(total+ "  "+ total_n);
            
            /*
            String unwanted=scan_split.next();System.out.println(",,"+unwanted);
            unwanted=scan_split.next();System.out.println(unwanted);
            unwanted=scan_split.next();System.out.println(unwanted);
            String nb=scan_split.next();
            
            Scanner scan_nb=new Scanner(nb);
            scan_nb.useDelimiter(" ");
            while(scan_nb.hasNext())
            {
                String temp=scan.next();
                if(!temp.equals("null"))
                {
                    total+=Float.valueOf(temp);
                    total_n++;
                }
            }
            
            */
            
        }
        
        PrintWriter out = new PrintWriter(new FileOutputStream(new File("C:\\Users\\nach\\Documents\\NetBeansProjects\\overall.txt"), false ));       
        
        Float value=total/total_n;
        out.print(value);
               
        
        scan.close();
        out.close();
    }
    
    public void feature() throws FileNotFoundException
    {
        Scanner scan = new Scanner(new FileInputStream("tuple.txt"));
        
        
        HashMap <String, Float> hm_feature=new HashMap <String, Float>();
        HashMap <String, Integer> hm_occurences=new HashMap <String, Integer>();

        while(scan.hasNextLine())
        {          
            Scanner scan_split=new Scanner(scan.nextLine());
            scan_split.useDelimiter(";");
            
            //String unwanted=scan_split.next();System.out.println("UNWANTED"+unwanted);
            String feature=scan_split.next();System.out.println("UNWANTED"+feature);
            String description=scan_split.next();System.out.println("UNWANTED"+description);
            String unwanted=scan_split.next();System.out.println("UNWANTED"+unwanted);
            String nb_total=scan_split.next();System.out.println("UNWANTED"+nb_total);
            
            Scanner scan_feature=new Scanner(feature);
            scan_feature.useDelimiter(" ");
            if(!(String.valueOf(Float.valueOf(nb_total))).equalsIgnoreCase("nan"))
                while(scan_feature.hasNext())
                {

                    String temp=scan_feature.next();System.out.println(temp+" "+nb_total);
                    if(hm_feature.get(temp)!=null)
                    {
                        hm_feature.put(temp,hm_feature.get(temp)+Float.valueOf(nb_total));
                        hm_occurences.put(temp,hm_occurences.get(temp)+1);
                    }
                    else
                    {
                        hm_feature.put(temp,Float.valueOf(nb_total));
                        hm_occurences.put(temp,1);
                    }
                }        
            
        }       
        
        
        PrintWriter out = new PrintWriter(new FileOutputStream(new File("C:\\Users\\nach\\Documents\\NetBeansProjects\\feature.txt"), false ));       
        
        for(Map.Entry<String, Float> e : hm_feature.entrySet())
        {  
            
             int value=hm_occurences.get(e.getKey());
             Float output=e.getValue()/value;
             
             out.println(e.getKey()+","+output);
             
        }
        
        
        scan.close();
        out.close();
    }
    
}
