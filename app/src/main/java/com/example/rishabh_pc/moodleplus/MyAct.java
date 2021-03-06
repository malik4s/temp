package com.example.rishabh_pc.moodleplus;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Fragment;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MyAct extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static Requeue requeue;

    private overview over;
    private allcourses allco;
    private ArrayList<String[]> courses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.requeue = new Requeue(this);
        requeue.start();
        courses = new ArrayList<>();


        Bundle bundle = getIntent().getExtras();
        String abc =bundle.size()+"";
        Log.d("length", abc );

        String fn = bundle.getString("first_name");
        String ln = bundle.getString("last_name");
        String welcome = "Welcome " + fn + " " + ln;
       Log.d("yeh sahi hai", welcome);
       over = (new overview()).newInstance(fn, ln);


       getFragmentManager().beginTransaction()
               .replace(R.id.blanklayout, over).addToBackStack(null)
               .commit();
//        try {
//        Log.d("Size", (response.getJSONObject("user")).length() + "");} catch (Exception e) {Log.d("Exception", e.getMessage().toString());}



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       Menu menu = navigationView.getMenu();
       SubMenu subMenu = menu.addSubMenu("Your courses");
       int i = 0;
       String tag = "c" + i;
       Log.d("array", bundle.getStringArray(tag).toString());

       while (bundle.getStringArray(tag)!=null) {
           String[] ret = getIntent().getStringArrayExtra(tag);
           courses.add(ret);
           subMenu.add(0, i, (i*100), ret[0]+": "+ ret[1]);
            i = i+1;
           tag = "c" + i;
           Log.d("machaya phir", tag);

       }
    }

