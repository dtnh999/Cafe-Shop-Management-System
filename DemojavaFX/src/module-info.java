module DemojavaFX {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	requires jasperreports;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	
}
