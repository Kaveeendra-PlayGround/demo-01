package lk.ijse.dep11.edupanel.service.util;

import com.google.rpc.Help;
import lk.ijse.dep11.edupanel.entity.Lecturer;
import lk.ijse.dep11.edupanel.entity.LinkedIn;
import lk.ijse.dep11.edupanel.to.LecturerTO;
import lk.ijse.dep11.edupanel.to.request.LecturerReqTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class Transformer {

    private final ModelMapper mapper = new ModelMapper();

    public Transformer() {
//        mapper.typeMap(Lecturer.class, LecturerTO.class)
//                .addMapping(lecturer -> lecturer.getLinkedIn().getUrl(), LecturerTO::setLinkedin);
//        mapper.typeMap(LecturerTO.class, Lecturer.class)
//                .addMapping(LecturerTO::getLinkedin, (lecturer, o) ->
//                        lecturer.getLinkedIn().setUrl((String) o));

        mapper.typeMap(LinkedIn.class, String.class)
                .setConverter(ctx -> ctx.getSource().getUrl());
        mapper.typeMap(String.class, LinkedIn.class)
                .setConverter(ctx -> new LinkedIn(null, ctx.getSource()));
    }

    public Lecturer fromLecturerReqTO(LecturerReqTO lecturerReqTO){
        Lecturer lecturer = mapper.map(lecturerReqTO, Lecturer.class);
        if (lecturerReqTO.getLinkedin() == null){
            lecturer.setLinkedIn(null);
        }else{
            lecturer.getLinkedIn().setLecturer(lecturer);
        }
        if (lecturerReqTO.getPicture() == null || lecturerReqTO.getPicture().isEmpty())
                lecturer.setPicture(null);

        return lecturer;
    }

    public Lecturer fromLecturerTO(LecturerTO lecturerTO){
        Lecturer lecturer = mapper.map(lecturerTO, Lecturer.class);
        lecturer.getLinkedIn().setLecturer(lecturer);
        return lecturer;
    }

    public LecturerTO toLecturerTO(Lecturer lecturer){
        return mapper.map(lecturer, LecturerTO.class);
    }

    public List<LecturerTO> toLectureTOList(List<Lecturer> lecturerList){
        return lecturerList.stream().map(this::toLecturerTO).collect(Collectors.toList());
    }

}