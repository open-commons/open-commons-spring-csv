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
 * Date  : 2021. 8. 15. 오후 5:55:51
 *
 * Author: Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 */

package open.commons.spring.csv;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.domain.Pageable;

/**
 * CSV 파일에서 읽은 데이터 정보.
 * 
 * @since 2021. 8. 15.
 * @version 0.1.0
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
public class ManagedCsvFile {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    /** CSV 파일 경로 */
    private final String filepath;
    /** CSV 파일 데이터 */
    private final CsvLines lines;
    /** 메모리에서 자동 해제될 시간. 단위: ms (millisecond) */
    private long releaseTime;
    /** 페이지 정보 */
    private Pageable pageable;
    /** 원본 데이터 전체 크기 */
    private int totalSize;

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 15.		박준홍			최초 작성
     * </pre>
     *
     * @param filepath
     *            CSV 파일 경로
     * @param lines
     *            CSV 파일 데이터
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public ManagedCsvFile(String filepath, CsvLines lines) {
        super();
        this.filepath = filepath;
        this.lines = lines;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 15.		박준홍			최초 작성
     * </pre>
     * 
     * @return the filepath
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #filepath
     */

    public String getFilepath() {
        return filepath;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 15.		박준홍			최초 작성
     * </pre>
     * 
     * @return the lines
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #lines
     */

    public CsvLines getLines() {
        return lines;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 15.		박준홍			최초 작성
     * </pre>
     * 
     * @return the pageable
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #pageable
     */

    public Pageable getPageable() {
        return pageable;
    }

    /**
     * 자동해제될 시간을 제공한다.<br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 15.		박준홍			최초 작성
     * </pre>
     * 
     * @return 메모리에서 자동 해제될 시간. 단위: ms (millisecond)
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #releaseTime
     */

    public long getReleaseTime() {
        return releaseTime;
    }

    /**
     * 전체 데이터 개수를 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 15.		박준홍			최초 작성
     * </pre>
     * 
     * @return the totalSize
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #totalSize
     */

    public int getTotalSize() {
        return totalSize;
    }

    /**
     * 페이지 정보를 설정한다.<br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 15.		박준홍			최초 작성
     * </pre>
     *
     * @param pageable
     *            the pageable to set
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #pageable
     */
    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    /**
     * 자동해제될 시간을 설정한다.<br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 15.		박준홍			최초 작성
     * </pre>
     *
     * @param releaseTime
     *            메모리에서 자동 해제될 시간. 단위: ms (millisecond)
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #releaseTime
     */
    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 15.		박준홍			최초 작성
     * </pre>
     *
     * @param totalSize
     *            the totalSize to set
     *
     * @since 2021. 8. 15.
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
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ManagedCsvFile [filepath=");
        builder.append(filepath);
        builder.append(", lines=");
        builder.append(lines);
        builder.append(", releaseTime=");
        builder.append(DATE_FORMAT.format(new Date(releaseTime)));
        builder.append(", pageable=");
        builder.append(pageable);
        builder.append(", totalSize=");
        builder.append(totalSize);
        builder.append("]");
        return builder.toString();
    }
}
