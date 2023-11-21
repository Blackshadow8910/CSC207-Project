package view.app;

import interface_adapters.app.inventory.InventoryController;
import interface_adapters.app.inventory.InventoryViewModel;
import javax.swing.*;

public class InventoryView extends JPanel {
    private InventoryViewModel viewModel;
    private InventoryController controller;

    public InventoryView(InventoryViewModel viewModel, InventoryController controller) {
        this.viewModel = viewModel;
        this.controller = controller;
    }
}
