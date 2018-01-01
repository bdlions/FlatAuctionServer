package org.bdlions.library;

import java.io.File;
import org.bdlions.auction.util.Constants;
import org.bdlions.auction.util.FileUtils;

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
            String uploadPath = Constants.SERVER_BASE_PATH + Constants.IMAGE_UPLOAD_PATH;
            String productPicPath = Constants.SERVER_BASE_PATH + Constants.PRODUCT_IMAGE_PATH;
            File path = new File(productPicPath);
            if (!path.exists()) {
                boolean status = path.mkdirs();
            }
            FileUtils.copyFile(uploadPath + image, productPicPath + image);

            //resize image to 328px to 212px
            String imgProductPath328_212 = Constants.SERVER_BASE_PATH + Constants.IMG_PRODUCT_PATH_328_212;
            ImageLibrary imageLibrary = new ImageLibrary();
            path = new File(imgProductPath328_212);
            if (!path.exists()) {
                boolean status = path.mkdirs();
            }
            imageLibrary.resizeImage(uploadPath + image, imgProductPath328_212 + image, Constants.IMG_PRODUCT_LIST_WIDTH, Constants.IMG_PRODUCT_LIST_HEIGHT);

            //resize image to 328px to 212px
            String imgProductPath103_87 = Constants.SERVER_BASE_PATH + Constants.IMG_PRODUCT_PATH_103_87;
            path = new File(imgProductPath103_87);
            if (!path.exists()) {
                boolean status = path.mkdirs();
            }
            imageLibrary.resizeImage(uploadPath + image, imgProductPath103_87 + image, Constants.IMG_PRODUCT_LIST_WIDTH_103, Constants.IMG_PRODUCT_LIST_HEIGHT_87);

            //resize image to 656px to 424px
            String imgProductPath658_424 = Constants.SERVER_BASE_PATH + Constants.IMG_PRODUCT_PATH_656_424;
            path = new File(imgProductPath658_424);
            if (!path.exists()) {
                boolean status = path.mkdirs();
            }
            imageLibrary.resizeImage(uploadPath + image, imgProductPath658_424 + image, Constants.IMG_PRODUCT_INFO_WIDTH, Constants.IMG_PRODUCT_INFO_HEIGHT);
        }
    }
}
