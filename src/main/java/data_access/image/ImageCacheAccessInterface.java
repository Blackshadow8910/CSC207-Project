package data_access.image;

import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public interface ImageCacheAccessInterface extends ImageDataAccessInterface{
    void cacheImage(String key, BufferedImage image);
    void addImageCacheListener(Consumer<String> listener);
    void fireImageCacheUpdated(String key);
}
