package org.eshishkin.edu.demospringnative.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/maintenance")
public class MaintenanceController {

    @GetMapping("/jmx")
    public Mono<String> jmx() {
        return Mono.fromCallable(() -> {
            OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            return new StringBuilder()
                    .append("Processors: ").append(operatingSystemMXBean.getAvailableProcessors()).append("\n")
                    .append("Name: ").append(operatingSystemMXBean.getName()).append("\n")
                    .append("Arch: ").append(operatingSystemMXBean.getArch()).append("\n")
                    .append("Version: ").append(operatingSystemMXBean.getVersion()).append("\n")
                    .append("Class: ").append(operatingSystemMXBean.getClass()).append("\n")
                    .append("\n")
                    .append("Heap Usage: ").append(memoryMXBean != null ? memoryMXBean.getHeapMemoryUsage() : null).append("\n")
                    .append("Nonheap Usage: ").append(memoryMXBean != null ? memoryMXBean.getNonHeapMemoryUsage() : null).append("\n")
                    .toString();
        });
    }

}
