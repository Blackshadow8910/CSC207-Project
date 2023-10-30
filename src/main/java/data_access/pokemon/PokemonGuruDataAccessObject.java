package data_access.pokemon;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.net.URL;
import java.net.URI;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Card;
import entity.Card;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PokemonGuruDataAccessObject implements PokemonGuruDataAccessInterface {
    private String apiKey;
    private OkHttpClient httpClient;

    private HashMap<String, Card> cardCache = new HashMap<>();
    private HashMap<String, Image> imageCache = new HashMap<>();

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
    public Image getImage(String url) {
        if (imageCache.containsKey(url)) {
            return imageCache.get(url);
        } else {
            Image image = requestImage(url);
            imageCache.put(url, image);
            return image;
        }
    }

    private Image requestImage(String url) {
        try {
            Image image = ImageIO.read(URI.create(url).toURL());
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
        Request req = getBaseRequest("cards?q=%s&pageSize=10".formatted(query));
        
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
