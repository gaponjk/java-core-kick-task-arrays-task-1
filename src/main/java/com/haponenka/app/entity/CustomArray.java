package com.haponenka.app.entity;

import com.haponenka.app.event.ArrayEvent;
import com.haponenka.app.observer.ArrayObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CustomArray {
  private final String id;
  private int[] elements;
  private final List<ArrayObserver> observers;

  public CustomArray(String id, int... elements) {
    this.id = Objects.requireNonNull(id, "id must not be null");
    this.elements = copyOrEmpty(elements);
    this.observers = new ArrayList<>();
  }

  public CustomArray(int... elements) {
    this(UUID.randomUUID().toString(), elements);
  }

  public String getId() {
    return this.id;
  }

  public int[] getElements() {
    return Arrays.copyOf(this.elements, this.elements.length);
  }

  public void setElements(int[] elements) {
    this.elements = copyOrEmpty(elements);
    notifyObservers();
  }

  public int getElement(int index) {
    return this.elements[index];
  }

  public void setElement(int index, int value) {
    this.elements[index] = value;
    notifyObservers();
  }

  public int getLength() {
    return this.elements.length;
  }

  public void attach(ArrayObserver observer) {
    if (observer != null) {
      this.observers.add(observer);
    }
  }

  public void detach(ArrayObserver observer) {
    this.observers.remove(observer);
  }

  private void notifyObservers() {
    ArrayEvent event = new ArrayEvent(this);
    for (ArrayObserver observer : this.observers) {
      observer.update(event);
    }
  }

  private static int[] copyOrEmpty(int[] source) {
    return (source != null) ? Arrays.copyOf(source, source.length) : new int[0];
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomArray that = (CustomArray) o;
    return this.id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return this.id.hashCode();
  }

  @Override
  public String toString() {
    return "CustomArray{id=" + this.id + ", elements=" + Arrays.toString(this.elements) + '}';
  }
}
