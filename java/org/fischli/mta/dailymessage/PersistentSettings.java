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

import android.content.Context;
import android.content.SharedPreferences;

public class PersistentSettings {
    private final String PREFS = "org.fischli.mta.dailymessage";
    private static PersistentSettings instance = null;
    private Context context;
    private boolean showIndexFlag = false;
    private MessageList messageList = new MessageList();

    private PersistentSettings(Context context) {
        this.context = context;
        loadSettings();
    }

    public static synchronized PersistentSettings getInstance(Context context) {
        if (instance == null) {
            instance = new PersistentSettings(context);
        }
        return instance;
    }

    private void loadSettings() {
        for (CountryCodeElement cce : CountryCodeMap.getInstance().getCountryCodeElements()) {
            if (isEnabled(cce.getCountryCode())) {
                getMessageList().
                        addMessageArray(context.getResources().getStringArray(cce.getArrayId()),
                                cce.getCountryCode().toString());
            }
        }
    }

    public void setShowIndexFlag(boolean flag) {
        showIndexFlag = flag;
    }

    public boolean showIndex() {
        return showIndexFlag;
    }

    public boolean isEnabled(CountryCodeMap.CC cc) {
        boolean flag;
        try {
            SharedPreferences settings = context.getSharedPreferences(PREFS, 0);
            flag = settings.getBoolean(buildPref(cc), false);
        } catch (NullPointerException e) {
            flag = false;
        }
        DailyMessage.debug(this.getClass(), cc.toString() + "=" + Boolean.toString(flag));
        return flag;
    }

    public void storeCountryCode(CountryCodeMap.CC cc, boolean flag) {
        DailyMessage.debug(this.getClass(), cc.toString() + "-" + Boolean.toString(flag));
        SharedPreferences settings = context.getSharedPreferences(PREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(buildPref(cc), flag);
        editor.apply();
    }

    private String buildPref(CountryCodeMap.CC cc) {
        return "countrycode_" + cc.toString();
    }

    public MessageList getMessageList() {
        return messageList;
    }
}
