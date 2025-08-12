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
 * Date  : 2021. 12. 8. 오후 6:14:53
 *
 * Author: Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 */

package open.commons.spring.csv;

import java.util.Arrays;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * CSV 파일을 메모리에 로딩한 결과 클래스.
 * 
 * @since 2021. 12. 8.
 * @version 0.1.0
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
public class CsvFileOnMemory extends AbstractCsvFileLoad {

    /** 줄 수 */
    @Min(value = 0, message = "줄번호는 0 또는 양의 정수이어야 합니다.")
    private final int lineCount;

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
     * @param lineCount
     *            줄 수
     *
     * @since 2021. 8. 10.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CsvFileOnMemory(@NotNull String filepath, @Min(0) int lineCount) {
        super(filepath);
        this.lineCount = lineCount;
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
     * @return the lineCount
     *
     * @since 2021. 8. 10.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see #lineCount
     */
    public int getLineCount() {
        return lineCount;
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
        builder.append("CsvFileOnMemory [filepath=");
        builder.append(filepath);
        builder.append(", headers=");
        builder.append(Arrays.toString(headers));
        builder.append(", lineCount=");
        builder.append(lineCount);
        builder.append("]");
        return builder.toString();
    }

}
