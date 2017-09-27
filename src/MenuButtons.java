import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.Statement;


class MenuButtons {



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

        Button create=new Button("Create Account");

        DatePicker cal=new DatePicker();
        cal.setPromptText("select date");

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
        GridPane.setConstraints(create,1,7);


        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        layout.setHgap(8);
        layout.getChildren().addAll(fullname,fullname_,address,address_,mobile,mobile_,email,email_,dob,cal,uidai,uidai_,pass,pass_,create);

        create.setOnAction(e ->
        {
            System.out.println(String.valueOf(cal.getValue()));

            String acc_id=null;

            if(!fullname_.getText().isEmpty() && !address_.getText().isEmpty() && !mobile_.getText().isEmpty() &&
                    !email_.getText().isEmpty() && cal.getValue()!=null && !uidai_.getText().isEmpty() && !pass_.getText().isEmpty())
            {

                if (Gui.alert_box("Confirm account creation ?", 1)) {

                    try {

                        Execute_query.write_data(fullname_.getText(), address_.getText(), mobile_.getText(), email_.getText(), String.valueOf((cal.getValue())), uidai_.getText(), pass_.getText());
                        Statement stmt=Execute_query.set_con().createStatement();
                        ResultSet res=stmt.executeQuery("select cust_acc_no from create_acc where cust_UIDAI='"+uidai_.getText()+"'");
                        if(res.next()) {

                            acc_id=res.getString(1);
                        }

                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                    String subject="SRS BANK ACCOUNT CREATION SUCCESSFUL!!";
                    String message="Hello +fullname_.getText()+!!\n\nYour Account Has been Successfully created !!" +
                            "\n\n\n\nYour account ID is :"+acc_id;

                    EmailSend.email(subject,message,email_.getText());

                    Gui.alert_box("Account ID: ["+acc_id+"] Successfully Created",0);
                    window2.close();
                }
            }

            else Gui.alert_box("All details not entered !!",0);

        });

        Scene scene=new Scene(layout,350,350);
        window2.setScene(scene);
        window2.showAndWait();



    }

    TableView<TableData> table;
    TableColumn<TableData, String> dateColumn;
    TableColumn<TableData, String> timeColumn;
    TableColumn<TableData, String> withdrawColumn;
    TableColumn<TableData, String> depositColumn;
    TableColumn<TableData, String> balanceColumn;


    public TableView makeTable(String acc_id,int flag) throws Exception {

        dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(120);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));


        timeColumn = new TableColumn<>("Time");
        timeColumn.setMinWidth(120);
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));


        withdrawColumn = new TableColumn<>("Withdraw");
        withdrawColumn.setMinWidth(120);
        withdrawColumn.setCellValueFactory(new PropertyValueFactory<>("withdraw"));


        depositColumn = new TableColumn<>("Deposit");
        depositColumn.setMinWidth(120);
        depositColumn.setCellValueFactory(new PropertyValueFactory<>("deposit"));


        balanceColumn = new TableColumn<>("Balance");
        balanceColumn.setMinWidth(120);
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        table = new TableView<>();
        table.setPrefHeight(500);

        buildData(acc_id,flag);

        table.getColumns().addAll(dateColumn, timeColumn, withdrawColumn, depositColumn, balanceColumn);

        return table;

    }

    private ObservableList<TableData> data;

    public void buildData(String acc_id,int flag) throws Exception {
        String query="";
        data = FXCollections.observableArrayList();
        Statement stmt = Execute_query.set_con().createStatement();
        if(flag==1) {
            query = "select * from log where cust_acc_no=" + acc_id+" order by date_ desc, time_ desc";
        }
        else
        {
            query="select * from log where cust_acc_no=" + acc_id+" order by date_ desc, time_ desc limit 5";
        }
        ResultSet res = stmt.executeQuery(query);

        while (res.next())
        {

            TableData obj=new TableData();

            obj.setDate(res.getString(2));
            System.out.println(obj.getDate());

            obj.setTime(res.getString(3));
            System.out.println(obj.getTime());

            obj.setWithdraw(res.getString(4));
            System.out.println(obj.getWithdraw());

            obj.setDeposit(res.getString(5));
            System.out.println(obj.getDeposit());

            obj.setBalance(res.getString(6));
            System.out.println(obj.getBalance());

            data.add(obj);

        }
        table.setItems(data);

        Execute_query.set_con().close();
    }

    static void callUpdate(String acc_id,BorderPane border) throws Exception {

        Label fullname = new Label("Full Name: ");
        Label address = new Label("Address: ");
        Label mobile = new Label("Mobile No: ");
        Label email = new Label("Email ID: ");
        Label dob = new Label("Date Of Birth: ");
        Label uidai = new Label("UIDAI no: ");
        Label pass=new Label("Password");

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
        /*GridPane.setConstraints(pass,0,12);
        GridPane.setConstraints(pass_,1,12);*/
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
                    String message="Hello +fullname_.getText()+!!\n\nYour Account Has been Successfully created !!";

                    EmailSend.email(subject,message,email_.getText());

                    Gui.alert_box("Updated Successfully",0);
                }
            }
            else Gui.alert_box("All details not entered !!",0);

        });


    }

}
