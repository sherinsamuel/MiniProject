import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.Statement;


class AddTable {



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

            obj.setTime(res.getString(3));

            obj.setWithdraw(res.getString(4));

            obj.setDeposit(res.getString(5));

            obj.setBalance(res.getString(6));

            data.add(obj);

        }
        table.setItems(data);

        Execute_query.set_con().close();
    }



}
