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
 * Date  : 2021. 8. 15. 오후 5:37:08
 *
 * Author: Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 */

package open.commons.csv;

import javax.validation.constraints.Min;

/**
 * CSV 데이터 컬럼 정렬 설정.
 * 
 * @since 2021. 8. 15.
 * @version 0.1.0
 * @author Park Jun-Hong (parkjunhong77@gmail.com)
 */
public class ColumnSort {

    /** 컬럼 이름 */
    @Min(1)
    private final Integer index;
    /** 정렬 방향 */
    private final ColumnDirection direction;

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
     * @param index
     * @param direction
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     */
    public ColumnSort(Integer index, ColumnDirection direction) {
        this.index = index;
        this.direction = direction;
    }

    /**
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ColumnSort other = (ColumnSort) obj;
        if (direction != other.direction)
            return false;
        if (index == null) {
            if (other.index != null)
                return false;
        } else if (!index.equals(other.index))
            return false;
        return true;
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
     * @return the direction
     *
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see #direction
     */

    public ColumnDirection getDirection() {
        return direction;
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
     * @since 2021. 8. 15.
     * @version 0.1.0
     * @author Park Jun-Hong (parkjunhong77@gmail.com)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((direction == null) ? 0 : direction.hashCode());
        result = prime * result + ((index == null) ? 0 : index.hashCode());
        return result;
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
        builder.append("ColumnSort [index=");
        builder.append(index);
        builder.append(", direction=");
        builder.append(direction);
        builder.append("]");
        return builder.toString();
    }

}
