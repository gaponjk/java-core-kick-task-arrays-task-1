package com.haponenka.app.repository.impl;

import com.haponenka.app.entity.CustomArray;
import com.haponenka.app.repository.CustomArrayRepository;
import com.haponenka.app.repository.specification.ArraySpecification;
import com.haponenka.app.warehouse.ArrayWarehouse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomArrayRepositoryImpl implements CustomArrayRepository {
  private static final Logger LOGGER = LogManager.getLogger(CustomArrayRepositoryImpl.class);
  private static CustomArrayRepositoryImpl instance;
  private final List<CustomArray> storage;

  private CustomArrayRepositoryImpl() {
    this.storage = new ArrayList<>();
  }

  // Non-thread-safe singleton, as required by the task.
  public static CustomArrayRepositoryImpl getInstance() {
    if (instance == null) {
      instance = new CustomArrayRepositoryImpl();
    }
    return instance;
  }

  @Override
  public void add(CustomArray customArray) {
    if (customArray != null) {
      this.storage.add(customArray);
      LOGGER.info("Added array to repository: id={}", customArray.getId());
    }
  }

  @Override
  public boolean remove(CustomArray customArray) {
    boolean removed = this.storage.remove(customArray);
    if (removed) {
      // Statistics of an array that is no longer stored must not stay in the warehouse.
      ArrayWarehouse.getInstance().remove(customArray.getId());
      LOGGER.info("Removed array from repository: id={}", customArray.getId());
    }
    return removed;
  }

  @Override
  public List<CustomArray> query(ArraySpecification specification) {
    Objects.requireNonNull(specification, "specification must not be null");
    List<CustomArray> result = this.storage.stream().filter(specification::specify).toList();
    LOGGER.debug("Specification query found {} items", result.size());
    return result;
  }

  @Override
  public List<CustomArray> sort(Comparator<CustomArray> comparator) {
    Objects.requireNonNull(comparator, "comparator must not be null");
    return this.storage.stream().sorted(comparator).toList();
  }

  @Override
  public List<CustomArray> getAll() {
    return Collections.unmodifiableList(this.storage);
  }
}
