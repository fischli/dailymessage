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

public class CountryCodeMap {
    private static CountryCodeMap instance = null;
    private ArrayList<CountryCodeElement> countryCodeElements;

    public static enum CC {DE, EN, ES, FR, IT, PT}

    private CountryCodeMap() {
        countryCodeElements = new ArrayList<CountryCodeElement>();
        // the first element will also be used as default
        countryCodeElements.add(new CountryCodeElement(CC.EN, R.id.ccode_en, R.array.pjk_en));
        countryCodeElements.add(new CountryCodeElement(CC.DE, R.id.ccode_de, R.array.pjk_de));
        countryCodeElements.add(new CountryCodeElement(CC.ES, R.id.ccode_es, R.array.pjk_es));
        countryCodeElements.add(new CountryCodeElement(CC.FR, R.id.ccode_fr, R.array.pjk_fr));
        countryCodeElements.add(new CountryCodeElement(CC.IT, R.id.ccode_it, R.array.pjk_it));
        countryCodeElements.add(new CountryCodeElement(CC.PT, R.id.ccode_pt, R.array.pjk_pt));
    }

    public static synchronized CountryCodeMap getInstance() {
        if (instance == null) {
            instance = new CountryCodeMap();
        }
        return instance;
    }

    public ArrayList<CountryCodeElement> getCountryCodeElements() {
        return countryCodeElements;
    }

}
