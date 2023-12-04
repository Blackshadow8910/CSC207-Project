package interface_adapters.signup;

public class SignupRestrictions {
    private SignupRestrictions() {

    }

    public static String usernameVerifier(String username)
    {
        if (!lengthVerifier(username, 3, 30))
        {
            return "LengthError";
        }
        if (alphanumericVerifier(username))
        {
            return "AlphanumError";
        }

        return "";
    }

    private static boolean lengthVerifier(String string, Integer min, Integer max)
    {
        // Return true if and only if the length of string falls between min and max
        // Return false otherwise
        return min<= string.length() && string.length() <= max ;
    }

    private static boolean alphanumericVerifier(String string)
    {
        // Return True if and only if there exists a non-alphanumeric character in string
        // Return False is all characters in string are alphanumeric
        return !string.matches("[A-Za-z0-9]+");
    }
    private static boolean complexityVerifier(String password)
    {
        boolean hasLowerCase = false;
        boolean hasNumeric = false;
        boolean hasUpperCase = false;
        for (int i =0; i < password.length(); i++)
        {
            if (Character.isUpperCase(password.charAt(i)))
            {
                hasUpperCase = hasUpperCase || true;
            }
            else if (Character.isLowerCase(password.charAt(i)))
            {
                hasLowerCase = hasLowerCase || true;
            }
            else if (Character.isDigit(password.charAt(i)))
            {
                hasNumeric = hasNumeric || true;
            }

        }
        return hasNumeric && hasLowerCase && hasUpperCase;
    }
    public static String passwordVerifier(String username, String password)
    {
        if (!lengthVerifier(password, 8, 30))
        {
            return "LengthError";
        }
        if (alphanumericVerifier(password))
        {
            return "AlphanumError";
        }
        if (complexityVerifier(password))
        {
            return "ComplexityError";
        }
        if (usernameInPassword(username, password))
        {
            return "UsernameInPasswordError";
        }

        return "";
    }
    public static boolean usernameInPassword(String username, String password)
            // Return True if username IS in password
            // Return false if username is NOT in password
    {
        return password.contains(username);

    }
}
