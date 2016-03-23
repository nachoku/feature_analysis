/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fyp_backend;

import com.softcorporation.suggester.util.SuggesterException;
import java.text.BreakIterator;
import java.util.Locale;

/**
 *
 * @author nach
 */
public class Splitter {
    /*
    
      public int sentences(String input)
      {
          int length=0;
          
          BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
          String source = input;
          
          iterator.setText(source);
          int start = iterator.first();
           for (int end = iterator.next();
               end != BreakIterator.DONE;
               start = end, end = iterator.next())
          {
              if (!source.substring(start, end).equals(" "))
                length++;          
          }
          
          return length;
      }
      public int words(String input)
      {
          int length=0;
          
          BreakIterator iterator = BreakIterator.getWordInstance(Locale.US);
          String source = input;
          
          iterator.setText(source);
          int start = iterator.first();
           for (int end = iterator.next();
               end != BreakIterator.DONE;
               start = end, end = iterator.next())
          {
              if (!source.substring(start, end).equals(" "))
                length++;          
          }
          
          return length;
      }
      */
      
      public String[] main_sentence (String input) throws SuggesterException
      {

          int i=0;
          String op[]=new String [100];
          BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
          String source = input;
          iterator.setText(source);
          int start = iterator.first();
          for (int end = iterator.next();
               end != BreakIterator.DONE;
               start = end, end = iterator.next()) 
          {      
              op[i++]= source.substring(start,end) ;    
          }
          return op;

      }
      
      
      
      public String[] main_word(String input) throws SuggesterException
      {
          int i=0;
          BreakIterator iterator = BreakIterator.getWordInstance(Locale.US);
          String source = input;
          String op[]=new String [3000];
          iterator.setText(source);
          int start = iterator.first();
          for (int end = iterator.next();
               end != BreakIterator.DONE;
               start = end, end = iterator.next())
          {
              if (!source.substring(start, end).equals(" "))
                op[i++]=source.substring(start,end);         
          }
          return op;
      }
      
      public String[][] sentence_word(String input) throws SuggesterException
      {
          Splitter obj=new Splitter();
          String sentence_word[][]=new String[100][500], sentence[]=new String[100], word[]=new String[500];
          sentence=obj.main_sentence(input);
          int i=0;
          while(sentence[i]!=null)
          {
              word=obj.main_word(sentence[i]);
              int j=0;
              while(word[j]!=null)
              {
                  sentence_word[i][j]=word[j];
                  j++;
              }
              i++;
          }
   
          return sentence_word;
      }
      
      public String[][] sentence_word(String input[]) throws SuggesterException
      {
          Splitter obj=new Splitter();
          String sentence_word[][]=new String[100][500], word[]=new String[500];

          int i=0;
          while(input[i]!=null)
          {
              word=obj.main_word(input[i]);
              int j=0;
              while(word[j]!=null)
              {
                  sentence_word[i][j]=word[j];
                  j++;
              }
              i++;
          }
          return sentence_word;
      }
      
      
      
      
    public String[] bracket(String[] input) throws SuggesterException
    {
        Splitter obj_split=new Splitter();
        boolean flag=false;
        int i=0,end=0;
        
        while(input[end]!=null)
        {
            System.out.println(input[end]);
            end++;
        }
        input[end]="";
        while(input[i]!=null)
        {
            String word[]=obj_split.main_word(input[i]);
            int j=0;
            while(word[j]!=null)
            {
               if(word[j].equalsIgnoreCase("-LR"))
               {
                   flag=!flag;
               }
               if(word[j].equalsIgnoreCase("-R"))
               {
                   break;
               }
                   
               if(flag==true)
               {
                   input[end]+=word[j]+" ";
                   input[j]="";
               }                 
               
               j++;
            }
            i++;
        }
        return input;
    }
}
