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
 * Date  : 2021. 8. 17. 오후 5:48:24
 *
 * Author: Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 */

package open.commons.csv;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

import open.commons.util.ArrayItr;

import au.com.bytecode.opencsv.ResultSetHelper;
import au.com.bytecode.opencsv.ResultSetHelperService;

/**
 * CSV 데이터를 파일로 저장하는 클래스. <br>
 * CSV 컬럼 데이터 타입에 따라 Quotes 사용 여부를 결정한다.
 * 
 * <br>
 * {@link au.com.bytecode.opencsv.CSVWriter}의 코드를 그대로 사용하였으며, CSV 헤더 데이터 부분만 추가하였습.
 * 
 * @since 2021. 8. 17.
 * @version _._._
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 * @see au.com.bytecode.opencsv.CSVWriter
 */
public class CSVWriter implements Closeable {

    public static final int INITIAL_STRING_SIZE = 128;

    /** The character used for escaping quotes. */
    public static final char DEFAULT_ESCAPE_CHARACTER = '"';

    /** The default separator to use if none is supplied to the constructor. */
    public static final char DEFAULT_SEPARATOR = ',';

    /**
     * The default quote character to use if none is supplied to the constructor.
     */
    public static final char DEFAULT_QUOTE_CHARACTER = '"';

    /** The quote constant to use when you wish to suppress all quoting. */
    public static final char NO_QUOTE_CHARACTER = '\u0000';

    /** The escape constant to use when you wish to suppress all escaping. */
    public static final char NO_ESCAPE_CHARACTER = '\u0000';

    /** Default line terminator uses platform encoding. */
    public static final String DEFAULT_LINE_END = "\n";

    private Writer rawWriter;

    private PrintWriter pw;

    private char separator;

    private char quotechar;

    private char escapechar;

    private String lineEnd;

    private ResultSetHelper resultService = new ResultSetHelperService();

    /** 컬럼 데이터 타입에 따른 Quotes 사용여부 확인 */
    private final Function<Integer, Boolean> wrapWithQuotes;
    private final CsvHeader[] headers;

