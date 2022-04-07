/*
 * Copyright 2021 Park Jun-Hong_(parkjunhong77@gmail.com)
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
 * Date  : 2021. 8.10. 오후 5:28:42
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
 * @since 2021. 8.10.
 * @version 0.1.0
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
@RequestValueSupported
public enum ColumnOp {

    /** 'Equal' */
    EQ("="), //
    /** 'Not Equal' */
    NE("!="), //
    /** 'Greater or Equal' */
    GE(">="), //
    /** 'Greater Than' */
    GT(">"), //
    /** 'Less or Equal' */
    LE("<="), //
    /** 'Less Than' */
    LT("<"), //
    /** 'Contains', only for {@link ColumnDataType#STR} */
    CO("()"), //
    /** 'Starts with', only for {@link ColumnDataType#STR} */
    ST("(="), //
    /** 'Ends with', only for {@link ColumnDataType#STR} */
    ED(")="), //
    /** 'Regular Expression' */
    RX("?="), //
    ;

    private String op;

    private ColumnOp(String op) {
        this.op = op;
    }

    /**
     *
     * @return a string of an instance of {@link ColumnOp}
     *
     * @since 2021. 8.10.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    @JsonValue
    public String get() {
        return this.op;
    }

    /**
     * @since 2021. 8.10.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return String.join(":", name(), this.op);
    }

    /**
     * 
     * @param op
     *            a string for {@link ColumnOp} instance.
     *
     * @return an instance of {@link ColumnOp}
     *
     * @since 2021. 8.10.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #get(String, boolean)
     */
    public static ColumnOp get(String op) {
        return get(op, false);
    }

    /**
     *
     * @param op
     *            a string for an instance of {@link ColumnOp}.
     * @param ignoreCase
     *            ignore <code><b>case-sensitive</b></code> or not.
     *
     * @return an instance of {@link ColumnOp}
     *
     * @since 2021. 8.10.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    @RequestValueConverter(hasIgnoreCase = true)
    public static ColumnOp get(String op, boolean ignoreCase) {

        if (op == null) {
            throw new IllegalArgumentException("'op' MUST NOT be null. input: " + op);
        }

        if (ignoreCase) {
            for (ColumnOp value : values()) {
                if (value.op.equalsIgnoreCase(op)) {
                    return value;
                }
            }
        } else {
            for (ColumnOp value : values()) {
                if (value.op.equals(op)) {
                    return value;
                }
            }
        }

        throw new IllegalArgumentException("Unexpected 'op' value of 'ColumnOp'. expected: " + values0() + " & Ignore case-sensitive: " + ignoreCase + ", input: " + op);
    }

    private static List<String> values0() {

        List<String> valuesStr = new ArrayList<>();

        for (ColumnOp value : values()) {
            valuesStr.add(value.get());
        }

        return valuesStr;
    }

}
