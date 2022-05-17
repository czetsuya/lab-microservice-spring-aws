package com.czetsuyatech.applicants.configs;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.AWSXRayRecorder;
import com.amazonaws.xray.AWSXRayRecorderBuilder;
import com.amazonaws.xray.plugins.EC2Plugin;
import com.amazonaws.xray.plugins.ECSPlugin;
import com.amazonaws.xray.slf4j.SLF4JSegmentListener;
import com.amazonaws.xray.strategy.ContextMissingStrategy;
import com.amazonaws.xray.strategy.LogErrorContextMissingStrategy;
import com.amazonaws.xray.strategy.RuntimeErrorContextMissingStrategy;
import com.amazonaws.xray.strategy.sampling.AllSamplingStrategy;
import com.amazonaws.xray.strategy.sampling.CentralizedSamplingStrategy;
import com.amazonaws.xray.strategy.sampling.SamplingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalXrayEnabled
public class XRayConfiguration {

  public static final String AWS_XRAY_SAMPLE_ALL = "AWS_XRAY_SAMPLE_ALL";
  public static final String AWS_XRAY_CONTEXT_MISSING = "AWS_XRAY_CONTEXT_MISSING";

  @Bean
  public AWSXRayRecorder awsXRayRecorder() {

    log.info("start loading AWSXRayRecorder...");

    AWSXRayRecorderBuilder builder =
        AWSXRayRecorderBuilder.standard();

    builder
        .withPlugin(new EC2Plugin())
        .withPlugin(new ECSPlugin());

    final SamplingStrategy samplingStrategy = getSamplingStrategy();
    builder.withSamplingStrategy(samplingStrategy);

    final ContextMissingStrategy contextMissingStrategy = getContextMissingStrategy();
    builder.withContextMissingStrategy(contextMissingStrategy);
    builder.withSegmentListener(new SLF4JSegmentListener());

    AWSXRayRecorder build = builder.build();
    AWSXRay.setGlobalRecorder(build);

    log.info("end loading AWSXRayRecorder...");

    return build;
  }

  private SamplingStrategy getSamplingStrategy() {

    final String sampleAll = System.getenv(AWS_XRAY_SAMPLE_ALL);

    if ("true".equals(sampleAll)) {
      log.info("sampling strategy: All");
      return new AllSamplingStrategy();

    } else {
      log.info("sampling strategy: Centralized");
      return new CentralizedSamplingStrategy();
    }
  }

  private ContextMissingStrategy getContextMissingStrategy() {

    final String contextMissing = System.getenv(AWS_XRAY_CONTEXT_MISSING);

    log.info("context missing strategy: ");
    if (null == contextMissing) {
      log.info(LogErrorContextMissingStrategy.OVERRIDE_VALUE);
      return new LogErrorContextMissingStrategy();
    }

    switch (contextMissing) {
      case RuntimeErrorContextMissingStrategy.OVERRIDE_VALUE:
        log.info(RuntimeErrorContextMissingStrategy.OVERRIDE_VALUE);
        return new RuntimeErrorContextMissingStrategy();

      case IgnoreErrorContextMissingStrategy.OVERRIDE_VALUE:
        log.info(IgnoreErrorContextMissingStrategy.OVERRIDE_VALUE);
        return new IgnoreErrorContextMissingStrategy();

      case LogErrorContextMissingStrategy.OVERRIDE_VALUE:
      default:
        log.info(LogErrorContextMissingStrategy.OVERRIDE_VALUE);
        return new LogErrorContextMissingStrategy();
    }
  }

  private static final class IgnoreErrorContextMissingStrategy implements ContextMissingStrategy {

    public static final String OVERRIDE_VALUE = "IGNORE_ERROR";

    @Override
    public void contextMissing(String message, Class<? extends RuntimeException> exceptionClass) {
    }
  }
}