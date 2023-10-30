package data_access.pokemon;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.URIParameter;

public class PokemonGuruCardSearchFilter {
    public String name = "";

    /**
     * Returns a formatted string query to be given to the search api.
     */
    public String getQuery() {
        try {
            String result = "name:\"%s\"".formatted(URLEncoder.encode(name, "UTF-8"));
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public PokemonGuruCardSearchFilter() {

    }

    public PokemonGuruCardSearchFilter(String query) {
        this.name = query;
    }

    public PokemonGuruCardSearchFilter setName(String name) {
        this.name = name;
        return this;
    }
}
