package usecase;

import java.io.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PokemonTCGApiUseCase {
    private final String apiKey;
    public OkHttpClient httpClient;

    public PokemonTCGApiUseCase() throws IOException {
        File f = new File("resources/pokemon-tcg-api-key.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));

        httpClient = new OkHttpClient().newBuilder()
                .build();


        apiKey = reader.readLine();

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

        reader.close();
    }
}
