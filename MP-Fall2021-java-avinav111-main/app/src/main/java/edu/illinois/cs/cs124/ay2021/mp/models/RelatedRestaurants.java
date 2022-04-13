package edu.illinois.cs.cs124.ay2021.mp.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

public class RelatedRestaurants {
  private Map<String, Map<String, Integer>> restaurantRelationships = new HashMap<>();
  private final Map<String, Restaurant> restaurantMap = new HashMap<>();
  private final List<String> relatedList = new ArrayList<>();
  public RelatedRestaurants(final List<Restaurant> restaurants, final List<Preference> preferences) {
    List<String> list = new ArrayList<>();
    for (Restaurant restaurant : restaurants) {
      list.add(restaurant.getId());
      restaurantMap.put(restaurant.getId(), restaurant);
      relatedList.add(restaurant.getId());
    }
    for (Restaurant restaurant : restaurants) {
      Map<String, Integer> in = new HashMap<>();
      for (Preference preference : preferences) {
        if ((preference.getRestaurantIDs().contains(restaurant.getId()))) {
          for (String s : preference.getRestaurantIDs()) {
            if ((!(s.equals(restaurant.getId()))) && (list.contains(s))) {
              if (in.containsKey(s)) {
                in.put(s, in.get(s) + 1);
              } else {
                in.put(s, 1);
              }
            }
          }
        }
      }
      restaurantRelationships.put(restaurant.getId(), in);
    }
  }
  public Map<String, Integer> getRelated(final String restaurantID) {
    if (restaurantRelationships.get(restaurantID) != null) {
      return restaurantRelationships.get(restaurantID);
    } else {
      return new HashMap<>();
    }
  }
  public List<Restaurant> getRelatedInOrder(final String restaurantID) {
    if (restaurantID == null || !relatedList.contains(restaurantID)) {
      throw new IllegalArgumentException();
    }
    Map<String, Integer> mapRelated = getRelated(restaurantID);
    List<Restaurant> sortedList = new ArrayList<>();
    for (String i : mapRelated.keySet()) {
      if (restaurantMap.containsKey(i)) {
        sortedList.add(restaurantMap.get(i));
      }
    }
    Collections.sort(
            sortedList,
            (rone, rtwo) -> {
              return rone.getName().compareTo(rtwo.getName());
            });
    Collections.sort(
            sortedList,
            (rone, rtwo) -> {
              return mapRelated.get(rtwo.getId()) - (mapRelated.get(rone.getId()));
            });
    return sortedList;
  }

  public Set<Restaurant> getConnectedTo(final String restaurantID) {
    if (restaurantID == null || !relatedList.contains(restaurantID)) {
      throw new IllegalArgumentException();
    }
    Set<String> s = new HashSet<>();
    Set<Restaurant> t = new HashSet<>();
    connected(s, restaurantID, 2);
    for (String values : s) {
      if ((!values.equals(restaurantID))) {
        t.add(restaurantMap.get(values));
      }
    }
    System.out.println(t.toArray());
    return t;
  }

  private void connected(final Set<String> var, final String id, final int var2) {
    if (var2 == 0) {
      return;
    }
    Map<String, Integer> relate = getRelated(id);
    for (String identity : relate.keySet()) {
      if (relate.get(identity) > 1) {
        var.add(identity);
        connected(var, identity, var2 - 1);
      }
    }
  }
}
