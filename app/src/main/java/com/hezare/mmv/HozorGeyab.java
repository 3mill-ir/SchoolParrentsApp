package com.hezare.mmv;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hezare.mmv.Adapter.Drawer_List_Adapter;
import com.hezare.mmv.Adapter.GeybatListAdapter;
import com.hezare.mmv.Adapter.TakalifListAdapter;
import com.hezare.mmv.Models.GeybatListModel;
import com.hezare.mmv.Models.TakalifListModel;
import com.hezare.mmv.Models.TashvigListModel;
import com.hezare.mmv.WebSide.Parser;
import com.hezare.mmv.WebSide.SendRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HozorGeyab extends AppCompatActivity{


    private List<GeybatListModel> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GeybatListAdapter mAdapter;

    public enum DilogType {
        LOADING,
        ERROR
    }
    ProgressDialog loading;
    AppCompatSpinner month;
    String zeroplus;
    DrawerLayout drawer;

    ListView ListDrawer;
    public static int [] icons={R.drawable.ic_menu_gallery,R.drawable.ic_menu_gallery,R.drawable.ic_menu_manage,R.drawable.ic_menu_send,R.drawable.ic_menu_share,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_edit_black_24dp,R.drawable.ic_info_black_24dp,R.drawable.ic_exit_to_app_black_24dp};
    public static String [] items={"نمره کلاسی","نمره امتحانات","بخش مالی","فراخوان ها(اعلانات)","مشاهده تاخیر-غیبت(روزانه)","مشاهده تشویق یا تنبیه","تکالیف روزانه","تماس با ما","ویرایش رمز عبور","درباره ما","خروج از حساب کاربری"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hozorgyabmain);
        Utli.StrictMode();
        Utli.changeFont(getWindow().getDecorView());








        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new GeybatListAdapter(movieList);
         RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(false);
        mAdapter.setHasStableIds(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);




        mAdapter.setOnClickListner(new GeybatListAdapter.OnClickListner() {
            @Override
            public void OnClicked(View view, int position, List<GeybatListModel> moviesList) {

            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        findViewById(R.id.opendrawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ListDrawer=(ListView) navigationView.findViewById(R.id.drawer_slidermenu);
        ListDrawer.setAdapter(new Drawer_List_Adapter(this, items,icons));
        ListDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(HozorGeyab.this,ClassGradeMain.class));
                        finish();
                        break;

                    case 1:
                        startActivity(new Intent(HozorGeyab.this,Karname.class));
                        finish();

                        break;
                    case 7:
                        startActivity(new Intent(HozorGeyab.this,Chat.class));
                        finish();
                        break;
                    case 2:
                        startActivity(new Intent(HozorGeyab.this,Mali.class));
                        finish();

                        break;
                    case 3:
                        startActivity(new Intent(HozorGeyab.this,Elanat.class));
                        finish();
                        break;
                    case 4:
                        startActivity(new Intent(HozorGeyab.this,HozorGeyab.class));
                        finish();
                        break;
                    case 5:
                        startActivity(new Intent(HozorGeyab.this,Tashvig.class));
                        finish();
                        break;

                    case 6:
                        startActivity(new Intent(HozorGeyab.this,Takalif.class));
                        finish();
                        break;
                    case 8:
                        final Dialog dl = new Dialog(HozorGeyab.this);
                        dl.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dl.setContentView(R.layout.changepass);
                        final TextInputLayout oldWrapper = (TextInputLayout) dl.findViewById(R.id.oldWrapper);
                        final TextInputLayout newWrapper = (TextInputLayout) dl.findViewById(R.id.newWrapper);
                        final Button change = (Button) dl.findViewById(R.id.change);
                        final Button cancel = (Button) dl.findViewById(R.id.cancel);
                        change.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String old = oldWrapper.getEditText().getText().toString().trim();
                                final String newe = newWrapper.getEditText().getText().toString().trim();
                                if (old.matches("")) {
                                    oldWrapper.setError("رمز عبور فعلی نباید خالی باشد");
                                    return;
                                } else {
                                    oldWrapper.setErrorEnabled(false);

                                }
                                if (newe.matches("")) {
                                    newWrapper.setError("رمز عبور جدید نباید خالی باشد");
                                    return;
                                } else {
                                    newWrapper.setErrorEnabled(false);
                                }


                                MakeDialog(DilogType.LOADING, null);
                                SendRequest.SendPostChangePass(old,newe);
                                new SendRequest().setOnChangePassCompleteListner(new SendRequest.OnChangePassCompleteListner() {
                                    @Override
                                    public void OnChangePassCompleteed(String response) {
                                        try {
                                            JSONObject oneObject = Parser.Parse(response).getJSONObject(0);
                                            String Text = oneObject.getString("Text");
                                            String Key = oneObject.getString("Key");
                                            if (Key.trim().matches("Success")) {
                                                if (loading.isShowing()) {
                                                    loading.dismiss();
                                                }
                                                dl.dismiss();
                                                Toast.makeText(HozorGeyab.this, Text, Toast.LENGTH_SHORT).show();
                                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(HozorGeyab.this);
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.clear();
                                                editor.commit();
                                                App.getDB().LogOut();
                                                startActivity(new Intent(HozorGeyab.this,Login.class));
                                                finish();
                                            } else {
                                                final AlertDialog alt = new AlertDialog.Builder(HozorGeyab.this).create();
                                                alt.setTitle(Html.fromHtml("<p style=\"color:red;\">خطا!</p>"));
                                                alt.setMessage(Text);
                                                alt.setCancelable(false);
                                                alt.setButton(Dialog.BUTTON_POSITIVE, "تمام", new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        alt.dismiss();

                                                    }
                                                });
                                                alt.show();



                                            }

                                        } catch (JSONException e) {

                                        }
                                    }
                                });
                                new SendRequest().setOnChangePassErrorListner(new SendRequest.OnChangePassErrorListner() {
                                    @Override
                                    public void OnChangePassErrored(String response) {
                                        if (response.trim().contains("connectionError")) {
                                            final AlertDialog alt = new AlertDialog.Builder(HozorGeyab.this).create();
                                            alt.setTitle(Html.fromHtml("<p style=\"color:red;\">خطا!</p>"));
                                            alt.setMessage("خطا در اتصال به سرور!");
                                            alt.setCancelable(false);
                                            alt.setButton(Dialog.BUTTON_POSITIVE, "تمام", new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    alt.dismiss();

                                                }
                                            });
                                            alt.show();

                                        } else {
                                            final AlertDialog alt = new AlertDialog.Builder(HozorGeyab.this).create();
                                            alt.setTitle(Html.fromHtml("<p style=\"color:red;\">خطا!</p>"));
                                            alt.setMessage( "خطایی پیش آمده!");
                                            alt.setCancelable(false);
                                            alt.setButton(Dialog.BUTTON_POSITIVE, "تمام", new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    alt.dismiss();

                                                }
                                            });
                                            alt.show();

                                        }
                                    }
                                });


                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dl.dismiss();
                            }
                        });

                        dl.show();
                        break;
                    case 9:
                        startActivity(new Intent(HozorGeyab.this,About.class));
                        finish();
                        break;
                    case 10:
                        final AlertDialog alt = new AlertDialog.Builder(HozorGeyab.this).create();
                        alt.setTitle(Html.fromHtml("<p style=\"color:red;\">خروج</p>"));
                        alt.setMessage("تمایل دارید از حساب کاربری خود خارج شوید؟");
                        alt.setButton(Dialog.BUTTON_POSITIVE, "آره", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MakeDialog(DilogType.LOADING, null);
                                SendRequest.SendPostLogOut();
                                new SendRequest().setOnLogOutCompleteListner(new SendRequest.OnLogOutCompleteListner() {
                                    @Override
                                    public void OnLogOutCompleteed(String response) {
                                        try {
                                            JSONObject oneObject = Parser.Parse(response).getJSONObject(0);
                                            String Text = oneObject.getString("Text");
                                            String Key = oneObject.getString("Key");
                                            String Name = oneObject.getString("Option");
                                            if (Key.trim().matches("OK")) {
                                                if (loading.isShowing()) {
                                                    loading.dismiss();
                                                }
                                                Toast.makeText(HozorGeyab.this, Text, Toast.LENGTH_SHORT).show();
                                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(HozorGeyab.this);
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.clear();
                                                editor.commit();
                                                App.getDB().LogOut();
                                                ActivityCompat.finishAffinity(HozorGeyab.this);

                                            } else {
                                                final AlertDialog alt = new AlertDialog.Builder(HozorGeyab.this).create();
                                                alt.setTitle(Html.fromHtml("<p style=\"color:red;\">خطا!</p>"));
                                                alt.setMessage( Text);
                                                alt.setCancelable(false);
                                                alt.setButton(Dialog.BUTTON_POSITIVE, "تمام", new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        alt.dismiss();

                                                    }
                                                });
                                                alt.show();
                                            }

                                        } catch (JSONException e) {

                                        }
                                    }
                                });
                                new SendRequest().setOnLogOutErrorListner(new SendRequest.OnLogOutErrorListner() {
                                    @Override
                                    public void OnLogOutErrored(String response) {
                                        if (response.trim().contains("connectionError")) {
                                            final AlertDialog alt = new AlertDialog.Builder(HozorGeyab.this).create();
                                            alt.setTitle(Html.fromHtml("<p style=\"color:red;\">خطا!</p>"));
                                            alt.setMessage("خطا در اتصال به سرور!");
                                            alt.setCancelable(false);
                                            alt.setButton(Dialog.BUTTON_POSITIVE, "تمام", new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    alt.dismiss();

                                                }
                                            });
                                            alt.show();
                                        } else {
                                            final AlertDialog alt = new AlertDialog.Builder(HozorGeyab.this).create();
                                            alt.setTitle(Html.fromHtml("<p style=\"color:red;\">خطا!</p>"));
                                            alt.setMessage( "خطایی پیش آمده!");
                                            alt.setCancelable(false);
                                            alt.setButton(Dialog.BUTTON_POSITIVE, "تمام", new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    alt.dismiss();

                                                }
                                            });
                                            alt.show();
                                        }
                                    }
                                });


                            }
                        });

                        alt.setButton(Dialog.BUTTON_NEGATIVE, "نه", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alt.dismiss();

                            }
                        });
                        alt.show();

                        break;
                }

                if(drawer.isDrawerOpen(GravityCompat.END)){
                    drawer.closeDrawer(GravityCompat.END);

                }
            }
        });



      /*  for(int i=0;i<10;i++){

            //date,type,tozih,sabtdate

            GeybatListModel movie = new GeybatListModel();
            movieList.add(movie);
            mAdapter.notifyDataSetChanged();



        }*/

        month=(AppCompatSpinner)findViewById(R.id.spinner1);
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#424242"));
                ((TextView) parent.getChildAt(0)).setTextSize(18);
                ((TextView) parent.getChildAt(0)).setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));
                int pp=(position+7)%12;
                if(pp==0){
                    zeroplus="12";
                }else if(pp<10){
                    zeroplus="0"+pp;

                }else{
                    zeroplus=""+pp;

                }
                DoLoad();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                zeroplus ="07";
                DoLoad();

            }
        });


       /* ClassStudentListModel movie = new ClassStudentListModel("رضا محمدی");
        movieList.add(movie);

        movie = new ClassStudentListModel("محمد علیزاده");
        movieList.add(movie);
        movie = new ClassStudentListModel("علی رضایی");
        movieList.add(movie);
        movie = new ClassStudentListModel("آرین حاجی زاده");
        movieList.add(movie);
        movie = new ClassStudentListModel("محمد جواد نژاد");
        movieList.add(movie);
        movie = new ClassStudentListModel("صدرا مختاری");
        movieList.add(movie);
        movie = new ClassStudentListModel("امیر حدودی آذر");
        movieList.add(movie);
        movie = new ClassStudentListModel("مبین حسینی");
        movieList.add(movie);
        movie = new ClassStudentListModel("سینا نعمتی");
        movieList.add(movie);
        movie = new ClassStudentListModel("مهدی بابایی");
        movieList.add(movie);
        movie = new ClassStudentListModel("صادق رضالو");
        movieList.add(movie);
        movie = new ClassStudentListModel("آرین دوستی");
        movieList.add(movie);
        movie = new ClassStudentListModel("پوریا شمس");
        movieList.add(movie);



        mAdapter.notifyDataSetChanged();*/






    }

