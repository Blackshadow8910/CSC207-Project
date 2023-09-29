package entity;

public enum Element {
    NORMAL,
    FIRE,
    WATER,
    GRASS,
    GROUND,
    PSYCHIC,
    FAIRY,
    GHOST,
    LIGHTNING,
    STEEL,
    ROCK;

    public static Element fromString(String src) {
        for (Element e : Element.values()) {
            if (e.name().equalsIgnoreCase(src)) {
                return e;
            }
        }
        throw new PokemonElementNotFoundException("elementNotFound");
    }

    public static class PokemonElementNotFoundException extends RuntimeException {
        public PokemonElementNotFoundException(String s) {
            super(s);
        }
    }
}
