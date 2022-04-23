package com.aspirepublicschool.gyanmanjari.Test.JEE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.Test.DBHelper;
import com.aspirepublicschool.gyanmanjari.Test.TestQuestion;
import com.aspirepublicschool.gyanmanjari.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhysicsFragment extends Fragment {
    View view;
    int val;
    WebView question1_text_view;
    WebView imga, imgb, imgc, imgd;
    RadioGroup firstanswer;
    Button btnmark, btnsave;
    private int currentval;
    private int pre = 0;
    private DBHelper mydb;
    CardView card_view_question1;
    RadioButton a, b, c, d, e;
    ArrayList<TestQuestion> testQuestions;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question_fragmnet, container, false);
        val = getArguments().getInt("someInt", 0);
        question1_text_view = view.findViewById(R.id.question1_text_view);
        imga = view.findViewById(R.id.imga);
        imgb = view.findViewById(R.id.imgb);
        imgc = view.findViewById(R.id.imgc);
        imgd = view.findViewById(R.id.imgd);
        btnmark = view.findViewById(R.id.btnmark);
        btnsave = view.findViewById(R.id.btnsave);
        firstanswer = view.findViewById(R.id.firstanswer);
        card_view_question1 = view.findViewById(R.id.card_view_question1);
        testQuestions = new ArrayList<>();
        mydb = new DBHelper(getContext());
        a = view.findViewById(R.id.a);
        b = view.findViewById(R.id.b);
        c = view.findViewById(R.id.c);
        d = view.findViewById(R.id.d);
        e = view.findViewById(R.id.e);
        if (Jee.dataflags == true) {

            String question = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + Jee.testQuestionArrayList.get(val).getQuestion() + "</body></html>";
            WebSettings settings = question1_text_view.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);
            settings.setSupportZoom(true);
            settings.setBuiltInZoomControls(false);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            settings.setDomStorageEnabled(true);
            question1_text_view.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            question1_text_view.setScrollbarFadingEnabled(true);
            question1_text_view.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            question1_text_view.setHorizontalScrollBarEnabled(false);
            question1_text_view.setVerticalScrollBarEnabled(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                question1_text_view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else {
                question1_text_view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
            question1_text_view.loadDataWithBaseURL(null, question, "text/html", "utf-8", null);
            question1_text_view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            String opa = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + Jee.testQuestionArrayList.get(val).getA() + "</body></html>";
            String opb = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + Jee.testQuestionArrayList.get(val).getB() + "</body></html>";
            String opc = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + Jee.testQuestionArrayList.get(val).getC() + "</body></html>";
            String opd = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + Jee.testQuestionArrayList.get(val).getD() + "</body></html>";
            WebSettings settingsa = imga.getSettings();
            settingsa.setJavaScriptEnabled(true);
            settingsa.setLoadWithOverviewMode(true);
            settingsa.setUseWideViewPort(true);
            settingsa.setSupportZoom(true);
            settingsa.setBuiltInZoomControls(false);
            settingsa.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            settingsa.setCacheMode(WebSettings.LOAD_NO_CACHE);
            settingsa.setDomStorageEnabled(true);
            imga.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            imga.setScrollbarFadingEnabled(true);
            imga.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            imga.setHorizontalScrollBarEnabled(false);
            imga.setVerticalScrollBarEnabled(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                imga.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else {
                imga.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
            imga.loadDataWithBaseURL(null, opa, "text/html", "utf-8", null);

            WebSettings settingsb = imgb.getSettings();
            settingsb.setJavaScriptEnabled(true);
            settingsb.setLoadWithOverviewMode(true);
            settingsb.setUseWideViewPort(true);
            settingsb.setSupportZoom(true);
            settingsb.setBuiltInZoomControls(false);
            //settingsb.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            settingsb.setCacheMode(WebSettings.LOAD_NO_CACHE);
            settingsb.setDomStorageEnabled(true);
            imgb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            imgb.setScrollbarFadingEnabled(true);
            imgb.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            imgb.setHorizontalScrollBarEnabled(false);
            imgb.setVerticalScrollBarEnabled(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                imgb.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else {
                imgb.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
            imgb.loadDataWithBaseURL(null, opb, "text/html", "utf-8", null);

            WebSettings settingsc = imgc.getSettings();
            settingsc.setJavaScriptEnabled(true);
            settingsc.setLoadWithOverviewMode(true);
            settingsc.setUseWideViewPort(true);
            settingsc.setSupportZoom(true);
            settingsc.setBuiltInZoomControls(false);
            settingsc.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            settingsc.setCacheMode(WebSettings.LOAD_NO_CACHE);
            settingsc.setDomStorageEnabled(true);
            imgc.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            imgc.setScrollbarFadingEnabled(true);
            imgc.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            imgc.setHorizontalScrollBarEnabled(false);
            imgc.setVerticalScrollBarEnabled(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                imgc.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else {
                imgc.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
            imgc.loadDataWithBaseURL(null, opc, "text/html", "utf-8", null);

            WebSettings settingsd = imgd.getSettings();
            settingsd.setJavaScriptEnabled(true);
            settingsd.setLoadWithOverviewMode(true);
            settingsd.setUseWideViewPort(true);
            settingsd.setSupportZoom(true);
            settingsd.setBuiltInZoomControls(false);
            settingsd.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            settingsd.setCacheMode(WebSettings.LOAD_NO_CACHE);
            settingsd.setDomStorageEnabled(true);
            imgd.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            imgd.setScrollbarFadingEnabled(true);
            imgd.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            imgd.setHorizontalScrollBarEnabled(false);
            imgd.setVerticalScrollBarEnabled(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                imgd.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else {
                imgd.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
            imgd.loadDataWithBaseURL(null, opd, "text/html", "utf-8", null);
            if(Jee.testQuestionArrayList.get(val).getSelected().equals("A"))
            {
                if (Jee.testQuestionArrayList.get(val).isMark()) {
                    Jee.testQuestionArrayList.get(val).setMark(true);
                    Jee.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.parseColor("#E02525"));
                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    a.setChecked(true);
                    Jee.testQuestionArrayList.get(val).setSelected("A");
                } else {
                    a.setChecked(true);
                    Jee.testQuestionArrayList.get(val).setSelected("A");
                    Jee.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.GREEN);
                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));

                }

            }
            else if( Jee.testQuestionArrayList.get(val).getSelected().equals("B"))
            {
                if (Jee.testQuestionArrayList.get(val).isMark()) {
                    Jee.testQuestionArrayList.get(val).setMark(true);
                    Jee.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.parseColor("#E02525"));
                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    firstanswer.check(R.id.b);
                    Jee.testQuestionArrayList.get(val).setSelected("B");
                } else {
                    firstanswer.check(R.id.b);
                    Jee.testQuestionArrayList.get(val).setSelected("B");
                    Jee.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.GREEN);
                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                }
            }
            else if(Jee.testQuestionArrayList.get(val).getSelected().equals("C"))
            {
                if (Jee.testQuestionArrayList.get(val).isMark()) {
                    Jee.testQuestionArrayList.get(val).setMark(true);
                    Jee.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.parseColor("#E02525"));
                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    firstanswer.check(R.id.c);
                    Jee.testQuestionArrayList.get(val).setSelected("C");
                } else {
                    firstanswer.check(R.id.c);
                    Jee.testQuestionArrayList.get(val).setSelected("C");
                    Jee.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.GREEN);
                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                }
            }
            else if(Jee.testQuestionArrayList.get(val).getSelected().equals("D"))
            {
                if (Jee.testQuestionArrayList.get(val).isMark()) {
                    Jee.testQuestionArrayList.get(val).setMark(true);
                    firstanswer.check(R.id.d);
                    Jee.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.parseColor("#E02525"));
                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    Jee.testQuestionArrayList.get(val).setSelected("D");
                } else {
                    firstanswer.check(R.id.d);
                    Jee.testQuestionArrayList.get(val).setSelected("D");
                    Jee.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.GREEN);
                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                }

            }
            else if(Jee.testQuestionArrayList.get(val).getSelected().equals("Not Set"))
            {
                if (Jee.testQuestionArrayList.get(val).isMark()) {
                    Jee.testQuestionArrayList.get(val).setMark(true);
                    firstanswer.check(R.id.e);
                    Jee.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.parseColor("#E02525"));
                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    Jee.testQuestionArrayList.get(val).setSelected("Not Set");
                } else {
                    firstanswer.check(R.id.e);
                    Jee.testQuestionArrayList.get(val).setSelected("Not Set");
                    Jee.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.WHITE);
                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));

                }
            }

           /* Cursor rs = mydb.getQuestions(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id());
            if (rs.moveToFirst()) {
                // record exists
                ArrayList<TestAnswer> array_list = mydb.getAllTestData(Jee.testQuestionArrayList.get(val).getTst_id());
                Log.d("answers", array_list.get(val).getAnswer());
                if (array_list.get(val).getAnswer().equals("A")) {
                    if (array_list.get(val).getMark() == 1) {
                        Jee.testQuestionArrayList.get(val).setMark(true);
                        Jee.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.parseColor("#E02525"));
                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        a.setChecked(true);
                        Jee.testQuestionArrayList.get(val).setSelected("A");
                    } else {
                        a.setChecked(true);
                        Jee.testQuestionArrayList.get(val).setSelected("A");
                        Jee.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));

                    }

                } else if (array_list.get(val).getAnswer().equals("B")) {
                    if (array_list.get(val).getMark() == 1) {
                        Jee.testQuestionArrayList.get(val).setMark(true);
                        Jee.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.parseColor("#E02525"));
                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        firstanswer.check(R.id.b);
                        Jee.testQuestionArrayList.get(val).setSelected("B");
                    } else {

                        firstanswer.check(R.id.b);
                        Jee.testQuestionArrayList.get(val).setSelected("B");
                        Jee.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    }

                } else if (array_list.get(val).getAnswer().equals("C")) {
                    if (array_list.get(val).getMark() == 1) {
                        Jee.testQuestionArrayList.get(val).setMark(true);
                        Jee.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.parseColor("#E02525"));
                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        firstanswer.check(R.id.c);
                        Jee.testQuestionArrayList.get(val).setSelected("C");
                    } else {
                        firstanswer.check(R.id.c);
                        Jee.testQuestionArrayList.get(val).setSelected("C");
                        Jee.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    }

                } else if (array_list.get(val).getAnswer().equals("D")) {
                    if (array_list.get(val).getMark() == 1) {
                        Jee.testQuestionArrayList.get(val).setMark(true);
                        firstanswer.check(R.id.d);
                        Jee.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.parseColor("#E02525"));
                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        Jee.testQuestionArrayList.get(val).setSelected("D");
                    } else {
                        firstanswer.check(R.id.d);
                        Jee.testQuestionArrayList.get(val).setSelected("D");
                        Jee.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    }

                } else if (array_list.get(val).getAnswer().equals("Not Set")) {
                    if (array_list.get(val).getMark() == 1) {
                        Jee.testQuestionArrayList.get(val).setMark(true);
                        firstanswer.check(R.id.e);
                        Jee.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.parseColor("#E02525"));
                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        Jee.testQuestionArrayList.get(val).setSelected("Not Set");
                    } else {
                        firstanswer.check(R.id.e);
                        Jee.testQuestionArrayList.get(val).setSelected("Not Set");
                        Jee.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.WHITE);
                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));

                    }
                }
            }*/
            firstanswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int selectedid = firstanswer.getCheckedRadioButtonId();
                    switch (selectedid) {
                        case R.id.a:
                            Jee.testQuestionArrayList.get(val).setSelected("A");
                            // ArrayList<TestAnswer> answers = mydb.getTestData(Jee.testQuestionArrayList.get(val).getTst_id(),Jee.testQuestionArrayList.get(val).getQ_id());
                            if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "A", true);
                            } else {
                                saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "A", false);
                            }

                            if (Jee.testQuestionArrayList.get(val).getSelected().equals("A")) {
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (Jee.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "A")) {

                                } else {
                                }

                            } else {
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "A")) {

                                } else {

                                }
                            }

                            break;
                        case R.id.b:
                                if (Jee.testQuestionArrayList.get(val).getSelected().equals("B")) {

                                    if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.parseColor("#E02525"));
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    } else {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.GREEN);
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    }
                                } else if (Jee.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                    if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.parseColor("#E02525"));
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    } else {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.GREEN);
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    }
                                    if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "B")) {

                                    } else {
                                    }
                                } else {
                                    if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.parseColor("#E02525"));
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    } else {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.GREEN);
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    }
                                    if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "B")) {


                                    } else {

                                    }

                                }
                                Jee.testQuestionArrayList.get(val).setSelected("B");
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "B", true);
                                } else {
                                    saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "B", false);
                                }

                            break;
                        case R.id.c:
                                if (Jee.testQuestionArrayList.get(val).getSelected().equals("C")) {
                                    if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.parseColor("#E02525"));
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    } else {

                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.GREEN);
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    }
                                } else if (Jee.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                    if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.parseColor("#E02525"));
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    } else {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.GREEN);
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    }
                                    if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "C")) {

                                    } else {

                                    }

                                } else {
                                    if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.parseColor("#E02525"));
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    } else {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.GREEN);
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    }
                                    if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "C")) {


                                    } else {

                                    }

                                }
                                Jee.testQuestionArrayList.get(val).setSelected("C");
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "C", true);
                                } else {
                                    saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "C", false);
                                }


                            break;
                        case R.id.d:
                                if (Jee.testQuestionArrayList.get(val).getSelected().equals("D")) {
                                    if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.parseColor("#E02525"));
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    } else {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.GREEN);
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    }
                                } else if (Jee.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                    if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.parseColor("#E02525"));
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    } else {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.GREEN);
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    }
                                    if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "D")) {

                                    } else {
                                    }

                                } else {
                                    if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.parseColor("#E02525"));
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    } else {
                                        Jee.tabLayout.getTabAt(val).setText("");
                                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                        tv.setTextColor(Color.GREEN);
                                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                    }
                                    if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "D")) {


                                    } else {

                                    }

                                }
                                Jee.testQuestionArrayList.get(val).setSelected("D");
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "D", true);
                                } else {
                                    saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "D", false);
                                }

                            break;
                        case R.id.e:
                                Jee.testQuestionArrayList.get(val).setSelected("Not Set");
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.WHITE);
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                Cursor rs = mydb.getQuestions(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id());
                                if (rs.moveToFirst()) {
                                    // record exists

                                    if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "Not Set")) {

                                    } else {
                                    }


                                } else {
                                    // record not found
                                    if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "Not Set")) {


                                    } else {

                                    }
                                }

                                if (!rs.isClosed()) {
                                    rs.close();
                                }
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "Not Set", true);
                                } else {
                                    saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "Not Set", false);
                                }

                            break;


                    }


                }
            });
        } else {
            if (Jee.testQuestionArrayList.get(val).getQ_img().equals("Not Set")) {

                String question = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" + Jee.testQuestionArrayList.get(val).getQuestion() + "</body></html>";
                WebSettings settings = question1_text_view.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setLoadWithOverviewMode(true);
                settings.setUseWideViewPort(true);
                settings.setSupportZoom(true);
                settings.setBuiltInZoomControls(false);
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
                settings.setDomStorageEnabled(true);
                question1_text_view.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                question1_text_view.setScrollbarFadingEnabled(true);
                question1_text_view.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                question1_text_view.setHorizontalScrollBarEnabled(false);
                question1_text_view.setVerticalScrollBarEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    question1_text_view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                } else {
                    question1_text_view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                }
                question1_text_view.loadDataWithBaseURL(null, question, "text/html", "utf-8", null);
                question1_text_view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                String opa = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" + Jee.testQuestionArrayList.get(val).getA() + "</body></html>";
                String opb = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" + Jee.testQuestionArrayList.get(val).getB() + "</body></html>";
                String opc = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" + Jee.testQuestionArrayList.get(val).getC() + "</body></html>";
                String opd = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" + Jee.testQuestionArrayList.get(val).getD() + "</body></html>";
                WebSettings settingsa = imga.getSettings();
                settingsa.setJavaScriptEnabled(true);
                settingsa.setLoadWithOverviewMode(true);
                settingsa.setUseWideViewPort(true);
                settingsa.setSupportZoom(true);
                settingsa.setBuiltInZoomControls(false);
                settingsa.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                settingsa.setCacheMode(WebSettings.LOAD_NO_CACHE);
                settingsa.setDomStorageEnabled(true);
                imga.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                imga.setScrollbarFadingEnabled(true);
                imga.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                imga.setHorizontalScrollBarEnabled(false);
                imga.setVerticalScrollBarEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    imga.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                } else {
                    imga.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                }
                imga.loadDataWithBaseURL(null, opa, "text/html", "utf-8", null);

                WebSettings settingsb = imgb.getSettings();
                settingsb.setJavaScriptEnabled(true);
                settingsb.setLoadWithOverviewMode(true);
                settingsb.setUseWideViewPort(true);
                settingsb.setSupportZoom(true);
                settingsb.setBuiltInZoomControls(false);
                settingsb.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                settingsb.setCacheMode(WebSettings.LOAD_NO_CACHE);
                settingsb.setDomStorageEnabled(true);
                imgb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                imgb.setScrollbarFadingEnabled(true);
                imgb.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                imgb.setHorizontalScrollBarEnabled(false);
                imgb.setVerticalScrollBarEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    imgb.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                } else {
                    imgb.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                }
                imgb.loadDataWithBaseURL(null, opb, "text/html", "utf-8", null);

                WebSettings settingsc = imgc.getSettings();
                settingsc.setJavaScriptEnabled(true);
                settingsc.setLoadWithOverviewMode(true);
                settingsc.setUseWideViewPort(true);
                settingsc.setSupportZoom(true);
                settingsc.setBuiltInZoomControls(false);
                settingsc.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                settingsc.setCacheMode(WebSettings.LOAD_NO_CACHE);
                settingsc.setDomStorageEnabled(true);
                imgc.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                imgc.setScrollbarFadingEnabled(true);
                imgc.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                imgc.setHorizontalScrollBarEnabled(false);
                imgc.setVerticalScrollBarEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    imgc.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                } else {
                    imgc.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                }
                imgc.loadDataWithBaseURL(null, opc, "text/html", "utf-8", null);

                WebSettings settingsd = imgd.getSettings();
                settingsd.setJavaScriptEnabled(true);
                settingsd.setLoadWithOverviewMode(true);
                settingsd.setUseWideViewPort(true);
                settingsd.setSupportZoom(true);
                settingsd.setBuiltInZoomControls(false);
                settingsd.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                settingsd.setCacheMode(WebSettings.LOAD_NO_CACHE);
                settingsd.setDomStorageEnabled(true);
                imgd.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                imgd.setScrollbarFadingEnabled(true);
                imgd.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                imgd.setHorizontalScrollBarEnabled(false);
                imgd.setVerticalScrollBarEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    imgd.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                } else {
                    imgd.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                }
                imgd.loadDataWithBaseURL(null, opd, "text/html", "utf-8", null);


            }
            firstanswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int selectedid = firstanswer.getCheckedRadioButtonId();
                    switch (selectedid) {
                        case R.id.a:
                            if (Jee.testQuestionArrayList.get(val).getSelected().equals("A")) {
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (Jee.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {

                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "A")) {


                                } else {

                                }

                            } else {
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "A")) {


                                } else {

                                }

                            }
                            Jee.testQuestionArrayList.get(val).setSelected("A");
                            if (Jee.testQuestionArrayList.get(val).isMark() == true)
                            {
                                saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "A", true);
                            }
                            else
                            {
                                saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "A", false);
                            }


                            break;
                        case R.id.b:
                            if (Jee.testQuestionArrayList.get(val).getSelected().equals("B")) {
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (Jee.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "B")) {


                                } else {

                                }
                            } else {
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "B")) {

                                } else {
                                }

                            }
                            Jee.testQuestionArrayList.get(val).setSelected("B");
                            if (Jee.testQuestionArrayList.get(val).isMark() == true)
                            {
                                saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "B", true);
                            }
                            else
                            {
                                saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "B", false);
                            }

                            break;
                        case R.id.c:
                            if (Jee.testQuestionArrayList.get(val).getSelected().equals("C")) {
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (Jee.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "C")) {


                                } else {

                                }

                            } else {

                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "C")) {

                                } else {
                                }

                            }
                            Jee.testQuestionArrayList.get(val).setSelected("C");
                            if (Jee.testQuestionArrayList.get(val).isMark() == true)
                            {
                                saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "C", true);
                            }
                            else
                            {
                                saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "C", false);
                            }

                            break;
                        case R.id.d:
                            if (Jee.testQuestionArrayList.get(val).getSelected().equals("D")) {
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (Jee.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {

                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "D")) {

                                } else {
                                }

                            } else {
                                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    Jee.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "D")) {


                                } else {

                                }

                            }
                            Jee.testQuestionArrayList.get(val).setSelected("D");
                            if (Jee.testQuestionArrayList.get(val).isMark() == true)
                            {
                                saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "D", true);
                            }
                            else
                            {
                                saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "D", false);
                            }

                            break;
                        case R.id.e:
                            Jee.testQuestionArrayList.get(val).setSelected("Not Set");
                            if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                                Jee.tabLayout.getTabAt(val).setText("");
                                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                tv.setTextColor(Color.parseColor("#E02525"));
                                Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                            } else {
                                Jee.tabLayout.getTabAt(val).setText("");
                                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                tv.setTextColor(Color.WHITE);
                                Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                            }
                            Cursor rs = mydb.getQuestions(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id());
                            if (rs.moveToFirst()) {
                                // record exists

                                if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "Not Set")) {

                                } else {

                                }


                            } else {
                                // record not found
                                if (mydb.updateTest(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "Not Set")) {

                                } else {

                                }
                            }

                            if (!rs.isClosed()) {
                                rs.close();
                            }
                            if (Jee.testQuestionArrayList.get(val).isMark() == true)
                            {
                                saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "Not Set", true);
                            }
                            else
                            {
                                saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), "Not Set", false);
                            }

                            break;


                    }


                }
            });


        }
        btnmark.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (Jee.testQuestionArrayList.get(val).isMark() == false) {
                    Jee.testQuestionArrayList.get(val).setMark(true);
                    Jee.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.parseColor("#E02525"));
                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    if (mydb.updateMark(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), 1)) {
                    } else {
                    }
                    if (Jee.testQuestionArrayList.get(val).isMark() == true)
                    {
                        saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), Jee.testQuestionArrayList.get(val).getSelected(), true);
                    }
                    else
                    {
                        saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), Jee.testQuestionArrayList.get(val).getSelected(), false);
                    }
                }
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if (Jee.testQuestionArrayList.get(val).isMark() == true) {
                    if (Jee.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                        Jee.testQuestionArrayList.get(val).setMark(false);
                        Jee.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.WHITE);
                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        if (mydb.updateMark(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), 0)) {
                        } else {

                        }
                    } else {
                        Jee.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                        Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        Jee.testQuestionArrayList.get(val).setMark(false);

                        if (mydb.updateMark(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), 0)) {

                        } else {

                        }

                    }
                    if (Jee.testQuestionArrayList.get(val).isMark() == true)
                    {
                        saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), Jee.testQuestionArrayList.get(val).getSelected(), true);
                    }
                    else
                    {
                        saveAnswers(Jee.testQuestionArrayList.get(val).getTst_id(), Jee.testQuestionArrayList.get(val).getQ_id(), Jee.testQuestionArrayList.get(val).getSelected(), false);
                    }
                } else {
                    //already unmark old code
//                    Toast.makeText(getContext(), "This Question is unmarked for Review", Toast.LENGTH_LONG).show();
                }

            }
        });


        currentval = val;
        return view;
    }

    private void saveAnswers(final String tst_id, final String q_id, final String answer, final Boolean mark) {
        String Webserviceurl= Common.GetWebServiceURL()+"answersubmittest.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String sc_id = preferences.getString("sc_id","none").toUpperCase();
        final String stu_id = preferences.getString("stu_id","none").toUpperCase();
        final String class_id = preferences.getString("class_id","none").toUpperCase();
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("aaa",response );
                try {
                    JSONObject object=new JSONObject(response);
                    if(object.getString("message").equals("Success"))
                    {
                        //Successfully answer stored on db

                    } else {
                            Jee.testQuestionArrayList.get(val).setSelected("Not Set");
                            Jee.tabLayout.getTabAt(val).setText("");
                            TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                            tv.setTextColor(Color.WHITE);
                            Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));

                            firstanswer.check(R.id.e);
                            Toast.makeText(getActivity(), R.string.test_message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();

                    //resetting answer
                    Jee.testQuestionArrayList.get(val).setSelected("Not Set");
                    Jee.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.WHITE);
                    Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));

                    firstanswer.check(R.id.e);
                    Toast.makeText(getActivity(), R.string.test_message, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //resetting answer
                Jee.testQuestionArrayList.get(val).setSelected("Not Set");
                Jee.tabLayout.getTabAt(val).setText("");
                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                tv.setTextColor(Color.WHITE);
                Jee.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));

                firstanswer.check(R.id.e);
                Toast.makeText(getActivity(), R.string.test_message, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id", sc_id);
                data.put("cid", class_id);
                data.put("stu_id", stu_id);
                data.put("tst_id", tst_id);
                data.put("qid", q_id);
                data.put("answer", answer);
                String marks;
                if(mark)
                {
                    marks="0";
                }
                else
                {
                    marks="1";
                }
                data.put("mark", ""+marks);
                Log.d("data", data.toString());
                return data;
            }
        };
       /* request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
        Volley.newRequestQueue(getActivity()).add(request);
    }

    public static PhysicsFragment addfrag(int val) {
        PhysicsFragment fragment = new PhysicsFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", val);
        fragment.setArguments(args);
        return fragment;
    }

   
}