/*    private void AddItem(String s) {
        if(!movieList.isEmpty()){
            movieList.clear();
        }
        GeybatListModel movie = new GeybatListModel("جمعه 20 "+s,2,"دیر آمدن سرویس","پنج شنبه 19 خرداد");
        movieList.add(movie);

        movie = new GeybatListModel("جمعه 21 "+s,2,"خواب موندن","پنج شنبه 18 "+s);
        movieList.add(movie);


        movie = new GeybatListModel("شنبه  21 "+s,1,null,"پنج شنبه 18 "+s);
        movieList.add(movie);


        movie = new GeybatListModel("شنبه  21 "+s,1,"شرکت در مسابقات","پنج شنبه 18 "+s);
        movieList.add(movie);


        movie = new GeybatListModel("یکشنبه  21 "+s,3,null,"پنج شنبه 18 "+s);
        movieList.add(movie);


        movie = new GeybatListModel("دوشنبه  21 "+s,3,"نیومدم","پنج شنبه 18 "+s);
        movieList.add(movie);


        mAdapter.notifyDataSetChanged();
    }*/
private void DoLoad() {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    String STID = preferences.getString("ID", "");
    MakeDialog(DilogType.LOADING, null);
    SendRequest.GetPostGeybat(STID,zeroplus);
    Log.e("zeroplus : ",zeroplus);

    new SendRequest().setOnGeybatCompleteListner(new SendRequest.OnGeybatCompleteListner() {
        @Override
        public void OnGeybatCompleteed(String response) {
            if (loading.isShowing()) {
                loading.dismiss();
            }
            Log.e("Json : ",response);
            try {
                JSONArray GeybatArray = Parser.Parse(response);
                if(!movieList.isEmpty()){
                    movieList.clear();
                    mAdapter.notifyDataSetChanged();

                }
                for (int i=0; i < GeybatArray.length(); i++) {
                    JSONObject GeybatArrayObj = GeybatArray.getJSONObject(i);
                    String  Tarikh = GeybatArrayObj.getString("Tarikh");
                    String  Takhir = GeybatArrayObj.getString("Takhir");
                    String  Tovzihat = GeybatArrayObj.getString("Tovzihat");
                    Log.e("Json : ",Tarikh);
                    Log.e("Json : ",Takhir);
                    Log.e("Json : ",Tovzihat);

                    GeybatListModel movie = new GeybatListModel(Tarikh,Tovzihat,Takhir);
                    movieList.add(movie);
                    mAdapter.notifyDataSetChanged();


                }

                if(GeybatArray.length()<1){
                    MakeDialog(DilogType.ERROR, "غیبت یا تاخیری در این تاریخ موجودنیست!");
                    if(!movieList.isEmpty()){
                        movieList.clear();
                        mAdapter.notifyDataSetChanged();

                    }
                }
            } catch (JSONException e) {
                MakeDialog(DilogType.ERROR, "خطایی پیش آمده!");
                Log.e("Json : ",e.getMessage());


            }

        }
    });
    new SendRequest().setOnGeybatErrorListner(new SendRequest.OnGeybatErrorListner() {
        @Override
        public void OnGeybatErrored(String response) {
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
            loading = new ProgressDialog(HozorGeyab.this);
            loading.setMessage("درحال بارگذاری...");
            loading.setCancelable(false);
            loading.show();
        } else if (type == DilogType.ERROR) {
            if (loading.isShowing()) {
                loading.dismiss();
            }
            final AlertDialog alt = new AlertDialog.Builder(HozorGeyab.this).create();
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

}

