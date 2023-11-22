package data_access.pokemon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.awt.Color;
import java.awt.Image;

import javax.imageio.ImageIO;

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
            "doesn't matter",
            List.of("normal")));
        cards.add(new PokemonCard(
            "Horse", 
            "horse",
            "doesn't matter",
            List.of("normal")));
        cards.add(new PokemonCard(
            "Pookichu", 
            "pikamon",
            "pica",
            List.of("electric")));
        cards.add(new PokemonCard(
            "Goober", 
            "gober",
            "stu",
            List.of("what")));
        cards.add(new PokemonCard(
            "Frep", 
            "freb",
            "ferb",
            List.of("derf")));
        cards.add(new PokemonCard(
            "junston", 
            "ppoo",
            "h",
            List.of("torp")));
    }

    @Override
    public BufferedImage getImage(String url) {
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

            base.createGraphics().drawImage(image, 0, 0, 245, 342, Color.WHITE, null);

            return base;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
