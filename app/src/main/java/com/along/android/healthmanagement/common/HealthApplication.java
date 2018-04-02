package com.along.android.healthmanagement.common;

import android.util.Log;
import android.widget.Toast;

import com.along.android.healthmanagement.apis.Apis;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.network.BaseResponse;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orm.SugarApp;


/**
 * Created by fenghongyu on 18/3/12.
 */

public class HealthApplication extends SugarApp {
    public static final String TAG = HealthApplication.class.getSimpleName();

    private HealthApplication sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
        sApp = this;
    }
}
