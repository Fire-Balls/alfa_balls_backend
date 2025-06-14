package org.fireballs.alfaballs.domain;

import lombok.Getter;

@Getter
public enum Type {
    BUG("Bug"),
    STORY("Story"),
    TASK("Task");

    private final String displayName;

    Type(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Type fromString(String value) {
        for (Type type : Type.values()) {
            if (type.displayName.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + value);
    }
}
