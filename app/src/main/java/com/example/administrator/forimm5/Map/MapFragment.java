package com.example.administrator.forimm5.Map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.forimm5.DB.Center;
import com.example.administrator.forimm5.DB.CenterLab;
import com.example.administrator.forimm5.R;
import com.example.administrator.forimm5.Util.CheckPermission;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * 나는 뭐하는 놈입니다~
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, CheckPermission.CallBack {

    View view;
    FragmentManager fragmentManager;
    SupportMapFragment mapFragment;
    RecyclerView recyclerView;
    ViewPager mapPager;
    MapAdapter adapter;
    MapPagerAdapter pagerAdapter;
    CircleIndicator indicator;
    GoogleMap googleMap;
    LinearLayoutManager manager;

    ImageView mapPagerHam, pagerDown, searchViewIcon;
    TextView searchViewText;
    ConstraintLayout pagerLayout, searchViewLayout;
    FloatingActionButton fab;

    String curRegion;
    Double curLat, curLng;

    List<List<Center>> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_map, container, false);

        // 초기화
        init();

        // 맵 동기화
        mapFragment.getMapAsync(this);

        // 리사이클러 세팅 -- 데이터 세팅되는 장소
        setRegionRecycler();

        // 리스너
        setListener();

        return view;
    }

    // 초기화
    public void init() {
        fragmentManager = getChildFragmentManager();
        mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.mapFragment);
        recyclerView = (RecyclerView) view.findViewById(R.id.mapRecycler);
        mapPager = (ViewPager) view.findViewById(R.id.mapPager);
        indicator = (CircleIndicator) view.findViewById(R.id.circleIndicator);
        pagerLayout = (ConstraintLayout) view.findViewById(R.id.pagerLayout);
        mapPagerHam = (ImageView) view.findViewById(R.id.mapPagerHam);
        pagerDown = (ImageView) view.findViewById(R.id.pagerDown);
        adapter = new MapAdapter(this, setData(), getContext());
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        searchViewLayout = (ConstraintLayout) view.findViewById(R.id.searchViewLayout);
        searchViewIcon = (ImageView) view.findViewById(R.id.searchViewIcon);
        searchViewText = (TextView) view.findViewById(R.id.searchViewText);
    }

    public void setListener() {

        mapPagerHam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .setCustomAnimations(R.anim.slide_up, 0, 0, R.anim.slide_down)
                        .add(R.id.containerLayout, MapListFragment.newInstance(curRegion))
                        .commit();

            }
        });


        searchViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.containerLayout, new MapSearchFragment())
                        .commit();
            }
        });

        pagerDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pagerLayout.getVisibility() == View.VISIBLE) {
                    pagerLayout.setVisibility(View.GONE);
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
                CheckPermission.checkVersion(MapFragment.this , perms);
            }
        });

    }

    @Override
    public void callInit() {
        setCurrentLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CheckPermission.onResult(requestCode, grantResults, this);
    }

    /**
     * 지역별 센터 페이저뷰 세팅
     */
    public void setCenterPager(int position) {
        pagerAdapter = new MapPagerAdapter(data.get(position), this);
        mapPager.setAdapter(pagerAdapter);

//        mapPager.setClipToPadding(false);
//        mapPager.setPadding(120, 0, 120, 0);

        indicator.setViewPager(mapPager);

    }

    // 지도 초기화 -- 서울로 해준다
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        this.googleMap = googleMap;
        LatLng seoul = new LatLng(37.5665350, 126.9779690);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 11));

        googleMap.setOnMarkerClickListener(markerClickListener);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (pagerLayout.getVisibility() == View.VISIBLE) {
                    pagerLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            if(marker.getTitle().equals("현재 위치")){
                marker.showInfoWindow();
            }else {
                List<Center> data = CenterLab.getInstance(getActivity()).getRegions(curRegion);
                int curpos = 0;
                LatLng latLng = null;
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getName().equals(marker.getTitle())) {
                        curpos = i;
                        latLng = new LatLng(Double.parseDouble(data.get(i).getLat()), Double.parseDouble(data.get(i).getLng()));
                    }
                }
                moveLocal(latLng, 10);

                pagerLayout.setVisibility(View.VISIBLE);
                mapPager.setCurrentItem(curpos);
                marker.showInfoWindow();
            }
            return true;
        }
    };

    /**
     *  지역 선택 리사이클러 관리
     */
    // 지역별로 데이터를 세팅
    public List<List<Center>> setData() {
        List<List<Center>> data = new ArrayList<>();
        List<Center> busan = CenterLab.getInstance(getActivity()).getRegions("부산", R.drawable.busan, R.drawable.busan_selected);
        data.add(busan);
        List<Center> gyeongnam = CenterLab.getInstance(getActivity()).getRegions("경남", R.drawable.gyeongnam, R.drawable.gyeongnam_selected);
        data.add(gyeongnam);
        List<Center> ulsan = CenterLab.getInstance(getActivity()).getRegions("울산", R.drawable.ulsan, R.drawable.ulsan_selected);
        data.add(ulsan);
        List<Center> daegu = CenterLab.getInstance(getActivity()).getRegions("대구", R.drawable.daegu, R.drawable.daegu_selected);
        data.add(daegu);
        List<Center> gyeongbuk = CenterLab.getInstance(getActivity()).getRegions("경북", R.drawable.gyeongbuk, R.drawable.gyeongbuk_selected);
        data.add(gyeongbuk);
        List<Center> daejeon = CenterLab.getInstance(getActivity()).getRegions("대전", R.drawable.daejeon, R.drawable.daegu_selected);
        data.add(daejeon);
        List<Center> choongchung = CenterLab.getInstance(getActivity()).getRegions("충청", R.drawable.choongnam, R.drawable.choongnam_selected);
        data.add(choongchung);
        List<Center> gwangju = CenterLab.getInstance(getActivity()).getRegions("광주", R.drawable.gwangju, R.drawable.gwangju_selected);
        data.add(gwangju);
        List<Center> jeonnam = CenterLab.getInstance(getActivity()).getRegions("전남", R.drawable.jeonnam, R.drawable.jeonnam_selected);
        data.add(jeonnam);
        List<Center> incheon = CenterLab.getInstance(getActivity()).getRegions("인천", R.drawable.incheon, R.drawable.incheon_selected);
        data.add(incheon);
        List<Center> gyeonggi = CenterLab.getInstance(getActivity()).getRegions("경기", R.drawable.gyeonggi, R.drawable.gyeonggi_selected);
        data.add(gyeonggi);
        List<Center> seoul = CenterLab.getInstance(getActivity()).getRegions("서울", R.drawable.seoul, R.drawable.seoul_selected);
        data.add(seoul);
        List<Center> jeju = CenterLab.getInstance(getActivity()).getRegions("제주", R.drawable.jeju, R.drawable.jeju_selcted);
        data.add(jeju);
        return data;
    }

    // 지역 선택 리사이클러 세팅
    public void setRegionRecycler() {
        data = setData();
        adapter = new MapAdapter(this, data, getContext());
        recyclerView.setAdapter(adapter);
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
    }

    // 지역 아이콘을 누를 때 뷰페이저 목록 출력
    public void setLayoutVisibility() {
//        Animation slide_up = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
//        Animation slide_down = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
//        pagerLayout.setAnimation(slide_up);
//        pagerLayout.setAnimation(slide_down);
//        slide_down.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                pagerLayout.setVisibility(View.GONE);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
        if (pagerLayout.getVisibility() == View.GONE) {
            pagerLayout.setVisibility(View.VISIBLE);
//            pagerLayout.startAnimation(slide_up);
        } else {
//            pagerLayout.setAnimation(slide_down);
//            pagerLayout.setVisibility(View.GONE);
        }
    }

    // 각 지역으로 움직이기
    public void moveLocal(int position) {
        // 1. 어떤 지역인지 꺼내온다
        List<Center> local = data.get(position);
        // 2. 첫번째 데이터의 지역으로 이동한다.
        double lat = Double.parseDouble(local.get(0).getLat());
        double lng = Double.parseDouble(local.get(0).getLng());
        LatLng latLng = new LatLng(lat, lng);
        // 3. 이동 -- 줌을 작게 잡아서 지역 전체가 보이도록 한다.
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

        // 애니메이션으로 이동하기
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
//                        .bearing(45)  // 평면에서 얼마나 지도를 기울일 것인가
//                        .tilt(90)  // 3d 로 얼마나 기울어져 있는가
                .zoom(10)   // 이것을 인자로 받아서 수동으로 설정해 주지 않으면 마지막 설정한 값으로 줌을 계속 사용한다.
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1000, null);
    }

    public void moveLocal(LatLng latLng, int zoom) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
