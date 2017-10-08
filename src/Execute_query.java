import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Execute_query
{


    static public void write_data(String fullname,String address,String mobile,String email,String dob,String uidai,String pass) throws Exception
    {

        Statement stmt=set_con().createStatement();

        String write="insert into create_acc (cust_pass,cust_fullname,cust_fulladdress,cust_mobileno,cust_email,DOB,cust_UIDAI)" +
                "values('"+pass+"','"+fullname+"','"+address+"','"+mobile+"','"+email+"','"+dob+"','"+uidai+"');";
        stmt.executeUpdate(write);
        System.out.println("Account Successfully Created !!");
        set_con().close();
    }


    public String search_data(String account_id, String passw) throws Exception
    {

        try{

            Integer.parseInt(account_id);

        }catch (Exception e)
        {
            return null;
        }

        Statement stmt=set_con().createStatement();
        String write="select *from create_acc where cust_acc_no="+account_id+" and cust_pass='"+passw+"';";
        ResultSet res=stmt.executeQuery(write);

        if (res.next())
        {
            String ex=res.getString(1);
            set_con().close();
            return ex;
        }
        else
        {
            set_con().close();
            return null;
        }


    }

    public static   String display_profile(String acc_id) throws Exception {


        Statement stmt=set_con().createStatement();
        String write="select *from create_acc where cust_acc_no="+acc_id+";";
        ResultSet res=stmt.executeQuery(write);

        String details=null;

       if( res.next())
       {
            details="Account ID: "+res.getString(1)+"\n\nName: "+ res.getString(3)+" \n\nAddress: "+ res.getString(4)+" \n\nMobile No: "+ res.getString(5)+"\n\nemail: "+ res.getString(6)+"\n\nAadhar no: "+res.getString(7)+"\n\nCurrent Balance: "+res.getString(8);
       }
        set_con().close();
        return details;
    }

    static Connection set_con() throws Exception
    {

        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root");

        return con;
    }

    static void setup() throws Exception
    {
            Statement stmt=null;

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");

            stmt=con.createStatement();

            stmt.executeUpdate("create database bank;");
            con.close();

        Statement stmt2=set_con().createStatement();

        stmt2.executeUpdate("create table create_acc(cust_acc_no int(11) auto_increment,cust_pass varchar(20) not null," +
                "primary key(cust_acc_no),cust_fullname varchar(30) not null,cust_fulladdress varchar(80) not null," +
                "cust_mobileno varchar(15)not null,cust_email varchar(30),cust_UIDAI varchar(12),BALANCE numeric(12,2) not null default 1000,DOB date);");

        stmt2.executeUpdate("create table log(cust_acc_no int,date_ date,time_ varchar(20),withdraw numeric(12,2)," +
                "deposit numeric(12,2),balance numeric(12,2));");

        stmt2.executeUpdate("alter table create_acc auto_increment=2000;");
        Gui.alert_box("Setup completed!!",0);

        set_con().close();
    }


    static void withdraw(String accid,String withdraw_str) throws Exception
    {
        double balance;
        double withdraw;
        int acc_id=Integer.parseInt(accid);

        Statement stmt=set_con().createStatement();
        ResultSet rs;

            String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

            rs = stmt.executeQuery("select BALANCE from create_acc where cust_acc_no='" + acc_id + "'");
            String balance_str = "";
            if (rs.next()) {
                balance_str = rs.getString(1);
            }

            balance = Double.parseDouble(balance_str);
            withdraw = Integer.parseInt(withdraw_str);
            balance = balance - withdraw;

        String message="\n Amount Rs. "+withdraw_str+" has been successfully withdrawn from ur account ID:"+accid;
        rs=stmt.executeQuery("select cust_email from create_acc where cust_acc_no="+accid);
        rs.next();

            if(balance>0) {
                if(EmailSend.email("SRS BANK: Withdraw Successful",message,rs.getString(1))) {

                String write = "insert into log values (?,?,?,?,?,?)";
                PreparedStatement prep = set_con().prepareStatement(write);
                prep.setInt(1, acc_id);
                prep.setString(2, date);
                prep.setDouble(4, withdraw);
                prep.setDouble(5, 00);
                prep.setDouble(6, balance);
                prep.setString(3, time);
                prep.executeUpdate();

                stmt.executeUpdate("update create_acc set BALANCE =" + balance + " where cust_acc_no=" + accid);

                Gui.alert_box("Withdraw Successful", 0);

            }

        }
            else Gui.alert_box("Not enough balance in Account!!",0);
        set_con().close();
    }

    static void deposit(String accid,String deposit_str) throws Exception
    {
        double balance;
        double deposit;
        int acc_id=Integer.parseInt(accid);

        Statement stmt=set_con().createStatement();
        ResultSet rs;
        rs=stmt.executeQuery("select cust_email from create_acc where cust_acc_no="+accid);
        rs.next();

        String message="\n Amount Rs. "+deposit_str+" has been successfully deposited into ur account ID:"+accid;

        if(EmailSend.email("SRS BANK: Deposit Successful",message,rs.getString(1))) {

            String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

            rs = stmt.executeQuery("select BALANCE from create_acc where cust_acc_no='" + acc_id + "'");
            String balance_str = "";
            if (rs.next()) {
                balance_str = rs.getString(1);
            }

            balance = Double.parseDouble(balance_str);
            deposit = Double.parseDouble(deposit_str);
            balance = balance + deposit;

            String write = "insert into log values (?,?,?,?,?,?)";
            PreparedStatement prep = set_con().prepareStatement(write);
            prep.setInt(1, acc_id);
            prep.setString(2, date);
            prep.setDouble(4, 00);
            prep.setDouble(5, deposit);
            prep.setDouble(6, balance);
            prep.setString(3, time);
            prep.executeUpdate();

            stmt.executeUpdate("update create_acc set BALANCE =" + balance + " where cust_acc_no=" + accid);

            Gui.alert_box("Deposit Successful", 0);

            set_con().close();
        }
    }

    static void update(String fullname,String addr,String mob,String email,String uidai,String dob,String acc_id) throws Exception
    {
        String write="update create_acc set cust_fullname=?,cust_fulladdress=?,cust_mobileno=?,cust_email=?" +
                ",cust_UIDAI=?,DOB=? where cust_acc_no=?";
        PreparedStatement prep = set_con().prepareStatement(write);
        prep.setString(1,fullname);
        prep.setString(2,addr);
        prep.setString(3,mob);
        prep.setString(4,email);
        prep.setString(5,uidai);
        prep.setString(6,dob);
        prep.setString(7,acc_id);
        prep.executeUpdate();
        set_con().close();

    }

    static void delete_acc(String id) throws Exception {

        String write="delete from create_acc where cust_acc_no="+id;
        String write2="delete from log where cust_acc_no="+id;

        set_con().createStatement().executeUpdate(write);
        set_con().createStatement().executeUpdate(write2);
        set_con().close();

    }

    public static boolean checkUIDAI(String uidai) {

        try {

        ResultSet rs=set_con().createStatement().executeQuery("select *from create_acc where cust_UIDAI='"+uidai+"'");
        rs.next();

            if(rs.getString(1).isEmpty()){

                return true;

            }
            else return false;
        }
         catch (Exception e) {
             return true;

        }


    }
}


