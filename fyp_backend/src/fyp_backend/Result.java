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

public class Result {
    
    public void overall() throws FileNotFoundException
    {
        Scanner scan = new Scanner(new FileInputStream("tuple.txt"));
        
        
        HashMap <String, Float> hm_feature=new HashMap <String, Float>();
        HashMap <String, Integer> hm_occurences=new HashMap <String, Integer>();
        HashMap <String, String> hm_review=new HashMap <String, String>(); 
        while(scan.hasNextLine())
        {          
            Scanner scan_split=new Scanner(scan.nextLine());
            scan_split.useDelimiter(";");
            
            String review_no=scan_split.next();System.out.println("Review:"+review_no);
            String feature=scan_split.next();System.out.println("Feature:"+feature);
            String description=scan_split.next();System.out.println("Description:"+description);
            String unwanted=scan_split.next();System.out.println("Nb_scores:"+unwanted);
            String nb_total=scan_split.next();System.out.println("nb_total:"+nb_total);
           
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
                        hm_review.put(temp,review_no);
                        
                    }
                    else
                    {
                        hm_feature.put(temp,Float.valueOf(nb_total));
                        hm_occurences.put(temp,1);
                        hm_review.put(temp,hm_review.get(temp)+ " "+review_no);
                    }
                }        
            
        }       
        int total_no=0;
        Float total=(float)0;
        for(Map.Entry<String, Float> e : hm_feature.entrySet())
        {  
            
             int value=hm_occurences.get(e.getKey());
             //System.out.println(value);
             if(value>2&&!e.getValue().equals("NaN"))
             {
                total+=e.getValue();
                total_no+=value;
                //System.out.println(e.getValue()+":"+e.getKey());
             }        
        }
        
        
        PrintWriter out = new PrintWriter(new FileOutputStream(new File("C:\\Users\\nach\\Documents\\NetBeansProjects\\overall.txt"), false ));       
        
        Float value=total/total_no;
        System.out.println("Overall"+value);
        out.print(value);
               
        
        scan.close();
        out.close();
    }
    
    public void feature() throws FileNotFoundException
    {
        Scanner scan = new Scanner(new FileInputStream("tuple.txt"));
        
        
        HashMap <String, Float> hm_feature=new HashMap <String, Float>();
        HashMap <String, Integer> hm_occurences=new HashMap <String, Integer>();
        HashMap <String, String> hm_review=new HashMap <String, String>();
        while(scan.hasNextLine())
        {          
            Scanner scan_split=new Scanner(scan.nextLine());
            scan_split.useDelimiter(";");
            
            String review_no=scan_split.next();System.out.println("Review:"+review_no);
            String feature=scan_split.next();System.out.println("Feature:"+feature);
            String description=scan_split.next();System.out.println("Description:"+description);
            String unwanted=scan_split.next();System.out.println("NB_per:"+unwanted);
            String nb_total=scan_split.next();System.out.println("nb_total:"+nb_total);
           
            Scanner scan_feature=new Scanner(feature);
            scan_feature.useDelimiter(" ");
  
            if(!(String.valueOf(Float.valueOf(nb_total))).equalsIgnoreCase("nan"))
            {
               
                while(scan_feature.hasNext())
                {
                    
                    String temp=scan_feature.next();
                    if(hm_feature.get(temp)!=null)
                    {
                        hm_feature.put(temp,hm_feature.get(temp)+Float.valueOf(nb_total));
                        hm_occurences.put(temp,hm_occurences.get(temp)+1);
                        hm_review.put(temp,hm_review.get(temp)+" "+review_no);
                        System.out.println(">>>>>>>>>>>"+hm_review.get(temp));
                    }
                    else
                    {
                        hm_feature.put(temp,Float.valueOf(nb_total));
                        hm_occurences.put(temp,1);
                        hm_review.put(temp,review_no);
                        System.out.println(">>>>>>>>>>>"+hm_review.get(temp));
                    }
                }        
            }
        }       
        
        
        PrintWriter out = new PrintWriter(new FileOutputStream(new File("C:\\Users\\nach\\Documents\\NetBeansProjects\\feature.txt"), false ));       
      
        for(Map.Entry<String, Float> e : hm_feature.entrySet())
        {  
             int value=hm_occurences.get(e.getKey());
             if(value>2)
             {
                Float output=e.getValue()/value;       
                out.println(e.getKey()+","+output+","+value+","+hm_review.get(e.getKey()));
             }
        }
        
        
        scan.close();
        out.close();
    }
    
}