package org.example.models;

import java.util.Date;

public class JobRole {
    private int roleId;
    private String roleName;
    private String description;
    private String responsibilities;
    public String locations;
    private String linkToJobSpec;
    private String capability;
    private String band;
    private Date closingDate;
    private boolean status;
    private int positionsAvailable;

    // Removed capability, status, positionsAvailable
    // from constructor due to 7 parameters limit.
    public JobRole(
            final int roleId,
            final String roleName,
            final String description,
            final String responsibilities,
            final String linkToJobSpec,
            final String band,
            final Date closingDate) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.description = description;
        this.responsibilities = responsibilities;
        this.linkToJobSpec = linkToJobSpec;
        this.band = band;
        this.closingDate = closingDate;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(final int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(final String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(final String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getLinkToJobSpec() {
        return linkToJobSpec;
    }

    public void setLinkToJobSpec(final String linkToJobSpec) {
        this.linkToJobSpec = linkToJobSpec;
    }

    public String getCapability() {
        return capability;
    }

    public void setCapability(final String capability) {
        this.capability = capability;
    }

    public String getBand() {
        return band;
    }

    public void setBand(final String band) {
        this.band = band;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(final Date closingDate) {
        this.closingDate = closingDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public int getPositionsAvailable() {
        return positionsAvailable;
    }

    public void setPositionsAvailable(final int positionsAvailable) {
        this.positionsAvailable = positionsAvailable;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(final String locations) {
        this.locations = locations;
    }
}
