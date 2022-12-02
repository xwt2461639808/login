package com.igeek.order.dao;

import com.igeek.order.entity.Items;

import java.sql.SQLException;
import java.util.List;

/**
 * @version 1.0
 * @Description Items  - 商品的数据交互层
 */
public class ItemsDao extends BaseDao<Items> {

    //根据条件，查询商品的总记录数
    public Long selectCounts(String query) throws SQLException {
        String sql = "select count(*) from items where name like concat('%',?,'%')";
        Object value = this.getSingleValue(sql, query);
        return Long.valueOf(value.toString());
    }

    //根据条件，模糊查询商品信息，分页
    public List<Items> selectAllByQuery(String query, Integer begin) throws SQLException {
        String sql = "select * from items where name like concat('%',?,'%') limit ?,3";
        List<Items> list = this.getBeanList(sql, Items.class, query, begin);
        return list;
    }

    //根据商品名称，精确查询商品信息
    public Items selectOne(String name) throws SQLException {
        String sql = "select * from items where name = ?";
        Items items = this.getBean(sql, Items.class, name);
        return items;
    }

    //插入商品信息
    public int insert(Items items) throws SQLException {
        String sql = "insert into items values(null,?,?,?,?,?)";
        int i = this.update(sql ,
                items.getName(),items.getPrice(),items.getDetail(),
                items.getPic(),items.getCreatetime());
        return i;
    }

    //删除商品信息
    public int delete(int id) throws SQLException {
        String sql = "delete from items where id = ?";
        int i = this.update(sql, id);
        return i;
    }

    //根据商品id，查询商品信息
    public Items selectOne(Integer id) throws SQLException {
        String sql = "select * from items where id = ?";
        Items items = this.getBean(sql, Items.class, id);
        return items;
    }

    //更新商品信息
    public int update(Items items) throws SQLException {
        String sql = "update items set name=? , price=? , createtime=? , detail=? , pic=? where id = ?";
        int i = this.update(sql,items.getName(),items.getPrice(),items.getCreatetime(),items.getDetail(),
                items.getPic(),items.getId());
        return i;
    }
}
