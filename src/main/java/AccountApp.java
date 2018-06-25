import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
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
        // create an instance of Account
        Account account = new Account();
        System.out.println("Enter account name");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        account.setName(name);

        System.out.println("Enter password");
        Scanner scan2 = new Scanner(System.in);
        String password = scan2.nextLine();
        account.setPassword(password);

        // persist the account object to the database
        // update the database after changing the object
        System.out.println(create(account));

        System.out.println("Enter ID");
        Scanner scan4 = new Scanner(System.in);
        int x = scan4.nextInt();
        System.out.println(findById(x));

        System.out.println("Enter ID and Name");
        Scanner scan5 = new Scanner(System.in);
        String j = scan5.nextLine();

        String newRow = j.replaceAll("\n", "");
        List<String> items = Arrays.asList(newRow.split("\\s*,\\s*"));
        long y = Long.parseLong(items.get(0));
        String updatedName = items.get(1);
        System.out.println(updateName(y, updatedName));



        // delete the account
        System.out.println("Enter ID");
        Scanner scan3 = new Scanner(System.in);
        int id = scan2.nextInt();
        accountDao.deleteById(id);
    }
    public int create(Account account) throws SQLException {
        accountDao.create(account);
        accountDao.update(account);
        return (int) account.getId();
    }

    /**
     * Find the user in the database with the given id
     * @param id id of the user
     * @return The user associated with that id
     * @throws SQLException
     */
    public Account findById(long id) throws SQLException {
        return accountDao.queryForId((int) id);
    }

    /**
     * Given a user id, find the user, and update the user name.
     * @param id id of the user
     * @param newName the user new name
     * @throws SQLException
     */
    public String updateName(long id, String newName) throws SQLException {
        Account temp = findById(id);
        temp.setName(newName);
        accountDao.update(temp);
        return "Account Updated";
    }

    public void deleteById(long id) throws SQLException {
        accountDao.deleteById((int) id);
    }



    /**
     * Setup our  DAOs
     */
    private void setupDao(ConnectionSource connectionSource) throws Exception {

        accountDao = DaoManager.createDao(connectionSource, Account.class);

    }
}
