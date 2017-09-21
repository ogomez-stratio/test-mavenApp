package com.stratio.scddatanps.web.rest;

import com.stratio.scdcommonutil.util.Constants;
import com.stratio.scdcommonutil.web.rest.util.ResponseUtil;
import com.stratio.scddatanps.dto.ChannelDto;
import com.stratio.scddatanps.service.TouchpointNpsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TouchpointNps.
 */
@RestController
@RequestMapping("/api/v1")
@Api(value = "NPS: Touchpoint API NPS",
    protocols = "HTTP",
    tags = Constants.SWAGGER.TAG_DATA_LAYER,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class TouchpointNpsResource {

    private final Logger log = LoggerFactory.getLogger(TouchpointNpsResource.class);
    private final TouchpointNpsService touchpointNpsService;

    /**
     * GET  /touchpoint-nps/:clientid/channels/score : get the score for clientId group by channels touchpointNps.
     *
     * @param clientId the id of the client to retrieve the score group by channels
     * @return the ResponseEntity with status 200 (OK) and with body the list of score group by channels.
     */
    @GetMapping("/touchpoint-nps/{clientId}/channels/scores")
    @ApiOperation(value = "Get the last 5 scores of the last 365 days for client and grouping by channels.",
                  notes = "The channels are: (VOZ, EMAIL, WEB, CALLBACK and ASISTENCIAL). "
                        + "If there are no notes on a channel, it will return N/A")
    public ResponseEntity<List<ChannelDto>> getScoresByClientIdGroupByChannel (
            @ApiParam(value = "The id of the client to retrieve the score group by channels", required = true) 
            @PathVariable("clientId") Long clientId)  throws Exception{
        List<ChannelDto> score = touchpointNpsService.getScoresByClientIdGroupByChannel(clientId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(score));
    }
    
    

    public TouchpointNpsResource(TouchpointNpsService touchpointNpsService) {
        this.touchpointNpsService = touchpointNpsService;
    }

}
