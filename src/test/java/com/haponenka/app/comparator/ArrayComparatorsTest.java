package com.haponenka.app.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.haponenka.app.entity.CustomArray;
import org.junit.jupiter.api.Test;

class ArrayComparatorsTest {

  @Test
  void idComparatorShouldOrderLexicographicallyWhenIdsAreDifferent() {
    // given
    CustomArray first = new CustomArray("a", 1);
    CustomArray second = new CustomArray("b", 1);

    // when
    int actual = new ArrayIdComparator().compare(first, second);

    // then
    assertTrue(actual < 0);
  }

  @Test
  void lengthComparatorShouldPutShorterArrayFirstWhenLengthsAreDifferent() {
    // given
    CustomArray shorter = new CustomArray(1);
    CustomArray longer = new CustomArray(1, 2);

    // when
    int actual = new ArrayLengthComparator().compare(shorter, longer);

    // then
    assertTrue(actual < 0);
  }

  @Test
  void firstElementComparatorShouldCompareFirstElementsWhenArraysAreNotEmpty() {
    // given
    CustomArray smaller = new CustomArray(-5, 100);
    CustomArray bigger = new CustomArray(3, 0);

    // when
    int actual = new ArrayFirstElementComparator().compare(smaller, bigger);

    // then
    assertTrue(actual < 0);
  }

  @Test
  void firstElementComparatorShouldTreatEmptyArrayAsZeroWhenArrayIsEmpty() {
    // given
    CustomArray empty = new CustomArray();
    CustomArray zeroFirst = new CustomArray(0, 7);

    // when
    int actual = new ArrayFirstElementComparator().compare(empty, zeroFirst);

    // then
    assertEquals(0, actual);
  }
}
