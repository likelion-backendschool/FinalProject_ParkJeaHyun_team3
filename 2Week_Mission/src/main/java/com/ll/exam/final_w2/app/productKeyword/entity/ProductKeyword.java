package com.ll.exam.final_w2.app.productKeyword.entity;

import com.ll.exam.final_w2.app.base.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ProductKeyword extends BaseEntity {

    private String content;

    public Object getListUrl() {
        return "/product/tag/" + content;
    }

    public long getExtra_postTagsCount() {
        return (long) getExtra().get("postTagsCount");
    }
}
