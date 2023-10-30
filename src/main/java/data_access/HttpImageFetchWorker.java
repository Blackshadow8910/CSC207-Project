package data_access;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

public class HttpImageFetchWorker extends SwingWorker<BufferedImage, Object> {

    public String url;
    public ImageCacheAccessInterface cache;

    /**
     * Will store the processed image in a bufferedimage in the image cache. 
     * This assumes the cache creates a temporary bufferedImage to hold the contents after this finishes.
     * @param cache
     * @param url
     */
    public HttpImageFetchWorker(ImageCacheAccessInterface cache, String url) {
        this.cache = cache;
        this.url = url;
    }

    @Override
    protected BufferedImage doInBackground() throws Exception {
        BufferedImage image = ImageIO.read(URI.create(url).toURL());

        return image;
    }

    @Override
    protected void done() {
        // BufferedImage target = cache.getImage(url);

        // Graphics2D g = target.createGraphics();
        try {
            // g.drawImage(get(), 0, 0, null);
            cache.cacheImage(url, get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    
}
