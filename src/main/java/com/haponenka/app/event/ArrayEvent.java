package com.haponenka.app.event;

import com.haponenka.app.entity.CustomArray;
import java.io.Serial;
import java.util.EventObject;

public class ArrayEvent extends EventObject {

  @Serial private static final long serialVersionUID = 1L;

  public ArrayEvent(CustomArray source) {
    super(source);
  }

  @Override
  public CustomArray getSource() {
    return (CustomArray) super.getSource();
  }
}
