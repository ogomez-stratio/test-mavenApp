package com.stratio.scddatanps.domain.pk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Pk por touchPointNps entity
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TouchpointNpsPk  implements Serializable{
    private static final long serialVersionUID = 1L;

    @Column(name = "id_cliente")
    private Long clientId;

    @Column(name = "des_canal")
    private String channel;

    @Column(name = "fec_estado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusDate;

    @Column(name = "fec_datalake")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datalakeDate;
}