    /**
     * Constructs CSVWriter using a comma for the separator. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 17.		박준홍			최초 작성
     * </pre>
     *
     * @param writer
     *            the writer to an underlying CSV source.
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CSVWriter(Writer writer) {
        this(writer, DEFAULT_SEPARATOR);
    }

    /**
     * Constructs CSVWriter with supplied separator. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 17.		박준홍			최초 작성
     * </pre>
     *
     * @param writer
     *            the writer to an underlying CSV source.
     * @param separator
     *            the delimiter to use for separating entries.
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CSVWriter(Writer writer, char separator) {
        this(writer, separator, DEFAULT_QUOTE_CHARACTER);
    }

    /**
     * Constructs CSVWriter with supplied separator and quote char. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 17.		박준홍			최초 작성
     * </pre>
     *
     * @param writer
     *            the writer to an underlying CSV source.
     * @param separator
     *            the delimiter to use for separating entries
     * @param quotechar
     *            the character to use for quoted elements
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CSVWriter(Writer writer, char separator, char quotechar) {
        this(writer, separator, quotechar, DEFAULT_ESCAPE_CHARACTER);
    }

    /**
     * Constructs CSVWriter with supplied separator and quote char. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 17.		박준홍			최초 작성
     * </pre>
     *
     * @param writer
     *            the writer to an underlying CSV source.
     * @param separator
     *            the delimiter to use for separating entries
     * @param quotechar
     *            the character to use for quoted elements
     * @param escapechar
     *            the character to use for escaping quotechars or escapechars
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CSVWriter(Writer writer, char separator, char quotechar, char escapechar) {
        this(writer, separator, quotechar, escapechar, DEFAULT_LINE_END);
    }

    /**
     * Constructs CSVWriter with supplied separator, quote char, escape char and 'csv' headers.<br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 17.     박준홍         최초 작성
     * </pre>
     *
     * @param writer
     *            writer the writer to an underlying CSV source.
     * @param separator
     *            the delimiter to use for separating entries
     * @param quotechar
     *            the character to use for quoted elements
     * @param escapechar
     *            the character to use for escaping quotechars or escapechars
     * @param headers
     *            CSV 헤더
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CSVWriter(Writer writer, char separator, char quotechar, char escapechar, CsvHeader[] headers) {
        this(writer, separator, quotechar, escapechar, DEFAULT_LINE_END, headers);
    }

    /**
     * Constructs CSVWriter with supplied separator, quote char, escape char and line ending. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 17.		박준홍			최초 작성
     * </pre>
     *
     *
     * @param writer
     *            the writer to an underlying CSV source.
     * @param separator
     *            the delimiter to use for separating entries
     * @param quotechar
     *            the character to use for quoted elements
     * @param escapechar
     *            the character to use for escaping quotechars or escapechars
     * @param lineEnd
     *            the line feed terminator to use
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CSVWriter(Writer writer, char separator, char quotechar, char escapechar, String lineEnd) {
        this(writer, separator, quotechar, escapechar, lineEnd, null);
    }

    /**
     * Constructs CSVWriter with supplied separator, quote char, escape char, line ending and 'csv' headers. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 17.     박준홍         최초 작성
     * </pre>
     *
     * @param writer
     *            the writer to an underlying CSV source.
     * @param separator
     *            the delimiter to use for separating entries
     * @param quotechar
     *            the character to use for quoted elements
     * @param escapechar
     *            the character to use for escaping quotechars or escapechars
     * @param lineEnd
     *            the line feed terminator to use
     * @param headers
     *            CSV 헤더 정보
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CSVWriter(Writer writer, char separator, char quotechar, char escapechar, String lineEnd, final CsvHeader[] headers) {
        this.rawWriter = writer;
        this.pw = new PrintWriter(writer);
        this.separator = separator;
        this.quotechar = quotechar;
        this.escapechar = escapechar;
        this.lineEnd = lineEnd;
        this.headers = headers;
        this.wrapWithQuotes = headers != null //
                ? idx -> {
                    switch (headers[idx].getDataType()) {
                        case STR:
                        case GENERAL:
                            return true;
                        default:
                            return false;
                    }
                }
                : idx -> true //
        ;
    }

    /**
     * Constructs CSVWriter with supplied separator, quote char and 'csv' headers. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 17.     박준홍         최초 작성
     * </pre>
     *
     * @param writer
     *            the writer to an underlying CSV source.
     * @param separator
     *            the delimiter to use for separating entries
     * @param quotechar
     *            the character to use for quoted elements
     * @param headers
     *            CSV 헤더
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CSVWriter(Writer writer, char separator, char quotechar, CsvHeader[] headers) {
        this(writer, separator, quotechar, DEFAULT_ESCAPE_CHARACTER, headers);
    }

    /**
     * Constructs CSVWriter with supplied separator and quote char. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 17.		박준홍			최초 작성
     * </pre>
     *
     *
     * @param writer
     *            the writer to an underlying CSV source.
     * @param separator
     *            the delimiter to use for separating entries
     * @param quotechar
     *            the character to use for quoted elements
     * @param lineEnd
     *            the line feed terminator to use
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CSVWriter(Writer writer, char separator, char quotechar, String lineEnd) {
        this(writer, separator, quotechar, DEFAULT_ESCAPE_CHARACTER, lineEnd);
    }

    /**
     * Constructs CSVWriter with supplied separator, quote char, line ending and 'csv' headers. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 17.     박준홍         최초 작성
     * </pre>
     *
     * @param writer
     *            the writer to an underlying CSV source.
     * @param separator
     *            the delimiter to use for separating entries
     * @param quotechar
     *            the character to use for quoted elements
     * @param lineEnd
     *            the line feed terminator to use
     * @param headers
     *            CSV 헤더
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CSVWriter(Writer writer, char separator, char quotechar, String lineEnd, CsvHeader[] headers) {
        this(writer, separator, quotechar, DEFAULT_ESCAPE_CHARACTER, lineEnd, headers);
    }

    /**
     * Constructs CSVWriter with supplied separator and 'csv' headers. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 17.     박준홍         최초 작성
     * </pre>
     *
     * @param writer
     *            the writer to an underlying CSV source.
     * @param separator
     *            the delimiter to use for separating entries
     * @param headers
     *            CSV 헤더
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CSVWriter(Writer writer, char separator, CsvHeader[] headers) {
        this(writer, separator, DEFAULT_QUOTE_CHARACTER, headers);
    }

    /**
     * Constructs CSVWriter with supplied 'csv' headers. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 17.     박준홍         최초 작성
     * </pre>
     *
     * @param writer
     *            the writer to an underlying CSV source.
     * @param headers
     *            CSV 헤더
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public CSVWriter(Writer writer, CsvHeader[] headers) {
        this(writer, DEFAULT_SEPARATOR, headers);
    }

    /**
     * Checks to see if the there has been an error in the printstream. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 17.		박준홍			최초 작성
     * </pre>
     *
     * @return
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public boolean checkError() {
        return pw.checkError();
    }

    /**
     * Close the underlying stream writer flushing any buffered content.<br>
     *
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 17.		박준홍			최초 작성
     * </pre>
     * 
     * @throws IOException
     *             if bad things happen
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     * 
     * @see java.io.Closeable#close()
     */
    public void close() throws IOException {
        flush();
        pw.close();
        rawWriter.close();
    }

