package com.haponenka.app.reader.impl;

import com.haponenka.app.exception.CustomFileException;
import com.haponenka.app.reader.CustomFileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomFileReaderImpl implements CustomFileReader {

  private static final Logger LOGGER = LogManager.getLogger(CustomFileReaderImpl.class);

  @Override
  public List<String> readLines(String filePath) throws CustomFileException {
    if (filePath == null || filePath.isBlank()) {
      throw new CustomFileException("File path is null or blank");
    }
    try {
      Path path = Path.of(filePath);
      List<String> lines = Files.readAllLines(path);
      LOGGER.info("File successfully read: {}", filePath);
      return lines;
    } catch (InvalidPathException e) {
      throw new CustomFileException("Invalid file path: " + filePath, e);
    } catch (NoSuchFileException e) {
      throw new CustomFileException("File not found: " + filePath, e);
    } catch (IOException e) {
      throw new CustomFileException("Error reading file: " + filePath, e);
    }
  }
}
