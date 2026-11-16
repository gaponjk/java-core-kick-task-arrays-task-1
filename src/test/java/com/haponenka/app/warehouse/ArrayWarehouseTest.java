package com.haponenka.app.warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.haponenka.app.entity.ArrayStatistics;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayWarehouseTest {

  private ArrayWarehouse warehouse;
  private String id;

  @BeforeEach
  void setUp() {
    warehouse = ArrayWarehouse.getInstance();
    id = UUID.randomUUID().toString();
  }

  @AfterEach
  void tearDown() {
    warehouse.remove(id);
  }

  @Test
  void getInstanceShouldReturnSameInstanceWhenCalledTwice() {
    // when
    ArrayWarehouse actual = ArrayWarehouse.getInstance();

    // then
    assertSame(warehouse, actual);
  }

  @Test
  void getShouldReturnStatisticsWhenStatisticsWerePut() {
    // given
    ArrayStatistics statistics = new ArrayStatistics(1, 3, 6, 2.0);
    warehouse.put(id, statistics);

    // when
    Optional<ArrayStatistics> actual = warehouse.get(id);

    // then
    assertEquals(Optional.of(statistics), actual);
  }

  @Test
  void getShouldReturnEmptyWhenIdIsUnknown() {
    // when
    Optional<ArrayStatistics> actual = warehouse.get(id);

    // then
    assertTrue(actual.isEmpty());
  }

  @Test
  void removeShouldDeleteStatisticsWhenIdExists() {
    // given
    warehouse.put(id, new ArrayStatistics(1, 3, 6, 2.0));

    // when
    boolean removed = warehouse.remove(id);

    // then
    assertTrue(removed);
    assertTrue(warehouse.get(id).isEmpty());
  }

  @Test
  void removeShouldReturnFalseWhenIdIsUnknown() {
    // when
    boolean removed = warehouse.remove(id);

    // then
    assertFalse(removed);
  }
}
