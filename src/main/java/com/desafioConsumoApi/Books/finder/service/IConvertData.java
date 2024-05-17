package com.desafioConsumoApi.Books.finder.service;

public interface IConvertData {
    <T> T gettingData(String json, Class<T> clase);
}
