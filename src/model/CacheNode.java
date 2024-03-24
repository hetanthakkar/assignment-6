package model;

/**
 * The CacheNode class represents a node in a cache. It stores data and
 * indicates the precision
 * of the cached data, providing a mechanism for efficient data storage and
 * retrieval.
 */

public class CacheNode implements CacheNodeInterface {
  private final boolean precise;
  private final String[] data;

  /**
   * Constructs a new CacheNode object with the specified precision and data.
   *
   * @param precise Indicates whether the data in the node is precise.
   * @param data    The data stored in the node.
   */
  public CacheNode(boolean precise, String[] data) {
    this.precise = precise;
    this.data = data;
  }

  @Override
  public String[] getData() {
    return this.data;
  }

  @Override
  public boolean getPrecise() {
    return this.precise;
  }
}