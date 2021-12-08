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
 * Date  : 2021. 12. 8. 오후 6:15:48
 *
 * Author: Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 */

package open.commons.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import javax.validation.constraints.NotNull;

import open.commons.utils.ArrayUtils;

/**
 * CSV 파일의 일부만 제공하는 클래스.
 * 
 * @since 2021. 8. 13.
 * @version _._._
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
public class CsvFileSampling extends AbstractCsvFileLoad {

    /** CSV 파일 데이터 */
    private List<Object[]> data = new ArrayList<>();

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 13.     박준홍         최초 작성
     * </pre>
     *
     * @param filepath
     * @param lineNumber
     * 
     * @since 2021. 8. 13.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CsvFileSampling(@NotNull String filepath) {
        super(filepath);
    }

    /**
     * 줄 데이터를 추가한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 13.     박준홍         최초 작성
     * </pre>
     *
     * @param data
     *
     * @since 2021. 8. 13.
     * 
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public void addData(Object[] data) {
        if (data == null) {
            return;
        }

        Object[] updated = updateColumnDataType(data);
        this.data.add(updated);
    }

    /**
     *
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 13.     박준홍         최초 작성
     * </pre>
     * 
     * @return the data
     *
     * @since 2021. 8. 13.
     * 
     * @see #data
     */
    public List<Object[]> getData() {
        return data;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 13.     박준홍         최초 작성
     * </pre>
     *
     * @param data
     *            the data to set
     *
     * @since 2021. 8. 13.
     * 
     * @see #data
     */
    public void setData(List<Object[]> data) {
        this.data.clear();
        if (data != null) {
            for (Object[] d : data) {
                addData(d);
            }
        }
    }

    /**
     * @since 2021. 8. 13.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CsvFileSampling [filepath=");
        builder.append(filepath);
        builder.append(", headers=");
        builder.append(Arrays.toString(headers));
        builder.append(", data=");
        builder.append(data);
        builder.append("]");
        return builder.toString();
    }

    /**
     * 실제 데이터를 확인하여 컬럼 데이터 타입을 갱신한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     *
     * @param data
     *
     * @since 2021. 8. 15.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    private Object[] updateColumnDataType(Object[] data) {

        Object[] updated = new Object[data.length];

        // Header 데이터 타입 갱신
        CsvHeader[] headers = getHeaders();
        CsvHeader header = null;

        ColumnDataType type = null;
        Object value = null;
        Object realValue = null;

        int colIndex = -1;
        for (Entry<Integer, Object> entry : ArrayUtils.entrySet(data)) {
            colIndex = entry.getKey();
            value = entry.getValue();
            if (value == null) {
                type = ColumnDataType.GENERAL;
                realValue = null;
            } else {
                try {
                    realValue = Double.parseDouble(value.toString());
                    type = ColumnDataType.NUM;

                    try {
                        realValue = Long.parseLong(value.toString());
                        type = ColumnDataType.INT;
                    } catch (NumberFormatException e) {
                        // 'type' is not changed
                    }

                } catch (NumberFormatException e) {
                    type = ColumnDataType.STR;
                    realValue = value.toString();
                }
            }

            header = headers[colIndex];
            if (!header.getDataType().equals(type)) {
                boolean update = false;
                switch (header.getDataType()) {
                    case INT:
                        if (type == ColumnDataType.NUM || type == ColumnDataType.STR) {
                            update = true;
                        }
                        break;
                    case NUM:
                        if (type == ColumnDataType.STR) {
                            update = true;
                        }
                        break;
                    case STR:
                        break;
                    case GENERAL:
                        update = true;
                        break;
                }

                if (update) {
                    header.setDataType(type);
                    updated[colIndex] = realValue;
                }
            }
            updated[colIndex] = realValue;
        }

        return updated;
    }

}
