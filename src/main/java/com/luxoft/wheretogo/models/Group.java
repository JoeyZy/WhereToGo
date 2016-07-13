package com.luxoft.wheretogo.models;

import com.google.common.io.ByteStreams;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.log4j.Logger;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Blob;

/**
 * Created by eleonora on 07.07.16.
 */
@Entity
@Table(name = "groups")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name"})
@ToString(of = {"id", "name"})
public class Group {

    @Transient
    private static final Logger LOG = Logger.getLogger(Event.class);

    @Id
    private long id;

    @Size(min = 2, max = 30)
    private String name;

    @Column(name="picture")
    private Blob picture;

    private String description;
    private String location;

    public String getPicture(){
        if(this.picture == null){
            return "";
        }

        try{
            return new String(ByteStreams.toByteArray(this.picture.getBinaryStream()));
        } catch (Exception e){
            LOG.warn("Cannot convert image",e);
        }
        return "";
    }

}
