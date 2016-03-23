
package fyp_backend;

/**
 *
 * @author nach
 */
import com.softcorporation.suggester.BasicSuggester;
import com.softcorporation.suggester.Suggestion;
import com.softcorporation.suggester.dictionary.BasicDictionary;
import com.softcorporation.suggester.tools.SpellCheck;
import com.softcorporation.suggester.util.Constants;
import com.softcorporation.suggester.util.SpellCheckConfiguration;
import com.softcorporation.suggester.util.SuggesterException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Corrector 
{
    Levenstein leven_obj= new Levenstein();
    
    
    SpellCheck spellCheck=null;
    BasicDictionary dictionary = null;
    SpellCheckConfiguration configuration = null;
    BasicSuggester suggester = null;

    public String main_corrector(String text) throws SuggesterException
    {
        
        
        if(spellCheck==null || dictionary==null || configuration==null || suggester==null)
        {
            dictionary = new BasicDictionary("file://english.jar");
            configuration = new SpellCheckConfiguration("file://spellCheck.config");
            suggester = new BasicSuggester(configuration);

            try {
                suggester.attach(dictionary);
            } catch (SuggesterException ex) {
                Logger.getLogger(Corrector.class.getName()).log(Level.SEVERE, null, ex);
            }
            spellCheck = new SpellCheck(configuration);
            spellCheck.setSuggester(suggester);
            spellCheck.setSuggestionLimit(1);         
            spellCheck.setText(text, Constants.DOC_TYPE_TEXT, "en");
            spellCheck.check();
        }     

               
       
          ArrayList suggestions = suggester.getSuggestions(text, 1);
          int idx = suggestions.size();
          if (idx>0)
          {
                   
                Suggestion suggestion = (Suggestion) suggestions.get(0);
                if(leven_obj.mains(text,suggestion.word)<3)
                {
                     return suggestion.word;
                }
          }
               
          return text;
               
 
    }
    
    
    public boolean check_corrector(String text) throws SuggesterException
    {
        
         if(spellCheck==null || dictionary==null || configuration==null || suggester==null)
         {
            dictionary = new BasicDictionary("file://english.jar");
            configuration = new SpellCheckConfiguration("file://spellCheck.config");
            suggester = new BasicSuggester(configuration);

            try {
                suggester.attach(dictionary);
            } catch (SuggesterException ex) {
                Logger.getLogger(Corrector.class.getName()).log(Level.SEVERE, null, ex);
            }
            spellCheck = new SpellCheck(configuration);
            spellCheck.setSuggester(suggester);
            spellCheck.setSuggestionLimit(1);         
            spellCheck.setText(text, Constants.DOC_TYPE_TEXT, "en");
            spellCheck.check();
         }     
         Levenstein leven_obj= new Levenstein();
         ArrayList suggestions = suggester.getSuggestions(text, 1);
         int idx = suggestions.size();
         if (idx>0)
         {
            Suggestion suggestion = (Suggestion) suggestions.get(0);
            if(text.equalsIgnoreCase(suggestion.word))
            {
                return true;
            }
                    
           
         }
         return false;
    }
      
}
