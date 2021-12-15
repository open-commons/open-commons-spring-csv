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
 * Date  : 2021. 12. 8. 오후 5:41:04
 *
 * Author: Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 */

package open.commons.csv;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import open.commons.csv.utils.CommonsUtils;
import open.commons.spring.web.servlet.InternalServerException;
import open.commons.utils.ExceptionUtils;

import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * 
 * @since 2021. 12. 8.
 * @version 0.1.0
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
public abstract class AbstractCsvFileLoad {

    protected static final ColumnDataType[] csvColumnDataType = ColumnDataType.values();

    /** 파일 경로 */
    @NotNull(message = "파일 경로를 반드시 존재해야 합니다.")
    protected final String filepath;

    /**
     * 헤더 정보<br>
     * 별도의 헤더 정보가 없는 경우 index 값.
     */
    @NotNull
    protected CsvHeader[] headers;

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 10.     박준홍         최초 작성
     * </pre>
     * 
     * @param filepath
     *            파일 경로
     * @since 2021. 8. 10.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public AbstractCsvFileLoad(@NotNull String filepath) {
        this.filepath = filepath;
    }

    /**
     * 헤더 정보를 추가한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 10.     박준홍         최초 작성
     * </pre>
     *
     * @param headers
     *
     * @since 2021. 8. 10.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public void addHeaders(String... headers) {
        if (headers == null) {
            this.headers = new CsvHeader[0];
        } else {
            this.headers = CommonsUtils.csvHeaders(headers);
        }
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
     * @return the csvcolumndatatype
     *
     * @since 2021. 8. 13.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see #csvcolumndatatype
     */
    public ColumnDataType[] getCsvColumnDataType() {
        return csvColumnDataType;
    }

    /**
     *
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 10.     박준홍         최초 작성
     * </pre>
     * 
     * @return the filepath
     *
     * @since 2021. 8. 10.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see #filepath
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     *
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 10.     박준홍         최초 작성
     * </pre>
     * 
     * @return the headers
     *
     * @since 2021. 8. 10.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see #headers
     */
    public CsvHeader[] getHeaders() {
        return headers != null ? headers : new CsvHeader[0];
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
     * @param headers
     *            the headers to set
     *
     * @since 2021. 8. 13.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see #headers
     */
    @JsonSetter
    public void setHeaders(CsvHeader[] headers) {
        this.headers = headers;
    }

    /**
     * 데이터 개수에 맞는 헤더명을 설정한다.<br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 11.     박준홍         최초 작성
     * </pre>
     *
     * @param count
     *            한줄에 포함된 데이터 개수
     *
     * @since 2021. 8. 11.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public void setHeaders(int count) {
        if (count < 0) {
            throw ExceptionUtils.newException(InternalServerException.class, "올바르지 않은 헤더 사이즈입니다. 기대=0 이상, 입력=%,d", count);
        }
        this.headers = CommonsUtils.csvHeaderArray(count);
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 10.     박준홍         최초 작성
     * </pre>
     *
     * @param headers
     *            the headers to set
     *
     * @since 2021. 8. 10.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see #headers
     */
    public void setHeaders(List<String> headers) {
        if (headers == null) {
            throw ExceptionUtils.newException(InternalServerException.class, "올바르지 않은 헤더 정보입니다. 입력=null");
        }
        this.headers = CommonsUtils.csvHeaders(headers);
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 10.     박준홍         최초 작성
     * </pre>
     *
     * @param headers
     *            the headers to set
     *
     * @since 2021. 8. 10.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see #headers
     */
    public void setHeaders(String... headers) {
        if (headers == null) {
            throw ExceptionUtils.newException(InternalServerException.class, "올바르지 않은 헤더 정보입니다. 입력=null");
        }
        this.headers = CommonsUtils.csvHeaders(headers);
    }

    /**
     * @since 2021. 8. 13.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AbstractCsvFileLoad [filepath=");
        builder.append(filepath);
        builder.append(", headers=");
        builder.append(Arrays.toString(headers));
        builder.append("]");
        return builder.toString();
    }

}
