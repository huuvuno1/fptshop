package nguyenhuuvu.entity;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class Common {
    @CreatedDate
    private Date createDate;

    @UpdateTimestamp
    private Date upDateAt;
}
