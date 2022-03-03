/*
 *  Copyright (c) 2020, 2021 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - initial API and implementation
 *       Fraunhofer Institute for Software and Systems Engineering
 *
 */

rootProject.name = "dataspaceconnector"

// modules for common/util code


include(":extensions:azure:blobstorage")
include(":extensions:azure:blobstorage:blob-core")
include(":extensions:azure:blobstorage:blob-provision")
include(":extensions:azure:blobstorage:blob-data-operator")
include(":extensions:azure:events")
include(":extensions:azure:events-config")
include(":extensions:azure:azure-test")
include(":extensions:azure:cosmos:transfer-process-store-cosmos")
include(":extensions:azure:cosmos:fcc-node-directory-cosmos")
include(":extensions:azure:cosmos:contract-definition-store-cosmos")
include(":extensions:azure:cosmos:contract-negotiation-store-cosmos")
include(":extensions:azure:cosmos:cosmos-common")
include(":extensions:azure:cosmos:assetindex-cosmos")
include(":extensions:azure:registration-service")
include(":extensions:azure:vault")

include(":extensions:azure:data-plane-azure-storage")
include(":extensions:data-plane:integration-tests")


include(":launchers:data-plane-server")
include(":launchers:registration-service-app")

//include(":openapi")

// numbered samples for the onboarding experience

include(":samples:05-file-transfer-cloud:consumer")
include(":samples:05-file-transfer-cloud:provider")
include(":samples:05-file-transfer-cloud:api")
include(":samples:05-file-transfer-cloud:data-seeder")
include(":samples:05-file-transfer-cloud:transfer-file")

