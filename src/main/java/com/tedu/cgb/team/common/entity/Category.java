package com.tedu.cgb.team.common.entity;

import java.io.Serializable;

public class Category implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column categorys.id
     *
     * @mbg.generated Sat Sep 21 01:57:29 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column categorys.type_name
     *
     * @mbg.generated Sat Sep 21 01:57:29 CST 2019
     */
    private String typeName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table categorys
     *
     * @mbg.generated Sat Sep 21 01:57:29 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column categorys.id
     *
     * @return the value of categorys.id
     *
     * @mbg.generated Sat Sep 21 01:57:29 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column categorys.id
     *
     * @param id the value for categorys.id
     *
     * @mbg.generated Sat Sep 21 01:57:29 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column categorys.type_name
     *
     * @return the value of categorys.type_name
     *
     * @mbg.generated Sat Sep 21 01:57:29 CST 2019
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column categorys.type_name
     *
     * @param typeName the value for categorys.type_name
     *
     * @mbg.generated Sat Sep 21 01:57:29 CST 2019
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table categorys
     *
     * @mbg.generated Sat Sep 21 01:57:29 CST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", typeName=").append(typeName);
        sb.append("]");
        return sb.toString();
    }
}