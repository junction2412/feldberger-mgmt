package de.code.junction.feldberger.mgmt.presentation.view.transaction.overview;

import de.code.junction.feldberger.mgmt.presentation.view.FXView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TransactionOverview extends FXView<TransactionOverviewModel> {

    @FXML
    private Button viewTransaction;
    @FXML
    private Button editTransaction;
    @FXML
    private Button newTransaction;
    @FXML
    private TextField filter;

    public TransactionOverview(TransactionOverviewModel viewModel) {
        super(viewModel);
    }

    @Override
    public String fxml() {
        return "transaction-overview.fxml";
    }

    @Override
    public void initialize() {
    }
}
