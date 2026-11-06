module ca.qc.bdeb.sim.tp_pratique {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;


    opens ca.qc.bdeb.sim.tp_pratique to javafx.fxml;
    exports ca.qc.bdeb.sim.tp_pratique;
}