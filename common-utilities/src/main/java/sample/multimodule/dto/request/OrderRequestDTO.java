/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author brijeshdhaker
 */
@Data
public class OrderRequestDTO implements Serializable {
    private Integer userId;
    private Integer productId;
    private UUID orderId;
}
