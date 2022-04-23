package com.aspirepublicschool.gyanmanjari.Test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TestTimeAdapter extends RecyclerView.Adapter {
    Context ctx;
    static ArrayList<TestQuestion> testArrayList;
    String[] mDataset;
    Bitmap a_img, b_img, c_img, d_img;
    int mCheckedId = R.id.e;
    boolean isChecking = true;

    public TestTimeAdapter(Context ctx, ArrayList<TestQuestion> testArrayList, String[] mDataset) {
        this.ctx = ctx;
        this.testArrayList = testArrayList;
        this.mDataset = mDataset;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.actvity_question_row, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final TestQuestion test = testArrayList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        Common.progressDialogDismiss(ctx);
        if (test.getQ_img().equals("noimg.jpg")) {
            container.imgquestion.setVisibility(View.GONE);
            container.question1_text_view.setText(test.getQuestion());
        } else {
            container.question1_text_view.setText(test.getQuestion());
            container.imgquestion.setVisibility(View.VISIBLE);
             String URL_QUES =Common.GetBaseImageURL()+ "test/question/" + test.getQ_img();
            //String URL_QUES = "http://www.zocarro.net/zocarro/image/" + "test/question/" + test.getQ_img();
            Glide.with(ctx).load(URL_QUES).into(container.imgquestion);
        }

        container.firstanswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    container.secondanswer.clearCheck();
                    mCheckedId = checkedId;
                }
                isChecking = true;
                if (mCheckedId == R.id.a) {

                    if (testArrayList.get(position).getSelected().equals("A")) {
                        //TestTine.txtquestion.setText("" + TestTine.ansques);
                        if (testArrayList.get(position).isMark() == true) {
                            container.card_view_question1.setBackgroundColor(Color.parseColor("#8510d8"));
                        } else {
                            container.card_view_question1.setBackgroundColor(Color.GREEN);
                        }

                    } else if (testArrayList.get(position).getSelected().equals("Not Set")) {
                        TestTine.ansques = TestTine.ansques + 1;
                        //TestTine.txtquestion.setText("" + TestTine.ansques);
                        if (testArrayList.get(position).isMark() == true) {
                            container.card_view_question1.setBackgroundColor(Color.parseColor("#8510d8"));
                        } else {
                            container.card_view_question1.setBackgroundColor(Color.GREEN);
                        }


                    } else {
                        TestTine.ansques = TestTine.ansques - 1;
                        TestTine.ansques = TestTine.ansques + 1;
                        //TestTine.txtquestion.setText("" + TestTine.ansques);
                        if (testArrayList.get(position).isMark() == true) {
                            container.card_view_question1.setBackgroundColor(Color.parseColor("#8510d8"));
                        } else {
                            container.card_view_question1.setBackgroundColor(Color.GREEN);
                        }


                    }
                    mDataset[position] = "a";
                    testArrayList.get(position).setSelected("A");
                }
                if (mCheckedId == R.id.b) {

                    if (testArrayList.get(position).getSelected().equals("B")) {
                      //  TestTine.txtquestion.setText("" + TestTine.ansques);
                        if (testArrayList.get(position).isMark() == true) {
                            container.card_view_question1.setBackgroundColor(Color.parseColor("#8510d8"));
                        } else {
                            container.card_view_question1.setBackgroundColor(Color.GREEN);
                        }

                    } else if (testArrayList.get(position).getSelected().equals("Not Set")) {
                        TestTine.ansques = TestTine.ansques + 1;
                       // TestTine.txtquestion.setText("" + TestTine.ansques);
                        if (testArrayList.get(position).isMark() == true) {
                            container.card_view_question1.setBackgroundColor(Color.parseColor("#8510d8"));
                        } else {
                            container.card_view_question1.setBackgroundColor(Color.GREEN);
                        }


                    } else {
                        TestTine.ansques = TestTine.ansques - 1;
                        TestTine.ansques = TestTine.ansques + 1;
                       // TestTine.txtquestion.setText("" + TestTine.ansques);
                        if (testArrayList.get(position).isMark() == true) {
                            container.card_view_question1.setBackgroundColor(Color.parseColor("#8510d8"));
                        } else {
                            container.card_view_question1.setBackgroundColor(Color.GREEN);
                        }


                    }
                    mDataset[position] = "b";
                    testArrayList.get(position).setSelected("B");
                }
                if (mCheckedId == R.id.c) {

                    if (testArrayList.get(position).getSelected().equals("C")) {
                        //TestTine.txtquestion.setText("" + TestTine.ansques);
                        if (testArrayList.get(position).isMark() == true) {
                            container.card_view_question1.setBackgroundColor(Color.parseColor("#8510d8"));
                        } else {
                            container.card_view_question1.setBackgroundColor(Color.GREEN);
                        }

                    } else if (testArrayList.get(position).getSelected().equals("Not Set")) {
                        TestTine.ansques = TestTine.ansques + 1;
                        //TestTine.txtquestion.setText("" + TestTine.ansques);
                        if (testArrayList.get(position).isMark() == true) {
                            container.card_view_question1.setBackgroundColor(Color.parseColor("#8510d8"));
                        } else {
                            container.card_view_question1.setBackgroundColor(Color.GREEN);
                        }


                    } else {
                        TestTine.ansques = TestTine.ansques - 1;
                        TestTine.ansques = TestTine.ansques + 1;
                       // TestTine.txtquestion.setText("" + TestTine.ansques);
                        if (testArrayList.get(position).isMark() == true) {
                            container.card_view_question1.setBackgroundColor(Color.parseColor("#8510d8"));
                        } else {
                            container.card_view_question1.setBackgroundColor(Color.GREEN);
                        }


                    }
                    mDataset[position] = "c";
                    testArrayList.get(position).setSelected("C");
                }

            }
        });

        container.secondanswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    container.firstanswer.clearCheck();
                    mCheckedId = checkedId;
                }
                isChecking = true;
                if (mCheckedId == R.id.d) {

                    if (testArrayList.get(position).getSelected().equals("D")) {
                      //  TestTine.txtquestion.setText("" + TestTine.ansques);
                        if (testArrayList.get(position).isMark() == true) {
                            container.card_view_question1.setBackgroundColor(Color.parseColor("#8510d8"));
                        } else {
                            container.card_view_question1.setBackgroundColor(Color.GREEN);
                        }

                    } else if (testArrayList.get(position).getSelected().equals("Not Set")) {
                        TestTine.ansques = TestTine.ansques + 1;
                       // TestTine.txtquestion.setText("" + TestTine.ansques);
                        if (testArrayList.get(position).isMark() == true) {
                            container.card_view_question1.setBackgroundColor(Color.parseColor("#8510d8"));
                        } else {
                            container.card_view_question1.setBackgroundColor(Color.GREEN);
                        }


                    } else {
                        TestTine.ansques = TestTine.ansques - 1;
                        TestTine.ansques = TestTine.ansques + 1;
                       // TestTine.txtquestion.setText("" + TestTine.ansques);
                        if (testArrayList.get(position).isMark() == true) {
                            container.card_view_question1.setBackgroundColor(Color.parseColor("#8510d8"));
                        } else {
                            container.card_view_question1.setBackgroundColor(Color.GREEN);
                        }


                    }
                    mDataset[position] = "d";
                    testArrayList.get(position).setSelected("D");
                }
                if (mCheckedId == R.id.e) {

                    mDataset[position] = "e";
                    testArrayList.get(position).setSelected("Not Set");
                   // TestTine.ansques = TestTine.ansques - 1;
                    if (testArrayList.get(position).isMark() == true) {
                        container.card_view_question1.setBackgroundColor(Color.parseColor("#8510d8"));
                    } else {
                        container.card_view_question1.setBackgroundColor(Color.WHITE);
                    }


                }
            }
        });


        container.btnmark.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (testArrayList.get(position).isMark() == false) {
                    testArrayList.get(position).setMark(true);
                    container.card_view_question1.setBackgroundColor(Color.parseColor("#8510d8"));
                    TestTine.unanswered = TestTine.unanswered + 1;
                    TestTine.txtunanswered.setText("" + TestTine.unanswered);
                }
                /*   else {

                 *//* if(TestTine.unanswered>0) {
                        TestTine.unanswered = TestTine.unanswered - 1;
                        TestTine.txtunanswered.setText("" + TestTine.unanswered);
                    }*//*
                    Toast.makeText(ctx, "This Question is  unmarked for Review", Toast.LENGTH_LONG).show();
                }*/
            }
        });
        container.btnsave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if (testArrayList.get(position).isMark() == true) {
                    if (TestTine.unanswered != 0) {
                        if (testArrayList.get(position).getSelected().equals("Not Set")) {
                            container.card_view_question1.setBackgroundColor(Color.WHITE);
                            testArrayList.get(position).setMark(false);
                            TestTine.unanswered = TestTine.unanswered - 1;
                            TestTine.txtunanswered.setText("" + TestTine.unanswered);
                        } else {
                            container.card_view_question1.setBackgroundColor(Color.GREEN);
                            testArrayList.get(position).setMark(false);
                            TestTine.unanswered = TestTine.unanswered - 1;
                            TestTine.txtunanswered.setText("" + TestTine.unanswered);

                        }
                    } else {
                        Toast.makeText(ctx, "This Question is unmarked for Review", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ctx, "This Question is  unmarked for Review", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void showType(View view) {
        if (mCheckedId == R.id.a) {
            Toast.makeText(ctx, "type1", Toast.LENGTH_SHORT).show();
        } else if (mCheckedId == R.id.b) {
            Toast.makeText(ctx, "type2", Toast.LENGTH_SHORT).show();
        } else if (mCheckedId == R.id.c) {
            Toast.makeText(ctx, "type3", Toast.LENGTH_SHORT).show();
        } else if (mCheckedId == R.id.d) {
            Toast.makeText(ctx, "type4", Toast.LENGTH_SHORT).show();
        } else if (mCheckedId == R.id.e) {
            Toast.makeText(ctx, "type5", Toast.LENGTH_SHORT).show();
        }
    }


    /*  if (test.getA_img().equals("noimg.jpg")) {*/



/*

            if(container.a.isChecked())
            {
                Toast.makeText(ctx, "a", Toast.LENGTH_LONG).show();
                container.b.setChecked(false);
                container.c.setChecked(false);
                container.d.setChecked(false);
                container.e.setChecked(false);
                if (testArrayList.get(position).getSelected().equals("A")) {
                    TestTine.txtquestion.setText("" + TestTine.ansques);
                    container.card_view_question1.setBackgroundColor(Color.GREEN);
                } else if (testArrayList.get(position).getSelected().equals("Not Set")) {
                    TestTine.ansques = TestTine.ansques + 1;
                    TestTine.txtquestion.setText("" + TestTine.ansques);
                    container.card_view_question1.setBackgroundColor(Color.GREEN);

                } else {
                    TestTine.ansques=TestTine.ansques-1;
                    TestTine.ansques = TestTine.ansques + 1;
                    TestTine.txtquestion.setText("" + TestTine.ansques);
                    container.card_view_question1.setBackgroundColor(Color.GREEN);

                }
                mDataset[position] = "a";
                testArrayList.get(position).setSelected("A");

            }
            if(container.b.isChecked())
            {
                container.a.setChecked(false);
                container.c.setChecked(false);
                container.d.setChecked(false);
                container.e.setChecked(false);
                if (testArrayList.get(position).getSelected().equals("B")) {
                    TestTine.txtquestion.setText("" + TestTine.ansques);
                    container.card_view_question1.setBackgroundColor(Color.GREEN);
                }
                else if (testArrayList.get(position).getSelected().equals("Not Set")) {
                    TestTine.ansques = TestTine.ansques + 1;
                    TestTine.txtquestion.setText("" + TestTine.ansques);
                    container.card_view_question1.setBackgroundColor(Color.GREEN);

                } else {
                    TestTine.ansques=TestTine.ansques-1;
                    TestTine.ansques = TestTine.ansques + 1;
                    TestTine.txtquestion.setText("" + TestTine.ansques);
                    container.card_view_question1.setBackgroundColor(Color.GREEN);

                }
                mDataset[position] = "b";
                testArrayList.get(position).setSelected("B");

            }
            if(container.c.isChecked())
            {
                container.a.setChecked(false);
                container.b.setChecked(false);
                container.d.setChecked(false);
                container.e.setChecked(false);
                if (testArrayList.get(position).getSelected().equals("C")) {
                    TestTine.txtquestion.setText("" + TestTine.ansques);
                    container.card_view_question1.setBackgroundColor(Color.GREEN);
                }
                else if (testArrayList.get(position).getSelected().equals("Not Set")) {
                    TestTine.ansques = TestTine.ansques + 1;
                    TestTine.txtquestion.setText("" + TestTine.ansques);
                    container.card_view_question1.setBackgroundColor(Color.GREEN);

                } else {
                    TestTine.ansques=TestTine.ansques-1;
                    TestTine.ansques = TestTine.ansques + 1;
                    TestTine.txtquestion.setText("" + TestTine.ansques);
                    container.card_view_question1.setBackgroundColor(Color.GREEN);

                }
                mDataset[position] = "c";
                testArrayList.get(position).setSelected("C");

            }
            if(container.d.isChecked())
            {
                container.a.setChecked(false);
                container.b.setChecked(false);
                container.c.setChecked(false);
                container.e.setChecked(false);
                if (testArrayList.get(position).getSelected().equals("D")) {
                    TestTine.txtquestion.setText("" + TestTine.ansques);
                    container.card_view_question1.setBackgroundColor(Color.GREEN);
                }
                else if (testArrayList.get(position).getSelected().equals("Not Set")) {
                    TestTine.ansques = TestTine.ansques + 1;
                    TestTine.txtquestion.setText("" + TestTine.ansques);
                    container.card_view_question1.setBackgroundColor(Color.GREEN);

                } else {
                    TestTine.ansques=TestTine.ansques-1;
                    TestTine.ansques = TestTine.ansques + 1;
                    TestTine.txtquestion.setText("" + TestTine.ansques);
                    container.card_view_question1.setBackgroundColor(Color.GREEN);

                }
                mDataset[position] = "d";
                testArrayList.get(position).setSelected("D");
            }
            if(container.e.isChecked())
            {
                container.a.setChecked(false);
                container.b.setChecked(false);
                container.c.setChecked(false);
                container.d.setChecked(false);
                mDataset[position] = "e";
                testArrayList.get(position).setSelected("Not Set");
                TestTine.ansques=TestTine.ansques-1;
                container.card_view_question1.setBackgroundColor(Color.WHITE);

            }
            int selectedid=container.rdanswer.getCheckedRadioButtonId();*/


           /* container.rdanswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int selectedid=container.rdanswer.getCheckedRadioButtonId();
                    switch (checkedId) {
                        case R.id.a:
                            if (testArrayList.get(position).getSelected().equals("A")) {
                                TestTine.txtquestion.setText("" + TestTine.ansques);
                                container.card_view_question1.setBackgroundColor(Color.GREEN);
                            } else if (testArrayList.get(position).getSelected().equals("Not Set")) {
                                TestTine.ansques = TestTine.ansques + 1;
                                TestTine.txtquestion.setText("" + TestTine.ansques);
                                container.card_view_question1.setBackgroundColor(Color.GREEN);

                            } else {
                                TestTine.ansques=TestTine.ansques-1;
                                TestTine.ansques = TestTine.ansques + 1;
                                TestTine.txtquestion.setText("" + TestTine.ansques);
                                container.card_view_question1.setBackgroundColor(Color.GREEN);

                            }
                            mDataset[position] = "a";
                            testArrayList.get(position).setSelected("A");

                            break;
                        case R.id.b:
                            if (testArrayList.get(position).getSelected().equals("B")) {
                                TestTine.txtquestion.setText("" + TestTine.ansques);
                                container.card_view_question1.setBackgroundColor(Color.GREEN);
                            }
                            else if (testArrayList.get(position).getSelected().equals("Not Set")) {
                                TestTine.ansques = TestTine.ansques + 1;
                                TestTine.txtquestion.setText("" + TestTine.ansques);
                                container.card_view_question1.setBackgroundColor(Color.GREEN);

                            } else {
                                TestTine.ansques=TestTine.ansques-1;
                                TestTine.ansques = TestTine.ansques + 1;
                                TestTine.txtquestion.setText("" + TestTine.ansques);
                                container.card_view_question1.setBackgroundColor(Color.GREEN);

                            }
                            mDataset[position] = "b";
                            testArrayList.get(position).setSelected("B");
                            break;
                        case R.id.c:
                            if (testArrayList.get(position).getSelected().equals("C")) {
                                TestTine.txtquestion.setText("" + TestTine.ansques);
                                container.card_view_question1.setBackgroundColor(Color.GREEN);
                            }
                            else if (testArrayList.get(position).getSelected().equals("Not Set")) {
                                TestTine.ansques = TestTine.ansques + 1;
                                TestTine.txtquestion.setText("" + TestTine.ansques);
                                container.card_view_question1.setBackgroundColor(Color.GREEN);

                            } else {
                                TestTine.ansques=TestTine.ansques-1;
                                TestTine.ansques = TestTine.ansques + 1;
                                TestTine.txtquestion.setText("" + TestTine.ansques);
                                container.card_view_question1.setBackgroundColor(Color.GREEN);

                            }
                            mDataset[position] = "c";
                            testArrayList.get(position).setSelected("C");
                            break;
                        case R.id.d:
                            if (testArrayList.get(position).getSelected().equals("D")) {
                                TestTine.txtquestion.setText("" + TestTine.ansques);
                                container.card_view_question1.setBackgroundColor(Color.GREEN);
                            }
                            else if (testArrayList.get(position).getSelected().equals("Not Set")) {
                                TestTine.ansques = TestTine.ansques + 1;
                                TestTine.txtquestion.setText("" + TestTine.ansques);
                                container.card_view_question1.setBackgroundColor(Color.GREEN);

                            } else {
                                TestTine.ansques=TestTine.ansques-1;
                                TestTine.ansques = TestTine.ansques + 1;
                                TestTine.txtquestion.setText("" + TestTine.ansques);
                                container.card_view_question1.setBackgroundColor(Color.GREEN);

                            }
                            mDataset[position] = "d";
                            testArrayList.get(position).setSelected("D");
                            break;
                        case R.id.e:
                            mDataset[position] = "e";
                            testArrayList.get(position).setSelected("Not Set");
                            TestTine.ansques=TestTine.ansques-1;
                            container.card_view_question1.setBackgroundColor(Color.WHITE);
                            break;


                    }


                }
            });*/
       /* } else {
            container.rdanswer.setVisibility(View.GONE);
            container.rdanswerimg.setVisibility(View.VISIBLE);
            String URL_A = Common.GetBaseImageURL()+"test/option/" + test.getA_img();
            String URL_B = Common.GetBaseImageURL()+"test/option/" + test.getB_img();
            String URL_C = Common.GetBaseImageURL()+"test/option/" + test.getC_img();
            String URL_D = Common.GetBaseImageURL()+"test/option/" + test.getD_img();
            String URL_A = "http://www.zocarro.net/zocarro_mobile/image/"+"test/option/" + test.getA_img();
            String URL_B = "http://www.zocarro.net/zocarro_mobile/image/"+"test/option/" + test.getB_img();
            String URL_C = "http://www.zocarro.net/zocarro_mobile/image/"+"test/option/" + test.getC_img();
            String URL_D ="http://www.zocarro.net/zocarro_mobile/image/"+"test/option/" + test.getD_img();
            Glide.with(ctx).load(URL_A).into(container.imga);
            Glide.with(ctx).load(URL_B).into(container.imgb);
            Glide.with(ctx).load(URL_C).into(container.imgc);
            Glide.with(ctx).load(URL_D).into(container.imgd);
            URL url_a,url_b,url_c,url_d = null;
            try {
                url_a = new URL("http://www.zocarro.net/zocarro/image/test/option/" + test.getA_img());
                HttpURLConnection connection = (HttpURLConnection) url_a.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                a_img = BitmapFactory.decodeStream(input);
                url_b = new URL( "http://www.zocarro.net/zocarro/image/test/option/" + test.getB_img());
                HttpURLConnection connection2 = (HttpURLConnection) url_b.openConnection();
                connection2.setDoInput(true);
                connection2.connect();
                InputStream input2 = connection2.getInputStream();
                b_img = BitmapFactory.decodeStream(input2);
                url_c = new URL("http://www.zocarro.net/zocarro/image/test/option/" + test.getC_img());
                HttpURLConnection connection3 = (HttpURLConnection) url_c.openConnection();
                connection3.setDoInput(true);
                connection3.connect();
                InputStream input3 = connection3.getInputStream();
                c_img = BitmapFactory.decodeStream(input3);
                url_d = new URL("http://www.zocarro.net/zocarro/image/test/option/" + test.getD_img());
                HttpURLConnection connection4 = (HttpURLConnection) url_d.openConnection();
                connection4.setDoInput(true);
                connection4.connect();
                InputStream input4 = connection4.getInputStream();
                d_img = BitmapFactory.decodeStream(input4);
                Drawable a=new BitmapDrawable(ctx.getResources(), a_img);
                Drawable b=new BitmapDrawable(ctx.getResources(), b_img);
                Drawable c=new BitmapDrawable(ctx.getResources(), c_img);
                Drawable d=new BitmapDrawable(ctx.getResources(), d_img);
                container.a_img.setCompoundDrawables(null,null, null, a);
                container.b_img.setCompoundDrawables(null,null, null, b);
                container.c_img.setCompoundDrawables(null,null, null, c);
                container.d_img.setCompoundDrawables(null,null, null, d);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

           /* container.rdanswerimg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int selectedIdimg = container.rdanswerimg.getCheckedRadioButtonId();
                    switch (selectedIdimg) {
                        case R.id.a_img:
                            if (testArrayList.get(position).getSelected().equals("A")) {
                                TestTine.txtquestion.setText("" + TestTine.ansques);
                            } else if (testArrayList.get(position).getSelected().equals("Not Set")) {
                                TestTine.ansques = TestTine.ansques + 1;
                                TestTine.txtquestion.setText("" + TestTine.ansques);

                            } else {
                                TestTine.ansques=TestTine.ansques-1;
                                TestTine.ansques = TestTine.ansques + 1;
                                TestTine.txtquestion.setText("" + TestTine.ansques);

                            }
                            mDataset[position] = "a";
                            testArrayList.get(position).setSelected("A");

                            break;
                        case R.id.b_img:
                            if (testArrayList.get(position).getSelected().equals("B")) {
                                TestTine.txtquestion.setText("" + TestTine.ansques);
                            }
                            else if (testArrayList.get(position).getSelected().equals("Not Set")) {
                                TestTine.ansques = TestTine.ansques + 1;
                                TestTine.txtquestion.setText("" + TestTine.ansques);

                            } else {
                                TestTine.ansques=TestTine.ansques-1;
                                TestTine.ansques = TestTine.ansques + 1;
                                TestTine.txtquestion.setText("" + TestTine.ansques);

                            }
                            mDataset[position] = "b";
                            testArrayList.get(position).setSelected("B");

                            break;
                        case R.id.c_img:
                            if (testArrayList.get(position).getSelected().equals("C")) {
                                TestTine.txtquestion.setText("" + TestTine.ansques);
                            }
                            else if (testArrayList.get(position).getSelected().equals("Not Set")) {
                                TestTine.ansques = TestTine.ansques + 1;
                                TestTine.txtquestion.setText("" + TestTine.ansques);

                            } else {
                                TestTine.ansques=TestTine.ansques-1;
                                TestTine.ansques = TestTine.ansques + 1;
                                TestTine.txtquestion.setText("" + TestTine.ansques);

                            }
                            mDataset[position] = "c";
                            testArrayList.get(position).setSelected("C");

                            break;
                        case R.id.d_img:
                            if (testArrayList.get(position).getSelected().equals("D")) {
                                TestTine.txtquestion.setText("" + TestTine.ansques);
                            }
                            else if (testArrayList.get(position).getSelected().equals("Not Set")) {
                                TestTine.ansques = TestTine.ansques + 1;
                                TestTine.txtquestion.setText("" + TestTine.ansques);

                            } else {
                                TestTine.ansques=TestTine.ansques-1;
                                TestTine.ansques = TestTine.ansques + 1;
                                TestTine.txtquestion.setText("" + TestTine.ansques);

                            }
                            mDataset[position] = "d";
                            testArrayList.get(position).setSelected("D");
                            break;
                        case R.id.e_img:
                            mDataset[position] = "e";
                            testArrayList.get(position).setSelected("Not Set");
                            TestTine.ansques=TestTine.ansques-1;
                            TestTine.txtquestion.setText("" + TestTine.ansques);
                            break;


                    }
                }
            });
        }*/

    @Override
    public int getItemCount() {
        return testArrayList.size();
    }

    class MyWidgetContainer extends RecyclerView.ViewHolder {
        public TextView question1_text_view;
        /* RadioGridLayout rdanswer, rdanswerimg;
         RadioButton a, b, c, d, e, a_img, b_img, c_img, d_img, e_img;*/
        RadioGroup firstanswer, secondanswer;
        RelativeLayout relassignment;
        CardView card_view_question1;
        ImageView imgquestion;
        ImageView imga, imgb, imgc, imgd;
        Button btnmark, btnsave;


        public MyWidgetContainer(View itemView) {
            super(itemView);
            question1_text_view = itemView.findViewById(R.id.question1_text_view);
            imgquestion = itemView.findViewById(R.id.imgquestion);
          /*  a = itemView.findViewById(R.id.a);
            b = itemView.findViewById(R.id.b);
            c = itemView.findViewById(R.id.c);
            d = itemView.findViewById(R.id.d);
            e = itemView.findViewById(R.id.e);

            a_img = itemView.findViewById(R.id.a_img);
            b_img = itemView.findViewById(R.id.b_img);
            c_img = itemView.findViewById(R.id.c_img);
            d_img = itemView.findViewById(R.id.d_img);
            e_img = itemView.findViewById(R.id.e_img);*/
            firstanswer = itemView.findViewById(R.id.firstanswer);
            secondanswer = itemView.findViewById(R.id.secondanswer);
            relassignment = itemView.findViewById(R.id.relassignment);
            card_view_question1 = itemView.findViewById(R.id.card_view_question1);
          /*  imga = itemView.findViewById(R.id.imga);
            imgb = itemView.findViewById(R.id.imgb);
            imgc = itemView.findViewById(R.id.imgc);
            imgd = itemView.findViewById(R.id.imgd);*/
            btnmark = itemView.findViewById(R.id.btnmark);
            btnsave = itemView.findViewById(R.id.btnsave);


        }
    }
}
