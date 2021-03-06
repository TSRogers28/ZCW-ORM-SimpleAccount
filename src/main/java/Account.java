import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "account")

public class Account {

        public static final String NAME_FIELD_NAME = "name";
        public static final String PASSWORD_FIELD_NAME = "password";

        @DatabaseField(columnName = "id", generatedId = true)
        private int id;

        @DatabaseField(columnName = "name", canBeNull = false)
        private String name;

        @DatabaseField(columnName = "password")
        private String password;

        Account() {
            // all persisted classes must define a no-arg constructor with at least package visibility
        }

        public Account(String name) {
            this.name = name;
        }

        public Account(String name, String password) {
            this.name = name;
            this.password = password;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other == null || other.getClass() != getClass()) {
                return false;
            }
            return name.equals(((Account) other).name);
        }
    }

