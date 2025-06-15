package com.hsbc.techtest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** The type Location info. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LocationInfo(String name) {}
