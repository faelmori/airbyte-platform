/*
 * Copyright (c) 2020-2025 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.workload.launcher

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
  build(*args).deduceCloudEnvironment(false).deduceEnvironment(false).start()
}
