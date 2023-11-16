package org.eu.huzpsb.unichat.llm.impl;

import nano.http.d2.json.NanoJSON;
import nano.http.d2.utils.Request;
import org.eu.huzpsb.unichat.conversation.Conversation;
import org.eu.huzpsb.unichat.conversation.Entry;
import org.eu.huzpsb.unichat.conversation.EntryOwner;
import org.eu.huzpsb.unichat.credential.CredentialType;
import org.eu.huzpsb.unichat.credential.manager.CredentialManager;
import org.eu.huzpsb.unichat.llm.LLM;
import org.eu.huzpsb.unichat.utils.JsonUtils;

import java.util.Properties;

public class Qwen implements LLM {
    public static final String endpoint = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";
    public final CredentialManager credentialManager;

    public Qwen(CredentialManager credentialManager) {
        this.credentialManager = credentialManager;
    }

    @Override
    public Entry Chat(Conversation c) {
        StringBuilder sb = new StringBuilder("{\"model\":\"qwen-turbo\",\"input\":{\"messages\":[");
        for (Entry e : c.entries) {
            String owner = null;
            switch (e.owner) {
                case USER:
                    owner = "user";
                    break;
                case BOT:
                    owner = "assistant";
                    break;
                case SYSTEM:
                    owner = "system";
                    break;
            }
            sb.append("{\"role\":\"");
            sb.append(owner);
            sb.append("\",\"content\":\"");
            sb.append(JsonUtils.escape(e.content));
            sb.append("\"},");
        }
        String token = credentialManager.getCredential().getValueIfType(CredentialType.TOKEN);
        Properties properties = new Properties();
        properties.setProperty("Authorization", "Bearer " + token);
        try {
            NanoJSON obj = new NanoJSON(Request.jsonPost(endpoint, sb.substring(0, sb.length() - 1) + "]},\"parameters\":{}}", properties));
            String result = obj.getJSONObject("output").getString("text");
            return new Entry(EntryOwner.BOT, result);
        } catch (Exception ex) {
            return new Entry(EntryOwner.BOT, "出错了");
        }
    }
}
