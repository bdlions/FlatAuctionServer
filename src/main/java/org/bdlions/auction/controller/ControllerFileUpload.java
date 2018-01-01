package org.bdlions.auction.controller;

/**
 *
 * @author alamgir
 */
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/file")
public class ControllerFileUpload {
    private final Logger logger = LoggerFactory.getLogger(ControllerFileUpload.class);
    @RequestMapping("/")
    String home() {
        return "Initial Java Spring service file upload.";
    }

    @RequestMapping("/upload")
    public String uploadFileHandler(@RequestParam("file") MultipartFile file) {

        String name = "";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                name = file.getOriginalFilename();

                // Creating the directory to store file
                String rootPath = "uploads";
                File dir = new File(rootPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                return name;
            } 
            catch (Exception ex) 
            {
                logger.debug(ex.toString());
                return "-1";
            }
        } 
        else 
        {
            logger.debug("Uploaded file is empty.");
            return "-2";
        }
    }

}
