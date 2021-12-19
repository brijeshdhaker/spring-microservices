/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author brijeshdhaker
 */
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime x) {
        return x == null ? null : Timestamp.valueOf(x);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp y) {
        return y == null ? null : y.toLocalDateTime();
    }

}
