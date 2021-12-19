/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.converters;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author brijeshdhaker
 */
public class DateTimeUtilsTest {

    public DateTimeUtilsTest() {
    }

    @org.junit.Test
    public void testConvertToLocalDateString() {
        LocalDateTime datetime = LocalDateTime.of(2021, 01, 01, 10, 10);
        String localDateStr = DateTimeUtils.convertToLocalDateString(datetime);
        assertEquals("2021-01-01", localDateStr.toString());
    }

    @org.junit.Test
    public void testConvertToLocalDate() {
    }

}
