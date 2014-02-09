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

public class CountryCodeElement {
    private CountryCodeMap.CC countryCode;
    private int checkBoxId;
    private int arrayId;

    public CountryCodeElement(CountryCodeMap.CC countryCode, int checkBoxId, int arrayId) {
        this.countryCode = countryCode;
        this.checkBoxId = checkBoxId;
        this.arrayId = arrayId;
    }

    public CountryCodeMap.CC getCountryCode() {
        return countryCode;
    }

    public int getCheckBoxId() {
        return checkBoxId;
    }

    public int getArrayId() {
        return arrayId;
    }

}
