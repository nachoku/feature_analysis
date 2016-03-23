/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fyp_backend;

import com.softcorporation.suggester.util.SuggesterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author nach
 */
public class TrainNaiveBayes {
    HashMap <String , Integer> hm_class1 = null;
    HashMap <String , Integer> hm_class2 = null;

    boolean extract=false;
    
    
    public void mains() throws FileNotFoundException, SuggesterException, IOException
    {
        Tuple obj_tuple=new Tuple();
        Splitter obj_split=new Splitter();
        TrainNaiveBayes obj_train=new TrainNaiveBayes();
        Elements obj_elements=new Elements();
        Stemmer obj_stem=new Stemmer();
        
       hm_class1=new HashMap <String, Integer>();
       hm_class2=new HashMap <String, Integer>();

       
       String review, words[], text[], type[],temp;
       int value;
       Scanner scan=null;
       System.out.println("Training...");
       
       Scanner scan_class1 = new Scanner(new FileInputStream("dataset.txt"));//V good
 
       PrintWriter out = new PrintWriter(new FileOutputStream(new File("class.txt"), false ));
       
       
       
       
       int j=0;
       
       while(scan_class1.hasNextLine())
       {
           
           System.out.println(++j);
           
           review=scan_class1.nextLine();
          
                review=review.toLowerCase();

          
                if (review!=null)
                {
   
                    char clas[]=review.toCharArray();
                    //review=review.replace(".", "");
                    //review=review.replace("!","");

                    review=obj_tuple.tag(review);    System.out.println(review);
                    review=review.replace(".", "");
                    review=review.replace("!","");

                    words=obj_split.main_word(review);
                    text=obj_train.text(words);
                    type=obj_train.type(words);
                    review=obj_elements.description(text, type);

                    System.out.println("__"+review+"___");
                    scan=new Scanner(review);

                    scan.useDelimiter(" ");






                    if (Integer.parseInt(String.valueOf(clas[clas.length-1]))==1)
                    {

                        System.out.println("Pos");

                         while(scan.hasNext())
                         {

                             temp=scan.next();
                             char array[]=temp.toCharArray();
                             if(array[0]=='|' && array[1]=='N' && array[2]=='|') 
                             {


                                 temp="";
                                 for(int i=3;i<array.length;i++)
                                 {
                                     if(array[i]!=0)
                                     {
                                        temp+=array[i];

                                     }
                                 }
                                 value=0;

                                 temp=obj_stem.main_stem(temp);
                                 if(hm_class2.get(temp)!=null)
                                 {

                                     hm_class2.put(temp, hm_class2.get(temp)+1);
                                 }    
                                 else
                                 {   
                                     hm_class2.put(temp, 1);
                                 }

                             }
                             else
                             {
                                 value=0;
                                 temp=obj_stem.main_stem(temp);
                                 if(hm_class1.get(temp)!=null)

                                 {

                                     hm_class1.put(temp, hm_class1.get(temp)+1);
                                 }    
                                 else
                                 {   
                                     hm_class1.put(temp, 1);
                                 }
                             }
                         }           


                    }


                    else
                    {
                        System.out.println("Neg");
                        while(scan.hasNext())
                         {

                             temp=scan.next();
                             char array[]=temp.toCharArray();
                             if(array[0]=='|' && array[1]=='N' && array[2]=='|') 
                             {
                                 temp="";
                                 for(int i=3;i<array.length;i++)
                                 {
                                     temp+=array[i];
                                 }
                                 value=0;
                                 temp=obj_stem.main_stem(temp);
                                 if(hm_class1.get(temp)!=null)

                                 {

                                     hm_class1.put(temp, hm_class1.get(temp)+1);
                                 }    
                                 else
                                 {   
                                     hm_class1.put(temp, 1);
                                 }

                             }
                             else
                             {
                                 value=0;
                                 temp=obj_stem.main_stem(temp);
                                 if(hm_class2.get(temp)!=null)
                                // {
                                //     value=hm_class2.get(temp);
                               //  }
                              //   if(value!=0)
                                 {
                                     hm_class2.put(temp, hm_class2.get(temp)+1);
                                 }    
                                 else
                                 {   
                                     hm_class2.put(temp, 1);
                                 }
                             }
                         }                 

                    }
                }
       }
       
       
       System.out.println("PRE>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
       
       for (Map.Entry<String, Integer> e : hm_class2.entrySet()) 
       {
           System.out.println(e.getKey()+":"+e.getValue());
       }
         
       //POSTIVE AND PARTIAL
       
       for (Map.Entry<String, Integer> e : hm_class1.entrySet()) 
       {
            String key1 = e.getKey();
            int value1=e.getValue();
            //System.out.println("HERE"+ key);
            Integer value2=hm_class2.get(key1);
            if(value2!=null)
            {
                
                float master_value=(float)value1/(value1+value2);
//                 hm_master.put(key1, master_value);
                float total_occurences=value1+value2;
                 out.println(key1+","+master_value+","+total_occurences);
                 
                 int rem=hm_class2.remove(key1);
                 System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+rem);
                 
                 
             }
            else
            {
                //hm_master.put(key1, (float)1);
                out.println(key1+","+1+","+value1);
            }
            
          
            
            
        }

       
       for (Map.Entry<String, Integer> e : hm_class2.entrySet()) 
       {
           out.println(e.getKey()+","+0+","+e.getValue());
       }
           
       
     
       
       
       scan_class1.close();
       //scan_class2.close();
       out.close();
       
       
       
       System.out.println("Done...");
    }
    
    
    
    
    
   public String[] text (String words[]) throws SuggesterException, FileNotFoundException, IOException
    {

        Tuple obj_tuple=new Tuple();
        Corrector main_corrector=new Corrector();
        
        int i,k;
        String tmp[]=new String [300]; 
        

        String type, word;
        i=0;
        String type_array[]=new String[3000], word_array[]=new String[3000];
        while(words[i]!=null)
        {   
            
            char tmpx[]=words[i].toCharArray();//I_PRP
            k=0;
            type="";
            word="";
                  
            while (tmpx.length>k)
            {
                if(tmpx[k]=='_' && (tmpx.length-k)<5)
                {   
                    k++;
                    while(k<tmpx.length)
                    {
                        type+=tmpx[k];
                        k++;
                    }
                    break;
                }
                word+=tmpx[k];                   
                k++;
            }
            word_array[i]=word;
            type_array[i]=type;
                  //System.out.print(word_array[j]);
                 // System.out.println(type_array[j]);
              
                  
                  i++;
         }
      

        return word_array;
    }
   
   
   public String[] type (String words[]) throws SuggesterException, FileNotFoundException, IOException
    {

        Tuple obj_tuple=new Tuple();

        
        int i,k;
        String tmp[]=new String [300]; 
        

        String type, word;
        i=0;
        String type_array[]=new String[3000], word_array[]=new String[3000];
        while(words[i]!=null)
        {          
            char tmpx[]=words[i].toCharArray();//I_PRP
            k=0;
            type="";
            word="";
                  
            while (tmpx.length>k)
            {
                if(tmpx[k]=='_' && (tmpx.length-k)<5)
                {   
                    k++;
                    while(k<tmpx.length)
                    {
                        type+=tmpx[k];
                        k++;
                    }
                    break;
                }
                word+=tmpx[k];                   
                k++;
            }
            word_array[i]=word;
            type_array[i]=type;
                  //System.out.print(word_array[j]);
                 // System.out.println(type_array[j]);
              
                  
                  i++;
         }
              
          
        

        

        return type_array;
    }
   
   
   
  
    
}
