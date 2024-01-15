package com.vocabpocker.util;

import java.security.MessageDigest;

public class HashingUtil
{
    
    public static String getSHA256Hash(String data) {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            return bytesToHex(hash); // make it printable
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    private static String  bytesToHex(byte[] hash) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
         sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
    
}