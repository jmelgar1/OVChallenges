package org.ovclub.ovchallenges.object.events;

import java.util.Map;

public class MinorEvent extends EventDTO {

    public MinorEvent(String name, Map<String, Integer> results) {
        super(name, results);
    }
}
