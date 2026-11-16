package com.haponenka.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.haponenka.app.entity.CustomArray;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayServiceImplTest {

  private ArrayServiceImpl service;

  @BeforeEach
  void setUp() {
    service = new ArrayServiceImpl();
  }

  @Test
  void findMinShouldReturnMinimumValueWhenArrayIsNotEmpty() {
    // given
    CustomArray array = new CustomArray(5, -2, 10, 0);

    // when
    OptionalInt actual = service.findMin(array);

    // then
    assertEquals(OptionalInt.of(-2), actual);
  }

  @Test
  void findMinShouldReturnEmptyWhenArrayIsEmpty() {
    // given
    CustomArray array = new CustomArray();

    // when
    OptionalInt actual = service.findMin(array);

    // then
    assertTrue(actual.isEmpty());
  }

  @Test
  void findMinShouldReturnEmptyWhenArrayIsNull() {
    // when
    OptionalInt actual = service.findMin(null);

    // then
    assertTrue(actual.isEmpty());
  }

  @Test
  void findMaxShouldReturnMaximumValueWhenArrayIsNotEmpty() {
    // given
    CustomArray array = new CustomArray(5, -2, 10, 0);

    // when
    OptionalInt actual = service.findMax(array);

    // then
    assertEquals(OptionalInt.of(10), actual);
  }

  @Test
  void findMaxShouldReturnEmptyWhenArrayIsEmpty() {
    // given
    CustomArray array = new CustomArray();

    // when
    OptionalInt actual = service.findMax(array);

    // then
    assertTrue(actual.isEmpty());
  }

  @Test
  void calculateSumShouldReturnCorrectSumWhenArrayIsNotEmpty() {
    // given
    CustomArray array = new CustomArray(1, 2, 3, 4);

    // when
    OptionalLong actual = service.calculateSum(array);

    // then
    assertEquals(OptionalLong.of(10), actual);
  }

  @Test
  void calculateSumShouldNotOverflowWhenSumExceedsIntRange() {
    // given
    CustomArray array = new CustomArray(Integer.MAX_VALUE, Integer.MAX_VALUE);
    long expected = 2L * Integer.MAX_VALUE;

    // when
    OptionalLong actual = service.calculateSum(array);

    // then
    assertEquals(OptionalLong.of(expected), actual);
  }

  @Test
  void calculateSumShouldReturnEmptyWhenArrayIsEmpty() {
    // given
    CustomArray array = new CustomArray();

    // when
    OptionalLong actual = service.calculateSum(array);

    // then
    assertTrue(actual.isEmpty());
  }

  @Test
  void calculateAverageShouldReturnCorrectAverageWhenArrayIsNotEmpty() {
    // given
    CustomArray array = new CustomArray(1, 2, 3, 4);

    // when
    OptionalDouble actual = service.calculateAverage(array);

    // then
    assertTrue(actual.isPresent());
    assertEquals(2.5, actual.getAsDouble(), 0.0001);
  }

  @Test
  void calculateAverageShouldNotOverflowWhenSumExceedsIntRange() {
    // given
    CustomArray array = new CustomArray(Integer.MAX_VALUE, Integer.MAX_VALUE);

    // when
    OptionalDouble actual = service.calculateAverage(array);

    // then
    assertTrue(actual.isPresent());
    assertEquals(Integer.MAX_VALUE, actual.getAsDouble(), 0.0001);
  }

  @Test
  void calculateAverageShouldReturnEmptyWhenArrayIsEmpty() {
    // given
    CustomArray array = new CustomArray();

    // when
    OptionalDouble actual = service.calculateAverage(array);

    // then
    assertTrue(actual.isEmpty());
  }
}
