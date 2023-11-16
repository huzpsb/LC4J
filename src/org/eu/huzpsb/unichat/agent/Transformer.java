package org.eu.huzpsb.unichat.agent;

import org.eu.huzpsb.unichat.conversation.Conversation;
import org.eu.huzpsb.unichat.conversation.Entry;

public interface Transformer {
    Conversation beforeSend(Conversation conversation);

    Entry afterReceive(Entry entry);
}
