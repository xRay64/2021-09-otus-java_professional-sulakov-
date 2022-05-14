package ru.otus.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.otus.model.Measurement;

public class MeasurementDTO {
    private final Measurement measurementObject;

    @JsonCreator
    public MeasurementDTO(@JsonProperty("name") String name, @JsonProperty("value") Double value) {
        this.measurementObject = new Measurement(name, value);
    }

    public Measurement getMeasurementObject() {
        return this.measurementObject;
    }
}
