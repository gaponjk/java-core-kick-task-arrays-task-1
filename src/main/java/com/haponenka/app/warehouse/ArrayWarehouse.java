package com.haponenka.app.warehouse;

import com.haponenka.app.entity.ArrayStatistics;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArrayWarehouse {
  private static final Logger LOGGER = LogManager.getLogger(ArrayWarehouse.class);
  private static ArrayWarehouse instance;
  private final Map<String, ArrayStatistics> statisticsMap;

  private ArrayWarehouse() {
    this.statisticsMap = new HashMap<>();
  }

  // Non-thread-safe singleton, as required by the task.
  public static ArrayWarehouse getInstance() {
    if (instance == null) {
      instance = new ArrayWarehouse();
    }
    return instance;
  }

  public void put(String id, ArrayStatistics statistics) {
    this.statisticsMap.put(id, statistics);
    LOGGER.debug("Saved statistics for id: {}", id);
  }

  public Optional<ArrayStatistics> get(String id) {
    return Optional.ofNullable(this.statisticsMap.get(id));
  }

  public boolean remove(String id) {
    boolean removed = this.statisticsMap.remove(id) != null;
    if (removed) {
      LOGGER.debug("Removed statistics for id: {}", id);
    }
    return removed;
  }
}
