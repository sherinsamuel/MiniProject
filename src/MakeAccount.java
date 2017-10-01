import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;

public class MakeAccount {

    static boolean flag=true;

    private Desktop desktop = Desktop.getDesktop();

    static void acc_details()
    {

        Stage window2 = new Stage();
        window2.setTitle("Admin Login");
        window2.initModality(Modality.APPLICATION_MODAL);

        window2.setTitle("Create Customer Account");

        Label fullname = new Label("Full Name: ");
        Label address = new Label("Address: ");
        Label mobile = new Label("Mobile No: ");
        Label email = new Label("Email ID: ");
        Label dob = new Label("Date Of Birth: ");
        Label uidai = new Label("UIDAI no: ");
        Label pass=new Label("Password");

        TextField fullname_=new TextField();
        TextField address_=new TextField();
        TextField mobile_=new TextField();
        TextField email_=new TextField();
        TextField uidai_=new TextField();
        TextField pass_=new TextField();

        // mobile_.setStyle("-fx-control-inner-background:#ff3333");

        Button create=new Button("Create Account");

        DatePicker cal=new DatePicker();
        cal.setPromptText("select date");

        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        Button fileButton=new Button("Upload Image");

        GridPane layout=new GridPane();
        layout.setPadding(new Insets(20));
        GridPane.setConstraints(fullname,0,0);
        GridPane.setConstraints(fullname_,1,0);
        GridPane.setConstraints(address,0,1);
        GridPane.setConstraints(address_,1,1);
        GridPane.setConstraints(mobile,0,2);
        GridPane.setConstraints(mobile_,1,2);
        GridPane.setConstraints(email,0,3);
        GridPane.setConstraints(email_,1,3);
        GridPane.setConstraints(dob,0,4);
        GridPane.setConstraints(cal,1,4);
        GridPane.setConstraints(uidai,0,5);
        GridPane.setConstraints(uidai_,1,5);
        GridPane.setConstraints(pass,0,6);
        GridPane.setConstraints(pass_,1,6);
        GridPane.setConstraints(fileButton,1,7);
        GridPane.setConstraints(create,1,8);


        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        layout.setHgap(8);
        layout.getChildren().addAll(fullname,fullname_,address,address_,mobile,mobile_,email,email_,dob,cal,uidai,uidai_,pass,pass_,fileButton,create);

        create.setOnAction(e ->
        {
            System.out.println(String.valueOf(cal.getValue()));

            String acc_id=null;

            String subject="SRS BANK ACCOUNT CREATION SUCCESSFUL!!";
            String message="Hello +fullname_.getText()+!!\n\nYour Account Has been Successfully created !!" +
                    "\n\n\n\nYour account ID is :"+acc_id;

            if(!fullname_.getText().isEmpty() && !address_.getText().isEmpty() && !mobile_.getText().isEmpty() &&
                    !email_.getText().isEmpty() && cal.getValue()!=null && !uidai_.getText().isEmpty() && !pass_.getText().isEmpty() && flag)
            {

                if (Gui.alert_box("Confirm account creation ?", 1)) {

                    if( EmailSend.email(subject,message,email_.getText())==true) {

                        try {

                            Execute_query.write_data(fullname_.getText(), address_.getText(), mobile_.getText(), email_.getText(), String.valueOf((cal.getValue())), uidai_.getText(), pass_.getText());
                            Statement stmt = Execute_query.set_con().createStatement();
                            ResultSet res = stmt.executeQuery("select cust_acc_no from create_acc where cust_UIDAI='" + uidai_.getText() + "'");
                            if (res.next()) {

                                acc_id = res.getString(1);
                            }

                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        Gui.alert_box("Account ID: [" + acc_id + "] Successfully Created", 0);

                        window2.close();
                    }
                }
            }

            else Gui.alert_box("All details not entered !!",0);

        });

        fileButton.setOnAction(e ->{

            //File file = fileChooser.showOpenDialog(window2);
            try {
                new ImageUpload().upload(window2);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });

        fullname_.textProperty().addListener((obs, oldText, newText) -> {

            int i=0;

            if(!newText.isEmpty()) {
                while (i != newText.length()) {

                    char c = newText.charAt(i);
                    if (Character.isLetter(c) == false && c!=' ') {
                        flag = false;
                        fullname_.setStyle("-fx-control-inner-background:#ff3333");
                        break;
                    }
                    flag = true;
                    fullname_.setStyle("-fx-control-inner-background:#33ff33");

                    i++;

                }
            }

        });


        mobile_.textProperty().addListener((obs, oldText, newText) -> {

            if(newText.length()<11) {

                flag=true;

                mobile_.setStyle("-fx-control-inner-background:#33ff33");

                try {

                    Integer.parseInt(newText);

                } catch (Exception e) {

                    flag=false;
                    mobile_.setStyle("-fx-control-inner-background:#ff3333");

                }
            }
            else{
                flag=false;
                mobile_.setStyle("-fx-control-inner-background:#ff3333");
            }


        });

        email_.textProperty().addListener((obs, oldText, newText) -> {

            System.out.println(newText.endsWith("@gmail.com"));

            if(newText.endsWith("@gmail.com"))
            {
                email_.setStyle("-fx-control-inner-background:#33ff33");
                flag=true;
            }
            else {
                email_.setStyle("-fx-control-inner-background:#ff3333");
                flag=false;
            }

        });

        uidai_.textProperty().addListener((obs, oldText, newText) -> {

            if(newText.length()==12) {

                flag=true;

                uidai_.setStyle("-fx-control-inner-background:#33ff33");

                try {

                    Long.parseLong(newText);

                } catch (Exception e) {

                    flag=false;
                    uidai_.setStyle("-fx-control-inner-background:#ff3333");

                }
            }
            else{
                flag=false;
                uidai_.setStyle("-fx-control-inner-background:#ff3333");
            }

        });

        pass_.textProperty().addListener((obs, oldText, newText) -> {

            if(newText.length()>4)
            {
                pass_.setStyle("-fx-control-inner-background:#33ff33");
            }
            else pass_.setStyle("-fx-control-inner-background:#ff3333");

        });


        Scene scene=new Scene(layout,350,350);
        window2.setScene(scene);
        window2.showAndWait();

    }


}
