package com.haponenka.app.repository.specification.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.haponenka.app.entity.CustomArray;
import org.junit.jupiter.api.Test;

class IdSpecificationTest {

  private final IdSpecification specification = new IdSpecification("42");

  @Test
  void specifyShouldReturnTrueWhenIdMatches() {
    // when
    boolean actual = specification.specify(new CustomArray("42", 1, 2));

    // then
    assertTrue(actual);
  }

  @Test
  void specifyShouldReturnFalseWhenIdIsDifferent() {
    // when
    boolean actual = specification.specify(new CustomArray("43", 1, 2));

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
