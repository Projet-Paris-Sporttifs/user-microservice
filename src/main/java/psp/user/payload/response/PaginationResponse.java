package psp.user.payload.response;

import lombok.Data;

import java.util.stream.Stream;

@Data
public class PaginationResponse<T> {

    private Stream<T> content;
    private long totalElements;
    private long totalPages;
    private boolean hasNext;
    private long pageNumber;
    private long pageSize;
}
