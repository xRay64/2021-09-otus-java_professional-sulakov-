package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        Map<String, Double> groupedData = new TreeMap<>();
        for (Measurement currentData : data) {
            String currentDataName = currentData.getName();
            groupedData.merge(currentDataName, currentData.getValue(), Double::sum);
        }
        return groupedData;
    }
}
