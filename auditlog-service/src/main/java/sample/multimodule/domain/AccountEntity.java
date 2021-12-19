package sample.multimodule.domain;

import sample.multimodule.converters.LocalDateTimeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ACCOUNT")
public class AccountEntity implements Serializable{

    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GENERATOR")
    //@SequenceGenerator(name= "ACCOUNT_SEQ_GENERATOR", sequenceName = "ACCOUNT_SEQUENCE", schema = "PUBLIC", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "ID", updatable = false, nullable = false)
    private Long id;
    
    @Column(name= "ACC_NUMBER")
    private String number;

    @Column(name= "ACC_TYPE")
    private String type;

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
    /**
     * Create an empty account.
     */
    public AccountEntity() {

    }

    /**
     * Create a new account.
     *
     * @param number the account number
     * @param id the account id
     */
    public AccountEntity(Long id, String number) {
        this.number = number;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getAddTs() {
        return addTs;
    }

    public void setAddTs(LocalDateTime addTs) {
        this.addTs = addTs;
    }

    public LocalDateTime getUpdTs() {
        return updTs;
    }

    public void setUpdTs(LocalDateTime updTs) {
        this.updTs = updTs;
    }
    
}
