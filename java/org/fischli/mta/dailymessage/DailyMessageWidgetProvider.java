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

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

public class DailyMessageWidgetProvider extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        DailyMessage.debug(this.getClass());
        //PersistentSettings settings = PersistentSettings.getInstance(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        DailyMessage.debug(this.getClass());

        // initializing widget layout
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        PersistentSettings settings = PersistentSettings.getInstance(context);
        if (!settings.getMessageList().isEmpty()) {
            CharSequence msg = settings.getMessageList().getRandomMessage();
            DailyMessage.debug(this.getClass(), '"' + msg.toString() + '"');
            // on android 2.3, msg as String doesn't update text
            remoteViews.setTextViewText(R.id.daily_message, msg);
        }

        // register for button event
        remoteViews.setOnClickPendingIntent(R.id.daily_message, DailyMessage.buildButtonPendingIntent(context));

        // request for widget update
        DailyMessage.pushWidgetUpdate(context, remoteViews);
    }
}
