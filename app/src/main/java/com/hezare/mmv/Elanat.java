package com.hezare.mmv;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hezare.mmv.Adapter.Drawer_List_Adapter;
import com.hezare.mmv.Adapter.ElanatListAdapter;
import com.hezare.mmv.Lib.FadingTextView;
import com.hezare.mmv.Models.ElanatListModel;
import com.hezare.mmv.WebSide.Parser;
import com.hezare.mmv.WebSide.SendRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Elanat extends AppCompatActivity{


    private List<ElanatListModel> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ElanatListAdapter mAdapter;
    public enum DilogType {
        LOADING,
        ERROR
    }
    ProgressDialog loading;
    DrawerLayout drawer;

    ListView ListDrawer;
    public static int [] icons={R.drawable.ic_menu_gallery,R.drawable.ic_menu_gallery,R.drawable.ic_menu_manage,R.drawable.ic_menu_send,R.drawable.ic_menu_share,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_edit_black_24dp,R.drawable.ic_info_black_24dp,R.drawable.ic_exit_to_app_black_24dp};
    public static String [] items={"نمره کلاسی","نمره امتحانات","بخش مالی","فراخوان ها(اعلانات)","مشاهده تاخیر-غیبت(روزانه)","مشاهده تشویق یا تنبیه","تکالیف روزانه","تماس با ما","ویرایش رمز عبور","درباره ما","خروج از حساب کاربری"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elanatmain);
        Utli.StrictMode();
        Utli.changeFont(getWindow().getDecorView());
        DoLoad();












        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new ElanatListAdapter(movieList);
         RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);




        mAdapter.setOnClickListner(new ElanatListAdapter.OnClickListner() {
            @Override
            public void OnClicked(View view, int position, List<ElanatListModel> moviesList) {
                Intent details=new Intent(Elanat.this,ElanatDetails.class);
                details.putExtra("title",moviesList.get(position).getTitle());
                details.putExtra("matn",moviesList.get(position).getMatn());
                details.putExtra("date",moviesList.get(position).getDate());
                startActivity(details);
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
                        startActivity(new Intent(Elanat.this,ClassGradeMain.class));
                        finish();
                        break;

                    case 1:
                        startActivity(new Intent(Elanat.this,Karname.class));
                        finish();

                        break;
                    case 7:
                        startActivity(new Intent(Elanat.this,Chat.class));
                        finish();
                        break;
                    case 2:
                        startActivity(new Intent(Elanat.this,Mali.class));
                        finish();

                        break;
                    case 3:
                        startActivity(new Intent(Elanat.this,Elanat.class));
                        finish();
                        break;
                    case 4:
                        startActivity(new Intent(Elanat.this,HozorGeyab.class));
                        finish();
                        break;
                    case 5:
                        startActivity(new Intent(Elanat.this,Tashvig.class));
                        finish();
                        break;

                    case 6:
                        startActivity(new Intent(Elanat.this,Takalif.class));
                        finish();
                        break;
                    case 8:
                        final Dialog dl = new Dialog(Elanat.this);
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
                                                Toast.makeText(Elanat.this, Text, Toast.LENGTH_SHORT).show();
                                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Elanat.this);
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.clear();
                                                editor.commit();
                                                App.getDB().LogOut();
                                                startActivity(new Intent(Elanat.this,Login.class));
                                                finish();

                                            } else {
                                                final AlertDialog alt = new AlertDialog.Builder(Elanat.this).create();
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
                                            final AlertDialog alt = new AlertDialog.Builder(Elanat.this).create();
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
                                            final AlertDialog alt = new AlertDialog.Builder(Elanat.this).create();
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
                        startActivity(new Intent(Elanat.this,About.class));
                        finish();
                        break;
                    case 10:
                        final AlertDialog alt = new AlertDialog.Builder(Elanat.this).create();
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
                                                Toast.makeText(Elanat.this, Text, Toast.LENGTH_SHORT).show();
                                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Elanat.this);
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.clear();
                                                editor.commit();
                                                App.getDB().LogOut();
                                                ActivityCompat.finishAffinity(Elanat.this);

                                            } else {
                                                final AlertDialog alt = new AlertDialog.Builder(Elanat.this).create();
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
                                            final AlertDialog alt = new AlertDialog.Builder(Elanat.this).create();
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
                                            final AlertDialog alt = new AlertDialog.Builder(Elanat.this).create();
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






    }
    private void DoLoad() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String STID = preferences.getString("ID", "");
        MakeDialog(DilogType.LOADING, null);
        SendRequest.GetPostElanat(STID);
        new SendRequest().setOnElanatCompleteListner(new SendRequest.OnElanatCompleteListner() {
            @Override
            public void OnElanatCompleteed(String response) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                Log.e("Json : ",response);
                try {
                    JSONArray ElanatArray = Parser.Parse(response);

                    for (int i=0; i < ElanatArray.length(); i++) {
                        JSONObject ElanatArrayObj = ElanatArray.getJSONObject(i);
                        String  Movzoo = ElanatArrayObj.getString("Movzoo");
                        String  Matn = ElanatArrayObj.getString("Matn");
                        String  TarikheFarakhan = ElanatArrayObj.getString("TarikheFarakhan");
                        ElanatListModel movie = new ElanatListModel(Movzoo,Matn,TarikheFarakhan,"");
                        movieList.add(movie);
                        mAdapter.notifyDataSetChanged();


                    }


                } catch (JSONException e) {
                    MakeDialog(DilogType.ERROR, "خطایی پیش آمده!");
                    Log.e("Json : ",e.getMessage());


                }
            }
        });
        new SendRequest().setOnElanatErrorListner(new SendRequest.OnElanatErrorListner() {
            @Override
            public void OnElanatErrored(String response) {
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
            loading = new ProgressDialog(Elanat.this);
            loading.setMessage("درحال بارگذاری...");
            loading.setCancelable(false);
            loading.show();
        } else if (type == DilogType.ERROR) {
            if (loading.isShowing()) {
                loading.dismiss();
            }
            final AlertDialog alt = new AlertDialog.Builder(Elanat.this).create();
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

