package entity;

import java.util.List;

import org.json.JSONObject;

public class PokemonCardBuilder extends CardBuilder {

    public List<String> types = List.of("normal");

    @Override
    public CardBuilder loadData(JSONObject data) {
        super.loadData(data);
        types = JSONArrayToStringList(data.getJSONArray("types"));

        return this;
    }

    @Override
    public Card build() {
        return new PokemonCard(name, id, imageURL, types);
    }
    
}
