package interface_adapters.signup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupRestrictions {
    public static String username_verifier(String username)
    {
        if (!length_verifier(username, 3, 30))
        {
            return "LengthError";
        }
        if (alphanumeric_verifier(username))
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
    }

    private static boolean alphanumeric_verifier(String string)
    {
        // Return True if and only if there exists a non-alphanumeric character in string
        // Return False is all characters in string are alphanumeric
        return !string.matches("[A-Za-z0-9]+");
    }
    private static boolean complexity_verifier(String password)
    {
        boolean hasLowerCase = false;
        boolean hasNumeric = false;
        boolean hasUpperCase = false;
        for (int i =0; i < password.length(); i++)
        {
            if (Character.isUpperCase(password.charAt(i)))
            {
                hasUpperCase = true;
            }
            else if (Character.isLowerCase(password.charAt(i)))
            {
                hasLowerCase = true;
            }
            else if (Character.isDigit(password.charAt(i)))
            {
                hasLowerCase = true;
            }

        }
        return hasNumeric & hasLowerCase & hasUpperCase;
    }
    public static String password_verifier(String username, String password)
    {
        if (!length_verifier(password, 8, 30))
        {
            return "LengthError";
        }
        if (alphanumeric_verifier(password))
        {
            return "AlphanumError";
        }
        if (complexity_verifier(password))
        {
            return "ComplexityError";
        }
        if (username_in_password(username, password))
        {
            return "UsernameInPasswordError";
        }

        return "";
    }
    public static boolean username_in_password(String username, String password)
            // Return True if username IS in password
            // Return false if username is NOT in password
    {
        return password.contains(username);

    }

    public static void windowCreator(String window_title, String error_message){
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(window_title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel(new BorderLayout());

            JTextArea textArea = new JTextArea(error_message);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setFocusable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            JButton okButton = new JButton("Ok");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose(); // Close the window when Ok button is pressed
                }
            });

            mainPanel.add(okButton, BorderLayout.SOUTH); // Place the button at the bottom

            frame.getContentPane().add(mainPanel);

            frame.setSize(200, 175);
            frame.setLocationRelativeTo(null); // Center the window
            frame.setVisible(true);
        });
    }
}
