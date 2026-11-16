package com.haponenka.app.entity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.haponenka.app.event.ArrayEvent;
import com.haponenka.app.observer.ArrayObserver;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class CustomArrayTest {

  @Test
  void constructorShouldCopyElementsWhenSourceArrayIsChangedLater() {
    // given
    int[] source = {1, 2, 3};
    CustomArray array = new CustomArray(source);

    // when
    source[0] = 99;

    // then
    assertArrayEquals(new int[] {1, 2, 3}, array.getElements());
  }

  @Test
  void constructorShouldCreateEmptyArrayWhenElementsAreNull() {
    // when
    CustomArray array = new CustomArray("id", (int[]) null);

    // then
    assertEquals(0, array.getLength());
  }

  @Test
  void constructorShouldThrowExceptionWhenIdIsNull() {
    // when
    Executable executable = () -> new CustomArray(null, 1, 2);

    // then
    assertThrows(NullPointerException.class, executable);
  }

  @Test
  void getElementsShouldReturnCopyWhenResultIsModified() {
    // given
    CustomArray array = new CustomArray(1, 2, 3);

    // when
    array.getElements()[0] = 99;

    // then
    assertEquals(1, array.getElement(0));
  }

  @Test
  void setElementShouldNotifyObserverWhenObserverIsAttached() {
    // given
    CustomArray array = new CustomArray(1, 2, 3);
    List<ArrayEvent> events = new ArrayList<>();
    array.attach(events::add);

    // when
    array.setElement(0, 10);

    // then
    assertEquals(1, events.size());
    assertEquals(array, events.getFirst().getSource());
  }

  @Test
  void setElementsShouldNotifyObserverWhenObserverIsAttached() {
    // given
    CustomArray array = new CustomArray(1, 2, 3);
    List<ArrayEvent> events = new ArrayList<>();
    array.attach(events::add);

    // when
    array.setElements(new int[] {7, 8});

    // then
    assertEquals(1, events.size());
    assertArrayEquals(new int[] {7, 8}, array.getElements());
  }

  @Test
  void setElementShouldNotNotifyObserverWhenObserverIsDetached() {
    // given
    CustomArray array = new CustomArray(1, 2, 3);
    List<ArrayEvent> events = new ArrayList<>();
    ArrayObserver observer = events::add;
    array.attach(observer);
    array.detach(observer);

    // when
    array.setElement(0, 10);

    // then
    assertEquals(0, events.size());
  }

  @Test
  void equalsAndHashCodeShouldStayStableWhenElementsChange() {
    // given
    CustomArray array = new CustomArray("same-id", 1, 2, 3);
    CustomArray sameId = new CustomArray("same-id", 1, 2, 3);
    int hashBefore = array.hashCode();

    // when
    array.setElement(0, 100);

    // then
    assertEquals(sameId, array);
    assertEquals(hashBefore, array.hashCode());
  }

  @Test
  void equalsShouldReturnFalseWhenIdsAreDifferent() {
    // given
    CustomArray first = new CustomArray("a", 1, 2, 3);
    CustomArray second = new CustomArray("b", 1, 2, 3);

    // then
    assertNotEquals(first, second);
  }
}
