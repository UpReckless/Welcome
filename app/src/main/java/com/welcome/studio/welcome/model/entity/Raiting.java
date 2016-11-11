package com.welcome.studio.welcome.model.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by Royal on 18.10.2016.
 */
@Entity(nameInDb = "raiting")
@JsonIgnoreProperties(value = {"daoSession","myDao","user__resolvedKey"})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id",scope = Raiting.class)
public class Raiting {

    @Id
    private long id;
    @ToOne(joinProperty = "id")
    @JsonProperty("user")
    private User user;
    private Long likeCount;
    private Long willcomeCount;
    private Integer postCount;
    private Integer vippostCount;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 367980714)
    private transient RaitingDao myDao;
    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;

    public Raiting(){}

    public Raiting(User user, Long likeCount, Long willcomeCount, Integer postCount, Integer vippostCount) {
        this.user = user;
        this.likeCount = likeCount;
        this.willcomeCount = willcomeCount;
        this.postCount = postCount;
        this.vippostCount = vippostCount;
    }

    @Generated(hash = 951874590)
    public Raiting(long id, Long likeCount, Long willcomeCount, Integer postCount, Integer vippostCount) {
        this.id = id;
        this.likeCount = likeCount;
        this.willcomeCount = willcomeCount;
        this.postCount = postCount;
        this.vippostCount = vippostCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getLikeCount() {
        return this.likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getWillcomeCount() {
        return this.willcomeCount;
    }

    public void setWillcomeCount(Long willcomeCount) {
        this.willcomeCount = willcomeCount;
    }

    public Integer getPostCount() {
        return this.postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Integer getVippostCount() {
        return this.vippostCount;
    }

    public void setVippostCount(Integer vippostCount) {
        this.vippostCount = vippostCount;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 866595985)
    public User getUser() {
        long __key = this.id;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1504227233)
    public void setUser(@NotNull User user) {
        if (user == null) {
            throw new DaoException("To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.user = user;
            id = user.getId();
            user__resolvedKey = id;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    public User getUserFromJson(){
        return user;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 350476304)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRaitingDao() : null;
    }
}
