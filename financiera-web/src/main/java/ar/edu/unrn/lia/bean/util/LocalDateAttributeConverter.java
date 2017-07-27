package ar.edu.unrn.lia.bean.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;


public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate locDate) {
        return ((locDate == null) ? null : Date.valueOf(locDate));
    }

    public LocalDate convertToEntity(java.util.Date javaDate) {
        return (javaDate == null ? null : javaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public java.util.Date convertTo(LocalDate localDate) {
        return (localDate == null ? null : java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
        return (sqlDate == null ? null : sqlDate.toLocalDate());
    }
}