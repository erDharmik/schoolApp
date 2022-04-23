package com.aspirepublicschool.gyanmanjari.ImageToPdf;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.aspirepublicschool.gyanmanjari.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ImagePDFActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE1 =111 ;
    private static final int REQUEST_CODE = 100;
    static Button btnchoose,btncreate;
    String imageEncoded;
    List<String> imagesEncodedList;
    private GridView gvGallery;
    private GalleryAdapter galleryAdapter;
    ArrayList<Uri> mArrayUri;
    ArrayList<Bitmap> bitmapArrayList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pdf);
        btnchoose=findViewById(R.id.btnchoose);
        btncreate=findViewById(R.id.btncreate);
        gvGallery=findViewById(R.id.gv);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }

        ActivityCompat.requestPermissions(
                ImagePDFActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Do the file write
        } else {
            // Request permission from the user
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        btnchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE1);
            }
        });
        btncreate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                createPDFWithMultipleImage();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPDFWithMultipleImage() {
//                int size=GalleryAdapter.bitmaps.size();
        int size=mArrayUri.size();
        if(size>0)
        {
            File file = getOutputFile();
//            if (!file.exists()) {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                    Display display = wm.getDefaultDisplay();
                    DisplayMetrics displaymetrics = new DisplayMetrics();
                    this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                    float hight = displaymetrics.heightPixels ;
                    float width = displaymetrics.widthPixels ;

                    int convertHighet = (int) hight, convertWidth = (int) width;

                    PdfDocument pdfDocument = new PdfDocument();
                    Collections.reverse(GalleryAdapter.bitmaps);
                    for (int i = 0; i < GalleryAdapter.bitmaps.size()-1; i++) {
                        //Bitmap bitmap = BitmapFactory.decodeFile(mArrayUri.get(i).getPath());

                        Log.d("size", String.valueOf(GalleryAdapter.bitmaps.size()));

                        //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(new File(mArrayUri.get(i).getPath())));
                        Bitmap bitmap = GalleryAdapter.bitmaps.get(i);

                        Bitmap resized = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        //resized.compress(Bitmap.CompressFormat.PNG, 70,stream );
                        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(resized.getWidth(), resized.getHeight(), i).create();
                        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
                        Canvas canvas = page.getCanvas();
                        Paint paint = new Paint();
                        paint.setColor(Color.BLUE);
                        canvas.drawPaint(paint);
                        canvas.drawBitmap(resized, 0f, 0f, null);
                        pdfDocument.finishPage(page);
                        resized.recycle();
                    }
                    pdfDocument.writeTo(fileOutputStream);
                    pdfDocument.close();
                    openFolder(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            } else {
//                Toast.makeText(ImagePDFActivity.this, "Please Select Image", Toast.LENGTH_LONG).show();
//            }
        }
    }
    public void openFolder(File filename){
//        File dirPath =  new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        File file = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOWNLOADS + "/" + filename.getName());
        Uri path = Uri.fromFile(file);
        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfOpenintent.setDataAndType(path, "application/pdf");
        try {
            startActivity(pdfOpenintent);
        } catch (ActivityNotFoundException e) {

        }
    }
    private File getOutputFile(){
        File root = new File(Environment.getExternalStorageDirectory(),"Downloads");

        boolean isFolderCreated = true;
        boolean isFileCreated = false;
        String imageFileName="";

//        if (!root.exists()){
//            isFolderCreated = root.mkdir();
//        }

//        if (isFolderCreated) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
            imageFileName = "PDF_" + timeStamp;

            return new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOWNLOADS + "/" + imageFileName + ".pdf");
//        }
//        else {
//            Toast.makeText(ImagePDFActivity.this, "Folder is not created", Toast.LENGTH_SHORT).show();
//            return null;
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMAGE1 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data



                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                    mArrayUri = new ArrayList<Uri>();
                    //
                    mArrayUri.add(mImageUri);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
                    // bitmaps.add(drawable);


                    galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                    gvGallery.setAdapter(galleryAdapter);
                    gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                            .getLayoutParams();
                    mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                            galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                            gvGallery.setAdapter(galleryAdapter);
                            gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                                    .getLayoutParams();
                            mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                // Re-attempt file write
        }
    }
}
