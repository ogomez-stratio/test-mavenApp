package com.stratio.scddatanps.service.impl;

import com.stratio.scddatanps.config.Channel;
import com.stratio.scddatanps.config.ScdAppProperties;
import com.stratio.scddatanps.dto.ChannelDto;
import com.stratio.scddatanps.dto.ScoreDto;
import com.stratio.scddatanps.repository.TouchpointNpsRepository;
import com.stratio.scddatanps.service.ConverterScore;
import com.stratio.scddatanps.service.TouchpointNpsService;
import org.dozer.DozerBeanMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing TouchpointNps.
 */
@Service
@Transactional
public class TouchpointNpsServiceImpl implements TouchpointNpsService{

    private final Logger log = LoggerFactory.getLogger(TouchpointNpsServiceImpl.class);
    
    private final TouchpointNpsRepository touchpointNpsRepository;
    private final DozerBeanMapper dozerBeanMapper;
    private final ConverterScore converterScore;
    private final ScdAppProperties scdAppProperties;

    @Override
    @Transactional(readOnly = true)
    public List<ChannelDto> getScoresByClientIdGroupByChannel(Long clientId) throws Exception {
        if(touchpointNpsRepository.countByClientId(clientId) == 0 ){
        return null;
       }
        List<ChannelDto> ret = initScores();
        touchpointNpsRepository.findByClientIdAndStatusDateAfterOrderByControlDateDesc(clientId, new DateTime().minusYears(1).toDate())
            .forEach(touchpoint -> {
                String channelType = getChannelType(touchpoint.getChannel());
                List<ScoreDto> scores = getScoresDtoByChannel(ret, touchpoint.getChannel(),channelType);
                if (scores.size() < scdAppProperties.getLimitRecordScoresByChannel()
                        && !ConverterScore.CLIENT_NO_INFORM.equals(touchpoint.getNoteRecomendation())) {
                    scores.add(ScoreDto.builder()
                            .channelType(touchpoint.getChannel())
                            .note(touchpoint.getNoteRecomendation().toString())
                            .date(touchpoint.getControlDate())
                            .build());
                }
            });

        fillChannelsEmpty(ret);
        
        return ret;
    }

    private String getChannelType(String channel) {
        return scdAppProperties.getChannels().stream()
                .filter(ch -> ch.getTypes().contains(channel))
                .findFirst()
                .map(Channel::getChannel)
                .orElse("");
    }

    private void fillChannelsEmpty(List<ChannelDto> ret) {
        ret.stream()
            .filter(channelDto -> channelDto.getScores() == null || channelDto.getScores().isEmpty())
            .forEach(emptyChannelDto -> emptyChannelDto.getScores().add(ScoreDto.builder()
                                                                        .channelType(ConverterScore.N_A)
                                                                        .note(ConverterScore.N_A)
                                                                        .build())
        );
    }

    private List<ChannelDto> initScores() {
        List<ChannelDto> ret = new ArrayList<>();
        
        scdAppProperties.getChannels().forEach(
                channel -> ret.add(ChannelDto.builder().channel(channel.getChannel()).scores(new ArrayList<>()).build())
        );
        return ret;
    }
    
    private List<ScoreDto> getScoresDtoByChannel(List<ChannelDto> channels, String channelType, String channel) {
       List<ScoreDto> ret = channels.stream()
            .filter(ch -> ch.getChannel().equalsIgnoreCase(channel) && belongsToChannel(channelType))
            .findFirst()
            .map(ChannelDto::getScores)
            .orElse(new ArrayList<>());

       return ret;
    }

    private boolean belongsToChannel(String channelType) {
        return scdAppProperties.getChannels().stream().anyMatch(prop->prop.getTypes().contains(channelType));
    }


    public TouchpointNpsServiceImpl(TouchpointNpsRepository touchpointNpsRepository, DozerBeanMapper dozerBeanMapper, ConverterScore converterScore, ScdAppProperties scdAppProperties) {
        this.touchpointNpsRepository = touchpointNpsRepository;
        this.dozerBeanMapper = dozerBeanMapper;
        this.converterScore = converterScore;
        this.scdAppProperties = scdAppProperties;
    }

}
