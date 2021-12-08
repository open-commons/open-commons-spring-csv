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
 * Date  : 2021. 12. 8. 오후 6:17:16
 *
 * Author: Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 */

package open.commons.csv.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.scheduling.annotation.Scheduled;

import open.commons.Result;
import open.commons.concurrent.Mutex;
import open.commons.csv.ColumnCondition;
import open.commons.csv.ColumnDirection;
import open.commons.csv.ColumnSort;
import open.commons.csv.CsvFileConfig;
import open.commons.csv.CsvFileOnMemory;
import open.commons.csv.CsvFileSampling;
import open.commons.csv.CsvHeader;
import open.commons.csv.ManagedCsvFile;
import open.commons.csv.MemorizedCsvFile;
import open.commons.csv.service.ICsvService;
import open.commons.csv.service.PositionDir;
import open.commons.spring.web.mvc.IAsyncJobHandler;
import open.commons.spring.web.mvc.service.AbstractGenericService;
import open.commons.spring.web.servlet.BadRequestException;
import open.commons.spring.web.servlet.InternalServerException;
import open.commons.test.StopWatch;
import open.commons.utils.ExceptionUtils;
import open.commons.utils.IOUtils;
import open.commons.utils.NumberUtils;

import au.com.bytecode.opencsv.CSVReader;

