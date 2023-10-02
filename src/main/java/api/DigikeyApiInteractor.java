package api;

import okhttp3.*;
import java.io.IOException;

import org.json.JSONObject;

// Calling the Code in Java to Run the API and Get the Response for the Product Detail.

public class DigikeyApiInteractor {
    public static String oauthToken = "Bearer q7zAohcObSXrzGq3VARRboqDCrPS"; // needs to be set dynamically
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        Request request = new Request.Builder()
                .url("https://api.digikey.com/Search/v3/Products/380-BC-HH006F-ND")
                .method("GET", null)
                .addHeader("X-DIGIKEY-Client-Id", "Rpr2ezZg6K0Mag1sR5ROpS0A69JuZAjH")
                .addHeader("Authorization", oauthToken)
                .addHeader("X-DIGIKEY-Locale-Currency", "CAD")
                .build();
        Response response = client.newCall(request).execute();

        JSONObject responseObject = new JSONObject(response.body().string());
        System.out.println(responseObject.toString());
    }
}
