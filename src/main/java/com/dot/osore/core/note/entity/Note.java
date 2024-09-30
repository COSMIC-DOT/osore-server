package com.dot.osore.core.note.entity;

import com.dot.osore.core.member.entity.Member;
import com.dot.osore.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "note")
public class Note extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "title")
    private String title;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "description")
    private String description;

    @Column(name = "contributorsCount")
    private Integer contributorsCount;

    @Column(name = "starsCount")
    private Integer starsCount;

    @Column(name = "forksCount")
    private Integer forksCount;

    @Column(name = "branch")
    private String branch;

    @Column(name = "version")
    private String version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Note(String url, String title, String avatar, String description, Integer contributorsCount,
                Integer starsCount, Integer forksCount, String branch, String version, Member member) {
        this.url = url;
        this.title = title;
        this.avatar = avatar;
        this.description = description;
        this.contributorsCount = contributorsCount;
        this.starsCount = starsCount;
        this.forksCount = forksCount;
        this.branch = branch;
        this.version = version;
        this.member = member;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
