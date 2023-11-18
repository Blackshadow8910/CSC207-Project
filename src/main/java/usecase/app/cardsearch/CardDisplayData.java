package usecase.app.cardsearch;

import java.awt.Image;

import entity.Card;

public class CardDisplayData {
    public final Card card;
    public final Image image;

    public CardDisplayData(Card card, Image image) {
        this.card = card;
        this.image = image;
    }
}
