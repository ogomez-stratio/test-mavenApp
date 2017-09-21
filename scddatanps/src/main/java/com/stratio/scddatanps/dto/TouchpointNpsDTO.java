package com.stratio.scddatanps.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the TouchpointNps entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TouchpointNpsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long touchpointId;

    private Long sessionId;

    private Long lineId;

    private Date controlDate;

    private String channel;

    private String channelType;

    private String organizationalUnit;

    private String type;

    private Date loadDate;

    private Long originId;

    private Long clientId;

    private Long interactionId;

    private String motive;

    private Integer noteSatisfaction;

    private Integer noteRecomendation;

    private Date statusDate;

    private String sourceVersion ;

    private Date operationalDate;

    private Date datalakeDate;

    private String touchpointUuid;
}
