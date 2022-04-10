package com.blackoutburst.wally.core.stats;

import javax.annotation.Nonnull;

/**
 * This class is used to serialize data using GSON
 */
public class PracticeStats {

    private final String q;
    private final String f;
    private final String l;
    private final String wq;
    private final String wf;

    public PracticeStats(@Nonnull final String q, @Nonnull final String f, @Nonnull final String l, @Nonnull final String wq, @Nonnull final String wf) {
        this.q = q;
        this.f = f;
        this.l = l;
        this.wq = wq;
        this.wf = wf;
    }

    public String getQ() {
        return q;
    }

    public String getF() {
        return f;
    }

    public String getL() {
        return l;
    }

    public String getWq() {
        return wq;
    }

    public String getWf() {
        return wf;
    }
}
