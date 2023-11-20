package interface_adapters.app.inventory;

public class InventoryPresenter implements _OutputBoundary {
    private InventoryViewModel viewModel;
    
    public InventoryPresenter(InventoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void present(_OutputData outputData) {

    }
}
