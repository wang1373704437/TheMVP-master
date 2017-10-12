/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kymjs.themvp.samples.demo1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.view.View;

import com.kymjs.themvp.presenter.ActivityPresenter;
import com.kymjs.themvp.samples.R;
import com.kymjs.themvp.samples.demo6.Demo6Activity;

/**
 * 在这里做业务逻辑操作，通过viewDelegate操作View层控件
 *
 * @author kymjs (http://www.kymjs.com/) on 10/23/15.
 */
public class SimpleActivity extends ActivityPresenter<SimpleDelegate> implements View
        .OnClickListener {

    private static int count = 0;

    @Override
    protected Class<SimpleDelegate> getDelegateClass() {
        return SimpleDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        //可以同时对多个控件设置同一个点击事件,后面id参数可以传多个
        viewDelegate.setOnClickListener(this, R.id.button1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                viewDelegate.setText("你点击了button");

                test();
                break;
        }
    }

    private void test() {
        count++;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);


        for (int i = 0; i < 8; i++) {

            Intent activityIntent = new Intent(
                    SimpleActivity.this,
                    Demo6Activity.class);
            PendingIntent intent = PendingIntent
                    .getActivity(
                            this,
                            i,
                            activityIntent,
                            PendingIntent.FLAG_CANCEL_CURRENT);
            Notification notification = builder

                    .setContentTitle("这是通知标题" + i)

                    .setContentText("这是通知内容" + i)

                    .setWhen(System.currentTimeMillis())

                    .setSmallIcon(R.mipmap.ic_launcher)

                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))

                    .setContentIntent(intent)

                    .build();


            manager.notify(i, notification);
        }
    }


}