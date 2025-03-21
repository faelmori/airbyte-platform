/*
 * Copyright (c) 2020-2025 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.analytics

import io.airbyte.api.client.model.generated.DeploymentMetadataRead
import io.airbyte.commons.version.AirbyteVersion
import io.airbyte.config.Configs
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class DeploymentFetcherTest {
  private val airbyteVersion = AirbyteVersion("dev")
  private val deploymentId = UUID.randomUUID()
  private lateinit var deploymentMetadata: DeploymentMetadataRead
  private lateinit var deploymentFetcher: DeploymentFetcher

  @BeforeEach
  fun setup() {
    deploymentMetadata =
      DeploymentMetadataRead(
        id = deploymentId,
        mode = Configs.AirbyteEdition.COMMUNITY.name,
        version = airbyteVersion.serialize(),
      )
    deploymentFetcher = DeploymentFetcher { deploymentMetadata }
  }

  @Test
  fun testRetrievingDeploymentMetadata() {
    val deployment = deploymentFetcher.get()
    assertEquals(deploymentMetadata.id, deployment.getDeploymentId())
    assertEquals(deploymentMetadata.mode, deployment.getDeploymentMode())
    assertEquals(deploymentMetadata.version, deployment.getDeploymentVersion())
  }
}
