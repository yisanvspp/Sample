/*
 * Copyright 2014-2017 Eduard Ereza Martínez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yisan.sample.crash;

import android.widget.Button;
import android.widget.TextView;

import com.yisan.base.annotation.ViewLayoutInject;
import com.yisan.base.base.BaseActivity;
import com.yisan.crash.CustomActivityOnCrash;
import com.yisan.crash.config.CaocConfig;
import com.yisan.sample.R;


/**
 * 自定义crash界面
 *
 * @author wzh
 * @packageName com.yisan.sample.crash
 * @fileName CustomErrorActivity.java
 * @date 2019-12-05  下午 4:14
 */
@ViewLayoutInject(R.layout.activity_custom_error)
public class CustomErrorActivity extends BaseActivity {

    @Override
    protected void afterBindView() {
        TextView errorDetailsText = findViewById(R.id.error_details);
        errorDetailsText.setText(CustomActivityOnCrash.getStackTraceFromIntent(getIntent()));

        Button restartButton = findViewById(R.id.restart_button);

        final CaocConfig config = CustomActivityOnCrash.getConfigFromIntent(getIntent());

        if (config == null) {
            //This should never happen - Just finish the activity to avoid a recursive crash.
            finish();
            return;
        }

        if (config.isShowRestartButton() && config.getRestartActivityClass() != null) {
            restartButton.setText(R.string.restart_app);
            restartButton.setOnClickListener(v -> CustomActivityOnCrash.restartApplication(CustomErrorActivity.this, config));
        } else {
            restartButton.setOnClickListener(v -> CustomActivityOnCrash.closeApplication(CustomErrorActivity.this, config));
        }
    }
}
