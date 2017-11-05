import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class Transaction {

    static void transfer(String cust_id,String password)
    {

        Stage window2 = new Stage();
        window2.setTitle("Admin Login");
        window2.initModality(Modality.APPLICATION_MODAL);

        Label ID = new Label("Transfer to: ");
        Label amount= new Label("Amount: ");
        Label pass = new Label("Password: ");
        Label alert = new Label();
        alert.setTextFill(Paint.valueOf("red"));
        alert.setVisible(true);

        TextField ID_=new TextField();
        TextField amount_=new TextField();
        TextField pass_=new TextField();

        Button transfer =new Button("Transfer");

        transfer.setOnAction(e -> {

            try {
                if(Execute_query.searchById(ID_.getText())!=null)
                {

                    if(password.equals(pass_.getText())){

                        Execute_query.writeTransfer(amount_.getText(),ID_.getText(),cust_id);

                    }
                    else {
                        alert.setVisible(true);
                        alert.setText("Incorrect Password");
                    }

                }
                else {
                    alert.setVisible(true);
                    alert.setText("ID not available");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });

        GridPane layout=new GridPane();
        layout.setPadding(new Insets(20));
        GridPane.setConstraints(ID,0,0);
        GridPane.setConstraints(ID_,1,0);
        GridPane.setConstraints(amount,0,1);
        GridPane.setConstraints(amount_,1,1);
        GridPane.setConstraints(pass,0,2);
        GridPane.setConstraints(pass_,1,2);
        GridPane.setConstraints(alert,1,3);
        GridPane.setConstraints(transfer,1,4);

        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        layout.setHgap(8);
        layout.getChildren().addAll(ID,ID_,amount,amount_,pass,pass_,alert,transfer);

        Scene scene=new Scene(layout,300,350);
        window2.setScene(scene);
        window2.showAndWait();



    }

}
