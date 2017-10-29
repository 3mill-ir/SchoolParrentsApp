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
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hezare.mmv.WebSide.SendRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Karname extends AppCompatActivity{



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
            "    <th>نام درس</th> \n" +
            "  </tr>\n"
 /*               "  <tr>\n" +
                "    <td>20</td>\n" +
                "    <td>95/2/4</td>\n" +
                "  </tr>\n" +*/
            ;



    String tabel2="<!DOCTYPE html>\n" +
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
            "    <th>نام درس</th> \n" +
            "  </tr>\n"
 /*               "  <tr>\n" +
                "    <td>20</td>\n" +
                "    <td>95/2/4</td>\n" +
                "  </tr>\n" +*/
            ;

    WebView term1;
    WebView term2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.karname);
        Utli.StrictMode();
        Utli.changeFont(getWindow().getDecorView());
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DoLoad();


        term1 = (WebView)findViewById(R.id.term1);
        WebSettings webSetting = term1.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(false);

        term2 = (WebView)findViewById(R.id.term2);
        WebSettings webSettingterm2 = term2.getSettings();
        webSettingterm2.setJavaScriptEnabled(true);
        webSettingterm2.setDisplayZoomControls(false);















        }

    private void DoLoad() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String STID = preferences.getString("ID", "");
        MakeDialog(DilogType.LOADING, null);
        SendRequest.SendPostKarname(STID);

        new SendRequest().setOnKarnameCompleteListner(new SendRequest.OnKarnameCompleteListner() {
            @Override
            public void OnKarnameCompleteed(String response) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                Log.e("Json : ",response);
                try {
                    String result=response.replaceAll("\\\\","").replaceAll("^\"|\"$", "");
                    JSONObject jObject = new JSONObject(result);
                    JSONObject Root = new JSONObject(jObject.get("Root").toString());
                    JSONArray TermeAvval = Root.getJSONArray("TermeAvval");
                    JSONArray TermeDovvom = Root.getJSONArray("TermeDovvom");
                    if(TermeAvval.length()<1){
                        String item=" <tr>\n" +
                                "    <td>"+"ثبت نشده"+"</td>\n" +
                                "    <td id=\"tarikh\">"+"ثبت نشده"+"</td>\n" +
                                "  </tr>";

                        tabel=tabel+item;

                    }
                    if(TermeDovvom.length()<1){
                        String item=" <tr>\n" +
                                "    <td>"+"ثبت نشده"+"</td>\n" +
                                "    <td id=\"tarikh\">"+"ثبت نشده"+"</td>\n" +
                                "  </tr>";

                        tabel2=tabel2+item;

                    }
                    for (int i=0; i < TermeAvval.length(); i++) {
                        JSONObject TermeAvvalArrayObj = TermeAvval.getJSONObject(i);
                        String  NomreDars = TermeAvvalArrayObj.getString("Nomre");
                        String  NaameDars = TermeAvvalArrayObj.getString("NaameDars");

                        String item=" <tr>\n" +
                                "    <td>"+NomreDars+"</td>\n" +
                                "    <td id=\"tarikh\">"+NaameDars+"</td>\n" +
                                "  </tr>";

                        tabel=tabel+item;

                    }

                    for (int i=0; i < TermeDovvom.length(); i++) {
                        JSONObject TermeDovvomArrayObj = TermeDovvom.getJSONObject(i);
                        String  NomreDars = TermeDovvomArrayObj.getString("Nomre");
                        String  NaameDars = TermeDovvomArrayObj.getString("NaameDars");

                        String item=" <tr>\n" +
                                "    <td>"+NomreDars+"</td>\n" +
                                "    <td id=\"tarikh\">"+NaameDars+"</td>\n" +
                                "  </tr>";

                        tabel2=tabel2+item;

                    }
                    tabel=tabel+"</table>\n" +
                            "\n" +
                            "</body>\n" +
                            "</html>";
                    tabel2=tabel2+"</table>\n" +
                            "\n" +
                            "</body>\n" +
                            "</html>";
                    term1.loadDataWithBaseURL(null, tabel, "text/html", "utf-8", null);
                    term2.loadDataWithBaseURL(null, tabel2, "text/html", "utf-8", null);

                } catch (JSONException e) {
                    MakeDialog(DilogType.ERROR, "خطایی پیش آمده!");
                    Log.e("Json : ",e.getMessage());


                }

            }
        });
        new SendRequest().setOnKarnameErrorListner(new SendRequest.OnKarnameErrorListner() {
            @Override
            public void OnKarnameErrored(String response) {
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
            loading = new ProgressDialog(Karname.this);
            loading.setMessage("درحال بارگذاری...");
            loading.setCancelable(false);
            loading.show();
        } else if (type == DilogType.ERROR) {
            if (loading.isShowing()) {
                loading.dismiss();
            }
            final AlertDialog alt = new AlertDialog.Builder(Karname.this).create();
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

