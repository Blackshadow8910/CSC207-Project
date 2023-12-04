package entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PokemonCardBuilder extends CardBuilder {

    public List<String> types = List.of("normal");
    public List<String> subtypes = List.of("Basic");
    public String setID = "";
    public double marketPrice = 0;

    @Override
    public CardBuilder loadData(JSONObject data) {
        super.loadData(data);
        types = JSONArrayToStringList(data.getJSONArray("types"));
        subtypes = JSONArrayToStringList(data.getJSONArray("subtypes"));
        setID = data.getJSONObject("set").getString("id");
        try {
            JSONObject priceData = data.getJSONObject("cardmarket").getJSONObject("prices");
            //marketPrice = priceData.getJSONObject(priceData.keys().next()).getDouble("market");
            marketPrice = priceData.getDouble("averageSellPrice");
        } catch (JSONException e) {
            e.printStackTrace();
            marketPrice = 0;
        }

        return this;
    }

    @Override
    public Card build() {
        return new PokemonCard(
                name,
                id,
                imageURL,
                types,
                subtypes,
                setID,
                marketPrice
        );
    }

}