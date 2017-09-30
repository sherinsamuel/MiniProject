

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Admin{

    private static Scene scene2;
    Execute_query q=new Execute_query();
    static Image image1 = null;
    static String details="";
    private static VBox layout_search;
    private static String check_id="";
    private static boolean set_layout_flag=false;

    void adminInterface(Stage window,  Scene scene1) throws Exception {

        Text default_text=new Text("Welcome Admin");
        default_text.setFont(Font.font("Verdana",30));

        Button profile=new Button("profile");
        profile.setStyle("-fx-padding:15");

        Button logs=new Button("logs");
        logs.setStyle("-fx-padding:20");

        Button withdraw=new Button("withdraw");
        withdraw.setStyle("-fx-padding:15");

        Button withdraw_rs=new Button("Withdraw");

        TextField withdraw_text=new TextField();
        withdraw_text.setPromptText("Enter Amount to Withdraw");

        Button deposit=new Button("deposit");
        deposit.setStyle("-fx-padding:15");

        Button deposit_rs=new Button("Deposit");

        TextField deposit_text=new TextField();
        deposit_text.setPromptText("Enter Deposit Amount");

        Button search_b =new Button("search");

        Button update =new Button("Update");
        update.setStyle("-fx-padding:15");

        Button delete=new Button("Delete");
        delete.setStyle("-fx-padding:15");


        TextField search_acc=new TextField();
        search_acc.setPromptText("Enter account ID");

        Text p=new Text();
        p.setFont(Font.font(14));

        Label title=new Label("SRS Bank");
        title.setFont(Font.font(20));
        title.setTextFill(Paint.valueOf("white"));

        ImageView selectedImage=new ImageView();
        selectedImage.setFitHeight(150);
        selectedImage.setFitWidth(150);

        ComboBox menu=new ComboBox<>();
        menu.getItems().addAll("print logs","Log out","Create Account");
        menu.setPromptText("Menu");
        menu.setOnAction(e ->{

            if(menu.getValue()=="Log out")
            {
                boolean flag= Gui.alert_box("Confirm Log out ?",1);
                if(flag)
                {
                    window.setScene(scene1);
                }
                else
                    menu.setPromptText("Menu");

            }

            if(menu.getValue()=="Create Account")
            {
                MakeAccount.acc_details();
            }

        });


        VBox layout1=new VBox(30);
        layout1.getChildren().addAll(profile,deposit,withdraw,logs,update,delete);
        layout1.setPadding(new Insets(5));
        layout1.setStyle("-fx-background-color:lightgray");
        layout1.setAlignment(Pos.CENTER);

        HBox layout2=new HBox(160);
        layout2.getChildren().addAll(title,menu);
        layout2.setAlignment(Pos.TOP_RIGHT);
        layout2.setPadding(new Insets(10));
        layout2.setStyle("-fx-background-color:ff6666");


        HBox layout5=new HBox(10);
        layout5.getChildren().addAll(search_b,search_acc);
        layout5.setAlignment(Pos.CENTER);
        layout5.setPadding(new Insets(10));
        layout5.setStyle("-fx-background-color:gray");

        VBox default_layout=new VBox();
        default_layout.getChildren().add(default_text);
        default_layout.setAlignment(Pos.CENTER);


        BorderPane border1=new BorderPane();
        border1.setTop(layout2);
        border1.setBottom(layout5);


        BorderPane border2=new BorderPane();
        border2.setCenter(default_layout);
        border2.setLeft(layout1);
        border2.setTop(border1);

        scene2=new Scene(border2,700,600);
        window.setScene(scene2);



        search_b.setOnAction(e ->{

            check_id = search_acc.getText(); //To keep common ID in whole actions

            try {
                details =q.display_profile(search_acc.getText());

            } catch (Exception e1) {
                e1.printStackTrace();
            }

            if(details!=null) {
                p.setText(details);
                try {
                    image1 = new Image(new FileInputStream("//home//sherin//Desktop//Snapchat//" + search_acc.getText() + ".jpg"));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                selectedImage.setImage(image1);

            layout_search=new VBox(20);
            layout_search.getChildren().addAll(selectedImage,p);
            layout_search.setPadding(new Insets(20));
            layout_search.setAlignment(Pos.TOP_CENTER);

            set_layout_flag=true; // To set layout only after ID is searched

            border2.setCenter(layout_search);

            }
            else Gui.alert_box("Account ID not available !!",0);


        });

        /*search_acc.textProperty().addListener((obs, oldText, newText) -> {
            System.out.println("Text changed from "+oldText+" to "+newText);
            // ...
        });*/

        profile.setOnAction(e -> border2.setCenter(layout_search));

        deposit.setOnAction(e -> {

            if(set_layout_flag)
            {
                VBox layout_deposit=new VBox(10);
                layout_deposit.setAlignment(Pos.TOP_CENTER);
                try {
                    layout_deposit.getChildren().addAll(deposit_text,deposit_rs,new AddTable().makeTable(check_id,0));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                layout_deposit.setPadding(new Insets(10));
                    border2.setCenter(layout_deposit);

            }

        });

        withdraw.setOnAction(e -> {

            if(set_layout_flag)
            {
                VBox layout_withdraw=new VBox(10);
                layout_withdraw.setAlignment(Pos.TOP_CENTER);
                try {
                    layout_withdraw.getChildren().addAll(withdraw_text,withdraw_rs,new AddTable().makeTable(check_id,0));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                layout_withdraw.setPadding(new Insets(10));
                border2.setCenter(layout_withdraw);

            }

        });

        logs.setOnAction(e ->{

            if(set_layout_flag) {
                VBox layout_log = new VBox(10);
                try {
                    layout_log.getChildren().add(new AddTable().makeTable(check_id, 1));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                //layout_log.setPadding(new Insets(20));
                layout_log.setAlignment(Pos.TOP_CENTER);
                border2.setCenter(layout_log);
            }
        });

        update.setOnAction(e ->{

            if(set_layout_flag)
            {
                try {
                    UpdateAccount.callUpdate(check_id,border2);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        });

        deposit_rs.setOnAction(e -> {

            if(!deposit_text.getText().isEmpty()) {

                try {

                    Integer.parseInt(deposit_text.getText());
                    if (Gui.alert_box("Confirm deposit ?", 1)) {

                        try {
                            Execute_query.deposit(check_id, deposit_text.getText());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }catch (Exception e1)
                {
                    Gui.alert_box("Invalid Input!!",0);
                }
            }
            else
                Gui.alert_box("Amount not entered!!",0);

        });

        withdraw_rs.setOnAction(e -> {

            if(!withdraw_text.getText().isEmpty()) {

                try {

                    Integer.parseInt(withdraw_text.getText());
                    if (Gui.alert_box("Confirm withdraw ?", 1)) {

                        try {
                            Execute_query.withdraw(check_id, withdraw_text.getText());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }catch (Exception e1){
                    Gui.alert_box("Invalid Input!!",0);
                }
            }
            else
                Gui.alert_box("Amount not entered!!",0);

        });

        delete.setOnAction(e -> {

            if(set_layout_flag)
            {
                if(Gui.alert_box("Confirm delete account "+check_id+" ??",1))
                {
                    try {
                        Execute_query.delete_acc(check_id);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }

                }
        );


    }




}
