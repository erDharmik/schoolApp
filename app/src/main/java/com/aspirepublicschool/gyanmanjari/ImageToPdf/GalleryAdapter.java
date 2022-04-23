package com.aspirepublicschool.gyanmanjari.ImageToPdf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aspirepublicschool.gyanmanjari.R;

import java.io.IOException;
import java.util.ArrayList;

public class GalleryAdapter extends BaseAdapter {

    private Context ctx;
    private int pos;
    private LayoutInflater inflater;
    private ImageView ivGallery;
    ArrayList<Uri> mArrayUri;
    static ArrayList<Bitmap> bitmaps=new ArrayList<>();

    public GalleryAdapter(Context ctx, ArrayList<Uri> mArrayUri) {
        this.ctx = ctx;
        this.mArrayUri = mArrayUri;
        bitmaps.clear();
    }

    @Override
    public int getCount() {
        return mArrayUri.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayUri.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        pos = position;
        inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.gv_item, parent, false);

        ivGallery = (ImageView) itemView.findViewById(R.id.ivGallery);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(),mArrayUri.get(position));
            ivGallery.setImageBitmap(bitmap);
            Drawable drawable =  ivGallery.getDrawable();
            Bitmap viewbitmap = ((BitmapDrawable) drawable).getBitmap();
            bitmaps.add(viewbitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ivGallery.setImageURI(mArrayUri.get(position));
       /* MainActivity.btncreate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                createPDFWithMultipleImage();
            }
        });
*/
        return itemView;
    }



}
