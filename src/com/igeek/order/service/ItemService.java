package com.igeek.order.service;

import com.igeek.order.dao.ItemsDao;
import com.igeek.order.entity.Items;
import com.igeek.order.utils.JDBCUtils;
import com.igeek.order.vo.PageVO;

import java.sql.SQLException;
import java.util.List;

/**
 * @version 1.0
 * @Description ItemService - 商品的业务逻辑层
 */
public class ItemService {

    private ItemsDao dao = new ItemsDao();

    /**
     * 根据条件+分页 查询
     * @param query   查询条件
     * @param pageNow 当前页码
     * @return  PageVO
     */
    public PageVO<Items> fenye(String query , Integer pageNow){
        PageVO<Items> vo = null;
        try {
            //总记录数
            Integer myCounts = Integer.valueOf(dao.selectCounts(query).toString());
            //总页数
            Integer myPages = myCounts%3==0?myCounts/3:myCounts/3+1;
            //计算起始值
            Integer begin = (pageNow-1)*3;
            //数据
            List<Items> list = dao.selectAllByQuery(query, begin);
            //封装PageVO
            vo = new PageVO<>(pageNow,myCounts,myPages,query,null,list);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close();
        }
        return vo;
    }

    //校验名字是否存在
    public boolean validateName(String name) {
        try {
            Items items = dao.selectOne(name);
            if(items!=null){
                return true;  //名字不可用
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close();
        }
        return false;  //名字可用
    }

    //上架商品
    public boolean add(Items items) {
        boolean flag = this.validateName(items.getName());
        //flag:  false 名字可用 可以添加商品信息
        if(!flag){
            int i = 0;
            try {
                i = dao.insert(items);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.close();
            }
            return i>0?true:false;  //true插入成功  false插入失败
        }
        return false;
    }

    //下架商品
    public boolean deleteAll(String[] ids) {
        int count = 0;
        for (String idStr : ids) {
            int id = Integer.parseInt(idStr);
            try {
                int i = dao.delete(id);
                if(i>0){
                    count++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.close();
            }
        }
        return (count==ids.length)?true:false;
    }

    //查看商品详情
    public Items viewOne(int id) {
        Items items = null;
        try {
            items = dao.selectOne(id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close();
        }
        return items;
    }

    //修改商品信息
    public boolean modify(Items items) {
        boolean b = false;
        try {
            //获得商品的原名字
            String oldName = dao.selectOne(items.getId()).getName();
            //获得商品的新名字
            String newName = items.getName();
            //若旧名称与新名称不同，校验新名称在数据库中是否存在
            if(!oldName.equals(newName)){
                boolean flag = this.validateName(newName);
                //true不可用  false可用
                if(!flag){
                    b = true;
                }
            }else{
                b = true;
            }
            if(b){
                int i = dao.update(items);
                return i>0?true:false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close();
        }
        return b;
    }
}