//    public void onClick(SubMenu sub) {
//        String name = sub.
//    }



    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    public void Assignment(String code){
        final FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();


        final assn allc = new assn();
        String url = "http://192.168.43.38:8000/courses/course.json/"+code+"/assignments";
        Log.d("frag", code);
        ParaJson jobjre = new ParaJson(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String[][] star = converttoassignments(response);
                Fragment ret = allc.newInstance(star);
                fm.beginTransaction()
                        .replace(R.id.blanklayout, ret).addToBackStack(null)
                        .commit();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "yeh hua");
            }
        });

        requeue.add(jobjre);



    }

    public void Coursegrades(String code){
        final FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();


        final coursegrades allc = new coursegrades();
        String url = "http://192.168.43.38:8000/courses/course.json/"+code+"/grades";
        Log.d("frag", code);
        ParaJson jobjre = new ParaJson(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String[][] star = converttocoursegrades(response);
                Fragment ret = allc.newInstance(star);
                fm.beginTransaction()
                        .replace(R.id.blanklayout, ret).addToBackStack(null)
                        .commit();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "yeh hua");
            }
        });

        requeue.add(jobjre);



    }


    public void gra(View v){
        FragmentManager fm = getFragmentManager();

//if you added fragment via layout xml

        TextView te = (TextView)findViewById(R.id.coursenamee);
        String abc= te.getText().toString();
        String qq[]=abc.split(":");

        Coursegrades(qq[0]);

    }

    public void ass(View v){

        FragmentManager fm = getFragmentManager();

//if you added fragment via layout xml

        TextView te = (TextView)findViewById(R.id.coursenamee);
       String abc= te.getText().toString();
        String qq[]=abc.split(":");

        Assignment(qq[0]);


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        final FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (id == R.id.nav_camera) {
            final allcourses allc = new allcourses();
               String url = "http://192.168.43.38:8000/courses/list.json";
               Log.d("frag", "yeh bhi hua");
               ParaJson jobjre = new ParaJson(url, new Response.Listener<JSONObject>() {
                   @Override
                   public void onResponse(JSONObject response) {

                       String[][] star = convert(response);
                       Fragment ret = allc.newInstance(star);
                       fm.beginTransaction()
                               .replace(R.id.blanklayout, ret).addToBackStack(null)
                               .commit();

                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Log.d("error", "yeh hua");
                   }
               });

               requeue.add(jobjre);


        } else if (id == R.id.grades) {

            final grades allg = new grades();
            String url = "http://192.168.43.38:8000/default/grades.json";
            Log.d("frag", "yeh bhi hua");
            ParaJson jobjre = new ParaJson(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                  String[][] star=convertforgrades(response);
         Log.d("abc",star.length+"");
                    Fragment ret = allg.newInstance(star);



                    fm.beginTransaction()
                            .replace(R.id.blanklayout, ret).addToBackStack(null)
                            .commit();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error", "yeh hua");
                }
            });

            requeue.add(jobjre);


        } else if (id == R.id.overview) {

            fm.beginTransaction()
                    .replace(R.id.blanklayout, over)
                    .commit();
                }else if (id == R.id.noti) {

            final noti allg = new noti();
            String url = "http://192.168.43.38:8000/default/notifications.json";
            Log.d("frag", "yeh bhi hua");
            ParaJson jobjre = new ParaJson(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String[][] star=convertfornoti(response);
                    Log.d("abc",star.length+"");
                    Fragment ret = allg.newInstance(star);



                    fm.beginTransaction()
                            .replace(R.id.blanklayout, ret).addToBackStack(null)
                            .commit();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error", "yeh hua");
                }
            });

            requeue.add(jobjre);


        }
            else {
            int i=0;
           while (i<courses.size()) {
               if (id == i ){
                   fm.beginTransaction()
                           .replace(R.id.blanklayout, new coursedata().newInstance(courses.get(i)))
                           .commit();
                   Log.d("tu sahi hai", i+"");
               }
               i = i+1;

           }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String[][] convertfornoti(JSONObject json) {
        try {
            JSONArray arr = json.getJSONArray("notifications");
            String[][] ret = new String[arr.length()][1];
if(arr.length()==0){
    String[][] q=new String[1][1];
    q[0][0]="No new Notification";
return q;
}


            for (int i = 0; i < arr.length(); i++) {
                String xx = arr.getJSONObject(i).getString("description");
              ret[i][0]= arr.getJSONObject(i).getString("created_at")+":  "+ android.text.Html.fromHtml(xx).toString();
 }
            return ret;
        } catch (Exception e) {
            Log.d("Excep", e.getMessage().toString());
            return null;
        }
    }


    private String[][] convert(JSONObject json) {
        try {
            JSONArray arr = json.getJSONArray("courses");
            String[][] ret = new String[arr.length()][4];


            for (int i = 0; i < arr.length(); i++) {
                ret[i][0] = arr.getJSONObject(i).getString("code");

                ret[i][1] = arr.getJSONObject(i).getString("name");
                ret[i][2] = arr.getJSONObject(i).getString("description");
                ret[i][3] = arr.getJSONObject(i).getString("id");
            }
            return ret;
        } catch (Exception e) {
            Log.d("Excep", e.getMessage().toString());
            return null;
        }
    }


    private String[][] convertforgrades(JSONObject json) {
        try {
            JSONArray arr = json.getJSONArray("grades");
            JSONArray arr1 = json.getJSONArray("courses");

            String[][] ret1 = new String[arr.length()+1][6];
            ret1[0][0] = "Weightage";
            ret1[0][1] = "  User_id";
            ret1[0][2] = "   Name"+"       ";
            ret1[0][3] ="  Max Marks";
            ret1[0][5] = "Score";
            ret1[0][4]= "  Code";


            if(arr.length()==0){
                String[][] q=new String[1][1];
                q[0][0]="No graded alotted";
                return q;
            }

            for (int i = 1; i < arr.length()+1; i++) {
                ret1[i][0] = arr.getJSONObject(i-1).getString("weightage")+"      ";
                ret1[i][1] ="  "+ arr.getJSONObject(i-1).getString("user_id")+"      ";
                ret1[i][2] = arr.getJSONObject(i-1).getString("name");
                ret1[i][3] = "  "+arr.getJSONObject(i-1).getString("out_of");
                ret1[i][5] = arr.getJSONObject(i-1).getString("score");
                ret1[i][4]= "  "+arr1.getJSONObject(i-1).getString("code");
                Log.d("abgghbbb",ret1[i].length+"");
            }

            Log.d("abbbb",ret1.length + "");



            return ret1;
        } catch (Exception e) {
            Log.d("Excepaasasasa", e.getMessage().toString());
            return null;

        }


    }


    private String[][] converttocoursegrades(JSONObject json) {
        try {
            JSONArray arr = json.getJSONArray("grades");
            String[][] ret = new String[arr.length()+1][4];


            ret[0][0] = "Weightage";
            ret[0][1] = "Name";
            ret[0][2] = "Max Marks";
            ret[0][3] = "Score";
            if(arr.length()==0){
                String[][] q=new String[1][1];
                q[0][0]="No graded alotted";
                return q;
            }

            for (int i = 1; i < arr.length()+1; i++) {
                ret[i][0] = arr.getJSONObject(i-1).getString("weightage");
                ret[i][1] = arr.getJSONObject(i-1).getString("name");
                ret[i][2] = arr.getJSONObject(i-1).getString("out_of");
                ret[i][3] = arr.getJSONObject(i-1).getString("score");

            }
            return ret;
        } catch (Exception e) {
            Log.d("Excep", e.getMessage().toString());
            return null;
        }
    }

    private String[][] converttoassignments(JSONObject json) {
        try {
            JSONArray arr = json.getJSONArray("assignments");
            String[][] ret = new String[arr.length()+1][4];


            ret[0][0] = "description";
            ret[0][1] = "name";
            ret[0][2] = "created_at";
            ret[0][3] = "deadline";


            for (int i = 1; i < arr.length()+1; i++) {
                ret[i][0] = arr.getJSONObject(i-1).getString("description");
                Log.d("awawawa",arr.getJSONObject(i-1).getString("description"));
                ret[i][1] = arr.getJSONObject(i-1).getString("name");
                ret[i][2] = arr.getJSONObject(i-1).getString("created_at");
                ret[i][3] = arr.getJSONObject(i-1).getString("deadline");
            }
            return ret;
        } catch (Exception e) {
            Log.d("Excep", e.getMessage().toString());
            return null;
        }
    }
}
