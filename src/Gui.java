import com.sun.org.apache.xpath.internal.operations.String;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;



public class Gui extends Application {

    Stage window;
    Execute_query q=new Execute_query();
    Admin admin=new Admin();
    Customer cust=new Customer();
    static boolean yes_flag;

    public static void main(String[] args) throws Exception {
        launch(java.lang.String.valueOf(args));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window=primaryStage;
        window.setTitle("Login Window");

        Label label_id=new Label("Account ID:");
        Label label_pw=new Label("Password:");
        Label title=new Label("SRSV Co-operative Bank");
        title.setTextFill(Paint.valueOf("white"));

        PasswordField password=new PasswordField();

        TextField ID=new TextField();
        password.setPromptText("enter password");

        Button button=new Button("LOGIN");
        button.setStyle("-fx-background-color:lightgreen");

        Button forgot=new Button("Forgot Password ?");
        forgot.setStyle("-fx-background-color:#ff6666");

        GridPane layout=new GridPane();
        GridPane.setConstraints(label_id,19,10);
        GridPane.setConstraints(ID,20,10);
        GridPane.setConstraints(label_pw,19,11);
        GridPane.setConstraints(password,20,11);
        GridPane.setConstraints(button,20,12);
        GridPane.setConstraints(forgot,20,14);

        HBox layout3=new HBox(10);
        layout3.setAlignment(Pos.BOTTOM_RIGHT);
        layout3.getChildren().addAll();
        layout3.setStyle("-fx-background-color:#66b2ff");

        VBox layout2=new VBox(10);
        layout2.setAlignment(Pos.CENTER);
        layout2.getChildren().add(title);

        layout.setStyle("-fx-background-image:url(\"currency3.jpg\");-fx-background-repeat: stretch;" +
                "-fx-background-size: 700 450;\n" +
                "    -fx-background-position: center center;");
        //layout.setAlignment(Pos.CENTER);
        label_id.setStyle("-fx-background-color:white;-fx-padding:5;");
        label_pw.setStyle("-fx-background-color:white;-fx-padding:5;");

        title.setFont(Font.font("Verdana",30));
        layout2.setStyle("-fx-background-color:#66b2ff");
        layout2.setPadding(new Insets(20));
        layout3.setPadding(new Insets(20));
        layout.setVgap(8);
        layout.setHgap(10);
        layout.setPadding(new Insets(20,20,20,20));

           BorderPane border= new BorderPane();
            border.setCenter(layout);
            border.setTop(layout2);
            border.setBottom(layout3);

        layout.getChildren().addAll(label_id, ID, label_pw, password,button,forgot);

        Scene scene=new Scene(border,700,600);

        window.setScene(scene);
        window.setResizable(false);
        window.show();

        try {
            Execute_query.set_con().createStatement().executeQuery("select *from create_acc;");
        }catch (Exception e1)
        {
            Execute_query.setup();
            new File(System.getProperty("user.home")+"/resources").mkdir();
        }


        button.setOnAction( e -> {

        //check password
            try {

                 if(ID.getText().equals("admin") && password.getText().equals("root"))
                {
                    admin.adminInterface(window,scene);
                }
                else if (q.search_data(ID.getText(), password.getText())!=null) {

                    cust.custInterface(window,ID.getText(),scene);

                }

                else
                    alert_box("Incorrect Account ID or Password",0);
            } catch(Exception ex){
                System.out.println(ex);
            }
        });

        forgot.setOnAction(e ->Customer.forgotPass());

    }

    static boolean alert_box(java.lang.String message,int flag) {

        Stage window2 = new Stage();
        window2.initModality(Modality.APPLICATION_MODAL);
        Label l = new Label(message);
        Button yes=new Button("yes");
        Button no=new Button("no");
        yes.setOnAction(e ->{
            yes_flag = true;
            window2.close();
        });

        no.setOnAction(e -> {
            yes_flag=false;
            window2.close();

        });
        GridPane layout=new GridPane();
        layout.setPadding(new Insets(20));
        GridPane.setConstraints(l,0,0);
        GridPane.setConstraints(yes,0,1);
        GridPane.setConstraints(no,1,1);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(l);
        if(flag==1)
        {
            layout.getChildren().addAll(yes,no);
        }
        Scene scene=new Scene(layout,400,50);
        window2.setScene(scene);
        window2.showAndWait();

        return yes_flag;

    }


}
