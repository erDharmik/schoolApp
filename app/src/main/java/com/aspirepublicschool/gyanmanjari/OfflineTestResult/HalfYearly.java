package com.aspirepublicschool.gyanmanjari.OfflineTestResult;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.FinalExamAdapter;
import com.aspirepublicschool.gyanmanjari.FinalExamModel;
import com.aspirepublicschool.gyanmanjari.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HalfYearly extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<OfflineResultModel> resultList=new ArrayList<>();
    ArrayList<FinalExamModel> finalExamModelArrayList=new ArrayList<>();
    FinalExamModel finalExamModel;
    Context ctx;
    int obtain=0;
    int total=0;
    double progress;
    OfflineResultModel resultModel;
    TextView textView_obtain,textView_percentage;
    View v;
    Button btn_progress;
    ArrayList<String> label=new ArrayList<>();
    int marks;
    String subject;
    private OnFragmentInteractionListener mListener;

    public HalfYearly() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_final_exam,container,false);
        recyclerView = v.findViewById(R.id.recsubjects);
        textView_obtain = v.findViewById(R.id.txt_obtainmark);
        textView_percentage = v.findViewById(R.id.txt_percentage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn_progress= v.findViewById(R.id.btn_progress);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        ctx=getContext();
        resultList.clear();
        loadData();
        btn_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<PieEntry> pieEntries = new ArrayList<>();
                LayoutInflater layoutInflater =LayoutInflater.from(ctx);
                final AlertDialog.Builder mBuilder =new AlertDialog.Builder(ctx);
                //  loadData();
                Common.progressDialogShow(ctx);
                View view = layoutInflater.inflate(R.layout.piechart_dialog,null);
                final BarChart barChart = view.findViewById(R.id.barChart);
                barChart.setDrawBarShadow(false);
                barChart.setDrawValueAboveBar(true);
                barChart.setMaxVisibleValueCount(50);
                barChart.setPinchZoom(false);
                barChart.setDrawGridBackground(true);
                final ArrayList<BarEntry> barEntries = new ArrayList<>();
                final ImageView imgcancel = view.findViewById(R.id.imgcancel);


                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);

                final String stu_id = mPrefs.getString("stu_id", "none");
                final String school_id = mPrefs.getString("sc_id","none");
                Log.v("XYZ",stu_id);
                Log.v("PQR",school_id);
                String URL = Common.GetWebServiceURL()+"resultavg.php";
                Log.v("URL",URL);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Common.progressDialogDismiss(ctx);
                            JSONArray array = new JSONArray(response);

                            resultList.clear();
                            label.clear();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject result = array.getJSONObject(i);


                                resultModel = new OfflineResultModel( result.getString("stu_id"),
                                        result.getString("cid"),
                                        result.getString("t_id"),
                                        result.getString("type"),
                                        result.getString("subject"),
                                        result.getString("total"),
                                        result.getString("obtain"),
                                        result.getString("test_date"),result.getString("label"));
                                //  Log.v("VVVV", ""+test_type);

                                if (resultModel.getTest_type().equals("Class Test")){

                                }
                                if (resultModel.getTest_type().equals("Half Term Exam")){
                                    resultList.add(resultModel);

                                }
                                if (resultModel.getTest_type().equals("Final Exam")){

                                }

                            }

                            for(int i=0;i<resultList.size();i++){
                                marks=Integer.parseInt(resultList.get(i).getObtain_marks());
                                subject=resultList.get(i).getSubject();
                                barEntries.add(new BarEntry(i,marks));
                                label.add(subject);

                            }

                            Log.d("a@@@",barEntries.toString());
                            BarDataSet barDataSet = new BarDataSet(barEntries,"Marks Subject Wise");
                            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            BarData data = new BarData(barDataSet);
                            data.setBarWidth(0.9f);

                            barChart.setData(data);

                            XAxis xAxis = barChart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(label));
                            xAxis.setPosition(XAxis.XAxisPosition.TOP);
                            xAxis.setDrawGridLines(false);
                            xAxis.setDrawAxisLine(false);
                            xAxis.setGranularity(1f);
                            xAxis.setLabelCount(label.size());
                            barChart.animateY(2000);
                            barChart.invalidate();

                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("DDDD",error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("stu_id",stu_id);
                        params.put("sc_id",school_id);
                        return params;
                    }
                };
                Volley.newRequestQueue(ctx).add(stringRequest);

                mBuilder.setView(view);
                final AlertDialog alertDialog =mBuilder.create();
                alertDialog.show();
                imgcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }

        });
        return v;
    }

    private void loadData() {
        //Common.progressDialogShow(ctx);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stu_id = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id","none");
        Log.v("XYZ",stu_id);
        Log.v("PQR",sc_id);
        String URL = Common.GetWebServiceURL()+"resultavg.php";
        Log.v("HAlf",URL);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("HalfYearly",response);
                    Common.progressDialogDismiss(ctx);
                    finalExamModelArrayList.clear();
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject result = array.getJSONObject(i);

                        resultModel = new OfflineResultModel( result.getString("stu_id"),
                                result.getString("cid"),
                                result.getString("t_id"),
                                result.getString("type"),
                                result.getString("subject"),
                                result.getString("total"),
                                result.getString("obtain"),
                                result.getString("test_date"),result.getString("label"));

                        finalExamModel = new FinalExamModel( result.getString("subject"),
                                result.getString("obtain"),
                                result.getString("total") );
                        if(resultModel.getTest_type().equals("Half Term Exam"))
                        { resultList.add(resultModel);
                            finalExamModelArrayList.add(finalExamModel);
                            obtain = obtain+Integer.parseInt(result.getString("obtain"));

                            total = total+Integer.parseInt(result.getString("total"));
                            progress = (obtain*100)/total;
                            Log.v("PPPP",String.valueOf(progress));

                        }

                        textView_obtain.setText(String.valueOf(obtain)+"/"+String.valueOf(total));
                        textView_percentage.setText(String.valueOf(progress)+"%");

                    }

                    FinalExamAdapter adapter = new FinalExamAdapter(ctx, finalExamModelArrayList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.getAdapter().notifyDataSetChanged();
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(ctx);
                Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();
                Log.v("DDDD",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("stu_id",stu_id);
                params.put("sc_id",sc_id);
                Log.v("DDDD",params.toString());
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public class MyaxisValue implements IAxisValueFormatter {
        private String[] mvalues;

        public MyaxisValue(String[] mvalues) {
            this.mvalues = mvalues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mvalues[(int)value];
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
