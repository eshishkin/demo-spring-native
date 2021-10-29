package org.eshishkin.edu.demospringnative.web;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/maintenance")
public class MaintenanceController {

    @PostMapping("/heap-dump")
    public void createHeapDump() {
        dump();
    }


    @SneakyThrows
    private void dump() {
//        File file = File.createTempFile("SVMHeapDump-", ".hprof");
//        VMRuntime.dumpHeap(file.getAbsolutePath(), true);
//        log.info("Heap dump created " + file.getAbsolutePath() + ", size: " + file.length());
    }
}
