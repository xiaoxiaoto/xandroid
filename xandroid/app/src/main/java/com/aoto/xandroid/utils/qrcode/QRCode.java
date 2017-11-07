package com.aoto.xandroid.utils.qrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.aoto.xandroid.Constants;
import com.aoto.xandroid.utils.Tools;
import com.aoto.xandroid.utils.exception.CustomException;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * Created by Administrator on 2017/10/10.
 */

public class QRCode {

    private static Context context;
    /**
     * 初始化上下文
     * @param c
     */
    public static void init(Context c){
        context=c.getApplicationContext();
    }

    public static Bitmap createQRCodeWithLogo(String content, Integer logo){
        if(Tools.isNotBlank(content)&&context!=null){
            Bitmap logoBitmap = BitmapFactory.decodeResource(context.getResources(), logo);
            return QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(context, 150), Color.parseColor("#ff0000"), logoBitmap);
        }
        throw new CustomException(Constants.QRCODE_CREATE_FAIL,"二维码生成失败");
    }
    public static Bitmap  createQRCodeWithoutLogo(String content){
        if(Tools.isNotBlank(content)&&context!=null){
            return QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(context, 150), Color.parseColor("#ff0000"));
        }
        throw new CustomException(Constants.QRCODE_CREATE_FAIL,"二维码生成失败");
    }

}
