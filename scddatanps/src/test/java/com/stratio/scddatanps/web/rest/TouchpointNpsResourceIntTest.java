package com.stratio.scddatanps.web.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.stratio.scdcommonutil.util.Util;
import com.stratio.scdcommonutil.util.test.TestBase;
import com.stratio.scddatanps.ScddatanpsApplication;
import com.stratio.scddatanps.config.ScdAppProperties;
import com.stratio.scddatanps.domain.TouchpointNps;
import com.stratio.scddatanps.dto.ChannelDto;
import com.stratio.scddatanps.dto.TouchpointNpsDTO;
import com.stratio.scddatanps.repository.TouchpointNpsRepository;
import com.stratio.scddatanps.service.ConverterScore;
import com.stratio.scddatanps.service.TouchpointNpsService;
import org.dozer.DozerBeanMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the TouchpointNpsResource REST controller.
 *
 * @see TouchpointNpsResource
 */
@SpringBootTest(classes = ScddatanpsApplication.class)
public class TouchpointNpsResourceIntTest extends TestBase {

    private static final Long DEFAULT_TOUCHPOINT_ID = 1L;
    private static final Long TOUCHPOINT_ID_1 = 2L;
    private static final Long TOUCHPOINT_ID_2 = 3L;

    private static final Long DEFAULT_SESSION_ID = 1L;
    private static final Long UPDATED_SESSION_ID = 2L;

    private static final Long DEFAULT_LINE_ID = 1L;
    private static final Long UPDATED_LINE_ID = 2L;

    private static final String DEFAULT_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATIONAL_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATIONAL_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Date DEFAULT_LOAD_DATE = new DateTime(0L).toDate();
    private static final Date UPDATED_LOAD_DATE = GregorianCalendar.getInstance().getTime();

    private static final Long DEFAULT_ORIGIN_ID = 1L;
    private static final Long UPDATED_ORIGIN_ID = 2L;

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;

    private static final Long DEFAULT_INTERACTION_ID = 1L;
    private static final Long UPDATED_INTERACTION_ID = 2L;

    private static final String DEFAULT_MOTIVE = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOTE_SATISFACTION = 1;
    private static final Integer UPDATED_NOTE_SATISFACTION = 2;

    private static final Integer DEFAULT_NOTE_RECOMENDATION = 4;
    private static final Integer UPDATED_NOTE_RECOMENDATION = 7;
    private static final Integer NOTE_RECOMENDATION_1 = 7;
    private static final Integer NOTE_RECOMENDATION_2 = 8;
    private static final Integer NOTE_RECOMENDATION_NA = null;

    private static final Date DEFAULT_STATUS_DATE = new DateTime(0L).toDate();
    private static final Date UPDATED_STATUS_DATE = GregorianCalendar.getInstance().getTime();

    private static final Date DEFAULT_DATALAKE_DATE = new DateTime(0L).toDate();
    private static final Date UPDATED_DATALAKE_DATE = GregorianCalendar.getInstance().getTime();

    private static final Date DEFAULT_CONTROL_DATE = new DateTime(0L).toDate();
    private static final Date UPDATED_CONTROL_DATE = GregorianCalendar.getInstance().getTime();
    private static final Date CONTROL_DATE_1 = new DateTime(100000L).toDate();
    private static final Date CONTROL_DATE_2 = new DateTime(200000L).toDate();

    private static final String DEFAULT_SOURCE_VERSION = "AAAAAAAAAA";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";

    private static final String DEFAULT_SOURCE_FILE = "AAAAAAAAAA";

    private static final Date DEFAULT_OPERATIONAL_DATE = new DateTime(0L).toDate();





    @Autowired
    private TouchpointNpsRepository touchpointNpsRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private TouchpointNpsService touchpointNpsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTouchpointNpsMockMvc;

    private TouchpointNps touchpointNps;
    private List<TouchpointNps> touchpointsNpsByChannel;
    
