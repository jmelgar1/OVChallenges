package org.ovclub.ovchallenges.object.events;

import java.util.Map;

public class EventDTO {
    protected String name;
    protected String date;
    protected Map<String, Integer> results;

    public EventDTO(String name, Map<String, Integer> results) {
        this.name = name;
        this.results = results;
    }

    public String getName() { return name; }
    public Map<String, Integer> getResults() { return results; }
}