/**
 * CSV 파일을 처리하는 기능을 제공하는 클래스.<br>
 * CSV 파일 유지 유효시간과 이를 검증하는 주기를 아래와 같은 속성명으로 설정합니다.
 * <ul>
 * <li>CSV 파일 유지 유효시간:
 * <ul>
 * <li>패턴: [0-9]+[s|m|h], (s: 초, m: 분, h: 시간)
 * <li>속성: application.csv.ttl.value
 * </ul>
 * <li>검증하는 주기 설정: application.csv.ttl.cron
 * <ul>
 * <li>
 * 
 * <pre>
 * A cron-like expression, extending the usual UN*X definition to include triggers on the second, minute, hour, day of month, month, and day of week.
 *      For example, "0 * * * * MON-FRI" means once per minute on weekdays (at the top of the minute - the 0th second).
 *      The fields read from left to right are interpreted as follows.
 *      - second
 *      - minute
 *      - hour
 *      - day of month
 *      - month
 *      - day of week
 *      
 *      The special value "-" indicates a disabled cron trigger, primarily meant for externally specified values resolved by a ${...} placeholder.
 * </pre>
 * 
 * <li>속성: application.csv.ttl.cron
 * </ul>
 * </ul>
 * 
 * @since 2021. 8. 12.
 * @version _._._
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
public class CsvService extends AbstractGenericService implements ICsvService, IAsyncJobHandler {

    public static final String BEAN_QUALIFIER = "open.commons.csv.service.impl.CsvService";

    /** static method 를 위한 {@link Logger} */
    private static final Logger sLogger = LoggerFactory.getLogger(CsvService.class);

    /**
     * CSV 파일 데이터
     * <ul>
     * <li>키: CSV 파일 적재 데이터 식별 정보
     * <li>값: CSV 파일 메모리 적재 데이터
     * </ul>
     */
    private static final Map<String, MemorizedCsvFile> MANAGED_CSV_FILES = new ConcurrentSkipListMap<>();
    private static final Mutex MUTEX_CSV_FILE = new Mutex("mutex for 'CSV_FILE'");

    /** 시간 포맷 */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    /** CSV 파일 유지 유효시간. 단위: ms (millisecond) */
    private final int ttl;

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
     * @since 2021. 8. 12.
     */
    public CsvService() {
        this("1H");
    }

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
     * @param ttl
     *            TTL 유효시간 표기. 포맷: ^([1-9][0-9]*)([s|m|h])$
     *
     * @since 2021. 8. 12.
     */
    public CsvService(@Value("${application.csv.ttl.value}") String ttl) {
        Matcher m = Pattern.compile("^([1-9][0-9]*)([s|m|h])$", Pattern.CASE_INSENSITIVE).matcher(ttl);
        if (m.matches()) {
            int n = Integer.parseInt(m.group(1));
            String u = m.group(2);
            int uv = 0;
            switch (u.toLowerCase()) {
                case "s":
                    uv = 1000;
                    break;
                case "m":
                    uv = 60 * 1000;
                    break;
                case "h":
                    uv = 60 * 60 * 1000;
                    break;
                default:
                    throw ExceptionUtils.newException(IllegalArgumentException.class, "CSV 파일 TTL(Time To Live) 시간설정이 올바르지 않습니다. 정규식=%s, 입력=%s", ttl);
            }
            this.ttl = n * uv;
        } else {
            throw ExceptionUtils.newException(IllegalArgumentException.class, "CSV 파일 TTL(Time To Live) 시간설정이 올바르지 않습니다. 정규식=%s, 입력=%s", ttl);
        }
    }

    private long createReleasedTime(MemorizedCsvFile csvfile) {
        return csvfile.getAccessed() + this.ttl;
    }

    /**
     * @since 2021. 8. 16.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see kr.re.etri.dt.fileservice.service.v1.ICsvService#delete(java.lang.String, int)
     */
    @Override
    public Result<Boolean> delete(@NotEmpty String uuid, @Min(1) int lineNumber) {
        return execute(() -> {
            MemorizedCsvFile csvfile = null;
            synchronized (MUTEX_CSV_FILE) {
                csvfile = getCsvFile(uuid);
            }
            return csvfile.delete(lineNumber);
        }, "데이터 삭제");
    }

    /**
     * @since 2021. 8. 12.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see open.commons.spring.web.mvc.IAsyncJobHandler#getAsyncManagerHolder()
     */
    @Override
    public Object getAsyncManagerHolder() {
        return null;
    }

    private MemorizedCsvFile getCsvFile(String uuid) {
        MemorizedCsvFile csvfile = MANAGED_CSV_FILES.get(uuid);
        if (csvfile == null) {
            String errMsg = String.format("식별정보(%s)에 해당하는 파일이 존재하지 않습니다.", uuid);
            logger.warn(errMsg);
            throw new BadRequestException(errMsg);
        }

        return csvfile;
    }

    /**
     * @since 2021. 8. 15.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see kr.re.etri.dt.fileservice.service.v1.ICsvService#getManagedFiles()
     */
    @Override
    public Result<Set<String>> getManagedFiles() {
        synchronized (MUTEX_CSV_FILE) {
            return Result.success(MANAGED_CSV_FILES.keySet());
        }
    }

    /**
     * @since 2021. 8. 16.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see kr.re.etri.dt.fileservice.service.v1.ICsvService#insert(java.lang.String, int,
     *      kr.re.etri.dt.fileservice.service.v1.PositionDir, java.lang.Object[])
     */
    @Override
    public Result<Boolean> insert(@NotEmpty String uuid, @Min(1) int lineNumber, @NotNull PositionDir position, @NotEmpty Object[] data) {
        return execute(() -> {
            MemorizedCsvFile csvfile = null;
            synchronized (MUTEX_CSV_FILE) {
                csvfile = getCsvFile(uuid);
            }
            return csvfile.insertData(lineNumber, position, data);
        }, "데이터 추가");
    }

    /**
     * @since 2021. 8. 12.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see kr.re.etri.dt.fileservice.service.v1.ICsvService#load(String, open.commons.csv.CsvFileConfig, CsvHeader[],
     *      boolean, java.lang.String, boolean)
     */
    @Override
    public Result<CsvFileOnMemory> load(@NotEmpty String uuid, @NotNull CsvFileConfig config, @NotEmpty CsvHeader[] headers, boolean hasHeader, @NotNull String filepath,
            boolean reload) {
        // #0. 파일 중복 적재 요청 검증
        validateCsvFile(uuid, filepath, reload);

        // #1. 파일 적재
        StopWatch watch = new StopWatch();
        try (CSVReader reader = createCSVReader(config, filepath)) {

            logger.info("[적재시작] 파일: {}", filepath);

            String[] readline = null;
            int lineCount = 0;

            // #1-1. 메모리 적재 시작
            MemorizedCsvFile managedCsvFile = new MemorizedCsvFile(uuid, config, headers, hasHeader, filepath);

            watch.start();
            while ((readline = reader.readNext()) != null) {
                lineCount++;
                managedCsvFile.loadLineData(lineCount, readline);

                // if (lineCount % 10000 == 0) {
                // logger.info("[적재 중] {}", NumberUtils.INT_TO_STR.apply(lineCount));
                // }
            }

            watch.stop();
            logger.info("[적재완료] 데이터개수: {}, 파일: {}, 경과시간: {}", NumberUtils.INT_TO_STR.apply(lineCount), filepath, watch.getAsPretty());

            // #2. 메모리 적재 등록
            Result<Boolean> resultRegistered = registerManagedCsvFile(uuid, filepath, managedCsvFile, reload);
            if (resultRegistered.getResult()) {
                // #1-2. 메모리 적재 결과
                CsvFileOnMemory csvfile = new CsvFileOnMemory(filepath, lineCount);
                csvfile.setHeaders(headers);

                return Result.success(csvfile);
            } else {
                return Result.copyOf(resultRegistered);
            }
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            logger.error("CSV 파일을 읽는 도중 에러가 발생하였습니다.", e);
            return Result.error("CSV 파일을 읽는 도중 에러가 발생하였습니다. 원인=%s, 타입=%s", e.getMessage(), e.getClass());
        }
    }

    /**
     * @since 2021. 8. 15.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see kr.re.etri.dt.fileservice.service.v1.ICsvService#read(java.lang.String, java.lang.Integer,
     *      java.lang.Integer)
     */
    @Override
    public Result<ManagedCsvFile> read(@NotEmpty String uuid, @Min(1) Integer lineNumber, @Min(1) Integer count) {

        return execute(() -> {
            MemorizedCsvFile csvfile = null;
            synchronized (MUTEX_CSV_FILE) {
                csvfile = getCsvFile(uuid);
            }
            ManagedCsvFile managed = csvfile.read(lineNumber, count);
            managed.setReleaseTime(createReleasedTime(csvfile));

            return Result.success(managed);
        }, "데이터 읽기");
    }

    /**
     * @since 2021. 8. 15.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see kr.re.etri.dt.fileservice.service.v1.ICsvService#release(java.lang.String)
     */
    @Override
    public Result<Boolean> release(@NotEmpty String uuid) {
        return unregisterManagedCsvFile(uuid);
    }

    /**
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see kr.re.etri.dt.fileservice.service.v1.ICsvService#reload(java.lang.String, String)
     */
    @Override
    public Result<CsvFileOnMemory> reload(@NotEmpty String uuid, String filepath) {
        return execute(() -> {
            MemorizedCsvFile csvfile = null;
            synchronized (MUTEX_CSV_FILE) {
                csvfile = getCsvFile(uuid);
            }

            if (!csvfile.getFilepath().equals(filepath)) {
                throw ExceptionUtils.newException(BadRequestException.class, "기존 파일과 요청한 파일명이 일치하지 않습니다. 기존=%s, 입력=%s", csvfile.getFilepath(), filepath);
            }

            return load(uuid, csvfile.getCsvFileConfig(), csvfile.getHeaders(), csvfile.getHasHeader(), filepath, true);
        }, "파일 재적재");
    }

    /**
     * @since 2021. 8. 13.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see kr.re.etri.dt.fileservice.service.v1.ICsvService#sample(open.commons.csv.CsvFileConfig, java.lang.String,
     *      int)
     */
    @Override
    public Result<CsvFileSampling> sample(@NotNull CsvFileConfig config, @NotNull String filepath, int count) {

        final int MAX_READ_COUNT = count < 0 ? Integer.MAX_VALUE : count;

        CsvFileSampling sampling = new CsvFileSampling(filepath);
        try (CSVReader reader = createCSVReader(config, filepath, 0)) {
            String[] headers = null;
            String[] readline = null;

            int readCount = 0;
            // #0. 헤더 정보 확인
            if (config.getSkip() < 1) {
                readline = reader.readNext();
                readCount++;
                // #0-1 첫번째 데이터의 길이를 이용하여 컬럼 정보를 생성
                headers = IntStream.range(1, readline.length + 1).mapToObj(String::valueOf).toArray(String[]::new);
            } else {
                final int skipLine = config.getSkip() - 1;
                // skip data
                int read = 0;
                while (read < skipLine) {
                    reader.readNext();
                    read++;
                }
                headers = reader.readNext();
                // #0-1. 1번째 데이터 읽기 - 헤더정보가 없는 경우, 1행을 읽어서 헤더 개수를 정의해야 하기 때문에,
                // 실제 데이터 처리는 2번째 데이터부터 동일한 로직을 적용하기 위함.
                readline = reader.readNext();
                readCount++;
            }

            // #1. 데이터 로딩
            sampling.setHeaders(headers);
            if (readline == null) {
                return Result.success(sampling);
            }

            // #1-1. 헤더와 첫번째 데이터 설정
            sampling.addData(readline);
            while ((readline = reader.readNext()) != null && readCount < MAX_READ_COUNT) {
                sampling.addData(readline);
                readCount++;
            }

            return new Result<>(sampling, true);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            logger.error("CSV 파일을 읽는 도중 에러가 발생하였습니다.", e);
            return Result.error("CSV 파일을 읽는 도중 에러가 발생하였습니다. 원인=%s, 타입=%s", e.getMessage(), e.getClass());
        }
    }

    /**
     * @since 2021. 8. 16.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see kr.re.etri.dt.fileservice.service.v1.ICsvService#search(java.lang.String, java.util.List,
     *      org.springframework.data.domain.Pageable)
     */
    @Override
    public Result<ManagedCsvFile> search(@NotEmpty String uuid, List<ColumnCondition> conditions, @NotNull Pageable pageable) {

        return execute(() -> {
            MemorizedCsvFile csvfile = null;
            synchronized (MUTEX_CSV_FILE) {
                csvfile = getCsvFile(uuid);
            }

            // #0. 정렬 조건
            ColumnSort clmnSort = null;
            Sort sort = pageable.getSort();
            if (sort != null) {
                Iterator<Order> itr = sort.iterator();
                while (itr.hasNext()) {
                    Order order = itr.next();
                    clmnSort = new ColumnSort(Integer.parseInt(order.getProperty()) - 1, Direction.ASC.equals(order.getDirection()) ? ColumnDirection.ASC : ColumnDirection.DESC);
                    break;
                }
            }

            ManagedCsvFile managed = csvfile.search(clmnSort, conditions, pageable);
            managed.setReleaseTime(createReleasedTime(csvfile));

            return Result.success(managed);
        }, "데이터 검색");
    }

    /**
     * @since 2021. 8. 15.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see kr.re.etri.dt.fileservice.service.v1.ICsvService#update(java.lang.String, int, Object[])
     */
    @Override
    public Result<Boolean> update(@NotEmpty String uuid, @Min(1) int lineNumber, @NotEmpty Object[] data) {

        return execute(() -> {
            MemorizedCsvFile csvfile = null;
            synchronized (MUTEX_CSV_FILE) {
                csvfile = getCsvFile(uuid);
            }
            return csvfile.updateLine(lineNumber, data);
        }, "데이터 변경");
    }

    /**
     * 설정된 시간마다 메모리에 올라간 CSV 파일 데이터의 TTL 시간을 검증한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     *
     *
     * @since 2021. 8. 15.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    @Scheduled(cron = "${application.csv.ttl.cron}")
    public void validateManagedCsvFilesTTL() {
        synchronized (MUTEX_CSV_FILE) {
            MemorizedCsvFile csvfile = null;
            for (Entry<String, MemorizedCsvFile> entry : MANAGED_CSV_FILES.entrySet()) {
                csvfile = entry.getValue();

                logger.debug("[validated] '{}'.accessed={}, release={}", csvfile.getFilepath(), DATE_FORMAT.format(new Date(csvfile.getAccessed())),
                        DATE_FORMAT.format(new Date(createReleasedTime(csvfile))));

                if (csvfile.afterAccessed() > this.ttl) {
                    MANAGED_CSV_FILES.remove(entry.getKey());
                    logger.info("'{}' 파일이 TTL 초과로 제거되었습니다. 최종접근시간={}, 경과시간={}ms", csvfile.getFilepath(), DATE_FORMAT.format(new Date(csvfile.getAccessed())),
                            csvfile.afterAccessed());
                }
            }
        }
    }

    /**
     * @since 2021. 8. 16.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see kr.re.etri.dt.fileservice.service.v1.ICsvService#write(java.lang.String, java.lang.String)
     */
    @Override
    public Result<Boolean> write(@NotEmpty String uuid, String filepath) {
        return execute(() -> {
            MemorizedCsvFile csvfile = null;
            synchronized (MUTEX_CSV_FILE) {
                csvfile = getCsvFile(uuid);
                return csvfile.write(filepath);
            }
        }, "파일 저장");
    }

    private static CSVReader createCSVReader(CsvFileConfig config, String filepath) {
        return createCSVReader(config, filepath, -1);
    }

    private static CSVReader createCSVReader(CsvFileConfig config, String filepath, int skip) {
        return new CSVReader(IOUtils.getReader(new File(filepath), config.getCharsetName()) //
                , config.getSeparator() //
                , config.getQuotechar() //
                , config.getEscape() //
                , skip < 0 ? config.getSkip() : skip //
                , config.isStrictQuotes() //
                , config.isIgnoreLeadingWhiteSpace());
    }

    /**
     * 읽어들인 CSV 파일 데이터를 등록한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 13.     박준홍         최초 작성
     * </pre>
     *
     * @param uuid
     *            CSV 파일 적재 데이터 식별 정보
     * @param filepath
     *            CSV 파일 경로
     * @param managedCsvFile
     *            읽어들인 CSV 파일 데이터
     * @param reload
     *            다시 읽기 여부
     * @return
     *
     * @since 2021. 8. 13.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    private static Result<Boolean> registerManagedCsvFile(String uuid, String filepath, MemorizedCsvFile managedCsvFile, boolean reload) {
        try {
            synchronized (MUTEX_CSV_FILE) {
                if (MANAGED_CSV_FILES.containsKey(uuid) && !reload) {
                    String errMsg = String.format("[적재 실패] 동일한 식별정보(%s)로 적재되어 있는 파일니다. 파일명=%s", uuid, filepath);
                    sLogger.warn(errMsg);
                    throw ExceptionUtils.newException(InternalServerException.class, errMsg);
                } else {
                    MANAGED_CSV_FILES.put(uuid, managedCsvFile);
                    sLogger.info("[적재 등록] '{}' 파일이 적재되었습니다. 정보={}", filepath, uuid);
                    return Result.success(true);
                }
            }
        } catch (Exception e) {
            String errMsg = String.format("[적재 실패] '%s' 파일을 적재하지 못하였습니다. 정보=%s, 원인=%s", filepath, uuid, e.getMessage());
            sLogger.error(errMsg, e);
            throw ExceptionUtils.newException(InternalServerException.class, e, errMsg);
        }
    }

    /**
     * 메모리 적재 중인 CSV 파일을 해제한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 15.     박준홍         최초 작성
     * </pre>
     *
     * @param uuid
     *            CSV 파일 적재 데이터 식별 정보
     * @return
     *
     * @since 2021. 8. 15.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    private static Result<Boolean> unregisterManagedCsvFile(String uuid) {
        try {
            synchronized (MUTEX_CSV_FILE) {
                MemorizedCsvFile file = MANAGED_CSV_FILES.remove(uuid);
                String msg = null;
                if (file != null) {
                    msg = String.format("[해제 성공] uuid=%s, file=%s", uuid, file.getFilepath());
                    sLogger.info(msg);
                } else {
                    msg = String.format("[해제 성공] uuid=%s, file=None", uuid);
                    sLogger.info(msg);
                }

                return Result.success(true).setMessage(msg);
            }
        } catch (Exception e) {
            String errMsg = String.format("[해제 실패] '%s'에 해당하는 CSV 파일을 제거하지 못하였습니다. 원인=%s", uuid, e.getMessage());
            sLogger.error(errMsg, e);
            throw ExceptionUtils.newException(InternalServerException.class, e, errMsg);
        }
    }

    /**
     * CSV 파일 메모리 적재작업을 등록한다.<br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 13.     박준홍         최초 작성
     * </pre>
     * 
     * @param uuid
     *            CSV 파일 적재 데이터 식별 정보
     * @param filepath
     *            CSV 파일 경로
     * @param reload
     *            다시읽기 여부
     * @since 2021. 8. 13.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    private static void validateCsvFile(String uuid, String filepath, boolean reload) {
        synchronized (MUTEX_CSV_FILE) {
            if (MANAGED_CSV_FILES.containsKey(uuid) && !reload) {
                String errMsg = String.format("[적재요청 실패] 이미 적재되어 있는 파일에 대한 중복 요청입니다. 파일명=%s", filepath);
                sLogger.warn(errMsg);
                throw ExceptionUtils.newException(InternalServerException.class, errMsg);
            }
        }
    }

}
