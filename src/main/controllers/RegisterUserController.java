package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.entities.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class RegisterUserController implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(RegisterUserController.class.getName());

    @FXML
    private TextField name_field;

    @FXML
    private TextField username_field;

    @FXML
    private PasswordField password_field;


    @FXML
    void registerNewUser() {
        User userToRegister = new User(
                name_field.getText(),
                username_field.getText(),
                password_field.getText()
        );
        try {
            userToRegister.validateUser();
            LOGGER.info("User was validated");
            DatabaseController.registerValidatedUser(userToRegister);
            LOGGER.info("User was added to database.");
            // If code reaches this statement a valid user was added to the Database.
            LOGGER.info("New user added:" +
                    "\nName: " + userToRegister.getName() +
                    "\nUsername: " + userToRegister.getUsername() +
                    "\nPassword: " + userToRegister.getPassword()
            );
            WindowController.openLoginWindow();
            closeCurrentWindow();
        } catch (SQLException e) {
            // TODO: 17/12/2018 Create DialogBox with SQL error
        } catch (Exception e) {
            // TODO: 17/12/2018 Create DialogBox with validation error
        }

    }

    @FXML
    void cancelCreateAccount() {
        // TODO: 20/12/2018 Confirm is user wants to cancel operation
        LOGGER.info("Returning to login");
        try {
            WindowController.openLoginWindow();
            closeCurrentWindow();
        } catch (IOException e) {
            LOGGER.warning("Failed to open window");
            e.printStackTrace();
        }
    }

    private void closeCurrentWindow() {
        Stage current = (Stage) name_field.getScene().getWindow();
        current.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOGGER.info("Initialized create account controller, URL: " + location.getPath());
    }
}