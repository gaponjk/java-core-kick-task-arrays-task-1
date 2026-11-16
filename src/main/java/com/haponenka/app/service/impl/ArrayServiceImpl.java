package com.haponenka.app.service.impl;

import com.haponenka.app.entity.CustomArray;
import com.haponenka.app.service.ArrayService;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArrayServiceImpl implements ArrayService {

  private static final Logger LOGGER = LogManager.getLogger(ArrayServiceImpl.class);

  @Override
  public OptionalInt findMin(CustomArray customArray) {
    if (isNullOrEmpty(customArray)) {
      return OptionalInt.empty();
    }
    int[] array = customArray.getElements();
    int min = array[0];
    for (int i = 1; i < array.length; i++) {
      if (array[i] < min) {
        min = array[i];
      }
    }
    LOGGER.debug("Min value found: {}", min);
    return OptionalInt.of(min);
  }

  @Override
  public OptionalInt findMax(CustomArray customArray) {
    if (isNullOrEmpty(customArray)) {
      return OptionalInt.empty();
    }
    int[] array = customArray.getElements();
    int max = array[0];
    for (int i = 1; i < array.length; i++) {
      if (array[i] > max) {
        max = array[i];
      }
    }
    LOGGER.debug("Max value found: {}", max);
    return OptionalInt.of(max);
  }

  @Override
  public OptionalLong calculateSum(CustomArray customArray) {
    if (isNullOrEmpty(customArray)) {
      return OptionalLong.empty();
    }
    long sum = sum(customArray.getElements());
    LOGGER.debug("Sum calculated: {}", sum);
    return OptionalLong.of(sum);
  }

  @Override
  public OptionalDouble calculateAverage(CustomArray customArray) {
    if (isNullOrEmpty(customArray)) {
      return OptionalDouble.empty();
    }
    int[] array = customArray.getElements();
    double average = (double) sum(array) / array.length;
    LOGGER.debug("Average calculated: {}", average);
    return OptionalDouble.of(average);
  }

  private static boolean isNullOrEmpty(CustomArray customArray) {
    return customArray == null || customArray.getLength() == 0;
  }

  // long is used so the sum of int values cannot overflow.
  private static long sum(int[] array) {
    long sum = 0;
    for (int value : array) {
      sum += value;
    }
    return sum;
  }
}
