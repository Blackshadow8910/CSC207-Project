package entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class CardBuilder {
    public String name;
    public String id = "0-0";
    public String imageURL;
    public String supertype = Card.SUPERTYPE_POKEMON;

    public CardBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CardBuilder id(String id) {
        this.id = id;
        return this;
    }

    public CardBuilder imageURL(String imageUrl) {
        this.imageURL = imageURL;
        return this;
    }

    /**
     * Returns a cardbuilder from jsonData
     */
    public CardBuilder loadData(JSONObject data) {
        name = data.getString("name");
        id = data.getString("id");
        imageURL = data.getJSONObject("images").getString("small");

        return this;
    }

    public abstract Card build();

    public static CardBuilder from(JSONObject data) {
        if (data.getString("supertype").equals(Card.SUPERTYPE_POKEMON)) {
            return new PokemonCardBuilder().loadData(data);
        }
        return null; // Not implemented yet
    }

    protected ArrayList<String> JSONArrayToStringList(JSONArray jsonArray) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(jsonArray.getString(i));
        }
        return result;
    } 
}
