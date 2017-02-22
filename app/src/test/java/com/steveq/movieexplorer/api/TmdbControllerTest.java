package com.steveq.movieexplorer.api;

import org.junit.Before;
import org.junit.Test;

public class TmdbControllerTest {
    private TmdbService controller;

    @Before
    public void setUp() throws Exception {
        controller = new TmdbService(null);
    }

    @Test
    public void responseCallbackIsPresent() throws Exception {
        assert(true);
    }
}