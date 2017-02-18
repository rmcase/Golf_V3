package com.ryancase.golf_v3.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import com.ryancase.golf_v3.R;

/**
 * Created by ryancase on 2/16/17.
 */
public class HelpActivity extends AppCompatActivity {

    private TextView holeComponents, profileTab, titleRow;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        titleRow = (TextView) findViewById(R.id.titleRow);
        titleRow.setText(Html.fromHtml("<h1>Help</h1>"));

        profileTab = (TextView) findViewById(R.id.profileTab);
        profileTab.setText(Html.fromHtml("<h4>Tab Functions</h4>" +
                "<p>\n\t• <strong>Profile Tab:</strong> View stats about your overall performance</p>" +
                "<p>\n\t• <strong>Play Tab:</strong> Start a round from an existing or new course</p>" +
                "<p>\n\t• <strong>History Tab:</strong> View deeper statistics about past rounds</p>" +
                "<p>\n\t• <strong>Statistics Tab:</strong> View statistics about your past 10 rounds</p>"));

        holeComponents = (TextView) findViewById(R.id.holeComponents);
        holeComponents.setText(Html.fromHtml("<h4>Playing a Round:</h4>" +
                "<p>\n\t<strong>Components listed from top to bottom</strong></p>" +
                "<p>\n\t• Score Selector</p>" +
                "<p>\n\t• (If new course) Par Selector</p>" +
                "<p>\n\t• Number of putts selector</p>" +
                "<p>\n\t• Checkboxes for various stats</p>" +
                "<p>\n\t• Switch to show the club ratings page</p>"));
    }
}
