package com.nuvoex.diesel.core;

public class Repositories {

    private Repositories() {
        // no instance
    }

    private static Repository sRepository = null;

    public synchronized static Repository getRepositoryInstance() {
        if (null == sRepository) {
            sRepository = new RepositoryImpl();
        }
        return sRepository;
    }
}