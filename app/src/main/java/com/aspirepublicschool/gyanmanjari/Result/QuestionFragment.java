package com.aspirepublicschool.gyanmanjari.Result;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Test.DBHelper;
import com.aspirepublicschool.gyanmanjari.Test.TestQuestion;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuestionFragment extends Fragment {
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
    TextView txtcorrect,txtreadmore;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questiondata_fragment, container, false);
        val = getArguments().getInt("someInt", 0);
        question1_text_view = view.findViewById(R.id.question1_text_view);
        txtreadmore = view.findViewById(R.id.txtreadmore);
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
        txtcorrect = view.findViewById(R.id.txtcorrect);
        try {
            String question = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + "<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>" +
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" + ViewTestAnswer.testGivenAnswers.get(val).getQuestion() + "</body></html>";
            question1_text_view.getSettings().setJavaScriptEnabled(true);
            question1_text_view.loadDataWithBaseURL(null, question, "text/html", "utf-8", null);
            question1_text_view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        }catch (Exception e)
        {

        }


        String opa = "<html><head> <meta charset=\"utf-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + "<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>" +
                "<style>\n" +
                "pre { white-space:normal !important;}" + "</style></head><body>" + ViewTestAnswer.testGivenAnswers.get(val).getA() + "</body></html>";
        String opb = "<html><head> <meta charset=\"utf-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + "<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>" +
                "<style>\n" +
                "pre { white-space:normal !important;}" + "</style></head><body>" + ViewTestAnswer.testGivenAnswers.get(val).getB() + "</body></html>";
        String opc = "<html><head> <meta charset=\"utf-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + "<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>" +
                "<style>\n" +
                "pre { white-space:normal !important;}" + "</style></head><body>" + ViewTestAnswer.testGivenAnswers.get(val).getC() + "</body></html>";
        String opd = "<html><head> <meta charset=\"utf-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + "<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>" +
                "<style>\n" +
                "pre { white-space:normal !important;}" + "</style></head><body>" + ViewTestAnswer.testGivenAnswers.get(val).getD() + "</body></html>";
        imga.getSettings().setJavaScriptEnabled(true);
        imga.loadDataWithBaseURL(null, opa, "text/html", "utf-8", null);
        imgb.getSettings().setJavaScriptEnabled(true);
        imgb.loadDataWithBaseURL(null, opb, "text/html", "utf-8", null);
        imgc.getSettings().setJavaScriptEnabled(true);
        imgc.loadDataWithBaseURL(null, opc, "text/html", "utf-8", null);
        imgd.getSettings().setJavaScriptEnabled(true);
        imgd.loadDataWithBaseURL(null, opd, "text/html", "utf-8", null);

        imga.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        imgb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        imgc.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        imgd.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        txtreadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = inflater.inflate(R.layout.activity_webview_dialog, null);
                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Loading Data...");
                progressDialog.setCancelable(false);
                WebView photoView = mView.findViewById(R.id.webview);
                String question = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + "<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>" +
                        "<style>\n" +
                        "pre { white-space:normal !important;} iframe{width:300px !important; height:300px !important;}" + "</style></head><body>" + ViewTestAnswer.testGivenAnswers.get(val).getSolution()+ "</body></html>";
                photoView.requestFocus();
                photoView.getSettings().setJavaScriptEnabled(true);
                photoView.loadDataWithBaseURL(null, question, "text/html", "utf-8", null);
                photoView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                photoView.setWebChromeClient(new WebChromeClient() {
                    public void onProgressChanged(WebView view, int progress) {
                        if (progress < 100) {
                            progressDialog.show();
                        }
                        if (progress == 100) {
                            progressDialog.dismiss();
                        }
                    }
                });
                //photoView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                mBuilder.setView(mView);
                final AlertDialog mDialog = mBuilder.create();
                ImageView cancelview=mView.findViewById(R.id.imgcancel);
                cancelview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDialog.show();

            }
        });
        if (ViewTestAnswer.testGivenAnswers.get(val).getAns().equals("A")) {
            a.setChecked(true);
        } else if (ViewTestAnswer.testGivenAnswers.get(val).getAns().equals("B")) {
            b.setChecked(true);
        } else if (ViewTestAnswer.testGivenAnswers.get(val).getAns().equals("C")) {
            c.setChecked(true);
        } else if (ViewTestAnswer.testGivenAnswers.get(val).getAns().equals("D")) {
            d.setChecked(true);
        } else if (ViewTestAnswer.testGivenAnswers.get(val).getAns().equals("Not Set")) {
            e.setChecked(true);
        }
        if(ViewTestAnswer.testGivenAnswers.get(val).getAns().equals("Grace Marks")){
            txtcorrect.setText("Consider for grace");
        } else{
            txtcorrect.setText(ViewTestAnswer.testGivenAnswers.get(val).getCorrect());
        }
        //txtcorrect.setText(ViewTestAnswer.testGivenAnswers.get(val).getCorrect());


        currentval = val;
        return view;
    }

    public static QuestionFragment addfrag(int val) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", val);
        fragment.setArguments(args);
        return fragment;
    }

    private void SendRequest() {
        // Common.progressDialogShow(this);
        //String Webserviceurl=Common.GetWebServiceURL()+"testQuestions.php";
        String Webserviceurl = "https://www.zocarro.com/zocarro_mobile_app/ws/testquestion.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String class_id = mPrefs.getString("class_id", "none");
        final String sc_id = mPrefs.getString("sc_id", "none");
        final String stu_id = mPrefs.getString("stu_id", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

                    Log.d("aaa", response);
                    testQuestions.clear();
                    JSONArray array = new JSONArray(response);
                    int total = array.getJSONObject(0).getInt("total");
                    if (total == 0) {
                        Toast.makeText(getContext(), "No Question", Toast.LENGTH_LONG).show();
                    } else {
                        /*txttotal.setText(""+total);
                        // txtquestion.setText(""+ansques);
                        txttotalunanswered.setText("/"+total);
                        txtunanswered.setText(""+unanswered);
                        mDataset=new String[array.length()-1];*/
                        for (int i = 1; i < array.length(); i++) {
                            /*  {
                            "qid": "QST1",
                                "question": "Test Question ?",
                                "a": "A",
                                "b": "B",
                                "c": "C",
                                "d": "D"
                        },*/
                            JSONObject object = array.getJSONObject(i);
                            testQuestions.add(new TestQuestion(object.getString("qid"), object.getString("question"), object.getString("a"), object.getString("b"),
                                    object.getString("c"), object.getString("d"), "Not Set", object.getString("q_img"), object.getString("a_img"), object.getString("b_img")
                                    , object.getString("c_img"), object.getString("d_img"), false, "TST20", object.getString("subject")));
                        }
                        //Common.progressDialogDismiss(this);
                        /*TestTimeAdapter adapter=new TestTimeAdapter(this,testQuestions,mDataset);
                        recteststart.setAdapter(adapter);*/

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "" + error, Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("sc_id", "SCIDN20");
                data.put("cid", "CIDN108");
                data.put("tst_id", "TST20");
                Log.d("data", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(3000, 3, 1));
        Volley.newRequestQueue(getContext()).add(request);
    }
}