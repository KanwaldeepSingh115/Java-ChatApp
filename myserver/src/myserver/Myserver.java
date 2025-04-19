package myserver;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.PasswordField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Myserver extends Application {
    private ServerSocket serverSocket;
    private Socket socket;
    private TextField text;
    private VBox messageBox;
    private DataOutputStream dout;
    private DataInputStream din;
    private ScrollPane scrollPane;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/logserver";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Anmol@2005";
    private static final String INSERT_IMAGE_QUERY = "INSERT INTO image_demo (image_data) VALUES (?)";
    private static final String RETRIEVE_IMAGE_QUERY = "SELECT image_data FROM image_demo WHERE image_id = ?";

    
    private static final String SERVER_DB_URL = "jdbc:mysql://localhost:3306/imagedemo";
    private static final int SERVER_PORT = 5678;

   
    @Override
public void start(Stage primaryStage) {
    primaryStage.setTitle("Welcome Page");

    

    Image image = new Image("file:C:\\Users\\ASUS\\OneDrive\\Documents\\NetBeansProjects\\mysummerproject\\myclient\\src\\icons\\servar.png");

    
    double maxWindowWidth = 800;
    double maxWindowHeight = 600;

    
    double imageWidth = image.getWidth();
    double imageHeight = image.getHeight();

    
    double aspectRatio = imageWidth / imageHeight;

    
    double scaledWidth = maxWindowWidth;
    double scaledHeight = maxWindowHeight;

    if (imageWidth > imageHeight) {
        scaledHeight = scaledWidth / aspectRatio;
    } else {
        scaledWidth = scaledHeight * aspectRatio;
    }

    if (scaledWidth > maxWindowWidth) {
        scaledWidth = maxWindowWidth;
        scaledHeight = scaledWidth / aspectRatio;
    }
    if (scaledHeight > maxWindowHeight) {
        scaledHeight = maxWindowHeight;
        scaledWidth = scaledHeight * aspectRatio;
    }

    
    BackgroundImage backgroundImage = new BackgroundImage(image,
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER, new BackgroundSize(scaledWidth, scaledHeight, false, false, false, false));

    
    BorderPane borderPane = new BorderPane();
    borderPane.setBackground(new Background(backgroundImage));

    
    Label productionLabel = new Label("A KK Productions App");
    productionLabel.setFont(new Font("Arial", 36));
    productionLabel.setTextFill(Color.WHITE);
    productionLabel.setOpacity(0); 

    
    DropShadow glow = new DropShadow();
    glow.setColor(Color.YELLOW);
    glow.setRadius(20);
    glow.setOffsetX(0);
    glow.setOffsetY(0);
    productionLabel.setEffect(glow);

    
    Timeline glowAnimation = new Timeline(
        new KeyFrame(Duration.ZERO, new KeyValue(productionLabel.opacityProperty(), 0.5)),
        new KeyFrame(Duration.millis(500), new KeyValue(productionLabel.opacityProperty(), 1)),
        new KeyFrame(Duration.millis(1000), new KeyValue(productionLabel.opacityProperty(), 0.5))
    );
    glowAnimation.setCycleCount(Timeline.INDEFINITE);
    glowAnimation.play();

    
    Button loginButton = new Button("Click here to login");
    loginButton.setStyle("-fx-background-color: #25D366; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 20 5 20;");

    
    VBox topBox = new VBox(productionLabel);
    topBox.setAlignment(Pos.TOP_CENTER);
    topBox.setSpacing(10);
    BorderPane.setAlignment(topBox, Pos.TOP_CENTER);
    borderPane.setTop(topBox);

    
    VBox bottomBox = new VBox(loginButton);
    bottomBox.setAlignment(Pos.CENTER);
    bottomBox.setSpacing(10);
    BorderPane.setAlignment(bottomBox, Pos.BOTTOM_CENTER);
    borderPane.setBottom(bottomBox);

    
    Scene initialScene = new Scene(borderPane, scaledWidth, scaledHeight);
    primaryStage.setScene(initialScene);
    primaryStage.show();

    
    loginButton.setOnAction(e -> openLoginInterface(primaryStage));
}



private void openLoginInterface(Stage primaryStage) {
    primaryStage.setTitle("Login");

    
    Image image = new Image("file:C:\\Users\\ASUS\\OneDrive\\Documents\\NetBeansProjects\\mysummerproject\\myclient\\src\\icons\\loginpage.png");
    
    
    double maxWindowWidth = 800;
    double maxWindowHeight = 600;

    
    double imageWidth = image.getWidth();
    double imageHeight = image.getHeight();

    
    double aspectRatio = imageWidth / imageHeight;

    
    double scaledWidth = maxWindowWidth;
    double scaledHeight = maxWindowHeight;

    if (imageWidth > imageHeight) {
        scaledHeight = scaledWidth / aspectRatio;
    } else {
        scaledWidth = scaledHeight * aspectRatio;
    }

    
    if (scaledWidth > maxWindowWidth) {
        scaledWidth = maxWindowWidth;
        scaledHeight = scaledWidth / aspectRatio;
    }
    if (scaledHeight > maxWindowHeight) {
        scaledHeight = maxWindowHeight;
        scaledWidth = scaledHeight * aspectRatio;
    }

    
    BackgroundImage backgroundImage = new BackgroundImage(image,
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER, new BackgroundSize(scaledWidth, scaledHeight, false, false, false, false));

    
    StackPane root = new StackPane();
    root.setBackground(new Background(backgroundImage));

    
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(8);
    grid.setHgap(10);

    
    Label usernameLabel = new Label("Username:");
    usernameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, #00FF00, 10, 0.5, 0, 0);");
    GridPane.setConstraints(usernameLabel, 0, 0);
    TextField usernameInput = new TextField();
    usernameInput.setStyle("-fx-background-color: #333333; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-padding: 10px; -fx-border-color: #25d366; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-prompt-text-fill: #888888;");
    GridPane.setConstraints(usernameInput, 1, 0);

    
    Label passwordLabel = new Label("Password:");
    passwordLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, #00FF00, 10, 0.5, 0, 0);");
    GridPane.setConstraints(passwordLabel, 0, 1);
    PasswordField passwordInput = new PasswordField();
    passwordInput.setStyle("-fx-background-color: #333333; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-padding: 10px; -fx-border-color: #25d366; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-prompt-text-fill: #888888;");
    GridPane.setConstraints(passwordInput, 1, 1);

    
    Button loginButton = new Button("Login");
    loginButton.setStyle("-fx-background-color: #25D366; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 20;");
    GridPane.setConstraints(loginButton, 1, 2);
    loginButton.setOnAction(e -> {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        if (validateLogin(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "You have logged in successfully.");
            showChatInterface(primaryStage);
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    });

    
    Button signUpButton = new Button("Sign Up");
    signUpButton.setStyle("-fx-background-color: #34B7F1; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 20;");
    GridPane.setConstraints(signUpButton, 0, 3);
    signUpButton.setOnAction(e -> openSignUpForm());

    
    Button forgotPasswordButton = new Button("Forgot Password?");
    forgotPasswordButton.setStyle("-fx-background-color: #128C7E; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 20;");
    GridPane.setConstraints(forgotPasswordButton, 1, 3);
    forgotPasswordButton.setOnAction(e -> openForgotPasswordForm());

    
    grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton, signUpButton, forgotPasswordButton);

    
    root.getChildren().add(grid);

    
    Scene scene = new Scene(root, scaledWidth, scaledHeight);
    primaryStage.setScene(scene);
    primaryStage.show();
}



private void openSignUpForm() {
    Stage signUpStage = new Stage();
    signUpStage.setTitle("Sign Up");

    
    Image image = new Image("file:C:\\Users\\ASUS\\OneDrive\\Documents\\NetBeansProjects\\mysummerproject\\myclient\\src\\icons\\sawnup.png");

    
    double maxWindowWidth = 800;
    double maxWindowHeight = 600;

    
    double imageWidth = image.getWidth();
    double imageHeight = image.getHeight();

    
    double aspectRatio = imageWidth / imageHeight;

    
    double scaledWidth = maxWindowWidth;
    double scaledHeight = maxWindowHeight;

    if (imageWidth > imageHeight) {
        scaledHeight = scaledWidth / aspectRatio;
    } else {
        scaledWidth = scaledHeight * aspectRatio;
    }

    
    if (scaledWidth > maxWindowWidth) {
        scaledWidth = maxWindowWidth;
        scaledHeight = scaledWidth / aspectRatio;
    }
    if (scaledHeight > maxWindowHeight) {
        scaledHeight = maxWindowHeight;
        scaledWidth = scaledHeight * aspectRatio;
    }

    
    BackgroundImage backgroundImage = new BackgroundImage(image,
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER, new BackgroundSize(scaledWidth, scaledHeight, false, false, false, false));

    
    StackPane root = new StackPane();
    root.setBackground(new Background(backgroundImage));

    
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(8);
    grid.setHgap(10);

    
    Label usernameLabel = new Label("Username:");
    usernameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, #FF0000, 10, 0.5, 0, 0);");
    GridPane.setConstraints(usernameLabel, 0, 0);
    TextField usernameInput = new TextField();
    usernameInput.setStyle("-fx-background-color: #333333; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-padding: 10px; -fx-border-color: #FFFF00; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-prompt-text-fill: #888888;");
    GridPane.setConstraints(usernameInput, 1, 0);

    
    Label passwordLabel = new Label("Password:");
    passwordLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, #FF0000, 10, 0.5, 0, 0);");
    GridPane.setConstraints(passwordLabel, 0, 1);
    PasswordField passwordInput = new PasswordField();
    passwordInput.setStyle("-fx-background-color: #333333; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-padding: 10px; -fx-border-color: #FFFF00; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-prompt-text-fill: #888888;");
    GridPane.setConstraints(passwordInput, 1, 1);

    
    Label confirmPasswordLabel = new Label("Confirm Password:");
    confirmPasswordLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, #FF0000, 10, 0.5, 0, 0);");
    GridPane.setConstraints(confirmPasswordLabel, 0, 2);
    PasswordField confirmPasswordInput = new PasswordField();
    confirmPasswordInput.setStyle("-fx-background-color: #333333; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-padding: 10px; -fx-border-color: #FFFF00; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-prompt-text-fill: #888888;");
    GridPane.setConstraints(confirmPasswordInput, 1, 2);

    
    Button signUpButton = new Button("Sign Up");
    signUpButton.setStyle("-fx-background-color: #FFFF00; -fx-text-fill: #000000; -fx-font-size: 14px; -fx-padding: 10px; -fx-border-color: #FFFF00; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-effect: dropshadow(gaussian, #FFFF00, 10, 0.5, 0, 0);");
    GridPane.setConstraints(signUpButton, 1, 3);
    signUpButton.setOnAction(e -> {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        String confirmPassword = confirmPasswordInput.getText();
        if (password.equals(confirmPassword)) {
            if (registerUser(username, password)) {
                showAlert(Alert.AlertType.INFORMATION, "Sign Up Successful", "You have successfully signed up.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Sign Up Failed", "Could not sign up. Please try again.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "Passwords do not match.");
        }
    });

    
    grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, confirmPasswordLabel, confirmPasswordInput, signUpButton);

    
    root.getChildren().add(grid);

    
    Scene scene = new Scene(root, scaledWidth, scaledHeight);
    signUpStage.setScene(scene);
    signUpStage.show();
}



private void openForgotPasswordForm() {
    Stage forgotPasswordStage = new Stage();
    forgotPasswordStage.setTitle("Forgot Password");

    
    Image image = new Image("file:C:\\Users\\ASUS\\OneDrive\\Documents\\NetBeansProjects\\mysummerproject\\myclient\\src\\icons\\farget.png");

    
    double maxWindowWidth = 800;
    double maxWindowHeight = 600;

    
    double imageWidth = image.getWidth();
    double imageHeight = image.getHeight();

    
    double aspectRatio = imageWidth / imageHeight;

    
    double scaledWidth = maxWindowWidth;
    double scaledHeight = maxWindowHeight;

    if (imageWidth > imageHeight) {
        scaledHeight = scaledWidth / aspectRatio;
    } else {
        scaledWidth = scaledHeight * aspectRatio;
    }

    
    if (scaledWidth > maxWindowWidth) {
        scaledWidth = maxWindowWidth;
        scaledHeight = scaledWidth / aspectRatio;
    }
    if (scaledHeight > maxWindowHeight) {
        scaledHeight = maxWindowHeight;
        scaledWidth = scaledHeight * aspectRatio;
    }

    
    BackgroundImage backgroundImage = new BackgroundImage(image,
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER, new BackgroundSize(scaledWidth, scaledHeight, false, false, false, false));

    
    StackPane root = new StackPane();
    root.setBackground(new Background(backgroundImage));

    
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(8);
    grid.setHgap(10);



    
    Label usernameLabel = new Label("Username:");
    usernameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, #0000FF, 10, 0.5, 0, 0);");
    GridPane.setConstraints(usernameLabel, 0, 0);
TextField usernameInput = new TextField();
    usernameInput.setStyle("-fx-background-color: #333333; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-padding: 10px; -fx-border-color: #0000FF; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-prompt-text-fill: #888888;");
 
    GridPane.setConstraints(usernameInput, 1, 0);

    
    Label newPasswordLabel = new Label("New Password:");
    newPasswordLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, #0000FF, 10, 0.5, 0, 0);");
    GridPane.setConstraints(newPasswordLabel, 0, 1);
 PasswordField newPasswordInput = new PasswordField();
    newPasswordInput.setStyle("-fx-background-color: #333333; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-padding: 10px; -fx-border-color: #0000FF; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-prompt-text-fill: #888888;");
   
GridPane.setConstraints(newPasswordInput, 1, 1);

   Button resetPasswordButton = new Button("Reset Password");
    resetPasswordButton.setStyle("-fx-background-color: #34B7F1; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 20;");
 
    GridPane.setConstraints(resetPasswordButton, 1, 2);
    resetPasswordButton.setOnAction(e -> {
        String username = usernameInput.getText();
        String newPassword = newPasswordInput.getText();
        if (resetPassword(username, newPassword)) {
            showAlert(Alert.AlertType.INFORMATION, "Password Reset Successful", "Password has been reset successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Password Reset Failed", "Could not reset your password.");
        }
    });


    
    grid.getChildren().addAll(usernameLabel, usernameInput, newPasswordLabel, newPasswordInput, resetPasswordButton);

    
    root.getChildren().add(grid);

    
    Scene scene = new Scene(root, scaledWidth, scaledHeight);
    forgotPasswordStage.setScene(scene);
    forgotPasswordStage.show();
}

private boolean registerUser(String username, String password) {
    String query = "INSERT INTO login (username, password) VALUES (?, ?)";
    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.executeUpdate();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

private boolean resetPassword(String username, String newPassword) {
    String query = "UPDATE login SET password = ? WHERE username = ?";
    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, newPassword);
        stmt.setString(2, username);
        int affectedRows = stmt.executeUpdate();
        return affectedRows > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    private boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM login WHERE username = ? AND password = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    



    private void showChatInterface(Stage primaryStage) {
        primaryStage.setTitle("Chat Application");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: white;");

        HBox topPanel = new HBox();
        topPanel.setStyle("-fx-background-color: #075e54;");
        topPanel.setPadding(new Insets(10, 10, 10, 10));
        topPanel.setSpacing(10);
        topPanel.setAlignment(Pos.CENTER_LEFT);
        root.setTop(topPanel);

        ImageView back = new ImageView(new Image("icons/3.png"));
        back.setFitWidth(25);
        back.setFitHeight(25);
        back.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));

        ImageView profile = new ImageView(new Image("icons/ksingh2.png"));
        profile.setFitWidth(50);
        profile.setFitHeight(50);

        ImageView video = new ImageView(new Image("icons/video.png"));
        video.setFitWidth(30);
        video.setFitHeight(30);
        video.setOnMouseClicked(event -> showFeatureUnderProgress());
        
        ImageView phone = new ImageView(new Image("icons/phone.png"));
        phone.setFitWidth(35);
        phone.setFitHeight(30);
        phone.setOnMouseClicked(event -> showFeatureUnderProgress());
        
                

        ImageView optionsIcon = new ImageView(new Image("icons/3icon.png"));
        optionsIcon.setFitWidth(10);
        optionsIcon.setFitHeight(25);

           
        ContextMenu optionsMenu = new ContextMenu();
    MenuItem optionsItem = new MenuItem("Options");
    MenuItem changeWallpaper = new MenuItem("Change Wallpaper");
    MenuItem clearChat = new MenuItem("Clear Chat");

    optionsItem.setOnAction(event -> {
        optionsMenu.hide();
        ContextMenu subMenu = new ContextMenu(changeWallpaper, clearChat);
        subMenu.show(optionsIcon, optionsIcon.getScene().getWindow().getX() + optionsIcon.getLayoutX(),
                     optionsIcon.getScene().getWindow().getY() + optionsIcon.getLayoutY() + optionsIcon.getFitHeight());
    });

    optionsMenu.getItems().add(optionsItem);

    changeWallpaper.setOnAction(event -> changeWallpaper(root));
    clearChat.setOnAction(event -> clearChat());

    optionsIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> 
        optionsMenu.show(optionsIcon, event.getScreenX(), event.getScreenY()));

        VBox nameStatusBox = new VBox();
        nameStatusBox.setAlignment(Pos.CENTER_LEFT);
        Label name = new Label("Kanwaldeep Singh");
        name.setTextFill(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", 18));

        Label status = new Label("Active Now");
        status.setTextFill(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", 14));

        nameStatusBox.getChildren().addAll(name, status);

        topPanel.getChildren().addAll(back, profile, nameStatusBox, video, phone, optionsIcon);

        messageBox = new VBox();
        messageBox.setStyle("-fx-background-image: url('icons/chat_bg.jpg');");
        messageBox.setPadding(new Insets(10));
        messageBox.setSpacing(10);
        scrollPane = new ScrollPane(messageBox);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);

        HBox bottomPanel = new HBox();
        bottomPanel.setPadding(new Insets(10));
        bottomPanel.setSpacing(10);
        bottomPanel.setAlignment(Pos.CENTER);
        root.setBottom(bottomPanel);

        text = new TextField();
        text.setPrefHeight(40);
        text.setFont(new Font("SAN_SERIF", 16));
        text.setPromptText("Type your message...");

        Button send = new Button("Send");
        send.setPrefHeight(40);
        send.setStyle("-fx-background-color: #075e54; -fx-text-fill: white;");
        send.setFont(new Font("SAN_SERIF", 16));
        send.setOnAction(event -> sendMessage());


        ImageView galleryIcon = new ImageView(new Image("icons/photo_icon.png"));
    galleryIcon.setFitWidth(40);
    galleryIcon.setFitHeight(40);
    galleryIcon.setOnMouseClicked(event -> sendImage());
    
        bottomPanel.getChildren().addAll(text, send, galleryIcon);

        Scene scene = new Scene(root, 450, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

        loadChatHistory();
        new Thread(this::startServer).start();

        primaryStage.setOnCloseRequest(event -> {
            try {
                if (socket != null) socket.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        
        
    }

    private void showFeatureUnderProgress() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Feature Under Progress");
    alert.setHeaderText(null);
    alert.setContentText("This feature is under development and will be available in the future.");
    alert.showAndWait();
}

    private void changeWallpaper(BorderPane root) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String encodedFilePath = encodeFilePath(selectedFile.getAbsolutePath());
            root.setStyle("-fx-background-image: url('" + encodedFilePath + "');");
        }
    }

    private String encodeFilePath(String filePath) {
        try {
            return "file:" + URLEncoder.encode(filePath, StandardCharsets.UTF_8.toString())
                    .replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void clearChat() {
        messageBox.getChildren().clear();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/my_chat.txt"))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private void sendMessage() {
    try {
        String out = text.getText();
        if (!out.isEmpty()) {
            addMessage(out, Pos.BASELINE_RIGHT);
            if (dout != null) {
                dout.writeUTF("TEXT:" + out);
                dout.flush(); 
                text.clear();
                saveMessageToFile("server", out);
            } else {
                showAlert(Alert.AlertType.ERROR, "Send Error", "Unable to send message. Not connected.");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    private void sendImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                
                int imageId = storeImageInDatabase(selectedFile);
                dout.writeUTF("IMAGE:" + imageId);
                addImage(selectedFile.toURI().toString(), Pos.BASELINE_RIGHT);
                saveMessageToFile("server", "Image ID: " + imageId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    
    private int storeImageInDatabase(File file) {
        try (Connection conn = DriverManager.getConnection(SERVER_DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(INSERT_IMAGE_QUERY, Statement.RETURN_GENERATED_KEYS);
             FileInputStream fis = new FileInputStream(file)) {

            ps.setBinaryStream(1, fis, (int) file.length());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); 
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void addMessage(String message, Pos position) {
        Platform.runLater(() -> {
            HBox messageContainer = new HBox();
            messageContainer.setAlignment(position);
            messageContainer.setPadding(new Insets(5, 5, 5, 5));

            VBox messageBox = new VBox();
            messageBox.setStyle("-fx-background-color: #25d366; -fx-padding: 10;");
            Label messageLabel = new Label(message);
            messageLabel.setFont(new Font("Tahoma", 16));
            messageLabel.setTextFill(Color.WHITE);

            Label timeLabel = new Label(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
            timeLabel.setFont(new Font("Tahoma", 12));
            timeLabel.setTextFill(Color.WHITE);

            messageBox.getChildren().addAll(messageLabel, timeLabel);
            messageContainer.getChildren().add(messageBox);

            this.messageBox.getChildren().add(messageContainer);
            
             scrollPane.setVvalue(1.0);
        });
    }

    private void addImage(String imagePath, Pos position) {
        Platform.runLater(() -> {
            HBox messageContainer = new HBox();
            messageContainer.setAlignment(position);
            messageContainer.setPadding(new Insets(5, 5, 5, 5));

            VBox imageBox = new VBox();
            imageBox.setStyle("-fx-background-color: #25d366; -fx-padding: 10;");

            ImageView imageView = new ImageView(new Image(imagePath));
            imageView.setFitWidth(300);
            imageView.setPreserveRatio(true);

            Label timeLabel = new Label(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
            timeLabel.setFont(new Font("Tahoma", 12));
            timeLabel.setTextFill(Color.WHITE);

            imageBox.getChildren().addAll(imageView, timeLabel);
            messageContainer.getChildren().add(imageBox);

            this.messageBox.getChildren().add(messageContainer);
            
             scrollPane.setVvalue(1.0);
        });
    }

    private void saveMessageToFile(String sender, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/my_chat.txt", true))) {
            writer.write(sender + ": " + message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    private void loadChatHistory() {
    StringBuilder clientMessages = new StringBuilder();
    StringBuilder serverMessages = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader("src/my_chat.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("client:")) {
                String message = line.replaceFirst("client: ", "");
                if (!clientMessages.toString().contains(message)) {
                    clientMessages.append(message).append("\n");
                    if (line.contains("Image ID:")) {
                        int startIndex = line.indexOf("Image ID:") + 9; 
                        int endIndex = line.length();
                        String imageIdStr = line.substring(startIndex, endIndex).trim();
                        if (imageIdStr.matches("\\d+")) { 
                            int imageId = Integer.parseInt(imageIdStr);
                            String imagePath = getImagePathFromDatabase(imageId);
                            if (imagePath != null) {
                                addImage(imagePath, Pos.BASELINE_LEFT);
                            }
                        } else {
                            
                            System.out.println("Invalid image ID: " + imageIdStr);
                        }
                    } else {
                        
                        addMessage(message, Pos.BASELINE_LEFT);
                    }
                }
            } else if (line.startsWith("server:")) {
                String message = line.replaceFirst("server: ", "");
                if (!serverMessages.toString().contains(message)) {
                    serverMessages.append(message).append("\n");
                    if (line.contains("Image ID:")) {
                        int startIndex = line.indexOf("Image ID:") + 9; 
                        int endIndex = line.length();
                        String imageIdStr = line.substring(startIndex, endIndex).trim();
                        if (imageIdStr.matches("\\d+")) { 
                            int imageId = Integer.parseInt(imageIdStr);
                            String imagePath = getImagePathFromDatabase(imageId);
                            if (imagePath != null) {
                                addImage(imagePath, Pos.BASELINE_RIGHT);
                            }
                        } else {
                            
                            System.out.println("Invalid image ID: " + imageIdStr);
                        }
                    } else {
                        
                        addMessage(message, Pos.BASELINE_RIGHT);
                    }
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    

   private String getImagePathFromDatabase(int imageId) {
    try (Connection conn = DriverManager.getConnection(SERVER_DB_URL, DB_USERNAME, DB_PASSWORD);
         PreparedStatement ps = conn.prepareStatement(RETRIEVE_IMAGE_QUERY)) {

        ps.setInt(1, imageId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            byte[] imageData = rs.getBytes("image_data");
            return saveImageToFile(imageData);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    private String saveImageToFile(byte[] imageData) {
        try {
            File tempFile = File.createTempFile("image", ".png");
            tempFile.deleteOnExit();
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(imageData);
            }
            return tempFile.toURI().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    private void startServer() {
    try {
        serverSocket = new ServerSocket(SERVER_PORT);
        while (true) {
            socket = serverSocket.accept();
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());

            String message;
            while ((message = din.readUTF()) != null) {
                if (message.startsWith("TEXT:")) {
                    String textMessage = message.replaceFirst("TEXT:", "");
                    Platform.runLater(() -> addMessage(textMessage, Pos.BASELINE_LEFT));
                    saveMessageToFile("client", textMessage);
                } else if (message.startsWith("IMAGE:")) {
                    int imageId = Integer.parseInt(message.replaceFirst("IMAGE:", ""));
                    String imagePath = getImagePathFromDatabase(imageId);
                    if (imagePath != null) {
                        Platform.runLater(() -> addImage(imagePath, Pos.BASELINE_LEFT));
                        saveMessageToFile("client", "Image ID: " + imageId);
                    }
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public static void main(String[] args) {
        launch(args);
    }
}


