package application;

import java.io.File;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

// Add more libraries to resolve this issue

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class mainFormFoodControl implements Initializable {
	@FXML
	private TableView<productData> inventory_tableView;

	@FXML
	private Button inventory_btn;

	@FXML
	private ImageView inventory_imageView;

	@FXML
	private TableColumn<productData, String> inventory_col_stock;

	@FXML
	private AnchorPane inventory_form;

	@FXML
	private AnchorPane main_form;

	@FXML
	private Button dashboard_btn;

	@FXML
	private TableColumn<productData, String> inventory_col_price;

	@FXML
	private Button logout_btn;

	@FXML
	private TableColumn<productData, String> inventory_col_productName;

	@FXML
	private TableColumn<productData, String> inventory_col_status;

	@FXML
	private TableColumn<productData, String> inventory_col_date;

	@FXML
	private Button inventory_clearBtn;

	@FXML
	private Button inventory_importBtn;

	@FXML
	private TableColumn<productData, String> inventory_col_type;

	@FXML
	private Button inventory_updateBtn;

	@FXML
	private Button customers_btn;

	@FXML
	private Button inventory_deleteBtn;

	@FXML
	private Button menu_btn;

	@FXML
	private TableColumn<productData, String> inventory_col_productID;

	@FXML
	private Button inventory_addBtn;

	@FXML
	private Label username;

	private Alert alert;

	@FXML
	private TextField inventory_productID;

	@FXML
	private TextField inventory_price;

	@FXML
	private TextField inventory_productName;

	@FXML
	private TextField inventory_stock;

	@FXML
	private ComboBox<?> inventory_status;

	@FXML
	private ComboBox<?> inventory_type;

	@FXML
	private TableColumn<productData, String> menu_col_quantity;

	@FXML
	private TableView<productData> menu_tableView;

	@FXML
	private ScrollPane menu_scrollPane;

	@FXML
	private Label menu_total;

	@FXML
	private Button menu_receiptBtn;

	@FXML
	private TableColumn<productData, String> menu_col_price;

	@FXML
	private TableColumn<productData, String> menu_col_productName;

	@FXML
	private AnchorPane menu_form;

	@FXML
	private GridPane menu_gridPane;

	@FXML
	private Label menu_change;

	@FXML
	private TextField menu_amount;

	@FXML
	private Button menu_removeBtn;

	@FXML
	private Button menu_payBtn;

	@FXML
	private AnchorPane dashboard_form;

	@FXML
	private AnchorPane customers_form;

	@FXML
	private TableColumn<customersData, String> customers_col_cashier;

	@FXML
	private TableView<customersData> customers_tableView;

	@FXML
	private TableColumn<customersData, String> customers_col_customerID;

	@FXML
	private TableColumn<customersData, String> customers_col_total;

	@FXML
	private TableColumn<customersData, String> customers_col_date;

	@FXML
	private BarChart<?, ?> dashboard_CustomerChart;

	@FXML
	private Label dashboard_NC;

	@FXML
	private AreaChart<?, ?> dashboard_incomeChart;

	@FXML
	private Label dashboard_NSP;

	@FXML
	private Label dashboard_TI;

	@FXML
	private Label dashboard_TotalI;

	private Connection connect;
	private PreparedStatement prepare;
	private Statement statement;
	private ResultSet result;

	private Image image;

	private ObservableList<productData> cardListData = FXCollections.observableArrayList();
	
	public void dashboardDisplayNC() {
		String sql = "SELECT COUNT(id) AS totalid FROM receipt";

		connect = database.connectDB();

		try {
			int nc = 0;
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			if (result.next()) {
				nc = result.getInt("totalid");
			}

			dashboard_NC.setText(String.valueOf(nc));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dashboardDisplayTI() {
		Date date = new Date(System.currentTimeMillis());
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		String sql = "SELECT SUM(total) AS sumtotal1 FROM receipt WHERE date = '" + sqlDate + "'";
		connect = database.connectDB();

		try {
			float ti = 0;
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			if (result.next()) {
				ti = result.getFloat("sumtotal1");

			}

			dashboard_TI.setText("VND " + ti);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void dashboardTotalI() {
		String sql = "SELECT SUM(total) AS sumtotal FROM receipt ";
		connect = database.connectDB();
		try {
			float ti = 0;
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			if (result.next()) {
				ti = result.getFloat("sumtotal");

			}
			dashboard_TotalI.setText("VND " + ti);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dashboardNSP() {

		String sql = "SELECT COUNT(quantity) AS countq FROM customer ";
		connect = database.connectDB();
		try {
			int q = 0;
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			if (result.next()) {
				q = result.getInt("countq");
			}
			dashboard_NSP.setText(String.valueOf(q));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dashboardIncomeChart() {
		dashboard_incomeChart.getData().clear();

		String sql = "SELECT CAST(date AS DATE) AS date, SUM(total) AS sumtotal2 FROM receipt GROUP BY CAST(date AS DATE)";
		connect = database.connectDB();
		XYChart.Series chart = new XYChart.Series();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			while (result.next()) {
				chart.getData().add(new XYChart.Data<>(result.getString(1), result.getFloat(2)));
			}

			dashboard_incomeChart.getData().add(chart);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dashboardCustomerChart() {
		dashboard_CustomerChart.getData().clear();

		String sql = "SELECT CAST(date AS DATE) AS date, COUNT(id) AS countid2 FROM receipt GROUP BY CAST(date AS DATE)";
		connect = database.connectDB();
		XYChart.Series chart = new XYChart.Series();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			while (result.next()) {
				chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
			}

			dashboard_CustomerChart.getData().add(chart);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void inventoryAddBtn() {
		if (inventory_productID.getText().isEmpty() || inventory_productName.getText().isEmpty()
				|| inventory_type.getSelectionModel().getSelectedItem() == null || inventory_stock.getText().isEmpty()
				|| inventory_price.getText().isEmpty() || inventory_status.getSelectionModel().getSelectedItem() == null
				|| data.path == null) {

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all blank fields");
			alert.showAndWait();

		} else {

			String checkProdID = "SELECT pro_id FROM product WHERE pro_id = '" + inventory_productID.getText() + "'";
			connect = database.connectDB();

			try {
				statement = connect.createStatement();
				result = statement.executeQuery(checkProdID);

				if (result.next()) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Messages");
					alert.setHeaderText(null);
					alert.setContentText(inventory_productID.getText() + " is already taken");
					alert.showAndWait();
				} else {
					String insertData = "INSERT INTO product"
							+ "(pro_id, pro_name, type, stock, price, status, image, date)" + "VALUES(?,?,?,?,?,?,?,?)";
					prepare = connect.prepareStatement(insertData);
					prepare.setString(1, inventory_productID.getText());
					prepare.setString(2, inventory_productName.getText());
					prepare.setString(3, (String) inventory_type.getSelectionModel().getSelectedItem());
					prepare.setString(4, inventory_stock.getText());
					prepare.setString(5, inventory_price.getText());
					prepare.setString(6, (String) inventory_status.getSelectionModel().getSelectedItem());

					String path = data.path;
					path = path.replace("\\", "\\\\");
					prepare.setString(7, path);

					// get current date
					Date date = new Date(System.currentTimeMillis());
					java.sql.Date sqlDate = new java.sql.Date(date.getTime());

					prepare.setString(8, String.valueOf(sqlDate));

					prepare.executeUpdate();

					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Successfully Added");
					alert.showAndWait();

					inventoryShowData();
					inventoryClearBtn();

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void inventoryUpdateBtn() {
		if (inventory_productID.getText().isEmpty() || inventory_productName.getText().isEmpty()
				|| inventory_type.getSelectionModel().getSelectedItem() == null || inventory_stock.getText().isEmpty()
				|| inventory_price.getText().isEmpty() || inventory_status.getSelectionModel().getSelectedItem() == null
				|| data.path == null || data.id == 0) {

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all blank fields");
			alert.showAndWait();

		} else {
			String path = data.path;
			path = path.replace("\\", "\\\\");

			String updateData = "UPDATE product SET " + "pro_id = '" + inventory_productID.getText() + "', pro_name = '"
					+ inventory_productName.getText() + "', type = '"
					+ inventory_type.getSelectionModel().getSelectedItem() + "', stock = '" + inventory_stock.getText()
					+ "', price = '" + inventory_price.getText() + "', status = '"
					+ inventory_status.getSelectionModel().getSelectedItem() + "', image = '" + path + "', date = '"
					+ data.date + "' WHERE pro_id = " + data.id;
			connect = database.connectDB();

			try {

				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText(
						"Are you sure you want to UPDATE product ID: " + inventory_productID.getText() + " ?");

				Optional<ButtonType> option = alert.showAndWait();

				if (option.get().equals(ButtonType.OK)) {
					prepare = connect.prepareStatement(updateData);
					prepare.executeUpdate();

					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Message");
					alert.setHeaderText(null);
					alert.setContentText("Successfully Updated");
					alert.showAndWait();
					// To update table view
					inventoryShowData();
					// To clear fields
					inventoryClearBtn();
				} else {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Cancelled");
					alert.showAndWait();
				}

				prepare = connect.prepareStatement(updateData);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void inventoryDeleteBtn() {
		if (data.id == 0) {

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all blank fields");
			alert.showAndWait();

		} else {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to DELETE Product ID: " + inventory_productID.getText() + "?");
			Optional<ButtonType> option = alert.showAndWait();

			if (option.get().equals(ButtonType.OK)) {

				String deleteData = "DELETE FROM product WHERE pro_id = " + data.id;
				try {
					prepare = connect.prepareStatement(deleteData);
					prepare.executeUpdate();
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Successfully Deleted");
					alert.showAndWait();

					inventoryShowData();
					inventoryClearBtn();

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Cancelled");
				alert.showAndWait();
			}

			String deleteData = "DELETE FROM product WHERE pro_id = " + data.id;
		}
	}

	public void inventoryClearBtn() {

		inventory_productID.setText("");
		inventory_productName.setText("");
		inventory_type.getSelectionModel().clearSelection();
		inventory_stock.setText("");
		inventory_price.setText("");
		inventory_status.getSelectionModel().clearSelection();
		data.path = "";
		data.id = 0;
		inventory_imageView.setImage(null);

	}

	// behavior to import image

	public void inventoryImportBtn() {
		FileChooser openFile = new FileChooser();
		openFile.getExtensionFilters().add(new ExtensionFilter("Open File Image", "*png", "*jpg"));
		File file = openFile.showOpenDialog(main_form.getScene().getWindow());

		if (file != null) {
			data.path = file.getAbsolutePath();
			image = new Image(file.toURI().toString(), 100, 100, false, true);

			inventory_imageView.setImage(image);

		}
	}

	// merge all data

	public ObservableList<productData> inventoryDataList() {
		ObservableList<productData> listData = FXCollections.observableArrayList();

		String sql = "SELECT * FROM product";

		connect = database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			productData prodData;

			while (result.next()) {
				prodData = new productData(result.getInt("pro_id"), result.getString("pro_id"),
						result.getString("pro_name"), result.getString("type"), result.getInt("stock"),
						result.getFloat("price"), result.getString("status"), result.getString("image"),
						result.getDate("date"));
				listData.add(prodData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}

	// To show data on our table

	private ObservableList<productData> inventoryListData;

	public void inventoryShowData() {
		inventoryListData = inventoryDataList();

		inventory_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
		inventory_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
		inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
		inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
		inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		inventory_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
		inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

		inventory_tableView.setItems(inventoryListData);
	}

	public void inventorySelectData() {

		productData prodData = inventory_tableView.getSelectionModel().getSelectedItem();
		int num = inventory_tableView.getSelectionModel().getSelectedIndex();

		if ((num - 1) < -1)
			return;

		inventory_productID.setText(prodData.getProductId());
		inventory_productName.setText(prodData.getProductName());
		inventory_stock.setText(String.valueOf(prodData.getStock()));
		inventory_price.setText(String.valueOf(prodData.getPrice()));

		data.path = prodData.getImage();

		String path = "File:" + prodData.getImage();
		data.date = String.valueOf(prodData.getDate());
		data.id = prodData.getId();

		image = new Image(path, 100, 100, false, true);

		inventory_imageView.setImage(image);

	}

	private String[] typeList = { "Coffee", "Tea", "Juice", "Snack" };

	public void inventoryTypeList() {
		List<String> typeL = new ArrayList<>();
		for (String data : typeList) {
			typeL.add(data);
		}

		ObservableList listData = FXCollections.observableArrayList(typeL);
		inventory_type.setItems(listData);
	}

	private String[] statusList = { "Available", "Unavailable" };

	public void inventoryStatusList() {
		List<String> statusL = new ArrayList<>();
		for (String data : statusList) {
			statusL.add(data);
		}
		ObservableList listData = FXCollections.observableArrayList(statusL);
		inventory_status.setItems(listData);
	}

	public ObservableList<productData> menuGetData() {

		String query = "SELECT pro_id, pro_name, type, stock, price, image, date FROM product";

		ObservableList<productData> listData = FXCollections.observableArrayList();
		connect = database.connectDB();

		try {
			prepare = connect.prepareStatement(query);
			result = prepare.executeQuery();

			productData prod;

			while (result.next()) {
				prod = new productData(result.getInt("pro_id"), 
						result.getString("pro_id"), 
						result.getString("pro_name"),
						result.getString("type"), 
						result.getInt("stock"), 
						result.getFloat("price"),
						result.getString("image"), 
						result.getDate("date"));
				listData.add(prod);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listData;
	}

	public void menuDisplayCard() {
		cardListData.clear();
		cardListData.addAll(menuGetData());

		int row = 0;
		int column = 0;

		menu_gridPane.getChildren().clear();
		menu_gridPane.getRowConstraints().clear();
		menu_gridPane.getColumnConstraints().clear();

		for (int q = 0; q < cardListData.size(); q++) {
			try {
				FXMLLoader load = new FXMLLoader();
				load.setLocation(getClass().getResource("/ui/cardProduct.fxml"));
				AnchorPane pane = load.load();
				cardProductControl cardC = load.getController();
				cardC.setData(cardListData.get(q));

				if (column == 3) {
					column = 0;
					row += 1;
				}

				menu_gridPane.add(pane, column++, row);

				GridPane.setMargin(pane, new Insets(10));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public ObservableList<productData> menuGetOrder() {
		customerID();
		ObservableList<productData> listData = FXCollections.observableArrayList();

		String sql = "SELECT * FROM customer WHERE customer_id = " + cID;
		connect = database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			productData prod;

			while (result.next()) {
				prod = new productData(result.getInt("customer_id"), result.getString("prod_id"), result.getString("prod_name"),
						result.getString("type"), result.getInt("quantity"), result.getFloat("price"),
						result.getString("image"), result.getDate("date"));
				listData.add(prod); 
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listData;
	}

	private ObservableList<productData> menuOrderListData;

	public void menuShowOrderData() {
		menuOrderListData = menuGetOrder();
		menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
		menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

		menu_tableView.setItems(menuOrderListData);
	}

	private int getid;

	public void menuSelectOrder() {
		productData prod = menu_tableView.getSelectionModel().getSelectedItem();
		int num = menu_tableView.getSelectionModel().getSelectedIndex();

		if ((num - 1) < -1)
			return;
		// to get id per order
		getid = prod.getId();

	}

	private float totalP;

	public void menuGetTotal() {
		customerID();
		String total = "SELECT SUM(price) AS total_price FROM customer WHERE customer_id = " + cID;

		connect = database.connectDB();
		try {
			prepare = connect.prepareStatement(total);
			result = prepare.executeQuery();

			if (result.next()) {
				totalP = result.getFloat("total_price");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void menuDisplayTotal() {
		menuGetTotal();
		menu_total.setText(totalP + " đ");
	}

	private float amount;
	private float change;

	public void menuAmount() {
		menuGetTotal();
		if (menu_amount.getText().isEmpty() || totalP == 0) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setContentText("Invalid");
			alert.showAndWait();
		} else {
			amount = Float.parseFloat(menu_amount.getText());
			if (amount < totalP) {
				menu_amount.setText("");
			} else {
				change = (amount - totalP);
				menu_change.setText(change + " đ");
			}
		}
	}

	public void menuPayBtn() {
		if (totalP == 0) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please order first!");
			alert.showAndWait();
		} else {
			menuGetTotal();
			String insertPay = "INSERT INTO receipt (customer_id, total, date, em_username)" + "VALUES(?,?,?,?)";

			connect = database.connectDB();
			try {
				if (amount == 0) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Something wrong");
					alert.showAndWait();
				} else {
					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation Message");
					alert.setHeaderText(null);
					alert.setContentText("Are you sure?");
					Optional<ButtonType> option = alert.showAndWait();

					if (option.get().equals(ButtonType.OK)) {
						customerID();
						prepare = connect.prepareStatement(insertPay);
						prepare.setString(1, String.valueOf(cID));
						prepare.setString(2, String.valueOf(totalP));

						Date date = new Date(System.currentTimeMillis());
						java.sql.Date sqlDate = new java.sql.Date(date.getTime());

						prepare.setString(3, String.valueOf(sqlDate));
						prepare.setString(4, data.username);

						prepare.executeUpdate();

						alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Message");
						alert.setHeaderText(null);
						alert.setContentText("Successful");
						alert.showAndWait();

						menuShowOrderData();

					} else {
						alert = new Alert(AlertType.WARNING);
						alert.setTitle("Information Message");
						alert.setHeaderText(null);
						alert.setContentText("Cancelled");
						alert.showAndWait();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void menuRemoveBtn() {
		if (getid == 0) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please select the order you want to remove");
			alert.showAndWait();
		} else {
			System.out.println("Deleting order with id: " + getid); // Print the id before deletion
			String deleteData = "DELETE FROM customer WHERE customer_id = " + getid;
			connect = database.connectDB();
			try {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Message");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure you want to delete this order?");
				Optional<ButtonType> option = alert.showAndWait();

				if (option.get().equals(ButtonType.OK)) {
					prepare = connect.prepareStatement(deleteData);
					prepare.executeUpdate();
				}

				menuShowOrderData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void menuReceiptBtn() {

		if (totalP == 0 || menu_amount.getText().isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please order first?");
			alert.showAndWait();
		} else {
			HashMap map = new HashMap();
			map.put("getReceipt", (cID - 1));

			try {
				JasperDesign jDesign = JRXmlLoader
						.load("C:\\Users\\letua\\eclipse-workspace\\DemojavaFX\\src\\application\\report.jrxml");
				JasperReport jReport = JasperCompileManager.compileReport(jDesign);
				JasperPrint jPrint = JasperFillManager.fillReport(jReport, map, connect);
				JasperViewer.viewReport(jPrint, false);

				menuRestart();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void menuRestart() {
		totalP = 0;
		change = 0;
		amount = 0;

		menu_total.setText("0 đ");
		menu_amount.setText("");
		menu_change.setText("0 đ");
	}

	private int cID;

	public void customerID() {
		String sql = "SELECT MAX(customer_id) AS maxcus FROM customer";
		connect = database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			if (result.next()) {
				cID = result.getInt("maxcus");
			}

			String checkCID = "SELECT MAX(customer_id) AS maxre FROM receipt";
			prepare = connect.prepareStatement(checkCID);
			result = prepare.executeQuery();

			int checkID = 0;
			if (result.next()) {
				checkID = result.getInt("maxre");
			}

			if (cID == 0) {
				cID += 1;
			} else if (cID == checkID) {
				cID += 1;
			}

			data.cID = cID;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ObservableList<customersData> customersDataList() {
		ObservableList<customersData> listData = FXCollections.observableArrayList();
		String sql = "SELECT * FROM receipt";
		connect = database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();
			customersData cData;

			while (result.next()) {
				cData = new customersData(result.getInt("id"), result.getInt("customer_id"), result.getFloat("total"),
						result.getDate("date"), result.getString("em_username"));

				listData.add(cData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}

	private ObservableList<customersData> customersListData;

	public void customersShowData() {
		customersListData = customersDataList();
		customers_col_customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
		customers_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
		customers_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
		customers_col_cashier.setCellValueFactory(new PropertyValueFactory<>("emUsername"));

		customers_tableView.setItems(customersListData);

	}

	public void switchForm(ActionEvent event) {
		if (event.getSource() == dashboard_btn) {
			dashboard_form.setVisible(true);
			inventory_form.setVisible(false);
			menu_form.setVisible(false);
			customers_form.setVisible(false);
			dashboardDisplayNC();
			dashboardDisplayTI();
			dashboardTotalI();
			dashboardNSP();
			dashboardIncomeChart();
			dashboardCustomerChart();

		} else if (event.getSource() == inventory_btn) {
			dashboard_form.setVisible(false);
			inventory_form.setVisible(true);
			menu_form.setVisible(false);
			customers_form.setVisible(false);
			inventoryTypeList();
			inventoryStatusList();
			inventoryShowData();

		} else if (event.getSource() == menu_btn) {
			dashboard_form.setVisible(false);
			inventory_form.setVisible(false);
			menu_form.setVisible(true);
			customers_form.setVisible(false);

			menuDisplayCard();
			menuDisplayTotal();
			menuShowOrderData();
		} else if (event.getSource() == customers_btn) {
			dashboard_form.setVisible(false);
			inventory_form.setVisible(false);
			menu_form.setVisible(false);
			customers_form.setVisible(true);

			customersShowData();
		}
	}

	public void logout() {
		try {

			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to log out");
			Optional<ButtonType> option = alert.showAndWait();

			if (option.get().equals(ButtonType.OK)) {
				// Hide main form
				logout_btn.getScene().getWindow().hide();

				// Link login form and show it
				Parent root = FXMLLoader.load(getClass().getResource("/ui/foodstore1.fxml"));
				Stage stage = new Stage();
				Scene scene = new Scene(root);

				stage.setTitle("Cafe Shop Management System");

				stage.setScene(scene);
				stage.show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayUsername() {
		String user = data.username;
		user = user.substring(0, 1).toUpperCase() + user.substring(1);
		username.setText(user);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Code to initialize your controller goes here
		displayUsername();

		dashboardDisplayNC();
		dashboardDisplayTI();
		dashboardTotalI();
		dashboardNSP();
		dashboardIncomeChart();
		dashboardCustomerChart();

		inventoryTypeList();
		inventoryStatusList();
		inventoryShowData();
		menuDisplayCard();
		menuGetOrder();
		menuDisplayTotal();
		menuShowOrderData();
		customersShowData();
	}

}
