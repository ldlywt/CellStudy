package com.ldlywt.cell.repository;

import com.ldlywt.cell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository reposity;

    @Test
    public void findOneTest(){
        ProductCategory category = reposity.findOne(1);
        System.out.println(category.toString());

    }

    @Test
    @Transactional // 测试完成后把数据库清掉，完全回滚
    public void saveTest(){
        ProductCategory category = new ProductCategory("女生最爱", 3);
        ProductCategory result = reposity.save(category);
        Assert.assertNotNull(result);
    }


}