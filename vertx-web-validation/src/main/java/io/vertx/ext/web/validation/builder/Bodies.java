package io.vertx.ext.web.validation.builder;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.ext.web.validation.impl.ValueParserInferenceUtils;
import io.vertx.ext.web.validation.impl.body.FormBodyProcessorImpl;
import io.vertx.ext.web.validation.impl.body.JsonBodyProcessorImpl;
import io.vertx.ext.web.validation.impl.body.TextPlainBodyProcessorImpl;
import io.vertx.ext.web.validation.impl.validator.SchemaValidator;
import io.vertx.json.schema.Schema;
import io.vertx.json.schema.common.dsl.ObjectSchemaBuilder;
import io.vertx.json.schema.common.dsl.SchemaBuilder;
import io.vertx.json.schema.common.dsl.StringSchemaBuilder;
import io.vertx.json.schema.validator.Draft;
import io.vertx.json.schema.validator.Validator;
import io.vertx.json.schema.validator.ValidatorOptions;

/**
 * In this interface you can find all available {@link BodyProcessorFactory} to use in {@link ValidationHandlerBuilder}. <br/>
 *
 * To create new schemas using {@link SchemaBuilder}, look at the <a href="https://vertx.io/docs/vertx-json-schema/java/">docs of vertx-json-schema</a>
 */
@VertxGen
public interface Bodies {

  /**
   * Create a json body processor
   *
   * @param schemaBuilder
   * @return
   */
  /**
   * @TODO: leaky abstraction it relies on API internals as public API breaking the codegen contract
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  static BodyProcessorFactory json(SchemaBuilder schemaBuilder) {
    return parser -> new JsonBodyProcessorImpl(new SchemaValidator(
      Validator.create(io.vertx.json.schema.validator.Schema.of(schemaBuilder.toJson()), new ValidatorOptions().setDraft(Draft.DRAFT7).setBaseUri("app://"))));
  }

  /**
   * Create a {@code text/plain} body processor
   *
   * @param schemaBuilder
   * @return
   */
  /**
   * @TODO: leaky abstraction it relies on API internals as public API breaking the codegen contract
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  static BodyProcessorFactory textPlain(StringSchemaBuilder schemaBuilder) {
    return parser -> new TextPlainBodyProcessorImpl(new SchemaValidator(
      Validator.create(io.vertx.json.schema.validator.Schema.of(schemaBuilder.toJson()), new ValidatorOptions().setDraft(Draft.DRAFT7).setBaseUri("app://"))));
  }

  /**
   * Create a form {@code application/x-www-form-urlencoded} processor
   *
   * @param schemaBuilder
   * @return
   */
  /**
   * @TODO: leaky abstraction it relies on API internals as public API breaking the codegen contract
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  static BodyProcessorFactory formUrlEncoded(ObjectSchemaBuilder schemaBuilder) {
    return parser -> {
      Object jsonSchema = schemaBuilder.toJson();
      return new FormBodyProcessorImpl(
        ValueParserInferenceUtils.infeerPropertiesFormValueParserForObjectSchema(jsonSchema),
        ValueParserInferenceUtils.infeerPatternPropertiesFormValueParserForObjectSchema(jsonSchema),
        ValueParserInferenceUtils.infeerAdditionalPropertiesFormValueParserForObjectSchema(jsonSchema),
        "application/x-www-form-urlencoded",
        new SchemaValidator(
          Validator.create(io.vertx.json.schema.validator.Schema.of(schemaBuilder.toJson()), new ValidatorOptions().setDraft(Draft.DRAFT7).setBaseUri("app://")))
      );
    };
  }

  /**
   * Create a form {@code multipart/form-data} processor
   *
   * @param schemaBuilder
   * @return
   */
  /**
   * @TODO: leaky abstraction it relies on API internals as public API breaking the codegen contract
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  static BodyProcessorFactory multipartFormData(ObjectSchemaBuilder schemaBuilder) {
    return parser -> {
      Object jsonSchema = schemaBuilder.toJson();
      return new FormBodyProcessorImpl(
        ValueParserInferenceUtils.infeerPropertiesFormValueParserForObjectSchema(jsonSchema),
        ValueParserInferenceUtils.infeerPatternPropertiesFormValueParserForObjectSchema(jsonSchema),
        ValueParserInferenceUtils.infeerAdditionalPropertiesFormValueParserForObjectSchema(jsonSchema),
        "multipart/form-data",
        new SchemaValidator(
          Validator.create(io.vertx.json.schema.validator.Schema.of(schemaBuilder.toJson()), new ValidatorOptions().setDraft(Draft.DRAFT7).setBaseUri("app://")))
      );
    };
  }
}
