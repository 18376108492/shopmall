package com.itdan.shopmall.service.impl;

import com.itdan.shopmall.dao.TbContentCategoryMapper;
import com.itdan.shopmall.dao.TbContentMapper;
import com.itdan.shopmall.entity.TbContent;
import com.itdan.shopmall.entity.TbContentCategory;
import com.itdan.shopmall.entity.TbContentCategoryExample;
import com.itdan.shopmall.entity.TbContentExample;
import com.itdan.shopmall.service.ContentService;
import com.itdan.shopmall.utils.result.EasyUITreeNode;
import com.itdan.shopmall.utils.result.ShopMallResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商城后台对前台内容管理逻辑类
 */
@Service
public class ConentServiceImpl implements ContentService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentList(long parentId) {
        //添加查询条件
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria=example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //通过父类id查询出树形结构
        List<TbContentCategory> categoryList=tbContentCategoryMapper.selectByExample(example);
        //创建返回集
        List<EasyUITreeNode> list=new ArrayList<>();
        //将查询结果进行遍历和封装
        for(TbContentCategory category:categoryList){
            //创建树形节点
            EasyUITreeNode node=new EasyUITreeNode();
            node.setId(category.getId());
            node.setText(category.getName());
            node.setState(category.getIsParent()?"closed":"open");
            //添加节点
            list.add(node);
        }

        return list;
    }

    @Override
    public ShopMallResult addContentCategroy(long parentId, String name) {

        //创建一个新的TbContentCategory对象，将参数存入对象中
        TbContentCategory contentCategory=new TbContentCategory();
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        //'状态。可选值:1(正常),2(删除)',
        contentCategory.setStatus(1);
        contentCategory.setSortOrder(1);//默认排序为1
        contentCategory.setIsParent(false);//新添加的节点为false
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //将其插入到数据库中
        tbContentCategoryMapper.insert(contentCategory);

        //还要将其父类的状态修改isParent为true状态,如果不为true将其修改为true
       TbContentCategory parentCategory=tbContentCategoryMapper.selectByPrimaryKey(parentId);
       parentCategory.setIsParent(true);
       tbContentCategoryMapper.updateByPrimaryKey(parentCategory);
       return ShopMallResult.ok(contentCategory);
    }
}
