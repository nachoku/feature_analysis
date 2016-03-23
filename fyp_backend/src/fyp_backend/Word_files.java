
package fyp_backend;

import com.softcorporation.suggester.util.SuggesterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author nach
 */
public class Word_files {
    ArrayList<String> list_extra = null;
    ArrayList<String> list_stop = null;
    HashMap <String, Integer> hm_extra=null;
    HashMap<String, String> hm_slang =null;
    
    
    
     public String[] stopwords(String[] words, String type[]) throws FileNotFoundException, SuggesterException, IOException
    {
        Stemmer obj_stem=new Stemmer();
        if(list_stop == null)
        {
            list_stop = new ArrayList<String>();
            Scanner scan = new Scanner(new FileInputStream("stopwords.txt"));
            while (scan.hasNextLine())
            {
                list_stop.add(obj_stem.main_stem(scan.nextLine()));
            }
            scan.close();
        }
            
               
        int i=0;

        if(words!=null)
        {
            while(words[i]!=null)
            {

                    if(list_stop.contains(obj_stem.main_stem(words[i].toLowerCase())))
                    {

                        type[i]="";
                
                    }
                    i++;
            }
        }
        return type;
    }
    
    
    public String[][] special_extra(String sentences_words[][], String filename) throws FileNotFoundException, SuggesterException
    {
        int j,i;
       
        Splitter obj_split=new Splitter();
        Levenstein obj_leven=new Levenstein();
        Corrector obj_correct=new Corrector();
        if(list_extra==null)
        {
            
            hm_extra = new HashMap <String, Integer>();
            list_extra = new ArrayList<String>();
            String  tmp;
            int value=0;
            Scanner ngram_scan = new Scanner(new File("C:\\Users\\nach\\Documents\\NetBeansProjects\\extra.txt"));
            
            while(ngram_scan.hasNextLine())
            {
               
                String op[]=obj_split.main_word(ngram_scan.nextLine());
                
                
                i=0;
                
                while(op[i]!=null)
                {
                    //if(hm_extra!=null)
                    if(hm_extra.get(op[i])!=null || obj_correct.check_corrector(op[i]))
                    {
                        list_extra.add(op[i]);
                    }
                    hm_extra.put(op[i],value);

                    i++;
                }
                
                
                value++;
            }
            
           ngram_scan.close();

            for(i=0;i<list_extra.size();i++)
            {
                hm_extra.remove(list_extra.get(i));
            }
          
        }
        
        
        
        
        
        // RETURN
        
        int k;
      
        i=0;
        String temp;
        while(sentences_words[i][0]!=null)
        {
            
            j=0;int flag;
            while(sentences_words[i][j]!=null)
                
            {//System.out.println(sentences_words[i][j]);
                for(Map.Entry<String, Integer> e : hm_extra.entrySet())
                {   
                    
                    if(obj_leven.mains(e.getKey(),sentences_words[i][j])<1)
                    {
                        
                        sentences_words[i][j]="|obj"+e.getValue()+"|";
  
                    }
                }
                j++;
            }
            i++;
        
        }
        return sentences_words;
    }

    
    
    
    public String[][] slang(String sentence_word[][]) throws FileNotFoundException
    {
        Levenstein obj_leven=new Levenstein();
         if(hm_slang==null)
         {
            

            hm_slang = new HashMap<String, String>();
            Scanner slang_scan = new Scanner(new FileInputStream("SlangLookupTable.txt"));
            String  one;
            String two;
            while (slang_scan.hasNextLine())
            {
                String line=slang_scan.nextLine();
                Scanner scan=new Scanner(line);
                scan.useDelimiter(",");

                one=scan.next();
                two=scan.next();
                hm_slang.put(one , two);
           }

           slang_scan.close();
        }
        
         
         for (Map.Entry<String, String> e : hm_slang.entrySet()) {
        String key = e.getKey();
        String value = e.getValue();
        
        int i=0,j;
        while(sentence_word[i][0]!=null)
        {
            j=0;
            while(sentence_word[i][j]!=null)
            {
                if(key.equalsIgnoreCase(sentence_word[i][j]))//check if input in hashmap
                {
                    sentence_word[i][j]=value;
                }
                j++;
            }
            i++;
        }
        
      }  
         
        //System.out.println(hm);
        return sentence_word;
    }
    
    
    
    
}

