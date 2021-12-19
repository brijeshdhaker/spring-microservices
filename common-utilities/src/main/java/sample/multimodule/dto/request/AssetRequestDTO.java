package sample.multimodule.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AssetRequestDTO implements Serializable {

    private Long assetId;
    private String uuid;
    private String assetName;
    private String serialNo;
    private String status;
    private Long userid;
    public String addTs;
    public String updTs;

}
