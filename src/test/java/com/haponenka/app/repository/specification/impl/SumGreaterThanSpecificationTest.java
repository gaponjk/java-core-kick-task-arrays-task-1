package com.haponenka.app.repository.specification.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.haponenka.app.entity.CustomArray;
import com.haponenka.app.service.impl.ArrayServiceImpl;
import org.junit.jupiter.api.Test;

class SumGreaterThanSpecificationTest {

  private final SumGreaterThanSpecification specification =
      new SumGreaterThanSpecification(10, new ArrayServiceImpl());

  @Test
  void specifyShouldReturnTrueWhenSumIsGreaterThanThreshold() {
    // when
    boolean actual = specification.specify(new CustomArray(5, 6));

    // then
    assertTrue(actual);
  }

  @Test
  void specifyShouldReturnFalseWhenSumIsEqualToThreshold() {
    // when
    boolean actual = specification.specify(new CustomArray(5, 5));

    // then
    assertFalse(actual);
  }

  @Test
  void specifyShouldReturnTrueWhenSumExceedsIntRange() {
    // when
    boolean actual = specification.specify(new CustomArray(Integer.MAX_VALUE, Integer.MAX_VALUE));

    // then
    assertTrue(actual);
  }

  @Test
  void specifyShouldReturnFalseWhenArrayIsEmpty() {
    // when
    boolean actual = specification.specify(new CustomArray());

    // then
    assertFalse(actual);
  }

  @Test
  void specifyShouldReturnFalseWhenArrayIsNull() {
    // when
    boolean actual = specification.specify(null);

    // then
    assertFalse(actual);
  }
}
