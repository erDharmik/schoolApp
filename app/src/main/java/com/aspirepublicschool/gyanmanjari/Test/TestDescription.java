package com.aspirepublicschool.gyanmanjari.Test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.aspirepublicschool.gyanmanjari.Test.JEE.Jee;
import com.aspirepublicschool.gyanmanjari.Test.NEET.Neet;
import com.aspirepublicschool.gyanmanjari.R;

public class TestDescription extends AppCompatActivity {
    WebView txtdesc;
    Button btnstart;
    String tst_id,sub,desc,pos,neg,t_type;
    int hours,min;
    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_description);
        allocatememory();

        tst_id=getIntent().getExtras().getString("tst_id");
        sub=getIntent().getExtras().getString("sub");
        hours=Integer.parseInt(getIntent().getExtras().getString("hours"));
        min=Integer.parseInt(getIntent().getExtras().getString("min"));
        desc=getIntent().getExtras().getString("des");
        pos=getIntent().getExtras().getString("pos");
        neg=getIntent().getExtras().getString("neg");
        t_type=getIntent().getExtras().getString("t_type");
       // txtdesc.setText(desc);
        String question = "<html><head> <meta charset=\"utf-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                "<style>\n" +
                "pre { white-space:normal !important;}" + "</style></head><body>" + desc + "</body></html>";
        txtdesc.loadDataWithBaseURL(null, question, "text/html", "utf-8", null);
        txtdesc.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(TestDescription.this);
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(TestDescription.this);
                View mView = inflater.inflate(R.layout.activity_test_guidelines, null);
                Button btnstarttest=mView.findViewById(R.id.btnstarttest);


                btnstarttest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mDialog.isShowing()){
                            mDialog.dismiss();
                        }

                        if(t_type.equals("Class Test"))
                        {
                            Intent intent = new Intent(TestDescription.this, TestActivity.class);
                            intent.putExtra("tst_id", tst_id);
                            intent.putExtra("sub", sub);
                            intent.putExtra("hours",  hours);
                            intent.putExtra("min",min);
                            intent.putExtra("pos", pos);
                            intent.putExtra("neg", neg);
                            intent.putExtra("type", t_type);
                            startActivity(intent);
                            finish();
                        }
                        else if(t_type.equals("JEE"))
                        {
                            Intent intent = new Intent(TestDescription.this, Jee.class);
                            intent.putExtra("tst_id",tst_id);
                            intent.putExtra("sub",sub);
                            intent.putExtra("hours",hours);
                            intent.putExtra("min",  min);
                            intent.putExtra("pos", pos);
                            intent.putExtra("neg", neg);
                            intent.putExtra("type",t_type);
                            startActivity(intent);
                            finish();
                        }
                        else if(t_type.equals("NEET"))
                        {
                            Intent intent = new Intent(TestDescription.this, Neet.class);
                            intent.putExtra("tst_id",tst_id);
                            intent.putExtra("sub",sub);
                            intent.putExtra("hours",hours);
                            intent.putExtra("min",min);
                            intent.putExtra("pos",pos);
                            intent.putExtra("neg",neg);
                            intent.putExtra("type",t_type);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

              /*  ImageView cancelview=mView.findViewById(R.id.imgcancel);
                cancelview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       mDialog.dismiss();
                    }
                });*/
                mBuilder.setView(mView);
                mDialog = mBuilder.create();
                mDialog.show();


            }
        });
    }

    private void allocatememory() {
        txtdesc=findViewById(R.id.txtdesc);
        btnstart=findViewById(R.id.btnstart);
    }

}
