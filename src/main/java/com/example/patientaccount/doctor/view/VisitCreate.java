package com.example.patientaccount.doctor.view;

import com.example.patientaccount.doctor.model.DoctorModel;
import com.example.patientaccount.doctor.model.DoctorsModel;
import com.example.patientaccount.doctor.model.VisitCreateModel;
import com.example.patientaccount.doctor.service.DoctorService;
import com.example.patientaccount.doctor.service.VisitService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ConversationScoped
@Named
@NoArgsConstructor
public class VisitCreate implements Serializable {
    private VisitService visitService;
    private DoctorService doctorService;

    @Setter
    @Getter
    private Long id;

    @Getter
    private VisitCreateModel visitCreateModel;

    @Getter
    private List<DoctorModel> doctors;

    private Conversation conversation;

    @Inject
    public VisitCreate(VisitService visitService, DoctorService doctorService, Conversation conversation) {
        this.visitService = visitService;
        this.doctorService = doctorService;
        this.conversation = conversation;
    }

    @PostConstruct
    public void init() {
        doctors = doctorService.findAll().stream()
                .map(DoctorModel.entityToModelMapper())
                .collect(Collectors.toList());
        visitCreateModel = VisitCreateModel.builder().build();
        conversation.begin();
    }

    public Object goToBasicAction() {
        return "/doctor/visit_create__basic.xhtml?faces-redirect=true";
    }

    public String cancelAction() {
        conversation.end();
        return "/doctor/doctor_list.xhtml?faces-redirect=true";
    }

    public String goToConfirmAction() {
        return "/doctor/visit_create__confirm.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        visitService.create(VisitCreateModel.modelToEntityMapper(
                doctor -> doctorService.find(doctor).orElseThrow()).apply(visitCreateModel));
        conversation.end();
        return "/doctor/doctor_list.xhtml?faces-redirect=true";
    }

    public String getConversationId() {
        return conversation.getId();
    }
}
