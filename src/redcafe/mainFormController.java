
package redcafe;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class mainFormController implements Initializable {

    public Button project_name;
    @FXML
    private AnchorPane main_form;
    
    @FXML
    private Label username;
    
    @FXML
    private Button dashboard_btn;
    
    @FXML
    private Button inventory_btn;
    
    @FXML
    private Button menu_btn;
    
    @FXML
    private Button customers_btn;
    
    @FXML
    private Button logout_btn;
    
    @FXML
    private AnchorPane inventory_form;
    
    @FXML
    private TableView<productData> inventory_tableView;
    
    @FXML
    private TableColumn<productData, String> inventory_col_productID;
    
    @FXML
    private TableColumn<productData, String> inventory_col_productName;
    
    @FXML
    private TableColumn<productData, String> inventory_col_type;
    
    @FXML
    private TableColumn<productData, String> inventory_col_stock;
    
    @FXML
    private TableColumn<productData, String> inventory_col_price;
    
    @FXML
    private TableColumn<productData, String> inventory_col_status;
    
    @FXML
    private TableColumn<productData, String> inventory_col_date;
    
    @FXML
    private ImageView inventory_imageView;
    
    @FXML
    private Button inventory_importBtn;
    
    @FXML
    private Button inventory_addBtn;
    
    @FXML
    private Button inventory_updateBtn;
    
    @FXML
    private Button inventory_clearBtn;
    
    @FXML
    private Button inventory_deleteBtn;
    
    @FXML
    private TextField inventory_productID;
    
    @FXML
    private TextField inventory_productName;
    
    @FXML
    private TextField inventory_stock;
    
    @FXML
    private TextField inventory_price;
    
    @FXML
    private ComboBox<?> inventory_status;
    
    @FXML
    private ComboBox<?> inventory_type;
    
    @FXML
    private AnchorPane menu_form;
    
    @FXML
    private ScrollPane menu_scrollPane;
    
    @FXML
    private GridPane menu_gridPane;
    
    @FXML
    private TableView<productData> menu_tableView;
    
    @FXML
    private TableColumn<productData, String> menu_col_productName;
    
    @FXML
    private TableColumn<productData, String> menu_col_quantity;
    
    @FXML
    private TableColumn<productData, String> menu_col_price;
    
    @FXML
    private Label menu_total;
    
    @FXML
    private TextField menu_amount;
    
    @FXML
    private Label menu_change;
    
    @FXML
    private Button menu_payBtn;
    
    @FXML
    private Button menu_removeBtn;
    
    @FXML
    private Button menu_receiptBtn;
    
    @FXML
    private AnchorPane dashboard_form;
    
    @FXML
    private AnchorPane customers_form;
    
    @FXML
    private TableView<customersData> customers_tableView;
    
    @FXML
    private TableColumn<customersData, String> customers_col_customerID;
    
    @FXML
    private TableColumn<customersData, String> customers_col_total;
    
    @FXML
    private TableColumn<customersData, String> customers_col_date;
    
    @FXML
    private TableColumn<customersData, String> customers_col_cashier;
    
    @FXML
    private Label dashboard_NC;
    
    @FXML
    private Label dashboard_TI;
    
    @FXML
    private Label dashboard_TotalI;
    
    @FXML
    private Label dashboard_NSP;
    
    @FXML
    private AreaChart<?, ?> dashboard_incomeChart;
    
    @FXML
    private BarChart<?, ?> dashboard_CustomerChart;
    
    private Alert alert;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    private Image image;

    /*
    public void projectNameJson(){
        try {
            // Load the new stage FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("JsonDisplay.fxml"));
            Stage jsonStage = new Stage();
            jsonStage.initModality(Modality.APPLICATION_MODAL);
            jsonStage.setTitle("JSON Data");

            // Set the controller for the new stage (JsonDisplayController)
            JsonDisplayController jsonController = new JsonDisplayController();
            loader.setController(jsonController);

            // Load the scene
            jsonStage.setScene(new Scene(loader.load()));

            // Show the stage
            jsonStage.show();

            // Fetch and display JSON data
            jsonController.displayJsonData("https://api.myjson.online/v1/records/dc3c45d3-48b7-453e-a92f-dff27d487a99");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

     */
    private ObservableList<productData> cardListData = FXCollections.observableArrayList();
    
    public void dashboardDisplayNC() {
        
        String sql = "SELECT COUNT(id) FROM receipt";
        connect = database.connectDB();
        
        try {
            int nc = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) {
                nc = result.getInt("COUNT(id)");
            }
            dashboard_NC.setText(String.valueOf(nc));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void dashboardDisplayTI() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        String sql = "SELECT SUM(total) FROM receipt WHERE date = '"
                + sqlDate + "'";
        
        connect = database.connectDB();
        
        try {
            double ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }
            
            dashboard_TI.setText("Tk" + ti);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void dashboardTotalI() {
        String sql = "SELECT SUM(total) FROM receipt";
        
        connect = database.connectDB();
        
        try {
            float ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) {
                ti = result.getFloat("SUM(total)");
            }
            dashboard_TotalI.setText("Tk" + ti);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void dashboardNSP() {
        
        String sql = "SELECT COUNT(quantity) FROM customer";
        
        connect = database.connectDB();
        
        try {
            int q = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) {
                q = result.getInt("COUNT(quantity)");
            }
            dashboard_NSP.setText(String.valueOf(q));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void dashboardIncomeChart() {
        dashboard_incomeChart.getData().clear();
        
        String sql = "SELECT date, SUM(total) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
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
    
    public void dashboardCustomerChart(){
        dashboard_CustomerChart.getData().clear();
        
        String sql = "SELECT date, COUNT(id) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
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
        
        if (inventory_productID.getText().isEmpty()
                || inventory_productName.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || data.path == null
                 ){

            
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            
        } else {

            // CHECK PRODUCT ID
            String checkProdID = "SELECT prod_id FROM product WHERE prod_id = '"
                    + inventory_productID.getText() + "'";
            
            connect = database.connectDB();
            
            try {
                
                statement = connect.createStatement();
                result = statement.executeQuery(checkProdID);
                
                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(inventory_productID.getText() + " is already taken");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO product "
                            + "(prod_id, prod_name, type, stock, price, status, image, date) "
                            + "VALUES(?,?,?,?,?,?,?,?)";
                    
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

                    // TO GET CURRENT DATE
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    
                    prepare.setString(8, String.valueOf(sqlDate));
                    
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
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
        
        if (inventory_productID.getText().isEmpty()
                || inventory_productName.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || data.path == null || data.id == 0) {
            
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            
        } else {
            
            String path = data.path;
            path = path.replace("\\", "\\\\");
            
            String updateData = "UPDATE product SET "
                    + "prod_id = '" + inventory_productID.getText() + "', prod_name = '"
                    + inventory_productName.getText() + "', type = '"
                    + inventory_type.getSelectionModel().getSelectedItem() + "', stock = '"
                    + inventory_stock.getText() + "', price = '"
                    + inventory_price.getText() + "', status = '"
                    + inventory_status.getSelectionModel().getSelectedItem() + "', image = '"
                    + path + "', date = '"
                    + data.date + "' WHERE id = " + data.id;
            
            connect = database.connectDB();
            
            try {
                
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Pr oduct ID: " + inventory_productID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryShowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }
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
                String deleteData = "DELETE FROM product WHERE id = " + data.id;
                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("successfully Deleted!");
                    alert.showAndWait();

                    // TO UPDATE TABLE VIEW
                    inventoryShowData();
                    // TO CLEAR FIELDS
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
    // LETS MAKE A BEHAVIOR FOR IMPORT BTN FIRST
    public void inventoryImportBtn() {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*.png", "*.jpg", "*.jpeg"));

        File file = openFile.showOpenDialog(getStage());

        if (file != null) {
            data.path = file.getAbsolutePath();
            Image image = new Image(file.toURI().toString(), 120, 127, false, true);
            inventory_imageView.setImage(image);
        }
    }

    // Helper function to get the Stage from the current Window
    private Stage getStage() {
        Stage stage = null;
        if (main_form.getScene() != null) {
            Window window = main_form.getScene().getWindow();
            if (window instanceof Stage) {
                stage = (Stage) window;
            }
        }
        return stage;
    }

    /*
    public void inventoryImportBtn() {
        
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new ExtensionFilter("Open Image File", "*png", "*jpg", "*jpeg"));
        
        File file = openFile.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            
            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 120, 127, false, true);
            
            inventory_imageView.setImage(image);
        }
    }
    */


    // MERGE ALL DATAS
    public ObservableList<productData> inventoryDataList() {
        
        ObservableList<productData> listData = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM product";
        
        connect = database.connectDB();
        
        try {
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            productData prodData;
            
            while (result.next()) {
                
                prodData = new productData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date"));
                
                listData.add(prodData);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    // TO SHOW DATA ON OUR TABLE
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
        
        if ((num - 1) < -1) {
            return;
        }
        
        inventory_productID.setText(prodData.getProductId());
        inventory_productName.setText(prodData.getProductName());
        inventory_stock.setText(String.valueOf(prodData.getStock()));
        inventory_price.setText(String.valueOf(prodData.getPrice()));
        
        data.path = prodData.getImage();
        
        String path = "File:" + prodData.getImage();
        data.date = String.valueOf(prodData.getDate());
        data.id = prodData.getId();
        
        image = new Image(path, 120, 127, false, true);
        inventory_imageView.setImage(image);
    }
    
    private String[] typeList = {"Saplings", "Seeds"};
    
    public void inventoryTypeList() {
        
        List<String> typeL = new ArrayList<>();
        
        for (String data : typeList) {
            typeL.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(typeL);
        inventory_type.setItems(listData);
    }
    
    private String[] statusList = {"Available", "Unavailable"};
    
    public void inventoryStatusList() {
        
        List<String> statusL = new ArrayList<>();
        
        for (String data : statusList) {
            statusL.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(statusL);
        inventory_status.setItems(listData);
        
    }
    
    public ObservableList<productData> menuGetData() {
        
        String sql = "SELECT * FROM product";
        
        ObservableList<productData> listData = FXCollections.observableArrayList();
        connect = database.connectDB();
        
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            productData prod;
            
            while (result.next()) {
                prod = new productData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
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
                load.setLocation(getClass().getResource("cardProduct.fxml"));
                AnchorPane pane = load.load();
                cardProductController cardC = load.getController();
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
                prod = new productData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("quantity"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));
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
        
        if ((num - 1) < -1) {
            return;
        }
        // TO GET THE ID PER ORDER
        getid = prod.getId();
        
    }
    
    private double totalP;
    
    public void menuGetTotal() {
        customerID();
        String total = "SELECT SUM(price) FROM customer WHERE customer_id = " + cID;
        
        connect = database.connectDB();
        
        try {
            
            prepare = connect.prepareStatement(total);
            result = prepare.executeQuery();
            
            if (result.next()) {
                totalP = result.getDouble("SUM(price)");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void menuDisplayTotal() {
        menuGetTotal();
        menu_total.setText("Tk" + totalP);
    }
    
    private double amount;
    private double change;
    
    public void menuAmount() {
        menuGetTotal();
        if (menu_amount.getText().isEmpty() || totalP == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid :Enter amount");
            alert.showAndWait();
        } else {
            amount = Double.parseDouble(menu_amount.getText());
            if (amount < totalP) {
                menu_amount.setText("");
            } else {
                change = (amount - totalP);
                menu_change.setText("Tk" + change);
            }
        }
    }
    
    public void menuPayBtn() {
        
        if (totalP == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please choose your order first!");
            alert.showAndWait();
        } else {
            menuGetTotal();
            String insertPay = "INSERT INTO receipt (customer_id, total, date, em_username) "
                    + "VALUES(?,?,?,?)";
            
            connect = database.connectDB();
            
            try {
                
                if (amount == 0) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Messaged");
                    alert.setHeaderText(null);
                    alert.setContentText("Something wrong :3");
                    alert.showAndWait();
                } else {
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure?");
                    Optional<ButtonType> option = alert.showAndWait();
                    
                    if (option.get().equals(ButtonType.OK)) {
                        customerID();
                        menuGetTotal();
                        prepare = connect.prepareStatement(insertPay);
                        prepare.setString(1, String.valueOf(cID));
                        prepare.setString(2, String.valueOf(totalP));
                        
                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        
                        prepare.setString(3, String.valueOf(sqlDate));
                        prepare.setString(4, data.username);
                        
                        prepare.executeUpdate();
                        
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successful.");
                        alert.showAndWait();
                        
                        menuShowOrderData();
                        
                    } else {
                        alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Cancelled.");
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
            String deleteData = "DELETE FROM customer WHERE id = " + getid;
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


    /*
    public void menuReceiptBtn() {
        
        if (totalP == 0 || menu_amount.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Please order first");
            alert.showAndWait();
        } else {
            HashMap map = new HashMap();
            map.put("getReceipt", (cID - 1));
            
            try {
                
                //JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\Akshit\\OneDrive\\Documents\\NetBeansProjects\\RedCafe\\src\\redcafe\\report.jrxml");
                JasperDesign jDesign = JRXmlLoader.load("/Users/hafsa_tazrian/Desktop/Desktop_project-RED_CAFE-main/src/redcafe/report.jrxml");
                JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                JasperPrint jPrint = JasperFillManager.fillReport(jReport, map, connect);
                
                JasperViewer.viewReport(jPrint, false);
                
                menuRestart();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        
    }




    public void menuReceiptBtn() {

        customerID();


        if (totalP == 0 || menu_amount.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Please order first");
            alert.showAndWait();
        } else {
            try {
                // Assuming connect is your database connection
                // Get the customer ID
                 // Implement this method

                // Generate a simple receipt
                StringBuilder receiptContent = new StringBuilder();
                receiptContent.append("GREENLIFE\n");
                receiptContent.append("Customer ID: ").append(cID).append("\n");
                receiptContent.append("--------------------------------\n");
                receiptContent.append(String.format("%-20s%-10s%-10s\n", "Product Name", "Quantity", "Price"));

                // Assuming menuOrderListData is an ObservableList of productData
                for (productData item : menuOrderListData) {
                    receiptContent.append(String.format("%-20s%-10d%-10.2f\n", item.getProductName(), item.getQuantity(), item.getPrice()));
                }

                receiptContent.append("--------------------------------\n");

                // Get the total amount from the receipt table
                String totalSql = "SELECT SUM(total) FROM receipt WHERE customer_id = " + cID;


                try {


                    prepare = connect.prepareStatement(total);
                    result = prepare.executeQuery();

                try (

                    PreparedStatement totalStmt = connect.prepareStatement(totalSql)) {
                    //totalStmt.setInt(1, cID);
                    ResultSet totalResult = totalStmt.executeQuery();

                    if (totalResult.next()) {
                        double totalAmount = totalResult.getDouble(1);
                        receiptContent.append(String.format("%-40s%-10.2f\n", "TOTAL", totalAmount));
                    }
                }

                receiptContent.append("--------------------------------\n");
                receiptContent.append("Thanks for your purchase. Please come again!\n");

                // Print the receipt (You can replace this with your print logic)
                System.out.println(receiptContent.toString());

                // Clear the order or update the status as needed
                menuRestart();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    */




    public void menuReceiptBtn() {
        if (totalP == 0 || menu_amount.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Please order first");
            alert.showAndWait();
        } else {
            try {
                // Your existing logic for creating the map
                //HashMap map = new HashMap();
                //map.put("getReceipt", (cID - 1));

                // Load the ReceiptDisplay.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ReceiptDisplay.fxml"));
                Parent root = loader.load();
                // Get the controller of the new FXML file
                ReceiptDisplayController receiptController = loader.getController();

                // Call a method in the new controller to set the receipt content
                receiptController.setReceiptContent(generateReceiptContent());

                // Create a new stage for the receipt display
                Stage receiptStage = new Stage();
                receiptStage.setTitle("Receipt");
                receiptStage.setScene(new Scene(root));

                // Show the new stage
                receiptStage.show();

                // Your existing logic for menuRestart

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private String prodName;
    private int quant;
    private double pp;
    private String generateReceiptContent() {
        // Generate the receipt content based on your logic and return it as a string
        // This could be similar to the logic in the previous example
        // ...
        customerID();
        //menuGetOrder();
        StringBuilder receiptContent = new StringBuilder();

        // Add header
        receiptContent.append("\t\t\t\t\t\t\t\t\tGREENLIFE\t\t\n\n");
        receiptContent.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tCustomer ID: ").append(cID).append("\n");
        receiptContent.append("--------------------------------------------------------------------------------\n");
        receiptContent.append(String.format("%-60s%-50s%-10s\n", "Product", "Quantity", "Price"));




        String orderSQL = "SELECT * FROM customer WHERE customer_id = " + (cID-1);

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(orderSQL);
            result = prepare.executeQuery();

            while (result.next()) {
                prodName = result.getString("prod_name");
                quant = result.getInt("quantity");
                pp = result.getDouble("price");

                receiptContent.append(String.format("%-50s%-50d%-10.2f\n", prodName, quant, pp));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }




        /*
        // Retrieve order details from the customer table
        String orderSql = "SELECT * FROM customer WHERE customer_id = " + getid;
        try (PreparedStatement orderStmt = connect.prepareStatement(orderSql)) {
            //orderStmt.setInt(1, cID);
            ResultSet orderResult = orderStmt.executeQuery();

            while (orderResult.next()) {
                var prodName = orderResult.getString("prod_name");
                int quantity = orderResult.getInt("quantity");
                double price = orderResult.getDouble("price");

                // Add each item to the receipt
                receiptContent.append(String.format("%-20s%-10d%-10.2f\n", prodName, quantity, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

         */
        receiptContent.append("---------------------------------------------------------------------------------\n");

        receiptContent.append("---------------------------------------------------------------------------------\n\n\n");

        // Get the total amount from the receipt table
        /*
        String totalSql = "SELECT SUM(total) FROM receipt WHERE customer_id = " + cID;
        try (PreparedStatement totalStmt = connect.prepareStatement(totalSql)) {
            totalStmt.setInt(1, cID);
            ResultSet totalResult = totalStmt.executeQuery();

            if (totalResult.next()) {
                double totalAmount = totalResult.getDouble(1);
                receiptContent.append(String.format("%-40s%-10.2f\n", "TOTAL: Tk. ", totalAmount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
         */

        receiptContent.append(String.format("\t\t\t\t\t\t%-40s%-10.2f\n", "TOTAL: Tk.", totalP));
        receiptContent.append(String.format("\t\t\t\t\t\t%-40s%-10.2f", "CHANGE: Tk.", change));


        receiptContent.append("---------------------------------------------------------------------------------\n\n");
        receiptContent.append("\t\t\t\t\t\tThanks for your purchase. Please come again!\n");

        return receiptContent.toString();
    }





    public void menuRestart() {
        totalP = 0;
        change = 0;
        amount = 0;
        menu_total.setText("Tk0.0");
        menu_amount.setText("");
        menu_change.setText("Tk0.0");
    }
    
    private int cID;
    
    public void customerID() {
        
        String sql = "SELECT MAX(customer_id) FROM customer";
        connect = database.connectDB();
        
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) {
                //cID = result.getInt("MAX(customer_id)");
                cID = result.getInt(1);
            }
            
            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID = 0;
            if (result.next()) {
                //checkID = result.getInt("MAX(customer_id)");
                checkID = result.getInt(1);
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
                cData = new customersData(result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getDouble("total"),
                        result.getDate("date"),
                        result.getString("em_username"));
                
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
// LETS PROCEED TO OUR DASHBOARD FORM : )

    public void logout() {
        
        try {
            
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();
            
            if (option.get().equals(ButtonType.OK)) {

                // TO HIDE MAIN FORM 
                logout_btn.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM AND SHOW IT 
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                
                stage.setTitle("GREEN LIFE");
                
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

