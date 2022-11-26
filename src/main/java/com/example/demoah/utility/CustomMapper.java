package com.example.demoah.utility;

import com.example.demoah.configuration.ApplicationContextProvider;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomMapper {
    private static ModelMapper modelMapper;
    private CustomMapper() {
        modelMapper = ApplicationContextProvider.getApplicationContext().getBean(ModelMapper.class);
    }

    public static Object mapList(Object[] toMap, Class clazz) {
        List<Object> mapped = new ArrayList<>();
        for (Object o : toMap) {
            mapped.add(modelMapper.map(o, clazz));
        }
        return mapped;
    }

    public static Object map(Object toMap, Class clazz) {
        return modelMapper.map(toMap, clazz);
    }
}
