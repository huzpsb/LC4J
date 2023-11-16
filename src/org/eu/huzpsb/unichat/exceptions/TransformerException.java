package org.eu.huzpsb.unichat.exceptions;

import org.eu.huzpsb.unichat.conversation.Conversation;
import org.eu.huzpsb.unichat.conversation.Entry;
import org.eu.huzpsb.unichat.conversation.EntryOwner;

public class TransformerException extends RuntimeException {
    public TransformerException(String message) {
        super(message);
    }

    public static void throwIfNotAu(Conversation conversation) {
        switch (conversation.entries.size()) {
            case 0:
                throw new TransformerException("Conversation has no entries");
            case 1:
                Entry entry = conversation.entries.get(0);
                if (entry.owner != EntryOwner.USER) {
                    throw new TransformerException("Conversation has only one non-user entry");
                }
                break;
            default:
                int size = conversation.entries.size();
                Entry first = conversation.entries.get(size - 1);
                Entry second = conversation.entries.get(size - 2);
                if (first.owner != EntryOwner.USER || second.owner == EntryOwner.USER) {
                    throw new TransformerException("Conversation doesn't end with Au pattern");
                }
        }
    }
}
