package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "IP5pVaHaWYJXW1YFdrA03mXo4IayAmPLytGphJqi";
    private static final String BASE_URL = "https://api.nasa.gov/";

    private ImageView imageView;
    private Button fetchButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        fetchButton = findViewById(R.id.fetchButton);

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức để lấy và hiển thị ảnh từ API
                fetchImageFromApi();
            }
        });
    }

    private void fetchImageFromApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);
        Call<NasaApodResponse> call = apiInterface.getNasaApod(API_KEY, "2023-07-21");
        //call.enqueue() để gửi yêu cầu API bất đồng bộ, điều này cho phép ứng dụng tiếp tục thực hiện các công việc khác trong khi yêu cầu đang chờ đợi phản hồi từ API.
        call.enqueue(new Callback<NasaApodResponse>() {
            @Override
            public void onResponse(Call<NasaApodResponse> call, Response<NasaApodResponse> response) {
                if (response.isSuccessful()) {
                    NasaApodResponse apodResponse = response.body();
                    if (apodResponse != null) {
                        String imageUrl = apodResponse.getImageUrl();
                        // Hiển thị ảnh từ URL lên ImageView
                        Picasso.get().load(imageUrl).into(imageView);

                        // Chuyển ảnh về dạng Base64 (nếu cần)
                        FetchImageTask fetchImageTask = new FetchImageTask();
                        fetchImageTask.execute(imageUrl);
                        Log.d("Image", "convertImageToBase64" + fetchImageTask);
                        // Sử dụng base64Image theo ý muốn
                    }
                }
            }

            @Override
            public void onFailure(Call<NasaApodResponse> call, Throwable t) {
                // Xử lý lỗi khi không lấy được dữ liệu từ API
            }
        });
    }
}

