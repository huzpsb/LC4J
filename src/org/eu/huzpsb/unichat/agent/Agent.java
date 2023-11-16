package org.eu.huzpsb.unichat.agent;

import org.eu.huzpsb.unichat.conversation.Conversation;
import org.eu.huzpsb.unichat.conversation.Entry;
import org.eu.huzpsb.unichat.llm.LLM;

public class Agent implements LLM {
    public final LLM baseLLM;
    public final Transformer transformer;

    public Agent(LLM baseLLM, Transformer transformer) {
        this.baseLLM = baseLLM;
        this.transformer = transformer;
    }

    @Override
    public Entry Chat(Conversation c) {
        return transformer.afterReceive(baseLLM.Chat(transformer.beforeSend(c)));
    }
}
