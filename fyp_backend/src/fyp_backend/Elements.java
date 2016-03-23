

package fyp_backend;

import com.softcorporation.suggester.util.SuggesterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author nach
 */
public class Elements 
{
    ArrayList<String> negative_list = null;
    ArrayList<String> booster_list = null;
    
    public String target(String[] text, String[] type)
    {
        String output="";
        int i=0;
        boolean flag=false;
        while(text[i]!=null)
        {
            if(text[i].equals("|"))
            {
                flag=!flag;
            }
            if(flag && !text[i].equals("NNP")&& !text[i].equals("|") && !text[i].equals(" "))
            {
                output+=text[i]+" ";
            }
            i++;
        }
                
                
        return output;
    }
    
    public String feature(String[] text, String[] type) throws SuggesterException
    {
        String output="";
        Corrector obj_correct=new Corrector();
        int i=0;
        while(text[i]!=null)
        {
            if(type[i].equalsIgnoreCase("NN")||type[i].equalsIgnoreCase("NNS")||type[i].equalsIgnoreCase("NNP")||type[i].equalsIgnoreCase("NNPS"))
            {
                if(obj_correct.check_corrector(text[i]))
                {
                    output+=text[i]+" ";        
                }
            }
            i++;
        }
        return output;
    }
    
    public String description(String[] text, String[] type) throws SuggesterException, FileNotFoundException
    {
        String output="";
        int i=0;
        boolean negative, booster;
        Corrector obj_correct=new Corrector();
        Elements obj_elements=new Elements();
        
        while(text[i]!=null)
        {
            
            if(type[i].equals("RB"))
            {
                negative=obj_elements.negative(text[i]);
                booster=obj_elements.booster(text[i]);
                if(negative)
                {
                    output+="|N|";
                }
                if(booster)
                {
                    output+="|B|";
                    text[i]="";
                }
            }
            if(type[i].equalsIgnoreCase("VB")||type[i].equalsIgnoreCase("JJ")||type[i].equalsIgnoreCase("VB")||type[i].equalsIgnoreCase("VBD")||type[i].equalsIgnoreCase("VBG")||type[i].equalsIgnoreCase("VBN")||type[i].equalsIgnoreCase("VBP") || type[i].equalsIgnoreCase("VBZ"))
            {
                if(!text[i].equals("") && obj_correct.check_corrector(text[i]))
                {
                    output+=text[i]+" ";        
                }      
            }
            
            i++;
        }
        return output;
    }
    
    public boolean negative(String text) throws FileNotFoundException
    {
        if(negative_list==null) 
        {
            negative_list = new ArrayList<String>();
            Scanner scan = new Scanner(new File("NegatingWordList.txt"));

             while (scan.hasNextLine())
             {
                   negative_list.add(scan.nextLine());
             }
             scan.close();
        }
         
         
         
         if(negative_list.contains(text))
         {
             return true;
         }
        
        return false;
    }
    /*
    public String negative_seperate(String text) throws FileNotFoundException
    {
        
        String root="";
        char array[]=text.toCharArray();
        int i=array.length,j=0;
        
        if(array[array.length-1]=='t')
        {
            if(array[array.length-3]=='\'')
            {
                i=i-3;
            }
            if(array[array.length-2]=='\'')
            {
                i=i-2;   
            }
        }
        while(j<i)
        {
            root+=array[j];
            j++;  
        }
        
        return root;
    }
    */
    
    public boolean booster(String text) throws FileNotFoundException
    {
        if(booster_list==null) 
        {
            booster_list = new ArrayList<String>();
            Scanner scan = new Scanner(new File("BoosterWordList.txt"));

             while (scan.hasNextLine())
             {
                   booster_list.add(scan.nextLine());
             }
             scan.close();
        }
         
         
         
         if(booster_list.contains(text))
         {
             return true;
         }
        
        return false;
    }
    
    
    public String class_name(String input) throws FileNotFoundException, IOException, SuggesterException
    {
        NaiveBayes obj_naive=new NaiveBayes();
        Stemmer obj_stem=new Stemmer();
        String output="";
        Scanner scan=new Scanner(input);
        scan.useDelimiter(" ");
        
        while(scan.hasNext())
        {
            String word=scan.next();
            char array[]=word.toCharArray();
            
            if(array.length>3)
            {
                if(array[0]=='|' && array[1]=='N' && array[2]=='|')
                {
                    word="";
                    for(int i=3;i<array.length;i++)
                    {
                        word+=array[i];
                        System.out.println(word);
                    }
                    if(obj_naive.main_naivebayes(obj_stem.main_stem(word))==null)
                    {
                        output+=obj_naive.main_naivebayes(obj_stem.main_stem(word))+" ";
                    }
                    else
                    {
                        output+=(float)1-obj_naive.main_naivebayes(obj_stem.main_stem(word))+" ";
                    }
                }
               else
                {
                    output+=obj_naive.main_naivebayes(obj_stem.main_stem(word))+" ";
                }
            }
            else
            {
                output+=obj_naive.main_naivebayes(obj_stem.main_stem(word))+" ";
            }
     
        }   
        return output;
        
        
    }
    public Float naive_total(String input)
    {
        Float output=(float)0;
        int i=0;
        Scanner scan_total=new Scanner(input);
        scan_total.useDelimiter(" ");
        while(scan_total.hasNext())
        {
            String temp=scan_total.next();
            if(!temp.equalsIgnoreCase("null"))
            {
                output+=Float.valueOf(temp);
                i++;
                System.out.println(">>>>"+output);
            }
        }
        if (output!=null)
        {
            output=output/i;
        }
        return output;
    }
    
   
    
}
