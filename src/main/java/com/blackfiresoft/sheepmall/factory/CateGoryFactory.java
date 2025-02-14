package com.blackfiresoft.sheepmall.factory;

import com.blackfiresoft.sheepmall.product.CateGory;

import java.util.List;

public interface CateGoryFactory {

    default void addCateGory(CateGory cateGory) {
    }


    default void deleteCateGoryById(Long id) {
    }


    default void updateCateGory(CateGory cateGory) {
    }


    List<CateGory> getAllCateGory();

}
