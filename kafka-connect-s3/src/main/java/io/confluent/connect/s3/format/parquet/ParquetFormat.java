/*
 * Copyright 2017 Confluent Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.confluent.connect.s3.format.parquet;

import io.confluent.connect.protobuf.ProtobufData;
import io.confluent.connect.s3.S3SinkConnectorConfig;
import io.confluent.connect.s3.storage.S3Storage;
import io.confluent.connect.storage.format.Format;
import io.confluent.connect.storage.format.RecordWriterProvider;
import io.confluent.connect.storage.format.SchemaFileReader;

public class ParquetFormat implements Format<S3SinkConnectorConfig, String> {
  private final S3Storage storage;
  private final ProtobufData protobufData;

  // DO NOT change this signature, it is required for instantiation via reflection
  public ParquetFormat(S3Storage storage) {
    this.storage = storage;
    this.protobufData = new ProtobufData();//storage.conf().protobufDataConfig());
  }

  @Override
  public RecordWriterProvider<S3SinkConnectorConfig> getRecordWriterProvider() {
    return new ParquetRecordWriterProvider(storage, protobufData);
  }

  @Override
  public SchemaFileReader<S3SinkConnectorConfig, String> getSchemaFileReader() {
    throw new UnsupportedOperationException("Reading schemas from S3 is not currently supported");
  }

  @Override
  @Deprecated
  public Object getHiveFactory() {
    throw new UnsupportedOperationException(
            "Hive integration is not currently supported in S3 Connector"
    );
  }

  public ProtobufData getProtobufData() {
    return protobufData;
  }
}
