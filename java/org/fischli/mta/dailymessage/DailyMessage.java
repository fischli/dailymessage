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

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class DailyMessage {
    final static String WIDGET_UPDATE_ACTION = "org.fischli.mta.dailymessage.intent.action.UPDATE_WIDGET";
    final static int NESTED_TRACE_LEVELS = 5;
    static Boolean allowDebug = Boolean.FALSE;
    private static int debugCounter = 4;

    private DailyMessage() {
        if (allowDebug == null) {
            allowDebug = Boolean.FALSE;
            if (Build.BRAND.equalsIgnoreCase("generic")) allowDebug = Boolean.TRUE;
            // if (Build.SERIAL.equals("FA399W123456")) allowDebug = Boolean.TRUE;
        }
    }

    public static void debug(Class c) {
        logDebugMsg(c, "");
    }

    public static void debug(Class c, String msg) {
        logDebugMsg(c, " " + msg);
    }

    private static void logDebugMsg(Class c, String msg) {
        if (isDebuggable()) {
            /**
             * TODO: prefix debug message by version code and build
             * to demonstrate lifecycle on widget update or installation
             */
            // Log.d(c.getPackage().getName() + "." + c.getSimpleName(), getMethod() + msg);
            Log.d(c.getSimpleName(), getMethod() + msg);
        }
    }

    public static boolean isDebuggable() {
        return (allowDebug);
    }

    public static void touchDebugCounter() {
        if (debugCounter > 0) debugCounter--;
        if (debugCounter <= 0) allowDebug = Boolean.TRUE;
    }

    private static String getMethod() {
        return trace(Thread.currentThread().getStackTrace(), NESTED_TRACE_LEVELS);
    }

    private static String trace(StackTraceElement e[], int level) {
        if (e != null && e.length >= level) {
            StackTraceElement s = e[level];
            if (s != null) {
                return s.getMethodName() + "()";
            }
        }
        return null;
    }

    public static PendingIntent buildButtonPendingIntent(Context context) {
        DailyMessage.debug(DailyMessage.class);
        // initiate widget update request
        Intent intent = new Intent();
        intent.setAction(DailyMessage.WIDGET_UPDATE_ACTION);
        return PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        PersistentSettings settings = PersistentSettings.getInstance(context);
        if (!settings.getMessageList().isEmpty()) {
            int index = settings.getMessageList().getLastMessageIndex() + 1;
            CharSequence text = settings.getMessageList().getLastCountryCode() + "/" + index;
            DailyMessage.debug(DailyMessage.class, "toast show " + text.toString());
            if (settings.showIndex()) {
                Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        ComponentName myWidget = new ComponentName(context, DailyMessageWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        if (manager != null) {
            manager.updateAppWidget(myWidget, remoteViews);
        }
    }
}
