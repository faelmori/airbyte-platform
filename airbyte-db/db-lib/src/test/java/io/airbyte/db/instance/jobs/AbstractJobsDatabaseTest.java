/*
 * Copyright (c) 2020-2025 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.db.instance.jobs;

import io.airbyte.db.Database;
import io.airbyte.db.init.DatabaseInitializationException;
import io.airbyte.db.instance.test.TestDatabaseProviders;
import io.airbyte.test.utils.AbstractDatabaseTest;
import java.io.IOException;
import javax.sql.DataSource;
import org.jooq.DSLContext;

public abstract class AbstractJobsDatabaseTest extends AbstractDatabaseTest {

  @Override
  public Database getDatabase(final DataSource dataSource, final DSLContext dslContext) throws IOException, DatabaseInitializationException {
    return new TestDatabaseProviders(dataSource, dslContext).turnOffMigration().createNewJobsDatabase();
  }

}
