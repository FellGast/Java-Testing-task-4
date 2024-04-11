import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class AppTest {
    @Test
    @DisplayName("additionFood")
    public void additionFood() throws SQLException {
        Connection connection =
                DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/mem:testdb", "user", "pass");
        Statement statement = connection.createStatement();

        String insert = "INSERT INTO FOOD (FOOD_ID, FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC)\n" +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(insert);
        pstmt.setInt(1, 5);
        pstmt.setString(2, "Банан");
        pstmt.setString(3, "FRUIT");
        pstmt.setInt(4, 1);
        pstmt.executeUpdate();

        String query = "SELECT FOOD_ID, FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC FROM FOOD";
        statement.executeQuery(query);


        ResultSet AssertSet = statement.executeQuery("SELECT COUNT(*) AS count FROM FOOD WHERE FOOD_ID = 5 ");
        AssertSet.next();
        int count = AssertSet.getInt("count");
        Assertions.assertEquals(1, count, "Банан не был добавлен"); // проверка, что запись с FOOD_ID=5


        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int FOOD_ID = resultSet.getInt("FOOD_ID");
            String FOOD_NAME = resultSet.getString("FOOD_NAME");
            String FOOD_TYPE = resultSet.getString("FOOD_TYPE");
            int FOOD_EXOTIC = resultSet.getInt("FOOD_EXOTIC");

            System.out.printf("%d, %s, %s, %d%n", FOOD_ID, FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC);
        }
        connection.close();

    }

    @Test
    @DisplayName("removeFood")
    public void removeFood() throws SQLException {
        Connection connection =
                DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/mem:testdb", "user", "pass");
        Statement statement = connection.createStatement();
        String delete = "DELETE FROM FOOD " +
                "WHERE FOOD_ID = 5 ;";
        statement.executeUpdate(delete);

        String query = "SELECT FOOD_ID, FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC FROM FOOD";
        statement.executeQuery(query);


        ResultSet AssertSet = statement.executeQuery("SELECT COUNT(*) AS count FROM FOOD WHERE FOOD_ID = 5 ");
        AssertSet.next();
        int count = AssertSet.getInt("count");
        Assertions.assertEquals(0, count, "Запись не удалена");


        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int FOOD_ID = resultSet.getInt("FOOD_ID");
            String FOOD_NAME = resultSet.getString("FOOD_NAME");
            String FOOD_TYPE = resultSet.getString("FOOD_TYPE");
            int FOOD_EXOTIC = resultSet.getInt("FOOD_EXOTIC");

            System.out.printf("%d, %s, %s, %d%n", FOOD_ID, FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC);
        }
        connection.close();

    }
}
