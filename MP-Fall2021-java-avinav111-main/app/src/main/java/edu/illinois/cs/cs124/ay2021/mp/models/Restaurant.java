package edu.illinois.cs.cs124.ay2021.mp.models;

import androidx.annotation.NonNull;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
 * Model storing information about a restaurant retrieved from the restaurant server.
 *
 * You will need to understand some of the code in this file and make changes starting with MP1.
 *
 * If your project builds successfully, you can safely ignore the warning about "Related problems" here.
 * It seems to be a bug in Android studio.
 */
@SuppressWarnings("unused")
public final class Restaurant implements SortedListAdapter.ViewModel {
  // Name of the restaurant
  private String name;

  // Getter for the name
  public String getName() {
    return name;
  }

  // Cuisine that a restaurant serves
  private String cuisine;

  // Getter for the cuisine
  public String getCuisine() {
    return cuisine;
  }

  // ID of a restaurant
  private String id;

  // Getter for the id
  public String getId() {
    return id;
  }
  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof Restaurant)) {
      return false;
    }
    return id == this.id;
  }
  @Override
  public int hashCode() {
    return id.hashCode();
  }

  public String getFullName() {
    return name + cuisine;
  }

  // You will need to add more fields here...
  public static List<Restaurant> search(final List<Restaurant> restaurants, final String search) {
    if (search == null || restaurants == null) {
      throw new IllegalArgumentException();
    }
    List<Restaurant> toReturn = new ArrayList<>();
    String s = search.trim();
    if (s.equals("") || s.length() == 0) {
      // should return a copy of the restaurants;
      for (int i = 0; i < restaurants.size(); i++) {
        toReturn.add(restaurants.get(i));
      }
      return toReturn;
    }
    for (int i = 0; i < restaurants.size(); i++) {
      if (s.equalsIgnoreCase(restaurants.get(i).getCuisine())) {
        toReturn.add(restaurants.get(i));
      }
    }

    // the second for loop should only run if toReturn is empty after the first
    if (toReturn.size() == 0) {
      for (int j = 0; j < restaurants.size(); j++) {
        // your contain needs to be case insensitive;
        if (restaurants.get(j).getName().toLowerCase().contains(s.toLowerCase())
                || restaurants.get(j).getCuisine().toLowerCase().contains(s.toLowerCase())) {
          toReturn.add(restaurants.get(j));
        }
      }
    }
    return toReturn;
  }
  /*
   * The Jackson JSON serialization library we are using requires an empty constructor.
   * So don't remove this!
   */
  public Restaurant() {}

  /*
   * Function to compare Restaurant instances by name.
   * Currently this does not work, but you will need to implement it correctly for MP1.
   * Comparator is like Comparable, except it defines one possible ordering, not a canonical ordering for a class,
   * and so is implemented as a separate method rather than directly by the class as is done with Comparable.
   */
  public static final Comparator<Restaurant> SORT_BY_NAME = ((restaurant1, restaurant2) -> {
    if (restaurant1.name.compareTo(restaurant2.name) > 0) {
      return 1;
    }
    if (restaurant1.name.compareTo(restaurant2.name) < 0) {
      return -1;
    }
    return 0;
  });

  // You should not need to modify this code, which is used by the list adapter component
  @Override
  public <T> boolean isSameModelAs(@NonNull final T model) {
    return equals(model);
  }

  // You should not need to modify this code, which is used by the list adapter component
  @Override
  public <T> boolean isContentTheSameAs(@NonNull final T model) {
    return equals(model);
  }
}
