package edu.illinois.cs.cs124.ay2021.mp.models;

import java.util.ArrayList;
import java.util.List;

//MP2: Part 2: Create a Preference Model
public class Preference { public Preference() { }
  private String id;
  public String getId() {
    return id; }
  private List<String> restaurantList;
  private List<String> restaurantIDs = new ArrayList<String>();
  public List<String> getRestaurantIDs() {
    return restaurantIDs;
  }
}
