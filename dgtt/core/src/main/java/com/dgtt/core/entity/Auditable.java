package com.dgtt.core.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DELL
 * @param <U>
 */
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> implements Serializable {

    private static final long serialVersionUID = 5282450495494154675L;

    @Column(name = "created_date",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @Column(name = "modified_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifiedDate;

    @Column(name = "created_by",
            columnDefinition = "VARCHAR(255) DEFAULT 'Unknown'")
    @CreatedBy
    protected U createdBy;

    @Column(name = "modified_by",
            columnDefinition = "VARCHAR(255) DEFAULT 'Unknown'")
    @LastModifiedBy
    protected U modifiedBy;
}
