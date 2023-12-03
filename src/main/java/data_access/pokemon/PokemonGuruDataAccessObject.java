package data_access.pokemon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

import entity.PokemonGuruCardSearchFilter;
import org.json.JSONArray;
import org.json.JSONObject;

import data_access.image.HttpImageFetchWorker;
import data_access.image.ImageCacheAccessInterface;
import entity.Card;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PokemonGuruDataAccessObject implements PokemonCardDataAccessInterface, ImageCacheAccessInterface {
    private String apiKey;
    private OkHttpClient httpClient;

    private HashMap<String, Card> cardCache = new HashMap<>();
    private HashMap<String, HttpImageFetchWorker> imageCache = new HashMap<>();


    public PokemonGuruDataAccessObject() {
        try {
            File f = new File("resources/pokemon-tcg-api-key.txt");
            BufferedReader reader = new BufferedReader(new FileReader(f));
            
            httpClient = new OkHttpClient().newBuilder()
            .build();
            
            
            String key = reader.readLine();
            reader.close();

            apiKey = key;

            Request req = new Request.Builder()
                .url("https://api.pokemontcg.io/v2/cards?q=name:bulbasaur&pageSize=1")
                .addHeader("X-Api-Key", apiKey)
                .build();

            Response res = httpClient.newCall(req).execute();
            if (!res.isSuccessful()) {
                throw new RuntimeException("Api key invalid");
            } else {
                System.out.println("Valid Api key found");
            }

        } catch (IOException e) {
            e.printStackTrace();
            apiKey = "";
        }
    }

    /*
     * API helpers
     */
    
        private Request getBaseRequest(String path, String method, RequestBody body) {
            return new Request.Builder()
                    .url("https://api.pokemontcg.io/v2/" + path)
                    .method(method, body)
                    .addHeader("X-Api-Key", apiKey)
                    .build();
        }
        
        private Request getBaseRequest(String path) {
            return new Request.Builder()
                    .url("https://api.pokemontcg.io/v2/" + path)
                    .addHeader("X-Api-Key", apiKey)
                    .build();
        }


    /*
    * API methods
    */
    
    @Override
    public Card getCard(String id) {
        if (cardCache.containsKey(id)) {
            return cardCache.get(id);
        } else {
            return requestCard(id);
        }
    }

    private Card requestCard(String id) {
        Request req = getBaseRequest("cards/%s".formatted(id));

        try {
            Response res = httpClient.newCall(req).execute();
            if (!res.isSuccessful()) { throw new RuntimeException("Error when searching"); }
            JSONObject resData = new JSONObject(res.body().string());

            Card card = parseCard(resData.getJSONObject("data"));
            return card;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public BufferedImage getImage(String url) {
        if (url.startsWith("img/")) {
            return getLocalImage(url);
        }

        try {
            if (imageCache.containsKey(url)) {
                return imageCache.get(url).get();
            } else {
                requestImage(url);
                return imageCache.get(url).get();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private BufferedImage getLocalImage(String url) {
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

        return null;
    }

    private void requestImage(String url) {
        HttpImageFetchWorker fetcher = new HttpImageFetchWorker(this, url);
        fetcher.execute();

        imageCache.put(url, fetcher);
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

    /**
     * Parses a card from a jsonobject and adds it to the cache before returning it.
     * 
     * @param carddata A json object containing the details of a pokemon card from pokemon guru's api
     */
    private Card parseCard(JSONObject carddata) {
        Card card = Card.from(carddata);
        cardCache.put(card.id, card);
        return card;
    }

    public ArrayList<Card> searchCards(PokemonGuruCardSearchFilter filter) {
        ArrayList<Card> result = new ArrayList<>();
        String query = filter.getQuery();
        Request req = getBaseRequest("cards?q=%s&pageSize=20".formatted(query));
        
        try {
            Response res = httpClient.newCall(req).execute();
            if (!res.isSuccessful()) { throw new RuntimeException("Error when searching"); }
            JSONObject resData = new JSONObject(res.body().string());

            JSONArray datalist = resData.getJSONArray("data");
            
            for (int i = 0; i < datalist.length(); i++) {
                JSONObject carddata = datalist.getJSONObject(i);

                Card card = parseCard(carddata);
                result.add(card);
            }

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    } 

    public ArrayList<Card> searchCards(String query) {
        return searchCards(new PokemonGuruCardSearchFilter(query));
    }
}
