package com.lab.soa.data.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "data")
public class Data {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", updatable = false)
    private Date createDate;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "message_received_date")
    private Date messageReceivedDate;
    @Column(name = "data")
    @NonNull private String data;
}
