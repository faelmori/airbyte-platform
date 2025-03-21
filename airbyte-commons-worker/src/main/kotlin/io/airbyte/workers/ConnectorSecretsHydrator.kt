/*
 * Copyright (c) 2020-2025 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.workers

import com.fasterxml.jackson.databind.JsonNode
import io.airbyte.api.client.AirbyteApiClient
import io.airbyte.api.client.model.generated.ScopeType
import io.airbyte.api.client.model.generated.SecretPersistenceConfig
import io.airbyte.api.client.model.generated.SecretPersistenceConfigGetRequestBody
import io.airbyte.config.secrets.SecretsRepositoryReader
import io.airbyte.metrics.MetricClient
import io.airbyte.workers.helper.SecretPersistenceConfigHelper
import java.io.IOException
import java.lang.RuntimeException
import java.util.UUID

/**
 * Performs secrets hydration of raw JSON connector configs.
 */
class ConnectorSecretsHydrator(
  private val secretsRepositoryReader: SecretsRepositoryReader,
  private val airbyteApiClient: AirbyteApiClient,
  private val useRuntimeSecretPersistence: Boolean,
  private val metricClient: MetricClient,
) {
  fun hydrateConfig(
    jsonConfig: JsonNode,
    organizationId: UUID?,
  ): JsonNode? =
    if (useRuntimeSecretPersistence) {
      hydrateFromRuntimePersistence(jsonConfig, organizationId!!) // useRuntimeHydration null checks org id
    } else {
      // Hydrates secrets from Airbyte's secret manager.
      secretsRepositoryReader.hydrateConfigFromDefaultSecretPersistence(jsonConfig)
    }

  /**
   *  Hydrates secrets from customer's configured secret manager.
   */
  private fun hydrateFromRuntimePersistence(
    jsonConfig: JsonNode,
    organizationId: UUID,
  ): JsonNode? {
    val secretPersistenceConfig: SecretPersistenceConfig
    try {
      secretPersistenceConfig =
        airbyteApiClient.secretPersistenceConfigApi.getSecretsPersistenceConfig(
          SecretPersistenceConfigGetRequestBody(ScopeType.ORGANIZATION, organizationId),
        )
    } catch (e: IOException) {
      throw RuntimeException(e)
    }

    val runtimeSecretPersistence =
      SecretPersistenceConfigHelper
        .fromApiSecretPersistenceConfig(secretPersistenceConfig, metricClient)

    return secretsRepositoryReader.hydrateConfigFromRuntimeSecretPersistence(
      jsonConfig,
      runtimeSecretPersistence,
    )
  }
}