//                        .bearing(45)  // 평면에서 얼마나 지도를 기울일 것인가
//                        .tilt(90)  // 3d 로 얼마나 기울어져 있는가
                .zoom(zoom)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1000, null);
    }

    // 마커 찍어주기 todo 로딩할 때 미리 해 놓고 visibility 관리만 해 줄지 결정하자
    public void setMarkers(int position) {
        googleMap.clear();
        List<Center> centers = data.get(position);
        for (int i = 0; i < centers.size(); i++) {
            MarkerOptions options = new MarkerOptions();
            options.title(centers.get(i).getName());
            options.position(new LatLng(Double.parseDouble(centers.get(i).getLat()), Double.parseDouble(centers.get(i).getLng())));
            options.draggable(false);
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_selected));
            // 아하 마커 옵션과 마커가 이런 차이가 있었군!

//            Marker marker = googleMap.addMarker(options);
//            marker.showInfoWindow();

//              이렇게 커스텀으로도 할 수 있다!!!
//            LinearLayout layout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.custom_marker, null, false);
//            layout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//            layout.layout(0,0, layout.getMeasuredWidth(), layout.getMeasuredHeight());
//            layout.setDrawingCacheEnabled(true);
//            layout.buildDrawingCache();
//            Bitmap bItmap = layout.getDrawingCache();
//            TextView title = (TextView) layout.findViewById(R.id.markerName);
//            title.setText(centers.get(i).getName());
//            options.icon(BitmapDescriptorFactory.fromBitmap(bItmap));

            googleMap.addMarker(options);
        }
    }

    // 현재 선택된 위치 알려줌
    public void setCurRegion(String region) {
        this.curRegion = region;
    }

    // 현재 위치로
    public void setCurrentLocation() {

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            // 뭐야 이거 없어도 되는데?
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//            moveLocal(new LatLng(curLat, curLng), 14);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                moveLocal(latLng, 14);
                Marker marker = googleMap.addMarker(new MarkerOptions().title("현재 위치").position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_selected)));
                marker.showInfoWindow();
                Toast.makeText(getActivity(), latLng.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }

    // 페이저 페이지 선택시 마커 들어가기
    public void setPagerPage(){
        // todo 사실 왜 instantiate 로 하면 안 되는지 모르겠다. 반드시 이유를 찾을 것!!!
        List<Center> centers = CenterLab.getInstance(getActivity()).getRegions(curRegion);
        int curPos = mapPager.getCurrentItem();
        double lat = Double.parseDouble(centers.get(curPos).getLat());
        double lng = Double.parseDouble(centers.get(curPos).getLng());
        LatLng latLng = new LatLng(lat, lng);
        moveLocal(latLng, 14);
    }

    // 페이저 마커 클릭한 곳으로 움직여주기
    public void movePagerPage(String title){
        List<Center> centers = CenterLab.getInstance(getActivity()).getRegions(curRegion);
        int curpso = 0;
        for (int i = 0; i < centers.size(); i++) {
            if(centers.get(i).getName().equals(title)){
                curpso = i;
            }
        }
        mapPager.setCurrentItem(curpso);
    }

//    LocationListener locationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//            curLat = location.getLatitude();
//            curLng = location.getLongitude();
//            moveLocal(new LatLng(curLat, curLng), 14);
//            Log.e("호출 확인", "locationListener");
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//
//        }
//    };
}
