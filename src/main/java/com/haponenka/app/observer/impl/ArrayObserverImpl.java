package com.haponenka.app.observer.impl;

import com.haponenka.app.entity.ArrayStatistics;
import com.haponenka.app.entity.CustomArray;
import com.haponenka.app.event.ArrayEvent;
import com.haponenka.app.observer.ArrayObserver;
import com.haponenka.app.service.ArrayService;
import com.haponenka.app.warehouse.ArrayWarehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArrayObserverImpl implements ArrayObserver {
  private static final Logger LOGGER = LogManager.getLogger(ArrayObserverImpl.class);
  private final ArrayService arrayService;
  private final ArrayWarehouse warehouse;

  public ArrayObserverImpl(ArrayService arrayService, ArrayWarehouse warehouse) {
    this.arrayService = arrayService;
    this.warehouse = warehouse;
  }

  @Override
  public void update(ArrayEvent event) {
    if (event == null) {
      LOGGER.warn("Received null array event in observer");
      return;
    }
    CustomArray array = event.getSource();
    String id = array.getId();
    if (array.getLength() == 0) {
      // An empty array has no min/max/average, so no statistics are kept for it.
      this.warehouse.remove(id);
      LOGGER.info("Array id: {} is empty, statistics removed from warehouse", id);
      return;
    }
    int min = this.arrayService.findMin(array).orElseThrow();
    int max = this.arrayService.findMax(array).orElseThrow();
    long sum = this.arrayService.calculateSum(array).orElseThrow();
    double average = this.arrayService.calculateAverage(array).orElseThrow();

    this.warehouse.put(id, new ArrayStatistics(min, max, sum, average));
    LOGGER.info("Warehouse statistics updated for array id: {}", id);
  }
}
