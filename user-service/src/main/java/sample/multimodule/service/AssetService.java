package sample.multimodule.service;

import sample.multimodule.dto.request.AssetRequestDTO;
import sample.multimodule.dto.request.UserRequestDTO;
import sample.multimodule.exceptions.AppException;

import java.util.List;

public interface AssetService {

    public List<AssetRequestDTO> listAssets() throws AppException;

    public AssetRequestDTO getAsset(Long assetId) throws AppException;

    public List<AssetRequestDTO> getUserAssets(Long userid) throws AppException;

    AssetRequestDTO saveAsset(AssetRequestDTO assetRequestDTO) throws AppException;

}
