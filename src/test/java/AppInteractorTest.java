import interface_adapters.app.AppPresenter;
import interface_adapters.app.AppViewModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import usecase.app.AppInteractor;
import usecase.app.AppOutputBoundary;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class AppInteractorTest {

    @Mock
    private AppOutputBoundary mockPresenter;

    private AppInteractor appInteractor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        appInteractor = new AppInteractor(mockPresenter);
    }

    @Test
    public void testChangeTab() {
        // Arrange
        String mockTab = "MockTab";

        AppViewModel viewModel = new AppViewModel();
        AppOutputBoundary presenter = new AppPresenter(viewModel);
        // Act
        presenter.changeTab(mockTab);

        // Assert
        // You can add more specific verifications based on your implementation
        assertEquals(mockTab,viewModel.currentTab);
    }
}
