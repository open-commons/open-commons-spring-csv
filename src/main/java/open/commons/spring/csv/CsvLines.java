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
 * Date  : 2021. 8. 11. 오후 5:46:02
 *
 * Author: Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 */

package open.commons.spring.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import open.commons.core.utils.ExceptionUtils;

/**
 * 
 * @since 2021. 8. 11.
 * @version 0.1.0
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
public class CsvLines {

    private int totalSize;

    /** CSV 데이터 헤더 */
    @NotNull
    @NotEmpty
    private final CsvHeader[] headers;

    /** 줄 데이터 개수 */
    private final int dataLength;

    /** CSV 데이터 */
    private List<CsvLine> lines = new ArrayList<>();

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 11.		박준홍			최초 작성
     * </pre>
     *
     * @param headers
     *
     * @since 2021. 8. 11.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CsvLines(@NotNull @NotEmpty CsvHeader[] headers) {
        this.headers = headers;
        this.dataLength = headers.length;
    }

    /**
     * CSV 줄 데이터를 추가한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 11.     박준홍         최초 작성
     * </pre>
     *
     * @param line
     *            줄 데이터.
     * @throws IllegalArgumentException
     *             입력받은 데이터 길이가 컬럼개수와 다른 경우.
     *
     * @since 2021. 8. 11.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public void addData(CsvLine line) throws IllegalArgumentException {
        if (line == null || line.getData() == null) {
            throw ExceptionUtils.newException(IllegalArgumentException.class, "데이터 올바르지 않습니다. 입력=%s", line);
        }

        if (this.dataLength != line.getData().length) {
            throw ExceptionUtils.newException(IllegalArgumentException.class, "데이터 길이가 올바르지 않습니다. 예상=%,d, 입력=%,d", this.dataLength, line.getData().length);
        }

        this.lines.add(line);
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 11.		박준홍			최초 작성
     * </pre>
     * 
     * @return the dataLength
     *
     * @since 2021. 8. 11.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #dataLength
     */

    public int getDataLength() {
        return dataLength;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 11.		박준홍			최초 작성
     * </pre>
     * 
     * @return the headers
     *
     * @since 2021. 8. 11.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #headers
     */

    public CsvHeader[] getHeaders() {
        return headers;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 11.		박준홍			최초 작성
     * </pre>
     * 
     * @return the lines
     *
     * @since 2021. 8. 11.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #lines
     */

    public List<CsvLine> getLines() {
        return lines;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 11.		박준홍			최초 작성
     * </pre>
     * 
     * @return the totalSize
     *
     * @since 2021. 8. 11.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #totalSize
     */

    public int getTotalSize() {
        return totalSize;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 11.		박준홍			최초 작성
     * </pre>
     *
     * @param lines
     *            the lines to set
     *
     * @since 2021. 8. 11.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #lines
     */
    public void setLines(List<CsvLine> lines) {
        this.lines = lines;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 11.		박준홍			최초 작성
     * </pre>
     *
     * @param totalSize
     *            the totalSize to set
     *
     * @since 2021. 8. 11.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #totalSize
     */
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    /**
     *
     * @since 2021. 8. 11.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CsvLines [totalSize=");
        builder.append(totalSize);
        builder.append(", headers=");
        builder.append(Arrays.toString(headers));
        builder.append(", dataLength=");
        builder.append(dataLength);
        builder.append(", lines=");
        builder.append(lines);
        builder.append("]");
        return builder.toString();
    }

}
