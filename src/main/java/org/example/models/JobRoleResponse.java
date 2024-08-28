package org.example.models;

import java.util.Date;

public class JobRoleResponse {
    private int roleId;
    private String roleName;
    private String location;
    private String capability;
    private String band;
    private Date closingDate;

    public JobRoleResponse(final int roleId, final String roleName,
                   final String location,
                   final String capability,
                   final String band, final Date closingDate) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.capability = capability;
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

    public String getCapability() {
        return capability;
    }

    public void setCapability(final String capability) {
        this.capability = capability;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
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
}
