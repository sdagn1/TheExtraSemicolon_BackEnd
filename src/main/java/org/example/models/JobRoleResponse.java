package org.example.models;

import java.util.Date;
import java.util.List;

public class JobRoleResponse {
    private String formattedLocations;
    private int roleId;
    private String roleName;
    private List<String> locations;
    private String capability;
    private String band;
    private Date closingDate;

    public JobRoleResponse(final int roleId,
                           final String roleName,
                           final List<String> locations,
                           final String capability,
                           final String band,
                           final Date closingDate) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.locations = locations;
        this.capability = capability;
        this.band = band;
        this.closingDate = closingDate;
    }

    public int getRoleId() {
        return roleId;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(final List<String> locations) {
        this.locations = locations;
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

    public String getFormattedLocations() {
        return formattedLocations;
    }

    public void setFormattedLocations(final String formattedLocations) {
        this.formattedLocations = formattedLocations;
    }
}
