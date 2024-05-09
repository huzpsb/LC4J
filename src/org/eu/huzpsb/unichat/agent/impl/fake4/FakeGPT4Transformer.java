package org.eu.huzpsb.unichat.agent.impl.fake4;

import org.eu.huzpsb.unichat.agent.Transformer;
import org.eu.huzpsb.unichat.conversation.Conversation;
import org.eu.huzpsb.unichat.conversation.Entry;
import org.eu.huzpsb.unichat.conversation.EntryOwner;
import org.eu.huzpsb.unichat.exceptions.TransformerException;

public class FakeGPT4Transformer implements Transformer {
    private static String getHint(String prompt) {
        StringBuilder sb = new StringBuilder();
        if (prompt.contains("鲁迅") && prompt.contains("周树人")) {
            sb.append("Answer in Chinese. Given that Lu Xun(鲁迅) is Zhou Shuren(周树人). Examine the following statement:");
        }
        if (prompt.toLowerCase().contains("shuren")) {
            sb.append("Given that Lu Xun is Zhou Shuren. ");
        }
        if (prompt.contains("鸟") && prompt.contains("打")) {
            sb.append("Answer in Chinese. Given that all of the birds on the tree fly away when any of them is disturbed. Then examine the following situation:");
        }
        if (prompt.contains("birds")) {
            sb.append("Given that birds will fly away when disturbed. ");
        }
        if (prompt.contains("昨天") && prompt.contains("今天") && prompt.contains("明天")) {
            sb.append("已知昨天的今天的明天就是今天。");
        }
        if (prompt.contains("yesterday") && prompt.contains("today") && prompt.contains("tomorrow")) {
            sb.append("Given that yesterday's today's tomorrow is today. ");
        }
        return sb.toString();
    }

    @Override
    public Conversation beforeSend(Conversation conversation) {
        TransformerException.throwIfNotAu(conversation);
        String prompt = conversation.entries.get(conversation.entries.size() - 1).content;
        String hint = getHint(prompt);
        if (hint.length() > 3) {
            Entry entry = new Entry(EntryOwner.USER, hint);
            conversation.entries.add(entry);
        }
        return conversation;
    }

    @Override
    public Entry afterReceive(Entry entry) {
        return entry;
    }
}
