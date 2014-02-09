/*
 * Copyright (c) 2014 Bernhard Fischer <bernhard@fischli.org>
 * Schoenstatt Family League <http://www.schoenstatt.org/>
 *
 * This file is part of Daily Message
 *
 * Daily Message is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Daily Message is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Daily Message. If not, see <http://www.gnu.org/licenses/>.
 */

package org.fischli.mta.dailymessage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class DailyMessageWidgetConfigure extends Activity {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    public DailyMessageWidgetConfigure() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setResult(RESULT_CANCELED);

        setContentView(R.layout.widget_configure);

        // Bind the action for the save button.
        findViewById(R.id.button).setOnClickListener(mOnClickListener);

        findViewById(R.id.aboutView).setOnClickListener(aboutOnClickListener);

        if (!DailyMessage.isDebuggable()) {
            findViewById(R.id.debuginfo).setVisibility(View.GONE);
        }

        final Context context = DailyMessageWidgetConfigure.this;
        PersistentSettings settings = PersistentSettings.getInstance(context);
        for (CountryCodeElement cce : CountryCodeMap.getInstance().getCountryCodeElements()) {
            ((CheckBox) findViewById(cce.getCheckBoxId())).setChecked(settings.isEnabled(cce.getCountryCode()));
        }

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = DailyMessageWidgetConfigure.this;
            storeCurrentConfig();

            // http://stackoverflow.com/questions/13694186/how-to-create-an-app-widget-with-a-configuration-activity-and-update-it-for-the
            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE, null, context, DailyMessageWidgetProvider.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{mAppWidgetId});
            sendBroadcast(intent);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    private void storeCurrentConfig() {
        boolean flag;
        boolean alreadySet = false;
        final Context context = DailyMessageWidgetConfigure.this;

        PersistentSettings settings = PersistentSettings.getInstance(context);
        settings.getMessageList().clearMessageList();

        settings.setShowIndexFlag(((CheckBox) findViewById(R.id.show_index)).isChecked());

        for (CountryCodeElement cce : CountryCodeMap.getInstance().getCountryCodeElements()) {
            flag = ((CheckBox) findViewById(cce.getCheckBoxId())).isChecked();
            settings.storeCountryCode(cce.getCountryCode(), flag);
            if (flag) {
                settings.getMessageList().addMessageArray(context.getResources().getStringArray(cce.getArrayId()), cce.getCountryCode().toString());
                alreadySet = true;
            }
        }

        // set language to default if still unset
        if (!alreadySet) {
            CountryCodeElement cce = CountryCodeMap.getInstance().getCountryCodeElements().get(0);
            settings.storeCountryCode(cce.getCountryCode(), true);
            settings.getMessageList().addMessageArray(context.getResources().getStringArray(cce.getArrayId()), cce.getCountryCode().toString());
        }
    }

    View.OnClickListener aboutOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = DailyMessageWidgetConfigure.this;
            String version = "?";
            String build = "?";
            try {
                String pn = context.getPackageName();
                PackageManager pm = context.getPackageManager();
                PackageInfo pi;
                if (pm != null) {
                    pi = pm.getPackageInfo(pn, 0);
                    version = pi.versionName;
                    build = Integer.toString(pi.versionCode);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            String msg = getString(R.string.about_dialog_message) + '\n' +
                    getString(R.string.app_author) + '\n' +
                    getString(R.string.app_email) + '\n' +
                    '\n' +
                    getString(R.string.version) + " " + version + " " +
                    getString(R.string.build) + " " + build + '\n' +
                    getString(R.string.app_url);
            Dialog about = new AlertDialog.Builder(DailyMessageWidgetConfigure.this)
                    .setIcon(R.drawable.schoenstatt)
                    .setTitle(R.string.about_info)
                    .setPositiveButton(R.string.about_dialog_ok, null)
                    .setMessage(msg)
                    .create();
            about.show();
            DailyMessage.touchDebugCounter();
            if (DailyMessage.isDebuggable()) {
                findViewById(R.id.debuginfo).setVisibility(View.VISIBLE);
            }

        }
    };

}
