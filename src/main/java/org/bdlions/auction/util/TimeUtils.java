/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bdlions.auction.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nazmul
 */
public class TimeUtils {
    private final Logger logger = LoggerFactory.getLogger(TimeUtils.class);
    public long getCurrentTime()
    {
        long currentTime = System.currentTimeMillis() / 1000L;        
        return currentTime;
    }
    
    public long getHumanToUnix(String date, String dateFormat)
    {
        //if no date formate is defined then we will use yyyy-MM-dd formate
        if(StringUtils.isNullOrEmpty(dateFormat))
        {
            dateFormat = "yyyy-MM-dd";
        }
        long unixTime = 0;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat); // the format of your date
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            unixTime = sdf.parse(date).getTime() / 1000;            
        }
        catch(Exception ex)
        {
            logger.error(ex.toString());
            unixTime = 0;
        }
        return unixTime;
    }
    
    public String convertUnixToHuman(long unixSeconds, String dateFormat) {
        if(StringUtils.isNullOrEmpty(dateFormat))
        {
            dateFormat = "dd-MM-yyyy h:mm a";
        }
        //reference http://stackoverflow.com/questions/17432735/convert-unix-time-stamp-to-date-in-java
        //http://stackoverflow.com/questions/8046167/convert-unix-time-into-readable-date-in-java
        Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}
