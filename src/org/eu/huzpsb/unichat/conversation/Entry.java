package org.eu.huzpsb.unichat.conversation;

public class Entry {
    public final EntryOwner owner;
    public final String content;

    public Entry(EntryOwner owner, String content) {
        this.owner = owner;
        this.content = content;
    }
}
