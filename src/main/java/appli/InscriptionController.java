package appli;

import appli.StartApplication;
import javafx.fxml.FXML;

import java.io.IOException;

public class InscriptionController {

    @FXML
    protected void inscription() {
        System.out.println("Inscription");
    }
    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("accueil/loginView.fxml");
    }
}