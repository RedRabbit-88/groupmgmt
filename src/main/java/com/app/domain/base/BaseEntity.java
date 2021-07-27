package com.app.domain.base;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @Column(name = "cre_dt", nullable = false, updatable = false)
    private LocalDateTime createDate;
    @Column(name = "cre_usr_id", nullable = false, updatable = false)
    protected String createUserId;
    @Column(name = "upd_dt", nullable = false)
    private LocalDateTime updateDate;
    @Column(name = "upd_usr_id", nullable = false)
    protected String updateUserId;

    @PrePersist
    protected void onCreate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        createDate = localDateTime;
        updateDate = localDateTime;
        createUserId = "TEMP";
        updateUserId = "TEMP";
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = LocalDateTime.now();
    }
}
