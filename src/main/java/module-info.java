module appli.todolistjx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens appli.todolistjx to javafx.fxml;
    exports appli.todolistjx;
    exports appli.todolistjx.accueil;
    opens appli.todolistjx.accueil to javafx.fxml;
}