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
 * Date  : 2021. 8. 13. 오후 5:32:46
 *
 * Author: Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 */

package open.commons.spring.csv;

import java.util.ArrayList;
import java.util.List;

import open.commons.spring.web.annotation.RequestValueConverter;
import open.commons.spring.web.annotation.RequestValueSupported;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * CSV 컬럼 데이터 타입
 * 
 * @since 2021. 8. 13.
 * @version 0.1.0
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
@RequestValueSupported
public enum ColumnDataType {

    /** 'unknow' */
    GENERAL("general"), //
    /** 'number' */
    INT("int"), //
    /** 'number' */
    NUM("num"), //
    /** 'string' */
    STR("str"), //
    ;

    private String type;

    private ColumnDataType(String type) {
        this.type = type;
    }

    /**
     *
     * @return a string of an instance of {@link ColumnDataType}
     *
     * @since 2021. 8. 13.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    @JsonValue
    public String get() {
        return this.type;
    }

    /**
     * @since 2021. 8. 13.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return new StringBuffer() //
                .append(name()) //
                .append(':')//
                .append(get()) //
                .toString();
    }

    /**
     * 
     * @param type
     *            a string for {@link ColumnDataType} instance.
     *
     * @return an instance of {@link ColumnDataType}
     *
     * @since 2021. 8. 13.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #get(String, boolean)
     */
    public static ColumnDataType get(String type) {
        return get(type, false);
    }

    /**
     *
     * @param type
     *            a string for an instance of {@link ColumnDataType}.
     * @param ignoreCase
     *            ignore <code><b>case-sensitive</b></code> or not.
     *
     * @return an instance of {@link ColumnDataType}
     *
     * @since 2021. 8. 13.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    @RequestValueConverter(hasIgnoreCase = true)
    public static ColumnDataType get(String type, boolean ignoreCase) {

        if (type == null) {
            throw new IllegalArgumentException("'type' MUST NOT be null. input: " + type);
        }

        if (ignoreCase) {
            for (ColumnDataType value : values()) {
                if (value.type.equalsIgnoreCase(type)) {
                    return value;
                }
            }
        } else {
            for (ColumnDataType value : values()) {
                if (value.type.equals(type)) {
                    return value;
                }
            }
        }

        throw new IllegalArgumentException("Unexpected 'type' value of 'ColumnDataType'. expected: " + values0() + " & Ignore case-sensitive: " + ignoreCase + ", input: " + type);
    }

    private static List<String> values0() {

        List<String> valuesStr = new ArrayList<>();

        for (ColumnDataType value : values()) {
            valuesStr.add(value.get());
        }

        return valuesStr;
    }

}
