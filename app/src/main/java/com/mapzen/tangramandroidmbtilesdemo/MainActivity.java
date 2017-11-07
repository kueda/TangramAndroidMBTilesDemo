package com.mapzen.tangramandroidmbtilesdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.SceneError;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements MapController.SceneLoadListener {
    String TAG = "MainActivity";
    MapView mapView;
    MapController map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.map);

        // Copy mbtiles from assets to files dir where we can use it
        File file = new File(getFilesDir(), "tm_world_borders.mbtiles");
        if (file.exists()) {
            Log.d(TAG, "mbtiles exists at " + file.getAbsolutePath());
        } else {
            try {
                InputStream inputStream = getAssets().open("tm_world_borders.mbtiles");
                OutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "Saved mbtiles to " + file.getAbsolutePath() + ", size: " + file.length());
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        map = mapView.getMap(this);
        map.loadSceneFile("asset:///scene.yaml");
        map.setPosition(new LngLat(0, 0));
        map.setZoom(2);
    }

    @Override
    public void onSceneReady(int sceneId, SceneError sceneError) {
        if (sceneError == null) {
            Toast.makeText(this, "Scene ready: " + sceneId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Scene load error: " + sceneId + " "
                    + sceneError.getSceneUpdate().toString()
                    + " " + sceneError.getError().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
