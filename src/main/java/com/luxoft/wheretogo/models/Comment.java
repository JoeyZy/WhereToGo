package com.luxoft.wheretogo.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.log4j.Logger;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "comments")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "message"})
@ToString(of = {"id", "message"})

/**
 * Created by serhii on 05.08.16.
 */
public class Comment {

        @Transient
        private static final Logger LOG = Logger.getLogger(Comment.class);

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Size(min = 1, max = 1000)
        @Column(name = "content")
        private String content;

//        @NotNull
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "author")
        private User author;

        @Column(name = "parent")
        private String parent;

        @NotNull
        @Column(name = "event")
        private long event;

        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yy/MM/dd HH:mm:ss", timezone="default")
        private Date created;

        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yy/MM/dd HH:mm:ss", timezone="default")
        private Date modified;



}
