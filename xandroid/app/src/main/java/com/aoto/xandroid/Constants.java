package com.aoto.xandroid;

import android.os.Environment;

/**
 * Created by 赵德华 on 2017/8/9.
 * 系统常量
 */

public class Constants {

    public static final Boolean DEBUG=true;//DeBug模式

    public static final String OSROOTPATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String PROJECT_PATH="/xandroid";//项目根路径
    public static final String DOWNLOAD_DIR = PROJECT_PATH+"/download";//APP资源下载路径

    public static final String ORM_DBNAME = "xutils3.db";//数据操作--db名称
    public static final Integer ORM_DBVERSION = 1;//数据操作--db版本
    public static final String ORM_DBPATH = PROJECT_PATH+"/database/";//数据操作--db名称

    public static final Integer NO_NET=-80;//没有网络异常码
    public static final Long HTTP_TIMEOUT_READ=60l;//http读超时
    public static final Long HTTP_TIMEOUT_WRITE=60l;//http写超时
    public static final Long HTTP_TIMEOUT_CONNECTION=60l;//http连接超时
    public static final String HTTP_BASE_URL="http://192.168.1.5:8888/mms/rest/";//项目根路径
    public static final String HTTP_TYPE_GET = "GET";//网络请求类型——GET请求
    public static final String HTTP_TYPE_POST = "POST";//网络请求类型--POST请求
    public static final String HTTP_PARAMS_TYPE_JSON = "JSON";//http请求参数类型--JSON

    public static final Integer QRCODE_CREATE_FAIL=-1000;//二维码生成失败

    public static final String MAP_TPKPATH = OSROOTPATH+PROJECT_PATH+"/map/";//离线地图缓存路径
    public static final Integer PACK_ASSETS_FAIL=-1001;//初始化项目资源失败

}
