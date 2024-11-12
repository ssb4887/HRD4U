package rbs.egovframework.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;

public class Sha512Util {
	
    public static String getSHA512(String input) throws Exception{
    	String toReturn = "";
    	try{
    		
    		MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(input.getBytes("utf-8"));
            toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
             
    	}catch(UnsupportedEncodingException e){
        	toReturn = "";
    	}
    	 
         return toReturn;
    }

}
