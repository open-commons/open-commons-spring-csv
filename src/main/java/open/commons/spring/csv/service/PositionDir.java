/*
 * Copyright 2021 Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 *
 * This file is generated under this project, "open-commons-csv".
 *
 * Date  : 2021. 8. 11. 오후 6:04:23
 *
 * Author: Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 */

package open.commons.spring.csv.service;

import java.util.ArrayList;
import java.util.List;

import open.commons.spring.web.annotation.RequestValueConverter;
import open.commons.spring.web.annotation.RequestValueSupported;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 
 * @since 2021. 8. 11.
 * @version 0.1.0
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
@RequestValueSupported
public enum PositionDir {

    /** 위 */
    TOP("top"), //
    /** 아래 */
    BOTTOM("bottom"), //
    /** 앞 */
    FRONT("front"), //
    /** 뒤 */
    BACK("back"), //
    ;

    private String position;

    private PositionDir(String position) {
        this.position = position;
    }

    /**
     *
     * @return a string of an instance of {@link PositionDir}
     *
     * @since 2021. 8. 11.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    @JsonValue
    public String get() {
        return this.position;
    }

    /**
     * @since 2021. 8. 11.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return new StringBuffer() //
                .append(name()) //
                .append(':') //
                .append(this.position) //
                .toString();
    }

    /**
     * 
     * @param position
     *            a string for {@link PositionDir} instance.
     *
     * @return an instance of {@link PositionDir}
     *
     * @since 2021. 8. 11.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #get(String, boolean)
     */
    public static PositionDir get(String position) {
        return get(position, false);
    }

    /**
     *
     * @param position
     *            a string for an instance of {@link PositionDir}.
     * @param ignoreCase
     *            ignore <code><b>case-sensitive</b></code> or not.
     *
     * @return an instance of {@link PositionDir}
     *
     * @since 2021. 8. 11.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    @RequestValueConverter(hasIgnoreCase = true)
    public static PositionDir get(String position, boolean ignoreCase) {

        if (position == null) {
            throw new IllegalArgumentException("'position' MUST NOT be null. input: " + position);
        }

        if (ignoreCase) {
            for (PositionDir value : values()) {
                if (value.position.equalsIgnoreCase(position)) {
                    return value;
                }
            }
        } else {
            for (PositionDir value : values()) {
                if (value.position.equals(position)) {
                    return value;
                }
            }
        }

        throw new IllegalArgumentException(
                "Unexpected 'position' value of 'PositionDir'. expected: " + values0() + " & Ignore case-sensitive: " + ignoreCase + ", input: " + position);
    }

    private static List<String> values0() {

        List<String> valuesStr = new ArrayList<>();

        for (PositionDir value : values()) {
            valuesStr.add(value.get());
        }

        return valuesStr;
    }

}
