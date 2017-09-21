package com.stratio.scddatanps.service;

import com.stratio.scddatanps.dto.ChannelDto;

import java.util.List;

/**
 * Service Interface for managing TouchpointNps.
 */
public interface TouchpointNpsService {

    List<ChannelDto> getScoresByClientIdGroupByChannel(Long clientId) throws Exception;
}
