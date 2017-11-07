package com.aoto.xandroid.utils.gis;

import com.esri.arcgisruntime.data.TileCache;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISSceneLayer;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.ArcGISVectorTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.ArcGISScene;
import com.esri.arcgisruntime.mapping.ArcGISTiledElevationSource;
import com.esri.arcgisruntime.mapping.Basemap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by Administrator on 2017/10/16.
 */

public class ArcGIS {

    public static final String GIS_SERVICE_TYPE_TPK = "TPK";//TPK离线图
    public static final String GIS_SERVICE_TYPE_VTPK = "VTPK";//VTPK离线图
    public static final String GIS_SERVICE_TYPE_TILED = "TILED";//普通切片服务
    public static final String GIS_SERVICE_TYPE_IMAGE = "IMAGE";//影像服务

    /**
     * ArcGIS地图Observable
     * @param restUrl
     * @param type
     * @return
     */
    public static Observable<ArcGISMap> ArcGISMapObservable(String restUrl, String type) {
        Observable<ArcGISMap> observable = Observable.create((ObservableOnSubscribe<ArcGISMap>) e -> {
            ArcGISMap arcGISMap = new ArcGISMap();
            //底图tpk离线图层
            if(type.equals(GIS_SERVICE_TYPE_TPK)){
                TileCache mainTileCache = new TileCache(restUrl);
                ArcGISTiledLayer theOfflinetiledLayer = new ArcGISTiledLayer(mainTileCache);
                Basemap theOfflineBasemap = new Basemap(theOfflinetiledLayer);
                arcGISMap.setBasemap(theOfflineBasemap);

             //底图vtpk离线图层
            }else if(type.equals(GIS_SERVICE_TYPE_VTPK)){
                ArcGISVectorTiledLayer theOfflineVectorTiledLayer = new ArcGISVectorTiledLayer(restUrl);
                Basemap theOfflineVectorBasemap = new Basemap(theOfflineVectorTiledLayer);
                arcGISMap.setBasemap(theOfflineVectorBasemap);

             //普通切片服务
            }else if(type.equals(GIS_SERVICE_TYPE_TILED)){
                ArcGISTiledLayer tiledLayer = new ArcGISTiledLayer(restUrl);
                Basemap basemap = new Basemap(tiledLayer);
                arcGISMap.setBasemap(basemap);

             //影像服务
            }else if(type.equals(GIS_SERVICE_TYPE_IMAGE)){
                ArcGISMapImageLayer censusLayer = new ArcGISMapImageLayer(restUrl);
                Basemap basemap = new Basemap(censusLayer);
                arcGISMap.setBasemap(basemap);
            }
            e.onNext(arcGISMap);
            e.onComplete();
        });
        return observable;
    }

    /**
     * ArcGIS地图Observable
     * @param basemapType
     * @param latitude
     * @param longitude
     * @param levelOfDetail
     * @return
     */
    public static Observable<ArcGISMap> ArcGISMapObservable(Basemap.Type basemapType, double latitude, double longitude, int levelOfDetail) {
        Observable<ArcGISMap> observable = Observable.create((ObservableOnSubscribe<ArcGISMap>) e -> {
            ArcGISMap arcGISMap = new ArcGISMap(basemapType,latitude,longitude,levelOfDetail);
            e.onNext(arcGISMap);
            e.onComplete();
        });
        return observable;
    }

    /**
     * ArcGIS三维Observable
     * @param restUrl
     * @return
     */
    public static Observable<ArcGISScene> ArcGISSceneObservable(String restUrl) {
        Observable<ArcGISScene> observable = Observable.create((ObservableOnSubscribe<ArcGISScene>) e -> {
            ArcGISScene arcGISScene = new ArcGISScene();
            Basemap basemap = new Basemap(new ArcGISTiledLayer(restUrl));
            arcGISScene.setBasemap(basemap);
            e.onNext(arcGISScene);
            e.onComplete();
        });
        return observable;
    }
    /**
     * ArcGIS三维Observable
     * @param basemap
     * @return
     */
    public static Observable<ArcGISScene> ArcGISSceneObservable(Basemap basemap) {
        Observable<ArcGISScene> observable = Observable.create((ObservableOnSubscribe<ArcGISScene>) e -> {
            ArcGISScene arcGISScene = new ArcGISScene();
            arcGISScene.setBasemap(basemap);
            e.onNext(arcGISScene);
            e.onComplete();
        });
        return observable;
    }

    /**
     * 设比例尺范围
     * @param arcGISMap
     * @param minScale
     * @param maxScale
     * @return
     */
    public static ArcGISMap setMapScaleRang(ArcGISMap arcGISMap,double minScale,double maxScale){
        arcGISMap.setMinScale(minScale);
        arcGISMap.setMinScale(maxScale);
        return arcGISMap;
    }
    /**
     * 添加操作图层
     * @param arcGISMap
     * @param restUrl
     * @param type
     * @return
     */
    public static ArcGISMap addMapOperationalLayers(ArcGISMap arcGISMap,String restUrl, String type){
        //tpk离线图层
        if(type.equals(GIS_SERVICE_TYPE_TPK)){
            TileCache mainTileCache = new TileCache(restUrl);
            ArcGISTiledLayer theOfflinetiledLayer = new ArcGISTiledLayer(mainTileCache);
            arcGISMap.getOperationalLayers().add(theOfflinetiledLayer);

            //vtpk离线图层
        }else if(type.equals(GIS_SERVICE_TYPE_VTPK)){
            ArcGISVectorTiledLayer theOfflineVectorTiledLayer = new ArcGISVectorTiledLayer(restUrl);
            arcGISMap.getOperationalLayers().add(theOfflineVectorTiledLayer);

            //普通切片服务
        }else if(type.equals(GIS_SERVICE_TYPE_TILED)){
            ArcGISTiledLayer tiledLayer = new ArcGISTiledLayer(restUrl);
            arcGISMap.getOperationalLayers().add(tiledLayer);

            //影像服务
        }else if(type.equals(GIS_SERVICE_TYPE_IMAGE)){
            ArcGISMapImageLayer censusLayer = new ArcGISMapImageLayer(restUrl);
            arcGISMap.getOperationalLayers().add(censusLayer);
        }
        return arcGISMap;
    }

    /**
     * 添加三维场景操作图层
     * @param arcGISScene
     * @param restUrl
     * @return
     */
    public static ArcGISScene addSceneOperationalLayers(ArcGISScene arcGISScene,String restUrl){
        ArcGISSceneLayer sceneLayer = new ArcGISSceneLayer(restUrl);
        arcGISScene.getOperationalLayers().add(sceneLayer);
        return arcGISScene;
    }

    /**
     * 添加三维场景资源
     * @param arcGISScene
     * @param restUrl
     * @return
     */
    public static ArcGISScene addSceneElevationSource(ArcGISScene arcGISScene,String restUrl){
        ArcGISTiledElevationSource elevationSource = new ArcGISTiledElevationSource(restUrl);
        arcGISScene.getBaseSurface().getElevationSources().add(elevationSource);
        return arcGISScene;
    }
}
