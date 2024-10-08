package appli.accueil;

import appli.StartApplication;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccueilController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void ajouterListe() {

    }

    public void lesTypes(){

    }

    public void deconnexion() throws IOException {
        StartApplication.changeScene("accueil/loginView.fxml");
    }

}
