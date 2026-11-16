package com.haponenka.app.repository.specification.impl;

import com.haponenka.app.entity.CustomArray;
import com.haponenka.app.repository.specification.ArraySpecification;
import com.haponenka.app.service.ArrayService;
import java.util.OptionalLong;

public class SumGreaterThanSpecification implements ArraySpecification {
  private final long threshold;
  private final ArrayService arrayService;

  public SumGreaterThanSpecification(long threshold, ArrayService arrayService) {
    this.threshold = threshold;
    this.arrayService = arrayService;
  }

  @Override
  public boolean specify(CustomArray customArray) {
    OptionalLong sum = this.arrayService.calculateSum(customArray);
    return sum.isPresent() && sum.getAsLong() > this.threshold;
  }
}
