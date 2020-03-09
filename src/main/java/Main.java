import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        String DBUrl = "jdbc:oracle:thin:@//155.158.112.45:1521/oltpstud";

        Runnable[] runners = new Runnable[3];
        Thread[] threads = new Thread[3];

            runners[0] = new DatabaseConnector("ziibd5","haslo1",DBUrl);
            runners[1] = new DatabaseConnector("ziibd1","haslo1",DBUrl);
            runners[2] = new DatabaseConnector("ziibd2","haslo1",DBUrl);


        for(int i=0; i<3; i++) {
            threads[i] = new Thread(runners[i]);
        }

        for (Thread thread : threads){
            thread.start();
        }

    }
}
