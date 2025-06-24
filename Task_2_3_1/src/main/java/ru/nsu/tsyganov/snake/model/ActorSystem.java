package ru.nsu.tsyganov.snake.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ActorSystem {
    //private final ExecutorService executor;
    //private final List<Actor> actors;
    private final GameModel model;
    private final List<Actor> actors = Collections.synchronizedList(new ArrayList<>());

    public ActorSystem(GameModel model) {
        this.model = model;
        //this.actors = new ArrayList<>();
        //this.executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public void update() {
        synchronized (actors) {
            actors.forEach(actor -> {
                if (actor.isAlive()) actor.update(model);
            });
            actors.removeIf(actor -> !actor.isAlive());
        }
    }

    public List<Actor> getActors() {
        return actors;
    }

    public <T extends Actor> List<T> getActorsOfType(Class<T> type) {
        return actors.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    public void clear() {
        actors.clear();
    }

    /*public void shutdown() {
        executor.shutdown();
    }*/
}