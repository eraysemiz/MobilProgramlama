package com.example.a26mayis2;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WallpaperAdapter.WallpaperClickListener {

    private RecyclerView recyclerView;
    private WallpaperAdapter adapter;
    private List<String> wallpaperURLs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wallpaperURLs = new ArrayList<>();
        adapter = new WallpaperAdapter(this, wallpaperURLs, this);
        recyclerView.setAdapter(adapter);

        loadWallpapers();
    }

    private void loadWallpapers() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference listRef = storage.getReference().child("wallpapers");

        listRef.listAll().addOnSuccessListener(listResult -> {
            for (StorageReference item : listResult.getItems()) {
                item.getDownloadUrl().addOnSuccessListener(uri -> {
                    wallpaperURLs.add(uri.toString());
                    adapter.notifyDataSetChanged();
                });
            }
        }).addOnFailureListener(e -> {
            // Hata yönetimi yapılabilir
            e.printStackTrace();
        });
    }

    @Override
    public void onWallpaperClick(String url) {
        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(MainActivity.this);
                try {
                    wallpaperManager.setBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                e.printStackTrace();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                // Yükleme öncesi işlem yapılabilir
            }
        });
    }
}
