package dev.choo;

import org.junit.jupiter.api.Test;

import java.util.List;

import static dev.choo.GatherExamples.count;
import static dev.choo.GatherExamples.doNothing;
import static dev.choo.GatherExamples.filter;
import static dev.choo.GatherExamples.map;
import static org.assertj.core.api.Assertions.assertThat;

public class GatherExamplesTest {

    @Test
    public void testGatherers() {
        final var names = List.of("Emma", "Noah", "Olivia", "Liam", "Sophia", "Jackson", "Ava", "Lucas", "Isabella", "Ethan");

        // Example do nothing gatherer
        assertThat(names).isEqualTo(names.stream().gather(doNothing()).toList());

        // Example map gatherer
        final var names2 = List.of("Emma", "Noah", "Olivia", "Liam");
        assertThat(names2.stream().gather(map((name -> name.substring(0, 1)))).toList()).isEqualTo(
                List.of("E", "N", "O", "L"));

        // Example count gatherer
        assertThat(names.stream().gather(count()).findFirst().orElseThrow()).isEqualTo(10);
        assertThat(names2.stream().gather(count()).findFirst().orElseThrow()).isEqualTo(4);

        // Example filter gatherer
        assertThat(names.stream().gather(filter(name -> name.length() == 4)).toList()).isEqualTo(List.of("Emma", "Noah", "Liam"));

    }
}
