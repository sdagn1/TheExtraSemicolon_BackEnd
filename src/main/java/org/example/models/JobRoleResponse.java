package org.example.models;

import org.example.enums.Capability;
import org.example.enums.JobBands;
import org.example.enums.Locations;

import java.util.Date;
import java.util.List;

public class JobRoleResponse {



    private int roleId;
    private String roleName;
    private Locations locations;
    private List<Locations> locations2;
    private Capability capability;
    private JobBands band;
    private Date closingDate;

    public JobRoleResponse(final int roleId,
                           final String roleName,
                           final List<Locations> locations2,
                           final Capability capability,
                           final JobBands band,
                           final Date closingDate) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.locations2 = locations2;
        this.capability = capability;
        this.band = band;
        this.closingDate = closingDate;
    }

    public int getRoleId() {
        return roleId;
    }

    public List<Locations> getLocations2() {
        return locations2;
    }

    public void setLocations2(final List<Locations> locations2) {
        this.locations2 = locations2;
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

    public Capability getCapability() {
        return capability;
    }

    public void setCapability(final Capability capability) {
        this.capability = capability;
    }

    public Locations getLocations() {
        return locations;
    }

    public void setLocations(final Locations locations) {
        this.locations = locations;
    }

    public JobBands getBand() {
        return band;
    }

    public void setBand(final JobBands band) {
        this.band = band;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(final Date closingDate) {
        this.closingDate = closingDate;
    }
}
