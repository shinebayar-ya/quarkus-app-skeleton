package io.neostack.skeleton.common;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class BaseQueryRequest implements Serializable {

  @QueryParam("page")
  @DefaultValue("0")
  private int page;

  @QueryParam("size")
  @DefaultValue("10")
  private int size;

  /**
   * Default sort is ascending.
   * <p>
   * Example: ?sort=field1,field2,-field3
   * <p>
   */
  @QueryParam("sort")
  private String sort;

  public int getPage() {
    return Math.max(page, 0);
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getSize() {
    return Math.max(size, 1);
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public int getOffset() {
    return getPage() * getSize();
  }

  public Set<String> getSortableFields() {
    return new HashSet<>();
  }
}
