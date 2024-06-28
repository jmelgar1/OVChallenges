package org.ovclub.ovchallenges.object.events;

import java.util.Map;

public class MajorEvent extends EventDTO {

    public MajorEvent(String name, Map<String, Integer> results) {
        super(name, results);
    }
}
