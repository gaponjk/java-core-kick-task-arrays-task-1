package com.haponenka.app.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.haponenka.app.comparator.ArrayLengthComparator;
import com.haponenka.app.entity.ArrayStatistics;
import com.haponenka.app.entity.CustomArray;
import com.haponenka.app.repository.specification.impl.IdSpecification;
import com.haponenka.app.warehouse.ArrayWarehouse;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class CustomArrayRepositoryImplTest {

  private CustomArrayRepositoryImpl repository;
  private CustomArray shortArray;
  private CustomArray longArray;

  @BeforeEach
  void setUp() {
    repository = CustomArrayRepositoryImpl.getInstance();
    shortArray = new CustomArray("short", 1);
    longArray = new CustomArray("long", 1, 2, 3);
  }

  @AfterEach
  void tearDown() {
    // The repository is a singleton, so every test has to leave it empty.
    List<CustomArray> stored = List.copyOf(repository.getAll());
    stored.forEach(repository::remove);
  }

  @Test
  void getInstanceShouldReturnSameInstanceWhenCalledTwice() {
    // when
    CustomArrayRepositoryImpl actual = CustomArrayRepositoryImpl.getInstance();

    // then
    assertSame(repository, actual);
  }

  @Test
  void addShouldStoreArrayWhenArrayIsNotNull() {
    // when
    repository.add(shortArray);

    // then
    assertEquals(List.of(shortArray), repository.getAll());
  }

  @Test
  void addShouldIgnoreArrayWhenArrayIsNull() {
    // when
    repository.add(null);

    // then
    assertTrue(repository.getAll().isEmpty());
  }

  @Test
  void removeShouldReturnTrueWhenArrayIsStored() {
    // given
    repository.add(shortArray);

    // when
    boolean removed = repository.remove(shortArray);

    // then
    assertTrue(removed);
    assertTrue(repository.getAll().isEmpty());
  }

  @Test
  void removeShouldReturnFalseWhenArrayIsNotStored() {
    // when
    boolean removed = repository.remove(shortArray);

    // then
    assertFalse(removed);
  }

  @Test
  void removeShouldDeleteStatisticsFromWarehouseWhenArrayIsStored() {
    // given
    ArrayWarehouse warehouse = ArrayWarehouse.getInstance();
    repository.add(shortArray);
    warehouse.put(shortArray.getId(), new ArrayStatistics(1, 1, 1, 1.0));

    // when
    repository.remove(shortArray);

    // then
    assertTrue(warehouse.get(shortArray.getId()).isEmpty());
  }

  @Test
  void queryShouldReturnMatchingArraysWhenSpecificationMatches() {
    // given
    repository.add(shortArray);
    repository.add(longArray);

    // when
    List<CustomArray> actual = repository.query(new IdSpecification("long"));

    // then
    assertEquals(List.of(longArray), actual);
  }

  @Test
  void queryShouldReturnEmptyListWhenNothingMatches() {
    // given
    repository.add(shortArray);

    // when
    List<CustomArray> actual = repository.query(new IdSpecification("unknown"));

    // then
    assertTrue(actual.isEmpty());
  }

  @Test
  void queryShouldThrowExceptionWhenSpecificationIsNull() {
    // when
    Executable executable = () -> repository.query(null);

    // then
    assertThrows(NullPointerException.class, executable);
  }

  @Test
  void sortShouldReturnSortedCopyWhenComparatorIsGiven() {
    // given
    repository.add(longArray);
    repository.add(shortArray);

    // when
    List<CustomArray> actual = repository.sort(new ArrayLengthComparator());

    // then
    assertEquals(List.of(shortArray, longArray), actual);
    assertEquals(List.of(longArray, shortArray), repository.getAll());
  }

  @Test
  void getAllShouldReturnUnmodifiableListWhenCalled() {
    // given
    List<CustomArray> all = repository.getAll();

    // when
    Executable executable = () -> all.add(shortArray);

    // then
    assertThrows(UnsupportedOperationException.class, executable);
  }
}
