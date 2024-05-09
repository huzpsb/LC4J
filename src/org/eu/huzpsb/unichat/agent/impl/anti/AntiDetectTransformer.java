package org.eu.huzpsb.unichat.agent.impl.anti;

import org.eu.huzpsb.unichat.agent.Transformer;
import org.eu.huzpsb.unichat.conversation.Conversation;
import org.eu.huzpsb.unichat.conversation.Entry;
import org.eu.huzpsb.unichat.exceptions.TransformerException;

public class AntiDetectTransformer implements Transformer {
    private static final String prompt = "请写一篇以“[X]”为题的文章，不要用Markdown，用“首先-其次-再次-综上所述”";

    @Override
    public Conversation beforeSend(Conversation conversation) {
        TransformerException.throwIfNotAu(conversation);
        Entry entry = conversation.entries.get(conversation.entries.size() - 1);
        Entry newEntry = new Entry(entry.owner, prompt.replace("[X]", entry.content));
        conversation.entries.set(conversation.entries.size() - 1, newEntry);
        return conversation;
    }

    @Override
    public Entry afterReceive(Entry entry) {
        try {
            String now = ">" + entry.content;
            StringBuilder sb = new StringBuilder();
            String[] split = entry.content.split("首先，");
            if (split.length != 2) {
                throw new Exception();
            }
            split = split[1].split("其次，");
            if (split.length != 2) {
                throw new Exception();
            }
            sb.append(split[0].replace("\r", "").replace("\n", ""));
            split = split[1].split("再次，");
            if (split.length != 2) {
                throw new Exception();
            }
            sb.append(split[0].replace("\r", "").replace("\n", ""));
            split = split[1].split("综上所述，");
            if (split.length != 2) {
                throw new Exception();
            }
            sb.append(split[0].replace("\r", "").replace("\n", ""));
            sb.append(split[1].replace("\r", "").replace("\n", ""));
            return new Entry(entry.owner, sb.toString());
        } catch (Exception e) {
            return new Entry(entry.owner, "后处理失败！为了安全（？）起见，已经将模型的输出清空。");
        }
    }
}
