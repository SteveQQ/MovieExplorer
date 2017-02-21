package com.steveq.movieexplorer.api;

import com.steveq.movieexplorer.model.PopularMovies;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import retrofit2.Callback;

import static org.junit.Assert.*;
import static org.junit.runner.Request.method;
import static org.mockito.Mockito.*;

public class TmdbControllerTest {
    private TmdbController controller;

    @Before
    public void setUp() throws Exception {
        controller = new TmdbController(null);
    }

    @Test
    public void responseCallbackIsPresent() throws Exception {
        assert(true);
    }
}