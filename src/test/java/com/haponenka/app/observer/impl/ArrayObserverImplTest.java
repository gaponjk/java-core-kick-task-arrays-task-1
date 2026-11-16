package com.haponenka.app.observer.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.haponenka.app.entity.ArrayStatistics;
import com.haponenka.app.entity.CustomArray;
import com.haponenka.app.event.ArrayEvent;
import com.haponenka.app.service.impl.ArrayServiceImpl;
import com.haponenka.app.warehouse.ArrayWarehouse;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayObserverImplTest {

  private ArrayWarehouse warehouse;
  private ArrayObserverImpl observer;
  private CustomArray array;

  @BeforeEach
  void setUp() {
    warehouse = ArrayWarehouse.getInstance();
    observer = new ArrayObserverImpl(new ArrayServiceImpl(), warehouse);
    array = new CustomArray(10, 20, 30);
  }

  @AfterEach
  void tearDown() {
    warehouse.remove(array.getId());
  }

  @Test
  void updateShouldSaveStatisticsWhenArrayIsNotEmpty() {
    // given
    ArrayStatistics expected = new ArrayStatistics(10, 30, 60, 20.0);

    // when
    observer.update(new ArrayEvent(array));

    // then
    assertEquals(Optional.of(expected), warehouse.get(array.getId()));
  }

  @Test
  void updateShouldRecalculateStatisticsWhenElementIsChanged() {
    // given
    array.attach(observer);
    ArrayStatistics expected = new ArrayStatistics(20, 100, 150, 50.0);

    // when
    array.setElement(0, 100);

    // then
    assertEquals(Optional.of(expected), warehouse.get(array.getId()));
  }

  @Test
  void updateShouldRemoveStatisticsWhenArrayBecomesEmpty() {
    // given
    array.attach(observer);
    observer.update(new ArrayEvent(array));

    // when
    array.setElements(new int[0]);

    // then
    assertTrue(warehouse.get(array.getId()).isEmpty());
  }

  @Test
  void updateShouldDoNothingWhenEventIsNull() {
    // when
    observer.update(null);

    // then
    assertTrue(warehouse.get(array.getId()).isEmpty());
  }
}
