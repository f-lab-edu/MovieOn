package kr.flab.movieon.supports;

import java.math.BigDecimal;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public final class BigDecimalAttributeConverter implements AttributeConverter<BigDecimal, Long> {

    @Override
    public Long convertToDatabaseColumn(BigDecimal attribute) {
        return attribute.longValue();
    }

    @Override
    public BigDecimal convertToEntityAttribute(Long dbData) {
        return BigDecimal.valueOf(dbData);
    }
}
