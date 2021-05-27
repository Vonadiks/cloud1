package nio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NioClasses {

    public static void main(String[] args) {
        // Path, Paths, Files
        NioClasses classes = new NioClasses();
//        classes.testPath("1.txt");
        //classes.testWatcher();
//        classes.testWrite();
        classes.navigate();
    }

    @SneakyThrows
    private void testPath(String fileName) {
        Path path = Paths.get(getClass().getResource(fileName).toURI());
        path = Paths.get("src", "main", "resources", "nio", "1.txt");
        byte[] bytes = Files.readAllBytes(path);
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }

    @SneakyThrows
    private void testWrite() {
        Path path = Paths.get("clientDir", "2.txt");
        Files.write(
                path,
                "New text".getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.APPEND
        );
    }

    @SneakyThrows
    private void navigate() {
        Path dir = Paths.get("clientDir");
        Files.walkFileTree(dir,
                new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        log.debug("visited file: {}", file);
                        return super.visitFile(file, attrs);
                    }
                });
        Files.walk(dir).forEach(System.out::println);
    }

    @SneakyThrows
    private void testWatcher() {
        Path dir = Paths.get("clientDir");
        WatchService service = FileSystems.getDefault().newWatchService();
        new Thread(() -> {
            try {
                while (true) {
                    log.debug("wait events...");
                    WatchKey take = service.take();
                    List<WatchEvent<?>> watchEvents = take.pollEvents();
                    for (WatchEvent<?> watchEvent : watchEvents) {
                        log.debug("event {}, file {}", watchEvent.kind(), watchEvent.context());
                    }
                    take.reset();
                }
            } catch (Exception e) {
                log.error("e=", e);
            }
        }).start();
        dir.register(service,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);
    }
}