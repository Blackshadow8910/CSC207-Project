package entity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

public class PokemonGuruCardSearchFilter {
    public String name = "";
    public final ArrayList<String> types = new ArrayList<>();
    public final ArrayList<String> subtypes = new ArrayList<>();
    public String setID = "";
    public double minPrice = 0;
    public double maxPrice = Double.MAX_VALUE;

    /**
     * Returns a formatted string query to be given to the search api.
     */
    public String getQuery() {
        try {
            String result = "supertype:Pokémon " + "name:\"%s\"".formatted(
                    URLEncoder.encode(name, StandardCharsets.UTF_8)
            ).replaceAll("%2B", " ") + " ";

            if (!types.isEmpty()) {
                StringBuilder text = new StringBuilder();

                for (String type : types) {
                    if (text.length() > 0) {
                        text.append(" OR ");
                    }
                    text.append("types:%s".formatted(encodeString(type)));
                }

                result += "(%s) ".formatted(text.toString());
            }

            if (!subtypes.isEmpty()) {
                StringBuilder text = new StringBuilder();

                ArrayList<String> exclude = new ArrayList<>();
                for (String subtype : subtypes) {
                    if (subtype.startsWith("-")) {
                        exclude.add(subtype.substring(1));
                        continue;
                    }
                    if (text.length() > 0) {
                        text.append(" AND ");
                    }

                    text.append("subtypes:%s".formatted(encodeString(subtype)));
                }

                result += "(%s) ".formatted(text.toString());

                text = new StringBuilder();
                for (String subtype : exclude) {
                    if (text.length() > 0) {
                        text.append(" AND ");
                    }

                    text.append("-subtypes:%s".formatted(encodeString(subtype)));
                }

                if (text.length() > 0)
                    result += "(%s) ".formatted(text.toString());
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

    private String encodeString(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
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
