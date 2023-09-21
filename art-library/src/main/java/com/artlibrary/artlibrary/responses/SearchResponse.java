package com.artlibrary.artlibrary.responses;

import java.util.List;
import java.util.Objects;

public class SearchResponse{
    private int page;
    private int count;
    private List<SearchResult> results;
    private int pageSize;

    public SearchResponse() {
    }

    public SearchResponse(int page, int count, List<SearchResult> results, int pageSize) {
        this.page = page;
        this.count = count;
        this.results = results;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SearchResult> getResults() {
        return this.results;
    }

    public void setResults(List<SearchResult> results) {
        this.results = results;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public SearchResponse page(int page) {
        this.page = page;
        return this;
    }

    public SearchResponse count(int count) {
        this.count = count;
        return this;
    }

    public SearchResponse results(List<SearchResult> results) {
        this.results = results;
        return this;
    }

    public SearchResponse pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SearchResponse)) {
            return false;
        }
        SearchResponse searchResponse = (SearchResponse) o;
        return page == searchResponse.page && count == searchResponse.count && Objects.equals(results, searchResponse.results) && pageSize == searchResponse.pageSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, count, results, pageSize);
    }

    @Override
    public String toString() {
        return "{" +
            " page='" + getPage() + "'" +
            ", count='" + getCount() + "'" +
            ", results='" + getResults() + "'" +
            ", pageSize='" + getPageSize() + "'" +
            "}";
    }
}