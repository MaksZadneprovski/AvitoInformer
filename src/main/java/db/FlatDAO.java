package db;

import java.sql.*;
import java.util.*;

public class FlatDAO {

    public List<String> getTopList(HashSet<String> cities) throws SQLException {
        Map<Long, String> treeMap = new TreeMap<>();
        try {
            Statement statement = PostgreConnection.getFlatAvitoConnection().createStatement();
            for (String city : cities) {
                ResultSet rs = statement.executeQuery("SELECT price,href FROM flat WHERE city = '"+ city +"';");
                while (rs.next()) {
                    treeMap.put(rs.getLong(1),rs.getString(2));
                }
                rs.close();
            }
            statement.close();
        } finally {
            PostgreConnection.getFlatAvitoConnection().close();
        }
        List<String> topList = new ArrayList<>();
        Set<Long> keySet = treeMap.keySet();
        for (int i = 0; i < 21; i++) {
            Long key = (Long) keySet.toArray()[i];
            topList.add(treeMap.get(key));
        }
        return topList;
    }
}
