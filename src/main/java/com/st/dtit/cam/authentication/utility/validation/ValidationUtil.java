package com.st.dtit.cam.authentication.utility.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {

      public static boolean validEmailAddress(final String emailAddress) {
            final Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
            final Matcher matcher = regexPattern.matcher(emailAddress);

            return matcher.matches();
      }

      public static boolean validPhoneNumber(final String phoneNumber) {
            final Pattern regexPattern = Pattern.compile("^\\d{3}[\\s.-]\\d{4}$");
            final Matcher matcher = regexPattern.matcher(phoneNumber);

            return matcher.matches();
      }

      private ValidationUtil(){}
}
