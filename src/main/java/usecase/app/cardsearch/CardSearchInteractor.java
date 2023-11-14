package usecase.app.cardsearch;

public class CardSearchInteractor implements CardSearchInputBoundary {
    private CardSearchOutputBoundary presenter;

    public CardSearchInteractor(CardSearchOutputBoundary presenter) {
        this.presenter = presenter;
    }

    public void execute(CardSearchInputData inputData) {

    } 
}
