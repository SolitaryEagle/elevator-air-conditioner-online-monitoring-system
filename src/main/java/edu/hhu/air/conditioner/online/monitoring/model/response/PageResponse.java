package edu.hhu.air.conditioner.online.monitoring.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static <T, R> PageResponse<R> of(Page<T> page, Function<? super T, ? extends R> mapper) {
        return of(page.getTotalElements(), page.stream().map(mapper).collect(Collectors.toList()));
    }

}
