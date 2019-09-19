package com.tedu.cgb.team.common.entity;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comments.id
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comments.creation_date
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    private Date creationDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comments.user_id
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comments.product_id
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    private Integer productId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comments.content
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table comments
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comments.id
     *
     * @return the value of comments.id
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comments.id
     *
     * @param id the value for comments.id
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comments.creation_date
     *
     * @return the value of comments.creation_date
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comments.creation_date
     *
     * @param creationDate the value for comments.creation_date
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comments.user_id
     *
     * @return the value of comments.user_id
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comments.user_id
     *
     * @param userId the value for comments.user_id
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comments.product_id
     *
     * @return the value of comments.product_id
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comments.product_id
     *
     * @param productId the value for comments.product_id
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comments.content
     *
     * @return the value of comments.content
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comments.content
     *
     * @param content the value for comments.content
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comments
     *
     * @mbg.generated Thu Sep 19 23:27:09 CST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", creationDate=").append(creationDate);
        sb.append(", userId=").append(userId);
        sb.append(", productId=").append(productId);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}