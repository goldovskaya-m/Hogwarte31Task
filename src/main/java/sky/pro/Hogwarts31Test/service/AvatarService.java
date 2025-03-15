package sky.pro.Hogwarts31Test.service;

import org.springframework.web.multipart.MultipartFile;
import sky.pro.Hogwarts31Test.model.Avatar;
import sky.pro.Hogwarts31Test.model.dto.AvatarView;

import java.io.IOException;

public interface AvatarService {

    long uploadAvatar(long studentId, MultipartFile file) throws IOException;


    Avatar getAvatarFromDb(long studentId);


    //byte[] getAvatarFromLocal(long studentId) throws IOException;
    //Avatar getAvatarFromLocal(long studentId) throws IOException;

    AvatarView getAvatarFromLocal(long studentId) throws IOException;

}
