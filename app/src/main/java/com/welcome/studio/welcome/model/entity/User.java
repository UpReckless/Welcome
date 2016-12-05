package com.welcome.studio.welcome.model.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Royal on 18.10.2016.
 */
@Entity(nameInDb = "user")
@JsonIgnoreProperties(value = {"daoSession","myDao","raiting__resolvedKey"})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id",scope = User.class)
public class User {

    @Id
    private long id;

    @NotNull
    private String nickname;
    private String email;
    private String photoRef;
    @JsonIgnore
    private String photoPath;

    @NotNull
    @Unique
    private String imei;

   // @JsonProperty("raiting")
    @JsonSerialize(as = Raiting.class)
    @ToOne(joinProperty = "id")
    private Raiting raiting;

    @ToMany(referencedJoinProperty = "id")
    @JsonIgnore private List<ArchivePhoto> archivePhotos;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;

    @Generated(hash = 1986467490)
    private transient Long raiting__resolvedKey;

    public User(){}

    public User(String nickname, String email, String imei) {
        this.nickname = nickname;
        this.email = email;
        this.imei = imei;
    }
    public User(String nickname, String imei) {
        this.nickname = nickname;
        this.imei = imei;
    }

    @Generated(hash = 2089303606)
    public User(long id, @NotNull String nickname, String email, String photoRef, String photoPath, @NotNull String imei) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.photoRef = photoRef;
        this.photoPath = photoPath;
        this.imei = imei;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }


    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1173434320)
    public Raiting getRaiting() {
        long __key = this.id;
        if (raiting__resolvedKey == null || !raiting__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RaitingDao targetDao = daoSession.getRaitingDao();
            Raiting raitingNew = targetDao.load(__key);
            synchronized (this) {
                raiting = raitingNew;
                raiting__resolvedKey = __key;
            }
        }
        return raiting;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1975553804)
    public void setRaiting(@NotNull Raiting raiting) {
        if (raiting == null) {
            throw new DaoException(
                    "To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.raiting = raiting;
            id = raiting.getId();
            raiting__resolvedKey = id;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2049049730)
    public List<ArchivePhoto> getArchivePhotos() {
        if (archivePhotos == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ArchivePhotoDao targetDao = daoSession.getArchivePhotoDao();
            List<ArchivePhoto> archivePhotosNew = targetDao
                    ._queryUser_ArchivePhotos(id);
            synchronized (this) {
                if (archivePhotos == null) {
                    archivePhotos = archivePhotosNew;
                }
            }
        }
        return archivePhotos;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1777312561)
    public synchronized void resetArchivePhotos() {
        archivePhotos = null;
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

    public Raiting getRaitingFromJson(){
        return raiting;
    }

    public String getPhotoPath() {
        return this.photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }
}
