package com.example.appprojet.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.appprojet.R;
import com.example.appprojet.models.Event;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {
    private static final int REQUEST_WRITE_STORAGE = 112;
    private static final int CREATE_FILE_REQUEST_CODE = 113;
    private BarChart chart;
    private Button exportCsvBtn;
    private ListView listView;
    private Button addEventBtn;
    private com.example.appprojet.adapters.EventAdapter eventAdapter;
    private List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        chart = findViewById(R.id.barChart);
        exportCsvBtn = findViewById(R.id.btn_export_csv);
        listView = findViewById(R.id.list_events);
        addEventBtn = findViewById(R.id.btn_add_event);

        setupChart();
        exportCsvBtn.setOnClickListener(v -> startExportCsvSaf());
        addEventBtn.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(this, com.example.appprojet.activities.AddEditEventActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEvents();
    }

    private void loadEvents() {
        eventList = Event.fetchAll(this);
        eventAdapter = new com.example.appprojet.adapters.EventAdapter(this, eventList);
        listView.setAdapter(eventAdapter);
        Toast.makeText(this, "Loaded events: " + eventList.size(), Toast.LENGTH_SHORT).show();
    }

    private void setupChart() {
        List<BarEntry> entries = new ArrayList<>();
        final List<String> labels = new ArrayList<>();

        // Fake sample data — replace with real data
        labels.add("Upcoming"); entries.add(new BarEntry(0, 12));
        labels.add("Ongoing"); entries.add(new BarEntry(1, 5));
        labels.add("Completed"); entries.add(new BarEntry(2, 20));

        BarDataSet dataSet = new BarDataSet(entries, "Event Status");
        dataSet.setColors(new int[]{ R.color.teal_700, R.color.teal_900, R.color.amber_500 }, this);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);
        chart.setData(barData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        chart.setFitBars(true);
        chart.getDescription().setEnabled(false);
        chart.animateY(1000);
        chart.invalidate();
    }

    private void startExportCsvSaf() {
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_CREATE_DOCUMENT);
        intent.setType("text/csv");
        intent.putExtra(android.content.Intent.EXTRA_TITLE, "events_report.csv");
        startActivityForResult(intent, CREATE_FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            android.net.Uri uri = data.getData();
            if (uri != null) {
                exportCsvToUri(uri);
            }
        }
    }

    private void exportCsvToUri(android.net.Uri uri) {
        List<Event> events = Event.fetchAll(this);
        try (java.io.OutputStream out = getContentResolver().openOutputStream(uri);
             java.io.OutputStreamWriter writer = new java.io.OutputStreamWriter(out)) {
            writer.write("Title,Date,Location,Status\n");
            for (Event e : events) {
                writer.write(e.getTitle() + "," + e.getDate() + "," + e.getLocation() + "," + e.getStatus() + "\n");
            }
            writer.flush();
            Toast.makeText(this, "CSV exported!", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(this, "Export failed: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_STORAGE) {
            Toast.makeText(this, "Permission logic no longer needed for export.", Toast.LENGTH_SHORT).show();
        }
    }
}