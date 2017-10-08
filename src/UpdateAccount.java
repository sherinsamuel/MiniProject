import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.sql.ResultSet;
import java.sql.Statement;

public class UpdateAccount {

    static boolean flag=true;

    static void callUpdate(String acc_id,BorderPane border) throws Exception {

        Label fullname = new Label("Full Name: ");
        Label address = new Label("Address: ");
        Label mobile = new Label("Mobile No: ");
        Label email = new Label("Email ID: ");
        Label dob = new Label("Date Of Birth: ");
        Label uidai = new Label("UIDAI no: ");
      //  Label pass=new Label("Password");

        Statement stmt=Execute_query.set_con().createStatement();
        ResultSet res=stmt.executeQuery("select * from create_acc where cust_acc_no="+acc_id);
        res.next();

        TextField fullname_=new TextField();
        fullname_.setText(res.getString(3));

        TextField address_=new TextField();
        address_.setText(res.getString(4));

        TextField mobile_=new TextField();
        mobile_.setText(res.getString(5));

        TextField email_=new TextField();
        email_.setText(res.getString(6));

        TextField uidai_=new TextField();
        uidai_.setText(res.getString(7));

        Button update=new Button("Update Details");

        DatePicker cal=new DatePicker();
        cal.setPromptText("select date");

        GridPane layout=new GridPane();
        layout.setPadding(new Insets(20));
        GridPane.setConstraints(fullname,0,0);
        GridPane.setConstraints(fullname_,1,0);
        GridPane.setConstraints(address,0,2);
        GridPane.setConstraints(address_,1,2);
        GridPane.setConstraints(mobile,0,4);
        GridPane.setConstraints(mobile_,1,4);
        GridPane.setConstraints(email,0,6);
        GridPane.setConstraints(email_,1,6);
        GridPane.setConstraints(dob,0,8);
        GridPane.setConstraints(cal,1,8);
        GridPane.setConstraints(uidai,0,10);
        GridPane.setConstraints(uidai_,1,10);

        GridPane.setConstraints(update,1,14);


        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        layout.setHgap(8);
        layout.getChildren().addAll(fullname,fullname_,address,address_,mobile,mobile_,email,email_,dob,cal,uidai,uidai_,update);

        border.setCenter(layout);

        update.setOnAction(e ->
        {

            if(!fullname_.getText().isEmpty() && !address_.getText().isEmpty() && !mobile_.getText().isEmpty() &&
                    !email_.getText().isEmpty() && cal.getValue()!=null && !uidai_.getText().isEmpty() )
            {

                if (Gui.alert_box("Confirm account creation ?", 1)) {

                    try {
                        Execute_query.update(fullname_.getText(), address_.getText(), mobile_.getText(), email_.getText(),
                                uidai_.getText(),String.valueOf((cal.getValue())),acc_id);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    String subject="SRS BANK ACCOUNT CREATION SUCCESSFUL!!";
                    String message="Hello +fullname_.getText()+!!\n\nYour Account Has been Successfully updated !!";

                    EmailSend.email(subject,message,email_.getText());

                    Gui.alert_box("Updated Successfully",0);
                }
            }
            else Gui.alert_box("All details not entered !!",0);

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

    }

}
