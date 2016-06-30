

package fyp_backend;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import static javax.xml.bind.DatatypeConverter.parseFloat;

/**
 *
 * @author nach
 */
public class NaiveBayes {
     HashMap <String , Float> hm_naive = null;

 
     
     public Float main_naivebayes(String input) throws FileNotFoundException
    {
         hm_naive = new HashMap <String, Float>();

        Levenstein obj_leven=new Levenstein();
        Scanner class_scan = new Scanner(new File("class.txt"));
                String  one;
                Float two;
                
        while (class_scan.hasNextLine())
        {
            Scanner scan=new Scanner(class_scan.nextLine());
            scan.useDelimiter(",");
            one=scan.next();
            two=parseFloat(scan.next());
            
            hm_naive.put(one , two);
        }

        for (Map.Entry<String, Float> e : hm_naive.entrySet()) {
        String key = e.getKey();
        Float value = e.getValue();
        //System.out.println(key + value);
        if(input.equals(key))//check if input in hashmap
        {
            //System.out.println("><<><><>"+value);
            return value;
        }
      }  
        //System.out.println("><<><><>NOT FOUND");
        return null;
        
    }
    
}
