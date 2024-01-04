package lk.ijse.dep11.edupanel.api;

import com.google.cloud.storage.Bucket;
import lk.ijse.dep11.edupanel.service.ServiceFactory;
import lk.ijse.dep11.edupanel.service.custom.LecturerService;
import lk.ijse.dep11.edupanel.store.AppStore;
import lk.ijse.dep11.edupanel.to.LecturerTO;
import lk.ijse.dep11.edupanel.to.request.LecturerReqTO;
import lk.ijse.dep11.edupanel.util.LecturerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lecturers")
@CrossOrigin
public class LecturerHttpController {
    @Autowired
    private EntityManager em;
    @Autowired
    private Bucket bucket;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "multipart/form-data", produces = "application/json")
    public LecturerTO createNewLecturer(@ModelAttribute @Validated(LecturerReqTO.Create.class)
                                        LecturerReqTO lecturerReqTo) {
        AppStore.setBucket(bucket);
        AppStore.setEntityManager(em);
        LecturerService lecturerService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.LECTURER);
        return lecturerService.saveLecturer(lecturerReqTo);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}", consumes = "multipart/form-data")
    public void updateLecturerDetailsViaMultipart(@PathVariable("lecturer-id") Integer lecturerId,
                                                  @ModelAttribute @Validated(LecturerReqTO.Update.class) LecturerReqTO lecturerReqTO) {
        AppStore.setBucket(bucket);
        AppStore.setEntityManager(em);
        LecturerService lecturerService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.LECTURER);
        lecturerReqTO.setId(lecturerId);
        lecturerService.updateLecturerDetails(lecturerReqTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}", consumes = "application/json")
    public void updateLecturerDetailsViaJson(@PathVariable("lecturer-id") Integer lecturerId,
                                             @RequestBody @Validated LecturerTO lecturerTO) {
        AppStore.setBucket(bucket);
        AppStore.setEntityManager(em);
        LecturerService lecturerService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.LECTURER);
        lecturerTO.setId(lecturerId);
        lecturerService.updateLecturerDetails(lecturerTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{lecturer-id}")
    public void deleteLecturer(@PathVariable("lecturer-id") Integer lecturerId) {
        AppStore.setBucket(bucket);
        AppStore.setEntityManager(em);
        LecturerService lecturerService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.LECTURER);
        lecturerService.deleteLecturer(lecturerId);
    }

    @GetMapping(produces = "application/json")
    public List<LecturerTO> getAllLecturers() {
        AppStore.setBucket(bucket);
        AppStore.setEntityManager(em);
        LecturerService lecturerService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.LECTURER);
        return lecturerService.getLecturers(null);
    }

    @GetMapping(value = "/{lecturer-id}", produces = "application/json")
    public LecturerTO getLecturerDetails(@PathVariable("lecturer-id") Integer lecturerId) {
        AppStore.setBucket(bucket);
        AppStore.setEntityManager(em);
        LecturerService lecturerService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.LECTURER);
        return lecturerService.getLecturerDetails(lecturerId);
    }

    @GetMapping(params = "type=full-time", produces = "application/json")
    public List<LecturerTO> getFullTimeLecturers() {
        AppStore.setBucket(bucket);
        AppStore.setEntityManager(em);
        LecturerService lecturerService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.LECTURER);
        return lecturerService.getLecturers(LecturerType.FULL_TIME);
    }

    @GetMapping(params = "type=visiting", produces = "application/json")
    public List<LecturerTO> getVisitingLecturers() {
        AppStore.setBucket(bucket);
        AppStore.setEntityManager(em);
        LecturerService lecturerService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.LECTURER);
        return lecturerService.getLecturers(LecturerType.VISITING);
    }
}
