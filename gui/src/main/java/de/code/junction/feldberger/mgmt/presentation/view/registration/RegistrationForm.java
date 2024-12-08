package de.code.junction.feldberger.mgmt.presentation.view.registration;

public record RegistrationForm(
        String username,
        String password,
        String repeatPassword
) {
}
