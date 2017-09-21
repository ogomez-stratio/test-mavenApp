package com.stratio.scddatanps.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@ApiModel(description = "Last scores in the last 365 days and grouping by Channel")
public class ChannelDto implements Serializable {
    
    private static final long serialVersionUID = 6761710701526627649L;

    @ApiModelProperty(value = "List of scores compose for note and date", required = true)
    private String channel;

    @ApiModelProperty(value = "List of scores compose for note and date", required = true)
    @JsonProperty(value = "scores")
    private List<ScoreDto> scores;
    
}
