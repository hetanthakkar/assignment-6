package model;

/**
 * The CacheNodeInterface interface defines a contract for classes that
 * represent nodes in a cache.
 * It provides methods to retrieve data stored in the node and to determine the
 * precision of the cached data.
 */

public interface CacheNodeInterface {

  /**
   * Retrieves the data stored in the cache node.
   *
   * @return An array of string representing the data stored in the cache node.
   */
  String[] getData();

  /**
   * Determines whether the data in the cache node is precise.
   *
   * @return true if the data is precise, false otherwise.
   */
  boolean getPrecise();
}
