package charts;

import db.StatisticsFlatAvitoDAO;
import model.StatisticsFlatAvito;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;

import java.awt.*;

public class TimeSeriesChart extends ApplicationFrame {

    public TimeSeriesChart(final String title ) {
        super( title );
        StatisticsFlatAvitoDAO statisticsFlatAvitoDAO = new StatisticsFlatAvitoDAO();
        XYDataset dataset = StatisticsFlatAvito.createTimeSeriesCollection(statisticsFlatAvitoDAO.getAllDataList());
        final JFreeChart chart = createChart( dataset );
        final ChartPanel chartPanel = new ChartPanel( chart );

        chartPanel.setPreferredSize( new Dimension( 1000 , 600 ) );
        chartPanel.setMouseZoomable( true , false );
        setContentPane( chartPanel );

        final XYPlot plot = chart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
//        renderer.setSeriesPaint( 0 , Color.getHSBColor(1,66,77) );
//        renderer.setSeriesPaint( 1 , Color.GREEN );
//        renderer.setSeriesPaint( 2 , Color.YELLOW );
        renderer.setSeriesStroke( 0 , new BasicStroke( 2.0f ) );
        renderer.setSeriesStroke( 1 , new BasicStroke( 2.0f ) );
        renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
        //renderer.setDefaultShapesVisible(false);
        plot.setRenderer( renderer );
        setContentPane( chartPanel );
    }

    private JFreeChart createChart( final XYDataset dataset ) {
        return ChartFactory.createTimeSeriesChart(
                "Цены",
                "Дата",
                "Рубли",
                dataset,
                true,
                false,
                false);
    }

    public static void main( final String[ ] args ) {
        final String title = "Авито статистика";
        final TimeSeriesChart demo = new TimeSeriesChart( title );
        demo.pack( );
        demo.setVisible( true );
    }
}