package org.eu.huzpsb.unichat.llm;

import org.eu.huzpsb.unichat.conversation.Conversation;
import org.eu.huzpsb.unichat.conversation.Entry;

public interface LLM {
    Entry Chat(Conversation c);
}
