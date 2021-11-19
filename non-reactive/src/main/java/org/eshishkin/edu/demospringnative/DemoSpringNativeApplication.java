package org.eshishkin.edu.demospringnative;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoSpringNativeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringNativeApplication.class, args);
	}

	@Bean
	public FileDescriptorMetrics fileDescriptorMetrics() {
		return new FileDescriptorMetrics() {
			@Override
			public void bindTo(MeterRegistry registry) {
				// do not collect and publish metrics because it takes around 700 ms to get
				// process.files.open metric in native image
			}
		};

	}
}
