package com.aoto.xandroid.utils.file;

import android.content.Context;
import android.content.res.AssetManager;

import com.aoto.xandroid.Constants;
import com.aoto.xandroid.utils.exception.CustomException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/10/12.
 */

public class FileUtils {
    private static Context context;

    /**
     * 初始化上下文
     * @param c
     */
    public static void init(Context c){
        context=c.getApplicationContext();
    }

    /**
     * 创建文件
     * @param destFileName
     * @return
     */
    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if(file.exists()) {
            return true;
        }
        if (destFileName.endsWith(File.separator)) {
            return false;
        }
        //判断目标文件所在的目录是否存在
        if(!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            if(!file.getParentFile().mkdirs()) {
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建文件夹
     * @param destDirName
     * @return
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            return true;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 创建临时文件
     * @param prefix
     * @param suffix
     * @param dirName
     * @return
     */
    public static String createTempFile(String prefix, String suffix, String dirName) {
        File tempFile = null;
        if (dirName == null) {
            try{
                //在默认文件夹下创建临时文件
                tempFile = File.createTempFile(prefix, suffix);
                //返回临时文件的路径
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            File dir = new File(dirName);
            //如果临时文件所在目录不存在，首先创建
            if (!dir.exists()) {
                if (!FileUtils.createDir(dirName)) {
                    return null;
                }
            }
            try {
                //在指定目录下创建临时文件
                tempFile = File.createTempFile(prefix, suffix, dir);
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 打包资源文件到外部设备
     * @param fileName
     * @param destPath
     * @return
     */
    public static boolean packAssetsFiles2ExternalStorage ( String fileName, String destPath) {
        InputStream in = null;
        OutputStream out = null;
        boolean dir = createDir(destPath);
        if(dir){
            try {
                AssetManager assetManager = context.getAssets();
                in = assetManager.open(fileName);
                File outFile = new File(destPath+fileName);
                out = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                out.flush();//刷新缓冲区
                return true;
            } catch (IOException e) {
                throw new CustomException(Constants.PACK_ASSETS_FAIL,"初始化项目资源失败");
            }finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        throw new CustomException(Constants.PACK_ASSETS_FAIL,"初始化项目资源失败");
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        throw new CustomException(Constants.PACK_ASSETS_FAIL,"初始化项目资源失败");
                    }
                }
            }
        }
        throw new CustomException(Constants.PACK_ASSETS_FAIL,"初始化项目资源失败");
    }

}
