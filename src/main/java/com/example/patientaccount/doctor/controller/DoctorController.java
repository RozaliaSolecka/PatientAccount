package com.example.patientaccount.doctor.controller;

import com.example.patientaccount.doctor.dto.CreateDoctorRequest;
import com.example.patientaccount.doctor.dto.GetDoctorResponse;
import com.example.patientaccount.doctor.dto.GetDoctorsResponse;
import com.example.patientaccount.doctor.dto.UpdateDoctorRequest;
import com.example.patientaccount.doctor.entity.Doctor;
import com.example.patientaccount.doctor.service.DoctorService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;

@Path("")
public class DoctorController {
    private DoctorService doctorService;

    public DoctorController() {
    }

    @Inject
    public void setService(DoctorService service) {
        this.doctorService = service;
    }

    @GET
    @Path("/doctors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDoctors() {
        return Response
                .ok(GetDoctorsResponse.entityToDtoMapper().apply(doctorService.findAll()))
                .build();
    }

    @GET
    @Path("/doctors/{surname}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDoctor(@PathParam("surname") String surname) {
        Optional<Doctor> doctor = doctorService.find(surname);
        if (doctor.isPresent()) {
            return Response
                    .ok(GetDoctorResponse.entityToDtoMapper().apply(doctor.get()))
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @POST
    @Path("/doctors")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postDoctor(CreateDoctorRequest request) {
        Doctor doctor = CreateDoctorRequest.dtoToEntityMapper().apply(request);
        doctorService.create(doctor);
        return Response
                .created(UriBuilder
                        .fromMethod(DoctorController.class, "getDoctor")
                        .build(doctor.getSurname()))
                .build();
    }

    @DELETE
    @Path("/doctors/{surname}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteDoctor(@PathParam("surname") String surname) {
        Optional<Doctor> doctor = doctorService.find(surname);
        if (doctor.isPresent()) {
            doctorService.delete(doctor.get().getSurname());
            return Response
                    .ok(UriBuilder
                            .fromMethod(DoctorController.class, "getDoctor")
                            .build(doctor.get().getSurname()))
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @PUT
    @Path("/doctors/{surname}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putDoctor(@PathParam("surname") String surname, UpdateDoctorRequest request) {
        Optional<Doctor> doctor = doctorService.find(surname);

        if (doctor.isPresent()) {
            UpdateDoctorRequest.dtoToEntityUpdater().apply(doctor.get(), request);

            doctorService.update(doctor.get());
            return Response
                    .noContent()
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }
}
