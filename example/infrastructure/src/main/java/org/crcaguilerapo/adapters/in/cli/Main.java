package org.crcaguilerapo.adapters.in.cli;

import org.crcaguilerapo.adapters.out.memory.ListRepository;
import org.crcaguilerapo.dtos.Message;
import org.crcaguilerapo.port.in.IMessagePort;
import org.crcaguilerapo.usecase.MessageUseCase;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.List;

@Command(name = "message", mixinStandardHelpOptions = true, version = "1.0")
public class Main implements Runnable {

    private final IMessagePort messagePort = new ListRepository();
    private final MessageUseCase messageUseCase = new MessageUseCase(messagePort);

    @Command(name = "save")
    public void save(
        @Option(names = {"--id"}) int id,
        @Option(names = {"--from"}) String from,
        @Option(names = {"--to"}) String to,
        @Option(names = {"--body"}) String body
    ) {
        messageUseCase.saveMessage(new Message(id, from, to, body));
        System.out.println("Saved");
    }

    @Command(name = "get")
    public void get() {
        var messages = messageUseCase.getMessages(List.of(1L, 2L));
        System.out.println(messages.toString());
    }

    @Override
    public void run() {
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}