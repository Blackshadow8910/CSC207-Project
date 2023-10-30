package data_access.image;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.function.Consumer;

public interface ImageCacheAccessInterface extends ImageDataAccessInterface{
    public void cacheImage(String key, BufferedImage image);
    public void addImageCacheListener(Consumer<String> listener);
    public void fireImageCacheUpdated(String key);
}
