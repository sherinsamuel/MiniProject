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


public class Gui extends Application {

    Stage window;
    Execute_query q=new Execute_query();
    Admin admin=new Admin();
    Customer cust=new Customer();
    static boolean yes_flag;

    public static void main(String[] args) {
        launch(java.lang.String.valueOf(args));
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        window=primaryStage;
        window.setTitle("Login Window");

        Label label_id=new Label("Account ID:");
        Label label_pw=new Label("Password:");
        Label title=new Label("SRS Co-operative Bank");
        title.setTextFill(Paint.valueOf("white"));

        PasswordField password=new PasswordField();

        TextField ID=new TextField();
        password.setPromptText("enter password");

        Button button=new Button("LOGIN");
        button.setStyle("-fx-background-color:lightgreen");
        Button button_setup=new Button("setup");
        button_setup.setStyle("-fx-background-color:ff9999");
        button_setup.setTextFill(Paint.valueOf("white"));

        GridPane layout=new GridPane();
        GridPane.setConstraints(label_id,19,10);
        GridPane.setConstraints(ID,20,10);
        GridPane.setConstraints(label_pw,19,11);
        GridPane.setConstraints(password,20,11);
        GridPane.setConstraints(button,20,12);

        HBox layout3=new HBox(10);
        layout3.setAlignment(Pos.BOTTOM_RIGHT);
        layout3.getChildren().addAll(button_setup);

        VBox layout2=new VBox(10);
        layout2.setAlignment(Pos.CENTER);
        layout2.getChildren().add(title);

        layout.setStyle("-fx-background-color:#cc99ff");
        title.setFont(Font.font("Verdana",30));
        layout2.setStyle("-fx-background-color:#66b2ff");
        layout2.setPadding(new Insets(20));
        layout3.setPadding(new Insets(20));
        layout.setVgap(8);
        layout.setHgap(10);
        layout.setPadding(new Insets(20,20,20,20));

           BorderPane border=new BorderPane();
            border.setCenter(layout);
            border.setTop(layout2);
            border.setBottom(layout3);

        layout.getChildren().addAll(label_id, ID, label_pw, password,button);

        Scene scene=new Scene(border,700,600);
        window.setScene(scene);
        window.show();

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


        button_setup.setOnAction(e -> {

        try{

            q.setup();

        }catch (Exception ex){
            System.out.println(ex);
        }

        });

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
        Scene scene=new Scene(layout,300,50);
        window2.setScene(scene);
        window2.showAndWait();

        return yes_flag;

    }


}
