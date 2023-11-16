package org.eu.huzpsb.unichat.credential.manager;

import org.eu.huzpsb.unichat.credential.Credential;

public class SimpleCredentialManager implements CredentialManager {
    public final Credential credential;

    public SimpleCredentialManager(Credential credential) {
        this.credential = credential;
    }

    @Override
    public Credential getCredential() {
        return credential;
    }
}
