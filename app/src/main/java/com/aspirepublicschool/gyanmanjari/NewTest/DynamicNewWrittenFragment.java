package com.aspirepublicschool.gyanmanjari.NewTest;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.aspirepublicschool.gyanmanjari.NewTest.WrittenTestFragment.testQuestionArrayList;

public class DynamicNewWrittenFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    View view;
    int val;
    private int currentval;
    WebView question1_text_view;
    EditText answerEditText;
    Button btnUnMark, btnSave, btnUpdate;
    private DBHelperNewWritten mydb;
    ArrayList<TestWrittenQuestion> testQuestions;
    ArrayList<FetchAnswersModel> fetchAnswersModelArrayList = new ArrayList<>();
    //spinner
    Spinner spin;
    String[] signs = {"+", "-","/","*","%","=","≠","≈",">","<","≥","≤","±","×"
            ,"mod","∠","deg","⊥","∥","≅","~","Δ","π","rad","grad","∝","≪","≫"
            ,"∑","e","γ","φ","μ","Ω","Ψ","f(x)","g(x)"};

    public DynamicNewWrittenFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dynamic_new_written, container, false);
        val = getArguments().getInt("someInt", 0);
        answerEditText = view.findViewById(R.id.answerEditText);
        btnUnMark = view.findViewById(R.id.btnUnMark);
        question1_text_view = view.findViewById(R.id.question1_text_view);
        btnSave = view.findViewById(R.id.btnSave);
        btnUpdate = view.findViewById(R.id.btnUpdate);

        fetchAnswers(testQuestionArrayList.get(val).getTst_id());

        //spinner
        spin = (Spinner) view.findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);


        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,signs);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        if (WrittenTestFragment.dataflags == true) {

            String question = "<html><head> <meta charset=\"utf-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async>" +
                    "</script>"+
                    "<style>\n" +
                    "pre { white-space:normal !important;}" + "</style></head><body>" +
                    testQuestionArrayList.get(val).getQuestion() + "</body></html>";

            question1_text_view.getSettings().setJavaScriptEnabled(true);
            question1_text_view.getSettings().setDomStorageEnabled(true);
            question1_text_view.setHorizontalScrollBarEnabled(false);
            question1_text_view.setVerticalScrollBarEnabled(false);
            question1_text_view.loadDataWithBaseURL(null, question, "text/html", "utf-8", null);
            question1_text_view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        else {
            if (testQuestionArrayList.get(val).getQ_img().equals("Not Set")) {

                String question = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        +"<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>"+
                        "<style>\n" +
                        "pre { white-space:normal !important;}" + "</style></head><body>" +
                        testQuestionArrayList.get(val).getQuestion() + "</body></html>";
                question1_text_view.loadDataWithBaseURL(null, question, "text/html",
                        "utf-8", null);
                question1_text_view.getSettings().setJavaScriptEnabled(true);
                question1_text_view.getSettings().setDomStorageEnabled(true);
                question1_text_view.setHorizontalScrollBarEnabled(false);
                question1_text_view.setVerticalScrollBarEnabled(false);
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAnswer(testQuestionArrayList.get(val).getTst_id(),
                        testQuestionArrayList.get(val).getQ_id(),true);

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnswer(testQuestionArrayList.get(val).getTst_id(),
                        testQuestionArrayList.get(val).getQ_id(),true);

            }
        });


        btnUnMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: ");
                deleteAnswer(testQuestionArrayList.get(val).getTst_id(),
                        testQuestionArrayList.get(val).getQ_id());
            }
        });
        currentval = val;
        return view;
    }

    private void fetchAnswers(final String tst_id) {
        String Webserviceurl= Common.GetWebServiceURL()+"fetch_written_answers.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        final String stu_id = mPrefs.getString("stu_id","none");
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("AAa",response);
                try {
                    JSONArray array=new JSONArray(response);
                    int total=array.getJSONObject(0).getInt("total");
                    if(total==0) {

                    }
                    else {
                        for (int i = 1; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            fetchAnswersModelArrayList.add(new FetchAnswersModel(
                                    object.getString("qid"),
                                    object.getString("sign"),
                                    object.getString("ans")));

                            if(fetchAnswersModelArrayList.get(i-1).getQid().equals(testQuestionArrayList.get(val).getQ_id())) {
                                String answer = fetchAnswersModelArrayList.get(i - 1).getAnswer();
                                String sign = fetchAnswersModelArrayList.get(i - 1).getSign();
                                answerEditText.setText(answer);
                                spin.setSelection(((ArrayAdapter<String>) spin.getAdapter()).getPosition(sign));
                            }
                        }
//                        String qid = fetchAnswersModelArrayList.get(val).getQid();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id", sc_id);
                data.put("cid", class_id);
                data.put("stu_id", stu_id);
                data.put("tst_id", tst_id);
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(getContext()).add(request);

    }

    private void updateAnswer(final String tst_id, final String q_id, final boolean mark) {

        String Webserviceurl= Common.GetWebServiceURL()+"updateOneWordAnswer.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String sc_id = preferences.getString("sc_id","none").toUpperCase();
        final String stu_id = preferences.getString("stu_id","none").toUpperCase();
        final String class_id = preferences.getString("class_id","none").toUpperCase();
        final StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("aaa",response );
                try {
                    JSONArray array = new JSONArray(response);
                    String message = array.getJSONObject(0).getString("message");
                    if(message.equals("Success")) {
                        Toast.makeText(getActivity(), "Answer Updated", Toast.LENGTH_SHORT).show();
                        WrittenTestFragment.unanswered = WrittenTestFragment.unanswered - 1;
                        WrittenTestFragment.wrttabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                        WrittenTestFragment.wrttabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        testQuestionArrayList.get(val).setMark(false);

                    }


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
                String sign = spin.getSelectedItem(). toString();
                data.put("sign", sign);
                String answer = answerEditText.getText().toString();
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
                Log.d("dsdfghata", data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);

    }

    public static DynamicNewWrittenFragment addfrag(int val) {
        DynamicNewWrittenFragment fragment = new DynamicNewWrittenFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", val);
        fragment.setArguments(args);
        return fragment;
    }


    private void saveAnswer(final String tst_id, final String q_id, final Boolean mark) {
        String Webserviceurl= Common.GetWebServiceURL()+"answersubmittestnewWritten.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String sc_id = preferences.getString("sc_id","none").toUpperCase();
        final String stu_id = preferences.getString("stu_id","none").toUpperCase();
        final String class_id = preferences.getString("class_id","none").toUpperCase();
        final StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("aaa",response );
                try {
                    JSONArray array = new JSONArray(response);
                    String message = array.getJSONObject(1).getString("message");
                    if(message.equals("reached max limit"))
                    {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                    if(message.equals("Success")) {
                        WrittenTestFragment.unanswered = WrittenTestFragment.unanswered - 1;
                        WrittenTestFragment.wrttabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.GREEN);
                        Toast.makeText(getActivity(), "Answer Saved", Toast.LENGTH_SHORT).show();
                        WrittenTestFragment.wrttabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        testQuestionArrayList.get(val).setMark(false);
                        btnSave.setVisibility(View.GONE);
                        btnUpdate.setVisibility(View.VISIBLE);
                    }


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
                String sign = spin.getSelectedItem(). toString();
                data.put("sign", sign);
                String answer = answerEditText.getText().toString();
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
                Log.d("dsdfghata", data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
    }

    private void deleteAnswer(final String tst_id, final String q_id) {
        String Webserviceurl= Common.GetWebServiceURL()+"deleteWrittenAnswer.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String sc_id = preferences.getString("sc_id","none").toUpperCase();
        final String stu_id = preferences.getString("stu_id","none").toUpperCase();
        final String class_id = preferences.getString("class_id","none").toUpperCase();

        final StringRequest request=new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("aaa",response );
                try {
                    JSONArray array = new JSONArray(response);
                    String message = array.getJSONObject(0).getString("message");
                    if(message.equals("Success"))
                    {

                        WrittenTestFragment.unanswered = WrittenTestFragment.unanswered - 1;
                        WrittenTestFragment.wrttabLayout.getTabAt(val).setText("");
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.WHITE);
                        Toast.makeText(getActivity(), "Answer Unmarked", Toast.LENGTH_SHORT).show();
                        WrittenTestFragment.wrttabLayout.getTabAt(val).setCustomView(tv).setText("" + (val + 1));
                        testQuestionArrayList.get(val).setMark(false);
                        btnSave.setVisibility(View.VISIBLE);
                        btnUpdate.setVisibility(View.GONE);
                        answerEditText.setText("");
                        Log.d("TAG", "onResponsemessage: "+message);
//                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
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
                String sign = spin.getSelectedItem(). toString();
                data.put("sign", sign);
                String answer = answerEditText.getText().toString();
                data.put("answer", answer);
                Log.d("dsdfghabnta", data.toString());
                return data;
            }
        };

        Volley.newRequestQueue(getContext()).add(request);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}