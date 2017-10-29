package com.hezare.mmv;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hezare.mmv.Adapter.ClassGradeListAdapter;
import com.hezare.mmv.Models.ClassGradeListModel;
import com.hezare.mmv.Models.GeybatListModel;
import com.hezare.mmv.WebSide.Parser;
import com.hezare.mmv.WebSide.SendRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClassGradeDetails extends AppCompatActivity{


    BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;
    public enum DilogType {
        LOADING,
        ERROR
    }
    ProgressDialog loading;
    String tabel="<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "<style>\n" +
            "table {\n" +
            "    width:100%;\n" +
            "\t\n" +
            "}\n" +
            "table, th, td {\n" +
            "    border: 1px solid #4d4d4d;\n" +
            "    border-collapse: collapse;\n" +
            "}\n" +
            "th, td {\n" +
            "    padding: 3px;\n" +
            "    text-align: center;\n" +
            "color: #e91e63; \n" +
            "font-weight:bold;\n"+
            "}\n" +
            "table#t01 tr:nth-child(even) {\n" +
            "    background-color: #fff;\n" +
            "}\n" +
            "table#t01 tr:nth-child(odd) {\n" +
            "   background-color:#fff;\n" +
            "}\n" +
            "table#t01 th {\n" +
            "    background-color: #4d4d4d;\n" +
            "    color: #fff;\n" +
            "}\n" +
            "#tarikh\t{\n" +
            "color: #000; \n" +
            "}"+
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "\n" +
            "<table id=\"t01\" align=\"center\">\n" +
            "  <tr>\n" +
            "    <th>نمره کسب شده</th>\n" +
            "    <th>تاریخ</th> \n" +
            "  </tr>\n"
 /*               "  <tr>\n" +
                "    <td>20</td>\n" +
                "    <td>95/2/4</td>\n" +
                "  </tr>\n" +*/
            ;
    WebView htmlWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classgradegrid_details);
        Utli.StrictMode();
        Utli.changeFont(getWindow().getDecorView());
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView)findViewById(R.id.classgradedetails_title)).setText("نمرات "+getIntent().getStringExtra("title"));

        final LinearLayout nomrelay=(LinearLayout)findViewById(R.id.nomrelay);
        final TextView nomretxt=(TextView)findViewById(R.id.nomretxt);
        final LinearLayout nemodarlay=(LinearLayout)findViewById(R.id.nemodarlay);
        final TextView nemodartxt=(TextView)findViewById(R.id.nemodartxt);
        final RelativeLayout webconteiner=(RelativeLayout)findViewById(R.id.webconteiner);
        final RelativeLayout chartconteiner=(RelativeLayout)findViewById(R.id.chartconteiner);

        nomrelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nemodarlay.setBackgroundColor(Color.parseColor("#9e9e9e"));
                nomrelay.setBackgroundColor(Color.parseColor("#4caf50"));
                nomretxt.setTextColor(Color.WHITE);
                nemodartxt.setTextColor(Color.parseColor("#cfcfcf"));
                webconteiner.setVisibility(View.VISIBLE);
                chartconteiner.setVisibility(View.GONE);
            }
        });
        nemodarlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomrelay.setBackgroundColor(Color.parseColor("#9e9e9e"));
                nemodarlay.setBackgroundColor(Color.parseColor("#4caf50"));
                nemodartxt.setTextColor(Color.WHITE);
                nomretxt.setTextColor(Color.parseColor("#cfcfcf"));
                webconteiner.setVisibility(View.GONE);
                chartconteiner.setVisibility(View.VISIBLE);

            }
        });



        DoLoad(getIntent().getStringExtra("id"));


         htmlWebView = (WebView)findViewById(R.id.tabel);
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(false);











        chart = (BarChart) findViewById(R.id.chart1);

        BARENTRY = new ArrayList<>();
        chart.setDescription("میانگین نمرات دانش آموز بر اساس درس");
        chart.setDescriptionColor(Color.BLACK);
        chart.setDescriptionTextSize(10);
        chart.setDescriptionTypeface(Typeface.DEFAULT_BOLD);
        chart.getXAxis().setLabelsToSkip(0);
        chart.setNoDataText("----");
        BarEntryLabels = new ArrayList<String>();








        }

    private void DoLoad(String BarnameHaftegiId) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String STID = preferences.getString("ID", "");
        MakeDialog(DilogType.LOADING, null);
        SendRequest.GetPostClassListDetails(STID,BarnameHaftegiId);

        new SendRequest().setOnClassListDetailsCompleteListner(new SendRequest.OnClassListDetailsCompleteListner() {
            @Override
            public void OnClassListDetailsCompleteed(String response) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                Log.e("Json : ",response);
                try {
                    String result=response.replaceAll("\\\\","").replaceAll("^\"|\"$", "");
                    JSONObject jObject = new JSONObject(result);
                    JSONObject Root = new JSONObject(jObject.get("Root").toString());
                    JSONArray Nomarat = Root.getJSONArray("Nomarat");
                    JSONArray Chart = Root.getJSONArray("Chart");
                    if(Nomarat.length()<1){
                        MakeDialog(DilogType.ERROR, "تاکنون نمره ای در سیستم ثبت نگردیده است!");
                        return;

                    }
                    for (int i=0; i < Nomarat.length(); i++) {
                        JSONObject NomaratArrayObj = Nomarat.getJSONObject(i);
                        String  NomreDars = NomaratArrayObj.getString("NomreDars");
                        String  Tarikh = NomaratArrayObj.getString("Tarikh");

                        String item=" <tr>\n" +
                                "    <td>"+NomreDars+"</td>\n" +
                                "    <td id=\"tarikh\">"+Tarikh+"</td>\n" +
                                "  </tr>";

                        tabel=tabel+item;

                    }


                    for (int i=0; i < Chart.length(); i++) {
                        JSONObject ChartArrayObj = Chart.getJSONObject(i);
                        String  Avrage = ChartArrayObj.getString("Avrage");
                        String  Month = ChartArrayObj.getString("Month");
                        BARENTRY.add(new BarEntry(Float.valueOf(Avrage), i,Month));
                        BarEntryLabels.add(Month);



                    }

                    tabel=tabel+"</table>\n" +
                            "\n" +
                            "</body>\n" +
                            "</html>";
                    htmlWebView.loadDataWithBaseURL(null, tabel, "text/html", "utf-8", null);
                    Bardataset = new BarDataSet(BARENTRY, "سطح نمره ها");
                    BARDATA = new BarData(BarEntryLabels, Bardataset);
                    Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    final Handler handler = new Handler();
                    chart.setVisibility(View.VISIBLE);
                    chart.setData(BARDATA);
                    chart.animateY(3000);


                } catch (JSONException e) {
                    MakeDialog(DilogType.ERROR, "خطایی پیش آمده!");
                    Log.e("Json : ",e.getMessage());


                }

            }
        });
        new SendRequest().setOnClassListDetailsErrorListner(new SendRequest.OnClassListDetailsErrorListner() {
            @Override
            public void OnClassListDetailsErrored(String response) {
                if (response.trim().contains("connectionError")) {
                    MakeDialog(DilogType.ERROR, "خطا در اتصال به سرور!");

                } else {
                    MakeDialog(DilogType.ERROR, "خطایی پیش آمده!");

                }
            }
        });


    }
    private void MakeDialog(DilogType type, String Text) {
        if (type == DilogType.LOADING) {
            loading = new ProgressDialog(ClassGradeDetails.this);
            loading.setMessage("درحال بارگذاری...");
            loading.setCancelable(false);
            loading.show();
        } else if (type == DilogType.ERROR) {
            if (loading.isShowing()) {
                loading.dismiss();
            }
            final AlertDialog alt = new AlertDialog.Builder(ClassGradeDetails.this).create();
            alt.setTitle(Html.fromHtml("<p style=\"color:red;\">خطا!</p>"));
            alt.setMessage(Text);
            alt.setButton(Dialog.BUTTON_POSITIVE, "تمام", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alt.dismiss();
                    finish();

                }
            });
            alt.show();
        }
    }
}

