module lk.ijse.gdse.smhtc {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires com.jfoenix;
    requires net.sf.jasperreports.core;
    requires java.mail;
    ///////////////////////////////////////////////////////////////////
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires jbcrypt;

    opens lk.ijse.gdse.smhtc.config to jakarta.persistence;
    opens lk.ijse.gdse.smhtc.entity to org.hibernate.orm.core;
    ////////////////////////////////////////////////////////////////////
    opens lk.ijse.gdse.smhtc.dto.tm to javafx.base;
    opens lk.ijse.gdse.smhtc.controller to javafx.fxml;
    exports lk.ijse.gdse.smhtc;
}