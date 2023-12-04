package data_access.image;

import java.awt.image.BufferedImage;

public interface ImageDataAccessInterface {
    BufferedImage getImage(String url);
}
