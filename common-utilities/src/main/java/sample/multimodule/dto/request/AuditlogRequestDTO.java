package sample.multimodule.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuditlogRequestDTO implements Serializable {

    private Long id;
    private String uuid;
    private String userid;
    private String auditType;
    private String logAction;
    private String logMessage;
    private String refrenceId;
    private String refrenceType;
    private long addTs;
    private long updTs;

}
