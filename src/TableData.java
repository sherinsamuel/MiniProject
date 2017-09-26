public class TableData {

    private String date,time,withdraw,deposit,balance;


    public TableData()
    {
        this.date="";
        this.time="";
        this.withdraw="";
        this.deposit="";
        this.balance="";
    }

    private TableData(String date,String time,String withdraw,String deposit,String balance)
    {

        this.date=date;
        this.time=time;
        this.withdraw=withdraw;
        this.deposit=deposit;
        this.balance=balance;

    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getBalance() { return balance; }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
