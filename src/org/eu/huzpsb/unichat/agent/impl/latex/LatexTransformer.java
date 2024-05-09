package org.eu.huzpsb.unichat.agent.impl.latex;

import org.eu.huzpsb.unichat.agent.Transformer;
import org.eu.huzpsb.unichat.conversation.Conversation;
import org.eu.huzpsb.unichat.conversation.Entry;
import org.eu.huzpsb.unichat.conversation.EntryOwner;
import org.eu.huzpsb.unichat.exceptions.TransformerException;

@SuppressWarnings("ALL")
// Mute the annoying "typo" warning. Many LaTeX commands are not in the dictionary. Stupid IDE.
public class LatexTransformer implements Transformer {
    public static final String instruction = "\nThis is a Latex file.\n\nmain.tex\n````TeX\n\\documentclass[UTF8]{ctexart}\n\\pagestyle{plain}\n\\begin{document}\n    \\begin{center}\n        \\textbf{\\huge [YOUR TITLE HERE]}\n    \\end{center}\n    \\section{[SECTION NAME]}\n    [THIS IS THE BLANK]\n    \\begingroup\n    \\bibliography{main}\n    \\bibliographystyle{plain}\n    \\endgroup\n\\end{document}\n````\n\nNow fill in the blank of the main.tex with ~1000 characters to meet the requirements above. \nGive the complete document, including the provided items. \nAdd \\cite{name0} \\cite{name1}, etc. at the end of each sentence as placeholder of citations.\nThere are 20 placeholder entries available. They are named, name0, name1, ..., name19.\nUse no less than 10 of them, and no more than 20 of them.\nUse them in the numercial order. Say, only use name3 if you've already used name2.\nSpread the cites at the end of each sentence. Do not place them all at the end of the paragraph.\nFor example, \"On the one hand, xxxx\\cite{name0}, on the other hand, xxxx\\cite{name1}. Thus, xxxx\\cite{name2}\" is good, and \"On the one hand, xxxx, on the other hand, xxxx. Thus, xxxx \\cite{name0} \\cite{name1} \\cite{name2}\" is bad.\nUse one placeholder item no more than once.\nYou don't have to do anything but giving me the generated text in the required format.\n";

    @Override
    public Conversation beforeSend(Conversation conversation) {
        TransformerException.throwIfNotAu(conversation);
        Conversation newConversation = conversation.clone();
        String pending = newConversation.entries.get(conversation.entries.size() - 1).content;
        Entry entry = new Entry(EntryOwner.USER, pending + instruction);
        newConversation.entries.set(conversation.entries.size() - 1, entry);
        return newConversation;
    }

    @Override
    public Entry afterReceive(Entry entry) {
        String pending = entry.content;
        int idxLeft = pending.indexOf("\\documentclass[UTF8]{ctexart}");
        if (idxLeft > 0) {
            pending = pending.substring(idxLeft);
        }
        int idxRight = pending.indexOf("\\end{document}");
        if (idxRight > 0) {
            idxRight += 14;
            if (idxRight < pending.length() - 1)
                pending = pending.substring(0, idxRight);
        }
        return new Entry(entry.owner, pending);
    }
}
