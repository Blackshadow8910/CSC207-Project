package entity;

import java.util.ArrayList;
import java.util.List;

public class PokemonCard extends Card {
    public final List<String> types;
    public final List<String> subtypes;
    public final String setID;
    public final double marketPrice;

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

    public PokemonCard(
            String name,
            String id,
            String imageURL,
            List<String> types
    ) {
        this(name, id, imageURL, types, new ArrayList<>(), "",0.0);
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
