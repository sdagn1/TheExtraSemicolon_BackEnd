package org.example.enums;

public enum Capability {
    ENGINEERING("Engineering"),
    PLATFORMS("Platforms"),
    DATA_AND_ARTIFICIAL_INTELLIGENCE("Data and Artificial Intelligence"),
    CYBER_SECURITY("Cyber Security"),
    WORKDAY("Workday"),
    EXPERIENCE_DESIGN("Experience Design"),
    PRODUCT("Product"),
    DELIVERY("Delivery"),
    OPERATIONS("Operations"),
    BUSINESS_DEVELOPMENT_AND_MARKETING("Business Development and Marketing"),
    ORGANISATIONAL_STRATEGY_AND_PLANNING(
            "Organisational Strategy and Planning"),
    PEOPLE("People"),
    COMMERCIAL_AND_FINANCIAL_MANAGEMENT("Commercial and Financial Management"),
    BUSINESS_SERVICES_SUPPORT("Business Services Support");

    private final String capability;

    Capability(final String capability) {
        this.capability = capability;
    }

    public String getCapability() {
        return capability;
    }


    public static Capability fromString(final String text) {
        for (Capability b : Capability.values()) {
            if (b.capability.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No enum constant " + text);
    }
}
