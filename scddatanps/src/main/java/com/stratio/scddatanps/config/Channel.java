package com.stratio.scddatanps.config;

import lombok.Data;

import java.util.List;

/**
 * Created by ogomez on 8/06/17.
 */
@Data
public class Channel{
    private String channel;
    private List<String> types;
}
