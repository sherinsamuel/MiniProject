import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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


public class Customer{

    Scene scene2;
    Execute_query q=new Execute_query();
    void custInterface(Stage window, String ID,Scene scene1) throws Exception {

        String details=q.display_profile(ID);

        String log_details=new String("blaaa blaaa bleeee");

        Button profile=new Button("profile");
        profile.setStyle("-fx-padding:15");

        Button logs=new Button("logs");
        logs.setStyle("-fx-padding:20");


        Text p=new Text(details);
        p.setFont(Font.font(14));

        Text logtext=new Text(log_details);

        Label title=new Label("SRS Bank");
        title.setFont(Font.font(20));
        title.setTextFill(Paint.valueOf("white"));

        ImageView selectedImage=new ImageView();
        selectedImage.setFitHeight(150);
        selectedImage.setFitWidth(150);
        Image image1 = new Image(new FileInputStream("//home//sherin//Desktop//Snapchat//"+ID+".jpg"));
        selectedImage.setImage(image1);

        ComboBox menu=new ComboBox<>();
        menu.getItems().addAll("print logs","Log out");
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

        });

        VBox layout1=new VBox(50);
        layout1.getChildren().addAll(profile,logs);
        layout1.setPadding(new Insets(10));
        layout1.setStyle("-fx-background-color:lightgray");
        layout1.setAlignment(Pos.CENTER);

        HBox layout2=new HBox(160);
        layout2.getChildren().addAll(title,menu);
        layout2.setAlignment(Pos.TOP_RIGHT);
        layout2.setPadding(new Insets(10));
        layout2.setStyle("-fx-background-color:gray");

        VBox layout3=new VBox(20);
        layout3.getChildren().addAll(selectedImage,p);
        layout3.setPadding(new Insets(20));
        layout3.setAlignment(Pos.TOP_CENTER);

        VBox layout4=new VBox(10);
        layout4.getChildren().add(logtext);
        layout4.setPadding(new Insets(20));
        layout4.setAlignment(Pos.TOP_LEFT);

        BorderPane border=new BorderPane();
        logs.setOnAction(e -> {
            VBox layout_log = new VBox(10);
            try {
                layout_log.getChildren().add(new MenuButtons().makeTable(ID, 1));
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


    }

}


