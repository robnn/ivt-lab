package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class GT4500Test {

  private GT4500 ship;

  private TorpedoStore first;
  private TorpedoStore second;
  @Before
  public void init(){
    first = mock(TorpedoStore.class);
    second = mock(TorpedoStore.class);
    this.ship = new GT4500(first,second);
  }

  @Test
  public void fireTorpedos_Single_Success(){
    // Arrange
    when(first.isEmpty()).thenReturn(false);
    when(second.isEmpty()).thenReturn(false);
    when(first.fire(1)).thenReturn(true);
    when(second.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    verify(first,times(1)).fire(1);
    verify(second,times(0)).fire(1);
  }

  @Test
  public void fireTorpedos_All_Success(){
    // Arrange
    when(first.isEmpty()).thenReturn(false);
    when(second.isEmpty()).thenReturn(false);
    when(first.fire(1)).thenReturn(true);
    when(second.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    verify(first,times(1)).fire(1);
    verify(second,times(1)).fire(1);

  }
  //Elsüti az első torpedót, majd a másikat,SINGLE módban majd megnézi, hogy mindkettő
  //elsült e egyszer.Mindkettő store egyet tartalmaz.
  @Test
  public void fireTwoTorpedosInSingleMode(){
    when(first.isEmpty()).thenReturn(false);
    when(second.isEmpty()).thenReturn(false);
    when(first.fire(1)).thenReturn(true);
    when(second.fire(1)).thenReturn(true);

    ship.fireTorpedos(FiringMode.SINGLE);
    ship.fireTorpedos(FiringMode.SINGLE);

    verify(first,times(1)).fire(1);
    verify(second,times(1)).fire(1);
  }

  //Elsüti 2x SINgLE módban, de csak az egyik store-ban van 2 töltény, ellenőrizzük,
  //hogy 2x hívodott e.
  @Test
  public void fireTwoTorpedosInSingleButOnlyOneStoreHasTorpedos(){
    when(first.isEmpty()).thenReturn(false);
    when(second.isEmpty()).thenReturn(true);
    when(first.fire(1)).thenReturn(true);
    when(second.fire(1)).thenReturn(false);

    ship.fireTorpedos(FiringMode.SINGLE);
    ship.fireTorpedos(FiringMode.SINGLE);

    verify(first,times(2)).fire(1);
    verify(second,times(0)).fire(1);
  }
  //Minden store üres és ALL módba tüzel, elvárt kimenet hogy nem sikerült hívjon 1x se
  @Test
  public void fireAllFailure(){
    when(first.isEmpty()).thenReturn(true);
    when(second.isEmpty()).thenReturn(true);
    when(first.fire(1)).thenReturn(false);
    when(second.fire(1)).thenReturn(false);

    ship.fireTorpedos(FiringMode.ALL);

    verify(first,times(0)).fire(1);
    verify(second,times(0)).fire(1);
  }
  //Minden store üres és singleben tüzel, elvárt, hogy nem hívott egyet se
  @Test
  public void fireSingleFailure(){
    when(first.isEmpty()).thenReturn(true);
    when(second.isEmpty()).thenReturn(true);
    when(first.fire(1)).thenReturn(false);
    when(second.fire(1)).thenReturn(false);

    ship.fireTorpedos(FiringMode.SINGLE);

    verify(first,times(0)).fire(1);
    verify(second,times(0)).fire(1);
  }
   //Üres secondary de ALL módban egyszer hívodott a primary és 0x a secondary
  @Test
  public void fireOneInAll(){
    when(first.isEmpty()).thenReturn(false);
    when(second.isEmpty()).thenReturn(true);
    when(first.fire(1)).thenReturn(true);
    when(second.fire(1)).thenReturn(false);

    ship.fireTorpedos(FiringMode.ALL);

    verify(first,times(1)).fire(1);
    verify(second,times(0)).fire(1);
  }
}
