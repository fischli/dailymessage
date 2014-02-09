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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class DailyMessageWidgetIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (DailyMessage.WIDGET_UPDATE_ACTION.equals(action)) {
            updateWidgetListener(context);
        }
    }

    private void updateWidgetListener(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        PersistentSettings settings = PersistentSettings.getInstance(context);
        if (!settings.getMessageList().isEmpty()) {
            CharSequence msg = settings.getMessageList().getRandomMessage();
            DailyMessage.debug(this.getClass(), '"' + msg.toString() + '"');
            // on android 2.3, msg as String doesn't update text
            remoteViews.setTextViewText(R.id.daily_message, msg);
        }

        // re-registering for click listener
        remoteViews.setOnClickPendingIntent(R.id.daily_message, DailyMessage.buildButtonPendingIntent(context));

        DailyMessage.pushWidgetUpdate(context.getApplicationContext(), remoteViews);
    }
}
