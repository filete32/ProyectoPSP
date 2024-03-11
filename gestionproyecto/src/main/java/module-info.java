module com.psp.gestionproyecto {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jakarta.mail;
    requires org.apache.commons.net;
    requires java.prefs;

    opens com.psp.gestionproyecto to javafx.fxml;
    opens com.psp.gestionproyecto.controller to javafx.fxml;
    opens com.psp.gestionproyecto.model to javafx.base;
    exports com.psp.gestionproyecto;
    exports com.psp.gestionproyecto.controller;

}