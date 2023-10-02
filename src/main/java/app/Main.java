package app;

import api.DigikeyApiInteractor;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        GUIManager guiManager = new GUIManager();
        try {
            DigikeyApiInteractor.main(args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
