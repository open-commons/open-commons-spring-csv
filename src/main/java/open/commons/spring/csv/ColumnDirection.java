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
 * Date  : 2021. 8. 15. 오후 5:35:17
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
 * 
 * @since 2021. 8. 15.
 * @version 0.1.0
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
@RequestValueSupported
public enum ColumnDirection {

    /** 오름차순 (asc) */
    ASC("asc"), //
    /** 내림차순 (desc) */
    DESC("desc"), //
    ;

    private String dir;

    private ColumnDirection(String dir) {
        this.dir = dir;
    }

    /**
     *
     * @return a string of an instance of {@link ColumnDirection}
     *
     * @since 2021. 8. 15.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    @JsonValue
    public String get() {
        return this.dir;
    }

    /**
     * @since 2021. 8. 15.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return String.join(":", name(), this.dir);
    }

    /**
     * 
     * @param dir
     *            a string for {@link ColumnDirection} instance.
     *
     * @return an instance of {@link ColumnDirection}
     *
     * @since 2021. 8. 15.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #get(String, boolean)
     */
    public static ColumnDirection get(String dir) {
        return get(dir, false);
    }

    /**
     *
     * @param dir
     *            a string for an instance of {@link ColumnDirection}.
     * @param ignoreCase
     *            ignore <code><b>case-sensitive</b></code> or not.
     *
     * @return an instance of {@link ColumnDirection}
     *
     * @since 2021. 8. 15.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    @RequestValueConverter(hasIgnoreCase = true)
    public static ColumnDirection get(String dir, boolean ignoreCase) {

        if (dir == null) {
            throw new IllegalArgumentException("'dir' MUST NOT be null. input: " + dir);
        }

        if (ignoreCase) {
            for (ColumnDirection value : values()) {
                if (value.dir.equalsIgnoreCase(dir)) {
                    return value;
                }
            }
        } else {
            for (ColumnDirection value : values()) {
                if (value.dir.equals(dir)) {
                    return value;
                }
            }
        }

        throw new IllegalArgumentException("Unexpected 'dir' value of 'ColumnDirection'. expected: " + values0() + " & Ignore case-sensitive: " + ignoreCase + ", input: " + dir);
    }

    private static List<String> values0() {

        List<String> valuesStr = new ArrayList<>();

        for (ColumnDirection value : values()) {
            valuesStr.add(value.get());
        }

        return valuesStr;
    }

}
