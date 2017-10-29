package com.hezare.mmv;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.hezare.mmv.WebSide.Parser;
import com.hezare.mmv.WebSide.SendRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Mali extends AppCompatActivity{



    public enum DilogType {
        LOADING,
        ERROR
    }
    ProgressDialog loading;
    String nagdi="<!DOCTYPE html> \n" +
            "            <html> \n" +
            "            <head> \n" +
            "                <meta charset=utf-8> \n" +
            "            <style> \n" +
            "            table { \n" +
            "                width:100%;  \n" +
            "            } \n" +
            "            table, th, td { \n" +
            "                border: 1px solid #4d4d4d; \n" +
            "                border-collapse: collapse; \n" +
            "            } \n" +
            "            th, td { \n" +
            "                padding: 3px; \n" +
            "                text-align: center; \n" +
            "\t\t\t\tcolor: #e91e63; \n" +
            "\t\t\t\tfont-weight:bold;\n" +
            "            } \n" +
            "            table#t01 tr:nth-child(even) { \n" +
            "                background-color: #fff; \n" +
            "            } \n" +
            "            table#t01 tr:nth-child(odd) { \n" +
            "               background-color:#fff; \n" +
            "            } \n" +
            "            table#t01 th { \n" +
            "                background-color: #4d4d4d; \n" +
            "                color: #fff; \n" +
            "            }\n" +
            "#tarikh\t{\n" +
            "color: #000; \n" +
            "}\n" +
            "\t\t\n" +
            "            </style> \n" +
            "            </head> \n" +
            "            <body> \n" +
            "             \n" +
            "             \n" +
            "            <table id=t01 align=center> \n" +
            "              <tr> \n" +
            "\t\t\t    <th>از بابت</th> \n" +
            "                <th>مبلغ پرداختی</th> \n" +
            "                <th>تاریخ</th>  \n" +
            "              </tr>";
 /*               "  <tr>\n" +
                "    <td>20</td>\n" +
                "    <td>95/2/4</td>\n" +
                "  </tr>\n" +*/
            ;



    String checki="<!DOCTYPE html> \n" +
            "            <html> \n" +
            "            <head> \n" +
            "                <meta charset=utf-8> \n" +
            "            <style> \n" +
            "            table { \n" +
            "                width:100%;  \n" +
            "            } \n" +
            "            table, th, td { \n" +
            "                border: 1px solid #4d4d4d; \n" +
            "                border-collapse: collapse; \n" +
            "            } \n" +
            "            th, td { \n" +
            "                padding: 3px; \n" +
            "                text-align: center; \n" +
            "\t\t\t\tcolor: #e91e63; \n" +
            "\t\t\t\tfont-weight:bold;\n" +
            "            } \n" +
            "            table#t01 tr:nth-child(even) { \n" +
            "                background-color: #fff; \n" +
            "            } \n" +
            "            table#t01 tr:nth-child(odd) { \n" +
            "               background-color:#fff; \n" +
            "            } \n" +
            "            table#t01 th { \n" +
            "                background-color: #4d4d4d; \n" +
            "                color: #fff; \n" +
            "            }\n" +
            "\n" +
            "\t\t\n" +
            "            </style> \n" +
            "            </head> \n" +
            "            <body> \n" +
            "             \n" +
            "             \n" +
            "            <table id=t01 align=center> \n" +
            "              <tr> \n" +
            "\t\t\t  \t<th>وصول</th> \n" +
            "\t\t\t    <th>بانک</th> \n" +
            "\t\t\t    <th>از بابت</th> \n" +
            "                <th>مبلغ چک</th> \n" +
            "                <th>تاریخ چک</th>  \n" +
            "              </tr>";
 /*               "  <tr>\n" +
                "    <td>20</td>\n" +
                "    <td>95/2/4</td>\n" +
                "  </tr>\n" +*/


    WebView nagd;
    WebView check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mali);
        Utli.StrictMode();
        Utli.changeFont(getWindow().getDecorView());
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DoLoad();


        nagd = (WebView)findViewById(R.id.nagd);
        WebSettings webSetting = nagd.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(false);

        check = (WebView)findViewById(R.id.check);
        WebSettings webSettingterm2 = check.getSettings();
        webSettingterm2.setJavaScriptEnabled(true);
        webSettingterm2.setDisplayZoomControls(false);















        }

    private void DoLoad() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String STID = preferences.getString("ID", "");
        MakeDialog(DilogType.LOADING, null);
        SendRequest.SendPostMali(STID);

        new SendRequest().setOnMaliCompleteListner(new SendRequest.OnMaliCompleteListner() {
            @Override
            public void OnMaliCompleteed(String response) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                Log.e("Json : ",response);
                try {
                    JSONObject Root = Parser.Parse(response).getJSONObject(0);
                    JSONArray PardakhthayeNaghdi = Root.getJSONArray("PardakhthayeNaghdi");
                    JSONArray PardakhthayeChecki = Root.getJSONArray("PardakhthayeChecki");
                    Log.e("Root : ",Root.toString());

                    if(PardakhthayeNaghdi.length()<1){
                        String item=" <tr>\n" +
                                "    <td>"+"ثبت نشده"+"</td>\n" +
                                "    <td>"+"ثبت نشده"+"</td>\n" +
                                "    <td>"+"ثبت نشده"+"</td>\n" +
                                "  </tr>";

                        nagdi=nagdi+item;

                    }
                    if(PardakhthayeChecki.length()<1){
                        String item=" <tr>\n" +
                                "    <td>"+"ثبت نشده"+"</td>\n" +
                                "    <td>"+"ثبت نشده"+"</td>\n" +
                                "    <td>"+"ثبت نشده"+"</td>\n" +
                                "    <td>"+"ثبت نشده"+"</td>\n" +
                                "    <td>"+"ثبت نشده"+"</td>\n" +
                                "  </tr>";

                        checki=checki+item;

                    }
                    for (int i=0; i < PardakhthayeNaghdi.length(); i++) {
                        JSONObject PardakhthayeNaghdiArrayObj = PardakhthayeNaghdi.getJSONObject(i);
                        String  Tarikh = PardakhthayeNaghdiArrayObj.getString("Tarikh");
                        String  MablaghePardakhti = PardakhthayeNaghdiArrayObj.getString("MablaghePardakhti");
                        String  AzBabate = PardakhthayeNaghdiArrayObj.getString("AzBabate");

                        String item=" <tr>\n" +
                                "    <td>"+AzBabate+"</td>\n" +
                                "    <td>"+MablaghePardakhti+"</td>\n" +
                                "    <td>"+Tarikh+"</td>\n" +
                                "  </tr>";

                        nagdi=nagdi+item;

                    }

                    for (int i=0; i < PardakhthayeChecki.length(); i++) {
                        JSONObject PardakhthayeCheckiArrayObj = PardakhthayeChecki.getJSONObject(i);
                        String  TarikheCheck = PardakhthayeCheckiArrayObj.getString("TarikheCheck");
                        String  MablagheCheck = PardakhthayeCheckiArrayObj.getString("MablagheCheck");
                        String  AzBabate = PardakhthayeCheckiArrayObj.getString("AzBabate");
                        String  Banke = PardakhthayeCheckiArrayObj.getString("Banke");
                        String  VaziateVosul = PardakhthayeCheckiArrayObj.getString("VaziateVosul");

                        String item=" <tr>\n" +
                                "    <td>"+VaziateVosul+"</td>\n" +
                                "    <td>"+Banke+"</td>\n" +
                                "    <td>"+AzBabate+"</td>\n" +
                                "    <td>"+MablagheCheck+"</td>\n" +
                                "    <td>"+TarikheCheck+"</td>\n" +
                                "  </tr>";

                        checki=checki+item;

                    }
                    nagdi=nagdi+"</table>\n" +
                            "\n" +
                            "</body>\n" +
                            "</html>";
                    checki=checki+"</table>\n" +
                            "\n" +
                            "</body>\n" +
                            "</html>";

                    nagd.loadDataWithBaseURL(null, nagdi, "text/html", "utf-8", null);
                    check.loadDataWithBaseURL(null, checki, "text/html", "utf-8", null);

                } catch (JSONException e) {
                    MakeDialog(DilogType.ERROR, "خطایی پیش آمده!");
                    Log.e("Json : ",e.getMessage());


                }

            }
        });
        new SendRequest().setOnMaliErrorListner(new SendRequest.OnMaliErrorListner() {
            @Override
            public void OnMaliErrored(String response) {
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
            loading = new ProgressDialog(Mali.this);
            loading.setMessage("درحال بارگذاری...");
            loading.setCancelable(false);
            loading.show();
        } else if (type == DilogType.ERROR) {
            if (loading.isShowing()) {
                loading.dismiss();
            }
            final AlertDialog alt = new AlertDialog.Builder(Mali.this).create();
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

