package entity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;

public class PokemonGuruCardSearchFilter {
    public String name = "";
    public ArrayList<String> types = new ArrayList<>();
    public ArrayList<String> subtypes = new ArrayList<>();
    public String setID = "";
    public double minPrice = 0;
    public double maxPrice = Double.MAX_VALUE;

    /**
     * Returns a formatted string query to be given to the search api.
     */
    public String getQuery() {
        try {
            String result = "supertype:Pokémon " + "name:\"%s\"".formatted(
                    URLEncoder.encode(name, "UTF-8")
            ).replaceAll("%2B", " ") + " ";

            if (!types.isEmpty()) {
                String text = "";

                for (String type : types) {
                    if (text.length() > 0) {
                        text += " OR ";
                    }
                    text += "types:%s".formatted(encodeString(type));
                }

                result += "(%s) ".formatted(text);
            }

            if (!subtypes.isEmpty()) {
                String text = "";

                ArrayList<String> exclude = new ArrayList<>();
                for (String subtype : subtypes) {
                    if (subtype.startsWith("-")) {
                        exclude.add(subtype.substring(1));
                    }
                    if (text.length() > 0) {
                        text += " OR ";
                    }

                    text += "subtypes:%s".formatted(encodeString(subtype));
                }

                result += "(%s) ".formatted(text);

                text = "";
                for (String subtype : exclude) {
                    if (text.length() > 0) {
                        text += " AND ";
                    }

                    text += "-subtypes:%s".formatted(encodeString(subtype));
                }

                result += "(%s) ".formatted(text);
            }

            if (!setID.isEmpty()) {
                result += "set.id:\"%s\" ".formatted(setID);
            }

            if (minPrice != 0 || maxPrice != Double.MAX_VALUE) {
                result += "cardmarket.prices.averageSellPrice:[%s TO %s] ".formatted(minPrice, Math.min(maxPrice, 1000000));
            }

            System.out.println(result);
            return result.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String encodeString(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "UTF-8");
    }

    public PokemonGuruCardSearchFilter() {

    }

    public PokemonGuruCardSearchFilter(String query) {
        this.name = query;
        if (query.isEmpty()) {
            this.name = "*";
        }
    }

    public PokemonGuruCardSearchFilter setName(String name) {
        this.name = name;
        return this;
    }

    public PokemonGuruCardSearchFilter clone() {
        return new PokemonGuruCardSearchFilter(getQuery())
                .addTypes(types)
                .addSubtypes(subtypes)
                .setSetID(setID)
                .setMinPrice(minPrice)
                .setMaxPrice(maxPrice);
    }

    public PokemonGuruCardSearchFilter addType(String type) {
        types.add(type);
        return this;
    }
    public PokemonGuruCardSearchFilter addTypes(Collection<String> types) {
        for (String type : types) {
            addType(type);
        }
        return this;
    }

    public PokemonGuruCardSearchFilter clearTypes() {
        types.clear();
        return this;
    }

    public PokemonGuruCardSearchFilter addSubtype(String type) {
        subtypes.add(type);
        return this;
    }

    public PokemonGuruCardSearchFilter addSubtypes(Collection<String> types) {
        for (String type : types) {
            addSubtype(type);
        }
        return this;
    }

    public PokemonGuruCardSearchFilter clearSubtypes() {
        subtypes.clear();
        return this;
    }

    public PokemonGuruCardSearchFilter setSetID(String setID) {
        this.setID = setID;
        return this;
    }

    public PokemonGuruCardSearchFilter setMinPrice(double minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public PokemonGuruCardSearchFilter setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }
}
