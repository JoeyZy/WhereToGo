package com.luxoft.wheretogo.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
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
            String ext = imageDataString.substring(imageDataString.indexOf("/")+1,imageDataString.indexOf(";"));
            String path = pathToFolder+fileName+"."+ext;
            //Default path: apache-tomcat -> bin
            File img = new File(path);
            File dir = new File(pathToFolder);
            path = img.getPath();
            if(!dir.exists()){
                (new File(pathToFolder)).mkdirs();
            }
            try {
                String imageDataBytes = imageDataString.substring(imageDataString.indexOf(",")+1);
                Base64.Decoder d = Base64.getDecoder();
                Files.write(get(img.getPath()),d.decode(imageDataBytes.getBytes()));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return path;
        }
        else return imageDataString;
        //End of generation
    }
    public static String giveMeImage(String picture, boolean fromEvent){
        String imageData = "";
                try {
                    imageData = new String(Files.readAllBytes(get(picture)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return imageData;
    }
    public static ResponseEntity<byte[]> giveMeImage(String picture) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<byte[]> responseEntity;
        byte[] response;
        String extension = picture.substring(picture.lastIndexOf(".")+1);
        MediaType type = MediaType.IMAGE_JPEG;
        switch (extension){
            case "png":
                type = MediaType.IMAGE_PNG;
                break;
            case "gif":
                type = MediaType.IMAGE_GIF;
                break;
        }
        headers.setContentType(type);
//        headers.setCacheControl("public");
        response = Files.readAllBytes(get(picture));
        responseEntity = new ResponseEntity<byte[]>(response,headers, HttpStatus.OK);
        return responseEntity;
    }
    public static void deleteOldPicture(String picture){
        File file = new File(picture);
        file.delete();
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
