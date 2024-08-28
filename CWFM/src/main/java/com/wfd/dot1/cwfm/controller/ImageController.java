package com.wfd.dot1.cwfm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ImageController {

    @RequestMapping("/image/{imageName}")
    public void getImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        // Set the content type of the response
        response.setContentType("image/jpeg"); // Adjust content type based on your image format

        // Read the image file from the file system
        File imageFile = new File("/resources/images/" + imageName);
        InputStream inputStream = new FileInputStream(imageFile);

        // Copy the image data to the response output stream
       // IOUtils.copy(inputStream, response.getOutputStream());

        // Flush the buffer
        response.flushBuffer();
    }
}
