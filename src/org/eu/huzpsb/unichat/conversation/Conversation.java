package org.eu.huzpsb.unichat.conversation;

import java.util.ArrayList;
import java.util.List;

public class Conversation {
    public List<Entry> entries = new ArrayList<>();

    @SuppressWarnings("ALL")
    @Override
    public Conversation clone() {
        // 1, We don't need to clone entries, because they are immutable.
        // 2, We don't need to call super.clone(), because it's an empty method.
        Conversation conversation = new Conversation();
        conversation.entries.addAll(entries);
        return conversation;
    }
}
