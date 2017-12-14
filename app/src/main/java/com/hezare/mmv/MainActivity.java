package com.hezare.mmv;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hezare.mmv.Adapter.Drawer_List_Adapter;
import com.hezare.mmv.Adapter.MainNomrehListAdapter;
import com.hezare.mmv.Lib.MovingTextView;
import com.hezare.mmv.Models.MainNomreListModel;
import com.hezare.mmv.Utils.AppUpdate;
import com.hezare.mmv.WebSide.Parser;
import com.hezare.mmv.WebSide.SendRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.ronash.pushe.Pushe;

public class MainActivity extends AppCompatActivity {
   /* BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;
    Runnable removeTxtPoint;*/

    public static int[] icons = {R.drawable.ic_menu_gallery, R.drawable.ic_menu_gallery, R.drawable.ic_menu_manage, R.drawable.ic_menu_send, R.drawable.ic_menu_share, R.drawable.ic_menu_camera, R.drawable.ic_menu_camera, R.drawable.ic_menu_camera, R.drawable.ic_menu_camera, R.drawable.ic_edit_black_24dp, R.drawable.ic_info_black_24dp, R.drawable.ic_exit_to_app_black_24dp};
    public static String[] items = {"نمره کلاسی", "نمره امتحانات", "بخش مالی", "فراخوان ها(اعلانات)", "مشاهده تاخیر-غیبت(روزانه)", "مشاهده تشویق یا تنبیه", "تکالیف روزانه", "تماس با ما", "ویرایش رمز عبور", "درباره ما", "خروج از حساب کاربری"};
    public Boolean YekiDarMian = true;
   /* String farakhan []={
            "این یک فراخوان تست است",
            "سلام این متن 2 هست",
            "اینم متن سوم هستش"
    };*/
    int cnt = 0;
    int crt=0;
    String matn2="";
    String matn="";
    Spinner st;
    DrawerLayout drawer;
    HashMap<Integer, String> meMap=new HashMap<Integer, String>();
    ListView ListDrawer;
    ProgressDialog loading;
    int check = 0;
    private Handler handler = new Handler();
    private List<MainNomreListModel> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MainNomrehListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Pushe.initialize(this,false);
        Utli.StrictMode();
        Utli.changeFont(getWindow().getDecorView());
        DoLoad();


     /*   GraphView graph = (GraphView) findViewById(R.id.graph);

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            for (int i = 0; i < 10; i++) {
                DataPoint point = new DataPoint(i+2, i+1);
                series.appendData(point, true, 10);
            }
            graph.addSeries(series);*/
      /*  chart = (BarChart) findViewById(R.id.chart1);

        BARENTRY = new ArrayList<>();
        chart.setDescription("میانگین نمرات دانش آموز بر اساس ماه");
        chart.setDescriptionColor(Color.BLACK);
        chart.setDescriptionTextSize(10);
        chart.setDescriptionTypeface(Typeface.DEFAULT_BOLD);
        chart.getXAxis().setLabelsToSkip(0);
        chart.setNoDataText("----");
        BarEntryLabels = new ArrayList<String>();
*/



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new MainNomrehListAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickListner(new MainNomrehListAdapter.OnClickListner() {
            @Override
            public void OnClicked( int position, List<MainNomreListModel> moviesList) {
                startActivity(new Intent(MainActivity.this,ClassGradeMain.class));

            }
        });

