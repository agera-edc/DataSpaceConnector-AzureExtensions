/*
 *  Copyright (c) 2022 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - initial API and implementation
 *
 */
package org.eclipse.dataspaceconnector.azure.dataplane.azuredatafactory.pipeline;

import org.eclipse.dataspaceconnector.dataplane.spi.pipeline.TransferService;
import org.eclipse.dataspaceconnector.dataplane.spi.result.TransferResult;
import org.eclipse.dataspaceconnector.spi.result.Result;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.DataFlowRequest;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class AzureDataFactoryTransferService implements TransferService {


    @Override
    public boolean canHandle(DataFlowRequest dataFlowRequest) {
        return false;
    }

    @Override
    public @NotNull Result<Boolean> validate(DataFlowRequest dataFlowRequest) {
        return null;
    }

    @Override
    public @NotNull CompletableFuture<TransferResult> transfer(DataFlowRequest dataFlowRequest) {
        return null;
    }
}