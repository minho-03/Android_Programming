package com.cookandroid.cookmap;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap gMap;
    SupportMapFragment mapFrag;
    ArrayList<GroundOverlay> cctvList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("CCTV 관리 지도");
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFrag != null) {
            mapFrag.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        gMap = map;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.568256, 126.897240), 13));
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                GroundOverlayOptions videoMark = new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(android.R.drawable.presence_video_busy))
                        .position(point, 400f, 400f);
                GroundOverlay cctv = gMap.addGroundOverlay(videoMark);
                cctvList.add(cctv);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, "위성지도");
        menu.add(0, 2, 0, "일반지도");
        SubMenu sMenu = menu.addSubMenu("CCTV 관리 >>");
        sMenu.add(0, 3, 0, "바로전 CCTV 지우기");
        sMenu.add(0, 4, 0, "모든 CCTV 지우기");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (gMap == null) return false;

        switch (item.getItemId()) {
            case 1:
                gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case 2:
                gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case 3: // 바로전 지우기 (마지막 인덱스 삭제)
                if (cctvList.size() > 0) {
                    int lastIndex = cctvList.size() - 1;
                    cctvList.get(lastIndex).remove();
                    cctvList.remove(lastIndex);
                }
                return true;
            case 4:
                for (GroundOverlay cctv : cctvList) {
                    cctv.remove();
                }
                cctvList.clear();
                return true;
        }
        return false;
    }
}