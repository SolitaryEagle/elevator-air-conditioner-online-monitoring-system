package edu.hhu.air.conditioner.online.monitoring.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 覃国强
 * @date 2019/5/4 10:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<E> {

    private long count;
    private List<E> data;

    public static <E> PageResponse<E> of(long count, List<E> data) {
        return new PageResponse<>(count, data);
    }

}
