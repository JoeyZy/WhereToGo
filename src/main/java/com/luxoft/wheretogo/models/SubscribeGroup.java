package com.luxoft.wheretogo.models;

import com.google.common.io.ByteStreams;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by eleonora on 07.07.16.
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name"})
@ToString(of = {"id", "name"})
public class SubscribeGroup {
    private static final Logger LOG = Logger.getLogger(SubscribeGroup.class);

    private long groupId;
    private long[] usersToAdd;

}
