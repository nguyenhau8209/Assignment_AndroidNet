package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FetchImageTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String imageUrl = params[0];
        String base64Image = ImageUtils.convertImageToBase64(imageUrl);
        return base64Image;
    }

    @Override
    protected void onPostExecute(String base64Image) {
        // Ở đây, bạn có thể sử dụng base64Image theo ý muốn sau khi đã chuyển đổi xong
        Log.d("Image", "base64Image: " + base64Image);
    }
    private String postImageToServer(String base64Image) {
        try {
            String url = "YOUR_SERVER_API_URL"; // Thay thế bằng URL của API trên server của bạn
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, base64Image);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "Error: " + response.code();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}

