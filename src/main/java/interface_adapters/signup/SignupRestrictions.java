package interface_adapters.signup;

public class SignupRestrictions {
    public static String username_verifier(String username)
    {
        if (length_verifier(username, 3, 30))
        {
            // Do Nothing
        }
        else
        {
            return "LengthError";
        };
        if (alphanumeric_verifier(username))
        {
            // Do Nothing
        }
        else
        {
            return "AlphanumError";
        }
        return "";
    }

    private static boolean length_verifier(String string, Integer min, Integer max)
    {
        // Return true if and only if the length of string falls between min and max
        // Return false otherwise
        return min<= string.length() & string.length() <= max ;
    };
    private static boolean alphanumeric_verifier(String string)
    {
        // Return True if and only if there exists a non-alphanumeric character in string
        // Return False is all characters in string are alphanumeric
        return !string.matches("[A-Za-z0-9]+");
    }
    public static String password_verifier(String password)
    {
        // do things
        return "";
    }
    public static boolean username_in_password(String username, String password)
    {
        return true;
    }
}
