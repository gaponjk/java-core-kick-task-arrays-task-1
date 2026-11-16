package com.haponenka.app.reader;

import com.haponenka.app.exception.CustomFileException;
import java.util.List;

public interface CustomFileReader {
  List<String> readLines(String filePath) throws CustomFileException;
}
