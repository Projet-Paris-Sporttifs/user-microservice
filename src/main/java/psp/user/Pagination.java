package psp.user;

import lombok.Data;

import java.util.stream.Stream;

@Data
public class Pagination<T> {

    private Stream<T> content;
    private long totalElements;
    private long totalPages;
    private boolean hasNext;
    private long pageNumber;
    private long pageSize;
}
