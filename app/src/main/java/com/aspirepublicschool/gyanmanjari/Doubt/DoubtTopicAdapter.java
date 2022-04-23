package com.aspirepublicschool.gyanmanjari.Doubt;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class DoubtTopicAdapter extends RecyclerView.Adapter
{
    Context ctx;
    ArrayList<SubjectDoubt> subjectDoubts;

    public DoubtTopicAdapter(Context ctx, ArrayList<SubjectDoubt> subjectDoubts)
    {
        this.ctx = ctx;
        this.subjectDoubts = subjectDoubts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_subject_doubt, null);
       MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final SubjectDoubt subjectDoubt = subjectDoubts.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        container.txtdate.setText(subjectDoubt.getDate());
        container.txtsub.setText(subjectDoubt.getSub());
        container.txttitle.setText(subjectDoubt.getChapter());
        container.txttopic.setText(subjectDoubt.getTopic());
        container.txtreadmore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LayoutInflater inflater = LayoutInflater.from(ctx);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                final ProgressDialog progressDialog = new ProgressDialog(ctx);
                progressDialog.setMessage("Loading Data...");
                progressDialog.setCancelable(false);
                View mView = inflater.inflate(R.layout.activity_webview_dialog, null);
                WebView photoView = mView.findViewById(R.id.webview);
                String question = "<html><head> <meta charset=\"utf-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                         "<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>" +
                        "<style>\n" +
                        "pre { white-space:normal !important;} iframe{width:300px !important; height:300px !important;}" + "</style></head><body>" + subjectDoubt.getData()+ "</body></html>";
                photoView.requestFocus();
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
                photoView.getSettings().setJavaScriptEnabled(true);
                photoView.loadDataWithBaseURL(null, question, "text/html", "utf-8", null);
                photoView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                mBuilder.setView(mView);
                final AlertDialog mDialog = mBuilder.create();
                ImageView cancelview=mView.findViewById(R.id.imgcancel);
                cancelview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        mDialog.dismiss();
                    }
                });
                mDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return subjectDoubts.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        TextView txttopic,txtdate,txtsub,txtreadmore,txttitle;
        public MyWidgetContainer(View itemView) {
            super(itemView);
            txtsub=itemView.findViewById(R.id.txtsub);
            txtdate=itemView.findViewById(R.id.txtdate);
            txttopic=itemView.findViewById(R.id.txttopic);
            txtreadmore=itemView.findViewById(R.id.txtreadmore);
            txttitle=itemView.findViewById(R.id.txttitle);
        }
    }
}
