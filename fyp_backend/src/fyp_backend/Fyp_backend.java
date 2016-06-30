package fyp_backend;

import com.softcorporation.suggester.util.SuggesterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Fyp_backend 
{
    
      public static void main(String[] args) throws SuggesterException, IOException
      {
          Result obj_result=new Result();
          int review_no=0;
          
          
          TrainNaiveBayes train=new TrainNaiveBayes();
          //train.mains();
          
          Formatter obj_format=new Formatter();
          //obj_format.master();
          
          
          
          Test obj_test=new Test();
          //obj_test.main_test();
     ///*      
          File f=new File("tuple.txt");
          f.createNewFile();
          f.delete();
          
          
       
          Fyp_backend object=new Fyp_backend();
          Tuple obj_tuple=new Tuple();
          Splitter obj_split=new Splitter();
          
          
          Scanner scan = new Scanner(new File("C:\\Users\\nach\\Documents\\NetBeansProjects\\config.txt"));
          scan.useDelimiter(",");
          String reviews_file=scan.next(), extra=scan.next();
          scan.close();
          System.out.println(reviews_file+extra);
          
          Scanner scan_review=new Scanner(new FileInputStream("C:\\Users\\nach\\Documents\\NetBeansProjects\\"+reviews_file)); 
          
          
        
          while(scan_review.hasNextLine())
          {
          
              //System.out.println(scan_review.nextLine());     
              //String text= "I was veryi not happy with it. Trusting Lenovo, I ordered 5x and it was a good decision.  The UI lags sometimes, but no lags when using any app or playing games (Asphalt 8 runs super smooth).";
              String text=scan_review.nextLine();
            
              
             //preprocessing
             String sentence_word[][]=object.preprocess(text, extra);



            //Analysis
            sentence_word=object.analysis(sentence_word);

            //classify
            obj_tuple.classify(review_no, sentence_word);
            
            review_no++;        
           
          }
           scan_review.close();
                  
     obj_result.overall();
     obj_result.feature();
     
     
     
     Sort obj_sort=new Sort();
     obj_sort.main_sort();
      
      //*/
      }   
      
      public String[][] analysis (String [][] sentence_word) throws SuggesterException
      {

          Splitter obj_split=new Splitter();
          Tuple obj_tuple=new Tuple();
          int i,j;
          String sentence[]=new String [3000];
        
          i=0;
          while(sentence_word[i][0]!=null)
          {
              j=0;
              sentence[i]="";
              while(sentence_word[i][j]!=null)
              {                
                  sentence[i]+=sentence_word[i][j]+" ";
                  j++;
              }

              i++;
          }
          //sentence
          
          
          sentence=obj_tuple.tag(sentence);
          //sentence=obj_split.bracket(sentence);
          
          sentence_word=obj_split.sentence_word(sentence);
          i=0;
          while(sentence_word[i][0]!=null)
          {
              j=0;
              while(sentence_word[i][j]!=null)
              {
                System.out.print(sentence_word[i][j]+" ");
                j++;
              }
              System.out.println("PRE");
              i++;
          }
          return sentence_word;
      }
      
      
      
      
      public String[][] preprocess (String text, String extra_file) throws IOException, SuggesterException 
      {
          Splitter obj_split=new Splitter();
          Corrector corrector_obj = new Corrector();
          Word_files obj_files=new Word_files();
          int i=0,j;
    
          String sentence_word[][]=obj_split.sentence_word(text);
          //sentence_word=obj_files.special_extra(sentence_word,extra_file);
          sentence_word=obj_files.slang(sentence_word);
          
          
          String tmp="";
          while(sentence_word[i][0]!=null)
          {
              j=0;
              
              while(sentence_word[i][j]!=null)
              {
                  tmp+=sentence_word[i][j]+" ";
                  System.out.print(sentence_word[i][j]+" ");
                  j++;
              }
              i++;
          }
          
          sentence_word=obj_split.sentence_word(tmp);
          

         //correction
          i=0;
          boolean flag=false;
          while(sentence_word[i][0]!=null)
          {
              j=0;
              while(sentence_word[i][j]!=null)
              { 
                  char str_array[]=sentence_word[i][j].toCharArray();
                  if(str_array[0]=='|'){
                      flag=!flag;
                  
                      
                  }
                  if(flag!=true)
                  {
                      sentence_word[i][j]=corrector_obj.main_corrector(sentence_word[i][j]);
                  }
   
                  j++;
              }

              i++;
          }
          
          

               return sentence_word;
      }

       
}
