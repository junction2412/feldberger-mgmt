package de.code.junction.feldberger.mgmt.presentation.view.transaction.overview;

import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

public class TransactionOverviewController extends FXController {

    @FXML
    private Button viewTransaction;
    @FXML
    private Button editTransaction;
    @FXML
    private Button newTransaction;
    @FXML
    private TextField filter;

    public TransactionOverviewController() {

        super("transaction-overview.fxml");
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void translate(ResourceBundle bundle) {

    }
}
