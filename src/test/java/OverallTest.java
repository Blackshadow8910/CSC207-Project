import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CardSearchInteractorTest.class,
        DeckBrowserInteractorTest.class,
        DeckBuilderInteractorTest.class,
        LoginInteractorTest.class,
        InventoryInteractorTest.class,
        TradeInteractorTest.class,
        SignupInteractorTest.class,
        LoginPresenterTest.class,
        LoginViewTest.class,
        SignupViewModelTest.class,
        SellListingTest.class,
        DeckTest.class,
        CardViewTest.class,
        DeckBuilderViewTest.class,
        AppInteractorTest.class
})
public class OverallTest {

}
