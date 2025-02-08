package de.code.junction.feldberger.mgmt.presentation.components.jfx;

import de.code.junction.feldberger.mgmt.presentation.messaging.Message;
import de.code.junction.feldberger.mgmt.presentation.messaging.MessageResponse;
import de.code.junction.feldberger.mgmt.presentation.messaging.MessageType;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.function.Consumer;

/**
 * The JavaFX specific {@link Messenger} implementation.
 *
 * @author J.Murray
 */
public class FXMessenger implements Messenger {

    /**
     * The stage to be used as the owner.
     */
    private final Stage stage;

    public FXMessenger(Stage stage) {
        this.stage = stage;
    }

    /**
     * Display messages in a JavaFX compliant way, thus respecting the JavaFX-Application-Thread.
     *
     * @param message message details to be displayed
     */
    @Override
    public void send(Message message, Consumer<MessageResponse> onResponse) {
        Platform.runLater(() -> showAlert(message, onResponse));
    }

    private void showAlert(Message message, Consumer<MessageResponse> onResponse) {

        final var alert = new Alert(determineAlertType(message.type()));
        alert.initOwner(stage);
        alert.setTitle(message.title());
        alert.setHeaderText(message.header());
        alert.setContentText(message.content());
        alert.setResizable(true);

        final var buttonType = alert.showAndWait();

        buttonType.ifPresent(type -> onResponse.accept(determineResponseType(type)));
    }

    /**
     * Translate a {@link MessageType} to the corresponding {@link Alert.AlertType}.
     *
     * @param messageType message type
     * @return alert type
     */
    private static Alert.AlertType determineAlertType(MessageType messageType) {

        return switch (messageType) {
            case ERROR -> Alert.AlertType.ERROR;
            case WARNING -> Alert.AlertType.WARNING;
            case INFORMATION -> Alert.AlertType.INFORMATION;
            case CONFIRMATION -> Alert.AlertType.CONFIRMATION;
        };
    }

    /**
     * Translate a {@link ButtonType} to the corresponding {@link MessageResponse}.
     *
     * @param buttonType button type
     * @return message response
     */
    private static MessageResponse determineResponseType(ButtonType buttonType) {

        return switch (buttonType) {
            case ButtonType type when type == ButtonType.OK || type == ButtonType.YES || type == ButtonType.APPLY ->
                    MessageResponse.PROCEED;

            default -> MessageResponse.CANCEL;
        };
    }
}
