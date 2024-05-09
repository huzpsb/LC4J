package org.eu.huzpsb.unichat.llm.impl;

import nano.http.d2.json.NanoJSON;
import nano.http.d2.utils.WebSocketClient;
import org.eu.huzpsb.unichat.conversation.Conversation;
import org.eu.huzpsb.unichat.conversation.Entry;
import org.eu.huzpsb.unichat.conversation.EntryOwner;
import org.eu.huzpsb.unichat.credential.Credential;
import org.eu.huzpsb.unichat.credential.CredentialType;
import org.eu.huzpsb.unichat.credential.manager.CredentialManager;
import org.eu.huzpsb.unichat.exceptions.CredentialMismatchException;
import org.eu.huzpsb.unichat.llm.LLM;

public class XFei implements LLM {
    public final CredentialManager credentialManager;

    public XFei(CredentialManager credentialManager) {
        this.credentialManager = credentialManager;
    }

    @Override
    public Entry Chat(Conversation c) {
        Credential credential = credentialManager.getCredential();
        if (credential.type != CredentialType.EP_AID) {
            throw new CredentialMismatchException("Credential type mismatch for XFei.");
        }
        try {
            WebSocketClient wsc = new WebSocketClient(credential.value);
            StringBuilder sb = new StringBuilder("{\"header\":{\"app_id\":\"");
            sb.append(credential.additional);
            sb.append("\"},\"parameter\":{\"chat\":{\"domain\":\"generalv3\",\"temperature\":0.5,\"max_tokens\":1024}},\"payload\":{\"message\":{\"text\":[");
            for (Entry e : c.entries) {
                String owner = null;
                switch (e.owner) {
                    case SYSTEM:
                    case USER:
                        owner = "user";
                        break;
                    case BOT:
                        owner = "assistant";
                        break;
                }
                sb.append("{\"role\":\"");
                sb.append(owner);
                sb.append("\",\"content\":\"");
                sb.append(e.content);
                sb.append("\"},");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]}}}");
            wsc.send(sb.toString());
            StringBuilder result = new StringBuilder();
            while (true) {
                String tmp_r = wsc.read();
                if (tmp_r != null) {
                    NanoJSON json = new NanoJSON(tmp_r);
                    result.append(json.getJSONObject("payload").getJSONObject("choices").getJSONArray("text").getJSONObject(0).getString("content"));
                } else {
                    return new Entry(EntryOwner.BOT, result.toString());
                }
            }
        } catch (Exception ex) {
            return new Entry(EntryOwner.BOT, "出错了");
        }
    }
}
