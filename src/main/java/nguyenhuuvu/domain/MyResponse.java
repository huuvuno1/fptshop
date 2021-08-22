package nguyenhuuvu.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MyResponse {
    private int status;
    private Date time;
    private String message;
    private String errorCode;
}
