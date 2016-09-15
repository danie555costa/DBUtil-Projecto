/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author xdata
 */
public class ReportTheme 
{
    private static int MAX_COLUNS_COLOR;
    public static void circularTheme(JFreeChart chart, String ... colunsName) 
    {
        chart.setBackgroundPaint(new GradientPaint(new Point(0, 0), 
                new Color(20, 20, 20), new Point(400, 200), Color.DARK_GRAY));
        
        TextTitle t = chart.getTitle();
        t.setHorizontalAlignment(HorizontalAlignment.LEFT);
        t.setPaint(new Color(240, 240, 240));
        t.setFont(new Font("Arial", Font.BOLD, 26));
        
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(null);
        plot.setInteriorGap(0.04);
        plot.setOutlineVisible(false);
        int iCount =0;
        
        
         // use gradients and white borders for the section colours
        for(Object s : colunsName)
        {
            plot.setSectionPaint(s.toString(), createGradientPaint(new Color(200, 200, 255), colorItems(iCount ++)));
            if(iCount == MAX_COLUNS_COLOR) iCount = 0;
        }
        
            
        plot.setBaseSectionOutlinePaint(Color.WHITE);
        plot.setSectionOutlinesVisible(true);
        plot.setBaseSectionOutlineStroke(new BasicStroke(2.0f));
        
        plot.setLabelFont(new Font("Courier New", Font.BOLD, 20));
        plot.setLabelLinkPaint(Color.WHITE);
        plot.setLabelLinkStroke(new BasicStroke(2.0f));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.WHITE);
        plot.setLabelBackgroundPaint(null);
        
        
        //        // add a subtitle giving the data source
        TextTitle source = new TextTitle("Source: http://www.bbc.co.uk/news/business-15489523", 
                new Font("Courier New", Font.PLAIN, 12));
        source.setPaint(Color.WHITE);
        source.setPosition(RectangleEdge.BOTTOM);
        source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        chart.addSubtitle(source);
    }
    
    public static void crescentTheme(JFreeChart chart, String ... lineNames)
    {
        
        
         XYPlot plot =  (XYPlot) chart.getPlot();
         
        chart.setBackgroundPaint(Color.white);

        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) 
        {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            //renderer.setBaseShapesVisible(true);
            renderer.setStroke(new BasicStroke(3));
            renderer.setBaseShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(true);
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        
         
         /*
        chart.setBackgroundPaint(new GradientPaint(new Point(0, 0), 
                Color.decode("#121E31"), new Point(400, 200), Color.DARK_GRAY));
        
       
        plot.setBackgroundPaint(null);
        //plot.setInteriorGap(0.04);
        plot.setOutlineVisible(false);
        
        int iCount = 0;
        // use gradients and white borders for the section colours
        for(Object s : lineNames)
        {
          
//          plot.setDomainGridlinePaint(new PrintColorUIResource(25, Color.CYAN)));//s.toString(), createGradientPaint(new Color(200, 200, 255), colorItems(iCount ++)));
            if(iCount == MAX_COLUNS_COLOR) iCount = 0;
        }
       
               
        
        //        // add a subtitle giving the data source
        TextTitle source = new TextTitle("Source: http://www.bbc.co.uk/news/business-15489523", 
                new Font("Courier New", Font.PLAIN, 12));
        source.setPaint(Color.WHITE);
        source.setPosition(RectangleEdge.BOTTOM);
        source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        chart.addSubtitle(source);
                */
    }
    
    
        private static RadialGradientPaint createGradientPaint(Color c1, Color c2) {
        Point2D center = new Point2D.Float(0, 0);
        float radius = 200;
        float[] dist = {0.0f, 1.0f};
        return new RadialGradientPaint(center, radius, dist,
                new Color[] {c1, c2});
    }

    private static Color colorItems(int i) 
    {
        ReportTheme.MAX_COLUNS_COLOR = 5;
        switch(i)
        {
            case 1:return Color.BLUE;
            case 2: return Color.RED;
            case 3: return Color.GREEN;
            default: return Color.YELLOW;
        }
    }

    public static void barTheme(JFreeChart chart, String Despesas, String Ganhos) 
    {
    }
}
