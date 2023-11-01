package view;

import javax.swing.JPanel;

import interface_adapters.signup.SignupController;
import interface_adapters.signup.SignupViewModel;

public class SignupView extends JPanel {
    private SignupViewModel viewModel;
    private SignupController controller;

    public SignupView(SignupViewModel viewModel, SignupController controller) {
        this.viewModel = viewModel;
        this.controller = controller;
    }
}
