package com.luxoft.wheretogo.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

import static java.nio.file.Paths.get;

/**
 * Created by bobbi on 10.08.16.
 */
public class ImageUtils {
    public static String generatePicturePath(String imageDataString, String pathToFolder) {
        //Generating image/file and path to store event data
        if(imageDataString.length()!=0){
            Random rnd = new Random();
            String fileName = generateString(rnd,"qwertyuiop0987654321asdfghjklzxcvbnm",8);
            String path = pathToFolder+fileName;
            //Default path: apache-tomcat -> bin
            File img = new File(path);
            path = img.getPath();
            try {
                Files.write(get(img.getPath()),imageDataString.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return path;
        }
        else return imageDataString;
        //End of generation
    }
    private static String generateString(Random rng, String characters, int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
}
