package com.haponenka.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.haponenka.app.entity.CustomArray;
import com.haponenka.app.exception.CustomArrayException;
import com.haponenka.app.service.ArraySortService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class ArraySortServiceImplTest {

  private static final int[] UNSORTED_NUMBERS = {9, 2, -5, 7, 1};
  private static final int[] EXPECTED_SORTED = {-5, 1, 2, 7, 9};

  private ArraySortService service;

  @BeforeEach
  void setUp() {
    service = new ArraySortServiceImpl();
  }

  @Test
  void bubbleSortShouldSortArrayAscending() throws CustomArrayException {
    // given
    CustomArray customArray = new CustomArray(UNSORTED_NUMBERS);

    // when
    service.bubbleSort(customArray);

    // then
    assertArrayEquals(EXPECTED_SORTED, customArray.getElements());
  }

  @Test
  void bubbleSortShouldKeepArrayEmptyWhenArrayIsEmpty() throws CustomArrayException {
    // given
    CustomArray customArray = new CustomArray();

    // when
    service.bubbleSort(customArray);

    // then
    assertArrayEquals(new int[0], customArray.getElements());
  }

  @Test
  void bubbleSortShouldThrowExceptionWhenArrayIsNull() {
    // when
    Executable executable = () -> service.bubbleSort(null);

    // then
    assertThrows(CustomArrayException.class, executable);
  }

  @Test
  void insertionSortShouldSortArrayAscending() throws CustomArrayException {
    // given
    CustomArray customArray = new CustomArray(UNSORTED_NUMBERS);

    // when
    service.insertionSort(customArray);

    // then
    assertArrayEquals(EXPECTED_SORTED, customArray.getElements());
  }

  @Test
  void insertionSortShouldKeepArrayEmptyWhenArrayIsEmpty() throws CustomArrayException {
    // given
    CustomArray customArray = new CustomArray();

    // when
    service.insertionSort(customArray);

    // then
    assertArrayEquals(new int[0], customArray.getElements());
  }

  @Test
  void insertionSortShouldThrowExceptionWhenArrayIsNull() {
    // when
    Executable executable = () -> service.insertionSort(null);

    // then
    assertThrows(CustomArrayException.class, executable);
  }
}
