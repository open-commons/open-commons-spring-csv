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
 * Date  : 2021. 8. 10. 오후 5:27:38
 *
 * Author: Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 */

package open.commons.spring.csv;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 컬럼을 비교하기 위한 클래스.<br>
 * 
 * 같음(=), 다름(!=), 비교(?=, 자바 정규식을 기반)을 제공한다.
 * 
 * @since 2021. 8. 10.
 * @version 0.1.0
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
public class ColumnCondition {

    /** 컬럼 index */
    @Min(0)
    private Integer index;

    /** 비교 데이터 데이터 */
    @NotNull(message = "검색대상 비교 데이터를 반드시 설정되어야 합니다.")
    private Object data;

    /** 비교 연산자 */
    @NotNull(message = "컬럼 검색 연산자를 반드시 설정되어야 합니다.")
    private ColumnOp op;

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 10.		박준홍			최초 작성
     * </pre>
     *
     *
     * @since 2021. 8. 10.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public ColumnCondition() {
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 10.		박준홍			최초 작성
     * </pre>
     * 
     * @return the data
     *
     * @since 2021. 8. 10.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #data
     */

    public Object getData() {
        return data;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 10.		박준홍			최초 작성
     * </pre>
     * 
     * @return the index
     *
     * @since 2021. 8. 10.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #index
     */

    public Integer getIndex() {
        return index;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 10.		박준홍			최초 작성
     * </pre>
     * 
     * @return the op
     *
     * @since 2021. 8. 10.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #op
     */

    public ColumnOp getOp() {
        return op;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 10.		박준홍			최초 작성
     * </pre>
     *
     * @param data
     *            the data to set
     *
     * @since 2021. 8. 10.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #data
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 10.		박준홍			최초 작성
     * </pre>
     *
     * @param index
     *            the index to set
     *
     * @since 2021. 8. 10.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #index
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2021. 8. 10.		박준홍			최초 작성
     * </pre>
     *
     * @param op
     *            the op to set
     *
     * @since 2021. 8. 10.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #op
     */
    public void setOp(ColumnOp op) {
        this.op = op;
    }

    /**
     *
     * @since 2021. 8. 10.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ColumnCondition [index=");
        builder.append(index);
        builder.append(", data=");
        builder.append(data);
        builder.append(", op=");
        builder.append(op);
        builder.append("]");
        return builder.toString();
    }

}
