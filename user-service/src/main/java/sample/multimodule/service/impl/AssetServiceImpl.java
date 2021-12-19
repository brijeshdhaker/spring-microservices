package sample.multimodule.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.FluxSink;
import sample.multimodule.domain.AssetEntity;
import sample.multimodule.domain.UserEntity;
import sample.multimodule.dto.avro.AuditlogDTO;
import sample.multimodule.dto.request.AssetRequestDTO;
import sample.multimodule.exceptions.AppException;
import sample.multimodule.helpers.ServiceHelper;
import sample.multimodule.repository.AssetRepository;
import sample.multimodule.repository.UserRepository;
import sample.multimodule.service.AssetService;

import java.util.ArrayList;
import java.util.List;

@Service("assetService")
public class AssetServiceImpl implements AssetService {

    private static final Logger log = LoggerFactory.getLogger(AssetServiceImpl.class);

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    UserRepository userRepository;

    @Qualifier("auditlogPublisher")
    @Autowired
    private FluxSink<AuditlogDTO> auditLogSink;

    @Override
    public List<AssetRequestDTO> listAssets() throws AppException {
        List<AssetRequestDTO> dtos = new ArrayList<>();
        Iterable<AssetEntity> assets = assetRepository.findAll();
        for(AssetEntity asset : assets){
            dtos.add(toRequestDTO(asset));
        }
        return dtos;
    }

    @Override
    public AssetRequestDTO getAsset(Long assetId) throws AppException {
        AssetEntity entity = assetRepository.findById(assetId).orElse(null);
        return toRequestDTO(entity);
    }

    @Override
    public List<AssetRequestDTO> getUserAssets(Long userid) throws AppException {
        return null;
    }

    @Override
    public AssetRequestDTO saveAsset(AssetRequestDTO assetRequestDTO) throws AppException {
        AssetEntity entity = assetRepository.save(toEntity(assetRequestDTO));
        AssetRequestDTO _asset = toRequestDTO(entity);
        if(assetRequestDTO.getAssetId() == null || assetRequestDTO.getAssetId().equals(0)){
            this.auditLogSink.next(ServiceHelper.toAuditlog(Long.toString(_asset.getAssetId()),"SAVE", String.format("Asset with id %d successfully added.",_asset.getAssetId())));
            log.info("Asset with id {} successfully added.",_asset.getAssetId());
        }else{
            this.auditLogSink.next(ServiceHelper.toAuditlog(Long.toString(_asset.getAssetId()),"UPDATE", String.format("Asset with id %d successfully updated.",_asset.getAssetId())));
            log.info("Asset with id {} successfully updated.",_asset.getAssetId());
        }
        return _asset;
    }


    private AssetEntity toEntity(AssetRequestDTO dto){
        AssetEntity entity = null;
        if(dto!= null){
            entity = new AssetEntity();
            entity.setAssetId(dto.getAssetId());
            entity.setAssetName(dto.getAssetName());
            entity.setSerialNo(dto.getSerialNo());
            entity.setStatus(dto.getStatus());
            UserEntity ue = userRepository.findById(dto.getUserid()).orElse(null);
            if (ue != null) {
                entity.setUser(ue);
            }
        }
        return  entity;
    }

    public AssetRequestDTO toRequestDTO(AssetEntity entity) {
        AssetRequestDTO dto = null;
        if(entity != null){
            dto = new AssetRequestDTO();
            dto.setAssetId(entity.getAssetId());
            dto.setAssetName(entity.getAssetName());
            dto.setSerialNo(entity.getSerialNo());
            dto.setStatus(entity.getStatus());
            dto.setUserid(entity.getUser().getId());
        }
        return  dto;
    }
}
