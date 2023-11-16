package org.eu.huzpsb.unichat.debug;

import org.eu.huzpsb.unichat.agent.Agent;
import org.eu.huzpsb.unichat.agent.Transformer;
import org.eu.huzpsb.unichat.agent.impl.latex.LatexTransformer;
import org.eu.huzpsb.unichat.conversation.Conversation;
import org.eu.huzpsb.unichat.conversation.Entry;
import org.eu.huzpsb.unichat.llm.LLM;

public class Main {
    public static void main(String[] args) {
        LLM llm = DebugUtils.getDebugLLM();
        Conversation conversation = DebugUtils.getSingletonConversation("写一篇关于5G网络的文章。中文。");
        Transformer transformer = new LatexTransformer();

        Agent agent = new Agent(llm, transformer);

        Entry result = agent.Chat(conversation);
        System.out.println(result.content);
    }
}
