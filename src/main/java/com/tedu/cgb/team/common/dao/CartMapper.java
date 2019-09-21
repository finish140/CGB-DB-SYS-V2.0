package com.tedu.cgb.team.common.dao;

import com.tedu.cgb.team.common.entity.Cart;
import com.tedu.cgb.team.common.entity.CartExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CartMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carts
     *
     * @mbg.generated Sat Sep 21 08:56:55 CST 2019
     */
    int countByExample(CartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carts
     *
     * @mbg.generated Sat Sep 21 08:56:55 CST 2019
     */
    int deleteByExample(CartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carts
     *
     * @mbg.generated Sat Sep 21 08:56:55 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carts
     *
     * @mbg.generated Sat Sep 21 08:56:55 CST 2019
     */
    int insert(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carts
     *
     * @mbg.generated Sat Sep 21 08:56:55 CST 2019
     */
    int insertSelective(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carts
     *
     * @mbg.generated Sat Sep 21 08:56:55 CST 2019
     */
    List<Cart> selectByExample(CartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carts
     *
     * @mbg.generated Sat Sep 21 08:56:55 CST 2019
     */
    Cart selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carts
     *
     * @mbg.generated Sat Sep 21 08:56:55 CST 2019
     */
    int updateByExampleSelective(@Param("record") Cart record, @Param("example") CartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carts
     *
     * @mbg.generated Sat Sep 21 08:56:55 CST 2019
     */
    int updateByExample(@Param("record") Cart record, @Param("example") CartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carts
     *
     * @mbg.generated Sat Sep 21 08:56:55 CST 2019
     */
    int updateByPrimaryKeySelective(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table carts
     *
     * @mbg.generated Sat Sep 21 08:56:55 CST 2019
     */
    int updateByPrimaryKey(Cart record);
}