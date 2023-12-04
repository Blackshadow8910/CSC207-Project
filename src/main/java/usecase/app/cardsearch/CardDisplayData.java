package usecase.app.cardsearch;

import entity.Card;

import java.awt.*;

public class CardDisplayData {
    public final Card card;
    public final Image image;

    public CardDisplayData(Card card, Image image) {
        this.card = card;
        this.image = image;
    }
}
