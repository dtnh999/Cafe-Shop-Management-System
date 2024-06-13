package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class cardProductControl implements Initializable {

    @FXML
    private TextField prod_amounts;

    @FXML
    private Label prod_price;

    @FXML
    private Button prod_addBtn;

    @FXML
    private AnchorPane card_form;

    @FXML
    private Label prod_name;

    @FXML
    private ImageView prod_imageView;

    private Image image;

    private String prodID;

    private String type;
    private String prod_date;
    private String prod_image;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;

    private float pr;

    public void setData(productData prodData) {
        prod_image = prodData.getImage();
        prod_date = String.valueOf(prodData.getDate());
        type = prodData.getType();
        prodID = prodData.getProductId();
        prod_name.setText(prodData.getProductName());
        prod_price.setText(String.valueOf(prodData.getPrice()) + " đ");
        String path = "File:" + prodData.getImage();
        image = new Image(path, 100, 100, false, true);
        prod_imageView.setImage(image);
        pr = prodData.getPrice();
    }

    private int qty;
    private float totalP;

    public void addBtn() {
        mainFormFoodControl mForm = new mainFormFoodControl();
        mForm.customerID();

        String inputText = prod_amounts.getText();

        try {
            qty = (inputText != null && !inputText.isEmpty()) ? Integer.parseInt(inputText) : 0;
        } catch (NumberFormatException e) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid quantity (numeric value).");
            alert.showAndWait();
            return;
        }

        connect = database.connectDB();

        try {
            int checkStck = 0;
            String checkStock = "SELECT stock FROM product WHERE pro_id = ?";
            prepare = connect.prepareStatement(checkStock);
            prepare.setString(1, prodID);
            result = prepare.executeQuery();

            if (result.next()) {
                checkStck = result.getInt("stock");
            }

            if (checkStck == 0) {
                String updateStock = "UPDATE product SET pro_name = ?, type = ?, stock = 0, price = ?, status = 'Unavailable', image = ?, date = ? WHERE pro_id = ?";
                prepare = connect.prepareStatement(updateStock);
                prepare.setString(1, prod_name.getText());
                prepare.setString(2, type);
                prepare.setFloat(3, pr);
                prepare.setString(4, prod_image);
                prepare.setString(5, prod_date);
                prepare.setString(6, prodID);
                prepare.executeUpdate();
            }

            String checkAvailable = "SELECT status FROM product WHERE pro_id = ?";
            prepare = connect.prepareStatement(checkAvailable);
            prepare.setString(1, prodID);
            result = prepare.executeQuery();

            String check = "";
            if (result.next()) {
                check = result.getString("status");
            }

            if (!check.equals("Available") || qty == 0) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Something Wrong!");
                alert.showAndWait();
            } else {
                prod_image = prod_image.replace("\\", "\\\\");

                if (checkStck < qty) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid. This product is Out of Stock");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO customer (prod_id, prod_name, type, quantity, price, date, image, em_username) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, prodID);
                    prepare.setString(2, prod_name.getText());
                    prepare.setString(3, type);
                    prepare.setInt(4, qty);

                    totalP = qty * pr;
                    prepare.setFloat(5, totalP);

                    Date date = new Date(System.currentTimeMillis());
                    prepare.setDate(6, date);
                    
                    prepare.setString(7, prod_image);
                    prepare.setString(8, data.username);

                    prepare.executeUpdate();

                    int upStock = checkStck - qty;

                    String updateStock = "UPDATE product SET pro_name = ?, type = ?, stock = ?, price = ?, status = ?, image = ?, date = ? WHERE pro_id = ?";
                    prepare = connect.prepareStatement(updateStock);
                    prepare.setString(1, prod_name.getText());
                    prepare.setString(2, type);
                    prepare.setInt(3, upStock);
                    prepare.setFloat(4, pr);
                    prepare.setString(5, check);
                    prepare.setString(6, prod_image);
                    prepare.setString(7, prod_date);
                    prepare.setString(8, prodID);

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    
                    mForm.menuGetTotal();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization code
    }
}
