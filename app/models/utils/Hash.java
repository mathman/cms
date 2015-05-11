package models.utils;

import java.security.MessageDigest;

public class Hash {

    private static final int BYTE_MASK = 0xFF;
    /**
     * Create an encrypted password from a clear string.
     *
     * @return an encrypted password of the clear string
     * @throws Exception APP Exception, from NoSuchAlgorithmException
     */
    public static String createPassword(String email, String password) throws Exception {
        if (email == null || password == null) {
            throw new Exception("No password or email defined!");
        }
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashPass = md.digest(password.getBytes("UTF-8"));
        return byteArrayToHexStr(hashPass);
    }

    /**
     * @param candidate         the clear text
     * @param encryptedPassword the encrypted password string to check.
     * @return true if the candidate matches, false otherwise.
     */
    public static boolean checkPassword(String candidate, String encryptedPassword) throws Exception {
        if (candidate == null) {
            return false;
        }
        if (encryptedPassword == null) {
            return false;
        }
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashCandidate = md.digest(candidate.getBytes("UTF-8"));

        String stringCandidate = byteArrayToHexStr(hashCandidate);
        return encryptedPassword.equals(stringCandidate);
    }

    private static String byteArrayToHexStr(byte[] data) {
        String output = "";

        for ( int cnt = 0; cnt < data.length; cnt++ )
        {
            //Deposit a byte into the 8 lsb of an int.
            int tempInt = data[cnt] & BYTE_MASK;

            //Get hex representation of the int as a string.
            String tempStr = Integer.toHexString( tempInt );

            //Append a leading 0 if necessary so that each hex string will contain 2 characters.
            if ( tempStr.length() == 1 )
            {
                tempStr = "0" + tempStr;
            }

            //Concatenate the two characters to the output string.
            output = output + tempStr;
        }

        return output.toUpperCase();
    }
}
