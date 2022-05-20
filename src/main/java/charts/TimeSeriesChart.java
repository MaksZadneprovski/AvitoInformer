package charts;

import db.StatisticsFlatAvitoDAO;
import model.FlatAvito;
import model.Periods;
import model.StatisticsFlatAvito;
import model.Yaxis;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Set;

public class TimeSeriesChart {
    JFreeChart chart;
    public TimeSeriesChart(final String title, List<StatisticsFlatAvito> statisticsFlatAvitoList, Set<String> cities, Yaxis yaxis ) {
        XYDataset dataset = StatisticsFlatAvito.createTimeSeriesCollection(statisticsFlatAvitoList,cities, yaxis);
        chart = createChart( dataset, title );

//         Для создания окна
//        final ChartPanel chartPanel = new ChartPanel( chart );
//        chartPanel.setPreferredSize( new Dimension( 1000 , 600 ) );
//        chartPanel.setMouseZoomable( true , false );
//        setContentPane( chartPanel );
        final XYPlot plot = chart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
//        renderer.setSeriesPaint( 0 , Color.getHSBColor(1,66,77) );
//        renderer.setSeriesPaint( 1 , Color.GREEN );
//        renderer.setSeriesPaint( 2 , Color.YELLOW );
        for (int i = 0; i < 20; i++) {
            renderer.setSeriesStroke( i , new BasicStroke( 3.0f ) );
        }


//        renderer.setSeriesStroke( 1 , new BasicStroke( 2.0f ) );
//        renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
//        renderer.setDefaultShapesVisible(false);
        plot.setRenderer( renderer );
//        setContentPane( chartPanel );
    }

    private JFreeChart createChart( final XYDataset dataset, String title ) {
        return ChartFactory.createTimeSeriesChart(
                title,
                "",
                "",
                dataset,
                true,
                false,
                false);
    }

    public static InputFile getJpeg(Set<String> cities, Yaxis yaxis, String title, Periods periods) throws IOException {
        StatisticsFlatAvitoDAO statisticsFlatAvitoDAO = new StatisticsFlatAvitoDAO();
        TimeSeriesChart timeSeriesChart = new TimeSeriesChart(title, statisticsFlatAvitoDAO.getDataList(cities, periods), cities, yaxis);;

        int width = 3472/5;
        int height = 4624/5;
        File timeChart = new File( "TimeChart.jpeg" );
        ChartUtils.saveChartAsJPEG( timeChart, timeSeriesChart.chart, width, height );
        return   new InputFile(timeChart);
    }
}