        FloatingActionButton barname=(FloatingActionButton)findViewById(R.id.fab);
        barname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindow(v);
            }
        });

       /* MainNomreListModel movie = new MainNomreListModel("ریاضی","20",false,this);
        movieList.add(movie);

        movie = new MainNomreListModel("دینی","18",false,this);
        movieList.add(movie);
        movie = new MainNomreListModel("عربی","13",false,this);
        movieList.add(movie);
        movie = new MainNomreListModel("جغرافیا","11",true,this);
        movieList.add(movie);
        movie = new MainNomreListModel("فیزیک","15",false,this);
        movieList.add(movie);
        movie = new MainNomreListModel("ادبیات","14",false,this);
        movieList.add(movie);
        mAdapter.notifyDataSetChanged();
*/

          drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        findViewById(R.id.opendrawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });

        st = (Spinner) findViewById(R.id.spinner);
        st.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#ffffff"));
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.LEFT);
              //  ((TextView) parent.getChildAt(0)).setTextSize(17);
                ((TextView) parent.getChildAt(0)).setTypeface(Typeface.createFromAsset(App.getContext().getAssets(), "font.ttf"));


                if(++check > 1) {
                    Cursor rs = null;
                    rs = App.getDB().getStudentData(position+1);
                    rs.moveToFirst();
                    int idid = rs.getInt(rs.getColumnIndex("id"));
                    String name = rs.getString(rs.getColumnIndex("name"));


                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("ID",""+idid);
                    editor.putInt("position",position);
                    editor.apply();
                    App.getDB().ClearDars();
                    Intent restart=getIntent();
                    Toast.makeText(MainActivity.this, "به دانش آموز "+name+" تغیر داده شد", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(restart);



                    Log.e("name",name);


                    if (!rs.isClosed())  {
                        rs.close();
                    }
                }


            /*    Cursor rs = null;
                    rs = App.getDB().getStudentData(0);
                    rs.moveToFirst();
                int idname = rs.getInt(rs.getColumnIndex("id"));
                String name = rs.getString(rs.getColumnIndex("name"));

                  Log.e("App",idname+"/"+name);

                if (!rs.isClosed())  {
                    rs.close();
                }*/


                /*Cursor rs = null;
                    rs = App.getDB().getStudentData(position);
                    rs.moveToFirst();
                    int stid = rs.getInt(rs.getColumnIndex("id"));
                    String name = rs.getString(rs.getColumnIndex("name"));

                Toast.makeText(MainActivity.this, stid, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();

                if (!rs.isClosed())  {
                    rs.close();
                }
*/


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


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
                        startActivity(new Intent(MainActivity.this,ClassGradeMain.class));

                        break;

                    case 1:
                        startActivity(new Intent(MainActivity.this,Karname.class));

                        break;
                    case 7:
                        startActivity(new Intent(MainActivity.this,Chat.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this,Mali.class));

                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this,Elanat.class));

                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this,HozorGeyab.class));

                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this,Tashvig.class));

                        break;

                    case 6:
                        startActivity(new Intent(MainActivity.this,Takalif.class));

                        break;
                    case 8:
                        ShowChange();
                        break;
                    case 9:
                        startActivity(new Intent(MainActivity.this,About.class));

                        break;
                    case 10:
                        final AlertDialog alt = new AlertDialog.Builder(MainActivity.this).create();
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
                                                Toast.makeText(MainActivity.this, Text, Toast.LENGTH_SHORT).show();
                                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.clear();
                                                editor.commit();
                                                App.getDB().LogOut();
                                                finish();

                                            } else {
                                                final AlertDialog alt = new AlertDialog.Builder(MainActivity.this).create();
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
                                            final AlertDialog alt = new AlertDialog.Builder(MainActivity.this).create();
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
                                            final AlertDialog alt = new AlertDialog.Builder(MainActivity.this).create();
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


       /*

        findViewById(R.id.takalifbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Takalif.class));
            }
        });



*/

        findViewById(R.id.takalifbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Takalif.class));
            }
        });
        findViewById(R.id.hozorgyabbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,HozorGeyab.class));
            }
        });
        findViewById(R.id.tashvigbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Tashvig.class));
            }
        });
        findViewById(R.id.elanatbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Elanat.class));
            }
        });
        findViewById(R.id.nomaratbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ClassGradeMain.class));
            }
        });
        findViewById(R.id.contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Chat.class));
            }
        });
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ClassGradeMain.class));
            }
        });
  /*   final   CardView b= (CardView) findViewById(R.id.tashvigbutton);
        ViewTreeObserver vto = b.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                b.getViewTreeObserver().removeOnPreDrawListener(this);
                Log.e("", "Height: " + b.getMeasuredHeight()+ " Width: " + b.getMeasuredWidth());
                return true;
            }
        });
        findViewById(R.id.rate).setLayoutParams (new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
 */


        final LinearLayout layout = (LinearLayout)findViewById(R.id.ts);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                int height = layout.getMeasuredHeight();
                LinearLayout rate=(LinearLayout)findViewById(R.id.rate);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, height);
                layoutParams.setMargins(15,0,0,0);
              //  rate.setGravity(Gravity.CENTER);
                rate.setLayoutParams(layoutParams);

            }
        });

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

    private void DoLoad() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String STID = preferences.getString("ID", "");
            MakeDialog(DilogType.LOADING, null);
            SendRequest.GetPostHome(STID);
            new SendRequest().setOnHomeCompleteListner(new SendRequest.OnHomeCompleteListner() {
                @Override
                public void OnHomeCompleteed(String response) {
                    if (loading.isShowing()) {
                        loading.dismiss();
                    }


                    Log.e("Json : ",response);
                    try {
                        String result=response.replaceAll("\\\\","").replaceAll("^\"|\"$", "");
                        JSONObject jObject = new JSONObject(result);
                        JSONObject Root = new JSONObject(jObject.get("Root").toString());
                        String NameAndPaye = Root.getString("NameAndPaye");
                        String STName = NameAndPaye.substring(0, NameAndPaye.lastIndexOf(":"));
                       // String STCLASS = NameAndPaye.substring(NameAndPaye.lastIndexOf(':') + 1);
                      //  ((TextView)findViewById(R.id.name)).setText(STName);


                        if(App.getDB().numberOfRowsStudent()<2){
                        //  st.setOnClickListener(null);
                        st.setEnabled(false);
                        }



                        List<String> list = new ArrayList<String>();

                        Cursor rs = null;
                        ArrayList notes=App.getDB().getAllStudents();
                        for (int i = 0; i < notes.size(); i++) {
                            rs = App.getDB().getStudentData(Integer.parseInt(notes.get(i).toString()));
                            rs.moveToFirst();
                            int id = rs.getInt(rs.getColumnIndex("id"));
                            String name = rs.getString(rs.getColumnIndex("name"));

                            list.add(name);


                        }
                        if (!rs.isClosed())  {
                            rs.close();
                        }




                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this,
                                android.R.layout.simple_spinner_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        st.setAdapter(dataAdapter);

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        int position = preferences.getInt("position", 0);

                        st.setSelection(position, false);

                      //  ((TextView)findViewById(R.id.classname)).setText(STCLASS);
                        List<String> texts = new ArrayList<String>();
                        List<String> farakhan = new ArrayList<String>();
                        JSONArray Nomarat = Root.getJSONArray("Nomarat");
                        JSONArray Farakhanha = Root.getJSONArray("Farakhanha");
                        JSONArray NafarateBartar = Root.getJSONArray("NafarateBartar");
                        JSONArray Barname = Root.getJSONArray("Barname");
                        for (int i=0; i < Nomarat.length(); i++) {
                            JSONObject NomaratObj = Nomarat.getJSONObject(i);
                            String   ID = NomaratObj.getString("ID");
                            String   NomreDars = NomaratObj.getString("NomreDars");
                            String  NaameDars = NomaratObj.getString("NaameDars");
                            if(App.getDB().numberOfRowsDarsAva(Integer.parseInt(ID))<1){
                                App.getDB().insertDars(Integer.parseInt(ID),NaameDars,NomreDars,0);
                            }



                        }
                        for (int i=0; i < Farakhanha.length(); i++) {
                            String FarakhanhaStr = Farakhanha.get(i).toString();
                            farakhan.add(FarakhanhaStr);
                        }



                        for (int i=0; i < NafarateBartar.length(); i++) {
                            JSONObject NafarateBartarObj = NafarateBartar.getJSONObject(i);
                            String  Rate = NafarateBartarObj.getString("Rate");
                            String  FullName = NafarateBartarObj.getString("FullName");
                            if(Rate.contains("1")){
                                ((TextView)findViewById(R.id.aval)).setText("تعیین نشده");

                            }else if(Rate.contains("2")){
                                ((TextView)findViewById(R.id.dovom)).setText("تعیین نشده");

                            }else  if(Rate.contains("3")){
                                ((TextView)findViewById(R.id.sevom)).setText("تعیین نشده");

                            }
                        }
                        for (int i=0; i < Barname.length(); i++) {
                            JSONObject BarnameObj = Barname.getJSONObject(i);
                            String   RuzeHafte = BarnameObj.getString("RuzeHafte");
                            String   Zang = BarnameObj.getString("Zang");
                            String  NameDars = BarnameObj.getString("NameDars");
                           // int id= com.hezare.mmv.Utils.Utils.WichTabel3(Integer.parseInt(RuzeHafte),Integer.parseInt(Zang));
                           // TextView tv=(TextView)findViewById(id);



                            meMap.put(i,Zang+"*"+RuzeHafte+":"+NameDars);

                        }

                       /* String[] nomarat = texts.toArray(new String[texts.size()]);
                        FadingTextView FTV = (FadingTextView) findViewById(R.id.fadingTextView);
                        FTV.setTexts(nomarat);
                        FTV.setTimeout(3, FadingTextView.SECONDS);
                        Log.e("Json : ",Root.toString());
*/

                       if(Farakhanha.length()>0){
                           String[] farakhanha = farakhan.toArray(new String[farakhan.size()]);
                           MovingTextView FarakhanTV = (MovingTextView) findViewById(R.id.farakhan);
                           FarakhanTV.setTexts(farakhanha);
                           FarakhanTV.setTimeout(7, MovingTextView.SECONDS);

                       }



                       ShowNomarat();

                       /* MainNomreListModel movie = new MainNomreListModel(NaameDars,NomreDars,false,MainActivity.this);
                        movieList.add(movie);
                        mAdapter.notifyDataSetChanged();*/

                      /*  if(NafarateBartar.length()<=0){
                           *//* ((TextView)findViewById(R.id.onest)).setText("تایین نشده");
                            ((TextView)findViewById(R.id.twost)).setText("تایین نشده");
                            ((TextView)findViewById(R.id.threest)).setText("تایین نشده");
*//*
                        }*/
                     /*   Bardataset = new BarDataSet(BARENTRY, "سطح نمره ها");
                        BARDATA = new BarData(BarEntryLabels, Bardataset);
                        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                        chart.setData(BARDATA);
                        chart.animateY(3000);*/


                    } catch (JSONException e) {
                        MakeDialog(DilogType.ERROR, "تاکنون داده ای برای این دانش آموز ثبت نشده!");
                        Log.e("Json : ",e.getMessage());


                    }
                }
            });
            new SendRequest().setOnHomeErrorListner(new SendRequest.OnHomeErrorListner() {
                @Override
                public void OnHomeErrored(String response) {
                    if (response.trim().contains("connectionError")) {
                        MakeDialog(DilogType.ERROR, "خطا در اتصال به سرور!");

                    } else {
                        MakeDialog(DilogType.ERROR, "خطایی پیش آمده لطفا کمی بعد مجددا تلاش نمایید");

                    }
                }
            });


    }

    private void ShowNomarat() {
        if(App.getDB().numberOfRowsDars()>0){
            movieList.clear();
            Cursor rs = null;
            ArrayList notes=App.getDB().getAllDars();
            for (int i = 0; i < notes.size(); i++) {
                rs = App.getDB().getDarsData(Integer.parseInt(notes.get(i).toString()));
                rs.moveToFirst();
                int id = rs.getInt(rs.getColumnIndex("id"));
                String title = rs.getString(rs.getColumnIndex("title"));
                String nomre = rs.getString(rs.getColumnIndex("nomre"));
                int seen = rs.getInt(rs.getColumnIndex("seen"));
                if(seen==1){
                    MainNomreListModel movie = new MainNomreListModel(title,nomre,0,MainActivity.this);
                    movieList.add(movie);
                    mAdapter.notifyDataSetChanged();
                }else{
                    MainNomreListModel movie = new MainNomreListModel(title,nomre,1,MainActivity.this);
                    movieList.add(movie);
                    mAdapter.notifyDataSetChanged();
                }




            }
            if (!rs.isClosed())  {
                rs.close();
            }
        }else{
            movieList.clear();
            mAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onBackPressed() {
       if(drawer.isDrawerOpen(GravityCompat.END)){
           drawer.closeDrawer(GravityCompat.END);

       }else{
           finish();
           if(App.getDB().numberOfRowsDars()>0){
               Cursor rs = null;
               ArrayList notes=App.getDB().getAllDars();
               for (int i = 0; i < notes.size(); i++) {
                   rs = App.getDB().getDarsData(Integer.parseInt(notes.get(i).toString()));
                   rs.moveToFirst();
                   int id = rs.getInt(rs.getColumnIndex("id"));
                   App.getDB().updateDars(id,1);





               }
               if (!rs.isClosed())  {
                   rs.close();
               }
           }
       }
    }

    private void MakeDialog(DilogType type, String Text) {
        if (type == DilogType.LOADING) {
            loading = new ProgressDialog(MainActivity.this);
            loading.setMessage("درحال بارگذاری...");
            loading.setCancelable(false);
            loading.show();
        } else if (type == DilogType.ERROR) {
            if (loading.isShowing()) {
                loading.dismiss();
            }
            final AlertDialog alt = new AlertDialog.Builder(MainActivity.this).create();
            alt.setTitle(Html.fromHtml("<p style=\"color:red;\">خطا!</p>"));
            alt.setMessage(Text);
            alt.setCancelable(false);
            alt.setButton(Dialog.BUTTON_POSITIVE, "تمام", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alt.dismiss();
                    //    finish();

                }
            });
            alt.show();
        }
    }


