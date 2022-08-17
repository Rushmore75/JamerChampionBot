package discordbot.commands;

@FunctionalInterface
public interface ISlashCommandFunction<T> {
    void run(T event);
}

// TODO this is already a java interface, find out what it is called and just use that.
