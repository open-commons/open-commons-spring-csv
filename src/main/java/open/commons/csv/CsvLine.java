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
 * Date  : 2021. 8. 13. 오후 5:44:17
 *
 * Author: Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 */

package open.commons.csv;

import java.util.Arrays;

import javax.validation.constraints.Min;

/**
 * CSV 파일 줄 데이터
 * 
 * @since 2021. 8. 13.
 * @version _._._
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
public class CsvLine {

    /** 줄 번호 */
    @Min(1)
    private Integer lineNumber;

    /** 줄 데이터 */
    private Object[] data;

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
     * @param lineNumber
     *
     * @since 2021. 8. 13.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CsvLine(@Min(1) Integer lineNumber) {
        this.lineNumber = lineNumber;
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
     * @return the data
     *
     * @since 2021. 8. 13.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #data
     */

    public Object[] getData() {
        return data;
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
     * @return the lineNumber
     *
     * @since 2021. 8. 13.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #lineNumber
     */

    public Integer getLineNumber() {
        return lineNumber;
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
     * @param data
     *            the data to set
     *
     * @since 2021. 8. 13.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #data
     */
    public void setData(Object[] data) {
        this.data = data;
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
     * @param lineNumber
     *            the lineNumber to set
     *
     * @since 2021. 8. 13.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #lineNumber
     */
    public void setLineNumber(@Min(1) Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     *
     * @since 2021. 8. 13.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CsvLine [lineNumber=");
        builder.append(lineNumber);
        builder.append(", data=");
        builder.append(Arrays.toString(data));
        builder.append("]");
        return builder.toString();
    }

}
