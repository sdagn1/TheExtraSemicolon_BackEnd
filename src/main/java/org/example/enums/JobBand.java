package org.example.enums;

public enum JobBand {
    LEADERSHIP_COMMUNITY("Leadership Community", 0),
    PRINCIPAL("Principal", 1),
    MANAGER("Manager", 2),
    CONSULTANT("Consultant", 3),
    SENIOR_ASSOCIATE("Senior Associate", 4),
    ASSOCIATE("Associate", 5),
    TRAINEE("Trainee", 6),
    APPRENTICE("Apprentice", 7);
    private final String jobBand;
    private final int index;

    JobBand(final String jobBand, final int index) {
        this.jobBand = jobBand;
        this.index = index;
    }

    public String getJobBand() {
        return jobBand;
    }

    @Override public String toString() {
        return jobBand;
    }

    public int getIndex() {
        return index;
    }

    public static JobBand fromString(final String text) {
        for (JobBand b : JobBand.values()) {
            if (b.jobBand.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No enum constant " + text);
    }
}
