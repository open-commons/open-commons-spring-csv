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
 * Date  : 2021. 8. 13. 오후 5:41:43
 *
 * Author: Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 */

package open.commons.csv;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * CSV 헤더 정보 클래스.
 * 
 * @since 2021. 8. 13.
 * @version 0.1.0
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
public class CsvHeader {

    /** 데이터 헤더 이름 */
    private final String header;
    /** 컬럼 데이터 타입 */
    private ColumnDataType dataType;

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 13.		박준홍			최초 작성
     * </pre>
     *
     * @param header
     *
     * @since 2021. 8. 13.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CsvHeader(String header) {
        this(header, ColumnDataType.GENERAL);
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 13.		박준홍			최초 작성
     * </pre>
     *
     * @param header
     * @param dataType
     *
     * @since 2021. 8. 13.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    @JsonCreator
    public CsvHeader(String header, ColumnDataType dataType) {
        this.header = header;
        this.dataType = dataType;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 13.		박준홍			최초 작성
     * </pre>
     * 
     * @return the dataType
     *
     * @since 2021. 8. 13.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #dataType
     */

    public ColumnDataType getDataType() {
        return dataType;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 13.		박준홍			최초 작성
     * </pre>
     * 
     * @return the header
     *
     * @since 2021. 8. 13.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #header
     */

    public String getHeader() {
        return header;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 13.		박준홍			최초 작성
     * </pre>
     *
     * @param dataType
     *            the dataType to set
     *
     * @since 2021. 8. 13.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #dataType
     */
    public void setDataType(ColumnDataType dataType) {
        this.dataType = dataType;
    }

    /**
     *
     * @since 2021. 8. 13.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CsvHeader [header=");
        builder.append(header);
        builder.append(", dataType=");
        builder.append(dataType);
        builder.append("]");
        return builder.toString();
    }

}
