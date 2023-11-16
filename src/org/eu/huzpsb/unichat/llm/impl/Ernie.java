package org.eu.huzpsb.unichat.llm.impl;

import nano.http.d2.json.NanoJSON;
import nano.http.d2.utils.Request;
import org.eu.huzpsb.unichat.conversation.Conversation;
import org.eu.huzpsb.unichat.conversation.Entry;
import org.eu.huzpsb.unichat.conversation.EntryOwner;
import org.eu.huzpsb.unichat.credential.Credential;
import org.eu.huzpsb.unichat.credential.CredentialType;
import org.eu.huzpsb.unichat.credential.manager.CredentialManager;
import org.eu.huzpsb.unichat.llm.LLM;

public class Ernie implements LLM {
    public final CredentialManager credentialManager;

    public Ernie(CredentialManager credentialManager) {
        this.credentialManager = credentialManager;
    }

    @Override
    public Entry Chat(Conversation c) {
        try {
            Credential credential = credentialManager.getCredential();
            String ak = credential.getValueIfType(CredentialType.AK_SK);
            String sk = credential.additional;
            String tk_ep = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=" + ak + "&client_secret=" + sk;
            NanoJSON json = new NanoJSON(Request.jsonPost(tk_ep, "", null));
            String token = json.getString("access_token");
            String endpoint = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions?access_token=" + token;
            StringBuilder sb = new StringBuilder("{\"messages\":[");
            for (Entry e : c.entries) {
                switch (e.owner) {
                    case USER:
                        sb.append("{\"role\":\"assistant\",\"content\":\"").append(e.content).append("\"},");
                        break;
                    case BOT:
                        sb.append("{\"role\":\"user\",\"content\":\"").append(e.content).append("\"},");
                        break;
                    default:
                        break;
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]}");
            json = new NanoJSON(Request.jsonPost(endpoint, sb.toString(), null));
            return new Entry(EntryOwner.BOT, json.getString("result"));
        } catch (Exception e) {
            return new Entry(EntryOwner.BOT, "出错了！");
        }
    }
}
