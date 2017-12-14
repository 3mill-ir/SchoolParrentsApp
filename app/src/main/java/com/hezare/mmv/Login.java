package com.hezare.mmv;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hezare.mmv.Utils.AppUpdate;
import com.hezare.mmv.WebSide.Parser;
import com.hezare.mmv.WebSide.SendRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {


    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean loggedin = preferences.getBoolean("Login", false);
        if(loggedin){
            if(Utli.isNetworkConnected()){
                Logined();
            }else{
                Toast.makeText(this, "به اینترنت متصل نیستید", Toast.LENGTH_LONG).show();
            }

        } else {
            final SharedPreferences pref = getSharedPreferences("ShowDialog", 0);
            Log.i("showDialog", String.valueOf(pref.getBoolean("ShowDialog", false)));
            if (pref.getBoolean("ShowDialog", false)) {
                try {
                    new AppUpdate(this).check_Version();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        setContentView(R.layout.login);
        Utli.StrictMode();
        Utli.changeFont(getWindow().getDecorView());
        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        findViewById(R.id.loginbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameWrapper.getEditText().getText().toString().trim();
                String password = passwordWrapper.getEditText().getText().toString().trim();
                if (username.matches("")) {
                    usernameWrapper.setError("نام کاربری نباید خالی باشد!");
                    return;
                } else {
                    usernameWrapper.setErrorEnabled(false);

                }
                if (password.matches("")) {
                    passwordWrapper.setError("رمز عبور نباید خالی باشد!");
                    return;
                } else {
                    passwordWrapper.setErrorEnabled(false);
                }

                if(Utli.isNetworkConnected()){
                    doLogin(username, password);
                }else{
                    Toast.makeText(Login.this, "به اینترنت متصل نیستید", Toast.LENGTH_LONG).show();
                }

            }
        });

findViewById(R.id.forgettxt).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(Utli.isNetworkConnected()){
            startActivity(new Intent(Login.this,Forget.class));
        }else{
            Toast.makeText(Login.this, "به اینترنت متصل نیستید", Toast.LENGTH_LONG).show();
        }


    }
});

    }

    private void Logined() {
        MakeDialog(DilogType.LOADING, null);
        SharedPreferences preferencesabsent = PreferenceManager.getDefaultSharedPreferences(Login.this);
        String username = preferencesabsent.getString("U", "");
        String password = preferencesabsent.getString("P", "");
        String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        SendRequest.SendPostLogin(username, password, android_id);
        new SendRequest().setOnLoginCompleteListner(new SendRequest.OnLoginCompleteListner() {
            @Override
            public void OnLoginCompleteed(String response) {

                try {
                    JSONObject Root = Parser.Parse(response).getJSONObject(0);
                    String Text = Root.getString("Text");
                    String Key = Root.getString("Key");
                    if (Key.trim().matches("OK")) {

                        JSONArray DaneshAmuzan = Root.getJSONArray("DaneshAmuzan");
                        for (int i=0; i < DaneshAmuzan.length(); i++) {
                            JSONObject DaneshAmuzanObj = DaneshAmuzan.getJSONObject(i);
                            String NAME = DaneshAmuzanObj.getString("FullName");
                            String ID = DaneshAmuzanObj.getString("ID");
                            if(App.getDB().numberOfRowsStudentAva(Integer.parseInt(ID))<1){
                                App.getDB().insertStudent(Integer.parseInt(ID),NAME);
                            }

                        }

                        finish();
                        startActivity(new Intent(Login.this,MainActivity.class));
                    } else {
                        Toast.makeText(Login.this, "خطا در بارگذاری برنامه", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } catch (JSONException e) {

                }
            }
        });
        new SendRequest().setOnLoginErrorListner(new SendRequest.OnLoginErrorListner() {
            @Override
            public void OnLoginErrored(String response) {
                if (response.trim().contains("connectionError")) {
                    Toast.makeText(Login.this, "خطا در اتصال به سرور!", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Toast.makeText(Login.this, "خطایی پیش آمده!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    public void doLogin(final String username, final String password) {
        MakeDialog(DilogType.LOADING, null);
        String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        SendRequest.SendPostLogin(username, password, android_id);
        new SendRequest().setOnLoginCompleteListner(new SendRequest.OnLoginCompleteListner() {
            @Override
            public void OnLoginCompleteed(String response) {
                Log.e("json",response);
                try {
                    JSONObject Root = Parser.Parse(response).getJSONObject(0);
                    String Text = Root.getString("Text");
                    String Key = Root.getString("Key");
                    String OvliaChatID = Root.getString("OvliaChatID");
                 //   String Name = oneObject.getString("Option");
                    if (Key.trim().matches("OK")) {
                        if (loading.isShowing()) {
                            loading.dismiss();
                        }
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        JSONArray DaneshAmuzan = Root.getJSONArray("DaneshAmuzan");
                        for (int i=0; i < DaneshAmuzan.length(); i++) {
                            JSONObject DaneshAmuzanObj = DaneshAmuzan.getJSONObject(i);
                            String NAME = DaneshAmuzanObj.getString("FullName");
                            String ID = DaneshAmuzanObj.getString("ID");

                            if(App.getDB().numberOfRowsStudentAva(Integer.parseInt(ID))<1){
                                App.getDB().insertStudent(Integer.parseInt(ID),NAME);
                            }

                        }

                        if(DaneshAmuzan.length()>0){
                            JSONObject DaneshAmuzanObj = DaneshAmuzan.getJSONObject(0);
                            String ID = DaneshAmuzanObj.getString("ID");
                            editor.putString("ID",ID);
                            editor.putBoolean("Login",true);
                            editor.putString("U",username);
                            editor.putString("P",password);
                            editor.putString("OvliaChatID",OvliaChatID);
                            editor.apply();
                            finish();
                            startActivity(new Intent(Login.this,MainActivity.class));
                            Toast.makeText(Login.this, Text, Toast.LENGTH_SHORT).show();

                        }else{
                            MakeDialog(DilogType.ERROR, "دانش آموزی برای این ولی ثبت نشده!");

                        }
                    } else {
                        MakeDialog(DilogType.ERROR, Text);

                    }

                } catch (JSONException e) {
                    Log.e("JSONException",e.getMessage());

                }
            }
        });
        new SendRequest().setOnLoginErrorListner(new SendRequest.OnLoginErrorListner() {
            @Override
            public void OnLoginErrored(String response) {
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
            loading = new ProgressDialog(Login.this);
            loading.setMessage("صبر کنید...");
            loading.setCancelable(false);
            loading.show();
        } else if (type == DilogType.ERROR) {
            if (loading.isShowing()) {
                loading.dismiss();
            }
            final AlertDialog alt = new AlertDialog.Builder(Login.this).create();
            alt.setTitle(Html.fromHtml("<p style=\"color:red;\">خطا!</p>"));
            alt.setMessage(Text);
            alt.setButton(Dialog.BUTTON_POSITIVE, "تمام", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alt.dismiss();

                }
            });
            alt.show();
        }
    }

    public enum DilogType {
        LOADING,
        ERROR
    }
}

