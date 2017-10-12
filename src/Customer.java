import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Customer{

    Scene scene2;
    Execute_query q=new Execute_query();

    void custInterface(Stage window, String ID,Scene scene1) throws Exception {

        String details=q.display_profile(ID);

        Button profile=new Button("profile");
        profile.setStyle("-fx-padding:15");

        Button logs=new Button("logs");
        logs.setStyle("-fx-padding:20");


        Text p=new Text(details);
        p.setFont(Font.font(14));


        Label title=new Label("SRS Bank");
        title.setFont(Font.font(20));
        title.setTextFill(Paint.valueOf("white"));

        ImageView selectedImage=new ImageView();
        selectedImage.setFitHeight(150);
        selectedImage.setFitWidth(150);
        Image image1 = new Image(new FileInputStream(System.getProperty("user.home")+"/resources/"+ID));
        selectedImage.setImage(image1);

        MenuBar menuBar = new MenuBar();
        Menu menu=new Menu("Menu");
        MenuItem logOut=new MenuItem("Log Out");
        menu.getItems().addAll(logOut);
        menuBar.getMenus().addAll(menu);

        logOut.setOnAction(e ->{

                boolean flag= Gui.alert_box("Confirm Log out ?",1);
                if(flag)
                {
                    window.setScene(scene1);
                    window.setTitle("Login Window");
                }

        });


        VBox layout1=new VBox(50);
        layout1.getChildren().addAll(profile,logs);
        layout1.setPadding(new Insets(10));
        layout1.setStyle("-fx-background-color:lightgray");
        layout1.setAlignment(Pos.CENTER);

        HBox layout2=new HBox(160);
        layout2.getChildren().addAll(title,menuBar);
        layout2.setAlignment(Pos.TOP_RIGHT);
        layout2.setPadding(new Insets(10));
        layout2.setStyle("-fx-background-color:#33ff33");

        VBox layout3=new VBox(20);
        layout3.getChildren().addAll(selectedImage,p);
        layout3.setPadding(new Insets(20));
        layout3.setAlignment(Pos.TOP_CENTER);

        BorderPane border=new BorderPane();
        logs.setOnAction(e -> {
            VBox layout_log = new VBox(10);
            try {
                layout_log.getChildren().add(new AddTable().makeTable(ID, 1));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            //layout_log.setPadding(new Insets(20));
            layout_log.setAlignment(Pos.TOP_CENTER);
            border.setCenter(layout_log);
        });

        profile.setOnAction(e -> {

            border.setCenter(layout3);

        });
        border.setCenter(layout3);
        border.setLeft(layout1);
        border.setTop(layout2);

        scene2=new Scene(border,700,600);
        window.setScene(scene2);
        window.setTitle("Customer Login");


    }

    public static void forgotPass() {

        Stage window =new Stage();
        window.setTitle("Forgot password");
        window.initModality(Modality.APPLICATION_MODAL);

        String instr="Your Password would be send to the \n registered email ID of your account";

        Label label=new Label("Enter your account number:");
        TextField textField=new TextField();
        Text text=new Text(instr);
        Button send =new Button("Send password to email");

        VBox layout =new VBox(5);
        layout.getChildren().addAll(label,textField,send,text);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        send.setOnAction(e ->
                {
                    ResultSet res = null;
                    ResultSet res2;

                    if (!textField.getText().isEmpty()) {

                        try {
                            res = Execute_query.set_con().createStatement().executeQuery("select *from create_acc where cust_acc_no=" + textField.getText());
                            res.next();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                        try {
                            if (!res.getString(1).isEmpty() && textField.getText() != null) {
                                res = Execute_query.set_con().createStatement().executeQuery("select cust_email from create_acc where cust_acc_no=" + textField.getText());
                                res.next();
                                res2 = Execute_query.set_con().createStatement().executeQuery("select cust_pass from create_acc where cust_acc_no=" + textField.getText());
                                res2.next();

                                if(EmailSend.email("SRS BANK: Forgot Password ?", "Your account ID :" + textField.getText() +
                                        "\n\n Password: " + res2.getString(1), res.getString(1)))
                                {
                                    Gui.alert_box("Email send!! ", 0);
                                }

                                window.close();

                            } else Gui.alert_box("Invalid ID", 0);

                        } catch (SQLException e1) {
                            Gui.alert_box("Invalid ID", 0);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                    }
                    else Gui.alert_box("Invalid ID",0);
                }
        );

        Scene scene=new Scene(layout,400,150);
        window.setScene(scene);
        window.showAndWait();


    }
}


