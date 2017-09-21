package com.stratio.scddatanps.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stratio.scdcommonutil.util.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonSerialize
@ApiModel(description = "Object that include note and the date of this note.")
public class ScoreDto {

    @ApiModelProperty(value = "List of scores compose for note and date", required = true)
    private String channelType;

    @ApiModelProperty(value = "Recommendation Note", required = true, notes = "Default Value = N/A")
    private String note;
    
    @ApiModelProperty(value = "Date on which the note was established")
    @JsonFormat(pattern = Constants.PATTERN_DATE_TIME)
    private Date date;
}
