package com.example.patientaccount.doctor.controller;

import com.example.patientaccount.doctor.dto.CreateVisitRequest;
import com.example.patientaccount.doctor.dto.GetVisitResponse;
import com.example.patientaccount.doctor.dto.GetVisitsResponse;
import com.example.patientaccount.doctor.dto.UpdateVisitRequest;
import com.example.patientaccount.doctor.entity.Doctor;
import com.example.patientaccount.doctor.entity.Visit;
import com.example.patientaccount.doctor.service.DoctorService;
import com.example.patientaccount.doctor.service.VisitService;

import javax.inject.Inject;
import javax.print.Doc;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Optional;

@Path("")
public class VisitController {
    private VisitService visitService;
    private DoctorService doctorService;

    public VisitController() {
    }

    @Inject
    public void setVisitService(VisitService visitService) {

        this.visitService = visitService;
    }

    @Inject
    public void setDoctorService(DoctorService doctorService) {

        this.doctorService = doctorService;
    }

    @GET
    @Path("/doctors/{surname}/visits")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVisits(@PathParam("surname") String surname) {
        Optional<List<Visit>> visits = visitService.findAllByDoctor(surname);
        if (visits.isEmpty()) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } else {
            return Response
                    .ok(GetVisitsResponse.entityToDtoMapper().apply(visits.get()))
                    .build();
        }
    }

    @GET
    @Path("/doctors/{surname}/visits/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVisit(@PathParam("surname") String surname, @PathParam("id") Long id) {
        Optional<Visit> visit = visitService.findByDoctor(surname, id);
        if (visit.isEmpty()) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } else {
            return Response
                    .ok(GetVisitResponse.entityToDtoMapper().apply(visit.get()))
                    .build();
        }
    }

    @POST
    @Path("/doctors/{surname}/visits")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postVisit(@PathParam("surname") String surname, CreateVisitRequest request) {

        Optional<Doctor> doctor = doctorService.find(surname);
        if (doctor.isPresent()) {
            Visit visit = CreateVisitRequest.dtoToEntityMapper().apply(request);
            visitService.createForDoctor(visit, surname);
            return Response
                    .created(UriBuilder
                            .fromMethod(VisitController.class, "getVisit")
                            .build(surname, visit.getId()))
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @PUT
    @Path("/doctors/{surname}/visits/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putVisit(@PathParam("surname") String surname, @PathParam("id") Long id, UpdateVisitRequest request) {

        Optional<Doctor> doctor = doctorService.find(surname);
        if (doctor.isPresent()) {
            Optional<Visit> visit = visitService.findByDoctor(surname, id);

            if (visit.isPresent()) {
                UpdateVisitRequest.dtoToEntityUpdater().apply(visit.get(), request);

                visitService.update(visit.get());
                return Response
                        .noContent()
                        .build();
            } else {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .build();
            }
        } else{
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @DELETE
    @Path("/doctors/{surname}/visits/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteVisit(@PathParam("surname") String surname, @PathParam("id") Long id) {

        Optional<Doctor> doctor = doctorService.find(surname);
        if (doctor.isPresent()) {
            Optional<Visit> visit = visitService.findByDoctor(surname, id);
            if (visit.isPresent()) {
                visitService.delete(id);
                return Response
                        .ok(UriBuilder
                                .fromMethod(VisitController.class, "getVisit")
                                .build(doctor.get().getSurname(), id))
                        .build();
            } else {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .build();
            }
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }
}

