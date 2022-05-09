package model;

import lombok.Data;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
public class StatisticsFlatAvito {
    private int dollar;
    private long averagePriceMeter;
    private long medianPriceMeter;
    private long averagePrice;
    private long medianPrice;
    private long averagePrice1;
    private long medianPrice1;
    private long averagePrice2;
    private long medianPrice2;
    private long averagePrice3;
    private long medianPrice3;
    private String city;
    private long date;
    private long countFlats;

    public StatisticsFlatAvito(int dollar, long averagePriceMeter, long medianPriceMeter,long averagePrice, long medianPrice, long averagePrice1, long medianPrice1, long averagePrice2, long medianPrice2, long averagePrice3, long medianPrice3, String city, long date,long countFlats) {
        this.dollar = dollar;
        this.averagePriceMeter = averagePriceMeter;
        this.medianPriceMeter = medianPriceMeter;
        this.averagePrice = averagePrice;
        this.medianPrice = medianPrice;
        this.averagePrice1 = averagePrice1;
        this.medianPrice1 = medianPrice1;
        this.averagePrice2 = averagePrice2;
        this.medianPrice2 = medianPrice2;
        this.averagePrice3 = averagePrice3;
        this.medianPrice3 = medianPrice3;
        this.city = city;
        this.date = date;
        this.countFlats = countFlats;
    }

    public static XYDataset createTimeSeriesCollection(List<StatisticsFlatAvito> statisticsFlatAvitoList, Set<String> cities, Yaxis yaxis) {

        final TimeSeriesCollection dataset = new TimeSeriesCollection( );

        for (String s: cities) {
            List<StatisticsFlatAvito> sFAList  = statisticsFlatAvitoList.stream().filter(sFA -> sFA.getCity().equals(s)).collect(Collectors.toList());
            TimeSeries series = new TimeSeries(s);
            for (StatisticsFlatAvito f : sFAList) {
                Hour hour = new Hour(new Date(f.getDate()));

                switch (yaxis){
                    case MedianPrice:
                        series.addOrUpdate(hour, f.getMedianPrice());
                        break;
                    case MedianPrice1:
                        series.addOrUpdate(hour, f.getMedianPrice1());
                        break;
                    case MedianPrice2:
                        series.addOrUpdate(hour, f.getMedianPrice2());
                        break;
                    case MedianPrice3:
                        series.addOrUpdate(hour, f.getMedianPrice3());
                        break;
                    case AveragePrice:
                        series.addOrUpdate(hour, f.getAveragePrice());
                        break;
                    case AveragePrice1:
                        series.addOrUpdate(hour, f.getAveragePrice1());
                        break;
                    case AveragePrice2:
                        series.addOrUpdate(hour, f.getAveragePrice2());
                        break;
                    case AveragePrice3:
                        series.addOrUpdate(hour, f.getAveragePrice3());
                        break;
                    case AveragePriceMeter:
                        series.addOrUpdate(hour, f.getAveragePriceMeter());
                        break;
                    case MedianPriceMeter:
                        series.addOrUpdate(hour, f.getMedianPriceMeter());
                        break;
                    case CountFlats:
                        series.addOrUpdate(hour, f.getCountFlats());
                        break;
                }

            }
            dataset.addSeries(series);
        }
        return dataset;
    }

}


