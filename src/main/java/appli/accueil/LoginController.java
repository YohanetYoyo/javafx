package appli.accueil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private Label erreur;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void setEmailField(TextField textField){
    }
    @FXML
    protected void setPasswordField(PasswordField passwordField){
    }

    @FXML
    protected void connexion() {
        System.out.println("Connexion");
    }
    @FXML
    protected void motDePasseOublie() {
        System.out.println("Mot de passe oublié");
    }
    @FXML
    protected void inscription() {
        System.out.println("Inscription");
    }

    public void setPasswordField(ActionEvent actionEvent) {
    }

    public void setEmailField(ActionEvent actionEvent) {
    }
}