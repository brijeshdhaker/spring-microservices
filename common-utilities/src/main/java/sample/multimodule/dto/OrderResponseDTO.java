/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.dto;

import lombok.Data;
import sample.multimodule.enums.avro.OrderStatus;

import java.util.UUID;

/**
 *
 * @author brijeshdhaker
 */
@Data
public class OrderResponseDTO {
    private UUID orderId;
    private Integer userId;
    private Integer productId;
    private Double amount;
    private OrderStatus status;
}
