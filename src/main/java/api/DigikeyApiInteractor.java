package api;

import okhttp3.*;
import java.io.IOException;

// Calling the Code in Java to Run the API and Get the Response for the Product Detail.

public class DigikeyApiInteractor {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.digikey.com/Search/v3/Products/380-BC-HH006F-ND")
                .method("GET", body)
                .addHeader("X-DIGIKEY-Client-Id", "Rpr2ezZg6K0Mag1sR5ROpS0A69JuZAjH")
                .addHeader("Authorization", "Bearer q7zAohcObSXrzGq3VARRboqDCrPS")
                .addHeader("X-DIGIKEY-Locale-Currency", "CAD")
                .addHeader("Cookie", "TS012a951d=01460246b6c15d8aa2405e4fc2e5f7bea0e24e6e2aba5dd279e3ab336c076d6355f5f0b76ccf626bcaa2793f905dc550d10e341a1a")
                .build();
        Response response = client.newCall(request).execute();
    }
}
