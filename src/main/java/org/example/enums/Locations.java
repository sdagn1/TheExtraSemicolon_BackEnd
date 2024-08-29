package org.example.enums;

public enum Locations {
    AMSTERDAM("Amsterdam"),
    ATLANTA("Atlanta"),
    BELFAST("Belfast"),
    BIRMINGHAM("Birmingham"),
    BUENOS_AIRES("Buenos Aires"),
    DERRY_LONDONDERRY("Derry-Londonderry"),
    DUBLIN("Dublin"),
    DUSSELDORF("Dusseldorf"),
    EDINBURGH("Edinburgh"),
    FRANKFURT("Frankfurt"),
    GDANSK("Gdansk"),
    HAMBURG("Hamburg"),
    HOMEWORKER_AUSTRALIA("Homeworker - Australia"),
    HOMEWORKER_CANADA_ALBERTA("Homeworker - Canada - Alberta"),
    HOMEWORKER_CANADA_BRITISH_COLUMBIA("Homeworker - Canada - British Columbia"),
    HOMEWORKER_CANADA_NOVA_SCOTIA("Homeworker - Canada - Nova Scotia"),
    HOMEWORKER_CANADA_ONTARIO("Homeworker - Canada - Ontario"),
    HOMEWORKER_CANADA_QUEBEC("Homeworker - Canada - Quebec"),
    HOMEWORKER_FINLAND("Homeworker - Finland"),
    HOMEWORKER_FRANCE("Homeworker - France"),
    HOMEWORKER_GERMANY("Homeworker - Germany"),
    HOMEWORKER_IRELAND("Homeworker - Ireland"),
    HOMEWORKER_NETHERLANDS("Homeworker - Netherlands"),
    HOMEWORKER_NORWAY("Homeworker - Norway"),
    HOMEWORKER_POLAND("Homeworker - Poland"),
    HOMEWORKER_ROMANIA("Homeworker - Romania"),
    HOMEWORKER_SWEDEN("Homeworker - Sweden"),
    HOMEWORKER_SWITZERLAND("Homeworker - Switzerland"),
    HOMEWORKER_UK("Homeworker - UK"),
    HOMEWORKER_USA("Homeworker - USA"),
    INDIANAPOLIS("Indianapolis"),
    LONDON("London"),
    TORONTO("Toronto");

    private final String location;

    Locations(final String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public static Locations fromString(final String text) {
        for (Locations b : Locations.values()) {
            if (b.location.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No enum constant " + text);
    }
}
