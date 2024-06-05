package com.dot.osore.domain.note.entity;

import com.dot.osore.domain.note.dto.NoteRequest;
import com.dot.osore.domain.user.entity.User;
import com.dot.osore.util.entity.BaseEntity;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "title")
    private String title;

    @Column(name = "branch")
    private String branch;

    @Column(name = "version")
    private String version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Note(NoteRequest note, User user) {
        this.url = note.getUrl();
        this.title = note.getTitle();
        this.branch = note.getBranch();
        this.version = note.getVersion();
        this.user = user;
    }
}
