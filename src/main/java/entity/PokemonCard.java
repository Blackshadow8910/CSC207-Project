package entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PokemonCard extends Card {
    public List<String> types;
    public List<String> subtypes;
    public String setID;
    public double marketPrice;

    public PokemonCard(
            String name,
            String id,
            String imageURL,
            List<String> types,
            List<String> subtypes,
            String setID,
            double marketPrice
    ) {
        super(name, id, imageURL);
        this.types = types;
        this.subtypes = subtypes;
        this.setID = setID;
        this.marketPrice = marketPrice;
    }
    public List<String> get_type()
    {
        return this.types;
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