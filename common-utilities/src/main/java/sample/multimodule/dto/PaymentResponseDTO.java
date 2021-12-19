/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.dto;

import lombok.Data;
import sample.multimodule.enums.PaymentStatus;

import java.util.UUID;

/**
 *
 * @author brijeshdhaker
 */
@Data
public class PaymentResponseDTO {
    private Integer userId;
    private UUID orderId;
    private Double amount;
    private PaymentStatus status;
}
