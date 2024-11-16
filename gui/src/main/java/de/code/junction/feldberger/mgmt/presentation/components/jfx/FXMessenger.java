package de.code.junction.feldberger.mgmt.presentation.components.jfx;

import de.code.junction.feldberger.mgmt.presentation.components.messaging.Message;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.MessageResponse;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.MessageType;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.Messenger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.function.Consumer;

public class FXMessenger implements Messenger {

    private final Stage stage;

    public FXMessenger(Stage stage) {

        this.stage = stage;
    }

    @Override
    public void send(Message message) {

        final Alert alert = new Alert(determineAlertType(message.type()));
        alert.initOwner(stage);
        alert.setTitle(message.title());
        alert.setHeaderText(message.header());
        alert.setContentText(message.content());

        final Optional<ButtonType> buttonType = alert.showAndWait();

        buttonType.ifPresent(type -> {
            final Consumer<MessageResponse> onResponse = message.onResponse();
            final MessageResponse response = determineResponseType(type);
            final Runnable runnable = () -> onResponse.accept(response);

            if (Platform.isFxApplicationThread())
                runnable.run();
            else
                Platform.runLater(runnable);
        });
    }

    private Alert.AlertType determineAlertType(MessageType messageType) {

        return switch (messageType) {
            case ERROR -> Alert.AlertType.ERROR;
            case WARNING -> Alert.AlertType.WARNING;
            case INFORMATION -> Alert.AlertType.INFORMATION;
            case CONFIRMATION -> Alert.AlertType.CONFIRMATION;
        };
    }

    private MessageResponse determineResponseType(ButtonType buttonType) {

        return switch (buttonType) {
            case ButtonType type when type == ButtonType.OK || type == ButtonType.YES || type == ButtonType.APPLY ->
                    MessageResponse.PROCEED;

            default -> MessageResponse.CANCEL;
        };
    }
}
