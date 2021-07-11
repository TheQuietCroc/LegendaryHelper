package com.thequietcroc.legendary.models;

import com.thequietcroc.legendary.database.daos.BaseDao;
import com.thequietcroc.legendary.database.entities.BaseEntity;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.io.Serializable;
import java.util.Comparator;

public abstract class BaseItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id = null;
    private String name = "";

    public BaseItem(final BaseEntity baseEntity) {
        setId(baseEntity.getId());
        setName(baseEntity.getName());
    }

    public BaseItem(final String name) {
        setName(name);
    }

    public Integer getId() {
        return id;
    }

    protected void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public abstract BaseEntity toEntity();

    public abstract void dbSave();

    public abstract void dbDelete();

    protected  <D extends BaseDao<E>, E extends BaseEntity> void dbSave(final D dao, final E entity) {
        new DbAsyncTask(() -> {
            if (null == getId()) {
                setId((int) dao.insert(entity));
            } else if (0 < getId()) {
                dao.update(entity);
            }
        });
    }

    protected  <D extends BaseDao<E>, E extends BaseEntity> void dbDelete(final D dao, final E entity) {
        new DbAsyncTask(() ->  {
            if (null != getId() && 0 < getId()) {
                dao.delete(entity);
            }
        });
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof BaseItem)) {
            return false;
        }

        final BaseItem b = (BaseItem) o;

        return id.equals(b.id);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    public static class BaseItemComparator<T extends BaseItem> implements Comparator<T> {
        @Override
        public int compare(final T t1, final T t2) {
            return t1.getName().compareTo(t2.getName());
        }
    }
}