    @Autowired
    private ConverterScore converterScore;
    @Autowired
    private ScdAppProperties properties;
    

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TouchpointNpsResource touchpointNpsResource = new TouchpointNpsResource(touchpointNpsService);
        this.restTouchpointNpsMockMvc = MockMvcBuilders.standaloneSetup(touchpointNpsResource)
                .setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter)
                .build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TouchpointNps createEntity(EntityManager em) {
        return TouchpointNps.builder()
                .touchpointId(DEFAULT_TOUCHPOINT_ID)
                .sessionId(DEFAULT_SESSION_ID)
                .lineId(DEFAULT_LINE_ID)
                .organizationalUnit(DEFAULT_ORGANIZATIONAL_UNIT)
                .type(DEFAULT_TYPE)
                .loadDate(DEFAULT_LOAD_DATE)
                .originId(DEFAULT_ORIGIN_ID)
                .interactionId(DEFAULT_INTERACTION_ID)
                .motive(DEFAULT_MOTIVE)
                .noteSatisfaction(DEFAULT_NOTE_SATISFACTION)
                .noteRecomendation(DEFAULT_NOTE_RECOMENDATION)
                .controlDate(DEFAULT_CONTROL_DATE)
                .sourceVersion(DEFAULT_SOURCE_VERSION)
                .operationalDate(DEFAULT_OPERATIONAL_DATE)
                .channel(DEFAULT_CHANNEL)
                .clientId(DEFAULT_CLIENT_ID)
                .datalakeDate(DEFAULT_DATALAKE_DATE).statusDate(DEFAULT_STATUS_DATE)
                .build();
    }


    public List<TouchpointNps> createListEntity(int numTouchpointPerChannel) {
        List<TouchpointNps> ret = new ArrayList<>();
        IntStream.range(1, numTouchpointPerChannel).forEach(num -> properties.getChannels().forEach(
                channel -> ret.add(TouchpointNps.builder()
                        .touchpointId(DEFAULT_TOUCHPOINT_ID)
                        .noteRecomendation(num)
                        .controlDate(new DateTime().minusDays(num).toDate())
                        .channel(channel.getTypes().get(0))
                        .clientId(DEFAULT_CLIENT_ID)
                        .datalakeDate(DEFAULT_DATALAKE_DATE)
                        .statusDate(new Date())
                        .build())
        ));

        return ret;
    }

    @Before
    public void initTest() {
        touchpointNps = createEntity(em);
        touchpointsNpsByChannel = createListEntity(20);
    }

    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void getScoresByClientIdGroupByChannelOK() throws Exception {
        // Initialize the database
        touchpointsNpsByChannel.forEach(touchpoint -> touchpointNpsRepository.saveAndFlush(touchpoint));
        
        // Get the Score by client
        MvcResult result = restTouchpointNpsMockMvc.perform(get("/api/v1/touchpoint-nps/{clientid}/channels/scores", DEFAULT_CLIENT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();
        
        List<ChannelDto> channels = (List<ChannelDto>) Util.convertJsonToObjJava(result.getResponse().getContentAsString(), List.class);
        assertThat(channels.size()).isEqualTo(properties.getChannels().size());
    }
    
    @Test
    @Transactional
    public void getScoresByClientIdGroupByChannel_ClientNoExist() throws Exception {
        // Initialize the database
        touchpointsNpsByChannel.forEach(touchpoint -> touchpointNpsRepository.saveAndFlush(touchpoint));
        
        // Get the Score by client
        restTouchpointNpsMockMvc.perform(get("/api/v1/touchpoint-nps/{clientid}/channels/scores", 0L))
                .andExpect(status().isNotFound());
    }
    
    @Test
    @Transactional
    public void getScoresByClientIdGroupByChannel_HasEmptyChannel() throws Exception {
        // Initialize the database
        touchpointsNpsByChannel.forEach(touchpoint -> {
                if (!properties.getChannels().get(0).getChannel().equals(touchpoint.getChannel())) {
                    touchpointNpsRepository.saveAndFlush(touchpoint);
                }
            });
        
        // Get the Score by client
        MvcResult result = restTouchpointNpsMockMvc.perform(get("/api/v1/touchpoint-nps/{clientid}/channels/scores", DEFAULT_CLIENT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();
        
        ObjectMapper mapper = new ObjectMapper();
        List<ChannelDto> channels = mapper.readValue(
                result.getResponse().getContentAsString(), 
                new TypeReference<List<ChannelDto>>(){}
            );
        assertThat(channels.size()).isEqualTo(properties.getChannels().size());
        
        channels.forEach(channel -> {
            if (properties.getChannels().get(0).getTypes().get(0).equals(channel.getChannel())) {
                assertThat(channel.getScores().size()).isEqualTo(1);
                assertThat(channel.getScores().get(0).getNote()).isEqualTo(ConverterScore.N_A);
                assertThat(channel.getScores().get(0).getDate()).isNull();
            } else {
                assertThat(channel.getScores().size()).isGreaterThanOrEqualTo(1);
                assertThat(channel.getScores().get(0).getDate())
                    .isAfterOrEqualsTo(new DateTime().minusYears(1).toDate());
            }
        });
    }

    private List<TouchpointNpsDTO> convertJsonToList(ResultActions actions) throws java.io.IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(actions.andReturn().getResponse().getContentAsString(),
                TypeFactory.defaultInstance().constructCollectionType(List.class, TouchpointNpsDTO.class));
    }
}