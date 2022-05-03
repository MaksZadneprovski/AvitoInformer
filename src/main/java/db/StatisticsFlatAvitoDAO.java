package db;

import model.StatisticsFlatAvito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StatisticsFlatAvitoDAO {

    public List<StatisticsFlatAvito> getAllDataList() {
        List<StatisticsFlatAvito> sFAList = new ArrayList<>();
        try {
            Statement st = PostgreConnection.getFlatAvitoConnection().createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * from statistics;");
            while (resultSet.next()){
                sFAList.add(new StatisticsFlatAvito(
                        resultSet.getInt(2),
                        resultSet.getLong(3),
                        resultSet.getLong(4),
                        resultSet.getLong(5),
                        resultSet.getLong(6),
                        resultSet.getLong(7),
                        resultSet.getLong(8),
                        resultSet.getLong(9),
                        resultSet.getLong(10),
                        resultSet.getLong(11),
                        resultSet.getLong(12),
                        resultSet.getString(13),
                        resultSet.getDate(14).getTime()
                ));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return sFAList;
    }
}
