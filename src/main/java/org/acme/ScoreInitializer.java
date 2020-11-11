package org.acme;

import org.infinispan.protostream.SerializationContextInitializer;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

@AutoProtoSchemaBuilder(
      includeClasses = Score.class,
      schemaFileName = "score.proto", 
      schemaFilePath = "proto/", 
      schemaPackageName = "org_acme")
interface ScoreInitializer extends SerializationContextInitializer {
}
