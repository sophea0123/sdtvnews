package com.sdtvnews.sdtvnews.config;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageUtil {



    private static final String IMAGE_URL_PREFIX = "/images/";

    public static String processBase64Images(String htmlContent, String title,String imageDirectory) throws IOException {
        // Regular expression to find base64 image strings in the HTML
        Pattern pattern = Pattern.compile("data:image/[^;]+;base64,([^\"]+)");
        Matcher matcher = pattern.matcher(htmlContent);

        StringBuffer updatedContent = new StringBuffer();

        // Process each base64 image
        while (matcher.find()) {
            String base64Image = matcher.group(1);  // Extract base64 data
            String imageUrl = saveBase64Image(base64Image, title,imageDirectory);  // Save image and get URL

            // Replace base64 image with URL in content
            matcher.appendReplacement(updatedContent, IMAGE_URL_PREFIX + imageUrl);
        }

        matcher.appendTail(updatedContent);
        return updatedContent.toString();  // Updated HTML content with URLs
    }

//    public static String saveBase64Image(String base64Image, String title) throws IOException {
//        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
//
//        // Generate a unique image name using the title
//        String imageName = getNewImageNameForExistingImage(title);
//        String imagePath = IMAGE_DIRECTORY + imageName;
//
//        try (FileOutputStream fos = new FileOutputStream(imagePath)) {
//            fos.write(imageBytes);
//        }
//
//        return imageName;  // Return the filename to create the full URL later
//    }

    public static String saveBase64Image(String base64Image, String title, String imageDirectory) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        String imageName = getNewImageNameForExistingImage(title, imageDirectory);
        String imagePath = Paths.get(imageDirectory, imageName).toString();

        try (FileOutputStream fos = new FileOutputStream(imagePath)) {
            fos.write(imageBytes);
        }

        return imageName;
    }
    private static String getNewImageNameForExistingImage(String title, String imageDirectory) {
        String titleHash = EncryptionImageUtil.hash64(title);
        int counter = 1;
        String newImageName = titleHash + "_" + counter + ".jpg";

        while (new File(Paths.get(imageDirectory, newImageName).toString()).exists()) {
            counter++;
            newImageName = titleHash + "_" + counter + ".jpg";
        }

        return newImageName;
    }

//    private static String getNewImageNameForExistingImage(String title,String imageDirectory) {
//        // Use the EncryptionUtil to hash the title
//        String titleHash = EncryptionImageUtil.hash64(title); // Generate a unique hash for the title
//
//        // Initialize a counter to ensure uniqueness
//        int counter = 1;
//        String newImageName = titleHash + "_" + counter + ".jpg"; // Start with the base name
//
//        // Check if the file already exists and increment the counter
//        while (new File(IMAGE_DIRECTORY + newImageName).exists()) {
//            counter++;
//            newImageName = titleHash + "_" + counter + ".jpg"; // Update the name with the counter
//        }
//
//        return newImageName; // Return the unique image name
//    }


}

