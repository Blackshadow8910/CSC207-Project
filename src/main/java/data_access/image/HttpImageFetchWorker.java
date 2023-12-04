package data_access.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;

public class HttpImageFetchWorker extends SwingWorker<BufferedImage, Object> {

    public final String url;
    public final ImageCacheAccessInterface cache;

    private static final BufferedImage placeHolderImage = readPlaceholderImage();
    /**
     * Will store the processed image in a bufferedimage in the image cache. 
     * This assumes the cache creates a temporary bufferedImage to hold the contents after this finishes.
     */
    public HttpImageFetchWorker(ImageCacheAccessInterface cache, String url) {
        this.cache = cache;
        this.url = url;
    }

    @Override
    protected BufferedImage doInBackground() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(URI.create(url).toURL());
        } catch (IOException e) {
            image = placeHolderImage;
            //throw new RuntimeException(e);
        }

        return image;
    }

    @Override
    protected void done() {

        try {
            // g.drawImage(get(), 0, 0, null);
            cache.cacheImage(url, get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage readPlaceholderImage() {
        try {
            File f = new File("resources/img/Cardback.jpg");
            Image image = ImageIO.read(f).getScaledInstance(245, 342, Image.SCALE_DEFAULT);

            BufferedImage base = new BufferedImage(245, 342, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g = base.createGraphics();
            g.drawImage(image, 0, 0, 245, 342, new Color(0, true), null);
            g.dispose();

            return base;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
