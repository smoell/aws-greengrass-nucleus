/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.aws.greengrass.lifecyclemanager;

import com.aws.greengrass.config.Topics;
import com.aws.greengrass.lifecyclemanager.exceptions.ServiceLoadException;

import java.util.Map;

import static com.aws.greengrass.deployment.bootstrap.BootstrapSuccessCode.REQUEST_RESTART;

public class BrokenService extends GreengrassService {
    ServiceLoadException serviceLoadException;

    public BrokenService(Topics topics, ServiceLoadException e) {
        super(topics);
        this.serviceLoadException = e;
    }

    @Override
    protected void install() {
        serviceErrored(serviceLoadException);
    }

    @Override
    public boolean isBootstrapRequired(Map<String, Object> newServiceConfig) {
        logger.atInfo().log("Bootstrap is required: current service broken", serviceLoadException);
        return true;
    }

    @Override
    public int bootstrap() {
        return REQUEST_RESTART;
    }
}
