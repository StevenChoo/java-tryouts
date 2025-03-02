package dev.choo;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Gatherer;
import java.util.stream.Gatherer.Downstream;
import java.util.stream.Gatherer.Integrator;

/**
 * Some example copied from: <a href="https://github.com/nipafx/demo-java-x/tree/main/src/main/java/dev/nipafx/demo/java_next/api/gather">CustomGatherers.java</a>
 * <p>
 * see <a href="https://github.com/nipafx/demo-java-x/tree/main">demo-java-x</a> for more Java examples
 */
public class GatherExamples {

    public static void main(String[] args) {

        var names = List.of("Emma", "Noah", "Olivia", "Liam", "Sophia", "Jackson", "Ava", "Lucas", "Isabella", "Ethan");

        // Same list is printed
        System.out.println("Do nothing gatherer:");
        names.stream().gather(doNothing()).forEach(System.out::println);

        // Only the first letter
        System.out.println("Map gatherer:");
        names.stream().gather(map((name -> name.charAt(0)))).forEach(System.out::println);

        // Count items
        System.out.println("Count gatherer:");
        System.out.println(names.stream().gather(count()).findFirst().orElseThrow());

        // Filter items
        System.out.println("Filter gatherer:");
        names.stream().gather(filter(name -> name.length() == 4)).toList().forEach(System.out::println);
    }

    // A Gatherer that just pushes the elements down stream
    // Gatherer<T, A, R> means T=type , A=type of mutable state , R=type of output elements
    public static <T> Gatherer<T, ?, T> doNothing() {
        Integrator<Void, T, T> integrator = (_, element, downstream) -> {
            downstream.push(element);
            return true;
        };
        return Gatherer.of(integrator);
    }

    // A Gatherer that acts as a mapper
    public static <T, R> Gatherer<T, ?, R> map(Function<? super T, ? extends R> mapper) {
        Integrator<Void, T, R> integrator = (_, element, downstream) -> {
            downstream.push(mapper.apply(element));
            return true;
        };
        return Gatherer.of(integrator);
    }

    // Give a total count of items in the list
    public static <T> Gatherer<T, ?, Long> count() {
        Supplier<AtomicLong> initializer = () -> new AtomicLong(0);
        Integrator<AtomicLong, T, Long> integrator = (counter, _, _) -> {
            counter.incrementAndGet();
            return true;
        };
        // Make sure to set ? super Long. Check javadoc for Gatherer.ofSequential
        BiConsumer<AtomicLong, Downstream<? super Long>> finisher = (counter, downstream) -> {
            downstream.push(counter.get());
        };

        return Gatherer.ofSequential(initializer, integrator, finisher);
    }

    // Filter items base on filter
    public static <T> Gatherer<T, ?, T> filter(Predicate<T> predicate) {
        Integrator<Void, T, T> integrator = (_, element, downstream) -> {
            if (predicate.test(element)) {
                downstream.push(element);
            }
            return true;
        };
        return Gatherer.of(integrator);
    }

}
