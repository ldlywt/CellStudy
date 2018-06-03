package com.ldlywt.cell.service.impl;

import com.ldlywt.cell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;


    @Test
    public void findOne() throws Exception{
        ProductCategory category = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1), category.getCategoryId());
    }



}