package com.dot.osore.domain.member.entity;

import com.dot.osore.util.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO: User -> Member 변경 필요
@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "avatar", nullable = false)
    private String avatar;

    @Builder
    public User(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }
}
