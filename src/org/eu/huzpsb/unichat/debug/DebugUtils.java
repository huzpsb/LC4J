package org.eu.huzpsb.unichat.debug;

import org.eu.huzpsb.unichat.conversation.Conversation;
import org.eu.huzpsb.unichat.conversation.Entry;
import org.eu.huzpsb.unichat.conversation.EntryOwner;
import org.eu.huzpsb.unichat.credential.Credential;
import org.eu.huzpsb.unichat.credential.CredentialType;
import org.eu.huzpsb.unichat.credential.manager.CredentialManager;
import org.eu.huzpsb.unichat.credential.manager.SimpleCredentialManager;
import org.eu.huzpsb.unichat.llm.LLM;

public class DebugUtils {
    public static LLM getDebugLLM() {
        Credential credential = new Credential(CredentialType.TOKEN, "sk-KCorlQGERPQjwtz4692fDb16Db82438987570dAf1aA9E313");
        CredentialManager credentialManager = new SimpleCredentialManager(credential);
        return new DebugGPTImpl(credentialManager);
    }

    public static Conversation getSingletonConversation(String input) {
        Conversation conversation = new Conversation();
        Entry entry = new Entry(EntryOwner.USER, input);
        conversation.entries.add(entry);
        return conversation;
    }
}
