package lev.philippov.geekmarket.errorHandlers;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemErrorResponse {
    private int status;
    private String message;
    private long timestamp;

}
