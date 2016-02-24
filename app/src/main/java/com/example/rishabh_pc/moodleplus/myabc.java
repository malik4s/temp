package com.example.rishabh_pc.moodleplus;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Rishab-pc on 23-Feb-16.
 */
public class myabc implements View.OnClickListener {
    public String params;
    public FragmentManager fm;
    private View va;


    public myabc(String params, FragmentManager fm){

        this.params = params;
        this.fm = fm;

    }




    public void onClick(View v) {
        Log.d("click", params);

va=v;

//  String qwe=      params.replace("</p><p>","     ");


  //      qwe=qwe.replace("<br>","  ");
    //    qwe=qwe.replace("/","  ");
      //  qwe=qwe.replace("nbsp","  ");
        //qwe=qwe.replace("&","  ");

//        qwe=qwe.replace("<p>","  ");

  //      String qe[]=qwe.split("  ");
        String aaa =android.text.Html.fromHtml(params).toString();
       // String abcde=qe[0];
        //for(int i=1;i<qe.length;i++)
        //{abcde=abcde+System.lineSeparator()+qe[i];

        //}

Fragment abc= new  assndescription();


        fm.beginTransaction()
                .replace(R.id.blanklayout, new assndescription().newInstance(aaa)).addToBackStack(null)
                        .commit();

    }

}