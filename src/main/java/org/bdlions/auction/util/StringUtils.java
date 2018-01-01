package org.bdlions.auction.util;

import java.util.Random;

/**
 *
 * @author Nazmul Hasan
 */
public class StringUtils {
    public static boolean isNullOrEmpty(String str){
        return str == null || str.equals("");
    }
    
    public static String getProductReferenceId()
    {
        String referenceId = "";
        Random random = new Random();
        int max = 9999999;
        int min = 1000000;
        referenceId = random.nextInt(max - min + 1) + min + "";
        return referenceId;
    }
}
