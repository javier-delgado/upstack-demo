package com.javierdelgado.upstack_demo.vistas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.javierdelgado.upstack_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoViewAttacher;

public class FullScreenImageActivity extends AppCompatActivity {
    @BindView(R.id.img)
    ImageView img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        ButterKnife.bind(this);
        setupImage();
        Toast.makeText(this, R.string.pinch_to_zoom_explain, Toast.LENGTH_SHORT).show();
    }

    private void setupImage() {
        Glide.with(this)
                .load(getIntent().getStringExtra("img_url"))
                .into(img);
        new PhotoViewAttacher(img);
    }

    @OnClick(R.id.btnClose)
    public void onClose() {
        onBackPressed();
    }
}
