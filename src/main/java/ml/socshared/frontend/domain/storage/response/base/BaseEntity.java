package ml.socshared.frontend.domain.storage.response.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


public abstract class BaseEntity {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;

    public BaseEntity() {
        this.status = Status.ACTIVE;
    }
}
