package entity;

import java.util.ArrayList;
import java.awt.Image;
import java.awt.image.BufferedImage;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A base class for any kind of pokemon tcg card; not neccesarily a pokemon
 */
public class Card {
    public static final String SUPERTYPE_POKEMON = "Pokémon";
    public static final String SUPERTYPE_TRAINER = "Trainer";
    public static final String SUPERTYPE_ENERGY = "Energy";

    public final String id;
    public final String name;
    public final String imageURL;

    // 
    public Card(String name, String id, String imageURL) {
        this.name = name;
        this.id = id;
        this.imageURL = imageURL;
    }

    public static Card from(JSONObject data) {
        return CardBuilder.from(data).build();
    }
}