/*    private void WriteText(final String[] s, final int nm){
        {

            Log.e("NM",""+nm);
            matn =s[nm];
            matn2=matn;
            for (int i=0;i<=matn.length()*2;i++){
                matn2 +=  "\u200E";

            }
               removeTxtPoint = new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub

                    if(cnt <= matn2.length()){
                        tv3.setText(matn2.substring(0, cnt));
                        cnt++;
                    }else{
                       //
                        if(crt<=s.length-2){
                            crt++;
                            cnt=0;
                            tv3.setText("");
                            WriteText(s,crt);
                            handler.removeCallbacks(removeTxtPoint);
                        }else{
                            crt=0;
                            cnt=0;
                            tv3.setText("");
                            WriteText(s,crt);
                            handler.removeCallbacks(removeTxtPoint);
                        }




                    }
                    handler.postDelayed(this, 100);
                }
            };
        }

        removeTxtPoint.run();

    }*/

    public void ChangeState(Boolean state){
        YekiDarMian=state;
    }

    private void initiatePopupWindow(View v) {
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.barname,
                    (ViewGroup) findViewById(R.id.popup_element));
            // create a 300px width and 470px height PopupWindow
            PopupWindow    pw = new PopupWindow(layout, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            pw.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, android.R.color.transparent)));
            pw.setOutsideTouchable(true);

            // display the popup in the center
         //   pw.showAtLocation(v, Gravity.CENTER, (int)v.getX(), ((int)v.getY()+v.getMeasuredHeight()));

        //    Log.e("Val",meMap.size()+"");


            for (Map.Entry<Integer,String> entry : meMap.entrySet()) {


                String val=entry.getValue();
                String Zang = val.substring(0,val.lastIndexOf("*"));
                String ClassName = val.substring(val.lastIndexOf(':') + 1);
                String RuzHafteTEMP = val.substring(val.lastIndexOf("*")+1);
                String RuzHafte = RuzHafteTEMP.replace(ClassName,"").replace(":","");

                Log.e("Zang",Zang);
                Log.e("RuzHafte",RuzHafte);
                Log.e("ClassName",ClassName);
                   int id= com.hezare.mmv.Utils.Utils.WichTabel3(Integer.parseInt(RuzHafte),Integer.parseInt(Zang));




                    TextView tv=(TextView)layout.findViewById(id);
                tv.setSelected(true);
                tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                tv.setSingleLine(true);
                  tv.setText(ClassName);
                     tv.setVisibility(View.VISIBLE);
            }



            pw.showAtLocation(v, Gravity.RIGHT|Gravity.BOTTOM, 20, 20);
            View contentView = (View) pw.getContentView().getParent();
            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) contentView.getLayoutParams();
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.5f;
            wm.updateViewLayout(contentView, p);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ShowChange() {
        final Dialog dl = new Dialog(MainActivity.this);
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

                                Toast.makeText(MainActivity.this, Text, Toast.LENGTH_SHORT).show();
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.commit();
                                App.getDB().LogOut();
                                startActivity(new Intent(MainActivity.this,Login.class));
                                finish();


                            } else {
                                final AlertDialog alt = new AlertDialog.Builder(MainActivity.this).create();
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
                            final AlertDialog alt = new AlertDialog.Builder(MainActivity.this).create();
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
                            final AlertDialog alt = new AlertDialog.Builder(MainActivity.this).create();
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

    }

    public enum DilogType {
        LOADING,
        ERROR
    }
}
