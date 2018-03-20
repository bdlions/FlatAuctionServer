package org.bdlions.library;

import java.io.File;
import org.bdlions.auction.util.Constants;
import org.bdlions.auction.util.FileUtils;
import org.bdlions.auction.util.ServerConfig;

/**
 *
 * @author Nazmul Hasan
 */
public class ProductLibrary {
    public void copyUploadedImages(String images)
    {
        String[] imageArray = images.split(",");
        for(String image: imageArray)
        {
            String uploadPath = ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_ABS_PATH) + Constants.IMAGE_UPLOAD_PATH;
            String productPicPath = ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_ABS_PATH) + Constants.PRODUCT_IMAGE_PATH;
            File path = new File(productPicPath);
            if (!path.exists()) {
                boolean status = path.mkdirs();
            }
            FileUtils.copyFile(uploadPath + image, productPicPath + image);

            ImageLibrary imageLibrary = new ImageLibrary();            
            
            //resize image to 103x to 87px
            String imgProductPath103_87 = ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_ABS_PATH) + Constants.IMG_PRODUCT_PATH_103_87;
            path = new File(imgProductPath103_87);
            if (!path.exists()) {
                boolean status = path.mkdirs();
            }
            imageLibrary.resizeImage(uploadPath + image, imgProductPath103_87 + image, Constants.IMG_PRODUCT_INFO_WIDTH_103, Constants.IMG_PRODUCT_INFO_HEIGHT_87);

            //resize image to 328px to 212px
            String imgProductPath328_212 = ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_ABS_PATH) + Constants.IMG_PRODUCT_PATH_328_212;
            path = new File(imgProductPath328_212);
            if (!path.exists()) {
                boolean status = path.mkdirs();
            }
            imageLibrary.resizeImage(uploadPath + image, imgProductPath328_212 + image, Constants.IMG_PRODUCT_INFO_WIDTH_328, Constants.IMG_PRODUCT_INFO_HEIGHT_212);

            
            //resize image to 656px to 424px
            String imgProductPath656_424 = ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_ABS_PATH) + Constants.IMG_PRODUCT_PATH_656_424;
            path = new File(imgProductPath656_424);
            if (!path.exists()) {
                boolean status = path.mkdirs();
            }
            imageLibrary.resizeImage(uploadPath + image, imgProductPath656_424 + image, Constants.IMG_PRODUCT_INFO_WIDTH_656, Constants.IMG_PRODUCT_INFO_HEIGHT_424);
            
            //resize image to 800px to 516px
            String imgProductPath800_516 = ServerConfig.getInstance().get(ServerConfig.SERVER_BASE_ABS_PATH) + Constants.IMG_PRODUCT_PATH_800_516;
            path = new File(imgProductPath800_516);
            if (!path.exists()) {
                boolean status = path.mkdirs();
            }
            imageLibrary.resizeImage(uploadPath + image, imgProductPath800_516 + image, Constants.IMG_PRODUCT_INFO_WIDTH_800, Constants.IMG_PRODUCT_INFO_HEIGHT_516);
        }
    }
}
