package io.gridgo.framework;

import io.gridgo.framework.support.MessageConstants;

public abstract class NonameComponentLifecycle extends AbstractComponentLifecycle {

    @Override
    protected String generateName() {
        return MessageConstants.NO_NAMED;
    }
}
