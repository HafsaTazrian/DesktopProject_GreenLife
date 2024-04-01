package redcafe;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.ResourceBundle;

public class ReceiptDisplayController implements Initializable {
    @FXML
    public TextArea yourNewID;


    public ReceiptDisplayController() {
    }

    public ReceiptDisplayController(TextArea yourNewId) {
        this.yourNewID = yourNewId;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize if needed
    }

    public void setReceiptContent(String content) {
        yourNewID.setText(content);
    }
}
