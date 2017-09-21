package com.stratio.scddatanps.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * A TouchpointNps.
 */
@Entity
@Table(name = "touchpoint_nps",
        indexes = @Index(name = "id_cliente", columnList = "id_cliente"))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TouchpointNps implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_touchpoint_nps")
    private Long touchpointId;

    @Column(name = "id_sesion")
    private Long sessionId;

    @Column(name = "id_linea")
    private Long lineId;

    @Column(name = "fec_control")
    @Temporal(TemporalType.TIMESTAMP)
    private Date controlDate;

    @Column(name = "des_unidad_organizativa")
    private String organizationalUnit;

    @Column(name = "des_tipo")
    private String type;

    @Column(name = "fec_carga")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loadDate;

    @Column(name = "id_origen")
    private Long originId;

    @Column(name = "id_interaccion")
    private Long interactionId;

    @Column(name = "des_motivo")
    private String motive;

    @Column(name = "des_nota_satisfaccion")
    private Integer noteSatisfaction;

    @Column(name = "des_nota_recomendacion")
    private Integer noteRecomendation;

    @Column(name = "id_version_fuente")
    private String sourceVersion ;

    @Column(name = "fec_operacional")
    @Temporal(TemporalType.TIMESTAMP)
    private Date operationalDate;

    @Column(name = "sw_autorizado")
    private String authSw;

    @Column(name = "des_tipo_canal")
    private String channelType;

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