package ru.itmo.wp.model.domain;

import java.io.Serializable;
import java.util.Date;

public interface Element extends Serializable {

    public long getId();

    public void setId(long id);

    public Date getCreationTime();

    public void setCreationTime(Date creationTime);
}
