package com.timbuchalka.top10downloader.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.timbuchalka.top10downloader.api.crud.ApiCrudFactory;
import com.timbuchalka.top10downloader.api.crud.convertor.ConsultantInformationConvertor;
import com.timbuchalka.top10downloader.models.ConsultantInformation;

import java.io.File;
import java.io.InputStream;
import java.util.Set;

public class GetFileByUrlData
        extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public GetFileByUrlData(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap mIcon = null;

        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return mIcon;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}





















