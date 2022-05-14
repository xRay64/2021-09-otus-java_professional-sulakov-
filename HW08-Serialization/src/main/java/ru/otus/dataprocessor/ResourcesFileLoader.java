package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.dto.MeasurementDTO;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        List<MeasurementDTO> measurements;
        try (InputStream jsonStream = this.getClass().getClassLoader().getResourceAsStream(fileName)) {
            measurements = mapper.readValue(jsonStream, new TypeReference<>() {});
        } catch (IOException e) {
            throw new FileProcessException(e);
        }

        if (measurements == null) {
            throw new FileProcessException("Cant get measurements from file");
        }
        return measurements.stream().map(MeasurementDTO::getMeasurementObject).toList();
    }
}
