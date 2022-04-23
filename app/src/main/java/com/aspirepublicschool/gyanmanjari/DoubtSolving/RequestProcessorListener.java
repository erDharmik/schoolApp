package com.aspirepublicschool.gyanmanjari.DoubtSolving;

import com.android.volley.VolleyError;

public interface RequestProcessorListener {

    void onSuccess(String response);

    void onLoading();

    void onError(VolleyError error);

    abstract class SimpleRequestProcessor implements RequestProcessorListener{
        @Override
        public void onError(VolleyError error) {
            //some error occurred
        }

    }
}
