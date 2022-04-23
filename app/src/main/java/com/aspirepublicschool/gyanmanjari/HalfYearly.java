package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HalfYearly#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HalfYearly extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    ArrayList<ResultModel> resultList=new ArrayList<>();
    ArrayList<FinalExamModel> finalExamModelArrayList=new ArrayList<>();
    FinalExamModel finalExamModel;
    Context ctx;
    int obtain=0;
    int total=0;
    double progress;
     String[] name;
    String PROGRESS;
    String test_type;
    ResultModel resultModel;
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HalfYearly.
     */
    // TODO: Rename and change types and number of parameters
    public static HalfYearly newInstance(String param1, String param2) {
        HalfYearly fragment = new HalfYearly();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                //final List<PieEntry> pieEntries = new ArrayList<>();
                LayoutInflater layoutInflater =LayoutInflater.from(ctx);
                final AlertDialog.Builder mBuilder =new AlertDialog.Builder(ctx);
                //  loadData();
                Common.progressDialogShow(ctx);
                View view = layoutInflater.inflate(R.layout.piechart_dialog,null);
               /* final BarChart barChart = view.findViewById(R.id.barChart);
                barChart.setDrawBarShadow(false);
                barChart.setDrawValueAboveBar(true);
                barChart.setMaxVisibleValueCount(50);
                barChart.setPinchZoom(false);
                barChart.setDrawGridBackground(true);
                final ArrayList<BarEntry> barEntries = new ArrayList<>();*/
                final ImageView imgcancel = view.findViewById(R.id.imgcancel);


                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);

                final String stu_id = mPrefs.getString("stu_id", "none");
                final String school_id = mPrefs.getString("sc_id","none");
                Log.v("XYZ",stu_id);
                Log.v("PQR",school_id);
                String URL = "https://www.aspirepublicschool.net/zocarro_mobile/school/ws/result.php";
                Log.v("URL",URL);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Common.progressDialogDismiss(ctx);
                            JSONArray array = new JSONArray(response);
                            // pieEntries.clear();
                            // resultModelList.clear();
                            //  pieEntries.clear();
                            resultList.clear();
                            label.clear();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject result = array.getJSONObject(i);


                                resultModel = new ResultModel( result.getString("stu_id"),
                                        result.getString("cid"),
                                        result.getString("t_id"),
                                        result.getString("type"),
                                        result.getString("subject"),
                                        result.getString("total"),
                                        result.getString("obtain"),
                                        result.getString("test_date"));
                                //  Log.v("VVVV", ""+test_type);

                                if (resultModel.getTest_type().equals("Class Test")){
                                    /*obtain_c = Integer.parseInt(resultModel.getObtain_marks());
                                    total_c = total_c+ Integer.parseInt(resultModel.getTotal_marks());
                                    progress_c = progress_c + obtain_c;*/
                                }
                                if (resultModel.getTest_type().equals("Half Term Exam")){
                                        resultList.add(resultModel);

                                  //  pieEntries.add(new PieEntry(Float.parseFloat(resultModel.getObtain_marks()),resultModel.getSubject()));
                                    /*pieEntries.add(new PieEntry(Float.parseFloat(resultModel.getObtain_marks()),resultModel.getSubject()));*/
                                   /* obtain_h = Integer.parseInt(resultModel.getObtain_marks());
                                    total_h = total_h+ Integer.parseInt(resultModel.getTotal_marks());
                                    progress_h = progress_h + obtain_h;*/
                                }
                                if (resultModel.getTest_type().equals("Final Exam")){

                                }
                               /* obtain = Integer.parseInt(resultModel.getObtain_marks());
                                total = total+ Integer.parseInt(resultModel.getTotal_marks());
                                progress = progress + obtain;*/
                            }

                          /* name=new String[resultList.size()];
                            for(int i=0;i<resultList.size();i++)
                            {
                                name[i]=resultList.get(i).getSubject();
                            }*/
                          for(int i=0;i<resultList.size();i++){
                              marks=Integer.parseInt(resultList.get(i).getObtain_marks());
                              subject=resultList.get(i).getSubject();
                             // barEntries.add(new BarEntry(i,marks));
                              label.add(subject);

                          }
                         /*   int growth_c=0;
                            int growth_h=0;
                            int growth_f=0;
                            growth_c = (progress_c*100)/total_c;
                            growth_h = (progress_h*100)/total_h;
                            growth_f = (progress_f*100)/total_f;
                            Log.v("LLLL",""+String.valueOf(growth_c));
                            Log.v("TOTAL",""+String.valueOf(total_c));
                            Log.v("PPPPP",""+test_type);*/

                           /* pieEntries.add(new PieEntry(growth_c,resultModelList.get(0).getTest_type()));
                            pieEntries.add(new PieEntry(growth_h,resultModelList.get(1).getTest_type()));
                            pieEntries.add(new PieEntry(growth_f,resultModelList.get(2).getTest_type()));*/
                            /*pieEntries.add(new PieEntry(Float.parseFloat(resultModelList.get(0).getObtain_marks()),resultModelList.get(0).getSubject()));
                            pieEntries.add(new PieEntry(growth_h,resultModelList.get(1).getTest_type()));
                            pieEntries.add(new PieEntry(growth_f,resultModelList.get(2).getTest_type()));*/
                            /*pieEntries.add(new PieEntry(10,resultModelList.get(0).getSubject()));
                            pieEntries.add(new PieEntry(10,resultModelList.get(1).getTest_type()));
                            pieEntries.add(new PieEntry(10,resultModelList.get(2).getTest_type()));
                            pieEntries.add(new PieEntry(10,resultModelList.get(0).getSubject()));
                            pieEntries.add(new PieEntry(10,resultModelList.get(1).getTest_type()));
                            pieEntries.add(new PieEntry(10,resultModelList.get(2).getTest_type()));*/
                         /*   Log.d("a@@@",barEntries.toString());
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
                            barChart.invalidate();*/
                           /* MyaxisValue myaxisValue = new MyaxisValue(name);
                            Log.d("###",""+name[0]);

                            xAxis.setValueFormatter(myaxisValue);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);*/

                           /* PieDataSet pieDataSet = new PieDataSet(pieEntries,"Growth");
                            pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                            pieChart.animateXY(5000,5000);
                            PieData pieData = new PieData(pieDataSet);

                            pieChart.setData(pieData);
                            Description description = new Description();
                            description.setText("Growth");
                            pieChart.setDescription(description);
                            pieChart.invalidate();*/

                            //resultModelList.clear();

                           /* int growth=0;
                            growth = (progress*100)/total;
                            Log.v("LLLL",String.valueOf(growth));
                            Log.v("TOTAL",String.valueOf(total));
                            Log.v("PPPPP",test_type);*/
                    /*textView_percentage.setText(growth+"% with");
                    if (growth>=50 && growth<=60){
                        textView_grad.setText("B Grad");
                    }
                    if (growth>=70 && growth<=80){
                        textView_grad.setText("B+ Grad");
                    }
                    if (growth>=90 && growth<=95){
                        textView_grad.setText("A Grad");
                    }
                    if (growth>=96 && growth<=100){
                        textView_grad.setText("A+ Grad");
                    }
                    if (growth>=30 && growth<=40){
                        textView_grad.setText("C+ Grad");
                    }
                    if (growth>=25 && growth<=29){
                        textView_grad.setText("C+ Grad");
                    }
                    if (growth>=20 && growth<=24){
                        textView_grad.setText("D Grad");
                    }
                    if (growth>=10 && growth<20){
                        textView_grad.setText("F Grad");
                    }*/
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




                //  pieEntries.add(new PieEntry(15,"two"));
                //   pieEntries.add(new PieEntry(20,"three"));



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
        String URL = Common.GetWebServiceURL()+"result.php";
        Log.v("HAlf",URL);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                        Common.progressDialogDismiss(ctx);
                        finalExamModelArrayList.clear();
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject result = array.getJSONObject(i);

                        resultModel = new ResultModel( result.getString("stu_id"),
                                result.getString("cid"),
                                result.getString("t_id"),
                                result.getString("type"),
                                result.getString("subject"),
                                result.getString("total"),
                                result.getString("obtain"),
                                result.getString("test_date"));

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
                Log.v("DDDD",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("stu_id",stu_id);
                params.put("sc_id",sc_id);
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
   /* public class MyaxisValue implements IAxisValueFormatter {
        private String[] mvalues;

        public MyaxisValue(String[] mvalues) {
            this.mvalues = mvalues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mvalues[(int)value];
        }
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
