package org.eu.huzpsb.unichat;

import org.eu.huzpsb.unichat.agent.Agent;
import org.eu.huzpsb.unichat.agent.impl.latex.LatexTransformer;
import org.eu.huzpsb.unichat.conversation.Conversation;
import org.eu.huzpsb.unichat.conversation.Entry;
import org.eu.huzpsb.unichat.conversation.EntryOwner;
import org.eu.huzpsb.unichat.credential.Credential;
import org.eu.huzpsb.unichat.credential.CredentialType;
import org.eu.huzpsb.unichat.credential.manager.CredentialManager;
import org.eu.huzpsb.unichat.credential.manager.SimpleCredentialManager;
import org.eu.huzpsb.unichat.llm.LLM;
import org.eu.huzpsb.unichat.llm.impl.ChatGPT;

public class Sample {
    public static void main(String[] args) {
        Credential credential = new Credential(CredentialType.TOKEN, "sk-1234567890");
        CredentialManager credentialManager = new SimpleCredentialManager(credential);
        LLM llm = new ChatGPT(credentialManager);
        Agent agent = new Agent(llm, new LatexTransformer());
        Conversation conversation = new Conversation();
        Entry entry = new Entry(EntryOwner.USER, "写一篇关于5G网络的文章。");
        conversation.entries.add(entry);
        Entry result = agent.Chat(conversation);
        System.out.println(result.content);
    }
}
