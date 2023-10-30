package data_access.pokemon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.awt.Color;
import java.awt.Image;

import javax.imageio.ImageIO;

import data_access.image.ImageCacheAccessInterface;
import entity.Card;

public class TestCardDataAccessObject implements PokemonCardDataAccessInterface, ImageCacheAccessInterface {

    public ArrayList<Card> cards = new ArrayList<>();
    public BufferedImage placeHolderImage = readPlaceholderImage();

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
    
    @Override
    public Card getCard(String id) {
        for (Card card : cards) {
            if (card.id.equals(id)) {
                return card;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Card> searchCards(PokemonGuruCardSearchFilter filter) {
        ArrayList<Card> results = new ArrayList<>();
        for (Card card : cards) {
            if (card.name.contains(filter.name)) {
                results.add(card);
            }
        }
        return results;
    }

    @Override
    public ArrayList<Card> searchCards(String query) {
        return searchCards(new PokemonGuruCardSearchFilter(query));
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
