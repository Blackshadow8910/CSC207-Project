package api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.PokemonCard;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PokemonTCGApiInteractor {
    private String apiKey;
    private OkHttpClient httpClient;

    public PokemonTCGApiInteractor() {
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

    public ArrayList<PokemonCard> searchCards(String query) {
        ArrayList<PokemonCard> result = new ArrayList<>();
        Request req = getBaseRequest("cards?q=name:%s&pageSize=10".formatted(query));
        
        try {
            Response res = httpClient.newCall(req).execute();
            if (!res.isSuccessful()) { throw new RuntimeException("Error when searching"); }
            JSONObject resData = new JSONObject(res.body().string());

            JSONArray datalist = resData.getJSONArray("data");
            for (int i = 0; i < datalist.length(); i++) {
                JSONObject carddata = datalist.getJSONObject(i);

                PokemonCard card = new PokemonCard.Builder()
                        .id(carddata.getString("id"))
                        .name(carddata.getString("name"))
                        .description(carddata.getString("supertype"))
                        .element(carddata.getJSONArray("types").getString(0))
                        .build();
                result.add(card);
            }

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    } 

    public PokemonCard getCard() {
        return new PokemonCard();
    }
}
