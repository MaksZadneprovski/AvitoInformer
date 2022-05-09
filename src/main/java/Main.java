import charts.TimeSeriesChart;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        Set<String> set = new HashSet<>();
        set.add("Волжский");
        //TimeSeriesChart.getJpeg(set);
    }
}
