package com.steveq.movieexplorer.model;


public enum Genre {
    ACTION(28),
    ADVENNTURE(12),
    ANIMATION(16),
    COMEDY(35),
    CRIME(80),
    DOCUMENTARY(99),
    DRAMA(18),
    FAMILY(10751),
    FANTASY(14),
    HISTORY(36),
    HORROR(27),
    MUSIC(10402),
    MYSTERY(9648),
    ROMANCE(10749),
    SCIENCEFICTION(878),
    TVMOVIE(10770),
    THRILLER(53),
    WAR(10752),
    WESTERN(37);

    int id;

    Genre(int id) {
        this.id = id;
    }
}
