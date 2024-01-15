package com.vocabpocker.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

public class AppUtils {

	public static String getCommaSeparatedListValues(Collection<?> list){
		return null != list && !list.isEmpty() ? StringUtils.join((Iterator<?>) list.iterator(),", ") : ""; 
	}
	
	public static String getDateAsString(long timeInMilis){
		return new LocalDate(timeInMilis).toString(DateTimeFormat.forPattern(Constants.DISPLAY_DATE_FORMAT));
	}
	
	public static String getCurrentDateAsString(){
		return new LocalDate(System.currentTimeMillis()).toString(DateTimeFormat.forPattern(Constants.DISPLAY_DATE_FORMAT));
	}
	
	public static int getRandomNumberInRange(final int min, final int max)
    {

        if (min >= max)
        {
            throw new IllegalArgumentException("max must be greater than min");
        }

        final Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
        // return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
    }
}
