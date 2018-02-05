package com.thw.file;

import org.xutils.common.Callback;

/**
 * Created by Administrator on 2016/12/8.
 */

public class MyCallBack<ResultType> implements Callback.CommonCallback<ResultType> {
    @Override
    public void onSuccess(ResultType result) {
    //
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
    //
    }

    @Override
    public void onCancelled(CancelledException cex) {
    }

    @Override
    public void onFinished() {
    }
}