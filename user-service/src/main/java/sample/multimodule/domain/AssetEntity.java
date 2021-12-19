package sample.multimodule.domain;

import sample.multimodule.converters.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="ASSETS")
public class AssetEntity {

    //@GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    @Column(name="ASSETS_ID")
    private Long assetId;

    @Column(name="ASSETS_NAME")
    private String assetName;

    @Column(name="SERIAL_NO")
    private String serialNo;

    @Column(name="STATUS")
    private String status;

    @ManyToOne
    @JoinColumn(name ="USERID")
    private UserEntity user;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "ADD_TS")
    protected LocalDateTime addTs;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "UPD_TS")
    protected LocalDateTime updTs;

    @PrePersist
    public void prePersist() {
        addTs = updTs = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updTs = LocalDateTime.now();
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "sample.multimodule.domain.AssetEntity[ id=" + assetId + " ]";
    }
}
