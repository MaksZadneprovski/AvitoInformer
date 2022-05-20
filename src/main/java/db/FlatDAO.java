package db;

import java.sql.*;
import java.util.*;

public class FlatDAO {

    public List<String> getTopList(Set<String> cities) throws SQLException {
        Map<Long, String> treeMap = new TreeMap<>();
        try {
            Statement statement = PostgreConnection.getFlatAvitoConnection().createStatement();
            for (String city : cities) {
                ResultSet rs = statement.executeQuery("SELECT price,href FROM flat WHERE city = '"+ city +"' AND date > current_date - integer  '3';");
                while (rs.next()) {
                    treeMap.put(rs.getLong(1),rs.getString(2));
                }
                rs.close();
            }
            statement.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        List<String> topList = new ArrayList<>();
        Set<Long> keySet = treeMap.keySet();
        for (int i = 0; i < 20; i++) {
            try {
                Long key = (Long) keySet.toArray()[i];
                topList.add(treeMap.get(key));
            }catch (Exception e){
                System.out.println("hueta");
            }
        }
        return topList;
    }
}
