package nguyenhuuvu.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MessageDTO {
    private String content;
    private Date time;
    private String sender;
    private String receiver;
}
