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
 * Date  : 2021. 8. 12. 오후 6:01:31
 *
 * Author: Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 */

package open.commons.csv;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import open.commons.Result;
import open.commons.concurrent.Mutex;
import open.commons.csv.service.PositionDir;
import open.commons.spring.web.servlet.BadRequestException;
import open.commons.spring.web.servlet.InternalServerException;
import open.commons.utils.ArrayUtils;
import open.commons.utils.ComparableUtils;
import open.commons.utils.ExceptionUtils;

/**
 * 
 * @since 2021. 8. 12.
 * @version 0.1.0
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
public class MemorizedCsvFile {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    private Logger logger = LoggerFactory.getLogger(getClass());

    /** 데이터 식별정보 */
    private final String uuid;

    /** CSV 파일 설정 */
    private final CsvFileConfig csvFileConfig;

    /** CSV 데이터 헤더 정보 */
    private final CsvHeader[] headers;
    /** 헤더 여부 */
    private final Boolean hasHeader;

    /**
     * 파일 경로<br>
     * 저장하는 경우, 기본 값으로 사용된다. (덮어쓰기)
     */
    private final String filepath;

    /** CSV 파일 데이터 */
    private final Vector<Object[]> lines = new Vector<>();
    /** CSV 데이터에 대한 Mutex 객체 */
    private final Mutex mutexLines = new Mutex("Mutex for 'lines'");

    /** 생성 시간. (millisecond) */
    private final long created;
    /** 최종 데이터 접근 시간 (millisecond) */
    private long accessed;
    /** 최종 데이터 수정 시간 (millisecond) */
    private long modified;
    private Mutex mutexTimestamp = new Mutex("Mutex for 'Timestamp'");

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 12.     박준홍         최초 작성
     * </pre>
     * 
     * @param uuid
     *            CSV 파일 데이터 식별정보
     * @param csvFileConfig
     *            CSV 파일 설정
     * @param headers
     *            CSV 파일 헤더 정보
     * @param hasHeader
     *            헤더 여부
     * @param filepath
     *            CSV 파일 경로
     * @since 2021. 8. 12.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public MemorizedCsvFile(String uuid, CsvFileConfig csvFileConfig, CsvHeader[] headers, boolean hasHeader, String filepath) {

        if (!Files.isReadable(Paths.get(filepath))) {
            throw ExceptionUtils.newException(BadRequestException.class, new FileNotFoundException(filepath), "존재하지 않거나 접근권한이 없는 파일입니다. 파일=%s", filepath);
        }

        this.created = System.currentTimeMillis();
        this.uuid = uuid;
        this.csvFileConfig = csvFileConfig;
        this.headers = headers;
        this.hasHeader = hasHeader;
        this.filepath = filepath;
    }

    /**
     * 데이터 접근이 발생한 후 경과된 시간을 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 14.     박준홍         최초 작성
     * </pre>
     *
     * @return millisecond.
     *
     * @since 2021. 8. 14.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public long afterAccessed() {
        synchronized (mutexTimestamp) {
            return System.currentTimeMillis() - this.accessed;
        }
    }

    /**
     * 생성후 경과된 시간을 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 14.     박준홍         최초 작성
     * </pre>
     *
     * @return millisecond.
     *
     * @since 2021. 8. 14.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public long afterCreated() {
        return System.currentTimeMillis() - this.created;
    }

    /**
     * 데이터가 수정된 후 경과된 시간을 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 14.     박준홍         최초 작성
     * </pre>
     *
     * @return millisecond.
     *
     * @since 2021. 8. 14.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public long afterModified() {
        synchronized (mutexTimestamp) {
            return System.currentTimeMillis() - this.modified;
        }
    }

    /**
     * 헤더와 데이터 길이의 일치 여부를 검증한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 13.     박준홍         최초 작성
     * </pre>
     *
     * @param <X>
     * @param line
     *            줄 데이터
     * @param exType
     *            예외상황 클래스.
     *
     * @since 2021. 8. 13.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    private <X extends RuntimeException> void assertDataLength(Object[] line, Class<X> exType) {
        // 헤더와 데이터 길이 검증
        if (this.headers.length != line.length) {
            String errMsg = String.format("헤더 길이와 데이터 길이가 일치하지 않습니다. 헤더=%,d, 데이터=%,d", this.headers.length, line.length);
            logger.warn(errMsg);
            throw ExceptionUtils.newException(exType, errMsg);
        }
    }

    /**
     * 중복 라인의 존재하는 것에 대해 예외판단을 한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 13.     박준홍         최초 작성
     * </pre>
     *
     * @param <X>
     * @param index
     *            줄 번호
     * @param exType
     *            예외상황 클래스
     * @param exMessage
     *            예외상황 메시지
     * @throws BadRequestException
     *             줄 번호가 올바르지 않은 경우
     * @since 2021. 8. 13.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    private <X extends RuntimeException> void assertLineNumber(int index, Class<X> exType, String exMessage) throws BadRequestException {
        if (index < 0 || index >= this.lines.size()) {
            logger.warn(exMessage);
            throw ExceptionUtils.newException(exType, exMessage);
        }
    }

    /**
     * 줄 데이터를 삭제한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 16.     박준홍         최초 작성
     * </pre>
     *
     * @param lineNumber
     *            지우려는 줄 번호
     * @return
     *
     * @since 2021. 8. 16.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public Result<Boolean> delete(@Min(1) Integer lineNumber) {
        synchronized (mutexLines) {
            assertLineNumber(lineNumber - 1, BadRequestException.class, String.format("범위를 벗어난 줄번호 입니다. 범위: 1 ~ %,d, 입력=%,d", this.lines.size(), lineNumber));

            this.lines.remove(lineNumber - 1);
            updateTimestamp(true);

            return Result.success(true);
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
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     * 
     * @param lineNumber
     *            줄 번호
     * @param readline
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    private Object[] deserialize(int lineNumber, String[] readline) {
        final Object[] data = new Object[readline.length];

        ArrayUtils.entrySet(readline).stream() //
                .forEach(entry -> {
                    Integer index = entry.getKey();
                    String value = entry.getValue();
                    // 값이 있어야 하고, 빈 문자열이 아닌 경우.
                    if (value != null) {
                        CsvHeader header = headers[index];
                        try {
                            switch (header.getDataType()) {
                                case INT:
                                    data[index] = value.isEmpty() ? null : Long.parseLong(value);
                                    break;
                                case STR:
                                    data[index] = value;
                                    break;
                                case NUM:
                                    data[index] = value.isEmpty() ? null : Double.parseDouble(value);
                                    break;
                                case GENERAL:
                                    data[index] = value;
                                    break;
                                default:
                                    break;
                            }
                        } catch (NumberFormatException e) {
                            String errMsg = String.format(
                                    "[적재 실패] '%s' 파일 로드 중 에러가 발생하였습니다. 헤더 설정을 확인하기 바랍니다. line.number=%,d, line.value=%s, file.header=%s, column.index=%,d, column.value=%s",
                                    this.filepath, lineNumber, Arrays.toString(readline), header, index, value);
                            logger.error(errMsg, e);
                            throw ExceptionUtils.newException(BadRequestException.class, e, errMsg);
                        } catch (Exception e) {
                            String errMsg = String.format(
                                    "[적재 실패] '%s' 파일 로드 중 에러가 발생하였습니다. 헤더 설정을 확인하기 바랍니다. line.number=%,d, line.value=%s, file.header=%s, column.index=%,d, column.value=%s",
                                    this.filepath, lineNumber, Arrays.toString(readline), header, index, value);
                            logger.error(errMsg, e);
                            throw ExceptionUtils.newException(InternalServerException.class, e, errMsg);
                        }
                    } else {
                        data[index] = null;
                    }
                });

        return data;
    }

    private List<IndexedObject> filter(@NotNull final Collection<ColumnCondition> conditions) {
        List<IndexedObject> result = new ArrayList<>();

        int idx = 0;
        int clmnIndex = -1;
        for (Object[] line : this.lines) {
            CsvHeader header = null;
            Object colValue = null;
            boolean matched = true;
            // #0. 줄 데이터를 검색조건과 비교
            for (ColumnCondition c : conditions) {
                clmnIndex = c.getIndex();
                header = headers[clmnIndex];
                colValue = line[clmnIndex];
                switch (header.getDataType()) {
                    case INT:
                        matched = filter((Long) colValue, c.getOp(), c.getData());
                        break;
                    case NUM:
                        matched = filter((Double) colValue, c.getOp(), c.getData());
                        break;
                    case STR:
                        matched = filter((String) colValue, c.getOp(), c.getData());
                        break;
                    case GENERAL:
                        matched = true;
                        break;
                    default:
                        break;
                }

                if (!matched) {
                    break;
                }
            }

            if (matched) {
                result.add(new IndexedObject(idx, line));
            }

            idx++;
        }

        return result;
    }

    /**
     * {@link ColumnDataType#INT} 타입의 데이터를 비교한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     * 
     * @param colData
     *            컬럼 데이터
     * @param op
     *            검색 조건 연산자
     * @param condValue
     *            검색 조건 데이터
     * 
     * @return
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    private boolean filter(Double colData, ColumnOp op, Object condValue) {
        if (colData == null) {
            return false;
        }

        switch (op) {
            case EQ:
                return colData == Double.parseDouble(condValue.toString());
            case NE:
                return colData != Double.parseDouble(condValue.toString());
            case GE:
                return colData >= Double.parseDouble(condValue.toString());
            case GT:
                return colData > Double.parseDouble(condValue.toString());
            case LE:
                return colData <= Double.parseDouble(condValue.toString());
            case LT:
                return colData < Double.parseDouble(condValue.toString());
            case CO:
            case ST:
            case ED:
            case RX:
                return false;
            default:
                // unreachable
                return false;
        }
    }

    /**
     * {@link ColumnDataType#INT} 타입의 데이터를 비교한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     * 
     * @param colData
     *            컬럼 데이터
     * @param op
     *            검색 조건 연산자
     * @param condValue
     *            검색 조건 데이터
     * 
     * @return
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    private boolean filter(Long colData, ColumnOp op, Object condValue) {
        if (colData == null) {
            return false;
        }

        switch (op) {
            case EQ:
                return colData == Long.parseLong(condValue.toString());
            case NE:
                return colData != Long.parseLong(condValue.toString());
            case GE:
                return colData >= Long.parseLong(condValue.toString());
            case GT:
                return colData > Long.parseLong(condValue.toString());
            case LE:
                return colData <= Long.parseLong(condValue.toString());
            case LT:
                return colData < Long.parseLong(condValue.toString());
            case CO:
            case ST:
            case ED:
            case RX:
                return false;
            default:
                // unreachable
                return false;
        }
    }

    /**
     * {@link ColumnDataType#INT} 타입의 데이터를 비교한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     * 
     * @param colData
     *            컬럼 데이터
     * @param op
     *            검색 조건 연산자
     * @param condValue
     *            검색 조건 데이터
     * 
     * @return
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    private boolean filter(String colData, ColumnOp op, Object condValue) {
        if (colData == null) {
            return false;
        }

        switch (op) {
            case EQ:
                return colData.equals(condValue.toString());
            case NE:
                return !colData.equals(condValue.toString());
            case GE:
                return colData.compareTo(condValue.toString()) >= 0;
            case GT:
                return colData.compareTo(condValue.toString()) > 0;
            case LE:
                return colData.compareTo(condValue.toString()) <= 0;
            case LT:
                return colData.compareTo(condValue.toString()) < 0;
            case CO:
                return colData.contains(condValue.toString());
            case ST:
                return colData.startsWith(condValue.toString());
            case ED:
                return colData.endsWith(condValue.toString());
            case RX:
                return colData.matches(condValue.toString());
        }

        return true;
    }

    /**
     * 
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     *
     * @param sort
     *            데이터 정렬 설정
     * @param conditions
     *            데이터 선택 조건
     * @return
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    private List<IndexedObject> get0(ColumnSort sort, @NotNull Collection<ColumnCondition> conditions) {
        sort(sort);
        if (conditions == null) {
            conditions = new ArrayList<>();
        }
        return filter(conditions);
    }

    /**
     *
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     * 
     * @return the accessed
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see #accessed
     */
    public long getAccessed() {
        return accessed;
    }

    /**
     *
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     * 
     * @return the created
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see #created
     */
    public long getCreated() {
        return created;
    }

    /**
     *
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 17.     박준홍         최초 작성
     * </pre>
     * 
     * @return the csvFileConfig
     *
     * @since 2021. 8. 17.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see #csvFileConfig
     */
    public CsvFileConfig getCsvFileConfig() {
        return csvFileConfig;
    }

    /**
     *
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
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
     *
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 17.     박준홍         최초 작성
     * </pre>
     * 
     * @return the hasHeader
     *
     * @since 2021. 8. 17.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see #hasHeader
     */
    public Boolean getHasHeader() {
        return hasHeader;
    }

    /**
     *
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 17.     박준홍         최초 작성
     * </pre>
     * 
     * @return the headers
     *
     * @since 2021. 8. 17.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see #headers
     */
    public CsvHeader[] getHeaders() {
        return headers;
    }

    /**
     *
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     * 
     * @return the modified
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see #modified
     */
    public long getModified() {
        return modified;
    }

    /**
     * 데이터 개수를 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 16.     박준홍         최초 작성
     * </pre>
     *
     * @return
     *
     * @since 2021. 8. 16.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public int getSize() {
        synchronized (mutexLines) {
            return this.lines.size();
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
     * 2021. 8. 17.     박준홍         최초 작성
     * </pre>
     * 
     * @return the uuid
     *
     * @since 2021. 8. 17.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see #uuid
     * 
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 신규 데이터를 추가한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 16.     박준홍         최초 작성
     * </pre>
     *
     * @param lineNumber
     *            줄 번호
     * @param position
     *            줄번호를 기준으로 삽입할 방향
     * @param data
     *            새로운 CSV 파일 데이터.
     * @return
     * @throws BadRequestException
     *             줄 번호가 올바르지 않은 경우.
     *
     * @since 2021. 8. 16.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public Result<Boolean> insertData(@NotNull @Min(1) Integer lineNumber, @NotNull PositionDir position, @NotEmpty Object[] data) throws BadRequestException {
        // 줄 번호 검증
        assertLineNumber(lineNumber - 1, BadRequestException.class, String.format("범위를 벗어난 줄번호 입니다. 범위: 1 ~ %,d, 입력=%,d", this.lines.size(), lineNumber));
        // 신규 데이터 검증
        validateData(data);

        // 신규 데이터 추가
        switch (position) {
            case BACK:
            case BOTTOM:
                if (lineNumber == this.lines.size()) {
                    this.lines.add(data);
                } else {
                    this.lines.add(lineNumber, data);
                }
                updateTimestamp(true);
                return Result.success(true);
            case FRONT:
            case TOP:
                this.lines.add(lineNumber - 1, data);
                updateTimestamp(true);
                return Result.success(true);
            default:
                // unreachable
                return Result.error("지원하지 않는 방향이니다. 기대=%s, 입력=%s", PositionDir.values(), position);
        }
    }

    /**
     * CSV 줄 데이터를 추가한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 13.     박준홍         최초 작성
     * </pre>
     *
     * @param lineNumber
     *            줄 번호
     * @param readline
     *            줄 데이터
     * @throws BadRequestException
     *             헤더 타입과 실제 데이터가 일치하지 않는 경우.
     *
     * @since 2021. 8. 13.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public void loadLineData(@Min(1) Integer lineNumber, @NotEmpty String[] readline) throws BadRequestException {
        // 헤더와 데이터 길이 검증
        assertDataLength(readline, InternalServerException.class);

        Object[] data = deserialize(lineNumber, readline);
        this.lines.add(data);

        updateTimestamp(true);
    }

    /**
     * 요청한 개수만큼 줄 번호 데이터를 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     *
     * @param lineNumber
     *            읽기 시작할 준 번호. (1부터 시작)
     * @param count
     *            읽을 데이터 개수.
     * @return
     * @throws BadRequestException
     *             줄 번호가 올바르지 않은 경우
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public ManagedCsvFile read(@Min(1) Integer lineNumber, @Min(1) Integer count) throws BadRequestException {
        synchronized (mutexLines) {
            List<IndexedObject> filtered = get0(null, null);

            int begin = lineNumber - 1;
            final int totalSize = filtered.size();
            if (begin > totalSize) {
                throw ExceptionUtils.newException(BadRequestException.class, "읽기 시작하려는 줄 번호가 전체 데이터보다 큽니다. 전체 데이터=%,d, 읽기 시작하려는 줄번호=%,d", totalSize, lineNumber);
            }

            final int end = Integer.min(begin + count, totalSize);

            try {
                CsvLines lines = select(filtered, begin, end);
                lines.setTotalSize(totalSize);

                ManagedCsvFile managed = new ManagedCsvFile(this.filepath, lines);
                managed.setTotalSize(this.lines.size());

                return managed;
            } catch (BadRequestException e) {
                throw e;
            } catch (Exception e) {
                String errMsg = String.format("데이터 읽는 도중 에러가 발생하였습니다. lineNumber=%,d, count=%,d, 원인=%s", lineNumber, count, e.getMessage());
                logger.error(errMsg, e);
                throw ExceptionUtils.newException(InternalServerException.class, e, errMsg);
            }
        }
    }

    /**
     * 검색 조건에 맞는 데이터를 찾아 설정된 페이지 정보에 맞는 데이터를 제공한다.<br>
     * 검색 조건 결과에 따라 페이지 정보와 맞지 않는 경우 페이지 정보를 변경하여 결과를 제공한다.
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 16.     박준홍         최초 작성
     * </pre>
     *
     * @param sort
     *            정렬 조건
     * @param conditions
     *            검색 조건
     * @param pageable
     *            TODO
     * @return
     * @throws BadRequestException
     *             줄 번호가 올바르지 않은 경우
     *
     * @since 2021. 8. 16.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public ManagedCsvFile search(ColumnSort sort, @NotNull final Collection<ColumnCondition> conditions, Pageable pageable) throws BadRequestException {

        synchronized (mutexLines) {
            List<IndexedObject> filtered = get0(sort, conditions);
            CsvLines lines = null;

            if (filtered.size() > 0) {
                int pageNumber = pageable.getPageNumber();
                int pageSize = pageable.getPageSize();
                // 순서(1,2,3,...)
                int lineNumber = (pageNumber - 1) * pageSize + 1;
                // Index (0,1,2,...)
                int beginIndex = (lineNumber - 1);
                // 개수 (1,2,3,...)
                final int totalSize = filtered.size();
                // 페이지가 전체 개수를 초과한 경우 페이지 정보 재설정
                if (beginIndex >= totalSize) {
                    // #0. 페이지 값 재조정 (페이지 단위로 계산)
                    int mod = totalSize / pageSize;
                    // #1. 시작위치가 전체 개수와 동일한 경우, 1페이지 뒤로 이동.
                    if ((beginIndex = mod * pageSize) == totalSize) {
                        pageNumber = mod;
                        beginIndex = (pageNumber - 1) * pageSize;
                    } else {
                        pageNumber = mod + 1;
                    }
                    pageable = PageRequest.of(pageNumber, pageSize, pageable.getSort());
                }

                final int endIndex = Integer.min(beginIndex + pageSize, totalSize);

                try {
                    lines = select(filtered, beginIndex, endIndex);
                    lines.setTotalSize(totalSize);

                } catch (BadRequestException e) {
                    throw e;
                } catch (Exception e) {
                    String errMsg = String.format("데이터 읽는 도중 에러가 발생하였습니다. lineNumber=%,d, size=%,d, 원인=%s", lineNumber, pageSize, e.getMessage());
                    logger.error(errMsg, e);
                    throw ExceptionUtils.newException(InternalServerException.class, e, errMsg);
                }
            } else {
                lines = new CsvLines(this.headers);
                lines.setTotalSize(0);
            }

            ManagedCsvFile managed = new ManagedCsvFile(this.filepath, lines);
            managed.setTotalSize(this.lines.size());
            managed.setPageable(pageable);

            return managed;
        }
    }

    /**
     * 주어진 범위에 맞는 데이터를 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     * 
     * @param data
     *            TODO
     * @param begin
     *            시작 index (inclusive)
     * @param end
     *            마지막 index (exclusive)
     *
     * @return
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see 이 함수를 호출하는 곳에서 반드시 {@link #mutexLines}에 대해 synchroized 설정을 해야 한다.
     */
    private CsvLines select(List<IndexedObject> data, final @Min(1) Integer begin, final @Min(1) Integer end) {

        if (begin < 0 || end < 1) {
            String errMsg = String.format("데이터 검색 위치가 범위를 벗어났습니다. begin=%,d, end=%,d, MAX=%,d", begin, end, Integer.MAX_VALUE);
            logger.error(errMsg);
            throw ExceptionUtils.newException(BadRequestException.class, errMsg);
        }

        CsvLines result = new CsvLines(this.headers);

        int pos = begin;
        CsvLine line = null;
        Object[] copied = null;

        try {
            IndexedObject idxObj = null;
            while (pos < end) {
                idxObj = data.get(pos);
                line = new CsvLine(idxObj.getIndex() + 1);
                // 데이터 복사
                copied = new Object[this.headers.length];
                System.arraycopy(idxObj.getValue(), 0, copied, 0, copied.length);
                line.setData(copied);
                // 반환목록에 추가
                result.addData(line);

                pos++;
            }
            return result;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            String errMsg = String.format("데이터 읽는 도중 에러가 발생하였습니다. lineNumber=%,d, count=%,d, postion=%,d, 원인=%s", begin + 1, end - begin, pos, e.getMessage());
            logger.error(errMsg, e);
            throw ExceptionUtils.newException(BadRequestException.class, e, errMsg);
        } finally {
            updateTimestamp(false);
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
     * 2021. 8. 16.     박준홍         최초 작성
     * </pre>
     *
     * @param lineNumber
     * @param data
     * @return
     *
     * @since 2021. 8. 16.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    private String[] serialize(int lineNumber, Object[] data) {
        final String[] lines = new String[data.length];

        ArrayUtils.entrySet(data).stream() //
                .forEach(entry -> {
                    Integer index = entry.getKey();
                    Object value = entry.getValue();
                    if (value != null) {
                        CsvHeader header = headers[index];
                        try {
                            switch (header.getDataType()) {
                                case INT:
                                case STR:
                                case NUM:
                                case GENERAL:
                                    lines[index] = value.toString();
                                    break;
                                default:
                                    // unreachable
                                    break;
                            }
                        } catch (NumberFormatException e) {
                            String errMsg = String.format(
                                    "[저장 실패] '%s' 파일 저장 중 에러가 발생하였습니다. 헤더 설정을 확인하기 바랍니다. line.number=%,d, line.value=%s, file.header=%s, column.index=%,d, column.value=%s",
                                    this.filepath, lineNumber, Arrays.toString(data), header, index, value);
                            logger.error(errMsg, e);
                            throw ExceptionUtils.newException(BadRequestException.class, e, errMsg);
                        } catch (Exception e) {
                            String errMsg = String.format(
                                    "[저장 실패] '%s' 파일 저장 중 에러가 발생하였습니다. 헤더 설정을 확인하기 바랍니다. line.number=%,d, line.value=%s, file.header=%s, column.index=%,d, column.value=%s",
                                    this.filepath, lineNumber, Arrays.toString(data), header, index, value);
                            logger.error(errMsg, e);
                            throw ExceptionUtils.newException(InternalServerException.class, e, errMsg);
                        }
                    } else {
                        lines[index] = null;
                    }
                });

        return lines;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     *
     * @param accessed
     *            the accessed to set
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see #accessed
     */
    public void setAccessed(long accessed) {
        this.accessed = accessed;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     *
     * @param modified
     *            the modified to set
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see #modified
     */
    public void setModified(long modified) {
        this.modified = modified;
    }

    /**
     * 
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     *
     * @param sorter
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    private void sort(ColumnSort sort) {
        if (sort != null) {
            Sorter sorter = new Sorter(this.headers[sort.getIndex()], sort);
            Collections.sort(this.lines, sorter);

            updateTimestamp(true);
        }
    }

    /**
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MemorizedCsvFile [uuid=");
        builder.append(uuid);
        builder.append(", csvFileConfig=");
        builder.append(csvFileConfig);
        builder.append(", headers=");
        builder.append(Arrays.toString(headers));
        builder.append(", filepath=");
        builder.append(filepath);
        builder.append(", lines=");
        builder.append(lines.size());
        builder.append(", created=");
        builder.append(DATE_FORMAT.format(new Date(this.created)));
        builder.append(", accessed=");
        builder.append(DATE_FORMAT.format(new Date(this.accessed)));
        builder.append(", modified=");
        builder.append(DATE_FORMAT.format(new Date(this.modified)));
        builder.append("]");
        return builder.toString();
    }

    /**
     * 줄 데이터를 변경한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 13.     박준홍         최초 작성
     * </pre>
     *
     * @param lineNumber
     *            줄 번호
     * @param line
     *            변경된 줄 데이터
     * @throws BadRequestException
     *             줄 번호가 올바르지 않은 경우
     *
     * @since 2021. 8. 13.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public Result<Boolean> updateLine(@NotNull Integer lineNumber, Object[] line) throws BadRequestException {
        synchronized (mutexLines) {
            // 중복 라인 검증
            assertLineNumber(lineNumber - 1, BadRequestException.class, String.format("범위를 벗어난 줄번호 입니다. 범위: 1 ~ %,d, 입력=%,d", this.lines.size(), lineNumber));
            // 헤더와 데이터 길이 검증
            assertDataLength(line, BadRequestException.class);
            // 헤더 정의에 맞는지 데이터 검증.
            validateData(line);

            this.lines.set(lineNumber - 1, line);

            updateTimestamp(true);

            return Result.success(true);
        }
    }

    /**
     * 데이터 관련 시간 정보를 갱신합니다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     *
     * @param isModified
     *            데이터 수정 여부.
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    private void updateTimestamp(boolean isModified) {
        final long currentTimestamp = System.currentTimeMillis();
        synchronized (mutexTimestamp) {
            this.accessed = currentTimestamp;
            if (isModified) {
                this.modified = currentTimestamp;
            }
        }
    }

    private void validateData(Object[] line) {

        CsvHeader header = null;
        ColumnDataType type = null;
        int colIndex = -1;
        Object value = null;
        for (Entry<Integer, Object> entry : ArrayUtils.entrySet(line)) {
            value = entry.getValue();
            if (value == null) {
                continue;
            }

            colIndex = entry.getKey();

            header = this.headers[colIndex];
            type = header.getDataType();

            try {
                switch (type) {
                    case INT:
                        if (value.toString().isEmpty()) {
                            line[colIndex] = null;
                        } else {
                            line[colIndex] = Long.parseLong(value.toString());
                        }
                        break;
                    case NUM:
                        if (value.toString().isEmpty()) {
                            line[colIndex] = null;
                        } else {
                            line[colIndex] = Double.parseDouble(value.toString());
                        }
                        break;
                    case STR:
                        if (String.class != value.getClass()) {
                            throw ExceptionUtils.newException(BadRequestException.class, "헤더의 컬럼 데이터 타입과 일치하지 않는 데이터 입니다. header.column-data-type=%s, 입력값: %s", type,
                                    value.getClass());
                        }
                        break;
                    case GENERAL:
                        line[colIndex] = value.toString();
                        break;
                    default:
                        // unreachable
                        break;
                }
            } catch (NumberFormatException e) {
                throw ExceptionUtils.newException(BadRequestException.class, "헤더의 컬럼 데이터 타입과 일치하지 않는 데이터 입니다. header.column-data-type=%s, 입력값: %s", type, value.getClass());
            }
        }
    }

    /**
     * 주어진 경로에 파일을 저장한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 16.     박준홍         최초 작성
     * </pre>
     *
     * @param filepath
     *            저장할 파일 경로.
     * @return
     *
     * @since 2021. 8. 16.
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public Result<Boolean> write(@NotEmpty String filepath) {
        synchronized (mutexLines) {
            try (CSVWriter writer = hasHeader //
                    ? new CSVWriter(new OutputStreamWriter(new FileOutputStream(filepath, false), this.csvFileConfig.getCharsetName()) //
                            , this.csvFileConfig.getSeparator(), this.csvFileConfig.getQuotechar(), this.csvFileConfig.getEscape(), this.headers) //
                    : new CSVWriter(new OutputStreamWriter(new FileOutputStream(filepath, false), this.csvFileConfig.getCharsetName()) //
                            , this.csvFileConfig.getSeparator(), this.csvFileConfig.getQuotechar(), this.csvFileConfig.getEscape())) {
                String[] line = null;
                if (hasHeader) {
                    writer.writeHeader();
                }

                int index = 0;
                for (Object[] data : this.lines) {
                    index++;
                    line = serialize(index, data);
                    writer.writeNext(line);
                }

                return Result.success(true);
            } catch (IOException e) {
                String errMsg = String.format("[파일 저장 실패] 파일 저장시 에러가 발생하였습니다. 원인=%s", e.getMessage());
                logger.error(errMsg, e);
                return Result.error(errMsg);
            } catch (Exception e) {
                String errMsg = String.format("[파일 저장 실패] 파일 저장시 에러가 발생하였습니다. 원인=%s", e.getMessage());
                logger.error(errMsg, e);
                throw new InternalServerException(errMsg, e);
            }
        }
    }

    class IndexedObject {
        private final int index;
        private final Object[] value;

        /**
         * <br>
         * 
         * <pre>
         * [개정이력]
         *      날짜      | 작성자   |   내용
         * ------------------------------------------
         * 2021. 8. 16.     박준홍         최초 작성
         * </pre>
         *
         * @param index
         *            전체 데이터에서의 Index
         * @param value
         *            데이터
         * @since 2021. 8. 16.
         */
        public IndexedObject(int index, Object[] value) {
            this.index = index;
            this.value = value;
        }

        /**
         * 전체 데이터에서의 Index 를 제공한다. <br>
         * 
         * <pre>
         * [개정이력]
         *      날짜      | 작성자   |   내용
         * ------------------------------------------
         * 2021. 8. 16.     박준홍         최초 작성
         * </pre>
         * 
         * @return 전체 데이터에서의 Index
         *
         * @since 2021. 8. 16.
         * 
         * @see #index
         */
        public int getIndex() {
            return index;
        }

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
         * @return the value
         *
         * @since 2021. 8. 16.
         * 
         * @see #value
         */
        public Object[] getValue() {
            return value;
        }

        /**
         * @since 2021. 8. 16.
         * @author Park Jun-Hong (parkjunhong77@gmail.com)
         *
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("IndexedObject [index=");
            builder.append(index);
            builder.append(", value=");
            builder.append(Arrays.toString(value));
            builder.append("]");
            return builder.toString();
        }

    }

    class Sorter implements Comparator<Object[]> {

        private final CsvHeader header;
        private final ColumnSort sort;
        private final Integer index;
        private final ColumnDirection orderBy;

        /**
         * <br>
         * 
         * <pre>
         * [개정이력]
         *      날짜      | 작성자   |   내용
         * ------------------------------------------
         * 2021. 8. 15.     박준홍         최초 작성
         * </pre>
         *
         * @param header
         * @param sort
         * @since 2021. 8. 15.
         * @version 0.1.0
         * @author Park Jun-Hong (parkjunhong77@gmail.com)
         */
        public Sorter(CsvHeader header, ColumnSort sort) {
            this.header = header;
            this.sort = sort;
            this.index = sort.getIndex();
            this.orderBy = sort.getDirection();
        }

        /**
         * @since 2021. 8. 15.
         * @version 0.1.0
         * @author Park Jun-Hong (parkjunhong77@gmail.com)
         *
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(Object[] o1, Object[] o2) {
            int c = 0;
            switch (header.getDataType()) {
                case INT:
                    Long i1 = (Long) o1[this.index];
                    Long i2 = (Long) o2[this.index];
                    c = ComparableUtils.compare(i1, i2);
                    break;
                case NUM:
                    Double n1 = (Double) o1[this.index];
                    Double n2 = (Double) o2[this.index];
                    c = ComparableUtils.compare(n1, n2);
                    break;
                case STR:
                    String s1 = (String) o1[this.index];
                    String s2 = (String) o2[this.index];
                    c = ComparableUtils.compare(s1, s2);
                    break;
                case GENERAL:
                    c = -1;
                    break;
                default:
                    throw ExceptionUtils.newException(IllegalArgumentException.class, "데이터 정렬 도중 에러가 발생하였습니다. header=%s, sort=%s", header, sort);
            }

            if (this.orderBy.equals(ColumnDirection.ASC)) {
                return c;
            } else {
                return -c;
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
         * 2021. 8. 15.     박준홍         최초 작성
         * </pre>
         * 
         * @return the header
         *
         * @since 2021. 8. 15.
         * @version 0.1.0
         * @author Park Jun-Hong (parkjunhong77@gmail.com)
         * 
         * @see #header
         */
        public CsvHeader getHeader() {
            return header;
        }

        /**
         *
         * <br>
         * 
         * <pre>
         * [개정이력]
         *      날짜      | 작성자   |   내용
         * ------------------------------------------
         * 2021. 8. 15.     박준홍         최초 작성
         * </pre>
         * 
         * @return the index
         *
         * @since 2021. 8. 15.
         * @version 0.1.0
         * @author Park Jun-Hong (parkjunhong77@gmail.com)
         * 
         * @see #index
         */
        public Integer getIndex() {
            return index;
        }

        /**
         *
         * <br>
         * 
         * <pre>
         * [개정이력]
         *      날짜      | 작성자   |   내용
         * ------------------------------------------
         * 2021. 8. 15.     박준홍         최초 작성
         * </pre>
         * 
         * @return the orderBy
         *
         * @since 2021. 8. 15.
         * @version 0.1.0
         * @author Park Jun-Hong (parkjunhong77@gmail.com)
         * 
         * @see #orderBy
         */
        public ColumnDirection getOrderBy() {
            return orderBy;
        }
    }

}
