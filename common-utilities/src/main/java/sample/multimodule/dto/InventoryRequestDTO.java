/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.dto;

import lombok.Data;

import java.util.UUID;

/**
 *
 * @author brijeshdhaker
 */
@Data
public class InventoryRequestDTO {
    
    private Integer userId;
    private Integer productId;
    private UUID orderId;
    
}
