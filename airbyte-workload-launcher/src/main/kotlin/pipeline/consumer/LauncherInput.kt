/*
 * Copyright (c) 2020-2025 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.workload.launcher.pipeline.consumer

import io.airbyte.config.WorkloadType
import java.util.UUID

data class LauncherInput(
  val workloadId: String,
  val workloadInput: String,
  val labels: Map<String, String>,
  val logPath: String,
  val mutexKey: String?,
  val workloadType: WorkloadType,
  val startTimeMs: Long? = null,
  val autoId: UUID,
)
