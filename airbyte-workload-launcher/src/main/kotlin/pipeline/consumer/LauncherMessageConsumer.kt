/*
 * Copyright (c) 2020-2025 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.workload.launcher.pipeline.consumer

import io.airbyte.commons.temporal.queue.MessageConsumer
import io.airbyte.config.messages.LauncherInputMessage
import io.airbyte.metrics.MetricAttribute
import io.airbyte.metrics.MetricClient
import io.airbyte.metrics.OssMetricsRegistry
import io.airbyte.metrics.lib.MetricTags
import io.airbyte.workload.launcher.pipeline.LaunchPipeline
import jakarta.inject.Singleton

@Singleton
class LauncherMessageConsumer(
  private val launchPipeline: LaunchPipeline,
  private val metricClient: MetricClient,
) : MessageConsumer<LauncherInputMessage> {
  override fun consume(input: LauncherInputMessage) {
    if (input.startTimeMs != null) {
      metricClient.gauge(
        metric = OssMetricsRegistry.PRODUCER_TO_CONSUMER_LATENCY_MS,
        stateObject = System.currentTimeMillis() - input.startTimeMs!!,
        function = { it.toDouble() },
        attributes =
          arrayOf(
            MetricAttribute(MetricTags.WORKLOAD_TYPE_TAG, input.workloadType.toString()),
          ),
      )
    }

    launchPipeline.accept(
      LauncherInput(
        workloadId = input.workloadId,
        workloadInput = input.workloadInput,
        labels = input.labels,
        logPath = input.logPath,
        mutexKey = input.mutexKey,
        workloadType = input.workloadType,
        startTimeMs = input.startTimeMs,
        autoId = input.autoId,
      ),
    )
  }
}
