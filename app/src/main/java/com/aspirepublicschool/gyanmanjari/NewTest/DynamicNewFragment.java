package com.aspirepublicschool.gyanmanjari.NewTest;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
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

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.Test.TestQuestion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DynamicNewFragment extends Fragment {

    View view;
    int val;
    WebView question1_text_view;
    WebView imga, imgb, imgc, imgd;
    RadioGroup firstanswer;
    Button btnmark, btnsave;
    private int currentval;
    private int pre = 0;
    private DBHelperNew mydb;
    CardView card_view_question1;
    RadioButton a, b, c, d, e;
    ArrayList<TestQuestion> testQuestions;

    public DynamicNewFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_dynamic_new, container, false);
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
        mydb = new DBHelperNew(getContext());
        a = view.findViewById(R.id.a);
        b = view.findViewById(R.id.b);
        c = view.findViewById(R.id.c);
        d = view.findViewById(R.id.d);
        e = view.findViewById(R.id.e);
        if (MCQTestFragment.dataflags == true) {

            String question = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + MCQTestFragment.testQuestionArrayList.get(val).getQuestion() + "</body></html>";
            question1_text_view.getSettings().setJavaScriptEnabled(true);
            question1_text_view.getSettings().setDomStorageEnabled(true);
            question1_text_view.setHorizontalScrollBarEnabled(false);
            question1_text_view.setVerticalScrollBarEnabled(false);
            question1_text_view.loadDataWithBaseURL(null, question, "text/html", "utf-8", null);
            question1_text_view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            String opa = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + MCQTestFragment.testQuestionArrayList.get(val).getA() + "</body></html>";
            String opb = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + MCQTestFragment.testQuestionArrayList.get(val).getB() + "</body></html>";
            String opc = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + MCQTestFragment.testQuestionArrayList.get(val).getC() + "</body></html>";
            String opd = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + MCQTestFragment.testQuestionArrayList.get(val).getD() + "</body></html>";
            imga.getSettings().setJavaScriptEnabled(true);
            imga.loadDataWithBaseURL(null, opa, "text/html", "utf-8", null);
            imga.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            imgb.getSettings().setJavaScriptEnabled(true);
            imgb.loadDataWithBaseURL(null, opb, "text/html", "utf-8", null);
            imgb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            imgc.getSettings().setJavaScriptEnabled(true);
            imgc.loadDataWithBaseURL(null, opc, "text/html", "utf-8", null);
            imgc.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            imgd.getSettings().setJavaScriptEnabled(true);
            imgd.loadDataWithBaseURL(null, opd, "text/html", "utf-8", null);
            imgd.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            if(MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("A"))
            {
                if (MCQTestFragment.testQuestionArrayList.get(val).isMark()) {
                    MCQTestFragment.testQuestionArrayList.get(val).setMark(true);
                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.parseColor("#005aac"));
                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    a.setChecked(true);
                    MCQTestFragment.testQuestionArrayList.get(val).setSelected("A");
                } else {
                    a.setChecked(true);
                    MCQTestFragment.testQuestionArrayList.get(val).setSelected("A");
                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.GREEN);
                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                }
            }
            else if( MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("B"))
            {
                if (MCQTestFragment.testQuestionArrayList.get(val).isMark()) {
                    MCQTestFragment.testQuestionArrayList.get(val).setMark(true);
                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.parseColor("#005aac"));
                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    firstanswer.check(R.id.b);
                    MCQTestFragment.testQuestionArrayList.get(val).setSelected("B");
                } else {

                    firstanswer.check(R.id.b);
                    MCQTestFragment.testQuestionArrayList.get(val).setSelected("B");
                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.GREEN);
                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                }
            }
            else if(MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("C"))
            {
                if (MCQTestFragment.testQuestionArrayList.get(val).isMark()) {
                    MCQTestFragment.testQuestionArrayList.get(val).setMark(true);
                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.parseColor("#005aac"));
                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    firstanswer.check(R.id.c);
                    MCQTestFragment.testQuestionArrayList.get(val).setSelected("C");
                } else {
                    firstanswer.check(R.id.c);
                    MCQTestFragment.testQuestionArrayList.get(val).setSelected("C");
                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.GREEN);
                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                }
            }
            else if(MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("D"))
            {
                if (MCQTestFragment.testQuestionArrayList.get(val).isMark()) {
                    MCQTestFragment.testQuestionArrayList.get(val).setMark(true);
                    firstanswer.check(R.id.d);
                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.parseColor("#005aac"));
                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    MCQTestFragment.testQuestionArrayList.get(val).setSelected("D");
                } else {
                    firstanswer.check(R.id.d);
                    MCQTestFragment.testQuestionArrayList.get(val).setSelected("D");
                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.GREEN);
                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                }
            }
            else if(MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("Not Set"))
            {
                if (MCQTestFragment.testQuestionArrayList.get(val).isMark()) {
                    MCQTestFragment.testQuestionArrayList.get(val).setMark(true);
                    firstanswer.check(R.id.e);
                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.parseColor("#005aac"));
                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    MCQTestFragment.testQuestionArrayList.get(val).setSelected("Not Set");
                } else {
                    firstanswer.check(R.id.e);
                    MCQTestFragment.testQuestionArrayList.get(val).setSelected("Not Set");
                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.WHITE);
                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                }
            }

            firstanswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int selectedid = firstanswer.getCheckedRadioButtonId();
                    switch (selectedid) {
                        case R.id.a:
                            if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("A")) {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "A")) {

                                } else {
                                }

                            } else {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "A")) {
                                } else {
                                }
                            }
                            MCQTestFragment.testQuestionArrayList.get(val).setSelected("A");
                            // ArrayList<TestAnswer> answers = mydb.getTestData(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(),MCQTestFragment.testQuestionArrayList.get(val).getQ_id());
                            if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true)
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(),
                                        MCQTestFragment.testQuestionArrayList.get(val).getQ_id(),
                                        "A", true);
                            }
                            else
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "A", false);
                            }

                            break;
                        case R.id.b:
                            if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("B")) {

                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "B")) {

                                } else {
                                }
                            } else {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "B")) {

                                } else {
                                }
                            }
                            MCQTestFragment.testQuestionArrayList.get(val).setSelected("B");
                            if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true)
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "B", true);
                            }
                            else
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "B", false);
                            }

                            break;
                        case R.id.c:
                            if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("C")) {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "C")) {

                                } else {
                                }

                            } else {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "C")) {

                                } else {

                                }
                            }
                            MCQTestFragment.testQuestionArrayList.get(val).setSelected("C");
                            if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true)
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "C", true);
                            }
                            else
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "C", false);
                            }

                            break;
                        case R.id.d:
                            if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("D")) {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "D")) {

                                } else {
                                }

                            } else {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "D")) {

                                } else {

                                }
                            }
                            MCQTestFragment.testQuestionArrayList.get(val).setSelected("D");
                            if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true)
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "D", true);
                            }
                            else
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "D", false);
                            }

                            break;
                        case R.id.e:
                            MCQTestFragment.testQuestionArrayList.get(val).setSelected("Not Set");
                            if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                tv.setTextColor(Color.parseColor("#005aac"));
                                MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                            } else {
                                MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                tv.setTextColor(Color.WHITE);
                                MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                            }
                            Cursor rs = mydb.getQuestions(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id());
                            if (rs.moveToFirst()) {
                                // record exists

                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "Not Set")) {
                                } else {
                                }
                            } else {
                                // record not found
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "Not Set")) {
                                } else {
                                }
                            }
                            if (!rs.isClosed()) {
                                rs.close();
                            }
                            if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true)
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "Not Set", true);
                            }
                            else
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "Not Set", false);
                            }
                            break;
                    }
                }
            });
        }
        else {
            if (MCQTestFragment.testQuestionArrayList.get(val).getQ_img().equals("Not Set")) {

                String question = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" + MCQTestFragment.testQuestionArrayList.get(val).getQuestion() + "</body></html>";
                question1_text_view.loadDataWithBaseURL(null, question, "text/html", "utf-8", null);
                question1_text_view.getSettings().setJavaScriptEnabled(true);
                question1_text_view.getSettings().setDomStorageEnabled(true);
                question1_text_view.setHorizontalScrollBarEnabled(false);
                question1_text_view.setVerticalScrollBarEnabled(false);
                String opa = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" + MCQTestFragment.testQuestionArrayList.get(val).getA() + "</body></html>";
                String opb = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" + MCQTestFragment.testQuestionArrayList.get(val).getB() + "</body></html>";
                String opc = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" + MCQTestFragment.testQuestionArrayList.get(val).getC() + "</body></html>";
                String opd = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" + MCQTestFragment.testQuestionArrayList.get(val).getD() + "</body></html>";
                imga.loadDataWithBaseURL(null, opa, "text/html", "utf-8", null);
                imga.getSettings().setJavaScriptEnabled(true);
                imgb.loadDataWithBaseURL(null, opb, "text/html", "utf-8", null);
                imgb.getSettings().setJavaScriptEnabled(true);
                imgc.loadDataWithBaseURL(null, opc, "text/html", "utf-8", null);
                imgc.getSettings().setJavaScriptEnabled(true);
                imgd.loadDataWithBaseURL(null, opd, "text/html", "utf-8", null);
                imgd.getSettings().setJavaScriptEnabled(true);

            }
            firstanswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int selectedid = firstanswer.getCheckedRadioButtonId();
                    switch (selectedid) {
                        case R.id.a:
                            if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("A")) {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {

                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {

                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "A")) {

                                } else {
                                }

                            } else {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "A")) {

                                } else {

                                }
                            }
                            MCQTestFragment.testQuestionArrayList.get(val).setSelected("A");
                            if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true)
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "A", true);
                            }
                            else
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "A", false);
                            }

                            break;
                        case R.id.b:
                            if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("B")) {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "B")) {

                                } else {

                                }
                            } else {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "B")) {

                                } else {
                                }

                            }
                            MCQTestFragment.testQuestionArrayList.get(val).setSelected("B");
                            if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true)
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "B", true);
                            }
                            else
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "B", false);
                            }

                            break;
                        case R.id.c:
                            if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("C")) {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "C")) {


                                } else {

                                }

                            } else {

                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "C")) {

                                } else {
                                }

                            }
                            MCQTestFragment.testQuestionArrayList.get(val).setSelected("C");
                            if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true)
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "C", true);
                            }
                            else
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "C", false);
                            }

                            break;
                        case R.id.d:
                            if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("D")) {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {

                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "D")) {

                                } else {
                                }

                            } else {
                                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#005aac"));
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "D")) {


                                } else {

                                }

                            }
                            MCQTestFragment.testQuestionArrayList.get(val).setSelected("D");
                            if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true)
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "D", true);
                            }
                            else
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "D", false);
                            }

                            break;
                        case R.id.e:
                            MCQTestFragment.testQuestionArrayList.get(val).setSelected("Not Set");
                            if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                                MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                tv.setTextColor(Color.parseColor("#005aac"));
                                MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                            } else {
                                MCQTestFragment.tabLayout.getTabAt(val).setText("");
                                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                tv.setTextColor(Color.WHITE);
                                MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                            }
                            Cursor rs = mydb.getQuestions(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id());
                            if (rs.moveToFirst()) {
                                // record exists

                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "Not Set")) {

                                } else {

                                }
                            } else {
                                // record not found
                                if (mydb.updateTest(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "Not Set")) {

                                } else {

                                }
                            }

                            if (!rs.isClosed()) {
                                rs.close();
                            }
                            if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true)
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "Not Set", true);
                            }
                            else
                            {
                                saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), "Not Set", false);
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
                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == false) {
                    /*MCQTestFragment.unanswered = MCQTestFragment.unanswered + 1;
                    MCQTestFragment.txtunanswered.setText("" + MCQTestFragment.unanswered);*/
                    MCQTestFragment.testQuestionArrayList.get(val).setMark(true);
                    MCQTestFragment.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.parseColor("#00386b"));
                    MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    if (mydb.updateMark(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(),
                            MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), 1)) {
                        //  Toast.makeText(getContext(), "Update", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(getContext(), "Not Done", Toast.LENGTH_SHORT).show();
                    }
                    if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true)
                    {
                        saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), MCQTestFragment.testQuestionArrayList.get(val).getSelected(), true);
                    }
                    else
                    {
                        saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), MCQTestFragment.testQuestionArrayList.get(val).getSelected(), false);
                    }
                }
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true) {
                    if (MCQTestFragment.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                        MCQTestFragment.testQuestionArrayList.get(val).setMark(false);
                        MCQTestFragment.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.WHITE);
                        MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        if (mydb.updateMark(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(),
                                MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), 0)) {
                            // Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                        } else {
                            //  Toast.makeText(getContext(), "Not Done", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        MCQTestFragment.unanswered = MCQTestFragment.unanswered - 1;
                        MCQTestFragment.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                        MCQTestFragment.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        MCQTestFragment.testQuestionArrayList.get(val).setMark(false);

                        if (mydb.updateMark(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), 0)) {
                            //Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(getContext(), "Not Done", Toast.LENGTH_SHORT).show();
                        }

                    }
                    if (MCQTestFragment.testQuestionArrayList.get(val).isMark() == true)
                    {
                        saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), MCQTestFragment.testQuestionArrayList.get(val).getSelected(), true);
                    }
                    else
                    {
                        saveAnswers(MCQTestFragment.testQuestionArrayList.get(val).getTst_id(), MCQTestFragment.testQuestionArrayList.get(val).getQ_id(), MCQTestFragment.testQuestionArrayList.get(val).getSelected(), false);
                    }

                } else {
                    Toast.makeText(getContext(), "This Question is unmarked for Review", Toast.LENGTH_LONG).show();
                }

            }
        });


        currentval = val;
        return view;
    }
    public static DynamicNewFragment addfrag(int val) {
        DynamicNewFragment fragment = new DynamicNewFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", val);
        fragment.setArguments(args);
        return fragment;
    }
    private void saveAnswers(final String tst_id, final String q_id, final String answer, final Boolean mark) {
        String Webserviceurl= Common.GetWebServiceURL()+"answersubmittestnew.php";
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
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
        Volley.newRequestQueue(getContext()).add(request);

    }
}