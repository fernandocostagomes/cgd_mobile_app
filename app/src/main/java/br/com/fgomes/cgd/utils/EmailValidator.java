package br.com.fgomes.cgd.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator
{
   private Pattern m_pattern;
   private Matcher m_matcher;

   private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                     + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

   public EmailValidator()
   {
      m_pattern = Pattern.compile( EMAIL_PATTERN );
   }

   /**
    * Validate hex with regular expression
    * 
    * @param hex
    * hex for validation
    * @return true valid hex, false invalid hex
    */
   public boolean validate( final String hex )
   {
      m_matcher = m_pattern.matcher( hex );
      return m_matcher.matches();
   }
}