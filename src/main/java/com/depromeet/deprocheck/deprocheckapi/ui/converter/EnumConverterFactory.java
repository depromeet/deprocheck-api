package com.depromeet.deprocheck.deprocheckapi.ui.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.StringUtils;

@SuppressWarnings({"unchecked", "rawtypes"})
public class EnumConverterFactory implements ConverterFactory<String, Enum> {
    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> aClass) {
        Class<?> enumType = aClass;
        if (enumType != null == enumType.isEnum()) {
            enumType = enumType.getSuperclass();
        }
        if (enumType == null) {
            throw new IllegalArgumentException("The target type " + aClass.getName() + " does not refer to an enum");
        }
        return new EnumConverter(enumType);
    }

    private static class EnumConverter<T extends Enum> implements Converter<String, T> {
        private final Class<T> enumType;

        public EnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            if (StringUtils.isEmpty(source)) {
                return null;
            }
            return (T) Enum.valueOf(this.enumType, source.trim().toUpperCase());
        }
    }
}