    /**
     * Flush underlying stream to writer. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 17.		박준홍			최초 작성
     * </pre>
     *
     * @throws IOException
     *             if bad things happen
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public void flush() throws IOException {
        pw.flush();
    }

    /**
     * 
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 17.		박준홍			최초 작성
     * </pre>
     *
     * @param nextElement
     * @return
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    protected StringBuilder processLine(String nextElement) {
        StringBuilder sb = new StringBuilder(INITIAL_STRING_SIZE);
        for (int j = 0; j < nextElement.length(); j++) {
            char nextChar = nextElement.charAt(j);
            if (escapechar != NO_ESCAPE_CHARACTER && nextChar == quotechar) {
                sb.append(escapechar).append(nextChar);
            } else if (escapechar != NO_ESCAPE_CHARACTER && nextChar == escapechar) {
                sb.append(escapechar).append(nextChar);
            } else {
                sb.append(nextChar);
            }
        }

        return sb;
    }

    /**
     * 
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 17.		박준홍			최초 작성
     * </pre>
     *
     * @param resultService
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public void setResultService(ResultSetHelper resultService) {
        this.resultService = resultService;
    }

    /**
     * 
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 17.		박준홍			최초 작성
     * </pre>
     *
     * @param line
     * @return
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    private boolean stringContainsSpecialCharacters(String line) {
        return line.indexOf(quotechar) != -1 || line.indexOf(escapechar) != -1;
    }

    /**
     * Writes the entire ResultSet to a CSV file.
     *
     * The caller is responsible for closing the ResultSet. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 17.		박준홍			최초 작성
     * </pre>
     *
     *
     * @param rs
     *            the recordset to write
     * @param includeColumnNames
     *            true if you want column names in the output, false otherwise
     *
     * @throws java.io.IOException
     *             thrown by getColumnValue
     * @throws java.sql.SQLException
     *             thrown by getColumnValue
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public void writeAll(java.sql.ResultSet rs, boolean includeColumnNames) throws SQLException, IOException {

        if (includeColumnNames) {
            writeColumnNames(rs);
        }

        while (rs.next()) {
            writeNext(resultService.getColumnValues(rs));
        }
    }

    /**
     * Writes the entire list to a CSV file. The list is assumed to be a String[] <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 17.		박준홍			최초 작성
     * </pre>
     *
     *
     * @param allLines
     *            a List of String[], with each String[] representing a line of the file.
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public void writeAll(List<String[]> allLines) {
        for (String[] line : allLines) {
            writeNext(line);
        }
    }

    /**
     * 
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 17.		박준홍			최초 작성
     * </pre>
     *
     * @param rs
     * @throws SQLException
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    protected void writeColumnNames(ResultSet rs) throws SQLException {
        writeNext(resultService.getColumnNames(rs));
    }

    /**
     * CSV 헤더가 있으면 파일에 추가한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2021. 8. 17.     박준홍         최초 작성
     * </pre>
     *
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public void writeHeader() {
        if (this.headers != null) {
            StringBuilder sb = new StringBuilder(INITIAL_STRING_SIZE);
            ArrayItr<CsvHeader> itr = new ArrayItr<>(this.headers);
            if (itr.hasNext()) {
                sb.append(itr.next().getHeader());
            }
            while (itr.hasNext()) {
                sb.append(this.separator);
                sb.append(itr.next().getHeader());
            }

            sb.append(this.lineEnd);
            this.pw.write(sb.toString());

        }
    }

    /**
     * Writes the next line to the file. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 17.		박준홍			최초 작성
     * </pre>
     *
     * @param nextLine
     *            a string array with each comma-separated element as a separate entry.
     *
     * @since 2021. 8. 17.
     * @version _._._
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public void writeNext(String[] nextLine) {

        if (nextLine == null) {
            return;
        }

        StringBuilder sb = new StringBuilder(INITIAL_STRING_SIZE);
        String nextElement = null;
        String processedString = null;
        for (int i = 0; i < nextLine.length; i++) {

            if (i != 0) {
                sb.append(this.separator);
            }

            nextElement = nextLine[i];
            if (nextElement == null) {
                continue;
            }

            processedString = (stringContainsSpecialCharacters(nextElement) ? processLine(nextElement) : nextElement).toString();

            if (this.quotechar != NO_QUOTE_CHARACTER // Quotes char이 설정되어 있고,
                    && this.wrapWithQuotes.apply(i) // 데이터 타입이 문자열이고,
                    && processedString.indexOf(this.separator) > -1 // 데이터에 구분자(separator)가 포함되어 있지 않고
            ) {
                sb.append(this.quotechar);
                sb.append(processedString);
                sb.append(this.quotechar);
            } else {
                sb.append(processedString);
            }
        }

        sb.append(this.lineEnd);
        this.pw.write(sb.toString());
    }

}
