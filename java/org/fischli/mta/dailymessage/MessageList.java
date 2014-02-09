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

import java.util.ArrayList;
import java.util.Random;

public class MessageList {
    private ArrayList<LocalizedMessage> localizedMessageArrayList;
    private int lastMessageArrayIndex;

    public MessageList() {
        localizedMessageArrayList = new ArrayList<LocalizedMessage>();
    }

    public void addMessageArray(String[] messages, String countryCode) {
        if (messages.length > 0) {
            LocalizedMessage localizedMessage = new LocalizedMessage(messages);
            localizedMessage.setCountryCode(countryCode);
            localizedMessageArrayList.add(localizedMessage);
            DailyMessage.debug(this.getClass(), countryCode + "[" + messages.length + "]");
        }
    }

    public String getRandomMessage() {
        Random rand = new Random();
        lastMessageArrayIndex = rand.nextInt(localizedMessageArrayList.size());
        return localizedMessageArrayList.get(lastMessageArrayIndex).getRandomMessage();
    }

    public int getLastMessageIndex() {
        return localizedMessageArrayList.get(lastMessageArrayIndex).getLastIndex();
    }

    public String getLastCountryCode() {
        return localizedMessageArrayList.get(lastMessageArrayIndex).getCountryCode();
    }

    public void clearMessageList() {
        localizedMessageArrayList = new ArrayList<LocalizedMessage>();
    }

    public boolean isEmpty() {
        boolean flag = false;
        if (localizedMessageArrayList.size() == 0) {
            flag = true;
        }
        return flag;
    }
}
