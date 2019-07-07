package com.example.tvs_task;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalariesOnBarGraph extends AppCompatActivity {

    BarChart chart ;
    ArrayList<User> all_users_org;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salaries_on_bar_graph);

        Intent in = getIntent();
        Bundle bundle = in.getExtras();
        all_users_org =(ArrayList<User>) bundle.getSerializable("values");

        chart = findViewById(R.id.barChart);
        chart.setPinchZoom(false);
        chart.setTouchEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDoubleTapToZoomEnabled(true);
        chart.getDescription().setEnabled(false);
        chart.getAxisLeft().setDrawGridLines(false);
        createChart();
    }


    public void createChart()
    {
        try {

            List<BarEntry> entries = new ArrayList<>();
            //entries.add(new BarEntry(0f, 30f));

            int total=10;
            if(all_users_org.size()<10)
            {
                total = all_users_org.size();
            }
            for (int i=0;i<total;i++)
            {
                String sal = all_users_org.get(i).salary;
                sal = sal.replace(",","");
                sal = sal.replace("$","");

                entries.add((new BarEntry(i+1,Float.parseFloat(sal))));
            }

            Log.d("MYMSG",entries.size()+" size");
            BarDataSet set = new BarDataSet(entries, "");
            set.setColors(ColorTemplate.MATERIAL_COLORS);

            BarData data = new BarData(set);
//            data.setBarWidth(2f);

            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setLabelCount(10);

            chart.setData(data);
            chart.setFitBars(true); // make the x-axis fit exactly all bars
            chart.animateY(1000);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
