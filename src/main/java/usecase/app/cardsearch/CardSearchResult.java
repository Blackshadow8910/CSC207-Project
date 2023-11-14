package usecase.app.cardsearch;

import java.awt.Image;

import entity.Card;

public class CardSearchResult {
    public final Card card;
    public final Image image;

    public CardSearchResult(Card card, Image image) {
        this.card = card;
        this.image = image;
    }
}
