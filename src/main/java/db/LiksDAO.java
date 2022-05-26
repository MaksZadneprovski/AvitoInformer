package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LiksDAO {
    public static TreeMap<String, String> getLinks() throws SQLException {
        TreeMap<String, String> links = new TreeMap<>();
        Statement statement = PostgreConnection.getFlatAvitoConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM links;");
        while (rs.next()) {
            String key = rs.getString(1);
            String value = rs.getString(2);
            links.put(key,value);
        }
        rs.close();
        statement.close();
        return links;
    }
}
