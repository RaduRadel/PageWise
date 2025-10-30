package com.pagewise.factory;

public interface EntityFactory<T> {
    T create(String... params);
}
