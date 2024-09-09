package org.example.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.example.daos.FileImportDao;
import org.example.exceptions.DoesNotExistException;
import org.example.models.UserRole;
import org.example.services.BucketService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

@Api("Upload Api")
@Path("/api/upload")
public class FileImportController {
    BucketService bucketService;

    public FileImportController(final BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({UserRole.ADMIN, UserRole.USER})
    @ApiOperation(
            value = "Imports Job Roles into database",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = List.class
    )
    public Response postCsvFIle() {
        return Response.ok().entity(bucketService.importJobRoles())
                .build();
    }



}
