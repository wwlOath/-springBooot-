package io.bioaitech.modules.bioaitech.service.impl;

import io.bioaitech.common.exception.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.bioaitech.common.utils.PageUtils;
import io.bioaitech.common.utils.Query;

import io.bioaitech.modules.bioaitech.dao.CategoryDao;
import io.bioaitech.modules.bioaitech.entity.CategoryEntity;
import io.bioaitech.modules.bioaitech.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    //它可以被标注在构造函数、属性、setter方法或配置方法上，用于实现依赖自动注入。
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> getChipCategory(int type) {
        List<CategoryEntity> categories = new ArrayList<>();
        List<CategoryEntity> list = this.list();
        if(type == 1) {
            getChildCategory(list, "1", categories);
        }else if(type == 2) {
            getChildCategory(list, "2", categories);
        }

        if(categories.size() <= 0) {
            throw new RRException("未查询到分类信息");
        }
        return categories;
    }

    //伪代码 表示重写
    @Override
    public Integer getMaxId() {
        return categoryDao.getMaxId();
    }

    /*
    * 递归查找子节点
    * */

    public static void getChildCategory(List<CategoryEntity> allCategoryList, String id, List<CategoryEntity> childCategoryList) {
        for(CategoryEntity category: allCategoryList) {
            if(category.getPid().toString().equals(id)) {
                getChildCategory(allCategoryList, category.getId().toString(), childCategoryList);
                childCategoryList.add(category);
            }
        }
    }
}
