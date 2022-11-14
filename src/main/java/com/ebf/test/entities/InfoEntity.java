package com.ebf.test.entities;

import com.ebf.test.utils.Utils;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@MappedSuperclass
public abstract class InfoEntity extends BaseEntity {

    protected Date creationDate;

    protected Date editDate;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    @PrePersist
    private void prePersistEntity() {

        if (getId() == null) {
            setId(Utils.generateUniqueId());
        }

        if (getCreationDate() == null) {
            setCreationDate(new Date());
        }

        if (getEntityVersion() == null) {
            setEntityVersion(0L);
        }

        setEditDate(new Date());
    }

    @PreUpdate
    private void preUpdatePrivateMetaEntity() {
        setEditDate(new Date());

        if (getEntityVersion() == null) {
            setEntityVersion(0L);
        }

        Long version = getEntityVersion();
        setEntityVersion(version == null ? 0 : ++version);
    }


}
