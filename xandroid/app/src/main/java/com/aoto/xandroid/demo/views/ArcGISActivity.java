package com.aoto.xandroid.demo.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aoto.xandroid.DaggerMainComponent;
import com.aoto.xandroid.MainModule;
import com.aoto.xandroid.R;
import com.aoto.xandroid.utils.gis.ArcGIS;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.view.Camera;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.SceneView;
import com.squareup.leakcanary.RefWatcher;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@ContentView(R.layout.activity_arcgis)
public class ArcGISActivity extends AppCompatActivity {
    @Inject
    RefWatcher refWatcher;

    @ViewInject(R.id.mapView)
    private MapView mMapView;
    @ViewInject(R.id.sceneView)
    private SceneView mSceneView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DaggerMainComponent.create().inject(this);//在没有Module注入时可简写
        DaggerMainComponent.builder().mainModule(new MainModule(this)).build().inject(this);
        x.view().inject(this);


        //Constants.MAP_TPKPATH + "XZQH.tpk"//TPK离线图
        //http://services.arcgisonline.com/arcgis/rest/services/Canvas/World_Dark_Gray_Base/MapServer//普通切片服务
        //http://sampleserver6.arcgisonline.com/arcgis/rest/services/Census/MapServer//影像服务

       ArcGIS.ArcGISMapObservable("http://services.arcgisonline.com/arcgis/rest/services/Canvas/World_Dark_Gray_Base/MapServer", ArcGIS.GIS_SERVICE_TYPE_TILED)
                 .map(arcGISMap->ArcGIS.addMapOperationalLayers(
                         arcGISMap,
                         "http://sampleserver6.arcgisonline.com/arcgis/rest/services/Census/MapServer",
                         ArcGIS.GIS_SERVICE_TYPE_IMAGE
                 ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(arcGISMap -> mMapView.setMap(arcGISMap));

       //Basemap.createImagery() //官方提供三维场景底图
       //http://map.geoq.cn/arcgis/rest/services/ChinaOnlineCommunity/MapServer //三维场景底图
       //http://tiles.arcgis.com/tiles/P3ePLMYs2RVChkJx/arcgis/rest/services/Buildings_Brest/SceneServer/layers/0 //操作图层
       //http://elevation3d.arcgis.com/arcgis/rest/services/WorldElevation3D/Terrain3D/ImageServer 三维场景资源

       ArcGIS.ArcGISSceneObservable("http://map.geoq.cn/arcgis/rest/services/ChinaOnlineCommunity/MapServer")
               .map(arcGISScene->ArcGIS.addSceneOperationalLayers(arcGISScene,"http://tiles.arcgis.com/tiles/P3ePLMYs2RVChkJx/arcgis/rest/services/Buildings_Brest/SceneServer/layers/0"))
               .map(arcGISScene->ArcGIS.addSceneElevationSource(arcGISScene,"http://elevation3d.arcgis.com/arcgis/rest/services/WorldElevation3D/Terrain3D/ImageServer"))
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(arcGISScene -> {
                   mSceneView.setScene(arcGISScene);
                   Camera camera = new Camera(53.06, -4.04, 1289.0, 295.0, 71, 0.0);
                   mSceneView.setViewpointCamera(camera);
               });
    }


    @Override
    protected void onPause() {
        mMapView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.resume();
    }

    @Override
    protected void onDestroy() {
        mMapView.dispose();
        super.onDestroy();
        refWatcher.watch(this);
    }
}
