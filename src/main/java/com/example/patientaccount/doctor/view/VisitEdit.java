package com.example.patientaccount.doctor.view;

import com.example.patientaccount.doctor.entity.Visit;
import com.example.patientaccount.doctor.model.VisitCreateModel;
import com.example.patientaccount.doctor.model.VisitEditModel;
import com.example.patientaccount.doctor.service.VisitService;
import lombok.Getter;
import lombok.Setter;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@ViewScoped
@Named
public class VisitEdit implements Serializable {
    private VisitService visitService;

    @Setter
    @Getter
    private Long id;

    @Getter
    private VisitEditModel visitEditModel;

    @Inject
    public VisitEdit(VisitService service) {
        this.visitService = service;
    }

    public void init() throws IOException {
        Optional<Visit> visit = visitService.find(id);
        if (visit.isPresent()) {
            this.visitEditModel = VisitEditModel.entityToModelMapper().apply(visit.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Visit not found");
        }
    }

    public String saveAction() {
        visitService.update(VisitEditModel.modelToEntityUpdater().apply(visitService.find(id).orElseThrow(), visitEditModel));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
