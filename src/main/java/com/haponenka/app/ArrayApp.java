package com.haponenka.app;

import com.haponenka.app.comparator.ArrayLengthComparator;
import com.haponenka.app.entity.ArrayStatistics;
import com.haponenka.app.entity.CustomArray;
import com.haponenka.app.event.ArrayEvent;
import com.haponenka.app.exception.CustomArrayException;
import com.haponenka.app.factory.CustomArrayFactory;
import com.haponenka.app.factory.impl.CustomArrayFactoryImpl;
import com.haponenka.app.observer.ArrayObserver;
import com.haponenka.app.observer.impl.ArrayObserverImpl;
import com.haponenka.app.parser.CustomParser;
import com.haponenka.app.parser.impl.CustomParserImpl;
import com.haponenka.app.reader.CustomFileReader;
import com.haponenka.app.reader.impl.CustomFileReaderImpl;
import com.haponenka.app.repository.CustomArrayRepository;
import com.haponenka.app.repository.impl.CustomArrayRepositoryImpl;
import com.haponenka.app.repository.specification.ArraySpecification;
import com.haponenka.app.repository.specification.impl.SumGreaterThanSpecification;
import com.haponenka.app.service.ArrayService;
import com.haponenka.app.service.ArraySortService;
import com.haponenka.app.service.impl.ArrayServiceImpl;
import com.haponenka.app.service.impl.ArraySortServiceImpl;
import com.haponenka.app.validator.impl.ArrayLineValidatorImpl;
import com.haponenka.app.warehouse.ArrayWarehouse;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArrayApp {

  private static final Logger LOGGER = LogManager.getLogger(ArrayApp.class);
  private static final String DATA_FILE_PATH = "data/arrays.txt";
  private static final long SUM_THRESHOLD = 10;
  private static final int DEMO_VALUE = 100;

  public static void main(String[] args) {
    LOGGER.info("Application started");

    ArrayService arrayService = new ArrayServiceImpl();
    ArrayWarehouse warehouse = ArrayWarehouse.getInstance();
    CustomArrayRepository repository = CustomArrayRepositoryImpl.getInstance();
    ArrayObserver observer = new ArrayObserverImpl(arrayService, warehouse);

    List<String> lines;
    try {
      CustomFileReader fileReader = new CustomFileReaderImpl();
      lines = fileReader.readLines(DATA_FILE_PATH);
    } catch (CustomArrayException e) {
      LOGGER.error("Failed to read input data file: {}", DATA_FILE_PATH, e);
      return;
    }

    loadArrays(lines, repository, observer);
    logStatistics(repository, warehouse);
    sortArrays(repository);
    runQueries(repository, arrayService);
    changeFirstElement(repository, warehouse);

    LOGGER.info("Application completed");
  }

  private static void loadArrays(
      List<String> lines, CustomArrayRepository repository, ArrayObserver observer) {
    CustomParser parser = new CustomParserImpl(new ArrayLineValidatorImpl());
    CustomArrayFactory factory = new CustomArrayFactoryImpl();
    for (String line : lines) {
      try {
        int[] elements = parser.parseLineToIntArray(line);
        CustomArray customArray = factory.createCustomArray(elements);
        customArray.attach(observer);
        // Fill the warehouse with the initial statistics; later changes arrive via the observer.
        observer.update(new ArrayEvent(customArray));
        repository.add(customArray);
      } catch (CustomArrayException e) {
        LOGGER.warn("Skipping line: {}", e.getMessage());
      }
    }
  }

  private static void logStatistics(CustomArrayRepository repository, ArrayWarehouse warehouse) {
    for (CustomArray customArray : repository.getAll()) {
      Optional<ArrayStatistics> statistics = warehouse.get(customArray.getId());
      if (statistics.isPresent()) {
        LOGGER.info("{} -> {}", customArray, statistics.get());
      } else {
        LOGGER.info("{} -> no statistics", customArray);
      }
    }
  }

  private static void sortArrays(CustomArrayRepository repository) {
    ArraySortService sortService = new ArraySortServiceImpl();
    for (CustomArray customArray : repository.getAll()) {
      try {
        sortService.bubbleSort(customArray);
        LOGGER.info("After bubble sort: {}", customArray);
      } catch (CustomArrayException e) {
        LOGGER.error("Failed to sort array: {}", customArray, e);
      }
    }
  }

  private static void runQueries(CustomArrayRepository repository, ArrayService arrayService) {
    ArraySpecification specification = new SumGreaterThanSpecification(SUM_THRESHOLD, arrayService);
    List<CustomArray> found = repository.query(specification);
    LOGGER.info("Arrays with sum greater than {}: {}", SUM_THRESHOLD, found);

    List<CustomArray> sortedByLength = repository.sort(new ArrayLengthComparator());
    LOGGER.info("Arrays sorted by length: {}", sortedByLength);
  }

  private static void changeFirstElement(
      CustomArrayRepository repository, ArrayWarehouse warehouse) {
    List<CustomArray> arrays = repository.getAll();
    if (arrays.isEmpty() || arrays.getFirst().getLength() == 0) {
      LOGGER.warn("No array available to demonstrate the observer");
      return;
    }
    CustomArray customArray = arrays.getFirst();
    // Changing an element makes the observer recalculate the statistics in the warehouse.
    customArray.setElement(0, DEMO_VALUE);
    Optional<ArrayStatistics> statistics = warehouse.get(customArray.getId());
    LOGGER.info("After change {} -> {}", customArray, statistics.orElse(null));
  }
}
