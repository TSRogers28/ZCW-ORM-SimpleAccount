import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Scanner;

public class AccountApp {

    // we are using a MySQl database
    private final static String DATABASE_URL = "jdbc:mysql://localhost:3306/orm_lab?useUnicode=true";

    private Dao<Account, Integer> accountDao;

    public static void main(String[] args) throws Exception {
        // turn our static method into an instance of Main
        new AccountApp().doMain(args);
    }

    private void doMain(String[] args) throws Exception {
        ConnectionSource connectionSource = null;
        try {
            // create our data-source for the database
            connectionSource = new JdbcConnectionSource(DATABASE_URL, "root", "");
            // setup our  DAOs
            setupDao(connectionSource);
            // read, write and delete some datase
            processData();

            System.out.println("\n\nIt seems to have worked\n\n");
        } finally {
            // destroy the data source which should close underlying connections
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    /**
     * Read and write some example data.
     */
    private void processData() throws Exception {
        Boolean flag = true;
       while(flag) {
           System.out.println("Select C, R, U, D, or exit");
           Scanner scanner = new Scanner(System.in);
           String option = scanner.next();
           if(option.equals("exit"))
               flag =false;
           else
               selectOption(option);
       }
    }
    public void selectOption(String input) throws Exception{
            if(input.equals("C"))
            System.out.println(create());
            else if(input.equals("R"))
            System.out.println(read());
            else if(input.equals("U"))
            System.out.println(update());
            else if(input.equals("D"))
            System.out.println(delete());
            else
                System.out.println("Incorrect command");
        }


    public String create() throws SQLException {
        Account account = new Account();
        account.setName(addName());
        account.setPassword(addPassword());
        accountDao.create(account);
        accountDao.update(account);
        return "New account is #" + account.getId();
    }

    public String addName(){
        System.out.println("Enter account name");
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    public String addPassword(){
        System.out.println("Enter password");
        Scanner scan2 = new Scanner(System.in);
        return scan2.nextLine();
    }

    public String read() throws SQLException{
        Account account = getAccount();
        int id = account.getId();
        String name = account.getName();
        String password = account.getPassword();
        System.out.println("| ID |   Name    |   Password   |");
        return "  "+id + "  " + name +"   " + password;
    }
    public Account getAccount() throws SQLException {
        System.out.println("Enter ID");
        Scanner scan4 = new Scanner(System.in);
        int x = scan4.nextInt();
        return accountDao.queryForId(x);
    }

    public String update() throws SQLException {
        Account temp = getAccount();
        temp.setName(addName());
        accountDao.update(temp);
        return "Account Updated";
    }

    public String delete() throws SQLException {
        System.out.println("Enter ID");
        Scanner scan3 = new Scanner(System.in);
        int id = scan3.nextInt();
        accountDao.deleteById(id);
        return "Account deleted";
    }

    /**
     * Setup our  DAOs
     */
    private void setupDao(ConnectionSource connectionSource) throws Exception {

        accountDao = DaoManager.createDao(connectionSource, Account.class);

    }
}
