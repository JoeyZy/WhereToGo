package com.luxoft.wheretogo.utils;
/**
 * Created by bobbi on 15.08.16.
 */
public class ValidationUtils {
    public static long validateRequestParam(String param){
        long id=0;
        try {
            id = Long.parseLong(param);
        }
        catch( Exception e ) {
            return -1;
        }
        return id;
    }
}
