package com.haponenka.app.parser.impl;

import com.haponenka.app.exception.CustomArrayException;
import com.haponenka.app.parser.CustomParser;
import com.haponenka.app.validator.ArrayLineValidator;
import java.util.regex.Pattern;

public class CustomParserImpl implements CustomParser {

  private static final Pattern DELIMITER_PATTERN = Pattern.compile("[,;\\s]+");

  private final ArrayLineValidator validator;

  public CustomParserImpl(ArrayLineValidator validator) {
    this.validator = validator;
  }

  @Override
  public int[] parseLineToIntArray(String line) throws CustomArrayException {
    if (!this.validator.isValid(line)) {
      throw new CustomArrayException("Invalid line content for parsing: " + line);
    }
    String strippedLine = line.strip();
    String[] stringNumbers = DELIMITER_PATTERN.split(strippedLine);
    int length = stringNumbers.length;
    int[] numbers = new int[length];
    try {
      for (int i = 0; i < length; i++) {
        numbers[i] = Integer.parseInt(stringNumbers[i]);
      }
    } catch (NumberFormatException e) {
      throw new CustomArrayException("Number is out of int range in line: " + line, e);
    }
    return numbers;
  }
}
