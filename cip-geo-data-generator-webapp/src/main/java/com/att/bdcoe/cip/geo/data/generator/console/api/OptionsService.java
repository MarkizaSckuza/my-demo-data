package com.att.bdcoe.cip.geo.data.generator.console.api;

public interface OptionsService<T> {
    T get();

    void saveAs(T entity);
}
