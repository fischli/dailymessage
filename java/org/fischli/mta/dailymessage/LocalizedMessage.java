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

import java.util.Random;

public class LocalizedMessage {
    private String countryCode;
    private String[] message;
    private int lastIndex = 0;

    // TODO check on empty array
    public LocalizedMessage(String[] message) {
        this.message = message;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getRandomMessage() {
        Random rand = new Random();
        lastIndex = rand.nextInt(message.length);
        return message[lastIndex];
    }

    public int getLastIndex() {
        return lastIndex;
    }
}
