package org.example.models;

import org.example.enums.Capability;
import org.example.enums.JobBand;
import org.example.enums.Location;

import java.util.Date;
import java.util.List;

public class JobRole {
    private int roleId;
    private String roleName;
    private String description;
    private String responsibilities;
    public List<Location> locations;
    private String linkToJobSpec;
    private Capability capability;
    private JobBand band;
    private Date closingDate;
    private boolean status;
    private int positionsAvailable;
    public JobRole(
            final int roleId,
            final String roleName,
            final String description,
            final String responsibilities,
            final String linkToJobSpec,
            final JobBand band,
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

    public Capability getCapability() {
        return capability;
    }

    public void setCapability(final Capability capability) {
        this.capability = capability;
    }

    public JobBand getBand() {
        return band;
    }

    public void setBand(final JobBand band) {
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

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(final List<Location> locations) {
        this.locations = locations;
    }
}
