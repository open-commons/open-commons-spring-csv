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
 * Date  : 2021. 12. 8. 오후 6:12:26
 *
 * Author: Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 */

package open.commons.csv.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.constraints.NotNull;

import open.commons.csv.CsvHeader;

/**
 * 
 * @since 2021. 8. 11.
 * @version _._._
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
public class CommonsUtils {

    private CommonsUtils() {
    }

    /**
     * 데이터 개수를 헤더명으로 변환한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 12.     박준홍         최초 작성
     * </pre>
     *
     * @param count
     *            데이터 개수
     * @return
     *
     * @since 2021. 8. 12.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public static CsvHeader[] csvHeaderArray(int count) {
        return count < 1 //
                ? new CsvHeader[0] //
                : csvHeaders(intToStrList(count));
    }

    /**
     * 데이터 개수를 헤더명으로 변환한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 11.     박준홍         최초 작성
     * </pre>
     *
     * @param count
     *            데이터 개수
     * @return
     *
     * @since 2021. 8. 11.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public static List<CsvHeader> csvHeaderList(int count) {
        return Arrays.asList(csvHeaderArray(count));
    }

    /**
     * 기본 {@link CsvHeader}로 이루어진 배열을 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 13.     박준홍         최초 작성
     * </pre>
     *
     * @param headers
     * @return
     *
     * @since 2021. 8. 13.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public static CsvHeader[] csvHeaders(@NotNull List<String> headers) {
        return csvHeaders(headers.toArray(new String[0]));
    }

    /**
     * 기본 {@link CsvHeader}로 이루어진 배열을 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 13.     박준홍         최초 작성
     * </pre>
     *
     * @param headers
     * @return
     *
     * @since 2021. 8. 13.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public static CsvHeader[] csvHeaders(@NotNull String... headers) {
        return Arrays.stream(headers).map(hd -> new CsvHeader(hd)).toArray(CsvHeader[]::new);
    }

    /**
     * 데이터 개수를 헤더명으로 변환한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 11.     박준홍         최초 작성
     * </pre>
     *
     * @param count
     *            데이터 개수
     * @return
     *
     * @since 2021. 8. 11.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    private static List<String> intToStrList(int count) {
        return count < 1 //
                ? new ArrayList<>() //
                : IntStream.range(1, count + 1).mapToObj(Integer::toString).collect(Collectors.toList());
    }

    /**
     * 랜덤 문자열을 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 13.     박준홍         최초 작성
     * </pre>
     *
     * @return
     *
     * @since 2021. 8. 13.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * @see UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

}
