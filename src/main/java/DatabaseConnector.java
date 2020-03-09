import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector implements Runnable {

    private String userName,password,url;

    DatabaseConnector(String userName, String password, String url){
        this.userName = userName;
        this.password = password;
        this.url = url;
    }

    @Override
    public void run() {
        //jdbc:oracle:thin:@localhost:1521:orcl", "system", "Password123"
        try (Connection conn = DriverManager.getConnection(
                url, userName, password)) {

            if (conn != null) {
                System.out.println("User " + userName + " is now connected to database!");
                //System.out.println(conn.prepareCall("select FIRST_NAME from EMPLOYEES").getString(0));

                try {
                    String sql ="SELECT table_name FROM all_tables where owner = (select user from dual)";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    List<String> tables = new ArrayList<String>();
                    while (rs.next()) {
                        tables.add( rs.getString("table_name") );

                        System.out.println("Fetched tables from user: " + userName +" " + tables);
                    }
                } catch (SQLException e ) {
                    System.out.println(e);
                }
            } else {
                System.out.println("Failed to make connection! User: " + userName);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
