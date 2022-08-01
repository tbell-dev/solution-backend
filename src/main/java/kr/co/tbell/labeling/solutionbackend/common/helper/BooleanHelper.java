package kr.co.tbell.labeling.solutionbackend.common.helper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanHelper implements AttributeConverter<Boolean, String> {

    /**
     * Boolean 값을 Y 또는 N 으로 컨버트
     *
     * @param attribute boolean 값
     * @return String true 인 경우 Y 또는 false 인 경우 N
     * */
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "Y" : "N";
    }


    /**
     * Y 또는 N 값을 boolean 으로 컨버트
     *
     * @param dbData boolean 값
     * */
    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equals(dbData);
    }
}
