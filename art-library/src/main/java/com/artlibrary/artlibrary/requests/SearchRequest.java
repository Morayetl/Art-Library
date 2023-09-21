
package com.artlibrary.artlibrary.requests;

import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SearchRequest{
    @NotNull  
    @NotEmpty
    private String query;
    private String filterBy;

    @NotEmpty
    @Min(0)
    private int page;

    public SearchRequest() {
    }

    public SearchRequest(String query, String filterBy, int page) {
        this.query = query;
        this.filterBy = filterBy;
        this.page = page;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getFilterBy() {
        return this.filterBy;
    }

    public void setFilterBy(String filterBy) {
        this.filterBy = filterBy;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public SearchRequest query(String query) {
        this.query = query;
        return this;
    }

    public SearchRequest filterBy(String filterBy) {
        this.filterBy = filterBy;
        return this;
    }

    public SearchRequest page(int page) {
        this.page = page;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SearchRequest)) {
            return false;
        }
        SearchRequest searchRequest = (SearchRequest) o;
        return Objects.equals(query, searchRequest.query) && Objects.equals(filterBy, searchRequest.filterBy) && page == searchRequest.page;
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, filterBy, page);
    }

    @Override
    public String toString() {
        return "{" +
            " query='" + getQuery() + "'" +
            ", filterBy='" + getFilterBy() + "'" +
            ", page='" + getPage() + "'" +
            "}";
    }
}