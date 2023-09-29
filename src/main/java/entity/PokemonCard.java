package entity;

public class PokemonCard extends Card {
    public String id;
    
    public PokemonCard() {
        
    }

    public PokemonCard(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.element = builder.element;
        this.id = builder.id;
    }

    public static class Builder {
        public String name;
        public String description;
        public Element element = Element.NORMAL;
        public String id = "0-0";

        public Builder name(String name) {
            this.name = name;
            return this;
        }


        public Builder description(String desc) {
            this.description = desc;
            return this;
        }

        public Builder element(String element) {
            //this.element = Element.fromString(element);
            return this;
        }

        public Builder element(Element element) {
            this.element = element;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }


        public PokemonCard build() {
            return new PokemonCard(this);
        }
    }
}
