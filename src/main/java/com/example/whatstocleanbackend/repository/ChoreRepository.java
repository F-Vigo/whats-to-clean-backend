package com.example.whatstocleanbackend.repository;

import com.example.whatstocleanbackend.Utils;
import com.example.whatstocleanbackend.domain.chore.Chore;
import com.example.whatstocleanbackend.domain.chore.Room;
import com.example.whatstocleanbackend.domain.chore.Section;
import com.example.whatstocleanbackend.domain.chore.periodicity.Periodicity;
import com.example.whatstocleanbackend.exception.IndexException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ChoreRepository {
    @Value
    @FieldDefaults(level = AccessLevel.PRIVATE)
    private static class ParsedLine {
        String roomName;
        String sectionName;
        String description;
        String periodicityName;
    }

    public void createFile(Integer index) {
        try {
            new File(String.format("store/%d.txt", index)).createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Chore> getAll(Integer index) {

        try (Scanner scanner = new Scanner(getFile(index))) {
            return getAllAux(scanner);
        } catch (FileNotFoundException e) {
            throw new IndexException();
        }
    }

    public void add(Chore chore, Integer index) {
        List<Chore> choreList = getAll(index).stream().collect(Collectors.toList());
        choreList.add(chore);
        overwriteChores(choreList, index);
    }

    public void update(Chore chore, String roomName, String sectionName, String description, Integer index) {
        Chore finalChore = chore;
        List<Chore> choreList = getAll(index).stream()
                .map(choreItem -> matches(choreItem, roomName, sectionName, description) ? finalChore : choreItem)
                .collect(Collectors.toList());
        overwriteChores(choreList, index);
    }

    public void delete(String roomName, String sectionName, String description, Integer index) {

        Predicate<Chore> isSame = chore -> {
            Boolean sameRoom = chore.getSection().getRoom().getName().equals(roomName);
            Boolean sameSection = chore.getSection().getName().equals(sectionName);
            Boolean sameDescription = chore.getDescription().equals(description);
            return sameRoom && sameSection && sameDescription;
        };

        List<Chore> choreList = getAll(index).stream()
                .filter(choreItem -> !isSame.test(choreItem))
                .collect(Collectors.toList());
        overwriteChores(choreList, index);
    }

    public Path getPath(Integer index) {
        return getFile(index).toPath();
    }

    public void saveFile(Integer index, byte[] file) {
        try (FileOutputStream fos = new FileOutputStream(String.format("store/%d.txt", index))) {
            fos.write(file);
        } catch (IOException e) {
            throw new IndexException();
        }
    }



    private List<Chore> getAllAux(Scanner scanner) {

        List<Chore> result = new ArrayList<Chore>();
        Set<Room> roomSet = new HashSet<Room>();
        Set<Section> sectionSet = new HashSet<Section>();

        while (scanner.hasNextLine()) {
            ParsedLine parsedLine = parseLine(scanner);

            Boolean isNewRoom = !roomSet.stream().anyMatch(
                    room -> room.getName().equals(parsedLine.roomName)
            );
            if (isNewRoom) {
                roomSet.add(new Room(parsedLine.roomName));
            }
            Room room = roomSet.stream().filter(
                    roomItem -> roomItem.getName().equals(parsedLine.roomName)
            ).findAny().get();

            Boolean isNewSection = !sectionSet.stream().anyMatch(section -> section.getName().equals(parsedLine.sectionName) && section.getRoom().getName().equals(parsedLine.roomName));
            if (isNewSection) {
                sectionSet.add(new Section(parsedLine.sectionName, room));
            }
            Section section = sectionSet.stream().filter(
                    sectionItem -> sectionItem.getRoom().getName().equals(parsedLine.roomName) && sectionItem.getName().equals(parsedLine.sectionName)
            ).findAny().get();

            result.add(new Chore(section, parsedLine.description, Periodicity.valueOf(parsedLine.periodicityName)));
        }
        return result;
    }

    private File getFile(Integer index) {
        return new File(String.format("store/%d.txt", index));
    }

    private ParsedLine parseLine(Scanner scanner) {
        List<String> line = Arrays.stream(scanner.nextLine().split(" :: ")).toList();
        return new ParsedLine(line.get(0), line.get(1), line.get(2), line.get(3));
    }

    private void overwriteChores(List<Chore> choreList, Integer index) {
        try (PrintStream printer = new PrintStream(getFile(index))) {
            for (Chore chore: choreList) printer.println(toLine(chore));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String toLine(Chore chore) {
        return String.format(
                "%s :: %s :: %s :: %s",
                chore.getSection().getRoom().getName(),
                chore.getSection().getName(),
                chore.getDescription(),
                chore.getPeriodicity().name()
        );
    }

    private Boolean matches(Chore choreItem, String roomName, String sectionName, String description) {
        return choreItem.getSection().getRoom().getName().equals(roomName)
                && choreItem.getSection().getName().equals(sectionName)
                && choreItem.getDescription().equals(description);
    }

    @PostConstruct
    private void clearStorePostConstruct() {
        log.info("Clearing storage post construct.");
        clearStore();
    }

    @PreDestroy
    private void clearStoragePreDestroy() {
        log.info("Clearing storage pre destroy.");
        clearStore();
    }

    @Scheduled(cron = "0 0 2 * * *")
    protected void clearStoreScheduled() {
        log.info("Clearing storage as scheduled (02:00).");
        clearStore();
    }

    private void clearStore() {
        Arrays.stream(new File("store").listFiles()).forEach(File::delete);
    }
}
