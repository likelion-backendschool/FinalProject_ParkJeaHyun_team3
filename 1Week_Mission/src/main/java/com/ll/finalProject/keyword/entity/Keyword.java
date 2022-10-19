package com.ll.finalProject.keyword.entity;

import com.ll.finalProject.base.entity.BaseEntity;
import com.ll.finalProject.hashTag.entity.HashTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Keyword extends BaseEntity {
    private String content;

    @OneToMany(mappedBy = "keyword")
    @Builder.Default
    private Set<HashTag> hashTags = new LinkedHashSet<>();
}
