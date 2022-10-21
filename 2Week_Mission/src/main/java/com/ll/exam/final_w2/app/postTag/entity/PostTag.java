package com.ll.exam.final_w2.app.postTag.entity;

import com.ll.exam.final_w2.app.base.entity.BaseEntity;
import com.ll.exam.final_w2.app.member.entity.Member;
import com.ll.exam.final_w2.app.post.entity.Post;
import com.ll.exam.final_w2.app.postkeyword.entity.PostKeyword;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class PostTag extends BaseEntity {
    @ManyToOne
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne
    @ToString.Exclude
    private Member member;

    @ManyToOne
    @ToString.Exclude
    private PostKeyword postKeyword;
}
