package org.eu.huzpsb.unichat.credential;

import org.eu.huzpsb.unichat.exceptions.CredentialMismatchException;

public class Credential {
    public final CredentialType type;
    public final String value;
    public final String additional;

    public Credential(CredentialType type, String value) {
        this.type = type;
        this.value = value;
        this.additional = null;
    }

    public Credential(CredentialType type, String value, String additional) {
        this.type = type;
        this.value = value;
        this.additional = additional;
    }

    public String getValueIfType(CredentialType type) {
        if (this.type == type) {
            return this.value;
        } else {
            throw new CredentialMismatchException("Credential type mismatch. (Expected: " + type + ", Actual: " + this.type + ")");
        }
    }
}
