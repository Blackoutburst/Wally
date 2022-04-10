package com.blackoutburst.wally.core.stats;

/**
 * This class is used to serialize data using GSON
 */
public class PracticeStats {

    private final String q;
    private final String f;
    private final String l;
    private final String wq;
    private final String wf;

    public PracticeStats(String q, String f, String l, String wq, String wf) {
        this.q = q;
        this.f = f;
        this.l = l;
        this.wq = wq;
        this.wf = wf;
    }

}
