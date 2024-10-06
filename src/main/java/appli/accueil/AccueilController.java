package appli.accueil;

import appli.StartApplication;
import java.io.IOException;

public class AccueilController {
    public void deconnexion() throws IOException {
        StartApplication.changeScene("accueil/loginView.fxml");
    }
}
