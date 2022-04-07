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
 * Date  : 2021. 12. 8. 오후 6:10:42
 *
 * Author: Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 */

package open.commons.spring.csv.service;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;

import open.commons.core.Result;
import open.commons.core.csv.CsvFileConfig;
import open.commons.spring.csv.ColumnCondition;
import open.commons.spring.csv.CsvFileOnMemory;
import open.commons.spring.csv.CsvFileSampling;
import open.commons.spring.csv.CsvHeader;
import open.commons.spring.csv.ManagedCsvFile;

/**
 * CSV 파일 관련 기능을 정의.
 * 
 * @since 2021. 12. 8.
 * @version 0.1.0
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
public interface ICsvService {

    /**
     * 줄 번호에 해당하는 데이터를 삭제한다. <br>
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 16.     박준홍         최초 작성
     * </pre>
     *
     * @param uuid
     *            CSV 파일 메모리 적재 데이터 식별정보
     * @param lineNumber
     *            줄 번호
     * @return
     *
     * @since 2021. 8. 16.
     * @author Park_Jun_Hong (parkjunhong77@gmail.com)
     */
    public Result<Boolean> delete(@NotEmpty String uuid, @Min(1) int lineNumber);

    /**
     * 메모리에 적재된 CSV 파일 데이터들의 식별정보 목록을 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     *
     * @return
     *
     * @since 2021. 8. 15.
     * @author Park_Jun_Hong (parkjunhong77@gmail.com)
     */
    public Result<Set<String>> getManagedFiles();

    /**
     * 
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 16.     박준홍         최초 작성
     * </pre>
     *
     * @param uuid
     *            CSV 파일 메모리 적재 데이터 식별정보
     * @param lineNumber
     *            줄 번호
     * @param position
     *            줄번호를 기준으로 삽입할 방향
     * @param data
     *            새로운 CSV 파일 데이터.
     * 
     * @return
     *
     * @since 2021. 8. 16.
     * @author Park_Jun_Hong (parkjunhong77@gmail.com)
     */
    public Result<Boolean> insert(@NotEmpty String uuid, @Min(1) int lineNumber, @NotNull PositionDir position, @NotEmpty Object[] data);

    /**
     * CSV 파일을 메모리에 로딩한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 12.     박준홍         최초 작성
     * </pre>
     * 
     * @param uuid
     *            CSV 파일 메모리 적재 데이터 식별정보
     * @param config
     *            CSV 파일 설정
     * @param headers
     *            CSV 파일 헤더 정보
     * @param hasHeader
     *            TODO
     * @param filepath
     *            CSV 파일 경로
     * @param reload
     *            재로딩 여부
     * @return
     *
     * @since 2021. 8. 12.
     * @author Park_Jun_Hong (parkjunhong77@gmail.com)
     */
    public Result<CsvFileOnMemory> load(@NotEmpty String uuid, @NotNull CsvFileConfig config, @NotEmpty CsvHeader[] headers, boolean hasHeader, @NotNull String filepath,
            boolean reload);

    /**
     * 주어진 개수만큼 데이터를 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     *
     * @param uuid
     *            CSV 파일 메모리 적재 데이터 식별정보
     * @param lineNumber
     *            읽기 시작할 줄 번호
     * @param count
     *            읽을 데이터 개수
     * @return
     *
     * @since 2021. 8. 15.
     * @author Park_Jun_Hong (parkjunhong77@gmail.com)
     */
    public Result<ManagedCsvFile> read(@NotEmpty String uuid, @Min(1) Integer lineNumber, @Min(1) Integer count);

    /**
     * 메모리 적재 중인 CSV 파일을 제거한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     *
     * @param uuid
     *            CSV 파일 메모리 적재 데이터 식별정보
     * @return
     *
     * @since 2021. 8. 15.
     * @author Park_Jun_Hong (parkjunhong77@gmail.com)
     */
    public Result<Boolean> release(@NotEmpty String uuid);

    /**
     * CSV 파일을 메모리로 재적재한다.<br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 17.     박준홍         최초 작성
     * </pre>
     *
     * @param uuid
     *            CSV 파일 메모리 적재 데이터 식별정보
     * @param filepath
     *            CSV 파일 경로
     * @return
     *
     * @since 2021. 8. 17.
     * @author Park_Jun_Hong (parkjunhong77@gmail.com)
     */
    public Result<CsvFileOnMemory> reload(@NotEmpty String uuid, @NotEmpty String filepath);

    /**
     * CSV 파일 데이터를 요청한만큼 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 13.     박준홍         최초 작성
     * </pre>
     *
     * @param config
     *            CSV 파일 설정
     * @param filepath
     *            CSV 파일 경로
     * @param count
     *            읽을 줄 개수
     * @return
     *
     * @since 2021. 8. 13.
     * @author Park_Jun_Hong (parkjunhong77@gmail.com)
     */
    public Result<CsvFileSampling> sample(@NotNull CsvFileConfig config, @NotNull String filepath, int count);

    /**
     * 
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 16.     박준홍         최초 작성
     * </pre>
     *
     * @param uuid
     *            CSV 파일 메모리 적재 데이터 식별정보
     * @param conditions
     *            검색 조건
     * @param pageable
     *            pagination 설정
     * @return
     *
     * @since 2021. 8. 16.
     * @author Park_Jun_Hong (parkjunhong77@gmail.com)
     */
    public Result<ManagedCsvFile> search(@NotEmpty String uuid, List<ColumnCondition> conditions, @NotNull Pageable pageable);

    /**
     * 주어진 줄 번호 데이터를 변경한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     *
     * @param uuid
     *            CSV 파일 메모리 적재 데이터 식별정보
     * @param lineNumber
     *            줄 번호
     * @param data
     *            새로운 CSV 파일 데이터.
     * @return
     *
     * @since 2021. 8. 15.
     * @author Park_Jun_Hong (parkjunhong77@gmail.com)
     */
    public Result<Boolean> update(@NotEmpty String uuid, @Min(1) int lineNumber, @NotEmpty Object[] data);

    /**
     * 파일로 저장한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 16.     박준홍         최초 작성
     * </pre>
     *
     * @param uuid
     *            CSV 파일 메모리 적재 데이터 식별정보
     * @param filepath
     *            저장할 파일 경로
     * @return
     *
     * @since 2021. 8. 16.
     * @author Park_Jun_Hong (parkjunhong77@gmail.com)
     */
    public Result<Boolean> write(@NotEmpty String uuid, String filepath);
}
