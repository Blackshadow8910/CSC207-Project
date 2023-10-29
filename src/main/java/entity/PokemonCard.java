package entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PokemonCard extends Card {
    public List<String> types;

    public PokemonCard(
        String name,
        String id,
        String imageURL,
        List<String> types
    ) {
        super(name, id, imageURL);
        this.types = types;
    }

    // public PokemonCard(JSONObject data) {
    //     super(data.getString("name"), data.getString("id"), );
    //     try {
    //         types = JSONArrayToStringList(data.getJSONArray("types"));

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }


}
