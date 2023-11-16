package org.eu.huzpsb.unichat.agent.impl.c4j.search;

public class PendingResult {
    public int index;
    public int weight;
    public String title;
    public String content;

    public PendingResult(int index, int weight) {
        this.index = index;
        this.weight = weight;
    }

    public String asPrompt() {
        return String.format("%s %s\n", content, title);
    }
}
