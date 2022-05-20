package db;

import model.Periods;
import model.StatisticsFlatAvito;
import model.Yaxis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
                        resultSet.getDate(14).getTime(),
                        resultSet.getLong(15)
                ));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return sFAList;
    }

    public List<StatisticsFlatAvito> getDataList(Set<String> cities, Periods period) {
        List<StatisticsFlatAvito> sFAList = new ArrayList<>();
        String per = "365";
        switch (period){
            case WEEK:
                per = "7";
                break;
            case MONTH:
                per = "31";
                break;
            case HALF_YEAR:
                per = "183";
                break;
        }
        try {
            Statement st = PostgreConnection.getFlatAvitoConnection().createStatement();
            for (String city : cities) {
                ResultSet resultSet = st.executeQuery("SELECT * from statistics WHERE city = '"+ city +"' AND date > current_date - integer  '"+per+"';");
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
                            resultSet.getDate(14).getTime(),
                            resultSet.getLong(15)
                    ));
                }
                resultSet.close();
            }
            st.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return sFAList;
    }
}
