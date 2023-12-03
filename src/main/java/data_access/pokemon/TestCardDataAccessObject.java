package data_access.pokemon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.naming.spi.DirectoryManager;

import data_access.image.ImageCacheAccessInterface;
import entity.Card;
import entity.PokemonCard;

public class TestCardDataAccessObject extends ArrayListCardDataAccessObject implements ImageCacheAccessInterface {
    public BufferedImage placeHolderImage = readPlaceholderImage();

    public TestCardDataAccessObject() {
        super();
        cards.add(new PokemonCard(
                "Salamander",
                "salamander",
                "https://images.pokemontcg.io/xy1/1.png",
                List.of("normal"),
                List.of("Basic"),
                "CringeSet",
                12));
        cards.add(new PokemonCard(
                "Horse",
                "horse",
                "https://images.pokemontcg.io/xy1/2.png",
                List.of("Fire"),
                List.of("EX"),
                "Cool Set",
                13)
        );
    }

    @Override
    public BufferedImage getImage(String url) {
        if (url.startsWith("img/")) {
            File f = new File("resources/%s".formatted(url));

            if (f.exists()) {
                Image image;
                try {
                    image = ImageIO.read(f);

                    BufferedImage base = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D g = base.createGraphics();
                    g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), new Color(0, 0, 0, 0), null);
                    g.dispose();

                    return base;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return placeHolderImage;
    }
    
    @Override
    public void cacheImage(String key, BufferedImage image) {
    }
    
    @Override
    public void addImageCacheListener(Consumer<String> listener) {
    }

    @Override
    public void fireImageCacheUpdated(String key) {
    }

    private BufferedImage readPlaceholderImage() {
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
