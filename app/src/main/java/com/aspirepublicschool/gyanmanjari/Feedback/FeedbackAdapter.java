package com.aspirepublicschool.gyanmanjari.Feedback;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;
import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyWidgetContainer> {

    Context ctx;
    static List<QuestionList> questionListList = new ArrayList<>();
    static List<ansList> ansListArrayList = new ArrayList<>();
    ArrayList<String> ansLists = new ArrayList<>();
    ansList ansList;
    String[] mDataset;

    String answer;

    public FeedbackAdapter(Context ctx, List<QuestionList> questionLists,String[] mDataset) {
        this.ctx = ctx;
        this.questionListList = questionLists;
        this.mDataset = mDataset;
    }

    @NonNull
    @Override
    public MyWidgetContainer onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.feedback_question_row, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyWidgetContainer myWidgetContainer, int i) {
        QuestionList list = questionListList.get(i);

        myWidgetContainer.question.setText(list.getQuestion());
        List<String> ans = new ArrayList<>();
        ans.add("not set");
        ans.add("Excellent");
        ans.add("Good");
        ans.add("Fair");
        ans.add("Poor");
        Log.v("PPP", "" + ans);


        myWidgetContainer.answer.setAdapter(new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_dropdown_item, ans));

        myWidgetContainer.answer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                answer = myWidgetContainer.answer.getItemAtPosition(myWidgetContainer.answer.getSelectedItemPosition()).toString();
                ansList = new ansList(answer);
                mDataset[position]=answer;
                questionListList.get(myWidgetContainer.getAdapterPosition()).setAnswer(mDataset[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return questionListList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyWidgetContainer extends RecyclerView.ViewHolder {
        TextView question;
        Spinner answer;
        ImageView p_img;

        public MyWidgetContainer(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.ans_spinner);
        }
    }
}
