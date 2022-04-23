package com.aspirepublicschool.gyanmanjari.Test.NEET;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
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

import com.aspirepublicschool.gyanmanjari.Test.DBHelper;
import com.aspirepublicschool.gyanmanjari.Test.TestAnswer;
import com.aspirepublicschool.gyanmanjari.Test.TestQuestion;
import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class BiologyFragment extends Fragment {

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
        if (NeetTest.dataflags == true) {

            String question = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + Biology.testQuestionArrayList.get(val).getQuestion() + "</body></html>";
            question1_text_view.getSettings().setJavaScriptEnabled(true);
            question1_text_view.loadDataWithBaseURL(null, question, "text/html", "utf-8", null);
            question1_text_view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            String opa = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" +Biology.testQuestionArrayList.get(val).getA() + "</body></html>";
            String opb = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" +Biology.testQuestionArrayList.get(val).getB() + "</body></html>";
            String opc = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" +Biology.testQuestionArrayList.get(val).getC() + "</body></html>";
            String opd = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" +Biology.testQuestionArrayList.get(val).getD() + "</body></html>";
            imga.loadDataWithBaseURL(null, opa, "text/html", "utf-8", null);
            imga.getSettings().setJavaScriptEnabled(true);
            imga.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            imgb.loadDataWithBaseURL(null, opb, "text/html", "utf-8", null);
            imgb.getSettings().setJavaScriptEnabled(true);
            imgb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            imgc.loadDataWithBaseURL(null, opc, "text/html", "utf-8", null);
            imgc.getSettings().setJavaScriptEnabled(true);
            imgc.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            imgd.loadDataWithBaseURL(null, opd, "text/html", "utf-8", null);
            imgd.getSettings().setJavaScriptEnabled(true);
            imgd.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            Cursor rs = mydb.getQuestions(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id());
            if (rs.moveToFirst()) {
                // record exists
                ArrayList<TestAnswer> array_list = mydb.getAllTestData(Biology.testQuestionArrayList.get(val).getTst_id());
                if (array_list.get(val).getAnswer().equals("A")) {
                    if (array_list.get(val).getMark() == 1) {
                       Biology.testQuestionArrayList.get(val).setMark(true);
                       Biology.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        //tv.setBackgroundColor(Color.parseColor("#E02525"));
                        tv.setTextColor(Color.parseColor("#E02525"));
                       Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        a.setChecked(true);
                       Biology.testQuestionArrayList.get(val).setSelected("A");
                    } else {
                        a.setChecked(true);
                        Biology.testQuestionArrayList.get(val).setSelected("A");
                        Biology.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                        Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    }

                } else if (array_list.get(val).getAnswer().equals("B")) {
                    if (array_list.get(val).getMark() == 1) {
                       Biology.testQuestionArrayList.get(val).setMark(true);
                       Biology.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.parseColor("#E02525"));
                       Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        firstanswer.check(R.id.b);
                       Biology.testQuestionArrayList.get(val).setSelected("B");
                    } else {

                        firstanswer.check(R.id.b);
                       Biology.testQuestionArrayList.get(val).setSelected("B");
                       Biology.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                       Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    }

                } else if (array_list.get(val).getAnswer().equals("C")) {
                    if (array_list.get(val).getMark() == 1) {
                       Biology.testQuestionArrayList.get(val).setMark(true);
                       Biology.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.parseColor("#E02525"));
                       Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        firstanswer.check(R.id.c);
                       Biology.testQuestionArrayList.get(val).setSelected("C");
                    } else {
                        firstanswer.check(R.id.c);
                       Biology.testQuestionArrayList.get(val).setSelected("C");
                       Biology.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                       Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    }

                } else if (array_list.get(val).getAnswer().equals("D")) {
                    if (array_list.get(val).getMark() == 1) {
                       Biology.testQuestionArrayList.get(val).setMark(true);
                        firstanswer.check(R.id.d);
                       Biology.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.parseColor("#E02525"));
                       Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                       Biology.testQuestionArrayList.get(val).setSelected("D");
                    } else {
                        firstanswer.check(R.id.d);
                       Biology.testQuestionArrayList.get(val).setSelected("D");
                       Biology.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                       Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    }

                } else if (array_list.get(val).getAnswer().equals("Not Set")) {
                    if (array_list.get(val).getMark() == 1) {
                       Biology.testQuestionArrayList.get(val).setMark(true);
                        firstanswer.check(R.id.e);
                       Biology.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.parseColor("#E02525"));
                       Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                       Biology.testQuestionArrayList.get(val).setSelected("Not Set");
                    } else {
                        firstanswer.check(R.id.e);
                       Biology.testQuestionArrayList.get(val).setSelected("Not Set");
                       Biology.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.WHITE);
                       Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    }
                }
            }
            firstanswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int selectedid = firstanswer.getCheckedRadioButtonId();
                    switch (selectedid) {
                        case R.id.a:
                            if (Biology.testQuestionArrayList.get(val).getSelected().equals("A")) {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                    /*Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    //Biology.txtquestion.setText("" +Biology.ansques);
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (Biology.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                   /*Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered + 1;
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                   /*Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "A")) {

                                } else {
                                }

                            } else {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                  /*Biology.ansques =Biology.ansques - 1;
                                  Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered - 1;
                                  Biology.unanswered =Biology.unanswered + 1;
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                  /*Biology.ansques =Biology.ansques - 1;
                                  Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "A")) {
                                    //Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    //Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }

                            }
                           Biology.testQuestionArrayList.get(val).setSelected("A");

                            break;
                        case R.id.b:
                            if (Biology.testQuestionArrayList.get(val).getSelected().equals("B")) {

                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                   /*Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    //Biology.txtquestion.setText("" +Biology.ansques);
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (Biology.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                   /*Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered + 1;
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    /*Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "B")) {
                                    // Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    // Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                   /*Biology.ansques =Biology.ansques - 1;
                                  Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered + 1;
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    /*Biology.ansques =Biology.ansques - 1;
                                  Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "B")) {
                                    //Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    // Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }

                            }
                           Biology.testQuestionArrayList.get(val).setSelected("B");
                            break;
                        case R.id.c:
                            if (Biology.testQuestionArrayList.get(val).getSelected().equals("C")) {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                   /*Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    //Biology.txtquestion.setText("" +Biology.ansques);
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (Biology.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                   /*Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered + 1;
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                   /*Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "C")) {
                                    // Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    // Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                    /*Biology.ansques =Biology.ansques - 1;
                                  Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered - 1;
                                  Biology.unanswered =Biology.unanswered + 1;
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                   /*Biology.ansques =Biology.ansques - 1;
                                  Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "C")) {
                                    //Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    //Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }

                            }
                           Biology.testQuestionArrayList.get(val).setSelected("C");
                            break;
                        case R.id.d:
                            if (Biology.testQuestionArrayList.get(val).getSelected().equals("D")) {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                   /*Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    //Biology.txtquestion.setText("" +Biology.ansques);
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (Biology.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                   /*Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered + 1;
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    /*Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "D")) {
                                    // Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    // Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                   /*Biology.ansques =Biology.ansques - 1;
                                  Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered - 1;
                                  Biology.unanswered =Biology.unanswered + 1;
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    /*Biology.ansques =Biology.ansques - 1;
                                  Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "D")) {
                                    //Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    //Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }

                            }
                           Biology.testQuestionArrayList.get(val).setSelected("D");
                            break;
                        case R.id.e:
                           Biology.testQuestionArrayList.get(val).setSelected("Not Set");
                            if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                               /*Biology.ansques =Biology.ansques - 1;
                              Biology.txtquestion.setText("" +Biology.ansques);
                              Biology.unanswered =Biology.unanswered - 1;
                              Biology.unanswered =Biology.unanswered + 1;
                              Biology.txtunanswered.setText("" +Biology.unanswered);*/
                               Biology.tabLayout.getTabAt(val).setText("");
                                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                tv.setTextColor(Color.parseColor("#E02525"));
                               Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                            } else {
                                /*Biology.ansques =Biology.ansques - 1;
                              Biology.txtquestion.setText("" +Biology.ansques);*/
                               Biology.tabLayout.getTabAt(val).setText("");
                                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                tv.setTextColor(Color.WHITE);
                               Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                            }
                            Cursor rs = mydb.getQuestions(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id());
                            if (rs.moveToFirst()) {
                                // record exists
                                //Toast.makeText(getContext(), "Data Exists",Toast.LENGTH_SHORT).show();
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "Not Set")) {
                                    // Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    //Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                // record not found
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "Not Set")) {
                                    //Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    // Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }
                            }

                            if (!rs.isClosed()) {
                                rs.close();
                            }

                            break;


                    }
                }
            });
        } else {
            if (Biology.testQuestionArrayList.get(val).getQ_img().equals("Not Set")) {

                String question = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" +Biology.testQuestionArrayList.get(val).getQuestion() + "</body></html>";
                question1_text_view.loadDataWithBaseURL(null, question, "text/html", "utf-8", null);
                question1_text_view.getSettings().setJavaScriptEnabled(true);
                question1_text_view.getSettings().setDomStorageEnabled(true);
                question1_text_view.setHorizontalScrollBarEnabled(false);
                question1_text_view.setVerticalScrollBarEnabled(false);
                String opa = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" +Biology.testQuestionArrayList.get(val).getA() + "</body></html>";
                String opb = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" +Biology.testQuestionArrayList.get(val).getB() + "</body></html>";
                String opc = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" +Biology.testQuestionArrayList.get(val).getC() + "</body></html>";
                String opd = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" +Biology.testQuestionArrayList.get(val).getD() + "</body></html>";
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
                            if (Biology.testQuestionArrayList.get(val).getSelected().equals("A")) {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                    /*Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                    Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                    Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    //Biology.txtquestion.setText("" +Biology.ansques);
                                    Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                    Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (Biology.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {

                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                   /*Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered + 1;
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    /*Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "A")) {
                                    // Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    //Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                    /*Biology.ansques =Biology.ansques - 1;
                                  Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered - 1;
                                  Biology.unanswered =Biology.unanswered + 1;
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    /*Biology.ansques =Biology.ansques - 1;
                                  Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "A")) {
                                    //Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    //  Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }

                            }
                           Biology.testQuestionArrayList.get(val).setSelected("A");

                            break;
                        case R.id.b:
                            if (Biology.testQuestionArrayList.get(val).getSelected().equals("B")) {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                   /*Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered + 1;*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    //Biology.txtquestion.setText("" +Biology.ansques);
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (Biology.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                    /*Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered + 1;
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    /*Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "B")) {
                                    //Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    // Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                    /*Biology.ansques =Biology.ansques - 1;
                                  Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered - 1;
                                  Biology.unanswered =Biology.unanswered + 1;
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    /*Biology.ansques =Biology.ansques - 1;
                                  Biology.ansques =Biology.ansques + 1;*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "B")) {
                                    //Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    //Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }

                            }
                           Biology.testQuestionArrayList.get(val).setSelected("B");
                            break;
                        case R.id.c:
                            if (Biology.testQuestionArrayList.get(val).getSelected().equals("C")) {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                   /*Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    /*Biology.txtquestion.setText("" +Biology.ansques);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (Biology.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                    /*Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered + 1;
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                   /*Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "C")) {
                                    // Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    //Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }

                            } else {

                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                   /*Biology.ansques =Biology.ansques - 1;
                                  Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered - 1;
                                  Biology.unanswered =Biology.unanswered + 1;
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    /*Biology.ansques =Biology.ansques - 1;
                                  Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "C")) {
                                    // Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    // Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }

                            }
                           Biology.testQuestionArrayList.get(val).setSelected("C");
                            break;
                        case R.id.d:
                            if (Biology.testQuestionArrayList.get(val).getSelected().equals("D")) {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                   /*Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    //Biology.txtquestion.setText("" +Biology.ansques);
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                            } else if (Biology.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {

                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                   /*Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered + 1;
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    /*Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "D")) {
                                    // Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    //  Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                    /*Biology.ansques =Biology.ansques - 1;
                                  Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);
                                  Biology.unanswered =Biology.unanswered - 1;
                                  Biology.unanswered =Biology.unanswered + 1;
                                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.parseColor("#E02525"));
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                } else {
                                    /*Biology.ansques =Biology.ansques - 1;
                                  Biology.ansques =Biology.ansques + 1;
                                  Biology.txtquestion.setText("" +Biology.ansques);*/
                                   Biology.tabLayout.getTabAt(val).setText("");
                                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                    tv.setTextColor(Color.GREEN);
                                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                                }
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "D")) {
                                    // Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    // Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }

                            }
                           Biology.testQuestionArrayList.get(val).setSelected("D");
                            break;
                        case R.id.e:
                           Biology.testQuestionArrayList.get(val).setSelected("Not Set");
                            if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                                /*TestTine.ansques = TestTine.ansques - 1;
                              Biology.txtquestion.setText("" +Biology.ansques);
                              Biology.unanswered =Biology.unanswered + 1;
                              Biology.txtunanswered.setText("" +Biology.unanswered);*/
                               Biology.tabLayout.getTabAt(val).setText("");
                                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                tv.setTextColor(Color.parseColor("#E02525"));
                               Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                            } else {
                                /*TestTine.ansques = TestTine.ansques - 1;
                              Biology.txtquestion.setText("" +Biology.ansques);*/
                               Biology.tabLayout.getTabAt(val).setText("");
                                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                                tv.setTextColor(Color.WHITE);
                               Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                            }
                            Cursor rs = mydb.getQuestions(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id());
                            if (rs.moveToFirst()) {
                                // record exists
                                //Toast.makeText(getContext(), "Data Exists", Toast.LENGTH_SHORT).show();
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "Not Set")) {
                                    //Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    // Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                // record not found
                                if (mydb.updateTest(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), "Not Set")) {
                                    // Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    //Toast.makeText(getContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                }
                            }

                            if (!rs.isClosed()) {
                                rs.close();
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
                if (Biology.testQuestionArrayList.get(val).isMark() == false) {
                    /*Biology.unanswered =Biology.unanswered + 1;
                  Biology.txtunanswered.setText("" +Biology.unanswered);*/
                   Biology.testQuestionArrayList.get(val).setMark(true);
                   Biology.tabLayout.getTabAt(val).setText("");
                    TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                    tv.setTextColor(Color.parseColor("#E02525"));
                   Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                    if (mydb.updateMark(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), 1)) {
                        //  Toast.makeText(getContext(), "Update", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(getContext(), "Not Done", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if (Biology.testQuestionArrayList.get(val).isMark() == true) {
                    if (Biology.testQuestionArrayList.get(val).getSelected().equals("Not Set")) {
                       /*Biology.unanswered =Biology.unanswered - 1;
                      Biology.txtunanswered.setText("" +Biology.unanswered);*/
                       Biology.testQuestionArrayList.get(val).setMark(false);
                       Biology.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.WHITE);
                        Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        if (mydb.updateMark(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), 0)) {
                            // Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                        } else {
                            //  Toast.makeText(getContext(), "Not Done", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                       Biology.tabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                       Biology.tabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                       Biology.testQuestionArrayList.get(val).setMark(false);

                        if (mydb.updateMark(Biology.testQuestionArrayList.get(val).getTst_id(),Biology.testQuestionArrayList.get(val).getQ_id(), 0)) {
                            //Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(getContext(), "Not Done", Toast.LENGTH_SHORT).show();
                        }

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

    public static BiologyFragment addfrag(int val) {
        BiologyFragment fragment = new BiologyFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", val);
        fragment.setArguments(args);
        return fragment;
    }

}
