/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fyp_backend;

import com.softcorporation.suggester.util.SuggesterException;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;


public class Tuple 
{
    MaxentTagger tagger = null;
    PrintWriter out = null;
   
    
    public int classify (int review_no, String sentences_words[][]) throws SuggesterException, FileNotFoundException, IOException
    {

        Tuple obj_tuple=new Tuple();

        
        int i,j,k;
        String tmp[]=new String [100];
        
        
        
        
        //tagged sentences in sentence array

 
          //System.out.println("START");   
        

        String type, word;
        i=0;
        
        while(sentences_words[i][0]!=null)
        {
              j=0;
              String type_array[]=new String[3000], word_array[]=new String[3000];
              while(sentences_words[i][j]!=null)
              {          
                  char tmpx[]=sentences_words[i][j].toLowerCase().toCharArray();//I_PRP
                  k=0;
                  type="";
                  word="";
                  
                  while (tmpx.length>k)
                  {
                      if(tmpx[k]=='_' && (tmpx.length-k)<5)
                      {   k++;
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
                    
                  //type determined

                  word_array[j]=word;
                  type_array[j]=type;
                  //System.out.print(word_array[j]);
                 // System.out.println(type_array[j]);
              
                  
                  j++;
              }
              obj_tuple.create_tuple(review_no, word_array,type_array);

              //DELETE THE SENTENCE & TYPE_ARRAY
              i++;
          }
        

        

        return 1;
    }
    
        
    
    
    
    
    public void create_tuple(int review_no,String text[], String type[]) throws FileNotFoundException, IOException, SuggesterException
    {
        
  
        Tuple obj_tuple=new Tuple();
        Word_files obj_files=new Word_files();
        NaiveBayes obj_naive=new NaiveBayes();
        Elements obj_element=new Elements();
        //int in=obj_naive.main_naivebayes("expensive");
        //delete stopwords
        
        int i=0;

       
             
        
         type=obj_files.stopwords(text,type);
        
     

        while(type[i]!=null)
        {
            System.out.print(text[i]+":"+type[i]+" ");

            i++;
        }
        System.out.println();
       
       
        String target=obj_element.target(text,type);   
        
        String feature=obj_element.feature(text, type);       
        
        String description=obj_element.description(text, type);
        
        String NB_score=obj_element.class_name(description);
        
        Float NB_total=obj_element.naive_total(NB_score);
            
        
              
        String opinion_holder="";
     
        
        obj_tuple.write(review_no, feature , description , NB_score, NB_total);
    
        
    }
    
        
    public void write(int one, String two, String three, String four, Float five) throws FileNotFoundException
    {
            out = new PrintWriter(new FileOutputStream(new File("tuple.txt"), true )); 
            out.println(one+";"+ two + ";" + three + ";" + four + ";" + five);
 
        out.close();
    }
    
    
    
    
    
    public String[] tag(String input[])
    {
        int i=0;
        if(tagger==null)
        {
            System.out.println("intialize");
            tagger = new MaxentTagger("gate-EN-twitter.model");
        }
            
        if(input!=null)
        {
            
            while(input[i]!=null)
            {
                input[i]= tagger.tagString(input[i]); //sentence is now tagged
                i++;
            }
        }
        return input;
    }
    
    public String tag(String input)
    {
        if(tagger==null)
        {
            System.out.println("intialize");
            tagger = new MaxentTagger("gate-EN-twitter.model");
        }
        if(input!=null)
        {
            return tagger.tagString(input);
        }
        return null;
    }










public String [][] cc(String text[][], int sentence_index,int word)
    {
        int i=sentence_index,j=0, vf=0,nf=0;
        
       String type;
 
       while(text[sentence_index][j]!=null)
       {
           char tmpx[]=text[sentence_index][j].toCharArray();
           int k=0;
           type="";

           if(type.equalsIgnoreCase("VB")||type.equalsIgnoreCase("VBD")||type.equalsIgnoreCase("VBG")||type.equalsIgnoreCase("VBN")||type.equalsIgnoreCase("VBP") || type.equalsIgnoreCase("VBZ"))
           {
                 vf=1;
           }
           if(type.equalsIgnoreCase("NN")||type.equalsIgnoreCase("NNS")||type.equalsIgnoreCase("NNP")||type.equalsIgnoreCase("NNPS"))
           {
                  nf=1;
           }
     
       }
       

        
        
        if(vf!=0 && nf!=0)
        {
            while(text[i][0]!=null)
            {
                i++;
            }
  
            int l=0;
            while(text[sentence_index][word]!=null)
            {
                text[i][l]=text[sentence_index][word];
                text[sentence_index][word]=null;
                word++;l++;
            }
        }
        return text;
    }




}